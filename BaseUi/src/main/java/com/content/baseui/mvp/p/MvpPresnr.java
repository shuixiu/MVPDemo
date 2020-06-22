package com.content.baseui.mvp.p;


import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.content.baseui.mvp.m.MvpModelInfc;
import com.content.baseui.mvp.v.MvpViewInfc;
import com.content.baseui.util.DevAppUtil;
import com.content.baseui.util.reflect.ClassAsFactory;

public abstract class MvpPresnr<V extends MvpViewInfc, M extends MvpModelInfc> implements MvpPresnrInfc<V, M> {
    protected V mView;
    protected M mModel;

    @Override
    public void init(@NonNull V view) { //
        mView = view;
        // 由子类来决定Model的类型。由于父类的泛型是接口，所以无法通过反射的方法来获取实例
        Class<? extends M> modelClazz = getModelClazz();
        mModel = ClassAsFactory.buildInstance(modelClazz);
        mModel.init();
    }

    protected abstract Class<? extends M> getModelClazz();

    public void whenStart() {
    }

    public void whenResume() {
    }

    public void whenPause() {
    }

    public void whenStop() {

    }

    public void whenDestroy() {
        mModel.release();
        mModel = null;
        mView = null;
    }

    protected String getResString(@StringRes int strResId) {
        return DevAppUtil.getApplication().getApplicationContext().getResources().getString(strResId);
    }

}
