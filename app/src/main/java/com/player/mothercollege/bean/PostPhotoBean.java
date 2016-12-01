package com.player.mothercollege.bean;

/**
 * Created by Administrator on 2016/11/28.
 */
public class PostPhotoBean {
    /**
     * dbUrl : /AppUserSpace/commonUser/images/UserFace/16b565e1-0572-4177-9591-7486cfbada06.png
     * displayUrl : http://121.42.31.133:8201/AppUserSpace/commonUser/images/UserFace/16b565e1-0572-4177-9591-7486cfbada06.png
     * msg :
     * resultCode : 1
     * success : true
     */

    private String dbUrl;
    private String displayUrl;
    private String msg;
    private String resultCode;
    private boolean success;

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public String getDisplayUrl() {
        return displayUrl;
    }

    public void setDisplayUrl(String displayUrl) {
        this.displayUrl = displayUrl;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
