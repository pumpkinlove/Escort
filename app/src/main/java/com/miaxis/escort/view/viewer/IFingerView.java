package com.miaxis.escort.view.viewer;

import android.graphics.Bitmap;

/**
 * Created by 一非 on 2018/4/25.
 */

public interface IFingerView {
    void updateTip(String message);
    void updateImage(Bitmap bitmap);
    void register(String finger);
}
