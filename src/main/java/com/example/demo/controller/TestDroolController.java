package com.example.demo.controller;

import com.example.demo.service.DroolsService;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by issac.hu on 2017/12/28.
 */
@RequestMapping("test")
@RestController
public class TestDroolController {
    @Autowired
    DroolsService droolsService;


    @RequestMapping("drools")
    public void test(){
        String drl1 = DroolsService.getClasspathFile("rules/test.drl");
        String drl2 = DroolsService.getClasspathFile("rules/address.drl");
        KieSession kieSession = droolsService.getKieSession("test", drl1,drl2);
        new Thread(new Runnable() {
            @Override
            public void run() {
                kieSession.fireUntilHalt();

            }
        }).start();


    }


}
