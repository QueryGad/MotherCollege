package com.player.mothercollege.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/10/18.
 */
public class GlodeMoneyBean implements Serializable{
    /**
     * currentNum : 999
     * detail : [{"changeNum":20,"changeType":1,"info":"活动赠送","logDate":"2016-10-11 11:20:58","logID":49595},{"changeNum":220,"changeType":1,"info":"官方奖励","logDate":"2016-10-11 11:20:58","logID":49595},{"changeNum":420,"changeType":1,"info":"优惠券兑换","logDate":"2016-10-01 11:20:58","logID":49595},{"changeNum":500,"changeType":0,"info":"体现消费","logDate":"2016-09-21 11:20:58","logID":49595},{"changeNum":120,"changeType":0,"info":"APP内消费","logDate":"2016-09-11 11:20:58","logID":49595},{"changeNum":120,"changeType":1,"info":"活动赠送","logDate":"2016-09-01 11:20:58","logID":49595},{"changeNum":220,"changeType":0,"info":"购买直播课程","logDate":"2016-08-22 11:20:58","logID":49595},{"changeNum":200,"changeType":1,"info":"活动赠送","logDate":"2016-08-12 11:20:58","logID":49595}]
     * totalAdd : 1100
     * totalPayOut : 101
     */

    private int currentNum;
    private int totalAdd;
    private int totalPayOut;
    /**
     * changeNum : 20
     * changeType : 1
     * info : 活动赠送
     * logDate : 2016-10-11 11:20:58
     * logID : 49595
     */

    private List<DetailBean> detail;

    public int getCurrentNum() {
        return currentNum;
    }

    public void setCurrentNum(int currentNum) {
        this.currentNum = currentNum;
    }

    public int getTotalAdd() {
        return totalAdd;
    }

    public void setTotalAdd(int totalAdd) {
        this.totalAdd = totalAdd;
    }

    public int getTotalPayOut() {
        return totalPayOut;
    }

    public void setTotalPayOut(int totalPayOut) {
        this.totalPayOut = totalPayOut;
    }

    public List<DetailBean> getDetail() {
        return detail;
    }

    public void setDetail(List<DetailBean> detail) {
        this.detail = detail;
    }

    public static class DetailBean implements Serializable{
        private int changeNum;
        private int changeType;
        private String info;
        private String logDate;
        private int logID;

        public int getChangeNum() {
            return changeNum;
        }

        public void setChangeNum(int changeNum) {
            this.changeNum = changeNum;
        }

        public int getChangeType() {
            return changeType;
        }

        public void setChangeType(int changeType) {
            this.changeType = changeType;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getLogDate() {
            return logDate;
        }

        public void setLogDate(String logDate) {
            this.logDate = logDate;
        }

        public int getLogID() {
            return logID;
        }

        public void setLogID(int logID) {
            this.logID = logID;
        }
    }
}
