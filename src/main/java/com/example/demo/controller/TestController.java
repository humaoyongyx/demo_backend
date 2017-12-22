package com.example.demo.controller;

import com.example.demo.bean.Address;
import com.example.demo.bean.AddressCheckResult;
import com.example.demo.bean.UserBean;
import com.example.demo.utils.RedisTemplateUtil;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.util.calendar.BaseCalendar;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Controller
@ResponseBody
@RequestMapping("test")
public class TestController {

    private final Logger logger = LoggerFactory.getLogger(TestController.class);


    @Autowired
    RedisTemplateUtil redisTemplateUtil;

    @RequestMapping("/")
    public String test(String name, UserBean userBean) {
        logger.info(name + "," + userBean);
        return "drool success!";


    }

    @RequestMapping("writeRedis")
    public void testWriteRedis() {
        redisTemplateUtil.set("demo_backend", System.currentTimeMillis() + "");
    }
    private  ExecutorService pool= Executors.newFixedThreadPool(5);

    @RequestMapping("/testPool")
    public String testPool(String name, UserBean userBean) {
        for (int i=0;i<30;i++){
            System.out.println("i=="+i);
            int finalI = i;
            Runnable r=  new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println(Thread.currentThread().toString());
                        System.out.println("before/...:"+finalI);
                       if (redisTemplateUtil.getLock("xxxx-test",3000)){
                           Thread.sleep(5000);
                           System.out.println("finalI:"+finalI);
                           System.out.println(Thread.currentThread().toString());

                           redisTemplateUtil.del("xxxx-test");
                       }

                    }catch (Exception e){
                        redisTemplateUtil.del("xxxx-test");
                        e.printStackTrace();
                    }

                }
            };

           Runnable r2= new Runnable() {
                @Override
                public void run() {
                    System.out.println("xx"+Thread.currentThread());
                }
            };

            pool.execute(r);
            pool.execute(r2);

        }

        System.out.println("end...");

        return "pool success!";


    }


    @RequestMapping("/testPool2")
    public String testPool2(String name, UserBean userBean) {

        if (redisTemplateUtil.exists("testpool2")){
            return "xxx";
        }else {
            redisTemplateUtil.setex("testpool2",30,System.currentTimeMillis()+"");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return "xxx";
    }


    public static void main(String[] args) {
        System.out.println(new Date().toString());
    }



}
