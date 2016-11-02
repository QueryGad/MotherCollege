package com.player.mothercollege.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/31.
 */
public class CirleBean {
    /**
     * groupId : g1
     * groupName : 夫妻和谐
     * joinCount : 99
     * hasJoin : true
     * icon : http://121.42.31.133:8201/m/face/faA088[1].bmp
     * classNo : g01
     * index : 1
     */

    private List<MyGroupsBean> myGroups;
    /**
     * groupId : g1
     * groupName : 我是宝妈妈
     * joinCount : 99
     * hasJoin : false
     * icon : http://121.42.31.133:8201/m/face/faA030[1].bmp
     * classNo : g01
     * index : 1
     */

    private List<MaybeLikeGroupsBean> maybeLikeGroups;

    public List<MyGroupsBean> getMyGroups() {
        return myGroups;
    }

    public void setMyGroups(List<MyGroupsBean> myGroups) {
        this.myGroups = myGroups;
    }

    public List<MaybeLikeGroupsBean> getMaybeLikeGroups() {
        return maybeLikeGroups;
    }

    public void setMaybeLikeGroups(List<MaybeLikeGroupsBean> maybeLikeGroups) {
        this.maybeLikeGroups = maybeLikeGroups;
    }

    public static class MyGroupsBean {
        private String groupId;
        private String groupName;
        private int joinCount;
        private boolean hasJoin;
        private String icon;
        private String classNo;
        private int index;

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

        public int getJoinCount() {
            return joinCount;
        }

        public void setJoinCount(int joinCount) {
            this.joinCount = joinCount;
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

        public String getClassNo() {
            return classNo;
        }

        public void setClassNo(String classNo) {
            this.classNo = classNo;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }

    public static class MaybeLikeGroupsBean {
        private String groupId;
        private String groupName;
        private int joinCount;
        private boolean hasJoin;
        private String icon;
        private String classNo;
        private int index;

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

        public int getJoinCount() {
            return joinCount;
        }

        public void setJoinCount(int joinCount) {
            this.joinCount = joinCount;
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

        public String getClassNo() {
            return classNo;
        }

        public void setClassNo(String classNo) {
            this.classNo = classNo;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }
}
