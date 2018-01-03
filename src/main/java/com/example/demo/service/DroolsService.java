package com.example.demo.service;

import com.example.demo.droolsBean.UserBean;
import jodd.io.StreamUtil;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Results;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by issac.hu on 2017/12/28.
 */
@Service
public class DroolsService {

    private Map<String,KieSession>  kieSessionCacheMap= new ConcurrentHashMap<>();
    private Map<String,KieContainer> kieContainerCacheMap =new ConcurrentHashMap<>();
    private static final  String default_rule_package="src/main/resources/rules/";

    private static final Logger LOGGER= LoggerFactory.getLogger(DroolsService.class);

    public synchronized KieSession getKieSession(String ruleName,String rule) {
        KieServices kieServices = KieServices.Factory.get();
        KieFileSystem kfs = kieServices.newKieFileSystem();
        kfs.write(default_rule_package+ruleName+".drl", rule);
        KieBuilder kieBuilder = kieServices.newKieBuilder(kfs).buildAll();
        Results results = kieBuilder.getResults();
        if (results.hasMessages(org.kie.api.builder.Message.Level.ERROR)) {
            System.out.println(results.getMessages());
            throw new IllegalStateException("### errors ###");
        }
        KieContainer kieContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());
        KieBase kieBase = kieContainer.getKieBase();
        KieSession kieSession = kieBase.newKieSession();
      //  handleKieSession(ruleName,kieSession);
        handleKieContainer(ruleName,kieContainer);
        return kieSession;
    }

    private  void  handleKieSession(String ruleName,KieSession kieSession ){
        KieSession _kieSession = kieSessionCacheMap.get(ruleName);
        if (_kieSession!=null){
            System.out.println("not null--------------------");
            _kieSession.halt();
            _kieSession.destroy();
            kieSessionCacheMap.put(ruleName,kieSession);
        }else {
            System.out.println("null--------------------");
            kieSessionCacheMap.put(ruleName,kieSession);
        }
    }

    private  void handleKieContainer(String ruleName, KieContainer kieContainer){
        KieContainer _kieContainer = kieContainerCacheMap.get(ruleName);
        if (_kieContainer!=null){
            _kieContainer.dispose();
        }else {
            kieContainerCacheMap.put(ruleName,kieContainer);
        }
    }


    public synchronized KieSession getKieSession(String ruleName,String... rules) {
            KieServices kieServices = KieServices.Factory.get();
            KieFileSystem kfs = kieServices.newKieFileSystem();
            for(int i=0;i<rules.length;i++){
                kfs.write("src/main/resources/rules/"+ruleName+i+".drl", rules[i]);
            }
            KieBuilder kieBuilder = kieServices.newKieBuilder(kfs).buildAll();
            Results results = kieBuilder.getResults();
            if (results.hasMessages(org.kie.api.builder.Message.Level.ERROR)) {
                System.out.println(results.getMessages());
                throw new IllegalStateException("### errors ###");
            }
            KieContainer kieContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());
            KieBase kieBase = kieContainer.getKieBase();
            KieSession kieSession = kieBase.newKieSession();
            handleKieSession(ruleName,kieSession);
            handleKieContainer(ruleName,kieContainer);
            return kieSession;

    }


    //可以读取打jar包之后的类路径中的文件
    public static String getClasspathFile(String path){

        InputStream resourceAsStream = DroolsService.class.getClassLoader().getResourceAsStream(path);
        StringWriter fileStringWriter=new StringWriter();
        try {
            StreamUtil.copy(resourceAsStream,fileStringWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(fileStringWriter.toString());
        return fileStringWriter.toString();
    }

    public static void out(){
        LOGGER.info("out....");
        System.out.println("out2...");
  }


    public void test2(){
        String drl1 = DroolsService.getClasspathFile("rules/test2.drl");
        KieSession kieSession = getKieSession("test", drl1);
        UserBean userBean=new UserBean();
        userBean.setName("testName");
        userBean.setAge(23);
        kieSession.insert(userBean);
        kieSession.fireAllRules();
       // kieSession.dispose();
    }

    public static void main(String[] args) {
        System.out.println(UserBean.class.getName());
    }

}
