package com.miaxis.escort.view.viewer;

/**
 * Created by 一非 on 2018/4/18.
 */

public interface ISystemView {
    void clearSuccess();
    void clearFailed();
    void downAppMessageSuccess(String message);
    void downAppMessageFailed(String message);
    void downloadSuccess(String path);
    void downloadFailed(String message);
    void updateProgress(int progress);
    void downloadPause();
}
