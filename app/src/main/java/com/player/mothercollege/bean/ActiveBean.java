package com.player.mothercollege.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/11/16.
 */
public class ActiveBean {
    /**
     * lastIndex : 5
     * Actives : [{"aid":"ACT1612050001","isTop":true,"img":"http://121.42.31.133:8201/upload/image/20161205/20161205142424_2408.jpg","title":"如何培养孩子的好习惯","city":"郑州","joinCount":0},{"aid":"ACT1612050003","isTop":true,"img":"http://121.42.31.133:8201/upload/image/20161205/20161205144529_2624.jpg","title":"家庭教育讲师班","city":"武汉","joinCount":0},{"aid":"ACT1612010001","isTop":false,"img":"http://121.42.31.133:8201/upload/image/20161201/20161201114712_4902.jpg","title":"郝大鹏老师《好父母成就孩子的一生》","city":"郑州","joinCount":0},{"aid":"ACT1612050002","isTop":false,"img":"http://121.42.31.133:8201/upload/image/20161205/20161205144308_6833.jpg","title":"好父母成就孩子一生","city":"南京","joinCount":0},{"aid":"ACT1612050004","isTop":false,"img":"http://121.42.31.133:8201/upload/image/20161205/20161205145526_9224.jpg","title":"情绪财富","city":"广州","joinCount":0}]
     */

    private int lastIndex;
    /**
     * aid : ACT1612050001
     * isTop : true
     * img : http://121.42.31.133:8201/upload/image/20161205/20161205142424_2408.jpg
     * title : 如何培养孩子的好习惯
     * city : 郑州
     * joinCount : 0
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
        private String aid;
        private boolean isTop;
        private String img;
        private String title;
        private String city;
        private int joinCount;

        public String getAid() {
            return aid;
        }

        public void setAid(String aid) {
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
