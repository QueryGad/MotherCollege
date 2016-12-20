package com.player.mothercollege.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/12/19.
 */
public class UserBankListBean {
    /**
     * isVipUser : false
     * userBankCardList : [{"cardNo":"尾号9000","index":1,"bankIcon":"","bankType":"借记卡/信用卡","bankName":"银行名称后期实现","bid":"UB000005"}]
     * CanUsedGoldCount : 4900
     * WX : {"WX_OpenID":null,"WX_NiceName":null,"isBind":false}
     * PhoneNo : 15036133506
     */

    private boolean isVipUser;
    private int CanUsedGoldCount;
    /**
     * WX_OpenID : null
     * WX_NiceName : null
     * isBind : false
     */

    private WXBean WX;
    private String PhoneNo;
    /**
     * cardNo : 尾号9000
     * index : 1
     * bankIcon :
     * bankType : 借记卡/信用卡
     * bankName : 银行名称后期实现
     * bid : UB000005
     */

    private List<UserBankCardListBean> userBankCardList;

    public boolean isIsVipUser() {
        return isVipUser;
    }

    public void setIsVipUser(boolean isVipUser) {
        this.isVipUser = isVipUser;
    }

    public int getCanUsedGoldCount() {
        return CanUsedGoldCount;
    }

    public void setCanUsedGoldCount(int CanUsedGoldCount) {
        this.CanUsedGoldCount = CanUsedGoldCount;
    }

    public WXBean getWX() {
        return WX;
    }

    public void setWX(WXBean WX) {
        this.WX = WX;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String PhoneNo) {
        this.PhoneNo = PhoneNo;
    }

    public List<UserBankCardListBean> getUserBankCardList() {
        return userBankCardList;
    }

    public void setUserBankCardList(List<UserBankCardListBean> userBankCardList) {
        this.userBankCardList = userBankCardList;
    }

    public static class WXBean {
        private Object WX_OpenID;
        private Object WX_NiceName;
        private boolean isBind;

        public Object getWX_OpenID() {
            return WX_OpenID;
        }

        public void setWX_OpenID(Object WX_OpenID) {
            this.WX_OpenID = WX_OpenID;
        }

        public Object getWX_NiceName() {
            return WX_NiceName;
        }

        public void setWX_NiceName(Object WX_NiceName) {
            this.WX_NiceName = WX_NiceName;
        }

        public boolean isIsBind() {
            return isBind;
        }

        public void setIsBind(boolean isBind) {
            this.isBind = isBind;
        }
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
