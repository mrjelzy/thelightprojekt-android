package com.thelightprojekt.model;

import android.content.Context;
import android.util.Log;

import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class HttpClientInstance {

    private static OkHttpClient clientJson = null;
    private static OkHttpClient clientXML = null;
    private static Retrofit retrofit;
    private static Retrofit retrofitXML;
    private static Picasso picasso;

    static {
        System.loadLibrary("native-lib");
    }

    public static native String getEncryptedKey();

    public static OkHttpClient getClientJson() {
        if (clientJson == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            clientJson = new OkHttpClient.Builder()
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

        return clientJson;
    }

    public static OkHttpClient getClientForXML() {
        if (clientXML == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            clientXML = new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request request = chain.request().newBuilder()
                                    .addHeader("Authorization", "Basic " + getEncryptedKey())
                                    .build();
                            return chain.proceed(request);
                        }
                    })
                    .addInterceptor(logging)
                    .build();
        }

        return clientXML;
    }


    public static Retrofit getRetrofit(){

        if(retrofit == null){
            OkHttpClient httpClient = HttpClientInstance.getClientJson();

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

    public static Retrofit getRetrofitXml(){

        if(retrofitXML == null){
            OkHttpClient httpClient = HttpClientInstance.getClientForXML();

            if(httpClient != null){
                retrofitXML = new Retrofit.Builder()
                        .baseUrl("https://www.thelightprojekt.com/")
                        .addConverterFactory(SimpleXmlConverterFactory.create())
                        .client(httpClient)
                        .build();
            }
        }
        return retrofitXML;
    }

    public static Picasso getPicasso(Context context){
        OkHttpClient httpClient = HttpClientInstance.getClientJson();
        if(httpClient != null) {
            picasso = new Picasso.Builder(context)
                    .downloader(new OkHttp3Downloader(httpClient))
                    .build();
        }
        return picasso;
    }

}
