package com.example.demo.wrapper;

import jodd.io.StreamUtil;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class JsonHttpServletRequestWrapper extends HttpServletRequestWrapper {
      
    private final byte[] body; // 报文  
  
    public JsonHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);  
        body = StreamUtil.readBytes(request.getInputStream());
    }  
      
    @Override  
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }  
      
    @Override  
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream bais = new ByteArrayInputStream(body);
        return new ServletInputStream() {

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() throws IOException {  
                return bais.read();  
            }

        };  
    }  
  
}  