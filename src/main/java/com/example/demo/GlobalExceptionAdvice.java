package com.example.demo;


import com.example.demo.resp.Resp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



@ControllerAdvice
@ResponseBody
public class GlobalExceptionAdvice {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionAdvice.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Resp handleException(Exception exception) throws Exception {
        exception.printStackTrace();
        logger.error("Global_Exception",exception);
        return Resp.fail("程序异常");
    }



    @ExceptionHandler(IllegalStateException.class)
    @ResponseBody
    public Resp handleIllegalStateException(HttpServletRequest request,
                                                   IllegalStateException ex, HttpServletResponse response) {
        logger.error("Global_IllegalStateException",ex);
        return Resp.fail("参数异常");

    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public Resp handleIllegalArgumentException(HttpServletRequest request,
                                                      IllegalArgumentException ex, HttpServletResponse response) {
        logger.error("Global_IllegalArgumentException",ex);
        return Resp.fail("参数异常");

    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseBody
    public Resp handleMethodArgumentTypeMismatchException(HttpServletRequest request,
                                                                 MethodArgumentTypeMismatchException ex, HttpServletResponse response) {
        logger.error("Global_MethodArgumentTypeMismatchException",ex);
        return  Resp.fail("参数异常");

    }

    @ExceptionHandler(BindException.class)
    @ResponseBody
    public Resp handleBindException(HttpServletRequest request, BindException ex, HttpServletResponse response) {
        logger.error("Global_BindException",ex);
        return Resp.fail("参数绑定异常");

    }


    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseBody
    public Resp handleNoHandlerFoundException(NoHandlerFoundException e, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        logger.error("Global_NoHandlerFoundException",e);
        return Resp.fail("请求地址不存在");
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public Resp handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        logger.error("Global_HttpRequestMethodNotSupportedException",e);
        return Resp.fail("请求方法不支持");
    }

}
