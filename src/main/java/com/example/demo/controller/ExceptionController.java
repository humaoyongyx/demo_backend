package com.example.demo.controller;

import com.example.demo.resp.Resp;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("ex")
public class ExceptionController {

    @RequestMapping
    @ResponseBody
    public Object ex(){
      return Resp.FAIL;
    }
}
