package com.player.mothercollege.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/19.
 */
public class JiLuBean {
    /**
     * currentPageSize : 5
     * lastIndex : 5
     * myPayClass : [{"editor":"孙正茂","hasViewInfo":"观看至23分钟","hasViewSecond":1380,"img":"http://121.42.31.133:8201/m/img/7.png","sType":1,"sid":"s00603","title":"夫妻如何应对长辈"},{"editor":"斯琴高娃","hasViewInfo":"阅读到章节2","hasViewSecond":0,"img":"http://121.42.31.133:8201/m/img/8.png","sType":0,"sid":"s00921","title":"最美中国妈妈"},{"editor":"万长春","hasViewInfo":"观看至23分钟","hasViewSecond":1380,"img":"http://121.42.31.133:8201/m/img/6.png","sType":1,"sid":"s00899","title":"夫妻如何应对长辈"},{"editor":"赫鲁晓夫","hasViewInfo":"观看至23分钟","hasViewSecond":1380,"img":"http://121.42.31.133:8201/m/img/3.png","sType":1,"sid":"s00823","title":"亲子情商教育系列"}]
     */

    private int currentPageSize;
    private int lastIndex;
    /**
     * editor : 孙正茂
     * hasViewInfo : 观看至23分钟
     * hasViewSecond : 1380
     * img : http://121.42.31.133:8201/m/img/7.png
     * sType : 1
     * sid : s00603
     * title : 夫妻如何应对长辈
     */

    private List<MyPayClassBean> myPayClass;

    public int getCurrentPageSize() {
        return currentPageSize;
    }

    public void setCurrentPageSize(int currentPageSize) {
        this.currentPageSize = currentPageSize;
    }

    public int getLastIndex() {
        return lastIndex;
    }

    public void setLastIndex(int lastIndex) {
        this.lastIndex = lastIndex;
    }

    public List<MyPayClassBean> getMyPayClass() {
        return myPayClass;
    }

    public void setMyPayClass(List<MyPayClassBean> myPayClass) {
        this.myPayClass = myPayClass;
    }

    public static class MyPayClassBean {
        private String editor;
        private String hasViewInfo;
        private int hasViewSecond;
        private String img;
        private int sType;
        private String sid;
        private String title;

        public String getEditor() {
            return editor;
        }

        public void setEditor(String editor) {
            this.editor = editor;
        }

        public String getHasViewInfo() {
            return hasViewInfo;
        }

        public void setHasViewInfo(String hasViewInfo) {
            this.hasViewInfo = hasViewInfo;
        }

        public int getHasViewSecond() {
            return hasViewSecond;
        }

        public void setHasViewSecond(int hasViewSecond) {
            this.hasViewSecond = hasViewSecond;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public int getSType() {
            return sType;
        }

        public void setSType(int sType) {
            this.sType = sType;
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
}
