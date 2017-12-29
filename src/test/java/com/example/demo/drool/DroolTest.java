package com.example.demo.drool;

import com.example.demo.service.DroolsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DroolTest {

    @Autowired
    DroolsService droolsService;



    @Test
    public void test(){
        String classPathFile = DroolsService.getClasspathFile("rules/test.drl");
        KieSession kieSession = droolsService.getKieSession("test", classPathFile);
        kieSession.fireUntilHalt();
        kieSession.dispose();
    }

    @Test
    public void test2(){
        String drl1 = DroolsService.getClasspathFile("rules/test.drl");
        String drl2 = DroolsService.getClasspathFile("rules/address.drl");
        KieSession kieSession = droolsService.getKieSession("test", drl1,drl2);
        kieSession.fireUntilHalt();
        kieSession.dispose();
    }

}
