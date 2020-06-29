package com.content.basehttp.impl;

import android.util.ArrayMap;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public interface OnRetrofit {

    interface OnQueryMapListener<T> {


        void onSuccess(T t);

        void onError(Throwable e);
    }


    interface OnDownLoadListener {

        void onSuccess(int terms);

        void onError(Throwable e);

//        void hasDown(String path);
    }

    interface OnUpLoadListener<T> {

        void onFormDataPartMap(Map<String, String> map);

        void onFileList(List<File> fileList);

        void onSuccess(T t);

        void onError(Throwable e);
    }
}
