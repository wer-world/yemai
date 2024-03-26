package com.kgc.entity;

import lombok.*;

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
public class Pages {
    private Integer currentPage = 1;
    private Integer pageSize = 5;
    private Long totalCount;
    private Integer totalPage;
}
