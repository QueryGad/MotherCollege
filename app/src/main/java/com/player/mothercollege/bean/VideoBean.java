package com.player.mothercollege.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/13.
 */
public class VideoBean {
    /**
     * date : 2016-10-01 22:00
     * editor : 王武
     * hasKeep : false
     * hasLike : false
     * hasReview : false
     * img : http://121.42.31.133:8201/m/img/8.png
     * index : 1
     * likeCount : 0
     * pState : 1
     * price : 0.0
     * sid : s0134
     * title : 李华老师亲临讲课
     * viewCount : 0
     * viewType : 0
     */

    private BzzbBean bzzb;
    /**
     * date : 2016-10-01
     * editor : 李玉刚
     * hasKeep : false
     * hasLike : false
     * hasReview : false
     * img : http://121.42.31.133:8201/m/img/6.png
     * index : 1
     * likeCount : 0
     * pState : 2
     * price : 0.0
     * sid : s0134
     * title : 幽默的日常应对
     * viewCount : 345
     * viewType : 0
     */

    private SqhgBean sqhg;
    /**
     * date : 2016-10-01
     * editor : 王珞丹
     * hasKeep : false
     * hasLike : false
     * hasReview : false
     * img : http://121.42.31.133:8201/m/img/4.png
     * index : 1
     * likeCount : 0
     * pState : 2
     * price : 0.0
     * sid : s0134
     * title : 频频吵架巧应对
     * viewCount : 345
     * viewType : 0
     */

    private List<WqhkBean> wqhk;

    public BzzbBean getBzzb() {
        return bzzb;
    }

    public void setBzzb(BzzbBean bzzb) {
        this.bzzb = bzzb;
    }

    public SqhgBean getSqhg() {
        return sqhg;
    }

    public void setSqhg(SqhgBean sqhg) {
        this.sqhg = sqhg;
    }

    public List<WqhkBean> getWqhk() {
        return wqhk;
    }

    public void setWqhk(List<WqhkBean> wqhk) {
        this.wqhk = wqhk;
    }

    public static class BzzbBean {
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

    public static class SqhgBean {
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

    public static class WqhkBean {
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
