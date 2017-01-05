package com.player.mothercollege.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/11/16.
 */
public class ActiveBean {
    /**
     * Actives : [{"aid":"ACT1612080005","city":"郑州","img":"http://121.42.31.133:8201/upload/image/20161212/20161212163617_9899.jpg","isTimeOver":false,"isTop":true,"joinCount":0,"title":"周正教授亲授：咨询专家孵化班"},{"aid":"ACT1612050002","city":"南京","img":"http://121.42.31.133:8201/upload/image/20161216/20161216150431_5576.jpg","isTimeOver":true,"isTop":false,"joinCount":3,"title":"好父母成就孩子一生"},{"aid":"ACT1612050003","city":"武汉","img":"http://121.42.31.133:8201/upload/image/20161216/20161216150043_5799.jpg","isTimeOver":true,"isTop":false,"joinCount":0,"title":"家庭教育讲师班"},{"aid":"ACT1612050004","city":"广州","img":"http://121.42.31.133:8201/upload/image/20161216/20161216145437_8287.jpg","isTimeOver":true,"isTop":false,"joinCount":0,"title":"情绪财富"},{"aid":"ACT1612080001","city":"郑州","img":"http://121.42.31.133:8201/upload/image/20161212/20161212163549_7527.jpg","isTimeOver":true,"isTop":false,"joinCount":3,"title":"卓越父母的潜意识修炼"},{"aid":"ACT1612080002","city":"郑州","img":"http://121.42.31.133:8201/upload/image/20161212/20161212163538_1188.jpg","isTimeOver":true,"isTop":false,"joinCount":2,"title":"好父母成就孩子一生child&you"},{"aid":"ACT1612080003","city":"郑州","img":"http://121.42.31.133:8201/upload/image/20161212/20161212163524_4257.jpg","isTimeOver":true,"isTop":false,"joinCount":0,"title":"《生命重塑》第四期"},{"aid":"ACT1612080004","city":"郑州","img":"http://121.42.31.133:8201/upload/image/20161212/20161212163503_3758.jpg","isTimeOver":true,"isTop":false,"joinCount":0,"title":"为人父母必修的十堂课"},{"aid":"ACT1612050001","city":"郑州","img":"http://121.42.31.133:8201/upload/image/20161205/20161205142424_2408.jpg","isTimeOver":true,"isTop":false,"joinCount":0,"title":"如何培养孩子的好习惯"}]
     * lastIndex : 9
     */

    private int lastIndex;
    /**
     * aid : ACT1612080005
     * city : 郑州
     * img : http://121.42.31.133:8201/upload/image/20161212/20161212163617_9899.jpg
     * isTimeOver : false
     * isTop : true
     * joinCount : 0
     * title : 周正教授亲授：咨询专家孵化班
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
        private String city;
        private String img;
        private boolean isTimeOver;
        private boolean isTop;
        private int joinCount;
        private String title;

        public String getAid() {
            return aid;
        }

        public void setAid(String aid) {
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

        public boolean isIsTimeOver() {
            return isTimeOver;
        }

        public void setIsTimeOver(boolean isTimeOver) {
            this.isTimeOver = isTimeOver;
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
