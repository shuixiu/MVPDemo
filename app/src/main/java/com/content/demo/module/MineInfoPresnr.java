package com.content.demo.module;


import android.util.ArrayMap;

import com.content.basehttp.impl.OnRetrofit;
import com.content.basehttp.observer.MyObserver;
import com.content.demo.base.p.HMVpmPresnr;
import com.content.demo.common.bean.MineBean;

import java.util.Map;

public class MineInfoPresnr extends HMVpmPresnr<MineInfoWectContract.IMineInfoView, MineInfoWectContract.IMineInfoModel> implements MineInfoWectContract.IMineInfoPresnr {


    @Override
    protected Class<? extends MineInfoWectContract.IMineInfoModel> getModelClazz() {
        return MineInfoModel.class;
    }

    @Override
    public void getMineInfo() {

       mModel.getMineInfo().subscribe(MyObserver.create().getObserver(MineBean.class, new OnRetrofit.OnQueryMapListener<MineBean>() {

            @Override
            public void onSuccess(MineBean mineBean) {
                mView.notcMineActivityRust(true,1,mineBean.getMsg());
            }

            @Override
            public void onError(Throwable e) {
                mView.notcMineActivityRust(true,2,e.getMessage());
            }
        }));
       /* WRetrofit.create().doGet(MineBean.class, "appAuth/wechatMember/my2", new OnRetrofit.OnQueryMapListener<MineBean>() {
            @Override
            public void onMap(ArrayMap<String, String> map) {
                map.put("memberId","201213");
                map.put("unionId","f1269ac3f55911e9847d00163e054dc1");
                map.put("storeId","117");
                map.put("openid","openid201213-117");
            }

            @Override
            public void onSuccess(MineBean mineBean) {
                mView.notcMineActivityRust(true,1,mineBean.getMsg());
            }

            @Override
            public void onError(Throwable e) {
                mView.notcMineActivityRust(true,2,e.getMessage());
            }
        });
*/
    }

}
