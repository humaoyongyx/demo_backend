package com.example.demo.drool;

import com.example.demo.bean.Address;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;


public class DroolTest {

    @Test
    public void test(){
        // 构建KieServices
        KieServices ks = KieServices.Factory.get();
        KieContainer kieContainer = ks.getKieClasspathContainer();
        // 获取kmodule.xml中配置中名称为ksession-rule的session，默认为有状态的。
        KieSession kSession = kieContainer.newKieSession("all-rules");

        Address address=new Address();
        address.setPostcode("99999");

        kSession.insert(address);
        int count = kSession.fireAllRules();
        System.out.println("命中了" + count + "条规则！");


    }
}
