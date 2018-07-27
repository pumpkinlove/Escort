package com.miaxis.escort.event;

/**
 * Created by tang.yf on 2018/7/27.
 */

public class VoiceEvent {

    private String message;

    public VoiceEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
