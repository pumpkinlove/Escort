package com.miaxis.escort.view.viewer;

import com.miaxis.escort.model.entity.BoxBean;

import java.util.List;

/**
 * Created by 一非 on 2018/4/17.
 */

public interface ISearchBoxView extends IBaseView{
    void updateData(List<BoxBean> boxBeans);
}
