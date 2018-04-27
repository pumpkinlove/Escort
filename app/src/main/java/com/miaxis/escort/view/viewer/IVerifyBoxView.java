package com.miaxis.escort.view.viewer;

import com.miaxis.escort.model.entity.BoxBean;

import java.util.List;

/**
 * Created by 一非 on 2018/4/20.
 */

public interface IVerifyBoxView {
    void updateBoxList(List<BoxBean> boxBeanList);
    void addCount();
    void updateVerify(BoxBean boxBean);
    void updateUnverified();
    void uploadSuccess();
    void uploadFailed(String message);
}
