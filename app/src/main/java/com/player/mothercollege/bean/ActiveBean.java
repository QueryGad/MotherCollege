package com.player.mothercollege.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/11/16.
 */
public class ActiveBean {
    /**
     * lastIndex : 6
     * Actives : [{"aid":1,"isTop":true,"img":"http://121.42.31.133:8201/m/img/2.png","title":"活动标题，活动标题","city":"郑州","joinCount":999},{"aid":1,"isTop":true,"img":"http://121.42.31.133:8201/m/img/s5.jpg","title":"活动标题，活动标题","city":"郑州","joinCount":999},{"aid":1,"isTop":true,"img":"http://121.42.31.133:8201/m/img/6.png","title":"活动标题，活动标题","city":"郑州","joinCount":999},{"aid":1,"isTop":true,"img":"http://121.42.31.133:8201/m/img/2.png","title":"活动标题，活动标题","city":"郑州","joinCount":999},{"aid":1,"isTop":true,"img":"http://121.42.31.133:8201/m/img/6.png","title":"活动标题，活动标题","city":"郑州","joinCount":999},{"aid":1,"isTop":true,"img":"http://121.42.31.133:8201/m/img/1.png","title":"活动标题，活动标题","city":"郑州","joinCount":999}]
     */

    private int lastIndex;
    /**
     * aid : 1
     * isTop : true
     * img : http://121.42.31.133:8201/m/img/2.png
     * title : 活动标题，活动标题
     * city : 郑州
     * joinCount : 999
     */

    private List<ActivesBean> Actives;

    public int getLastIndex() {
        return lastIndex;
    }

    public void setLastIndex(int lastIndex) {
        this.lastIndex = lastIndex;
    }

    public List<ActivesBean> getActives() {
        return Actives;
    }

    public void setActives(List<ActivesBean> Actives) {
        this.Actives = Actives;
    }

    public static class ActivesBean {
        private int aid;
        private boolean isTop;
        private String img;
        private String title;
        private String city;
        private int joinCount;

        public int getAid() {
            return aid;
        }

        public void setAid(int aid) {
            this.aid = aid;
        }

        public boolean isIsTop() {
            return isTop;
        }

        public void setIsTop(boolean isTop) {
            this.isTop = isTop;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public int getJoinCount() {
            return joinCount;
        }

        public void setJoinCount(int joinCount) {
            this.joinCount = joinCount;
        }
    }
}
