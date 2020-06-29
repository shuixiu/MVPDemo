package com.content.demo.module;



import com.content.basehttp.BaseMsg;
import com.content.baseui.mvp.m.MvpModelInfc;
import com.content.baseui.mvp.p.MvpPresnrInfc;
import com.content.baseui.mvp.v.MvpViewInfc;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

public interface MineInfoWectContract {

    interface IMineInfoPresnr extends MvpPresnrInfc<IMineInfoView, IMineInfoModel> {
        /**
         * 获取当前登陆用户的信息
         */
        void getMineInfo();
    }

    interface IMineInfoModel extends MvpModelInfc {

        Observable<ResponseBody> getMineInfo();
    }

    interface IMineInfoView extends MvpViewInfc {
        void notcMineActivityRust(boolean isOk, Integer number, String msg);
    }
}
