package com.example.demo;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.example.demo.config.MyConfig;
import com.example.demo.interceptor.JsonInterceptor;
import com.example.demo.resolver.JsonRequestResolver;
import com.example.demo.wrapper.HttpServletRequestJsonFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.List;


@Configuration
public class WebMVCConfigs extends WebMvcConfigurerAdapter {


    @Autowired
    JsonInterceptor jsonInterceptor;

    @Autowired
    JsonRequestResolver jsonRequestResolver;

    @Autowired
    MyConfig myConfig;


    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters() {
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat,SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteMapNullValue);
        fastConverter.setFastJsonConfig(fastJsonConfig);
        HttpMessageConverter<?> converter = fastConverter;
        return new HttpMessageConverters(converter);
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {

        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        HttpServletRequestJsonFilter filter = new HttpServletRequestJsonFilter();

        registrationBean.setFilter(filter);
        registrationBean.setName("jsonFilter");
        List<String> urlPatterns = new ArrayList<String>();
        if (myConfig.isJsonConvert()) {
            urlPatterns.add("/*");
        } else {
            urlPatterns.add("/_x_");
        }
        registrationBean.setUrlPatterns(urlPatterns);

        return registrationBean;


    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        if (myConfig.isJsonConvert()) {
            registry.addInterceptor(jsonInterceptor)
                    .excludePathPatterns("/ex/**")
                    .addPathPatterns("/**");

        }


    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        if (myConfig.isJsonConvert()) {
            argumentResolvers.add(jsonRequestResolver);
        }
    }
}
