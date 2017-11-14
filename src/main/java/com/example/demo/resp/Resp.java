package com.example.demo.resp;


/**
 * Created by admin on 2017/11/9.
 */
public class Resp {

    private int code;
    private String msg;

    public static final Resp FAIL=new Resp(0,"FAIL");
    public static final Resp SUCCESS=new Resp(1,"SUCCESS");

    public Resp() {
    }

    public Resp(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
