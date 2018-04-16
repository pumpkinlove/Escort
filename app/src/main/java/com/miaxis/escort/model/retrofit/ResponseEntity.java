package com.miaxis.escort.model.retrofit;

import java.util.List;

/**
 * Created by xu.nan on 2018/2/23.
 */

public class ResponseEntity<T> {
    private String code;
    private String message;
    private T data;
    private List<T> listData;

    public ResponseEntity() {
    }

    public ResponseEntity(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<T> getListData() {
        return listData;
    }

    public void setListData(List<T> listData) {
        this.listData = listData;
    }
}
