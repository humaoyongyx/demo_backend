package com.example.demo.service;

import com.alibaba.fastjson.JSON;
import com.example.demo.mapper.TestDao;
import com.example.demo.model.TestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by issac.hu on 2018/1/10.
 */
@Service
public class TestDaoService {

    @Autowired
    TestDao testDao;

    public void test(){
        List<TestEntity> all = testDao.getAll();
        System.out.println(JSON.toJSON(all));
    }
}
