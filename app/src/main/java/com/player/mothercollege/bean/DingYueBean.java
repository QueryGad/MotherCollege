package com.player.mothercollege.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/18.
 */
public class DingYueBean {

    /**
     * lastIndex : 2
     * currentPageSize : 2
     * myPayClass : [{"sid":"COU1611180002","buyCount":2,"editor":"","title":"课程标题（有课时数据）","stype":12,"img":"http://localhost:8765/upload/image/20161117/20161117194630_2730.jpg","vtype":0,"date":"2016-12-04","url":"http://200036379.vod.myqcloud.com/200036379_dfde072c4f3b4d5c91ec4c47b8bc59e3.f220.av.m3u8","viewCount":0}]
     */

    private int lastIndex;
    private int currentPageSize;
    /**
     * sid : COU1611180002
     * buyCount : 2
     * editor :
     * title : 课程标题（有课时数据）
     * stype : 12
     * img : http://localhost:8765/upload/image/20161117/20161117194630_2730.jpg
     * vtype : 0
     * date : 2016-12-04
     * url : http://200036379.vod.myqcloud.com/200036379_dfde072c4f3b4d5c91ec4c47b8bc59e3.f220.av.m3u8
     * viewCount : 0
     */

    private List<MyPayClassBean> myPayClass;

    public int getLastIndex() {
        return lastIndex;
    }

    public void setLastIndex(int lastIndex) {
        this.lastIndex = lastIndex;
    }

    public int getCurrentPageSize() {
        return currentPageSize;
    }

    public void setCurrentPageSize(int currentPageSize) {
        this.currentPageSize = currentPageSize;
    }

    public List<MyPayClassBean> getMyPayClass() {
        return myPayClass;
    }

    public void setMyPayClass(List<MyPayClassBean> myPayClass) {
        this.myPayClass = myPayClass;
    }

    public static class MyPayClassBean {
        private String sid;
        private int buyCount;
        private String editor;
        private String title;
        private int stype;
        private String img;
        private int vtype;
        private String date;
        private String url;
        private int viewCount;

        public String getSid() {
            return sid;
        }

        public void setSid(String sid) {
            this.sid = sid;
        }

        public int getBuyCount() {
            return buyCount;
        }

        public void setBuyCount(int buyCount) {
            this.buyCount = buyCount;
        }

        public String getEditor() {
            return editor;
        }

        public void setEditor(String editor) {
            this.editor = editor;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getStype() {
            return stype;
        }

        public void setStype(int stype) {
            this.stype = stype;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public int getVtype() {
            return vtype;
        }

        public void setVtype(int vtype) {
            this.vtype = vtype;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
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
