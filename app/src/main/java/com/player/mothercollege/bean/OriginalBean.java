package com.player.mothercollege.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/13.
 */
public class OriginalBean {
    /**
     * books : [{"date":"2016-10-13 15:52","editor":"李峰","hasKeep":false,"hasLike":false,"hasReview":false,"img":"http://121.42.31.133:8201/m/img/5.png","index":1,"likeCount":0,"pState":"1","price":5,"sid":"s3w4oi5","title":"小二心里健康培养","url":"#","viewCount":"1432","viewType":0},{"date":"2016-10-13 15:52","editor":"孙正茂","hasKeep":false,"hasLike":false,"hasReview":false,"img":"http://121.42.31.133:8201/m/img/4.png","index":2,"likeCount":0,"pState":"1","price":5,"sid":"s3w4oi5","title":"三岁决定孩子的一生","url":"#","viewCount":"1432","viewType":0},{"date":"2016-10-13 15:52","editor":"魏来","hasKeep":false,"hasLike":false,"hasReview":false,"img":"http://121.42.31.133:8201/m/img/6.png","index":3,"likeCount":0,"pState":"1","price":5,"sid":"s3w4oi5","title":"非暴力沟通孩子","url":"#","viewCount":"1432","viewType":0},{"date":"2016-10-13 15:52","editor":"赫鲁晓夫","hasKeep":false,"hasLike":false,"hasReview":false,"img":"http://121.42.31.133:8201/m/img/3.png","index":4,"likeCount":0,"pState":"1","price":5,"sid":"s3w4oi5","title":"非暴力沟通孩子","url":"#","viewCount":"1432","viewType":0}]
     * lastIndex : 5
     */

    private int lastIndex;
    /**
     * date : 2016-10-13 15:52
     * editor : 李峰
     * hasKeep : false
     * hasLike : false
     * hasReview : false
     * img : http://121.42.31.133:8201/m/img/5.png
     * index : 1
     * likeCount : 0
     * pState : 1
     * price : 5.0
     * sid : s3w4oi5
     * title : 小二心里健康培养
     * url : #
     * viewCount : 1432
     * viewType : 0
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
        private int likeCount;
        private String pState;
        private double price;
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

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
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
