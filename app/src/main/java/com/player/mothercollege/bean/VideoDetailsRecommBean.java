package com.player.mothercollege.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/11/7.
 * 视频通用详情页面
 */
public class VideoDetailsRecommBean {
    /**
     * date : 2016-12-10 19:35:00
     * editor : 周正
     * hasBuy : false
     * hasKeep : false
     * hasLike : false
     * hasReview : false
     * img : http://121.42.31.133:8201/upload/image/20161112/20161112181615_1625.jpg
     * index : 1
     * isNew : true
     * isTop : false
     * likeCount : 0
     * pState : 2
     * price : 44
     * review : []
     * sid : V1611190002
     * title : 这是一个未开始的直播
     * url : http://4943.liveplay.myqcloud.com/4943_fc39984ca58911e69776e435c87f075e.m3u8
     * videoContent : 直播内容介绍在这里直播内容介绍在这里直播内容介绍在这里直播内容介绍在这里直播内容介绍在这里
     * viewCount : 112
     * viewType : 1
     */

    private VideoBean video;
    /**
     * otherVideo : []
     * video : {"date":"2016-12-10 19:35:00","editor":"周正","hasBuy":false,"hasKeep":false,"hasLike":false,"hasReview":false,"img":"http://121.42.31.133:8201/upload/image/20161112/20161112181615_1625.jpg","index":1,"isNew":true,"isTop":false,"likeCount":0,"pState":"2","price":44,"review":[],"sid":"V1611190002","title":"这是一个未开始的直播","url":"http://4943.liveplay.myqcloud.com/4943_fc39984ca58911e69776e435c87f075e.m3u8","videoContent":"直播内容介绍在这里直播内容介绍在这里直播内容介绍在这里直播内容介绍在这里直播内容介绍在这里","viewCount":"112","viewType":1}
     */

    private List<?> otherVideo;

    public VideoBean getVideo() {
        return video;
    }

    public void setVideo(VideoBean video) {
        this.video = video;
    }

    public List<?> getOtherVideo() {
        return otherVideo;
    }

    public void setOtherVideo(List<?> otherVideo) {
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
        private List<ReveiwBean> review;

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

        public List<ReveiwBean> getReview() {
            return review;
        }

        public void setReview(List<ReveiwBean> review) {
            this.review = review;
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


}
