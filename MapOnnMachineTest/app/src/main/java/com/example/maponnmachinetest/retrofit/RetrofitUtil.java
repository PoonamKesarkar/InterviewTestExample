package com.example.maponnmachinetest.retrofit;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import static com.example.maponnmachinetest.util.Constant.homeUrl;

public class RetrofitUtil {
    private static Retrofit retrofit = null;
    private static RetrofitUtil retrofitUtilInstance;

    private RetrofitUtil() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).addInterceptor(new Interceptor() {
            @NotNull
            @Override
            public Response intercept(@NotNull Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .header("X-OC-MERCHANT-ID", "dlMDJT8UNnV8OFi8hk5OqCEUgYiAU6wsddud6M3iCn2RB1MmeaeDQt1F2axybRicFTXfrK5NO8ARBXCINPaEHuaPvn8P5ZiyBgAtsxxHJNpr28URCrTanE564lxUyeCT5r0hg1ITr0GOKDuvIAbwWGc8c5b7K0vcKUcirWi02qUYSkEfVhOv05EwCST8DOZ8r5ZMcxOsvcZtnXrZ42YYBy78xGwzlltGLLodTkqViEz2Sz3RAJaPOAC4iRjNCMk7")
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        }).build();


        retrofit = new Retrofit.Builder()
                .baseUrl(homeUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();


    }

    public static synchronized RetrofitUtil getInstance() {
        if (retrofitUtilInstance == null)
            retrofitUtilInstance = new RetrofitUtil();
        return retrofitUtilInstance;
    }
    public Retrofit getRetrofit(){
        return retrofit;
    }
}
