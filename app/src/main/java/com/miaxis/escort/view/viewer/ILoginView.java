package com.miaxis.escort.view.viewer;

import com.miaxis.escort.model.entity.WorkerBean;

/**
 * Created by 一非 on 2018/4/8.
 */

public interface ILoginView extends IBaseView {
    void showLoginView();
    void showConfigView();
    void getPermissionsSuccess();
    void getPermissionsFailed();
    void loginSuccess(WorkerBean workerBean);
    void loginFailed(String message);
    void setDialogMessage(String message);
    void loadConfigFailed();
}
