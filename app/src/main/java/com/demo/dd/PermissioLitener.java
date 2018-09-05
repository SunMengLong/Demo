package com.demo.dd;

import java.util.ArrayList;

/**
 * Created by Pooh on 2018/9/5.
 */

public interface PermissioLitener {
    void onGranted();
    void onDenied(ArrayList<String> list);
}
