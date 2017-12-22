package com.example.demo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by issac.hu on 2017/12/21.
 */
public class MapParams {

    private  Map<String,Object> _params=new HashMap<>();

    public static MapParams get(){
        return new MapParams();
    }

    public  Map<String,Object> toMap(){
        return _params;
    }

    public MapParams add(String key, Object value){
        _params.put(key,value);
        return this;
    }

}
