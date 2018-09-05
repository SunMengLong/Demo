package com.demo.dd;

import android.Manifest;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {

    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置状态栏底色颜色
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().setStatusBarColor(Color.parseColor("#A5A5A5"));
        setContentView(R.layout.activity_main);
        img = (ImageView) findViewById(R.id.img);
        requestRuntimePermision(new String[]{Manifest.permission.READ_PHONE_STATE}, new PermissioLitener() {
            @Override
            public void onGranted() {
                if("867398034955559".equals(getIMEI())){
                    img.setVisibility(View.VISIBLE);
                }
                Log.i("sml", "onGranted: ...."+getIMEI());
            }

            @Override
            public void onDenied(ArrayList<String> list) {
                finish();
            }
        });
    }

    public final String getIMEI() {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
            String imei = telephonyManager.getDeviceId();
            if (imei == null) {
                imei = "";
            }
            return imei;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }
}
