package com.example.demo.controller;

import com.example.demo.resp.Resp;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("ex")
@ResponseBody
public class ExceptionController {

    @RequestMapping
    public Object ex() {
        return Resp.FAIL;
    }

    @RequestMapping("signError")
    public Object signError() {
        return Resp.fail("签名错误");
    }
}
