package com.example.demo;

import com.example.demo.utils.CommonUtils;
import com.example.demo.utils.HttpClientHelperWithoutSSL;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import java.util.Map;

/**
 * Created by admin on 2017/9/12.
 */
public class BaseAbstractTest {

    public static String url = null;

    private static MapParams _params=MapParams.get();



    static {
        if (url == null) {
            url = "http://localhost:8080";
        }

    }




    public static MapParams add(String key, Object value, Boolean... clear){
        if (clear.length==0||clear[0]){
            _params=MapParams.get();
        }
        _params.add(key,value);
        return _params;
    }


    public static String post(String apiUrl, Map<String, Object> params) {
        String uriContentUsingPost = HttpClientHelperWithoutSSL.getUriContentUsingJsonPost(url + apiUrl, params, "UTF-8");
        System.out.println(uriContentUsingPost);
        return uriContentUsingPost;
    }

    public static String post(String apiUrl, Map<String, Object> params,boolean sign) {
        String uriContentUsingPost=null;
        if (sign){
          String timestamp=  System.currentTimeMillis()+"";
            params.put("secret", "secretKey");
            params.put("timestamp", timestamp);
            String signParam = CommonUtils.sign(params);
            Header[] headers={new BasicHeader("sign",signParam),new BasicHeader("timestamp",timestamp)};
            uriContentUsingPost = HttpClientHelperWithoutSSL.getUriContentUsingJsonPost(url + apiUrl, params, headers,"UTF-8");
        }else {
            uriContentUsingPost = HttpClientHelperWithoutSSL.getUriContentUsingJsonPost(url + apiUrl, params,"UTF-8");
        }

        System.out.println(uriContentUsingPost);
        return uriContentUsingPost;
    }


}
