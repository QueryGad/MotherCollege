package com.player.mothercollege.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/11/4.
 */
public class MoreCirleBean {
    /**
     * groupClass : {"className":"同学","classNo":"g01"}
     * groups : [{"classNo":"g01","groupId":"g1","groupName":"我是宝妈妈","hasJoin":false,"icon":"http://121.42.31.133:8201/m/face/faA003[1].bmp","index":1,"joinCount":99},{"classNo":"g01","groupId":"g2","groupName":"家庭教育","hasJoin":false,"icon":"http://121.42.31.133:8201/m/face/faA009[1].bmp","index":2,"joinCount":99},{"classNo":"g01","groupId":"g3","groupName":"亲子交流团","hasJoin":false,"icon":"http://121.42.31.133:8201/m/face/faA078[1].bmp","index":3,"joinCount":99},{"classNo":"g01","groupId":"g4","groupName":"文学交流社","hasJoin":false,"icon":"http://121.42.31.133:8201/m/face/faA092[1].bmp","index":4,"joinCount":99},{"classNo":"g01","groupId":"g5","groupName":"文学交流社","hasJoin":false,"icon":"http://121.42.31.133:8201/m/face/faA036[1].bmp","index":5,"joinCount":99},{"classNo":"g01","groupId":"g6","groupName":"亲子交流团","hasJoin":false,"icon":"http://121.42.31.133:8201/m/face/faA013[1].bmp","index":6,"joinCount":99},{"classNo":"g01","groupId":"g7","groupName":"悟人生","hasJoin":false,"icon":"http://121.42.31.133:8201/m/face/faA043[1].bmp","index":7,"joinCount":99},{"classNo":"g01","groupId":"g8","groupName":"我是你的一滴泪","hasJoin":false,"icon":"http://121.42.31.133:8201/m/face/faA027[1].bmp","index":8,"joinCount":99},{"classNo":"g01","groupId":"g9","groupName":"婚姻家庭矛盾","hasJoin":false,"icon":"http://121.42.31.133:8201/m/face/faA084[1].bmp","index":9,"joinCount":99},{"classNo":"g01","groupId":"g10","groupName":"文学交流社","hasJoin":false,"icon":"http://121.42.31.133:8201/m/face/faA069[1].bmp","index":10,"joinCount":99},{"classNo":"g01","groupId":"g11","groupName":"婚姻家庭矛盾","hasJoin":false,"icon":"http://121.42.31.133:8201/m/face/faA040[1].bmp","index":11,"joinCount":99},{"classNo":"g01","groupId":"g12","groupName":"婚姻家庭矛盾","hasJoin":false,"icon":"http://121.42.31.133:8201/m/face/faA089[1].bmp","index":12,"joinCount":99},{"classNo":"g01","groupId":"g13","groupName":"婚姻家庭矛盾","hasJoin":false,"icon":"http://121.42.31.133:8201/m/face/faA001[1].bmp","index":13,"joinCount":99},{"classNo":"g01","groupId":"g14","groupName":"夫妻和谐","hasJoin":false,"icon":"http://121.42.31.133:8201/m/face/faA084[1].bmp","index":14,"joinCount":99},{"classNo":"g01","groupId":"g15","groupName":"悟人生","hasJoin":false,"icon":"http://121.42.31.133:8201/m/face/faA022[1].bmp","index":15,"joinCount":99},{"classNo":"g01","groupId":"g16","groupName":"文学交流社","hasJoin":false,"icon":"http://121.42.31.133:8201/m/face/faA088[1].bmp","index":16,"joinCount":99},{"classNo":"g01","groupId":"g17","groupName":"我是你的一滴泪","hasJoin":false,"icon":"http://121.42.31.133:8201/m/face/faA089[1].bmp","index":17,"joinCount":99},{"classNo":"g01","groupId":"g18","groupName":"我是你的一滴泪","hasJoin":false,"icon":"http://121.42.31.133:8201/m/face/faA055[1].bmp","index":18,"joinCount":99},{"classNo":"g01","groupId":"g19","groupName":"夫妻和谐","hasJoin":false,"icon":"http://121.42.31.133:8201/m/face/faA003[1].bmp","index":19,"joinCount":99},{"classNo":"g01","groupId":"g20","groupName":"夫妻和谐","hasJoin":false,"icon":"http://121.42.31.133:8201/m/face/faA086[1].bmp","index":20,"joinCount":99}]
     * index : 1
     */

    private List<GroupWithClassBean> GroupWithClass;

    public List<GroupWithClassBean> getGroupWithClass() {
        return GroupWithClass;
    }

    public void setGroupWithClass(List<GroupWithClassBean> GroupWithClass) {
        this.GroupWithClass = GroupWithClass;
    }

    public static class GroupWithClassBean {
        /**
         * className : 同学
         * classNo : g01
         */

        private GroupClassBean groupClass;
        private int index;

        private boolean onClick;
        /**
         * classNo : g01
         * groupId : g1
         * groupName : 我是宝妈妈
         * hasJoin : false
         * icon : http://121.42.31.133:8201/m/face/faA003[1].bmp
         * index : 1
         * joinCount : 99
         */

        public void  setOnClick(boolean onClick){
            this.onClick = onClick;
        }

        public boolean getOnClick(){
            return onClick;
        }

        private List<GroupsBean> groups;

        public GroupClassBean getGroupClass() {
            return groupClass;
        }

        public void setGroupClass(GroupClassBean groupClass) {
            this.groupClass = groupClass;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public List<GroupsBean> getGroups() {
            return groups;
        }

        public void setGroups(List<GroupsBean> groups) {
            this.groups = groups;
        }

        public static class GroupClassBean {
            private String className;
            private String classNo;

            public String getClassName() {
                return className;
            }

            public void setClassName(String className) {
                this.className = className;
            }

            public String getClassNo() {
                return classNo;
            }

            public void setClassNo(String classNo) {
                this.classNo = classNo;
            }
        }

        public static class GroupsBean {
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
}
