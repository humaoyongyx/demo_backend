package com.example.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="myConfig")
public class MyConfig {

    private boolean jsonConvert=true;
    private boolean enableSign=true;

    public boolean isJsonConvert() {
        return jsonConvert;
    }

    public void setJsonConvert(boolean jsonConvert) {
        this.jsonConvert = jsonConvert;
    }

    public boolean isEnableSign() {
        return enableSign;
    }

    public void setEnableSign(boolean enableSign) {
        this.enableSign = enableSign;
    }
}
