package com.example.demo.controller;

import com.example.demo.bean.UserBean;
import com.example.demo.utils.RedisTemplateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping("test")
public class TestController {

    private final Logger logger= LoggerFactory.getLogger(TestController.class);


    @Autowired
    RedisTemplateUtil redisTemplateUtil;

    @RequestMapping
    public String test( String  name,@RequestBody UserBean userBean){
       // logger.info("test success!"+name);
        logger.info(name+","+userBean);
        return "test success!";
    }
    @RequestMapping("writeRedis")
    public void testWriteRedis(){
        redisTemplateUtil.set("demo_backend",System.currentTimeMillis()+"");
    }

}
