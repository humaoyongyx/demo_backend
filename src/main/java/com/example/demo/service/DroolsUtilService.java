package com.example.demo.service;

import com.example.demo.droolsBean.UserBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by issac.hu on 2018/1/3.
 */
@Service
public class DroolsUtilService {

    private static final Logger logger= LoggerFactory.getLogger(DroolsUtilService.class);



    private static final String default_drl_package="package rules;\n";

    public static  String buildRules(){
      StringBuffer drl=new StringBuffer();
      drl.append(default_drl_package);
      drl.append("import "+ UserBean.class.getName()+";\n");
      drl.append("rule "+"auto_"+System.currentTimeMillis()+"\n");
      drl.append("when \n");
      drl.append("$bean:"+UserBean.class.getSimpleName()+"(");
      drl.append("age>10 ");
      drl.append(");\n");
      drl.append("then \n");
      drl.append("System.out.println($bean);\n");
      drl.append("end");
        System.out.println(drl.toString());
        return drl.toString();
    }


    public static  String buildRules(Class<?> clazz,String condition){
        StringBuffer drl=new StringBuffer();
        drl.append(default_drl_package);
        drl.append("import "+ clazz.getName()+";\n");
        drl.append("rule "+"auto_"+System.currentTimeMillis()+"\n");
        drl.append("when \n");
        drl.append("$bean:"+clazz.getSimpleName()+"(");
        drl.append(condition);
        drl.append(");\n");
        drl.append("then \n");
        drl.append("System.out.println($bean);\n");
        drl.append("end");
        System.out.println("-----------------------------");
        System.out.println(drl.toString());
        System.out.println("-----------------------------");
        return drl.toString();
    }


    public static  String buildRules(Class<?>[] clazzes,String[] condition){
        StringBuffer drl=new StringBuffer();
        drl.append(default_drl_package);
        for (Class clazz:clazzes){
            drl.append("import "+ clazz.getName()+";\n");
        }

        drl.append("rule "+"auto_"+System.currentTimeMillis()+"\n");
        drl.append("when \n");
        int i=0;
        for (Class clazz:clazzes){
            drl.append("\t$"+clazz.getSimpleName()+":"+clazz.getSimpleName()+"(");
            drl.append(condition[i++]);
            drl.append(");\n");
        }

        drl.append("then \n");
        for (Class clazz:clazzes){
            drl.append("\tSystem.out.println("+"$"+clazz.getSimpleName()+");\n");
        }

        drl.append("end");
        System.out.println("-----------------------------");
        System.out.println(drl.toString());
        System.out.println("-----------------------------");
        return drl.toString();
    }

    public static void main(String[] args) {
        System.out.println(buildRules());
    }
}
