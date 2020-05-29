package com.edu.hus.androidsdk.utils;

import com.google.gson.Gson;

import java.lang.reflect.Type;

public class JsonUtils {
    private final String TAG = "JsonUtils";

    private Gson gson;

    public JsonUtils()
    {
        gson = new Gson();
    }

    public <T> T fromJson(String json, Class<T> classOfT)
    {
       return gson.fromJson(json,classOfT);
    }

    public <T> T fromJson(String json, Type typeOfT) {
        return gson.fromJson(json,typeOfT);
    }


    public String toJson(Object object)
    {
        return gson.toJson(object);
    }

}
