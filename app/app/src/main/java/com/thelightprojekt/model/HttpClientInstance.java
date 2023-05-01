package com.thelightprojekt.model;

import android.util.Log;

import com.squareup.picasso.Picasso;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpClientInstance {
    private static OkHttpClient client = null;

    private static Retrofit retrofit;

    static {
        System.loadLibrary("native-lib");
    }

    public static native String getEncryptedKey();

    public static OkHttpClient getClient() {
        if (client == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            client = new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request request = chain.request().newBuilder()
                                    .addHeader("Authorization", "Basic " + getEncryptedKey())
                                    .addHeader("Io-Format","JSON")
                                    .build();
                            return chain.proceed(request);
                        }
                    })
                    .addInterceptor(logging)
                    .build();
        }

        return client;
    }


    public static Retrofit getRetrofit(){

        if(retrofit == null){
            OkHttpClient httpClient = HttpClientInstance.getClient();

            if(httpClient != null){
                retrofit = new Retrofit.Builder()
                        .baseUrl("https://www.thelightprojekt.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(httpClient)
                        .build();
            }
        }
        return retrofit;
    }

}
