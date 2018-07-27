package com.miaxis.escort.util;

/**
 * Created by 一非 on 2018/4/17.
 */

public class StaticVariable {

    public static final int TASK_TYPE_COMMON_GET_BOX = 1;
    public static final int TASK_TYPE_COMMON_GIVE_BOX = 2;
    public static final int TASK_TYPE_TEMP_GET_BOX = 3;
    public static final int TASK_TYPE_TEMP_GIVE_BOX = 4;

    public static final String CONFIG = "config";
    public static final String WORKER = "worker";
    public static final String SUCCESS = "200";
    public static final String FAILED = "400";
    public static final String LOGIN_SUCCESS = "login_success";
    public static final String FLAG = "flag";
    public static final int FINGER1ST = 1;
    public static final int FINGER2ND = 2;

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

    public static String getTasktypeName(String tasktype, String tasklevel) {
        if (tasktype.equals("1")) {
            if(!tasklevel.equals("1")){
                return "常规接箱";
            }else{
                return "出库";
            }
        } else if (tasktype.equals("2")) {
            if(!tasklevel.equals("1")){
                return "常规送箱";
            }else{
                return "入库";
            }
        } else if (tasktype.equals("3")) {
            return "临时接箱";
        } else if (tasktype.equals("4")) {
            return "临时送箱";
        } else if (tasktype.equals("7")) {
            return "现金送箱";
        } else if (tasktype.equals("8")) {
            return "现金接箱";
        } else {
            return tasktype;
        }
    }

    public static String convertFileSize(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;

        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        } else
            return String.format("%d B", size);
    }

}
