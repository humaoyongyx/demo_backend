package com.example.demo.controller;

import com.example.demo.resp.Resp;
import com.example.demo.service.TestRedisService;
import com.example.demo.utils.RedisTemplateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping("redis")
public class TestRedisController {

    private final Logger logger= LoggerFactory.getLogger(TestRedisController.class);

    @Autowired
    RedisTemplateUtil redisTemplateUtil;

    @Autowired
    TestRedisService testRedisService;

    @RequestMapping("lock1")
    public Object getLock1(){

        boolean demo_backend_lock = redisTemplateUtil.getLock("demo_backend_lock", 10);
        if (demo_backend_lock){
            logger.info("getlock success");
            return Resp.SUCCESS;
        }else {
            return Resp.FAIL;
        }
    }

    @RequestMapping("lock2")
    public Object getLock2(){

        boolean demo_backend_lock = redisTemplateUtil.getLock("demo_backend_lock", 10);
        if (demo_backend_lock){
            logger.info("getlock success");
            return Resp.SUCCESS;
        }else {
            return Resp.FAIL;
        }
    }


    @RequestMapping("normal")
    public void testNormalMethod(){
        tenTimesNormal();
    }

    @RequestMapping("lock")
    public void testLockMethod(){
        tenTimesLock();
    }
    private void tenTimesNormal(){
        for (int i=0;i<10;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                  testRedisService.normalMethod();
                }
            }).start();
        }
    }

    private void tenTimesLock(){
        for (int i=0;i<10;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    testRedisService.lockMethod();
                }
            }).start();
        }
    }
}
