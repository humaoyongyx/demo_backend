package com.example.demo.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.sql.SQLException;

@Configuration
@EnableTransactionManagement
@MapperScan("com.example.demo.mapper")
public class DataSourceApplicationConfigure {

    @Autowired
    DataSourceProperties dataSourceProperties;

    @Bean(name = "dataSource")
    public DruidDataSource dataSource() throws SQLException {

        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(dataSourceProperties.getUrl());
        datasource.setDriverClassName(dataSourceProperties.getDriverClassName());
        datasource.setUsername(dataSourceProperties.getUsername());
        datasource.setPassword(dataSourceProperties.getPassword());
        datasource.setName("druid-default");
        datasource.setMaxActive(dataSourceProperties.getMaxPoolSize());
        datasource.setInitialSize(dataSourceProperties.getInitialPoolSize());
        return datasource;
    }



}