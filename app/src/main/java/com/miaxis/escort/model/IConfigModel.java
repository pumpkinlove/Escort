package com.miaxis.escort.model;

import com.miaxis.escort.model.entity.Config;

/**
 * Created by 一非 on 2018/4/8.
 */

public interface IConfigModel {
    void saveConfig(Config config);
    void loadConfig();
}
