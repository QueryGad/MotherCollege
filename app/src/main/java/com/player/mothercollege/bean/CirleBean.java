package com.player.mothercollege.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/31.
 */
public class CirleBean {
    /**
     * classNo : g01
     * groupId : g1
     * groupName : 我是你的一滴泪
     * hasJoin : false
     * icon : http://121.42.31.133:8201/m/face/faA063[1].bmp
     * index : 1
     * joinCount : 99
     */

    private List<MaybeLikeGroupsBean> maybeLikeGroups;
    /**
     * classNo : g01
     * groupId : g1
     * groupName : 我是宝妈妈
     * hasJoin : true
     * icon : http://121.42.31.133:8201/m/face/faA066[1].bmp
     * index : 1
     * joinCount : 99
     */

    private List<MyGroupsBean> myGroups;

    public List<MaybeLikeGroupsBean> getMaybeLikeGroups() {
        return maybeLikeGroups;
    }

    public void setMaybeLikeGroups(List<MaybeLikeGroupsBean> maybeLikeGroups) {
        this.maybeLikeGroups = maybeLikeGroups;
    }

    public List<MyGroupsBean> getMyGroups() {
        return myGroups;
    }

    public void setMyGroups(List<MyGroupsBean> myGroups) {
        this.myGroups = myGroups;
    }

    public static class MaybeLikeGroupsBean {
        private String classNo;
        private String groupId;
        private String groupName;
        private boolean hasJoin;
        private String icon;
        private int index;
        private int joinCount;

        public String getClassNo() {
            return classNo;
        }

        public void setClassNo(String classNo) {
            this.classNo = classNo;
        }

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

        public boolean isHasJoin() {
            return hasJoin;
        }

        public void setHasJoin(boolean hasJoin) {
            this.hasJoin = hasJoin;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public int getJoinCount() {
            return joinCount;
        }

        public void setJoinCount(int joinCount) {
            this.joinCount = joinCount;
        }
    }

    public static class MyGroupsBean {
        private String classNo;
        private String groupId;
        private String groupName;
        private boolean hasJoin;
        private String icon;
        private int index;
        private int joinCount;

        public String getClassNo() {
            return classNo;
        }

        public void setClassNo(String classNo) {
            this.classNo = classNo;
        }

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

        public boolean isHasJoin() {
            return hasJoin;
        }

        public void setHasJoin(boolean hasJoin) {
            this.hasJoin = hasJoin;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public int getJoinCount() {
            return joinCount;
        }

        public void setJoinCount(int joinCount) {
            this.joinCount = joinCount;
        }
    }
}
