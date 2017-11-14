package com.example.demo.service;

import com.example.demo.utils.RedisTemplateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestRedisService {

    private final Logger logger= LoggerFactory.getLogger(TestRedisService.class);

    @Autowired
    RedisTemplateUtil redisTemplateUtil;


    public void normalMethod(){
        logger.info(Thread.currentThread().toString());
        logger.info("normal Method executed!");
    }

    public void lockMethod(){
        logger.info(Thread.currentThread().toString());
        boolean lockMethod = redisTemplateUtil.getLock("lockMethod", 15);
        logger.info("getLock"+Thread.currentThread().toString()+lockMethod);
        if (lockMethod){
            logger.info("lockMethod Method executed!"+Thread.currentThread().toString());
        }

    }
}
