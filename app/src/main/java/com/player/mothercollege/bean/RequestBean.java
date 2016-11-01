package com.player.mothercollege.bean;

/**
 * Created by Administrator on 2016/11/1.
 */
public class RequestBean {
    /**
     * resultCode : 1
     * resultInfo :
     * isSuccess : true
     * arg :
     */

    private String resultCode;
    private String resultInfo;
    private boolean isSuccess;
    private String arg;

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

    public boolean isIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getArg() {
        return arg;
    }

    public void setArg(String arg) {
        this.arg = arg;
    }
}
