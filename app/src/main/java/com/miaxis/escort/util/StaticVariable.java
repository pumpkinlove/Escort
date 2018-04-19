package com.miaxis.escort.util;

/**
 * Created by 一非 on 2018/4/17.
 */

public class StaticVariable {
    public static final String CONFIG = "config";
    public static final String WORKER = "worker";
    public static final String SUCCESS = "200";
    public static final String FAILED = "400";
    public static final String LOGIN_SUCCESS = "login_success";

    public static String upTaskTypeTurnToString(String type) {
        if ("常规网点接箱".equals(type)) {
            return "1";
        } else if ("常规网点送箱".equals(type)) {
            return "2";
        } else if ("临时网点接箱".equals(type)) {
            return "3";
        } else if ("临时网点送箱".equals(type)) {
            return "4";
        }
        return "0";
    }

}
