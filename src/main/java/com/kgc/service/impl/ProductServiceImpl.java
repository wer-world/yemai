package com.kgc.service.impl;

import com.kgc.entity.File;
import com.kgc.enums.ProductExceptionEnum;
import com.kgc.exception.ServiceException;
import com.kgc.service.FileService;
import com.kgc.util.ProductESRepositoryUtil;
import com.kgc.dao.ProductDao;
import com.kgc.entity.Category;
import com.kgc.entity.Message;
import com.kgc.entity.Pages;
import com.kgc.entity.Product;
import com.kgc.service.ProductService;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品管理业务实现类
 *
 * @Author: 魏小可
 * @Date: 2024-03-19-10:09
 */
@Service
public class ProductServiceImpl implements ProductService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private FileService fileService;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ElasticsearchRestTemplate template;

    @Autowired
    private ProductESRepositoryUtil productESRepository;

    @Override
    public Boolean saveProductListToEs() {
        List<Product> productList = productDao.getProductList();
        logger.debug("ProductServiceImpl saveProductListToEs result:" + productList);
        Iterable<Product> products = template.save(productList);
        return products.iterator().hasNext();
    }

    @Override
    public Message getProductListPages(Map<String, Object> paramMap) {
        // 参数获取
        Integer currentPage = (Integer) paramMap.get("currentPage"); // 当前页码
        Integer pageSize = (Integer) paramMap.get("pageSize"); // 分页容量
        Double minPrice = (Double) paramMap.get("minPrice"); // 最小价格
        Double maxPrice = (Double) paramMap.get("maxPrice"); // 最大价格
        String brandName = (String) paramMap.get("brandName"); // 品牌名
        String productName = (String) paramMap.get("productName"); // 商品名
        String categoryName = (String) paramMap.get("categoryName"); // 类型
        String globalCondition = (String) paramMap.get("globalCondition"); // 全局条件
        Boolean isSales = (Boolean) paramMap.get("isSales"); // 销量排序
        Boolean isNewProduct = (Boolean) paramMap.get("isNewProduct"); // 新品排序
        Boolean isPrice = (Boolean) paramMap.get("isPrice"); // 价格排序

        // 条件拼接
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        boolean isSearch = false;
        if (globalCondition != null && !globalCondition.isEmpty()) {
            isSearch = true;
            queryBuilder.should(QueryBuilders.matchQuery("name", globalCondition));
            queryBuilder.should(QueryBuilders.matchQuery("categoryLeve1Name", globalCondition));
            queryBuilder.should(QueryBuilders.matchQuery("categoryLeve2Name", globalCondition));
            queryBuilder.should(QueryBuilders.matchQuery("categoryLeve3Name", globalCondition));
            queryBuilder.should(QueryBuilders.matchQuery("description", globalCondition));
            queryBuilder.should(QueryBuilders.termQuery("price.keyword", globalCondition));
            queryBuilder.should(QueryBuilders.termQuery("brandName", globalCondition));
        } else {
            if (productName != null && !productName.isEmpty()) {
                isSearch = true;
                queryBuilder.must(QueryBuilders.matchQuery("name", productName));
            }
            if (categoryName != null && !categoryName.isEmpty()) {
                isSearch = true;
                queryBuilder.should(QueryBuilders.matchQuery("categoryLeve1Name", categoryName));
                queryBuilder.should(QueryBuilders.matchQuery("categoryLeve2Name", categoryName));
                queryBuilder.should(QueryBuilders.matchQuery("categoryLeve3Name", categoryName));
            }
            if (maxPrice != null && maxPrice > 0) {
                isSearch = true;
                queryBuilder.must(QueryBuilders.rangeQuery("price").lt(maxPrice));
                if (minPrice != null && minPrice >= 0 && minPrice < maxPrice) {
                    queryBuilder.must(QueryBuilders.rangeQuery("price").gt(minPrice));
                }
            }
            if (brandName != null && !brandName.isEmpty()) {
                isSearch = true;
                queryBuilder.must(QueryBuilders.termQuery("brandName", brandName));
            }
        }

        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("description");
        highlightBuilder.preTags("<font style='color:red'>");
        highlightBuilder.postTags("</font>");

        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        if (isSearch) {
            builder.withQuery(queryBuilder);
        }

        if (isSales != null) {
            if (isSales) {
                builder.withSorts(SortBuilders.fieldSort("sales").order(SortOrder.DESC));
            } else {
                builder.withSorts(SortBuilders.fieldSort("sales").order(SortOrder.ASC));
            }
        } else if (isPrice != null) {
            if (isPrice) {
                builder.withSorts(SortBuilders.fieldSort("price").order(SortOrder.DESC));
            } else {
                builder.withSorts(SortBuilders.fieldSort("price").order(SortOrder.ASC));
            }
        } else if (isNewProduct != null) {
            if (isNewProduct) {
                builder.withSorts(SortBuilders.fieldSort("newProduct").order(SortOrder.DESC));
            } else {
                builder.withSorts(SortBuilders.fieldSort("newProduct").order(SortOrder.ASC));
            }
        }
        builder.withPageable(PageRequest.of(currentPage - 1, pageSize));
        builder.withHighlightBuilder(highlightBuilder);

        SearchHits<Product> hits = template.search(builder.build(), Product.class);
        Pages pages = new Pages(currentPage, pageSize, hits.getTotalHits(), null);
        List<Product> productList = new ArrayList<>();
        for (SearchHit<Product> hit : hits) {
            Product product = hit.getContent();
            List<String> highlightField = hit.getHighlightField("description");
            if (!highlightField.isEmpty()) {
                product.setDescription(highlightField.get(0));
            }
            productList.add(product);
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("productList", productList);
        resultMap.put("page", pages);
        return Message.success(resultMap);
    }

    @Override
    public Message getProduct(Product product) {
        Product newProduct = productDao.getProduct(product);
        if (newProduct != null) {
            return Message.success(newProduct);
        }
        return Message.error();
    }

    @Override
    @Transactional
    public Message addProduct(Product product, MultipartFile multipartFile) {
        // TODO 需要更新事务操作，未完成
        Message upload = fileService.upload(multipartFile);
        String picPath = (String) upload.getData();
        fileService.addFile(picPath); // 可以传入对象获取主键 picPath 可更改为文件对象

        Message fileIdByPicPath = fileService.getFileIdByPicPath(picPath); // 多余操作

        File data = (File) fileIdByPicPath.getData();
        if (data == null) {
            return Message.error();
        }
        product.setPicId(data.getId());
        Integer flag = productDao.addProduct(product);
        Product product1 = productDao.selectProIdByPicId(product.getPicId());
        if (product1 == null) {
            return Message.error();
        }
        File file = new File();
        file.setId(product.getPicId());
        file.setProduct_id(product1.getId());
        fileService.modifyProIdById(file);
        if (flag > 0) {
            template.save(product);
            return Message.success();
        }
        return Message.error();
    }
    @Override
    @Transactional
    public Message modProduct(Product product) {
        Integer flag = productDao.modProduct(product);
        if (flag == 0) {
            throw new ServiceException("ProductServiceImpl modProduct " + ProductExceptionEnum.PRODUCT_UPDATE_FAILURE.getMessage(), ProductExceptionEnum.PRODUCT_UPDATE_FAILURE.getMsg());
        }
        Document document = Document.create();
        UpdateQuery build = UpdateQuery.builder(String.valueOf(product.getId())).withDocument(document).build();
        template.update(build, IndexCoordinates.of("product"));
        return Message.success();
    }

    @Override
    @Transactional
    public Message modifyProductById(Product product, MultipartFile multipartFile) {
        Message upload = fileService.upload(multipartFile);
        String picPath = (String) upload.getData();
        File file = new File();
        file.setId(product.getPicId());
        file.setPicPath(picPath);
        fileService.modifyPicPathById(file);
        Integer flag = productDao.modifyProductById(product);
        if (flag == 0) {
            throw new ServiceException("ProductServiceImpl modProduct " + ProductExceptionEnum.PRODUCT_UPDATE_FAILURE.getMessage(), ProductExceptionEnum.PRODUCT_UPDATE_FAILURE.getMsg());
        }
        Document document = Document.create();
        UpdateQuery build = UpdateQuery.builder(String.valueOf(product.getId())).withDocument(document).build();
        template.update(build, IndexCoordinates.of("product"));
        return Message.success();
    }

    @Override
    @Transactional
    public Message delProduct(Product product) {
        Integer flag = productDao.delProduct(product);
        if (flag == 0) {
            throw new ServiceException("ProductServiceImpl delProduct " + ProductExceptionEnum.PRODUCT_DELETE_FAILURE.getMessage(), ProductExceptionEnum.PRODUCT_DELETE_FAILURE.getMsg());
        }
        productESRepository.deleteById(String.valueOf(product.getId()));
        return Message.success();
    }

    @Override
    public Product getProductById(Integer id) {
        Product productById = productDao.getProductById(id);
        if(productById != null){
            Message picPathByFileId = fileService.getPicPathByFileId(productById.getPicId());
            File file = (File) picPathByFileId.getData();
            productById.setPicPath(file.getPicPath());
        }
        return productById;
    }

    @Override
    public Message getSimilarProducts(Product product) {
        List<Product> productList = productDao.getSimilarProducts(product);
        if (productList != null && !productList.isEmpty()) {
            return Message.success(productList);
        }
        return Message.error();
    }

    @Override
    public Message getProductsByHigHestId(Category category) {
        List<Product> productList = productDao.getProductsByHigHestId(category);
        if (productList != null && !productList.isEmpty()) {
            return Message.success(productList);
        }
        return Message.error();
    }

    @Override
    public void downLoad(String picPath, HttpServletRequest request, HttpServletResponse response) {
        if (picPath != null && !"".equals(picPath)) {
            InputStream is = null;
            ServletOutputStream sos = null;
            try {
                picPath = URLDecoder.decode(picPath, "utf-8");
                is = new FileInputStream(picPath);
                sos = response.getOutputStream();
                byte[] bytes = new byte[1024];
                int length;
                while ((length = is.read(bytes)) != -1) {
                    sos.write(bytes, 0, length);
                }
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    if (sos != null) {
                        sos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    if (is != null) {
                        is.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
