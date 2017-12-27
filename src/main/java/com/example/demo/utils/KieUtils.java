package com.example.demo.utils;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import java.io.UnsupportedEncodingException;

/**
 * Created by issac.hu on 2017/12/25.
 */
public class KieUtils {

    public static void reload() throws UnsupportedEncodingException {
        KieServices kieServices = KieServices.Factory.get();
        KieFileSystem kfs = kieServices.newKieFileSystem();
        kfs.write("src/main/resources/rules/temp.drl", loadRules());
        KieBuilder kieBuilder = kieServices.newKieBuilder(kfs).buildAll();
        Results results = kieBuilder.getResults();
        if (results.hasMessages(Message.Level.ERROR)) {
            System.out.println(results.getMessages());
            throw new IllegalStateException("### errors ###");
        }
        System.out.println("新规则重载成功");
    }

    private static String loadRules() {
        // 从数据库加载的规则
        return "package rules;\n" +
                "\n" +
                "rule \"this is for temp\"\n" +
                "  no-loop false\n" +
                "when\n" +
                "\n" +
                "then\n" +
                "  System.out.println(\"this is for temp!\");\n" +
                "\n" +
                "end\n";

    }

}
