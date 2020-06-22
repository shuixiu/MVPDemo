package com.content.baseui.mvp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;


import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import com.content.baseui.util.log.DevLog;
import com.content.baseui.util.toast.DevToastUtil;

import butterknife.ButterKnife;

public abstract class DevBaseActivity extends AppCompatActivity {

    private final String EXCEP_KEY = "EXCEP_KEY";// Activity 被异常关闭后 保存信息用的KEY值

    protected String TAG = DevBaseActivity.class.getSimpleName();

    protected Context mContext;

  /*  *//**
     * toolbar 辅助类
     *//*
    private DevToolbarHelper toolbarHelper;

    *//**
     * 封装了常用的弹出框实现方法辅助类
     *//*
    //    protected DialogHelper dialogHelper;
    protected ComnlyDialogTool comnlyDialogTool;

    *//**
     * 封装了常用控制系统键盘的方法辅助类
     *//*
    private SysKeyboardUtil keyboardUtil;

    *//**
     * 防连点辅助组件
     *//*
    protected MultiClickHelper multiClickHelper;

    *//**
     * 系统状态栏辅助组件
     *//*
    private DevSysStatusHelper sysStatusHelper;

    *//**
     * Rx 异步调度 管理器
     *//*
    protected DevRxMana mRxManage;*/

    /**
     * 获取布局ID
     *
     * @return 布局id
     */
    protected abstract int getContentViewLayoutID();

    /**
     * 初始化布局以及View控件
     */
    protected abstract void initView(Bundle savedInstanceState);

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        mContext = this;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        DevLog.i(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        if (getContentViewLayoutID() != 0) {
            setContentView(getContentViewLayoutID());
            /*toolbarHelper = new DevToolbarHelper();
            toolbarHelper.initToolbar(this);
//            dialogHelper = new DialogHelper(mContext);
            comnlyDialogTool = new ComnlyDialogTool(mContext);
            mRxManage = new DevRxMana();
            keyboardUtil = new SysKeyboardUtil();
            multiClickHelper = new MultiClickHelper();
            sysStatusHelper = new DevSysStatusHelper();*/
//            resetSysStatusBarBgColor(Color.WHITE, true);
//            View content = ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
//            content.setFitsSystemWindows(true);
            initView(savedInstanceState);
        }
        if (savedInstanceState != null && savedInstanceState.containsKey(EXCEP_KEY)) {// APP异常关闭后的处理
            appHasbeenExcepClosed();
        }
    }

    protected void appHasbeenExcepClosed() {

    }

    @Override
    protected void onNewIntent(Intent intent) {
        DevLog.i(TAG, "onNewIntent: ");
        super.onNewIntent(intent);
    }

    @Override
    protected void onResume() {
        DevLog.i(TAG, "onResume: ");
        super.onResume();
    }

    @Override
    protected void onPause() {
        DevLog.i(TAG, "onPause: ");
        super.onPause();
    }

    @Override
    protected void onStop() {
        DevLog.i(TAG, "onStop: ");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        DevLog.i(TAG, "onDestroy: ");
        super.onDestroy();
       /* if (comnlyDialogTool != null) {
            comnlyDialogTool.free();
            comnlyDialogTool = null;
        }
        if (mRxManage != null) {
            mRxManage.unsubscribe();
            mRxManage = null;
        }
        keyboardUtil = null;
        multiClickHelper = null;
        sysStatusHelper = null;*/
        mContext = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(EXCEP_KEY, EXCEP_KEY);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        DevLog.i(TAG, "onWindowFocusChanged: ");
        super.onWindowFocusChanged(hasFocus);
        /*if (toolbarHelper != null) {
            toolbarHelper.measureToolbarHeightWhenFocusChange(hasFocus);
        }*/
    }

