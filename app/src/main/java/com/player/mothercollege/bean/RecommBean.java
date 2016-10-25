package com.player.mothercollege.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/12.
 */
public class RecommBean {

    /**
     * img : http://121.42.31.133:8201/m/img/5.png
     * index : 1
     * link : #
     * rtype : 1
     * sid : r1
     * title : 张教授教学1
     */

    private List<BanerBean> baner;
    /**
     * aState : 1
     * date : 2016-10-01
     * editor : 张三
     * img : http://121.42.31.133:8201/m/img/4.png
     * sid : r40923
     * title : 小二心里健康培养
     * type : a01
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

    public static class ListBean {
        private String aState;
        private String date;
        private String editor;
        private String img;
        private String sid;
        private String title;
        private String type;
        private String viewCount;

        public String getAState() {
            return aState;
        }

        public void setAState(String aState) {
            this.aState = aState;
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

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getViewCount() {
            return viewCount;
        }

        public void setViewCount(String viewCount) {
            this.viewCount = viewCount;
        }
    }
}
