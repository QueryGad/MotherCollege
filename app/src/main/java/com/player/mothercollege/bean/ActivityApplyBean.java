package com.player.mothercollege.bean;

/**
 * Created by Administrator on 2016/12/9.
 */
public class ActivityApplyBean {
    /**
     * arg :
     * isSuccess : false
     * resultCode : 1021
     * resultInfo : 用户已报名
     */

    private String arg;
    private boolean isSuccess;
    private String resultCode;
    private String resultInfo;

    public String getArg() {
        return arg;
    }

    public void setArg(String arg) {
        this.arg = arg;
    }

    public boolean isIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(String resultInfo) {
        this.resultInfo = resultInfo;
    }
}
