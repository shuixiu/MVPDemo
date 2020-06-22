package com.content.baseui.mvp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.content.baseui.util.log.DevLog;
import com.content.baseui.util.toast.DevToastUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class DevBaseFragment extends Fragment {

    protected String TAG = DevBaseFragment.class.getSimpleName();

    protected Context mContext;
//    /**
//     * 封装了常用的弹出框实现方法辅助类
//     */
////    protected DialogHelper dialogHelper;
//    protected ComnlyDialogTool comnlyDialogTool;
//
//    /**
//     * 封装了常用控制系统键盘的方法辅助类
//     */
//    private SysKeyboardUtil keyboardUtil;
//
//    /**
//     * 防连点辅助组件
//     */
//    protected MultiClickHelper multiClickHelper;
//
//    /**
//     * 系统状态栏辅助组件
//     */
//    private DevSysStatusHelper sysStatusHelper;
//
//    /**
//     * Rx 异步调度 管理器
//     */
//    protected DevRxMana mRxManage;

    Unbinder unbinder;

    @Override
    public void onAttach(Context context) {
        DevLog.i(TAG, "onAttach()");
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        DevLog.i(TAG, "onCreate()");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        DevLog.i(TAG, "onCreateView()");
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(getContentViewLayoutID(), container, false);
        unbinder = ButterKnife.bind(this, view);
//        dialogHelper = new DialogHelper(mContext);
      /*  comnlyDialogTool = new ComnlyDialogTool(mContext);
        mRxManage = new DevRxMana();
        keyboardUtil = new SysKeyboardUtil();
        multiClickHelper = new MultiClickHelper();
        sysStatusHelper = new DevSysStatusHelper();*/
//        resetSysStatusBarBgColor(Color.WHITE, true);
        initView(savedInstanceState);
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
//        if (getActivity() == null) { // ViewPager机制，在初始化时会触发一次，需要忽略，但是不应该写在这里！写着没用
//            return;
//        }
        DevLog.i(TAG, "setUserVisibleHint() isVisibleToUser:" + isVisibleToUser);
        super.setUserVisibleHint(isVisibleToUser);
    }

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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        DevLog.i(TAG, "onActivityCreated()");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        DevLog.i(TAG, "onStart()");
        super.onStart();
    }

    @Override
    public void onResume() {
        DevLog.i(TAG, "onResume()");
        super.onResume();
    }

    @Override
    public void onPause() {
        DevLog.i(TAG, "onPause()");
        super.onPause();
    }

    @Override
    public void onStop() {
        DevLog.i(TAG, "onStop()");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        DevLog.i(TAG, "onDestroyView()");
        super.onDestroyView();
//        if (comnlyDialogTool != null) {
//            comnlyDialogTool.free();
//            comnlyDialogTool = null;
//        }
       /* if (mRxManage != null) {
            mRxManage.unsubscribe();
            mRxManage = null;
        }
        keyboardUtil = null;
        multiClickHelper = null;
        sysStatusHelper = null;*/
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        DevLog.i(TAG, "onDestroy()");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        DevLog.i(TAG, "onDetach()");
        super.onDetach();
        mContext = null;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        DevLog.i(TAG, "onHiddenChanged() hidden:" + hidden);
        super.onHiddenChanged(hidden);
    }

   /* *//**
     * 统一广播发射器
     *//*
    protected void sendBroadcast(Info info) {
        Intent intent = new Intent(info.getAction());
        Bundle bInfo = new Bundle();
        bInfo.putSerializable(Info.INFO_KEY, info);
        intent.putExtras(bInfo);
        getActivity().sendBroadcast(intent);
    }

    *//**
     * 隐藏系统默认输入法
     *//*
    protected void goneKeyboard() {
        if (keyboardUtil != null) {
            keyboardUtil.goneKeyboard(getActivity());
        }
    }*/

   /* *//**
     * 强制隐藏键盘
     *
     * @param view 接受软键盘输入的视图
     *//*
    protected void goneKeyboard(View view) {
        if (view == null || mContext == null) {
            return;
        }
        if (keyboardUtil != null) {
            keyboardUtil.goneKeyboard(mContext, view);
        }
    }

    //<editor-fold desc="提供等待框展示功能" defaultstate="collapsed">
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

//    protected MaterialDialog buildConfirmDialog(String title, String content, int positiveTxResId, int negativeTxResId, MaterialDialog.SingleButtonCallback callback) {
//        MaterialDialog.Builder builder = new MaterialDialog.Builder(mContext);
//        builder.backgroundColor(ContextCompat.getColor(mContext, com.deve.agileui.dialog.R.color.new_c1))// 弹出框背景色
//                .titleColor(ContextCompat.getColor(mContext, com.deve.agileui.dialog.R.color.new_c2))// 标题颜色
//                .contentColor(ContextCompat.getColor(mContext, com.deve.agileui.dialog.R.color.new_c3))// 内容文字颜色
//                .widgetColor(ContextCompat.getColor(mContext, com.deve.agileui.dialog.R.color.new_c9))// 不再提醒的checkbox 颜色
//                .positiveColor(ContextCompat.getColor(mContext, com.deve.agileui.dialog.R.color.new_c9)) // 右侧按钮 确认按钮颜色
//                .negativeColor(ContextCompat.getColor(mContext, com.deve.agileui.dialog.R.color.new_c3)) // 右侧按钮 取消按钮颜色
//                .neutralColor(ContextCompat.getColor(mContext, com.deve.agileui.dialog.R.color.new_c3)) // 左侧按钮颜色
//                .cancelable(false).autoDismiss(false).canceledOnTouchOutside(false);// 交互式弹出框 必须用户操作才能够消失
//        MaterialDialog dialog = builder.build();
//        dialog.setTitle(title);
//        dialog.setContent(content);
//        return builder.build();
//    }
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
        if (getActivity() == null || getActivity().getWindow() == null) {
            return;
        }
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getActivity().getWindow().setAttributes(lp);
    }

   /* *//**
     * 将手机状态设置为透明
     *//*
    protected void traslaSysStatusBar() {
        if (getActivity() != null) sysStatusHelper.traslaSysStatusBar(getActivity());
    }

    *//**
     * API >= 21 时 设置状态栏颜色
     *
     * @param color               状态栏颜色
     * @param isSysStatusTextDark 控制状态栏字体 是否是黑色样式
     *//*
    protected void resetSysStatusBarBgColor(@ColorInt int color, boolean isSysStatusTextDark) {
        DevLog.i(TAG, "resetSysStatusBarBgColor isSysStatusTextDark:" + isSysStatusTextDark);
        if (getActivity() != null)
            sysStatusHelper.resetSysStatusBarBgColor(getActivity(), color, isSysStatusTextDark);
    }


    *//**
     * 修改状态栏文字颜色，这里小米，魅族区别对待。
     *//*
    protected void setLightStatusBar(final Activity activity, final boolean dark) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            switch (RomUtils.getLightStatusBarAvailableRomType()) {
                case RomUtils.AvailableRomType.MIUI:
                    MIUISetStatusBarLightMode(activity, dark);
                    break;

                case RomUtils.AvailableRomType.FLYME:
                    setFlymeLightStatusBar(activity, dark);

                    break;

                case RomUtils.AvailableRomType.ANDROID_NATIVE:
                    setAndroidNativeLightStatusBar(activity, dark);
                    break;

            }
        }
    }*/

/*

    public static boolean MIUISetStatusBarLightMode(Activity activity, boolean dark) {
        boolean result = false;
        Window window = activity.getWindow();
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (dark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
                }
                result = true;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && RomUtils.isMiUIV7OrAbove()) {
                    //开发版 7.7.13 及以后版本采用了系统API，旧方法无效但不会报错，所以两个方式都要加上
                    if (dark) {
                        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                    } else {
                        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                    }
                }
            } catch (Exception e) {

            }
        }
        return result;
    }
*/

    private static boolean setFlymeLightStatusBar(Activity activity, boolean dark) {
        boolean result = false;
        if (activity != null) {
            try {
                WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                activity.getWindow().setAttributes(lp);
                result = true;
            } catch (Exception e) {
            }
        }
        return result;
    }

    private static void setAndroidNativeLightStatusBar(Activity activity, boolean dark) {
        View decor = activity.getWindow().getDecorView();
        if (dark) {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }

}
