package com.thelightprojekt.view;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SendConnectMessage {
    @SerializedName("msg")
    private String msg;

    @SerializedName("version")
    private String version;

    @SerializedName("support")
    private List<String> support;

    private Map<String, Object> messageMap;

    public SendConnectMessage(String msg, String version, List<String> support) {
        messageMap = new LinkedHashMap<>();
        messageMap.put("msg", msg);
        messageMap.put("version", version);
        messageMap.put("support", support);
    }

    public String toJson() {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(this.messageMap);
    }
}
