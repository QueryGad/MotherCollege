package com.player.mothercollege.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/12/6.
 */
public class ClassDetailsBean {
    /**
     * courseID : COU1612010001
     * date : 2016-12-01
     * editor : 周正
     * hasBuy : false
     * hasKeep : false
     * hasLike : false
     * info : 观点鲜明、条理清晰、实用性强，为人父母必修的第一堂课。
     * likeCount : 1
     * payType : 1
     * price : 3980
     * reviews : [{"content":"牙疼","date":"2016-12-01 23:37","index":"1","rbodyID":0,"rid":85,"uicon":"http://121.42.31.133:8201/AppUserSpace/U00000002/images/UserFace/514299d4-67ba-438d-a7b8-602cce1c38fe.png","uid":"U00000002","unicename":"奋斗是一个人的事"},{"content":" 挖掘机","date":"2016-12-03 15:15","index":"2","rbodyID":0,"rid":174,"uicon":"http://121.42.31.133:8201/AppUserSpace/U00000002/images/UserFace/514299d4-67ba-438d-a7b8-602cce1c38fe.png","uid":"U00000002","unicename":"奋斗是一个人的事"}]
     * title : 孩子的说明书
     * viewCount : 215
     * zlikes : [{"uicon":"http://121.42.31.133:8201/AppUserSpace/U00000002/images/UserFace/514299d4-67ba-438d-a7b8-602cce1c38fe.png","uid":"U00000002","unicename":"奋斗是一个人的事","zindex":1}]
     */

    private CourseInfoBean courseInfo;
    /**
     * date : 2016-12-01
     * editor : 周正
     * hasBuy : false
     * hasKeep : false
     * hasLike : false
     * hasReview : false
     * img : http://121.42.31.133:8201/upload/image/20161201/20161201140448_5186.jpg
     * index : 1
     * isNew : true
     * isTop : false
     * likeCount : 1
     * pState : 1
     * price : 3980
     * review : []
     * sid : V1612010001
     * title : 第一集01
     * url : http://200036379.vod.myqcloud.com/200036379_547e9978b77111e695ff957a64bb4efc.f20.mp4
     * videoContent :
     * viewCount : 115
     * viewType : 0
     */

    private List<VideosBean> videos;

    public CourseInfoBean getCourseInfo() {
        return courseInfo;
    }

    public void setCourseInfo(CourseInfoBean courseInfo) {
        this.courseInfo = courseInfo;
    }

    public List<VideosBean> getVideos() {
        return videos;
    }

    public void setVideos(List<VideosBean> videos) {
        this.videos = videos;
    }

    public static class CourseInfoBean {
        private String courseID;
        private String date;
        private String editor;
        private boolean hasBuy;
        private boolean hasKeep;
        private boolean hasLike;
        private String info;
        private int likeCount;
        private int payType;
        private int price;
        private String title;
        private int viewCount;
        /**
         * content : 牙疼
         * date : 2016-12-01 23:37
         * index : 1
         * rbodyID : 0
         * rid : 85
         * uicon : http://121.42.31.133:8201/AppUserSpace/U00000002/images/UserFace/514299d4-67ba-438d-a7b8-602cce1c38fe.png
         * uid : U00000002
         * unicename : 奋斗是一个人的事
         */

        private List<ReviewsBean> reviews;
        /**
         * uicon : http://121.42.31.133:8201/AppUserSpace/U00000002/images/UserFace/514299d4-67ba-438d-a7b8-602cce1c38fe.png
         * uid : U00000002
         * unicename : 奋斗是一个人的事
         * zindex : 1
         */

        private List<ZlikesBean> zlikes;

        public String getCourseID() {
            return courseID;
        }

        public void setCourseID(String courseID) {
            this.courseID = courseID;
        }

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

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public int getLikeCount() {
            return likeCount;
        }

        public void setLikeCount(int likeCount) {
            this.likeCount = likeCount;
        }

        public int getPayType() {
            return payType;
        }

        public void setPayType(int payType) {
            this.payType = payType;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getViewCount() {
            return viewCount;
        }

        public void setViewCount(int viewCount) {
            this.viewCount = viewCount;
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

    public static class VideosBean {
        private String date;
        private String editor;
        private boolean hasBuy;
        private boolean hasKeep;
        private boolean hasLike;
        private boolean hasReview;
        private String img;
        private int index;
        private boolean isNew;
        private boolean isTop;
        private int likeCount;
        private String pState;
        private int price;
        private String sid;
        private String title;
        private String url;
        private String videoContent;
        private String viewCount;
        private int viewType;
        private List<?> review;

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

        public boolean isIsNew() {
            return isNew;
        }

        public void setIsNew(boolean isNew) {
            this.isNew = isNew;
        }

        public boolean isIsTop() {
            return isTop;
        }

        public void setIsTop(boolean isTop) {
            this.isTop = isTop;
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

        public String getVideoContent() {
            return videoContent;
        }

        public void setVideoContent(String videoContent) {
            this.videoContent = videoContent;
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

        public List<?> getReview() {
            return review;
        }

        public void setReview(List<?> review) {
            this.review = review;
        }
    }
}
