package com.player.mothercollege.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/13.
 */
public class OriginalBean {
    /**
     * books : [{"date":"2016-10-27 21:47","editor":"孙正茂","hasKeep":false,"hasLike":false,"hasReview":false,"img":"http://121.42.31.133:8201/m/img/5.png","index":1,"isNew":false,"isTop":true,"likeCount":0,"sid":"Y0001","title":"让孩子学会心悦诚服","url":"#","viewCount":999},{"date":"2016-10-27 21:47","editor":"李峰","hasKeep":false,"hasLike":false,"hasReview":false,"img":"http://121.42.31.133:8201/m/img/1.png","index":1,"isNew":false,"isTop":true,"likeCount":0,"sid":"Y0001","title":"让孩子学会心悦诚服","url":"#","viewCount":999},{"date":"2016-10-27 21:47","editor":"张微风","hasKeep":false,"hasLike":false,"hasReview":false,"img":"http://121.42.31.133:8201/m/img/8.png","index":1,"isNew":false,"isTop":true,"likeCount":0,"sid":"Y0001","title":"小二心里健康培养","url":"#","viewCount":999},{"date":"2016-10-27 21:47","editor":"万长春","hasKeep":false,"hasLike":false,"hasReview":false,"img":"http://121.42.31.133:8201/m/img/4.png","index":1,"isNew":false,"isTop":true,"likeCount":0,"sid":"Y0001","title":"端正孩子的从众心里","url":"#","viewCount":999},{"date":"2016-10-27 21:47","editor":"曾丽友","hasKeep":false,"hasLike":false,"hasReview":false,"img":"http://121.42.31.133:8201/m/img/5.png","index":1,"isNew":false,"isTop":true,"likeCount":0,"sid":"Y0001","title":"三岁决定孩子的一生","url":"#","viewCount":999},{"date":"2016-10-27 21:47","editor":"李峰","hasKeep":false,"hasLike":false,"hasReview":false,"img":"http://121.42.31.133:8201/m/img/6.png","index":1,"isNew":false,"isTop":true,"likeCount":0,"sid":"Y0001","title":"让孩子学会心悦诚服","url":"#","viewCount":999}]
     * lastIndex : 6
     */

    private int lastIndex;
    /**
     * date : 2016-10-27 21:47
     * editor : 孙正茂
     * hasKeep : false
     * hasLike : false
     * hasReview : false
     * img : http://121.42.31.133:8201/m/img/5.png
     * index : 1
     * isNew : false
     * isTop : true
     * likeCount : 0
     * sid : Y0001
     * title : 让孩子学会心悦诚服
     * url : #
     * viewCount : 999
     */

    private List<BooksBean> books;

    public int getLastIndex() {
        return lastIndex;
    }

    public void setLastIndex(int lastIndex) {
        this.lastIndex = lastIndex;
    }

    public List<BooksBean> getBooks() {
        return books;
    }

    public void setBooks(List<BooksBean> books) {
        this.books = books;
    }

    public static class BooksBean {
        private String date;
        private String editor;
        private boolean hasKeep;
        private boolean hasLike;
        private boolean hasReview;
        private String img;
        private int index;
        private boolean isNew;
        private boolean isTop;
        private int likeCount;
        private String sid;
        private String title;
        private String url;
        private int viewCount;

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

        public int getViewCount() {
            return viewCount;
        }

        public void setViewCount(int viewCount) {
            this.viewCount = viewCount;
        }
    }
}
