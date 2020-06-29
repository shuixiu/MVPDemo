package com.content.demo.base.m;


import android.util.Log;

import com.content.basehttp.impl.ApiService;
import com.content.basehttp.observer.MyObserver;
import com.content.basehttp.util.HttpManager;
import com.content.baseui.mvp.m.MvpModelInfc;

public class NetApiModel implements MvpModelInfc {
    protected String TAG = NetApiModel.class.getName();

    protected ApiService netApi;

    @Override
    public void init() {
        netApi = HttpManager.create().getApi();
    }

    @Override
    public void release() {
        if (netApi != null) {
            netApi = null;
        }
        MyObserver.create().dispose();
    }
}
