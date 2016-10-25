package com.player.mothercollege.bean;

/**
 * Created by Administrator on 2016/10/25.
 */
public class QRBean {
    /**
     * info : 每邀请一名好友注册，送50智慧币。
     智慧币可以订阅学院中的收费课程
     * qrCode : http://121.42.31.133:8201/users/456/qr_34554.png
     * inviteCode : 430596
     */

    private String info;
    private String qrCode;
    private String inviteCode;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }
}
