package com.kgc.service.impl;

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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.stereotype.Service;

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

    @Value("${easy-buy-init.init-es-data}")
    private Boolean isInitSave;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ElasticsearchRestTemplate template;

    @Autowired
    private ProductESRepositoryUtil productESRepository;

    @Override
    public Boolean isNotInitEs() {
        return isInitSave;
    }

    @Override
    public Boolean saveProductListToEs() {
        List<Product> productList = productDao.getProductList();
        logger.debug("ProductServiceImpl saveProductListToEs result:" + productList);
        Iterable<Product> products = template.save(productList);
        return products.iterator().hasNext();
    }

    @Override
    public Message getProductListPages(Map<String, Object> paramMap) {
        Integer currentPage = (Integer) paramMap.get("currentPage"); // 当前页码
        Integer pageSize = (Integer) paramMap.get("pageSize"); // 分页容量
        Double minPrice = (Double) paramMap.get("minPrice"); // 最小价格
        Double maxPrice = (Double) paramMap.get("maxPrice"); // 最大价格
        String brandName = (String) paramMap.get("brandName"); // 品牌名
        String name = (String) paramMap.get("name"); // 商品名
        String categoryName = (String) paramMap.get("categoryName"); // 类型
        Boolean isSales = (Boolean) paramMap.get("isSales"); // 销量排序
        Boolean isNewProduct = (Boolean) paramMap.get("isNewProduct"); // 新品排序
        Boolean isPrice = (Boolean) paramMap.get("isPrice"); // 价格排序
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        if (name != null && !name.isEmpty()) {
            queryBuilder.must(QueryBuilders.matchQuery("name", name));
        }
        if (categoryName != null && !categoryName.isEmpty()) {
            queryBuilder.must(QueryBuilders.matchQuery("categoryName", categoryName));
        }
        if (maxPrice != null && maxPrice > 0) {
            queryBuilder.must(QueryBuilders.rangeQuery("price").lt(maxPrice));
            if (minPrice != null && minPrice >= 0 && minPrice < maxPrice) {
                queryBuilder.must(QueryBuilders.rangeQuery("price").gt(minPrice));
            }
        }
        if (brandName != null && !brandName.isEmpty()) {
            queryBuilder.must(QueryBuilders.termQuery("brandName", brandName));
        }

        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("categoryName");
        highlightBuilder.preTags("<font style='color:red'>");
        highlightBuilder.postTags("</font>");

        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        builder.withQuery(queryBuilder);
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
            List<String> highlightField = hit.getHighlightField("categoryName");
            if (!highlightField.isEmpty()) {
                product.setCategoryName(highlightField.get(0));
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
    public Message addProduct(Product product) {
        Integer flag = productDao.addProduct(product);
        if (flag > 0) {
            template.save(product);
            return Message.success();
        }
        return Message.error();
    }

    @Override
    public Message modProduct(Product product) {
        Integer flag = productDao.modProduct(product);
        if (flag > 0) {
            Document document = Document.create();
            UpdateQuery build = UpdateQuery.builder(String.valueOf(product.getId())).withDocument(document).build();
            template.update(build, IndexCoordinates.of("product"));
            return Message.success();
        }
        return Message.error();
    }

    @Override
    public Message delProduct(Product product) {
        Integer flag = productDao.delProduct(product);
        if (flag > 0) {
            productESRepository.deleteById(String.valueOf(product.getId()));
            return Message.success();
        }
        return Message.error();
    }

    @Override
    public Message getProductById(Integer id) {
        Product product = productDao.getProductById(id);
        if (product != null) {
            return Message.success(product);
        }
        return Message.error();
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
    public void downLoad(HttpServletRequest request, HttpServletResponse response) {
        String picPath = request.getParameter("picPath");
        if (picPath != null && !"".equals(picPath)){
            InputStream is  = null;
            ServletOutputStream sos = null;
            try {
                picPath = URLDecoder.decode(picPath,"utf-8");
                is = new FileInputStream(picPath);
                sos = response.getOutputStream();
                byte[] bytes = new byte[1024];
                int length;
                while ((length=is.read(bytes)) != -1){
                    sos.write(bytes,0,length);
                }
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }finally {
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
