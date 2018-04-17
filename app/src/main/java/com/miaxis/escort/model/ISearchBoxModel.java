package com.miaxis.escort.model;

import com.miaxis.escort.model.entity.BoxBean;
import com.miaxis.escort.model.entity.Config;
import com.miaxis.escort.model.entity.OpdateBean;

import java.util.List;

/**
 * Created by 一非 on 2018/4/17.
 */

public interface ISearchBoxModel {
    List<BoxBean> loadSearchBox();
    void saveSearchBox(List<BoxBean> boxBeans);
    List<OpdateBean> getBoxOpdate();
}
