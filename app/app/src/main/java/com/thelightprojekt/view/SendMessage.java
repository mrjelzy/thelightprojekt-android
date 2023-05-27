package com.thelightprojekt.view;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SendMessage<T> {

    private Map<String, Object> messageMap;

    public SendMessage(String msg, String method, String id, List<T> params) {
        messageMap = new LinkedHashMap<>();
        messageMap.put("msg", msg);
        messageMap.put("method", method);
        messageMap.put("id", id);
        messageMap.put("params", params);
    }


    public SendMessage(String msg, String method, String id,String params) {
        messageMap = new LinkedHashMap<>();
        messageMap.put("msg", msg);
        messageMap.put("method", method);
        messageMap.put("id", id);
        messageMap.put("params", params);
    }



    protected static class SendMessageParamsRegister {
        @SerializedName("email")
        private String email;

        @SerializedName("pass")
        private String pass;

        @SerializedName("name")
        private String user;

        @SerializedName("user-name")
        private String username;

        public SendMessageParamsRegister(String email, String pass,String user,String username) {
            this.email = email;
            this.pass = pass;
            this.user=user;
            this.username=username;
        }
    }

    protected static class SendMessageParams {
        @SerializedName("_id")
        private String _id;

        @SerializedName("rid")
        private String rid;

        @SerializedName("msg")
        private String msg;

        public SendMessageParams(String _id, String rid,String msg) {
            this._id = _id;
            this.rid = rid;
            this.msg=msg;
        }
    }

    protected static class SendMessageParamsLogin {

        protected static class User{
            @SerializedName("username")
            private String username;

            public User(String username){
                this.username=username;
            }
        }

        protected static class Password{

            @SerializedName("digest")
            private String digest;

            @SerializedName("algorithm")
            private String algorithm;

            public Password(String digest,String algorithm){
                this.digest=digest;
                this.algorithm=algorithm;
            }
        }
        @SerializedName("user")
        private User user;

        @SerializedName("password")
        private Password password;


        public SendMessageParamsLogin(User user,Password password) {
            this.password = password;
            this.user = user;
        }
    }


    public String toJson() {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(this.messageMap);
    }
}
