package com.player.mothercollege.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/10.
 */
public class LoveBean {
    /**
     * users : [{"uid":"u2439455","uIcon":"http://121.42.31.133:8201/m/face/faA097[1].bmp","uniceName":"a~~Girls","index":1,"tjrs":501},{"uid":"u2439455","uIcon":"http://121.42.31.133:8201/m/face/faA006[1].bmp","uniceName":"简单爱","index":1,"tjrs":629},{"uid":"u2439455","uIcon":"http://121.42.31.133:8201/m/face/faA042[1].bmp","uniceName":"宝东","index":1,"tjrs":861},{"uid":"u2439455","uIcon":"http://121.42.31.133:8201/m/face/faA062[1].bmp","uniceName":"宝东","index":1,"tjrs":879},{"uid":"u2439455","uIcon":"http://121.42.31.133:8201/m/face/faA093[1].bmp","uniceName":"听雨人生","index":1,"tjrs":39},{"uid":"u2439455","uIcon":"http://121.42.31.133:8201/m/face/faA066[1].bmp","uniceName":"宝东","index":1,"tjrs":765}]
     * lastIndex : 6
     * currentPageSize : 6
     */

    private int lastIndex;
    private int currentPageSize;
    /**
     * uid : u2439455
     * uIcon : http://121.42.31.133:8201/m/face/faA097[1].bmp
     * uniceName : a~~Girls
     * index : 1
     * tjrs : 501
     */

    private List<UsersBean> users;

    public int getLastIndex() {
        return lastIndex;
    }

    public void setLastIndex(int lastIndex) {
        this.lastIndex = lastIndex;
    }

    public int getCurrentPageSize() {
        return currentPageSize;
    }

    public void setCurrentPageSize(int currentPageSize) {
        this.currentPageSize = currentPageSize;
    }

    public List<UsersBean> getUsers() {
        return users;
    }

    public void setUsers(List<UsersBean> users) {
        this.users = users;
    }

    public static class UsersBean {
        private String uid;
        private String uIcon;
        private String uniceName;
        private int index;
        private int tjrs;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getUIcon() {
            return uIcon;
        }

        public void setUIcon(String uIcon) {
            this.uIcon = uIcon;
        }

        public String getUniceName() {
            return uniceName;
        }

        public void setUniceName(String uniceName) {
            this.uniceName = uniceName;
        }

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
    }
}
