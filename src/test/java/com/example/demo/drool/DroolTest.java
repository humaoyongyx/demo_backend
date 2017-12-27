package com.example.demo.drool;

import com.example.demo.bean.Address;
import com.example.demo.droolsBean.TestBean;
import com.example.demo.utils.KieUtils;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import java.io.UnsupportedEncodingException;


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
        TestBean testBean=new TestBean();
        testBean.setId(4);
        kSession.insert(address);
        kSession.insert(testBean);
        int count = kSession.fireAllRules();
        System.out.println("命中了" + count + "条规则！");
      /*  try {
            KieUtils.reload();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        KieContainer kieContainer1 = ks.newKieContainer(ks.getRepository().getDefaultReleaseId());
        KieSession kieSession = kieContainer1.newKieSession();
        kieSession.fireAllRules();*/

    }

    public static void main(String[] args) throws InterruptedException {
        // 构建KieServices
        KieServices ks = KieServices.Factory.get();
        KieContainer kieContainer = ks.getKieClasspathContainer();
        // 获取kmodule.xml中配置中名称为ksession-rule的session，默认为有状态的。
        KieSession kSession = kieContainer.newKieSession("all-rules");

        Address address=new Address();
        address.setPostcode("99999");
        TestBean testBean=new TestBean();
        testBean.setId(4);
        kSession.insert(address);
        kSession.insert(testBean);
        new Thread(new Runnable() {
            @Override
            public void run() {
                kSession.fireUntilHalt();
            }
        }).start();

        //System.out.println("命中了" + count + "条规则！");
       try {
            KieUtils.reload();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        KieContainer kieContainer1 = ks.newKieContainer(ks.getRepository().getDefaultReleaseId());
        KieSession kieSession = kieContainer1.newKieSession();
        kieSession.fireAllRules();

       // Thread.sleep(1000000);

    }
}
