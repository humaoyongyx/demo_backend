package com.example.demo.dao;

import com.example.demo.service.TestDaoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DaoTest {

    @Autowired
    TestDaoService testDaoService;

    @Test
    public void test(){
        testDaoService.test();
    }

}
