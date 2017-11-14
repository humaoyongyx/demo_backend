package com.example.demo.interceptor;


import com.alibaba.fastjson.JSON;
import com.example.demo.config.MyConfig;
import com.example.demo.resolver.JsonRequestResolver;
import com.example.demo.utils.CommonUtils;
import com.example.demo.wrapper.JsonHttpServletRequestWrapper;
import jodd.io.StreamUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.StringWriter;
import java.util.Map;

@Component
public class JsonInterceptor extends HandlerInterceptorAdapter {

    private Logger logger = LoggerFactory.getLogger(JsonInterceptor.class);

    @Autowired
    MyConfig myConfig;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        //处理json格式
        if (request instanceof JsonHttpServletRequestWrapper) {
            JsonHttpServletRequestWrapper wrapper = (JsonHttpServletRequestWrapper) request;
            StringWriter stringWriter = new StringWriter();
            StreamUtil.copy(wrapper.getInputStream(), stringWriter);
            String jsonRaw = stringWriter.toString();
            logger.info("JsonInterceptor params:\n" + jsonRaw);
            if (!StringUtils.isEmpty(jsonRaw)) {
                try {
                    Map map = JSON.parseObject(stringWriter.toString(), Map.class);
                    request.setAttribute(JsonRequestResolver.JsonAttr, map);

                    //简单的签名验证
                   if (myConfig.isEnableSign()){
                       if (!CommonUtils.checkSign(request,map)){
                           request.getRequestDispatcher("/ex/signError").forward(request,response);
                           return false;
                       }
                   }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }


        }else {
            //处理非json格式
            if (myConfig.isEnableSign()){
                if (!CommonUtils.checkSign(request)){
                    request.getRequestDispatcher("/ex/signError").forward(request,response);
                    return false;
                }
            }
        }



        return true;
    }

}
