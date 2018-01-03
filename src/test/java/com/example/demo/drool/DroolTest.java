package com.example.demo.drool;

import com.example.demo.droolsBean.TestBean;
import com.example.demo.droolsBean.UserBean;
import com.example.demo.service.DroolsService;
import com.example.demo.service.DroolsUtilService;
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
        droolsService.test2();
    }

    @Test
    public void test3(){

        KieSession kieSession = droolsService.getKieSession("test", DroolsUtilService.buildRules());
        UserBean userBean=new UserBean();
        userBean.setAge(23);
        userBean.setName("testName");
        kieSession.insert(userBean);
        kieSession.fireAllRules();
        kieSession.dispose();
    }

    @Test
    public void test4(){

        KieSession kieSession = droolsService.getKieSession("test", DroolsUtilService.buildRules(UserBean.class,"age >13, name==\"testName\""));
        UserBean userBean=new UserBean();
        userBean.setAge(23);
        userBean.setName("testName");
        kieSession.insert(userBean);
        kieSession.fireAllRules();
        kieSession.dispose();
    }

    @Test
    public void test5(){

        KieSession kieSession = droolsService.getKieSession("test", DroolsUtilService.buildRules(new Class[]{UserBean.class, TestBean.class},new String[]{"age >13, name==\"testName\"",""}));
        UserBean userBean=new UserBean();
        userBean.setAge(23);
        userBean.setName("testName");
        kieSession.insert(userBean);
        kieSession.insert(new TestBean());
        kieSession.fireAllRules();
        kieSession.dispose();
    }

}
