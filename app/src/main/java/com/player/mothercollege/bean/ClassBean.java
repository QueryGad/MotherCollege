package com.player.mothercollege.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/10/15.
 */
public class ClassBean implements Serializable{
    /**
     * img : http://121.42.31.133:8201/m/img/s8.jpg
     * index : 1
     * link : #
     * rtype : 1
     * sid : s24503
     * title : 小二心里健康培养
     */

    private List<BanerBean> baner;
    /**
     * Vedios : [{"date":"2016-11-07 15:13","editor":"曾丽友","hasBuy":false,"hasKeep":false,"hasLike":false,"hasReview":false,"img":"http://121.42.31.133:8201/m/img/s7.jpg","index":1,"likeCount":0,"pState":"1","price":5,"sid":"s3w4oi5","title":"亲子解读","url":"#","viewCount":"1432","viewType":0},{"date":"2016-11-07 15:13","editor":"毛泽东","hasBuy":false,"hasKeep":false,"hasLike":false,"hasReview":false,"img":"http://121.42.31.133:8201/m/img/5.png","index":2,"likeCount":0,"pState":"1","price":5,"sid":"s3w4oi5","title":"小二心里健康培养","url":"#","viewCount":"1432","viewType":0},{"date":"2016-11-07 15:13","editor":"魏来","hasBuy":false,"hasKeep":false,"hasLike":false,"hasReview":false,"img":"http://121.42.31.133:8201/m/img/s12.jpg","index":3,"likeCount":0,"pState":"1","price":5,"sid":"s3w4oi5","title":"妈妈，我也行","url":"#","viewCount":"1432","viewType":0},{"date":"2016-11-07 15:13","editor":"曾丽友","hasBuy":false,"hasKeep":false,"hasLike":false,"hasReview":false,"img":"http://121.42.31.133:8201/m/img/6.png","index":4,"likeCount":0,"pState":"1","price":5,"sid":"s3w4oi5","title":"心理营养大餐","url":"#","viewCount":"1432","viewType":0}]
     * className : 父母课堂
     * index : 1
     */

    private List<TabClassBean> tabClass;

    public List<BanerBean> getBaner() {
        return baner;
    }

    public void setBaner(List<BanerBean> baner) {
        this.baner = baner;
    }

    public List<TabClassBean> getTabClass() {
        return tabClass;
    }

    public void setTabClass(List<TabClassBean> tabClass) {
        this.tabClass = tabClass;
    }

    public static class BanerBean {
        private String img;
        private int index;
        private String link;
        private int rtype;
        private String sid;
        private String title;

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

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public int getRtype() {
            return rtype;
        }

        public void setRtype(int rtype) {
            this.rtype = rtype;
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
    }

    public static class TabClassBean {
        private String className;
        private int index;
        /**
         * date : 2016-11-07 15:13
         * editor : 曾丽友
         * hasBuy : false
         * hasKeep : false
         * hasLike : false
         * hasReview : false
         * img : http://121.42.31.133:8201/m/img/s7.jpg
         * index : 1
         * likeCount : 0
         * pState : 1
         * price : 5
         * sid : s3w4oi5
         * title : 亲子解读
         * url : #
         * viewCount : 1432
         * viewType : 0
         */

        private List<VediosBean> Vedios;

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public List<VediosBean> getVedios() {
            return Vedios;
        }

        public void setVedios(List<VediosBean> Vedios) {
            this.Vedios = Vedios;
        }

        public static class VediosBean {
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
}
