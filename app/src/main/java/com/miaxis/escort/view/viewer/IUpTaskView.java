package com.miaxis.escort.view.viewer;

import com.miaxis.escort.model.entity.BoxBean;

import java.util.List;

/**
 * Created by 一非 on 2018/4/11.
 */

public interface IUpTaskView extends IBaseView{
    void updateBox(List<BoxBean> boxBeanList);
    void upTaskSuccess();
    void upTaskFailed(String message);
}
