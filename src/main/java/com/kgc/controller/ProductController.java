package com.kgc.controller;

import com.kgc.entity.Category;
import com.kgc.entity.Message;
import com.kgc.entity.Product;
import com.kgc.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 商品管理控制类
 *
 * @Author: 魏小可
 * @Date: 2024-03-19-9:30
 */
@RestController
@RequestMapping("product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("getProductListPages")
    public Message getProductListPages(@RequestBody Map<String, Object> paramMap) {
        paramMap.putIfAbsent("currentPage", 1); // 当前页码
        paramMap.putIfAbsent("pageSize", 20); // 分页容量
        if (paramMap.get("minPrice") != null) {
            Double minPrice = Double.parseDouble(paramMap.get("minPrice").toString()); // 最小价格
            paramMap.put("minPrice", minPrice); // 最小价格
        }
        if (paramMap.get("maxPrice") != null) {
            Double maxPrice = Double.parseDouble(paramMap.get("maxPrice").toString()); // 最小价格
            paramMap.put("maxPrice", maxPrice); // 最大价格
        }
        if (paramMap.get("isSales") != null) {
            String isSales = paramMap.get("isSales").toString();
            if (!isSales.equals("true") && !isSales.equals("false")) {
                paramMap.put("isSales", null); // 销量排序
            }
        }
        if (paramMap.get("isNewProduct") != null) {
            String isSales = paramMap.get("isNewProduct").toString();
            if (!isSales.equals("true") && !isSales.equals("false")) {
                paramMap.put("isNewProduct", null); // 新品排序
            }
        }
        if (paramMap.get("isPrice") != null) {
            String isSales = paramMap.get("isPrice").toString();
            if (!isSales.equals("true") && !isSales.equals("false")) {
                paramMap.put("isPrice", null); // 价格排序
            }
        }
        return productService.getProductListPages(paramMap);
    }

    @GetMapping("getProduct")
    public Message getProduct(Product product) {
        return productService.getProduct(product);
    }

    @PostMapping("addProduct")
    public Message addProduct(Product product, @RequestParam(value = "filePath", required = false) MultipartFile file) {
        if (file.getOriginalFilename() == null || file.getOriginalFilename().isEmpty()) {
            return Message.error("文件路径为空!");
        }
        return productService.addProduct(product, file);
    }

    @PostMapping("modProduct")
    public Message modProduct(@RequestBody Product product) {
        if (product.getId() == null) {
            return Message.error("商品id不能为空!");
        }
        return productService.modProduct(product);
    }

    @GetMapping("delProduct")
    public Message delProduct(Product product) {
        if (product.getId() == null) {
            return Message.error("商品id不能为空!");
        }
        return productService.delProduct(product);
    }

    @RequestMapping("getProductById")
    public Message getProductById(@RequestBody Product product) {
        if (product.getId() == null) {
            return Message.error("商品id不能为空!");
        }
        Product resultProduct = productService.getProductById(product.getId());
        if (resultProduct != null) {
            return Message.success(resultProduct);
        }
        return Message.error();
    }

    @RequestMapping("getSimilarProducts")
    public Message getSimilarProducts(@RequestBody Product product) {
        if (product.getId() == null) {
            return Message.error("商品id不能为空!");
        }
        return productService.getSimilarProducts(product);
    }

    @RequestMapping("getProductsByHigHestId")
    public Message getProductsByHigHestId(@RequestBody Category category) {
        return productService.getProductsByHigHestId(category);
    }

    @RequestMapping("downLoad")
    public void downLoad(@RequestBody Map<String, Object> params, HttpServletRequest request, HttpServletResponse response) {
        String picPath = (String) params.get("picPath");
        productService.downLoad(picPath, request, response);
    }
}
