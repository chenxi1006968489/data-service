package com.cx.springboot.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author chenxi
 * @Package:
 * @Description:
 * @create 2021·11
 */
@Component
public class JSONUtil {
    public static String getJSONString(int code, String msg, Map<String, Object> map) {
        JSONObject json = new JSONObject();
        json.put("code", code);
        json.put("msg", msg);
        if (map != null) {
            for (String key : map.keySet()) {
                json.put(key, map.get(key));
            }
        }
        return json.toJSONString();
    }

    public static String getJSONString(int code, String msg) {
        return getJSONString(code, msg, null);
    }

    public static String getJSONString(int code) {
        return getJSONString(code, "", null);
    }
    public static JSONObject stringToJson(String content){
        JSONObject result = JSON.parseObject(content);
        return result;
    }

//    public static void main(String[] args) {
//        String test = "{\"depth\":\"18\",\"longitudes\":\"83.43\",\"epicenter\":\"新疆阿克苏地区库车市\",\"eartqua_id\":\"5615838\",\"id\":\"522\",\"num_mag\":\"4.1\",\"latitudes\":\"41.16\",\"orig_time\":\"2021-11-29 14:46:01\"}";
//
//        JSONObject result = stringToJson(test);
//
//        System.out.println(result.get("epicenter"));
//
//    }
}