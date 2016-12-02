package com.player.mothercollege.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/11/2.
 */
public class ReadBookDetailsBean {
    /**
     * zlist : [{"zindex":1,"uid":"as243","uicon":"http://121.42.31.133:8201/m/face/faA079[1].bmp","unicename":"宝东"},{"zindex":2,"uid":"as243","uicon":"http://121.42.31.133:8201/m/face/faA064[1].bmp","unicename":"宝东"},{"zindex":3,"uid":"as243","uicon":"http://121.42.31.133:8201/m/face/faA098[1].bmp","unicename":"中桃小鹿"},{"zindex":4,"uid":"as243","uicon":"http://121.42.31.133:8201/m/face/faA029[1].bmp","unicename":"听雨人生"},{"zindex":5,"uid":"as243","uicon":"http://121.42.31.133:8201/m/face/faA016[1].bmp","unicename":"中桃小鹿"},{"zindex":6,"uid":"as243","uicon":"http://121.42.31.133:8201/m/face/faA099[1].bmp","unicename":"柠檬香味"},{"zindex":7,"uid":"as243","uicon":"http://121.42.31.133:8201/m/face/faA021[1].bmp","unicename":"中桃小鹿"},{"zindex":8,"uid":"as243","uicon":"http://121.42.31.133:8201/m/face/faA008[1].bmp","unicename":"中桃小鹿"},{"zindex":9,"uid":"as243","uicon":"http://121.42.31.133:8201/m/face/faA066[1].bmp","unicename":"Agnes"},{"zindex":10,"uid":"as243","uicon":"http://121.42.31.133:8201/m/face/faA050[1].bmp","unicename":"宝东"},{"zindex":11,"uid":"as243","uicon":"http://121.42.31.133:8201/m/face/faA047[1].bmp","unicename":"宝东"},{"zindex":12,"uid":"as243","uicon":"http://121.42.31.133:8201/m/face/faA034[1].bmp","unicename":"a~~Girls"}]
     * reveiw : [{"uid":"aa234","content":"这个很好,!","index":"1","rid":1,"rbodyID":0,"uicon":"http://121.42.31.133:8201/m/face/faA093[1].bmp","unicename":"起航..","date":"2016-11-09-03 22:09"},{"uid":"aa234","content":"过来抢占个沙发试试","index":"2","rid":2,"rbodyID":0,"uicon":"http://121.42.31.133:8201/m/face/faA057[1].bmp","unicename":"听雨人生","date":"2016-11-09-03 22:09"},{"uid":"aa234","content":"很期待这个课程能长期提供","index":"3","rid":3,"rbodyID":2,"uicon":"http://121.42.31.133:8201/m/face/faA066[1].bmp","unicename":"听雨人生","date":"2016-11-09-03 22:09"},{"uid":"aa234","content":"这个很好,!","index":"4","rid":3,"rbodyID":0,"uicon":"http://121.42.31.133:8201/m/face/faA050[1].bmp","unicename":"Agnes","date":"2016-11-09-03 22:09"},{"uid":"aa234","content":"过来抢占个沙发试试","index":"2","rid":4,"rbodyID":0,"uicon":"http://121.42.31.133:8201/m/face/faA067[1].bmp","unicename":"起航..","date":"2016-11-09-03 22:09"},{"uid":"aa234","content":"很期待这个课程能长期提供","index":"5","rid":5,"rbodyID":2,"uicon":"http://121.42.31.133:8201/m/face/faA085[1].bmp","unicename":"快乐就好","date":"2016-11-09-03 22:09"},{"uid":"aa234","content":"这个很好,!","index":"6","rid":6,"rbodyID":0,"uicon":"http://121.42.31.133:8201/m/face/faA066[1].bmp","unicename":"中桃小鹿","date":"2016-11-09-03 22:09"},{"uid":"aa234","content":"过来抢占个沙发试试","index":"7","rid":7,"rbodyID":0,"uicon":"http://121.42.31.133:8201/m/face/faA033[1].bmp","unicename":"快乐就好","date":"2016-11-09-03 22:09"},{"uid":"aa2434","content":"很期待这个课程能长期提供","index":"8","rid":8,"rbodyID":4,"uicon":"http://121.42.31.133:8201/m/face/faA022[1].bmp","unicename":"a~~Girls","date":"2016-11-09-03 22:09"}]
     * sid : S430923
     * title : 张教授讲家庭教育
     * content : H5页面URL 或其他内容填充主体
     * reviewCount : 34
     * hasLike : false
     * hasReview : false
     * date : 2016-10-01
     * viewCount : 748
     */

    private String sid;
    private String title;
    private String content;
    private String reviewCount;
    private String hasLike;
    private String hasKeep;
    private String hasReview;
    private String date;
    private int viewCount;
    /**
     * zindex : 1
     * uid : as243
     * uicon : http://121.42.31.133:8201/m/face/faA079[1].bmp
     * unicename : 宝东
     */

    private List<ZlistBean> zlist;
    /**
     * uid : aa234
     * content : 这个很好,!
     * index : 1
     * rid : 1
     * rbodyID : 0
     * uicon : http://121.42.31.133:8201/m/face/faA093[1].bmp
     * unicename : 起航..
     * date : 2016-11-09-03 22:09
     */

    private List<ReveiwBean> reveiw;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(String reviewCount) {
        this.reviewCount = reviewCount;
    }

    public String getHasLike() {
        return hasLike;
    }

    public void setHasLike(String hasLike) {
        this.hasLike = hasLike;
    }

    public String getHasKeep() {
        return hasKeep;
    }

    public void setHasKeep(String hasKeep) {
        this.hasKeep = hasKeep;
    }

    public String getHasReview() {
        return hasReview;
    }

    public void setHasReview(String hasReview) {
        this.hasReview = hasReview;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public List<ZlistBean> getZlist() {
        return zlist;
    }

    public void setZlist(List<ZlistBean> zlist) {
        this.zlist = zlist;
    }

    public List<ReveiwBean> getReveiw() {
        return reveiw;
    }

    public void setReveiw(List<ReveiwBean> reveiw) {
        this.reveiw = reveiw;
    }

    public static class ZlistBean {
        private int zindex;
        private String uid;
        private String uicon;
        private String unicename;

        public int getZindex() {
            return zindex;
        }

        public void setZindex(int zindex) {
            this.zindex = zindex;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getUicon() {
            return uicon;
        }

        public void setUicon(String uicon) {
            this.uicon = uicon;
        }

        public String getUnicename() {
            return unicename;
        }

        public void setUnicename(String unicename) {
            this.unicename = unicename;
        }
    }

    public static class ReveiwBean {
        private String uid;
        private String content;
        private String index;
        private int rid;
        private int rbodyID;
        private String uicon;
        private String unicename;
        private String date;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getIndex() {
            return index;
        }

        public void setIndex(String index) {
            this.index = index;
        }

        public int getRid() {
            return rid;
        }

        public void setRid(int rid) {
            this.rid = rid;
        }

        public int getRbodyID() {
            return rbodyID;
        }

        public void setRbodyID(int rbodyID) {
            this.rbodyID = rbodyID;
        }

        public String getUicon() {
            return uicon;
        }

        public void setUicon(String uicon) {
            this.uicon = uicon;
        }

        public String getUnicename() {
            return unicename;
        }

        public void setUnicename(String unicename) {
            this.unicename = unicename;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }
}
