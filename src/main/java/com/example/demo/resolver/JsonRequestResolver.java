package com.example.demo.resolver;

import com.example.demo.annotation.JsonParam;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@Component
public class JsonRequestResolver implements HandlerMethodArgumentResolver {

    public static final String JsonAttr = JsonRequestResolver.class.getName() + "_jsonData";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        if (!parameter.hasParameterAnnotation(JsonParam.class)) {
            return false;
        }

        return true;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        Map jsonObject = (Map) servletRequest.getAttribute(JsonAttr);
        if (jsonObject != null) {
            return jsonObject.get(parameter.getParameterName());
        }
        return null;
    }

}
