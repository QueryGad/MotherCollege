package com.player.mothercollege.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/18.
 */
public class FansBean {
    /**
     * currentPageSize : 7
     * lastIndex : 7
     * users : [{"index":1,"tjrs":0,"uIcon":"http://121.42.31.133:8201/m/face/faA019[1].bmp","uid":"u2439455","uniceName":"柠檬香味"},{"index":2,"tjrs":321,"uIcon":"http://121.42.31.133:8201/m/face/faA007[1].bmp","uid":"u2439455","uniceName":"简单爱"},{"index":3,"tjrs":0,"uIcon":"http://121.42.31.133:8201/m/face/faA091[1].bmp","uid":"u2439455","uniceName":"起航.."},{"index":4,"tjrs":0,"uIcon":"http://121.42.31.133:8201/m/face/faA024[1].bmp","uid":"u2439455","uniceName":"快乐就好"},{"index":5,"tjrs":0,"uIcon":"http://121.42.31.133:8201/m/face/faA022[1].bmp","uid":"u2439455","uniceName":"起航.."},{"index":6,"tjrs":0,"uIcon":"http://121.42.31.133:8201/m/face/faA090[1].bmp","uid":"u2439455","uniceName":"宝东"},{"index":7,"tjrs":0,"uIcon":"http://121.42.31.133:8201/m/face/faA077[1].bmp","uid":"u2439455","uniceName":"吊儿郎当"}]
     */

    private int currentPageSize;
    private int lastIndex;
    /**
     * index : 1
     * tjrs : 0
     * uIcon : http://121.42.31.133:8201/m/face/faA019[1].bmp
     * uid : u2439455
     * uniceName : 柠檬香味
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
