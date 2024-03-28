package com.kgc.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * md摘要加密
 *
 * @Author: 魏小可
 * @Date: 2024-03-28-11:35
 */
public class MD5Util {
    /**
     * @param text 明文
     * @param key  密钥
     * @return 密文
     */
    public static String encodeMd5(String text, String key) {
        return DigestUtils.md5Hex(text + key);
    }

    /**
     * MD5验证方法
     *
     * @param text 明文
     * @param key  密钥
     * @param md5  密文
     */
    public static boolean verify(String text, String key, String md5) throws Exception {
        String md5str = encodeMd5(text, key);
        if (md5str.equalsIgnoreCase(md5)) {
            return true;
        }
        return false;
    }
}
