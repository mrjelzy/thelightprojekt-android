package com.thelightprojekt.view;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.util.LinkedHashMap;
import java.util.Map;

public class SendMessagePing {

    private Map<String, Object> messageMap;

    public SendMessagePing(){
        messageMap = new LinkedHashMap<>();
        messageMap.put("msg","pong");
    }

    public String toJson() {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(this.messageMap);
    }
}
