package com.player.mothercollege.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/11/7.
 * 视频通用详情页面
 */
public class VideoDetailsRecommBean {
    /**
     * date : 2016-11-07 16:13
     * editor : 万长春
     * hasBuy : false
     * hasKeep : false
     * hasLike : false
     * hasReview : false
     * img : http://121.42.31.133:8201/m/img/4.png
     * index : 1
     * likeCount : 0
     * pState : 1
     * price : 5
     * review : [{"content":"这个很好,!","date":"2016-11-09-03 22:09","index":"1","rbodyID":0,"rid":0,"uicon":"http://121.42.31.133:8201/m/face/faA068[1].bmp","uid":"aa234","unicename":"快乐就好"},{"content":"过来抢占个沙发试试","date":"2016-11-09-03 22:09","index":"2","rbodyID":0,"rid":0,"uicon":"http://121.42.31.133:8201/m/face/faA082[1].bmp","uid":"aa234","unicename":"a~~Girls"},{"content":"很期待这个课程能长期提供","date":"2016-11-09-03 22:09","index":"3","rbodyID":0,"rid":0,"uicon":"http://121.42.31.133:8201/m/face/faA095[1].bmp","uid":"aa234","unicename":"Agnes"}]
     * sid : s3w4oi5
     * title : 心理营养大餐
     * url : #
     * viewCount : 1432
     * viewType : 0
     */

    private VideoBean video;
    /**
     * date : 2016-11-07 16:13
     * editor : 魏来
     * hasBuy : false
     * hasKeep : false
     * hasLike : false
     * hasReview : false
     * img : http://121.42.31.133:8201/m/img/s5.jpg
     * index : 1
     * likeCount : 0
     * pState : 1
     * price : 5
     * sid : s3w4oi5
     * title : 端正孩子的从众心里
     * url : #
     * viewCount : 1432
     * viewType : 0
     */

    private List<OtherVideoBean> otherVideo;

    public VideoBean getVideo() {
        return video;
    }

    public void setVideo(VideoBean video) {
        this.video = video;
    }

    public List<OtherVideoBean> getOtherVideo() {
        return otherVideo;
    }

    public void setOtherVideo(List<OtherVideoBean> otherVideo) {
        this.otherVideo = otherVideo;
    }

    public static class VideoBean {
        private String date;
        private String editor;
        private boolean hasBuy;
        private boolean hasKeep;
        private boolean hasLike;
        private boolean hasReview;
        private String img;
        private int index;
        private int likeCount;
        private String pState;
        private int price;
        private String sid;
        private String title;
        private String url;
        private String viewCount;
        private int viewType;
        /**
         * content : 这个很好,!
         * date : 2016-11-09-03 22:09
         * index : 1
         * rbodyID : 0
         * rid : 0
         * uicon : http://121.42.31.133:8201/m/face/faA068[1].bmp
         * uid : aa234
         * unicename : 快乐就好
         */

        private List<ReviewBean> review;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getEditor() {
            return editor;
        }

        public void setEditor(String editor) {
            this.editor = editor;
        }

        public boolean isHasBuy() {
            return hasBuy;
        }

        public void setHasBuy(boolean hasBuy) {
            this.hasBuy = hasBuy;
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

        public boolean isHasReview() {
            return hasReview;
        }

        public void setHasReview(boolean hasReview) {
            this.hasReview = hasReview;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public int getLikeCount() {
            return likeCount;
        }

        public void setLikeCount(int likeCount) {
            this.likeCount = likeCount;
        }

        public String getPState() {
            return pState;
        }

        public void setPState(String pState) {
            this.pState = pState;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

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

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getViewCount() {
            return viewCount;
        }

        public void setViewCount(String viewCount) {
            this.viewCount = viewCount;
        }

        public int getViewType() {
            return viewType;
        }

        public void setViewType(int viewType) {
            this.viewType = viewType;
        }

        public List<ReviewBean> getReview() {
            return review;
        }

        public void setReview(List<ReviewBean> review) {
            this.review = review;
        }

        public static class ReviewBean {
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

    public static class OtherVideoBean {
        private String date;
        private String editor;
        private boolean hasBuy;
        private boolean hasKeep;
        private boolean hasLike;
        private boolean hasReview;
        private String img;
        private int index;
        private int likeCount;
        private String pState;
        private int price;
        private String sid;
        private String title;
        private String url;
        private String viewCount;
        private int viewType;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getEditor() {
            return editor;
        }

        public void setEditor(String editor) {
            this.editor = editor;
        }

        public boolean isHasBuy() {
            return hasBuy;
        }

        public void setHasBuy(boolean hasBuy) {
            this.hasBuy = hasBuy;
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

        public boolean isHasReview() {
            return hasReview;
        }

        public void setHasReview(boolean hasReview) {
            this.hasReview = hasReview;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public int getLikeCount() {
            return likeCount;
        }

        public void setLikeCount(int likeCount) {
            this.likeCount = likeCount;
        }

        public String getPState() {
            return pState;
        }

        public void setPState(String pState) {
            this.pState = pState;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

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

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getViewCount() {
            return viewCount;
        }

        public void setViewCount(String viewCount) {
            this.viewCount = viewCount;
        }

        public int getViewType() {
            return viewType;
        }

        public void setViewType(int viewType) {
            this.viewType = viewType;
        }
    }
}
