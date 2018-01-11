package com.example.demo;

import com.example.demo.resp.Resp;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GlobalErrorController implements ErrorController{

    @RequestMapping("/error")
    public Resp error(){
        return Resp.fail("请求地址不存在");
    }

    @Override
    public String getErrorPath() {
        return "error";
    }
}
