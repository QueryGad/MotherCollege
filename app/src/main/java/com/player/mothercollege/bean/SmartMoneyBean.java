package com.player.mothercollege.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/10/18.
 */
public class SmartMoneyBean implements Serializable{
    /**
     * currentNum : 999
     * totalAdd : 1100
     * totalPayOut : 101
     * detail : [{"logID":49595,"logDate":"2016-10-11 15:16:47","info":"活动赠送","changeType":1,"changeNum":20,"desc":null},{"logID":49595,"logDate":"2016-10-11 15:16:47","info":"官方奖励","changeType":1,"changeNum":220,"desc":null},{"logID":49595,"logDate":"2016-10-01 15:16:47","info":"优惠券兑换","changeType":1,"changeNum":420,"desc":null},{"logID":49595,"logDate":"2016-09-21 15:16:47","info":"体现消费","changeType":0,"changeNum":500,"desc":null},{"logID":49595,"logDate":"2016-09-11 15:16:47","info":"APP内消费","changeType":0,"changeNum":120,"desc":null},{"logID":49595,"logDate":"2016-09-01 15:16:47","info":"活动赠送","changeType":1,"changeNum":120,"desc":null},{"logID":49595,"logDate":"2016-08-22 15:16:47","info":"购买直播课程","changeType":0,"changeNum":220,"desc":null},{"logID":49595,"logDate":"2016-08-12 15:16:47","info":"活动赠送","changeType":1,"changeNum":200,"desc":null}]
     */

    private int currentNum;
    private int totalAdd;
    private int totalPayOut;
    /**
     * logID : 49595
     * logDate : 2016-10-11 15:16:47
     * info : 活动赠送
     * changeType : 1
     * changeNum : 20
     * desc : null
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
        private int logID;
        private String logDate;
        private String info;
        private int changeType;
        private int changeNum;
        private Object desc;

        public int getLogID() {
            return logID;
        }

        public void setLogID(int logID) {
            this.logID = logID;
        }

        public String getLogDate() {
            return logDate;
        }

        public void setLogDate(String logDate) {
            this.logDate = logDate;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public int getChangeType() {
            return changeType;
        }

        public void setChangeType(int changeType) {
            this.changeType = changeType;
        }

        public int getChangeNum() {
            return changeNum;
        }

        public void setChangeNum(int changeNum) {
            this.changeNum = changeNum;
        }

        public Object getDesc() {
            return desc;
        }

        public void setDesc(Object desc) {
            this.desc = desc;
        }
    }
}
