package com.kgc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 分页对象
 *
 * @Author: 魏小可
 * @Date: 2024-03-19-9:56
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Page {
    private Integer currentPage;
    private Integer pageSize;
    private Long totalCount;
}
