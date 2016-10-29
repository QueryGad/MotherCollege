package com.player.mothercollege.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/29.
 */
public class FastBean {
    /**
     * lastIndex : 6
     * qustions : [{"asw_content":"我是回答内容，我是回答内容，我是回答内容，我是回答内容，我是回答内容，我是回答内容，我是回答内容，我是回答内容","asw_uicon":"http://121.42.31.133:8201/m/face/faA085[1].bmp","asw_uid":"U4559","asw_unicename":"答题人昵称","date":"2016-10-29 10:19","qid":"1","qusition":"这里是问题的内容、这里是问题的内容、这里是问题的内容、这里是问题的内容、这里是问题的内容、这里是问题的内容","reviewCount":999,"title":"我是问题的标题","uicon":"http://121.42.31.133:8201/m/face/faA099[1].bmp","uid":"u4096","unicename":"宝东"},{"asw_content":"我是回答内容，我是回答内容，我是回答内容，我是回答内容，我是回答内容，我是回答内容，我是回答内容，我是回答内容","asw_uicon":"http://121.42.31.133:8201/m/face/faA065[1].bmp","asw_uid":"U4559","asw_unicename":"答题人昵称","date":"2016-10-29 10:19","qid":"1","qusition":"这里是问题的内容、这里是问题的内容、这里是问题的内容、这里是问题的内容、这里是问题的内容、这里是问题的内容","reviewCount":999,"title":"我是问题的标题","uicon":"http://121.42.31.133:8201/m/face/faA031[1].bmp","uid":"u4096","unicename":"听雨人生"},{"asw_content":"我是回答内容，我是回答内容，我是回答内容，我是回答内容，我是回答内容，我是回答内容，我是回答内容，我是回答内容","asw_uicon":"http://121.42.31.133:8201/m/face/faA022[1].bmp","asw_uid":"U4559","asw_unicename":"答题人昵称","date":"2016-10-29 10:19","qid":"1","qusition":"这里是问题的内容、这里是问题的内容、这里是问题的内容、这里是问题的内容、这里是问题的内容、这里是问题的内容","reviewCount":999,"title":"我是问题的标题","uicon":"http://121.42.31.133:8201/m/face/faA015[1].bmp","uid":"u4096","unicename":"Agnes"},{"asw_content":"我是回答内容，我是回答内容，我是回答内容，我是回答内容，我是回答内容，我是回答内容，我是回答内容，我是回答内容","asw_uicon":"http://121.42.31.133:8201/m/face/faA066[1].bmp","asw_uid":"U4559","asw_unicename":"答题人昵称","date":"2016-10-29 10:19","qid":"1","qusition":"这里是问题的内容、这里是问题的内容、这里是问题的内容、这里是问题的内容、这里是问题的内容、这里是问题的内容","reviewCount":999,"title":"我是问题的标题","uicon":"http://121.42.31.133:8201/m/face/faA100[1].bmp","uid":"u4096","unicename":"快乐就好"},{"asw_content":"我是回答内容，我是回答内容，我是回答内容，我是回答内容，我是回答内容，我是回答内容，我是回答内容，我是回答内容","asw_uicon":"http://121.42.31.133:8201/m/face/faA010[1].bmp","asw_uid":"U4559","asw_unicename":"答题人昵称","date":"2016-10-29 10:19","qid":"1","qusition":"这里是问题的内容、这里是问题的内容、这里是问题的内容、这里是问题的内容、这里是问题的内容、这里是问题的内容","reviewCount":999,"title":"我是问题的标题","uicon":"http://121.42.31.133:8201/m/face/faA040[1].bmp","uid":"u4096","unicename":"宝东"},{"asw_content":"我是回答内容，我是回答内容，我是回答内容，我是回答内容，我是回答内容，我是回答内容，我是回答内容，我是回答内容","asw_uicon":"http://121.42.31.133:8201/m/face/faA012[1].bmp","asw_uid":"U4559","asw_unicename":"答题人昵称","date":"2016-10-29 10:19","qid":"1","qusition":"这里是问题的内容、这里是问题的内容、这里是问题的内容、这里是问题的内容、这里是问题的内容、这里是问题的内容","reviewCount":999,"title":"我是问题的标题","uicon":"http://121.42.31.133:8201/m/face/faA006[1].bmp","uid":"u4096","unicename":"吊儿郎当"}]
     */

    private int lastIndex;
    /**
     * asw_content : 我是回答内容，我是回答内容，我是回答内容，我是回答内容，我是回答内容，我是回答内容，我是回答内容，我是回答内容
     * asw_uicon : http://121.42.31.133:8201/m/face/faA085[1].bmp
     * asw_uid : U4559
     * asw_unicename : 答题人昵称
     * date : 2016-10-29 10:19
     * qid : 1
     * qusition : 这里是问题的内容、这里是问题的内容、这里是问题的内容、这里是问题的内容、这里是问题的内容、这里是问题的内容
     * reviewCount : 999
     * title : 我是问题的标题
     * uicon : http://121.42.31.133:8201/m/face/faA099[1].bmp
     * uid : u4096
     * unicename : 宝东
     */

    private List<QustionsBean> qustions;

    public int getLastIndex() {
        return lastIndex;
    }

    public void setLastIndex(int lastIndex) {
        this.lastIndex = lastIndex;
    }

    public List<QustionsBean> getQustions() {
        return qustions;
    }

    public void setQustions(List<QustionsBean> qustions) {
        this.qustions = qustions;
    }

    public static class QustionsBean {
        private String asw_content;
        private String asw_uicon;
        private String asw_uid;
        private String asw_unicename;
        private String date;
        private String qid;
        private String qusition;
        private int reviewCount;
        private String title;
        private String uicon;
        private String uid;
        private String unicename;

        public String getAsw_content() {
            return asw_content;
        }

        public void setAsw_content(String asw_content) {
            this.asw_content = asw_content;
        }

        public String getAsw_uicon() {
            return asw_uicon;
        }

        public void setAsw_uicon(String asw_uicon) {
            this.asw_uicon = asw_uicon;
        }

        public String getAsw_uid() {
            return asw_uid;
        }

        public void setAsw_uid(String asw_uid) {
            this.asw_uid = asw_uid;
        }

        public String getAsw_unicename() {
            return asw_unicename;
        }

        public void setAsw_unicename(String asw_unicename) {
            this.asw_unicename = asw_unicename;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getQid() {
            return qid;
        }

        public void setQid(String qid) {
            this.qid = qid;
        }

        public String getQusition() {
            return qusition;
        }

        public void setQusition(String qusition) {
            this.qusition = qusition;
        }

        public int getReviewCount() {
            return reviewCount;
        }

        public void setReviewCount(int reviewCount) {
            this.reviewCount = reviewCount;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUicon() {
            return uicon;
        }

        public void setUicon(String uicon) {
            this.uicon = uicon;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getUnicename() {
            return unicename;
        }

        public void setUnicename(String unicename) {
            this.unicename = unicename;
        }
    }
}
