package com.player.mothercollege.bean;

/**
 * Created by Administrator on 2016/10/19.
 */
public class MeBean {


    /**
     * uid : U00000001
     * uicon : http://121.42.31.133:8201/AppUserSpace/U00000001/images/UserFace/62e55c81-72d5-4dc3-b170-6530d01beeb9.png
     * uniceName : 我是谁
     * coins : 500
     * gold : 0
     * followCount : 1
     * fansCount : 2
     */

    private String uid;
    private String uicon;
    private String uniceName;
    private int coins;
    private int gold;
    private int followCount;
    private int fansCount;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUicon() {
        return uicon;
    }

    public void setUicon(String uicon) {
        this.uicon = uicon;
    }

    public String getUniceName() {
        return uniceName;
    }

    public void setUniceName(String uniceName) {
        this.uniceName = uniceName;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getFollowCount() {
        return followCount;
    }

    public void setFollowCount(int followCount) {
        this.followCount = followCount;
    }

    public int getFansCount() {
        return fansCount;
    }

    public void setFansCount(int fansCount) {
        this.fansCount = fansCount;
    }
}
