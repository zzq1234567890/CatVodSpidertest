package com.github.catvod.utils;

import com.github.catvod.crawler.SpiderDebug;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;

public class Json {

    public static JsonElement parse(String json) {
        try {
            return JsonParser.parseString(json);
        } catch (Throwable e) {
            return new JsonParser().parse(json);
        }
    }

    public static JsonObject safeObject(String json) {
        try {
            JsonObject obj = parse(json).getAsJsonObject();
            return obj == null ? new JsonObject() : obj;
        } catch (Throwable e) {
            return new JsonObject();
        }
    }

    public static String toJson(Object obj) {
        return new Gson().toJson(obj);
    }

    public static <T> T parseSafe(String json, Type t) {
        try {
            return new Gson().fromJson(json, t);
        } catch (JsonSyntaxException e) {
            SpiderDebug.log("json parse error: " + e.getMessage() + "\n" + " " + json);
            return null;
        }
    }

}
