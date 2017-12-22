package com.example.demo.json;

import com.example.demo.BaseAbstractTest;
import org.junit.Test;

/**
 * Created by issac.hu on 2017/12/21.
 */
public class JsonTest extends BaseAbstractTest{

    @Test
    public void test() {


        post("/testJson", add("name", "xxx").add("id", "123").toMap(),true);

    }


}
