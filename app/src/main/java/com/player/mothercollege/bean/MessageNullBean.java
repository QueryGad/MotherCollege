package com.player.mothercollege.bean;

/**
 * Created by Administrator on 2016/12/9.
 */
public class MessageNullBean {
    /**
     * isSuccess : true
     * resultCode : 1
     * resultInfo : 操作成功
     */

    private boolean isSuccess;
    private String resultCode;
    private String resultInfo;

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
