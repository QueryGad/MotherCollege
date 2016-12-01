package com.player.mothercollege.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/12/1.
 */
public class HotArticleDetailsBean {
    /**
     * datetime : 2016-12-01 15:53:43
     * from : 家有宝宝
     * hasKeep : true
     * hasLike : true
     * pics : ["http://121.42.31.133:8201/AppUserSpace/U00000002/images/UserTrends/20161201/20161201155343_01740.jpg","http://121.42.31.133:8201/AppUserSpace/U00000002/images/UserTrends/20161201/20161201155343_04871.jpg","http://121.42.31.133:8201/AppUserSpace/U00000002/images/UserTrends/20161201/20161201155343_04872.jpg"]
     * reviews : [{"content":"发放宣传资料","date":"2016-12-01 17:20","index":"1","rbodyID":0,"rid":67,"uicon":"http://121.42.31.133:8201/AppUserSpace/U00000002/images/UserFace/514299d4-67ba-438d-a7b8-602cce1c38fe.png","uid":"U00000002","unicename":"奋斗，永远是一个人的事。"},{"content":"我是评论1","date":"2016-12-01 17:21","index":"2","rbodyID":0,"rid":76,"uicon":"http://121.42.31.133:8201http://121.42.31.133:8201/AppUserSpace/U00000007/images/UserFace/86fc499b-5fb7-436c-b0b4-29618afe5ded.png","uid":"U00000007","unicename":"大家好"},{"content":"我是评论2","date":"2016-12-01 17:21","index":"3","rbodyID":0,"rid":77,"uicon":"http://121.42.31.133:8201/AppUserSpace/U00000006/images/UserFace/205a741a-3e90-4def-abc3-34426fcac6ef.png","uid":"U00000006","unicename":"你好"},{"content":"我是评论3","date":"2016-12-01 17:21","index":"4","rbodyID":0,"rid":78,"uicon":"","uid":"U00000005","unicename":"她说"},{"content":"我是评4","date":"2016-12-01 17:21","index":"5","rbodyID":0,"rid":79,"uicon":"","uid":"U00000004","unicename":"亟待解决的"},{"content":"我是评论5","date":"2016-12-01 17:21","index":"6","rbodyID":0,"rid":80,"uicon":"","uid":"U00000003","unicename":"123"}]
     * tid : 88
     * title : 我从服务器直接写的标题
     * ttype : 1
     * uicon : http://121.42.31.133:8201/AppUserSpace/U00000002/images/UserFace/514299d4-67ba-438d-a7b8-602cce1c38fe.png
     * uid : U00000002
     * uniceName : 奋斗，永远是一个人的事。
     * uvipType : 0
     * viewCount : 5
     * zlikes : [{"uicon":"http://121.42.31.133:8201/AppUserSpace/U00000002/images/UserFace/514299d4-67ba-438d-a7b8-602cce1c38fe.png","uid":"U00000002","unicename":"奋斗，永远是一个人的事。","zindex":1},{"uicon":"","uid":"U00000003","unicename":"123","zindex":2},{"uicon":"","uid":"U00000004","unicename":"亟待解决的","zindex":3},{"uicon":"","uid":"U00000005","unicename":"她说","zindex":4},{"uicon":"http://121.42.31.133:8201/AppUserSpace/U00000006/images/UserFace/205a741a-3e90-4def-abc3-34426fcac6ef.png","uid":"U00000006","unicename":"你好","zindex":5}]
     */

    private String datetime;
    private String from;
    private boolean hasKeep;
    private boolean hasLike;
    private String tid;
    private String title;
    private String content;
    private int ttype;
    private String uicon;
    private String uid;
    private String uniceName;
    private int uvipType;
    private int viewCount;
    private List<String> pics;
    /**
     * content : 发放宣传资料
     * date : 2016-12-01 17:20
     * index : 1
     * rbodyID : 0
     * rid : 67
     * uicon : http://121.42.31.133:8201/AppUserSpace/U00000002/images/UserFace/514299d4-67ba-438d-a7b8-602cce1c38fe.png
     * uid : U00000002
     * unicename : 奋斗，永远是一个人的事。
     */

    private List<ReviewsBean> reviews;
    /**
     * uicon : http://121.42.31.133:8201/AppUserSpace/U00000002/images/UserFace/514299d4-67ba-438d-a7b8-602cce1c38fe.png
     * uid : U00000002
     * unicename : 奋斗，永远是一个人的事。
     * zindex : 1
     */

    private List<ZlikesBean> zlikes;

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public boolean isHasKeep() {
        return hasKeep;
    }

    public void setHasKeep(boolean hasKeep) {
        this.hasKeep = hasKeep;
    }

    public boolean isHasLike() {
        return hasLike;
    }

    public void setHasLike(boolean hasLike) {
        this.hasLike = hasLike;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
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

    public void setContent(String title) {
        this.content = content;
    }

    public int getTtype() {
        return ttype;
    }

    public void setTtype(int ttype) {
        this.ttype = ttype;
    }

    public String getUicon() {
        return uicon;
    }

    public void setUicon(String uicon) {
        this.uicon = uicon;
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

    public int getUvipType() {
        return uvipType;
    }

    public void setUvipType(int uvipType) {
        this.uvipType = uvipType;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public List<String> getPics() {
        return pics;
    }

    public void setPics(List<String> pics) {
        this.pics = pics;
    }

    public List<ReviewsBean> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewsBean> reviews) {
        this.reviews = reviews;
    }

    public List<ZlikesBean> getZlikes() {
        return zlikes;
    }

    public void setZlikes(List<ZlikesBean> zlikes) {
        this.zlikes = zlikes;
    }

    public static class ReviewsBean {
        private String content;
        private String date;
        private String index;
        private int rbodyID;
        private int rid;
        private String uicon;
        private String uid;
        private String unicename;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getIndex() {
            return index;
        }

        public void setIndex(String index) {
            this.index = index;
        }

        public int getRbodyID() {
            return rbodyID;
        }

        public void setRbodyID(int rbodyID) {
            this.rbodyID = rbodyID;
        }

        public int getRid() {
            return rid;
        }

        public void setRid(int rid) {
            this.rid = rid;
        }

        public String getUicon() {
            return uicon;
        }

        public void setUicon(String uicon) {
            this.uicon = uicon;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getUnicename() {
            return unicename;
        }

        public void setUnicename(String unicename) {
            this.unicename = unicename;
        }
    }

    public static class ZlikesBean {
        private String uicon;
        private String uid;
        private String unicename;
        private int zindex;

        public String getUicon() {
            return uicon;
        }

        public void setUicon(String uicon) {
            this.uicon = uicon;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getUnicename() {
            return unicename;
        }

        public void setUnicename(String unicename) {
            this.unicename = unicename;
        }

        public int getZindex() {
            return zindex;
        }

        public void setZindex(int zindex) {
            this.zindex = zindex;
        }
    }
}
