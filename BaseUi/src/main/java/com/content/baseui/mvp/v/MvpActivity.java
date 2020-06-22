package com.content.baseui.mvp.v;

import android.content.Intent;
import android.os.Bundle;


import com.content.baseui.mvp.DevBaseActivity;
import com.content.baseui.mvp.p.MvpPresnr;
import com.content.baseui.util.toast.DevToastUtil;

import java.util.HashSet;
import java.util.Set;

public abstract class MvpActivity extends DevBaseActivity implements MvpViewInfc {

    // 用于缓存页面中可能存在的 presnrs组件
    private Set<MvpPresnr> mAllPresenters;

    /**
     * 需要子类来实现，初始化流程中需要的 presnrs组件
     */
    protected abstract MvpPresnr[] setUpPresnrs();

    /**
     * 从intent中解析数据，具体子类来实现
     *
     * @param arg 一定不会为空 已判断过了
     */
    protected abstract void parseArgumentsFromIntent(Intent arg);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAllPresenters = new HashSet<>();
        MvpPresnr[] presnrClazzs = setUpPresnrs();
        if (presnrClazzs != null) {
            for (int i = 0; i < presnrClazzs.length; i++) {
                presnrClazzs[i].init(this);
                mAllPresenters.add(presnrClazzs[i]);
            }
        }
        if (getIntent() != null) {
            parseArgumentsFromIntent(getIntent());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //依次调用presenter的onResume方法
        for (MvpPresnr presenter : mAllPresenters) {
            if (presenter != null) {
                presenter.whenResume();
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        //依次调用presenter的onStop方法
        for (MvpPresnr presenter : mAllPresenters) {
            if (presenter != null) {
                presenter.whenStop();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //依次调用presenter的onPause方法
        for (MvpPresnr presenter : mAllPresenters) {
            if (presenter != null) {
                presenter.whenPause();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        //依次调用presenter的onStart方法
        for (MvpPresnr presenter : mAllPresenters) {
            if (presenter != null) {
                presenter.whenStart();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //依次调用presenter的onDestroy方法
        for (MvpPresnr presenter : mAllPresenters) {
            if (presenter != null) {
                presenter.whenDestroy();
            }
        }
        mAllPresenters.clear();
        mAllPresenters = null;
    }

    @Override
    public void showShortToast(int stringResId) {
        DevToastUtil.getInstance().showShortToast(stringResId);
    }

    @Override
    public void showShortToast(String str) {
        DevToastUtil.getInstance().showShortToast(str);
    }

    @Override
    public void showLongToast(int stringResId) {
        DevToastUtil.getInstance().showLongToast(stringResId);
    }

    @Override
    public void showLongToast(String str) {
        DevToastUtil.getInstance().showLongToast(str);
    }

    @Override
    public void notcNeedLogin() {
    }

    @Override
    public void notcLogOut(boolean isToMain) {

    }
}
