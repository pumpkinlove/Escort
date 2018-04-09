package com.miaxis.escort.model.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by 一非 on 2018/4/9.
 */

@Entity
public class Config {
    @Id
    private Long id;
    private String ip;
    private String port;
    private String orgCode;
    @Generated(hash = 1823216238)
    public Config(Long id, String ip, String port, String orgCode) {
        this.id = id;
        this.ip = ip;
        this.port = port;
        this.orgCode = orgCode;
    }
    @Generated(hash = 589037648)
    public Config() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getIp() {
        return this.ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    public String getPort() {
        return this.port;
    }
    public void setPort(String port) {
        this.port = port;
    }
    public String getOrgCode() {
        return this.orgCode;
    }
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
}
