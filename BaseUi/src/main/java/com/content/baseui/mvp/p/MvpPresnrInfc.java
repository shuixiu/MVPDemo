package com.content.baseui.mvp.p;


import androidx.annotation.NonNull;

import com.content.baseui.mvp.m.MvpModelInfc;
import com.content.baseui.mvp.v.MvpViewInfc;

public interface MvpPresnrInfc<V extends MvpViewInfc, M extends MvpModelInfc> {

    /**
     * 伴随着 activity onCreate 时触发 初始化自己
     *
     * @param view
     */
    void init(@NonNull V view);

    /**
     * 伴随着 activity onStart 时触发
     */
    void whenStart();

    /**
     * 伴随着 activity onResume 时触发
     */
    void whenResume();

    /**
     * 伴随着 activity onPause 时触发
     */
    void whenPause();

    /**
     * 伴随着 activity onStop 时触发
     */
    void whenStop();

    /**
     * 伴随着 activity onDestory 时触发
     */
    void whenDestroy();
}
