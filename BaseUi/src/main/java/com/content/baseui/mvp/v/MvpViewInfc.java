package com.content.baseui.mvp.v;



public interface MvpViewInfc {

    /**
     * 页面展示Toast
     */
    void showShortToast(int stringResId);

    void showShortToast(String str);

    void showLongToast(int stringResId);

    void showLongToast(String str);


    /**
     * 加载框
     */
    void showLoading();

    void hideLoading();

    void showError();

    /**
     * 提示用户 需要登录
     */
    void notcNeedLogin();

    /**
     * 退出
     */
    void notcLogOut(boolean isToMain);

}
