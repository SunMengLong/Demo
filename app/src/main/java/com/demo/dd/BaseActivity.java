package com.demo.dd;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.Window;

import java.util.ArrayList;

/**
 * @author Created by sml on 2018/8/23.
 */

public class BaseActivity extends FragmentActivity {

    private PermissioLitener permissioLitener;
    ArrayList<String> permisionList = new ArrayList<>();
    ArrayList<String> deniedList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
    }

    public void requestRuntimePermision(String[] permission, PermissioLitener permissioLitener) {
        this.permissioLitener = permissioLitener;
        permisionList.clear();
        for (String per : permission) {
            if (ContextCompat.checkSelfPermission(this, per) != PackageManager.PERMISSION_GRANTED) {
                permisionList.add(per);
            }
        }
        if (!permisionList.isEmpty()) {
            ActivityCompat.requestPermissions(this, permisionList.toArray(new String[permisionList.size()]), 1);
        } else {
            permissioLitener.onGranted();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults.length > 0) {
            deniedList.clear();
            for (int i = 0; i < grantResults.length; i++) {
                int grantResult = grantResults[i];
                String permission = permissions[i];
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    deniedList.add(permission);
                }
            }
            if (!deniedList.isEmpty()) {
                permissioLitener.onDenied(deniedList);
            } else {
                permissioLitener.onGranted();
            }
        }
    }
}
