package com.miaxis.escort.presenter;

import com.miaxis.escort.model.entity.EscortBean;
import com.miaxis.escort.model.entity.TaskBean;

import java.util.List;

/**
 * Created by 一非 on 2018/4/27.
 */

public interface IVerifyTaskDialogPresenter extends IBasePresenter{
    void verifyCar(TaskBean taskBean);
    void verifyWorker();
    void verifyEscort(List<EscortBean> escortBeanList);
    void pause();
}
