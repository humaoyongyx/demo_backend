package com.example.demo.mapper;

import com.example.demo.model.TestEntity;

import java.util.List;

/**
 * Created by issac.hu on 2018/1/10.
 */
public interface TestDao {
    public List<TestEntity> getAll();
}
