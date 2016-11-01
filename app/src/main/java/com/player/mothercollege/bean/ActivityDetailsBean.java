package com.player.mothercollege.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/31.
 */
public class ActivityDetailsBean {
    /**
     * address : 河南省郑州市CBD
     * aid : 1
     * city : 郑州
     * content : url转H5页面或文本内容
     * endDate : 2016-11-02 20:07
     * img : http://121.42.31.133:8201/m/img/5.png
     * isJoin : false
     * isKeep : false
     * isLike : false
     * isTop : false
     * joinCount : 2
     * joiner : [{"uicon":"http://121.42.31.133:8201/m/face/faA023[1].bmp","uid":"as243","unicename":"宝东","zindex":1},{"uicon":"http://121.42.31.133:8201/m/face/faA093[1].bmp","uid":"as243","unicename":"听雨人生","zindex":2}]
     * likes : [{"uicon":"http://121.42.31.133:8201/m/face/faA036[1].bmp","uid":"as243","unicename":"快乐就好","zindex":1},{"uicon":"http://121.42.31.133:8201/m/face/faA091[1].bmp","uid":"as243","unicename":"宝东","zindex":2},{"uicon":"http://121.42.31.133:8201/m/face/faA097[1].bmp","uid":"as243","unicename":"a~~Girls","zindex":3},{"uicon":"http://121.42.31.133:8201/m/face/faA014[1].bmp","uid":"as243","unicename":"听雨人生","zindex":4}]
     * reviews : [{"content":"这个很好,!","date":"2016-11-09-03 22:09","index":"1","rbodyID":0,"rid":1,"uicon":"http://121.42.31.133:8201/m/face/faA074[1].bmp","uid":"aa234","unicename":"Agnes"},{"content":"过来抢占个沙发试试","date":"2016-11-09-03 22:09","index":"2","rbodyID":0,"rid":2,"uicon":"http://121.42.31.133:8201/m/face/faA041[1].bmp","uid":"aa234","unicename":"吊儿郎当"},{"content":"很期待这个课程能长期提供","date":"2016-11-09-03 22:09","index":"3","rbodyID":2,"rid":3,"uicon":"http://121.42.31.133:8201/m/face/faA050[1].bmp","uid":"aa234","unicename":"中桃小鹿"}]
     * starDate : 2016-10-31 20:07
     * title : 这次活动的标题..
     */

    private String address;
    private int aid;
    private String city;
    private String content;
    private String endDate;
    private String img;
    private boolean isJoin;
    private boolean isKeep;
    private boolean isLike;
    private boolean isTop;
    private int joinCount;
    private String starDate;
    private String title;
    /**
     * uicon : http://121.42.31.133:8201/m/face/faA023[1].bmp
     * uid : as243
     * unicename : 宝东
     * zindex : 1
     */

    private List<JoinerBean> joiner;
    /**
     * uicon : http://121.42.31.133:8201/m/face/faA036[1].bmp
     * uid : as243
     * unicename : 快乐就好
     * zindex : 1
     */

    private List<LikesBean> likes;
    /**
     * content : 这个很好,!
     * date : 2016-11-09-03 22:09
     * index : 1
     * rbodyID : 0
     * rid : 1
     * uicon : http://121.42.31.133:8201/m/face/faA074[1].bmp
     * uid : aa234
     * unicename : Agnes
     */

    private List<ReviewsBean> reviews;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public boolean isIsJoin() {
        return isJoin;
    }

    public void setIsJoin(boolean isJoin) {
        this.isJoin = isJoin;
    }

    public boolean isIsKeep() {
        return isKeep;
    }

    public void setIsKeep(boolean isKeep) {
        this.isKeep = isKeep;
    }

    public boolean isIsLike() {
        return isLike;
    }

    public void setIsLike(boolean isLike) {
        this.isLike = isLike;
    }

    public boolean isIsTop() {
        return isTop;
    }

    public void setIsTop(boolean isTop) {
        this.isTop = isTop;
    }

    public int getJoinCount() {
        return joinCount;
    }

    public void setJoinCount(int joinCount) {
        this.joinCount = joinCount;
    }

    public String getStarDate() {
        return starDate;
    }

    public void setStarDate(String starDate) {
        this.starDate = starDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<JoinerBean> getJoiner() {
        return joiner;
    }

    public void setJoiner(List<JoinerBean> joiner) {
        this.joiner = joiner;
    }

    public List<LikesBean> getLikes() {
        return likes;
    }

    public void setLikes(List<LikesBean> likes) {
        this.likes = likes;
    }

    public List<ReviewsBean> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewsBean> reviews) {
        this.reviews = reviews;
    }

    public static class JoinerBean {
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

    public static class LikesBean {
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
}
