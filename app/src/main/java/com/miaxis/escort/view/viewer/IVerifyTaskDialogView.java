package com.miaxis.escort.view.viewer;

import com.miaxis.escort.model.entity.EscortBean;
import com.miaxis.escort.model.entity.WorkerBean;

/**
 * Created by 一非 on 2018/4/27.
 */

public interface IVerifyTaskDialogView {
    void verifySuccess();
    void verifyFailed(String message);
    void showToasty(String message);
    void removeVerified(EscortBean escortBean);
    void setVerifyWorker(WorkerBean worker);
    void playVoiceMessage(String message);
    void playVoiceMessageOnUIThread(String message);
}
