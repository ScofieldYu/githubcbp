package com.blchina.common.util.Json;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsonUtil {

    private JsonUtil(){}

    /**
     * 对象转换成json字符串
     * @param obj
     * @return
     */
    public static String toJson(Object obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }

    /**
     * json字符串转成对象
     * @param str
     * @return
     */
    public static Map<String, Object> fromJsonToMap(String str) {
        Gson gson = new Gson();
        return gson.fromJson(str, new TypeToken<Map<String, Object>>(){}.getType());
    }

    /**
     * json字符串转成map对象
     * @param str
     * @param type
     * @return
     */
    public static <T> T fromJson(String str, Type type) {
        Gson gson = new Gson();
        return gson.fromJson(str, type);
    }

    /**
     * json字符串转成对象
     * @param str
     * @param type
     * @return
     */
    public static <T> T fromJson(String str, Class<T> type) {
        Gson gson = new Gson();
        return gson.fromJson(str, type);
    }

    public static <T> List<T> fromJsonToList(String str, Class<T> clazz){
        List<T> list = new ArrayList<T>();
        JsonArray jsonArray = new JsonParser().parse(str).getAsJsonArray();
        for(final JsonElement elem:jsonArray){
            list.add(new Gson().fromJson(elem,clazz));
        }
        return list;
    }
}
