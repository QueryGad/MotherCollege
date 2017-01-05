package com.player.mothercollege.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/1/2.
 */
public class SmartListBean {
    /**
     * goodID : p10001
     * goodName : 300智慧币
     * index : 1
     */

    private List<PayGoodListBean> payGoodList;

    public List<PayGoodListBean> getPayGoodList() {
        return payGoodList;
    }

    public void setPayGoodList(List<PayGoodListBean> payGoodList) {
        this.payGoodList = payGoodList;
    }

    public static class PayGoodListBean {
        private String goodID;
        private String goodName;
        private int index;

        public String getGoodID() {
            return goodID;
        }

        public void setGoodID(String goodID) {
            this.goodID = goodID;
        }

        public String getGoodName() {
            return goodName;
        }

        public void setGoodName(String goodName) {
            this.goodName = goodName;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }
}