   /* protected void showToolbar(String titleStr) {
        toolbarHelper.showToolbar(this, titleStr);
    }

    protected void showToolbar(@StringRes int strResId) {
        toolbarHelper.showToolbar(this, getResources().getString(strResId));
    }

    protected void showBackbtn() {
        toolbarHelper.showBackbtn(this);
    }

    protected void showBackbtn(@DrawableRes int resId) {
        toolbarHelper.showBackbtn(this, resId);
    }

    protected void showToolbarNotc(String notcStr) {
        toolbarHelper.showToolbarNotc(notcStr);
    }

    protected void hideToolbarNotc() {
        toolbarHelper.hideToolbarNotc();
    }

    protected Toolbar getToolbar() {
        return toolbarHelper.getToolbar();
    }

    protected TextView getToolTitleTxv() {
        return toolbarHelper.getToolTitleTxv();
    }*/

    /**
     * 隐藏系统默认输入法
     */
    protected void goneKeyboard() {
     /*   if (keyboardUtil != null) {
            keyboardUtil.goneKeyboard(this);
        }*/
    }

    /**
     * 强制隐藏键盘
     *
     * @param view 接受软键盘输入的视图
     */
    protected void goneKeyboard(EditText view) {
        if (view == null) {
            return;
        }
        /*if (keyboardUtil != null) {
            keyboardUtil.goneKeyboard(mContext, view);
        }*/
    }

//    /**
//     * 根据用户点击输入动作弹出与收回键盘
//     *
//     * @param event 用户动作
//     * @return
//     */
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent event) {
//        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            // 获得当前焦点所在View
//            View view = getCurrentFocus();
//            if (KeyBoardUtils.isClickEt(view, event)) {
//                // 如果不是edittext，则隐藏键盘
//                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(
//                        Context.INPUT_METHOD_SERVICE);
//                if (inputMethodManager != null) {
//                    // 隐藏键盘
//                    inputMethodManager
//                            .hideSoftInputFromWindow(view.getWindowToken(), 0);
//                }
//            }
//            return super.dispatchTouchEvent(event);
//        }
//        /**
//         * superDispatchTouchEvent是个抽象方法，用于自定义的Window
//         * 此处目的是为了继续将事件由dispatchTouchEvent(MotionEvent
//         * event)传递到onTouchEvent(MotionEvent event) 必不可少，否则所有组件都不能触发
//         * onTouchEvent(MotionEvent event)
//         */
//        try {
//            if (getWindow().superDispatchTouchEvent(event)) {
//                return super.dispatchTouchEvent(event);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return onTouchEvent(event);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            FragmentManager fm = getSupportFragmentManager();
            if (fm != null && fm.getBackStackEntryCount() > 0) {
                fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            } else {
                onBackPressed();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // 统一系统“返回”按钮操作
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 处理系统自带返回键
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }


  /*  //<editor-fold desc="提供等待框展示功能" defaultstate="collapsed">
    public void showLoadingDialog(int strResId) {
        comnlyDialogTool.showLoadingDialog(strResId);
    }

    *//**
     * 展示统一样式的等待弹出框
     *
     * @param strResId 若<0则默认展示 请稍后...
     *//*
    public void showLoadingDialog(int strResId, boolean cancelable) {
        comnlyDialogTool.showLoadingDialog(strResId, cancelable);
    }

    public void showLoadingDialog(String msg) {
        comnlyDialogTool.showLoadingDialog(msg);
    }

    *//**
     * 展示统一样式的等待弹出框
     *
     * @param msg 若为空则默认展示 请稍后...
     *//*
    public void showLoadingDialog(String msg, boolean cancelable) {
        comnlyDialogTool.showLoadingDialog(msg, cancelable);
    }

    public void showDelayedLoadingDialog(int strResId) {
        comnlyDialogTool.showDelayedLoadingDialog(strResId);
    }

    *//**
     * 延时展示等待弹出框 延时300毫秒
     *
     * @param strResId 若<0则默认展示 请稍后...
     *//*
    public void showDelayedLoadingDialog(int strResId, boolean cancelable) {
        comnlyDialogTool.showDelayedLoadingDialog(strResId, cancelable);
    }

    public void showDelayedLoadingDialog(final String msg) {
        comnlyDialogTool.showDelayedLoadingDialog(msg);
    }

    *//**
     * 延时展示等待弹出框 延时300毫秒
     *
     * @param msg 若为空则默认展示 请稍后...
     *//*
    public void showDelayedLoadingDialog(final String msg, boolean cancelable) {
        comnlyDialogTool.showDelayedLoadingDialog(msg, cancelable);
    }

    *//**
     * 销毁等待弹出框
     *//*
    public void dismissLoadingDialog() {
        comnlyDialogTool.dismissLoadingDialog();
    }
    //</editor-fold>

    //<editor-fold desc="提供确认框展示功能" defaultstate="collapsed">

    *//**
     * 展示建议的确认交互弹出框
     *
     * @param title    标题
     * @param content  内容
     * @param callback 确认、取消、中立按钮点击事件
     *//*
    protected void showInteractiveDialog(String title, String content, @NonNull MaterialDialog.SingleButtonCallback callback) {
        comnlyDialogTool.showConfirmDialog(mContext, title, content, callback);
    }

    protected void showInteractiveDialog(String title, String content, int positiveTxResId, int negativeTxResId, @NonNull MaterialDialog.SingleButtonCallback callback) {
        comnlyDialogTool.showConfirmDialog(mContext, title, content, positiveTxResId, negativeTxResId, callback);
    }

    protected void showInteractiveDialog(String title, String content, int positiveTxResId, int negativeTxResId, int neutralTxResId, @NonNull MaterialDialog.SingleButtonCallback callback) {
        comnlyDialogTool.showConfirmDialog(mContext, title, content, positiveTxResId, negativeTxResId, neutralTxResId, callback);
    }

    *//**
     * 销毁通用交互弹出框
     *//*
    public void dismissConfirmDialog() {
        comnlyDialogTool.dismissConfirmDialog();
    }
    //</editor-fold>

    //<editor-fold desc="提供自定义框展示功能" defaultstate="collapsed">
    protected MaterialDialog buildCustomDialog(int layoutResId) {
        return new MaterialCustomDialogTool().buildCustomDialog(mContext, layoutResId);
    }

    protected MaterialDialog buildCustomDialog(int layoutResId, String title, int positiveTxResId, int negativeTxResId, MaterialDialog.SingleButtonCallback callback) {
        return new MaterialCustomDialogTool().buildCustomDialog(mContext, layoutResId, title, positiveTxResId, negativeTxResId, callback);
    }
    //</editor-fold>*/

    protected void showShortToast(int stringResId) {
        DevToastUtil.getInstance().showShortToast(stringResId);
    }

    protected void showShortToast(String str) {
        DevToastUtil.getInstance().showShortToast(str);
    }

    protected void showLongToast(int stringResId) {
        DevToastUtil.getInstance().showLongToast(stringResId);
    }

    protected void showLongToast(String str) {
        DevToastUtil.getInstance().showLongToast(str);
    }

    /**
     * 设置遮罩背景色
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        if (getWindow() == null) {
            return;
        }
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

    /**
     * 将手机状态设置为透明
     *//*
    protected void traslaSysStatusBar() {
        sysStatusHelper.traslaSysStatusBar(this);
    }

    *//**
     * API >= 21 时 设置状态栏颜色
     *
     * @param color               状态栏颜色
     * @param isSysStatusTextDark 控制状态栏字体 是否是黑色样式
     *//*
    protected void resetSysStatusBarBgColor(@ColorInt int color, boolean isSysStatusTextDark) {
        sysStatusHelper.resetSysStatusBarBgColor(this, color, isSysStatusTextDark);
    }
*/
    /**
     * 统一广播发射器
     */
 /*   protected void sendBroadcast(Info info) {
        Intent intent = new Intent(info.getAction());
        Bundle bInfo = new Bundle();
        bInfo.putSerializable(Info.INFO_KEY, info);
        intent.putExtras(bInfo);
        mContext.sendBroadcast(intent);
    }*/

}
