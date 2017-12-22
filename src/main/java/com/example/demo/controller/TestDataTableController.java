package com.example.demo.controller;

import com.example.demo.bean.UserBean;
import com.example.demo.res.DataTableRes;
import com.example.demo.resp.DataTableResp;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("dataTable")
public class TestDataTableController {

    @RequestMapping("get")
    public Object get(DataTableRes dataTableRes){
        DataTableResp dataTableResp=new DataTableResp();
        dataTableResp.setDraw(dataTableRes.getDraw());

        int mockNum=24;
        dataTableResp.setData(getMockData(mockNum,dataTableRes.getStart(),dataTableRes.getLength()));
        dataTableResp.setRecordsTotal(mockNum);
        dataTableResp.setRecordsFiltered(mockNum);
        return  dataTableResp;
    }

    private List<UserBean> getMockData(){

        List<UserBean> userBeans=new ArrayList<>();
        UserBean userBean=new UserBean("id01-"+Math.random(),"name1");
        userBeans.add(userBean);
        UserBean userBean2=new UserBean("id02-"+Math.random(),"name2");
        userBeans.add(userBean2);
        return userBeans;

    }

    private List<UserBean> getMockData(int num,int start,int length){

        List<UserBean> userBeans=new ArrayList<>();

        if (num<=0){
            num=1;
        }

        UserBean userBean=null;

        for(int i=0;i<num;i++){
             userBean=new UserBean("id"+i+"->"+Math.random(),"name"+i);
            userBeans.add(userBean);
        }
        int end=start+length;
        if (userBeans.size()<end){
            end=userBeans.size();
        }

        return userBeans.subList(start,end);

    }
}
