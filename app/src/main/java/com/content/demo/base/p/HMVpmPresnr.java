package com.content.demo.base.p;


import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.content.basehttp.BaseMsg;
import com.content.baseui.mvp.m.MvpModelInfc;
import com.content.baseui.mvp.p.MvpPresnr;
import com.content.baseui.mvp.v.MvpViewInfc;
import com.content.baseui.util.str.StrUtil;
import com.content.demo.AppContext;

public abstract class HMVpmPresnr<V extends MvpViewInfc, M extends MvpModelInfc> extends MvpPresnr<V, M> {


    protected boolean isResultOk(BaseMsg<?> msg) {
        return !(msg == null || !BaseMsg.CODE_OK.equals(msg.getCode()));
    }

    protected String getResultMsg(BaseMsg<?> msg, @NonNull String defaultMsgStr) {
        if (msg == null || StrUtil.isEmpty(msg.getMsg())) {
            return defaultMsgStr;
        }
        return msg.getMsg();
    }

    protected String getResultMsg(BaseMsg<?> msgr) {
        return getResultMsg(msgr, "");
    }

}
