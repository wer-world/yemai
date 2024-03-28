package com.kgc;

import com.kgc.util.MD5Util;
import org.junit.jupiter.api.Test;

/**
 * @Author: 魏小可
 * @Date: 2024-03-28-11:38
 */
public class MD5UtilTest {

    @Test
    public void md5Test() throws Exception {
        String easyBuy = MD5Util.encodeMd5("ad0e1bc3-e829-46fe-829e-b88993f28cf1", "EasyBuy");
        System.out.println(easyBuy);
        boolean verify = MD5Util.verify("ad0e1bc3-e829-46fe-829e-b88993f28cf1", "EasyBuy", easyBuy);
        System.out.println(verify);
    }
}
