package com.player.mothercollege.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/12.
 */
public class RecommBean {
    /**
     * index : 2
     * title : 穷样富养不如妈妈的教养
     * link : null
     * img : http://121.42.31.133:8201/upload/image/20161220/20161220172807_6602.jpg
     * rtype : 14
     * sid : ORI1612050001
     * vedioUrl : null
     */

    private List<BanerBean> baner;
    /**
     * sid : BKP1701030001
     * editor : 周正
     * title : 2.4成为校园小明星
     * img : http://121.42.31.133:8201/upload/image/20170103/20170103141618_5445.jpg
     * aState : 0
     * type : a01
     * date : 2017-01-03
     * url : null
     * viewCount : 118
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
        private String vedioUrl;

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

        public Object getLink() {
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

        public String getVedioUrl() {
            return vedioUrl;
        }

        public void setVedioUrl(String vedioUrl) {
            this.vedioUrl = vedioUrl;
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
        private String url;
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
    }
}
