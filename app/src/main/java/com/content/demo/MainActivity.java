package com.content.demo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.content.baseui.mvp.p.MvpPresnr;
import com.content.demo.base.activity.BaseControlActivity;
import com.content.demo.module.MineInfoPresnr;
import com.content.demo.module.MineInfoWectContract;


public class MainActivity extends BaseControlActivity implements MineInfoWectContract.IMineInfoView {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    private MineInfoPresnr mineInfoPresnr;
    @Override
    protected MvpPresnr[] setUpPresnrs() {
        mineInfoPresnr = new MineInfoPresnr();
        return new MvpPresnr[]{mineInfoPresnr};
    }

    @Override
    protected void parseArgumentsFromIntent(Intent arg) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tv = findViewById(R.id.test);
        tv.setText(stringFromJNI());

        mineInfoPresnr.getMineInfo();
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    @Override
    public void notcMineActivityRust(boolean isOk, Integer number, String msg) {

        Log.d("wwn","+"+number);
    }
}
