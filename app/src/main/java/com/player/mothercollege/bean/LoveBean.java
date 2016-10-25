package com.player.mothercollege.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/10.
 */
public class LoveBean {
    /**
     * currentPageSize : 7
     * lastIndex : 7
     * users : [{"index":1,"tjrs":333,"uIcon":"http://121.42.31.133:8201/m/face/faA018[1].bmp","uid":"u2439455","uniceName":"Agnes"},{"index":2,"tjrs":321,"uIcon":"http://121.42.31.133:8201/m/face/faA047[1].bmp","uid":"u2439455","uniceName":"起航.."},{"index":3,"tjrs":300,"uIcon":"http://121.42.31.133:8201/m/face/faA061[1].bmp","uid":"u2439455","uniceName":"Agnes"},{"index":4,"tjrs":234,"uIcon":"http://121.42.31.133:8201/m/face/faA069[1].bmp","uid":"u2439455","uniceName":"宝东"},{"index":5,"tjrs":200,"uIcon":"http://121.42.31.133:8201/m/face/faA001[1].bmp","uid":"u2439455","uniceName":"Agnes"},{"index":6,"tjrs":198,"uIcon":"http://121.42.31.133:8201/m/face/faA033[1].bmp","uid":"u2439455","uniceName":"中桃小鹿"},{"index":7,"tjrs":121,"uIcon":"http://121.42.31.133:8201/m/face/faA030[1].bmp","uid":"u2439455","uniceName":"柠檬香味"}]
     */

    private int currentPageSize;
    private int lastIndex;
    /**
     * index : 1
     * tjrs : 333
     * uIcon : http://121.42.31.133:8201/m/face/faA018[1].bmp
     * uid : u2439455
     * uniceName : Agnes
     */

    private List<UsersBean> users;

    public int getCurrentPageSize() {
        return currentPageSize;
    }

    public void setCurrentPageSize(int currentPageSize) {
        this.currentPageSize = currentPageSize;
    }

    public int getLastIndex() {
        return lastIndex;
    }

    public void setLastIndex(int lastIndex) {
        this.lastIndex = lastIndex;
    }

    public List<UsersBean> getUsers() {
        return users;
    }

    public void setUsers(List<UsersBean> users) {
        this.users = users;
    }

    public static class UsersBean {
        private int index;
        private int tjrs;
        private String uIcon;
        private String uid;
        private String uniceName;

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public int getTjrs() {
            return tjrs;
        }

        public void setTjrs(int tjrs) {
            this.tjrs = tjrs;
        }

        public String getUIcon() {
            return uIcon;
        }

        public void setUIcon(String uIcon) {
            this.uIcon = uIcon;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getUniceName() {
            return uniceName;
        }

        public void setUniceName(String uniceName) {
            this.uniceName = uniceName;
        }
    }
}
