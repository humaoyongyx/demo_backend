package com.example.demo.controller;

import com.example.demo.bean.UserBean;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.*;

/**
 * Created by issac.hu on 2018/1/9.
 */
@Controller
@RequestMapping("ftl")
public class TestFtlController {

    @RequestMapping("test")
    public String test(Model model) {
        model.addAttribute("hello", "hello world!");
        model.addAttribute("null", null);
        model.addAttribute("empty", "  ");
        model.addAttribute("int", 123);
        model.addAttribute("double", 123.0153);
        model.addAttribute("date", new Date());
        model.addAttribute("boolean", true);
        model.addAttribute("list", getList(5));
        model.addAttribute("map", getMap());
        return "ftl/test";
    }






    @RequestMapping("test2")
    public String test2(HttpServletResponse response) throws IOException {
        return "ftl/test2";
    }

    private List<UserBean> getList(int size) {
        List<UserBean> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(new UserBean("id" + i, "name" + i));
        }


        return list;
    }

    private Map<String, Object> getMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("key1", "object1");
        map.put("key2", "object2");
        map.put("key3", "object3");
        return map;

    }

    public static void savePdf(OutputStream out, String html) {
        Document document = new Document(PageSize.A4, 50, 50, 60, 60);
        try {
            PdfWriter writer = PdfWriter.getInstance(document, out);
            document.open();
            XMLWorkerHelper.getInstance().parseXHtml(writer, document, new StringReader(html));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }


}
