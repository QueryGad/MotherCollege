package com.player.mothercollege.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/31.
 */
public class ActivityBean {
    /**
     * Actives : [{"aid":1,"city":"郑州","img":"http://121.42.31.133:8201/m/img/6.png","isTop":true,"joinCount":999,"title":"活动标题，活动标题"},{"aid":1,"city":"郑州","img":"http://121.42.31.133:8201/m/img/3.png","isTop":true,"joinCount":999,"title":"活动标题，活动标题"},{"aid":1,"city":"郑州","img":"http://121.42.31.133:8201/m/img/3.png","isTop":true,"joinCount":999,"title":"活动标题，活动标题"},{"aid":1,"city":"郑州","img":"http://121.42.31.133:8201/m/img/5.png","isTop":true,"joinCount":999,"title":"活动标题，活动标题"},{"aid":1,"city":"郑州","img":"http://121.42.31.133:8201/m/img/4.png","isTop":true,"joinCount":999,"title":"活动标题，活动标题"},{"aid":1,"city":"郑州","img":"http://121.42.31.133:8201/m/img/7.png","isTop":true,"joinCount":999,"title":"活动标题，活动标题"}]
     * lastIndex : 6
     */

    private int lastIndex;
    /**
     * aid : 1
     * city : 郑州
     * img : http://121.42.31.133:8201/m/img/6.png
     * isTop : true
     * joinCount : 999
     * title : 活动标题，活动标题
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
        private String city;
        private String img;
        private boolean isTop;
        private int joinCount;
        private String title;

        public int getAid() {
            return aid;
        }

        public void setAid(int aid) {
            this.aid = aid;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public boolean isIsTop() {
            return isTop;
        }

        public void setIsTop(boolean isTop) {
            this.isTop = isTop;
        }

        public int getJoinCount() {
            return joinCount;
        }

        public void setJoinCount(int joinCount) {
            this.joinCount = joinCount;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
