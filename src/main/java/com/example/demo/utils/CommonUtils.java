package com.example.demo.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


public class CommonUtils {

    private static final Logger logger = LoggerFactory.getLogger(CommonUtils.class);

    private static final String secretKey = "secretKey";

    public static String md5(String code) {
        return DigestUtils.md5Hex(code);
    }

    public static String sign(Map<String, Object> map) {

        if (map == null) {
            return null;
        }
        TreeMap<String, Object> params = new TreeMap<>(map);
        List<String> keys = new ArrayList<>();
        List<String> values = new ArrayList<>();
        for (Map.Entry<String, Object> param : params.entrySet()) {
            if (StringUtils.isNotEmpty(param.getKey()) && StringUtils.isNotEmpty(param.getValue().toString())) {
                keys.add(param.getKey());
                values.add(param.getValue().toString());
            }
        }
        if (!keys.isEmpty() && !values.isEmpty()) {
            StringBuffer keyBuff = new StringBuffer();
            StringBuffer valueBuff = new StringBuffer();
            for (String key : keys) {
                keyBuff.append(key);
            }
            for (String value : values) {
                valueBuff.append(value);
            }
            String md5one = md5(keyBuff.toString()) + md5(valueBuff.toString());
            return md5(md5one);
        }

        return null;
    }

    public static Map<String, Object> requestToMap(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String key = parameterNames.nextElement();
            String value = request.getParameter(key);
            map.put(key, value);
        }
        return map;
    }

    public static boolean checkSign(HttpServletRequest request) {
        return checkSign(request, null, null);
    }

    public static boolean checkSign(HttpServletRequest request, Map map) {
        return checkSign(request, map, null);
    }

    //取request除header里面的参数值，按照非空key和value的字母自然顺序升序排列，加入header里面的timestamp和sign进行md5加密
    //将key取出md5加密加上将value取出md5加密，然后将相加的结果md5加密
    public static boolean checkSign(HttpServletRequest request, Map map, String secretKey) {
        String timestampString = request.getHeader("timestamp");
        String sign = request.getHeader("sign");
        logger.info("header timestamp:" + timestampString);
        if (StringUtils.isEmpty(timestampString) || StringUtils.isEmpty(sign)) {
            return false;
        }

        try {
            long timestamp = Long.parseLong(timestampString);
            long currentTime = System.currentTimeMillis();
            if (currentTime - timestamp > 1000 * 60 * 5) {
                return false;
            }
            if (map == null) {
                map = requestToMap(request);
            }
            if (StringUtils.isEmpty(secretKey)) {
                secretKey = CommonUtils.secretKey;
            }
            map.put("secret", secretKey);
            map.put("timestamp", timestamp);
            if (sign.equals(sign(map))) {
                return true;
            }
        } catch (Exception e) {

        }
        return false;
    }

    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", 1);
        map.put("name", "test1");
        map.put("secret", secretKey);
        long l = System.currentTimeMillis();
        System.out.println(l);
        map.put("timestamp", l);
        System.out.println(sign(map));

    }
}
