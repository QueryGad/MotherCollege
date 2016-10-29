package com.player.mothercollege.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/12.
 */
public class RecommBean {
    /**
     * index : 1
     * title : 张教授教学1
     * link : #
     * img : http://121.42.31.133:8201/m/img/1.png
     * rtype : 1
     * sid : r1
     */

    private List<BanerBean> baner;
    /**
     * sid : r40923
     * editor : 张三
     * title : 最美中国妈妈
     * img : http://121.42.31.133:8201/m/img/5.png
     * aState : 1
     * type : a01
     * date : 2016-10-01
     * viewCount : 233
     */

    private List<ListBean> list;

    public List<BanerBean> getBaner() {
        return baner;
    }

    public void setBaner(List<BanerBean> baner) {
        this.baner = baner;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class BanerBean {
        private int index;
        private String title;
        private String link;
        private String img;
        private int rtype;
        private String sid;

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
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
    }

    public static class ListBean {
        private String sid;
        private String editor;
        private String title;
        private String img;
        private String aState;
        private String type;
        private String date;
        private String viewCount;

        public String getSid() {
            return sid;
        }

        public void setSid(String sid) {
            this.sid = sid;
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

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getAState() {
            return aState;
        }

        public void setAState(String aState) {
            this.aState = aState;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getViewCount() {
            return viewCount;
        }

        public void setViewCount(String viewCount) {
            this.viewCount = viewCount;
        }
    }
}
