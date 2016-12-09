package com.player.mothercollege.bean;

/**
 * Created by Administrator on 2016/12/9.
 */
public class SettingHelpBean {
    /**
     * arg : null
     * resultCode : 1001
     * isSuccess : false
     * resultInfo : 参数不符合规定
     */

    private Object arg;
    private String resultCode;
    private boolean isSuccess;
    private String resultInfo;

    public Object getArg() {
        return arg;
    }

    public void setArg(Object arg) {
        this.arg = arg;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public boolean isIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(String resultInfo) {
        this.resultInfo = resultInfo;
    }
}
