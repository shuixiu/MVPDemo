package com.content.baseui.mvp.v;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.content.baseui.mvp.DevBaseFragment;
import com.content.baseui.mvp.p.MvpPresnr;
import com.content.baseui.mvp.p.MvpPresnrInfc;
import com.content.baseui.util.toast.DevToastUtil;

import java.util.HashSet;
import java.util.Set;

public abstract class MvpFragment extends DevBaseFragment implements MvpViewInfc {

    // 用于缓存页面中可能存在的 presnrs组件
    private Set<MvpPresnrInfc> mAllPresenters;

    /**
     * 需要子类来实现，初始化流程中需要的 presnrs组件
     */
    protected abstract MvpPresnr[] setUpPresnrs();

    /**
     * 从intent中解析数据，具体子类来实现
     *
     * @param arg 一定不会为空 已判断过了
     */
    protected abstract void parseArgumentsFromBundle(Bundle arg);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAllPresenters = new HashSet<>();
        MvpPresnr[] presnrs = setUpPresnrs();
        if (presnrs != null) {
            for (int i = 0; i < presnrs.length; i++) {
                presnrs[i].init(this);
                mAllPresenters.add(presnrs[i]);
            }
        }
        if (getArguments() != null) {
            parseArgumentsFromBundle(getArguments());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //依次调用presenter的onResume方法
        for (MvpPresnrInfc presenter : mAllPresenters) {
            if (presenter != null) {
                presenter.whenResume();
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        //依次调用presenter的onStop方法
        for (MvpPresnrInfc presenter : mAllPresenters) {
            if (presenter != null) {
                presenter.whenStop();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        //依次调用presenter的onPause方法
        for (MvpPresnrInfc presenter : mAllPresenters) {
            if (presenter != null) {
                presenter.whenPause();
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        //依次调用presenter的onStart方法
        for (MvpPresnrInfc presenter : mAllPresenters) {
            if (presenter != null) {
                presenter.whenStart();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //依次调用presenter的onDestroy方法 页面控件均以销毁 mvp模式没有继续存在的必要
        for (MvpPresnrInfc presenter : mAllPresenters) {
            if (presenter != null) {
                presenter.whenDestroy();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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

