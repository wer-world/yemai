package com.kgc.util;

import sun.misc.BASE64Encoder;

/**
 * @Author: 魏小可
 * @Date: 2024-03-22-16:22
 */
public class Base64Util {

    public static String imgArryToBase64Str(byte[] bytes){
        BASE64Encoder base64Encoder=new BASE64Encoder();
        return base64Encoder.encode(bytes);
    }
}
