package com.content.basehttp.util;

import android.util.Log;


import com.content.basehttp.WRetrofitApp;

import java.io.IOException;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;


public class InterceptorUtil {


    //创建拦截器
    public static HttpLoggingInterceptor LogInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(final String message) {
                Log.e(WRetrofitApp.getLogTag(), "retrofitBack -> " + message); //打印retrofit日志
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return loggingInterceptor;
    }


    //添加请求头
    public static Interceptor HeaderInterceptor(final Map<String, String> headMap) {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Request.Builder builder1 = request.newBuilder();
                /*for (Map.Entry<String, String> entry : headMap.entrySet()) {
                    builder1.addHeader(entry.getKey(), entry.getValue()).build();
                }*/
                builder1.addHeader("unionId", "f1269ac3f55911e9847d00163e054dc1");
                builder1.addHeader("appToken", "t6p3tvV272CK4isF6ObqT6d2B2q7y0B4p4UNc6/UnfbwtiH57BQJWGfcxOlbCUC/sk4Tp2A3nXXx78kMtdPN7MAyVBoIDvkF");

                request =  builder1.build();
                return chain.proceed(request);
            }
        };

    }
}
