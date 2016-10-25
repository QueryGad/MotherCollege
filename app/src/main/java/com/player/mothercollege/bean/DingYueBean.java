package com.player.mothercollege.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/18.
 */
public class DingYueBean {
    /**
     * currentPageSize : 5
     * lastIndex : 5
     * myPayClass : [{"date":"2016-10-18","editor":"斯琴高娃","img":"http://121.42.31.133:8201/m/img/8.png","sid":"s00335","title":"端正孩子的从众心里","viewCount":972},{"date":"2016-10-18","editor":"李峰","img":"http://121.42.31.133:8201/m/img/8.png","sid":"s00353","title":"亲子解读","viewCount":275},{"date":"2016-10-18","editor":"孙正茂","img":"http://121.42.31.133:8201/m/img/3.png","sid":"s00915","title":"最美中国妈妈","viewCount":961},{"date":"2016-10-18","editor":"曾丽友","img":"http://121.42.31.133:8201/m/img/5.png","sid":"s00209","title":"心理营养大餐","viewCount":533},{"date":"2016-10-18","editor":"万长春","img":"http://121.42.31.133:8201/m/img/1.png","sid":"s00705","title":"心理营养大餐","viewCount":50}]
     */

    private int currentPageSize;
    private int lastIndex;
    /**
     * date : 2016-10-18
     * editor : 斯琴高娃
     * img : http://121.42.31.133:8201/m/img/8.png
     * sid : s00335
     * title : 端正孩子的从众心里
     * viewCount : 972
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
        private String date;
        private String editor;
        private String img;
        private String sid;
        private String title;
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

        public int getViewCount() {
            return viewCount;
        }

        public void setViewCount(int viewCount) {
            this.viewCount = viewCount;
        }
    }
}
