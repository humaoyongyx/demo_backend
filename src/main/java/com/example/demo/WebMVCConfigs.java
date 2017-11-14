package com.example.demo;

import com.example.demo.interceptor.JsonInterceptor;
import com.example.demo.resolver.JsonRequestResolver;
import com.example.demo.wrapper.HttpServletRequestJsonFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        HttpServletRequestJsonFilter filter = new HttpServletRequestJsonFilter();
        registrationBean.setFilter(filter);
        registrationBean.setName("jsonFilter");
        List<String> urlPatterns = new ArrayList<String>();
        urlPatterns.add("/*");
        registrationBean.setUrlPatterns(urlPatterns);
        return registrationBean;
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {


        registry.addInterceptor(jsonInterceptor)
                .excludePathPatterns("/ex/**")
                .addPathPatterns("/**");


    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(jsonRequestResolver);
    }
}
