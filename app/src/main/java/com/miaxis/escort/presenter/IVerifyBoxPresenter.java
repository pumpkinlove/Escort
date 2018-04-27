package com.miaxis.escort.presenter;

import com.miaxis.escort.model.entity.BoxBean;
import com.miaxis.escort.model.entity.TaskBean;
import com.miaxis.escort.model.entity.TaskExeBean;

import java.util.List;

/**
 * Created by 一非 on 2018/4/20.
 */

public interface IVerifyBoxPresenter extends IBasePresenter{
    void loadBox(TaskBean taskBean);
    void upTaskExe(TaskExeBean taskExeBean);
    void verifyBox(List<BoxBean> boxBeanList);
    void pause();
}
