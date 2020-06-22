package com.content.baseui.util.toast;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class DevToast {

    private String backgroundColor = "#323232";
    /**
     * 最长显示时间 毫秒
     */
    public static final int LENGTH_LONG = 3500;
    /**
     * 最短显示时间 毫秒
     */
    public static final int LENGTH_SHORT = 2000;

    private WindowManager mWindowManager;

    private WindowManager.LayoutParams mWindowParams;

    private View toastView;

    private Context mContext;

    private Handler mHandler;

    private String mToastContent = "";

    private int duration = 0;

    private int animStyleId = android.R.style.Animation_Toast;

    private final Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            removeView();
        }
    };

    private DevToast(Context context, String content, int duration) {
//        Context ctx = context.getApplicationContext();
//        if (ctx == null) {
//            ctx = context;
//        }
        this.mContext = context;
        this.mToastContent = content;
        this.duration = duration;
        mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        init();
    }

    private void init() {
        mWindowParams = new WindowManager.LayoutParams();
        mWindowParams.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        // 定义透明度
        mWindowParams.alpha = 1.0f;
        mWindowParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mWindowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        // 定义在
        mWindowParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
        mWindowParams.format = PixelFormat.TRANSLUCENT;
        mWindowParams.type = WindowManager.LayoutParams.TYPE_TOAST;
        mWindowParams.setTitle("DevToast");
        mWindowParams.packageName = mContext.getPackageName();
        mWindowParams.windowAnimations = animStyleId;
        // mWindowParams.y =
        // mContext.getResources().getDisplayMetrics().widthPixels / 5;
        mWindowParams.y = dip2px(64);
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    private View getDefaultToastView() {
        TextView view = new TextView(mContext);
        view.setText(mToastContent);
        // view.setGravity(Gravity.CENTER_VERTICAL | Gravity.START);
        view.setGravity(Gravity.CENTER_VERTICAL | Gravity.START);
        view.setFocusable(false);
        view.setClickable(false);
        view.setFocusableInTouchMode(false);
        view.setMinHeight(dip2px(24));
        view.setMaxWidth(dip2px(284));
        view.setTextColor(Color.WHITE);
        view.setTextSize(14);
        view.setBackgroundColor(Color.parseColor(backgroundColor));
        Drawable drawable = mContext.getResources().getDrawable(android.R.drawable.toast_frame);
        // sdk版本低于16
        if (Build.VERSION.SDK_INT < 16) {
            view.setBackgroundDrawable(drawable);
        } else {
            view.setBackground(drawable);
        }
        return view;
    }

    public void show() {
        removeView();
        toastView = getDefaultToastView();
        // if (toastView == null)
        // {
        // toastView = getDefaultToastView();
        // }
        // mWindowParams.gravity = android.support.v4.view.GravityCompat
        // .getAbsoluteGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM,
        // android.support.v4.view.ViewCompat
        // .getLayoutDirection(toastView));

        // mWindowParams.gravity = android.support.v4.view.GravityCompat
        // .getAbsoluteGravity(Gravity.CENTER_HORIZONTAL,
        // android.support.v4.view.ViewCompat
        // .getLayoutDirection(toastView));
        // removeView();
        mWindowManager.addView(toastView, mWindowParams);
        if (mHandler == null) {
            mHandler = new Handler();
        }
        mHandler.postDelayed(timerRunnable, duration);
    }

    /**
     * @Description:清除toast窗口<br/>
     * @author:Auri<br/>
     */
    public void removeView() {
        if (toastView != null && toastView.getParent() != null) {
            mWindowManager.removeView(toastView);
            mHandler.removeCallbacks(timerRunnable);
        }
    }

    public static DevToast makeText(Context context, String content, int duration) {
        DevToast helper = new DevToast(context, content, duration);
        return helper;
    }

    public static DevToast makeText(Context context, int strId, int duration) {
        String content = context.getString(strId);
        DevToast helper = new DevToast(context, content, duration);
        return helper;
    }

    public DevToast setAnimation(int animStyleId) {
        this.animStyleId = animStyleId;
        mWindowParams.windowAnimations = this.animStyleId;
        return this;
    }

    public void setmToastContent(String mToastContent) {
        this.mToastContent = mToastContent;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    private int dip2px(float dpValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
