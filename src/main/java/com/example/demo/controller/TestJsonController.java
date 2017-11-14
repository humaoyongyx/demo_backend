package com.example.demo.controller;

import com.example.demo.annotation.JsonParam;
import com.example.demo.bean.UserBean;
import com.example.demo.resp.Resp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestJsonController {

    private final Logger logger= LoggerFactory.getLogger(TestJsonController.class);
    @RequestMapping("testJson")
    public Object testJson(@JsonParam String name, @RequestBody UserBean userBean){
        logger.info(name+","+userBean);
        return Resp.SUCCESS;
    }
}
