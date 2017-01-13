package com.player.mothercollege.bean;

/**
 * Created by Administrator on 2017/1/9.
 */
public class JiaQunBean {
    /**
     * arg :
     * resultCode : 2002
     * isSuccess : false
     * resultInfo : gid对应im群不存在
     */

    private String arg;
    private String resultCode;
    private boolean isSuccess;
    private String resultInfo;

    public String getArg() {
        return arg;
    }

    public void setArg(String arg) {
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
