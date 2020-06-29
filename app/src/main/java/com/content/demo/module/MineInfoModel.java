package com.content.demo.module;

import android.util.ArrayMap;

import com.content.basehttp.observer.MyObserver;
import com.content.demo.base.m.NetApiModel;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;


public class MineInfoModel extends NetApiModel implements MineInfoWectContract.IMineInfoModel {

    @Override
    public Observable<ResponseBody> getMineInfo() {

        Map<String,Object> map= new HashMap<>();
        map.put("memberId","201213");
        map.put("unionId","f1269ac3f55911e9847d00163e054dc1");
        map.put("storeId","117");
        map.put("openid","openid201213-117");
        return netApi.getGetDataMapBody(map).subscribeOn(Schedulers.io())//在子线程取数据
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
