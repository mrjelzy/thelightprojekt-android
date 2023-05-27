package com.thelightprojekt.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.service.controls.actions.FloatAction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.thelightprojekt.R;

import java.lang.reflect.Array;
import java.util.Arrays;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class ChatFragment extends Fragment {

    private RocketChatWebSocket webSocketClient;
    private FloatingActionButton button;
    private WebSocket webSocket;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        super.onCreate(savedInstanceState);

        button=view.findViewById(R.id.sendButton);
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder().url("wss://chat.thelightprojekt.com/websocket").build();
        WebSocketListener webSocketListener=new WebSocketListener() {
            @Override
            public void onClosed(@NonNull WebSocket webSocket, int code, @NonNull String reason) {
                super.onClosed(webSocket, code, reason);
                System.out.println("Fermer");
            }

            @Override
            public void onMessage(@NonNull WebSocket webSocket, @NonNull String text) {
                System.out.println("Receiving" + text);



                if(text.contains("ping")) {
                    SendMessagePing ping=new SendMessagePing();
                    String pingResponse=ping.toJson();
                    webSocket.send(pingResponse);
                }
            }

            @Override
            public void onOpen(@NonNull WebSocket webSocket, @NonNull Response response) {
                SendConnectMessage messageRequest = new SendConnectMessage(
                        "connect",
                        "1",
                        Arrays.asList("1", "pre2", "pre1")
                );

                String jsonRequest = messageRequest.toJson();
                System.out.println(jsonRequest);
                webSocket.send(jsonRequest);

                SendMessage loginMessage = new SendMessage(
                        "method",
                        "login",
                        "10",
                        Arrays.asList(new SendMessage.SendMessageParamsLogin(
                                new SendMessage.SendMessageParamsLogin.User("maxime"),
                                new SendMessage.SendMessageParamsLogin.Password("aa3d2fe4f6d301dbd6b8fb2d2fddfb7aeebf3bec53ffff4b39a0967afa88c609", "sha-256")
                        ))
                );

                jsonRequest = loginMessage.toJson();
                System.out.println(jsonRequest);
                webSocket.send(jsonRequest);

                SendMessage directRoom = new SendMessage(
                        "method",
                        "createDirectMessage",
                        "11",
                        Arrays.asList("yanis")

                );

                jsonRequest = directRoom.toJson();
                System.out.println(jsonRequest);
                webSocket.send(jsonRequest);
                SendSub messageNotify = new SendSub(
                        "sub",
                        "60",
                        "stream-livechat-room",
                        Arrays.asList("EozcgDJzMLg9Qn5Rva8Q7ZH65Wuhisiux4",new SendSub.SendMessageSub(
                                true,Arrays.asList(new SendSub.SendMessageSub.Args("jkGaw6duhiuh45"))
                        ) )
                );
//                SendSub messageNotify = new SendSub(
//                        "sub",
//                        "13",
//                        "stream-livechat-room",
//                        Arrays.asList("EozcgDJzMLg9Qn5Rva8Q7ZH65Wuhisiux4","true")
//
//                );

                jsonRequest = messageNotify.toJson();
                System.out.println(jsonRequest);
                webSocket.send(jsonRequest);
            }
        };
        webSocket = client.newWebSocket(request, webSocketListener);
        button.setOnClickListener(new View.OnClickListener() {
            int cpt=25;
            @Override
            public void onClick(View v) {
                cpt +=1;
                if(webSocket !=null){
                    SendMessage sendMessage= new SendMessage(
                            "method",
                            "sendMessage",
                            "12",
                            Arrays.asList(new SendMessage.SendMessageParams(
                                    Integer.toString(cpt),
                                    "EozcgDJzMLg9Qn5Rva8Q7ZH65Wuhisiux4",
                                    "Test message"
                            ))

                    );
//                    SendMessage sendMessage=new SendMessage(
//                            "method",
//                            "eraseRoom",
//                            "15",
//                            Arrays.asList("XJqzpxBaa4mKRxrH7a8Q7ZH65Wuhisiux4")
//                    );
                    String jsonRequest=sendMessage.toJson();
                    System.out.println(jsonRequest);
                    webSocket.send(jsonRequest);
                }
            }
        });
//        webSocketClient=new RocketChatWebSocket();
//        webSocket = client.newWebSocket(request, webSocketClient);




    }
}