package com.example.demo;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 部署环境的test，不要修改或者删除此test
 *
 */
public class DeployEnvTest {

    private Logger logger= LoggerFactory.getLogger(DeployEnvTest.class);

    @Test
    public  void test(){

        System.out.println("DeployEnvTest passed...");
        logger.info("DeployEnvTest passed...");

    }
}
