package com.player.mothercollege.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/12/21.
 */
public class FrendQunRecommBean {
    /**
     * groupId : xxx
     * groupName : 群名称
     * groupIcon : http://localhost:10302/m/face/faA098[1].bmp
     * currentUserJoinState : 1
     * snsGroupID : 环信群ID
     */

    private List<RecommendGroupBean> recommendGroup;
    /**
     * uid : 用户id
     * niceName : 简单爱
     * snsUid : 环信账号
     * icon : http://localhost:10302/m/face/faA073[1].bmp
     * arg : null
     * isVip : true
     * addressBookKey : null
     */

    private List<RecommendUsersBean> recommendUsers;

    public List<RecommendGroupBean> getRecommendGroup() {
        return recommendGroup;
    }

    public void setRecommendGroup(List<RecommendGroupBean> recommendGroup) {
        this.recommendGroup = recommendGroup;
    }

    public List<RecommendUsersBean> getRecommendUsers() {
        return recommendUsers;
    }

    public void setRecommendUsers(List<RecommendUsersBean> recommendUsers) {
        this.recommendUsers = recommendUsers;
    }

    public static class RecommendGroupBean {
        private String groupId;
        private String groupName;
        private String groupIcon;
        private int currentUserJoinState;
        private String snsGroupID;

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }

        public String getGroupName() {
            return groupName;
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }

        public String getGroupIcon() {
            return groupIcon;
        }

        public void setGroupIcon(String groupIcon) {
            this.groupIcon = groupIcon;
        }

        public int getCurrentUserJoinState() {
            return currentUserJoinState;
        }

        public void setCurrentUserJoinState(int currentUserJoinState) {
            this.currentUserJoinState = currentUserJoinState;
        }

        public String getSnsGroupID() {
            return snsGroupID;
        }

        public void setSnsGroupID(String snsGroupID) {
            this.snsGroupID = snsGroupID;
        }
    }

    public static class RecommendUsersBean {
        private String uid;
        private String niceName;
        private String snsUid;
        private String icon;
        private Object arg;
        private boolean isVip;
        private Object addressBookKey;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getNiceName() {
            return niceName;
        }

        public void setNiceName(String niceName) {
            this.niceName = niceName;
        }

        public String getSnsUid() {
            return snsUid;
        }

        public void setSnsUid(String snsUid) {
            this.snsUid = snsUid;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public Object getArg() {
            return arg;
        }

        public void setArg(Object arg) {
            this.arg = arg;
        }

        public boolean isIsVip() {
            return isVip;
        }

        public void setIsVip(boolean isVip) {
            this.isVip = isVip;
        }

        public Object getAddressBookKey() {
            return addressBookKey;
        }

        public void setAddressBookKey(Object addressBookKey) {
            this.addressBookKey = addressBookKey;
        }
    }
}
