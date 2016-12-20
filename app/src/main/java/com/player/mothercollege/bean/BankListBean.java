package com.player.mothercollege.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/12/17.
 */
public class BankListBean {

    /**
     * cardNo : 尾号9000
     * index : 1
     * bankIcon :
     * bankType : 借记卡/信用卡
     * bankName : 银行名称后期实现
     * bid : UB000005
     */

    private List<UserBankCardListBean> userBankCardList;

    public List<UserBankCardListBean> getUserBankCardList() {
        return userBankCardList;
    }

    public void setUserBankCardList(List<UserBankCardListBean> userBankCardList) {
        this.userBankCardList = userBankCardList;
    }

    public static class UserBankCardListBean {
        private String cardNo;
        private int index;
        private String bankIcon;
        private String bankType;
        private String bankName;
        private String bid;

        public String getCardNo() {
            return cardNo;
        }

        public void setCardNo(String cardNo) {
            this.cardNo = cardNo;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getBankIcon() {
            return bankIcon;
        }

        public void setBankIcon(String bankIcon) {
            this.bankIcon = bankIcon;
        }

        public String getBankType() {
            return bankType;
        }

        public void setBankType(String bankType) {
            this.bankType = bankType;
        }

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }

        public String getBid() {
            return bid;
        }

        public void setBid(String bid) {
            this.bid = bid;
        }
    }
}
