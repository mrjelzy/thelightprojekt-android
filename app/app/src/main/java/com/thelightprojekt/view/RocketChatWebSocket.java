package com.thelightprojekt.view;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.LinkedHashMap;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class RocketChatWebSocket extends WebSocketListener{
    private WebSocket webSocket;
    Gson gson;

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        this.webSocket = webSocket;
        SendConnectMessage messageRequest = new SendConnectMessage(
                "connect",
                "1",
                Arrays.asList("1","pre2","pre1")
        );

        String jsonRequest = messageRequest.toJson();
        System.out.println(jsonRequest);
        sendMessage(jsonRequest);

        SendMessage loginMessage=new SendMessage(
                "method",
                "login",
                "10",
                Arrays.asList(new SendMessage.SendMessageParamsLogin(
                        new SendMessage.SendMessageParamsLogin.User("yanis"),
                        new SendMessage.SendMessageParamsLogin.Password("aa3d2fe4f6d301dbd6b8fb2d2fddfb7aeebf3bec53ffff4b39a0967afa88c609","sha-256")
                ))
        );

        jsonRequest = loginMessage.toJson();
        System.out.println(jsonRequest);
        sendMessage(jsonRequest);

        SendMessage directRoom= new SendMessage(
                "method",
                "createDirectMessage",
                "11",
                Arrays.asList("ryanbengoufa")

        );

        jsonRequest=directRoom.toJson();
        System.out.println(jsonRequest);
        sendMessage(jsonRequest);

//        SendMessage sendMessage= new SendMessage(
//                "method",
//                "sendMessage",
//                "12",
//                Arrays.asList(new SendMessage.SendMessageParams(
//                        "1",
//                        "XJqzpxBaa4mKRxrH7a8Q7ZH65Wuhisiux4",
//                        "Test message 2"
//                ))
//
//        );
//
//        jsonRequest=sendMessage.toJson();
//        System.out.println(jsonRequest);
//        sendMessage(jsonRequest);


    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        System.out.println("Receiving" + text);

        if(text.contains("ping")) {
            SendMessagePing ping=new SendMessagePing();
            String pingResponse=ping.toJson();
            sendMessage(pingResponse);
        }
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        // Le websocket est en train de se fermer
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        // Une erreur s'est produite, traitez-la ici
    }

    public void sendMessage(String message) {
        if (webSocket != null) {
            webSocket.send(message);
        }
        else{
            System.out.println("Web Socket null");
        }
    }

    public void closeWebSocket() {
        if (webSocket != null) {
            webSocket.close(1000, "WebSocket closed");
        }
    }
}

