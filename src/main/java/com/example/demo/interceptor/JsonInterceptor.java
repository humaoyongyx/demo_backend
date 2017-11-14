package com.example.demo.interceptor;


import com.alibaba.fastjson.JSON;
import com.example.demo.wrapper.JsonHttpServletRequestWrapper;
import jodd.io.StreamUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class JsonInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        System.out.println("dointerceptor");

        if (request instanceof JsonHttpServletRequestWrapper) {

            JsonHttpServletRequestWrapper wrapper = (JsonHttpServletRequestWrapper) request;
            System.out.println("JsonHttpServletRequestWrapper");
            StreamUtil.copy(wrapper.getInputStream(), System.out);
            StringWriter stringWriter = new StringWriter();
            StreamUtil.copy(wrapper.getInputStream(), stringWriter);
            System.out.print("\n");
            String jsonRaw = stringWriter.toString();
            System.out.println(stringWriter.toString());
            if (!StringUtils.isEmpty(jsonRaw)) {
                try {
                    Map map = JSON.parseObject(stringWriter.toString(), Map.class);
                    request.setAttribute("jsonObject", map);
                    System.out.println(map);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }


        }


        return true;
    }

}
