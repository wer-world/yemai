package com.kgc.controller;

import com.kgc.util.ReplayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 重放攻击防御控制类
 *
 * @Author: 魏小可
 * @Date: 2024-03-18-18:14
 */
@RestController
@RequestMapping("replay")
public class ReplayController {

    @Autowired
    private ReplayUtil replayUtil;

    @GetMapping("getRandom")
    public String getRandom() {
        return replayUtil.getRandom();
    }
}
