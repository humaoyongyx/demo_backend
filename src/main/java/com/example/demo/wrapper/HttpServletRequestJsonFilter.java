package com.example.demo.wrapper;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class HttpServletRequestJsonFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(HttpServletRequestJsonFilter.class);

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        ServletRequest jsonRequestWrapper = null;
        String contentType = request.getContentType();
        logger.info("jsonFilter contentType:" + contentType);
        if (StringUtils.isNotEmpty(contentType) && "application/json".equals(contentType)) {
            if (request instanceof HttpServletRequest) {
                jsonRequestWrapper = new JsonHttpServletRequestWrapper((HttpServletRequest) request);
            }
        }
        logger.info("jsonFilter jsonRequestWrapper:" + jsonRequestWrapper);
        if (jsonRequestWrapper == null) {
            chain.doFilter(request, response);
        } else {
            chain.doFilter(jsonRequestWrapper, response);
        }
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {

    }

}  