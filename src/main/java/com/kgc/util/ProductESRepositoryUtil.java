package com.kgc.util;

import com.kgc.entity.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 商品本地es操作类
 *
 * @Author: 魏小可
 * @Date: 2024-03-19-10:01
 */
public interface ProductESRepositoryUtil extends ElasticsearchRepository<Product, String> {
}
