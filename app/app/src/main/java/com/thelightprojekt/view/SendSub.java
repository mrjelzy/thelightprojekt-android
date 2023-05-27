package com.thelightprojekt.view;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SendSub<T> {
    private Map<String, Object> messageMap;

    public SendSub(String msg,String id,String name, List<T> params) {
        messageMap = new LinkedHashMap<>();
        messageMap.put("msg", msg);
        messageMap.put("id", id);
        messageMap.put("name", name);
        messageMap.put("params", params);
    }

    protected static class SendMessageSub{

        protected static class Args{
            @SerializedName("token")
            private String token;

            public Args(String token){
                this.token=token;
            }
        }
        @SerializedName("useCollection")
        private boolean useCollection;

        @SerializedName("args")
        private List<Args> args;


        public SendMessageSub(boolean useCollection, List<Args> args) {
            this.useCollection = useCollection;
            this.args = args;
        }
    }

    public String toJson() {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(this.messageMap);
    }
}
