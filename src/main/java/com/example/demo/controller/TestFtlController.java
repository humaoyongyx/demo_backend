package com.example.demo.controller;

import com.example.demo.bean.UserBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

/**
 * Created by issac.hu on 2018/1/9.
 */
@Controller
@RequestMapping("ftl")
public class TestFtlController {

    @RequestMapping("test")
    public String test(Model model){
        model.addAttribute("hello","hello world!");
        model.addAttribute("null",null);
        model.addAttribute("empty","  ");
        model.addAttribute("int",123);
        model.addAttribute("double",123.0153);
        model.addAttribute("date",new Date());
        model.addAttribute("boolean",true);
        model.addAttribute("list",getList(5));
        model.addAttribute("map",getMap());
       return "ftl/test";
    }

    private List<UserBean> getList(int size){
        List<UserBean> list=new ArrayList<>();
        for (int i=0;i<size;i++){
            list.add(new UserBean("id"+i,"name"+i));
        }


        return list;
    }

    private Map<String,Object> getMap(){
        Map<String,Object> map=new HashMap<>();
        map.put("key1","object1");
        map.put("key2","object2");
        map.put("key3","object3");
        return map;

    }

}
