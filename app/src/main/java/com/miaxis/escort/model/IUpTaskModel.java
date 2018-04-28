package com.miaxis.escort.model;

import com.miaxis.escort.model.entity.BoxBean;
import com.miaxis.escort.model.entity.OpdateBean;

import java.util.List;

/**
 * Created by 一非 on 2018/4/16.
 */

public interface IUpTaskModel {
    List<BoxBean> loadBox();
    List<String> loadBoxCode();
    void saveBoxList(List<BoxBean> boxBeanList);
}
