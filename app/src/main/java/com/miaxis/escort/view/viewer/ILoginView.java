package com.miaxis.escort.view.viewer;

/**
 * Created by 一非 on 2018/4/8.
 */

public interface ILoginView extends IBaseView {
    void showLoginView();
    void showConfigView();
    void getPermissionsSuccess();
    void getPermissionsFailed();
}
