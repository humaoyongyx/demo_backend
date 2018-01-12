package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by issac.hu on 2018/1/12.
 */
@Controller
@RequestMapping("mvc")
public class TestMVCController {

    @RequestMapping("test1")
    public String test(HttpServletRequest request,RedirectAttributes redirectAttributes){
        request.setAttribute("name","testMVC");
        //重定向所带的参数
        redirectAttributes.addAttribute("id",1212);
        //重定向到controller，地址改变，request属性参数丢失
        return "redirect:/test/testCN?name=xxx";
    }

    @RequestMapping("test2")
    public String test2(HttpServletRequest request){
        request.setAttribute("name","testMVC");
        //转发到controller，地址不变，request属性参数保留
        return "forward:/test/testCN";
    }


}
