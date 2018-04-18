package com.miaxis.escort.presenter;

import com.miaxis.escort.model.entity.BoxBean;
import com.miaxis.escort.model.entity.TaskBoxBean;
import com.miaxis.escort.model.entity.TaskUpBean;

import java.util.List;

/**
 * Created by 一非 on 2018/4/11.
 */

public interface IUpTaskPresenter extends IBasePresenter{
    void loadBox();
    void upTask(TaskUpBean taskUpBean, List<TaskBoxBean> boxBeanList);
}
