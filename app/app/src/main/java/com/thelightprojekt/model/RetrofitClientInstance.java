package com.thelightprojekt.model;

import com.thelightprojekt.BuildConfig;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {

    static {
        System.loadLibrary("native-lib");
    }

    public static native String getEncryptedKey();
    private static Retrofit retrofit;

    public static Retrofit getRetrofit(){

        if(retrofit == null){

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient httpClient = new OkHttpClient.Builder()
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

            retrofit = new Retrofit.Builder()
                    .baseUrl("https://www.thelightprojekt.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient)
                    .build();
        }
        return retrofit;
    }
}
