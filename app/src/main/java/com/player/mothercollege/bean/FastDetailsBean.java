package com.player.mothercollege.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/11/1.
 */
public class FastDetailsBean {
    /**
     * answer : [{"aid":1,"answer":"我是回答的内容。我是回答的内容。我是回答的内容。我是回答的内容。我是回答的内容。我是回答的内容。我是回答的内容。我是回答的内容。我是回答的内容。","date":"2016-11-01 10:59","qid":1,"reviews":[{"content":"哈哈","date":"2016-11-09-03 22:09","index":"0","rbodyID":0,"rid":4354,"uicon":"http://121.42.31.133:8201/m/face/faA066[1].bmp","uid":"SF3494","unicename":"中桃小鹿"},{"content":"你的文章太好了，我喜欢","date":"2016-11-09-03 22:09","index":"1","rbodyID":0,"rid":4354,"uicon":"http://121.42.31.133:8201/m/face/faA032[1].bmp","uid":"SF3494","unicename":"快乐就好"}],"uicon":"http://121.42.31.133:8201/m/face/faA003[1].bmp","uid":"u34566","unicename":"听雨人生"},{"aid":1,"answer":"我是回答的内容。我是回答的内容。我是回答的内容。我是回答的内容。我是回答的内容。我是回答的内容。我是回答的内容。我是回答的内容。我是回答的内容。","date":"2016-11-01 10:59","qid":1,"reviews":[{"content":"我能联系到他，你私聊我","date":"2016-11-09-03 22:09","index":"0","rbodyID":0,"rid":4354,"uicon":"http://121.42.31.133:8201/m/face/faA069[1].bmp","uid":"SF3494","unicename":"起航.."},{"content":"你家宝宝真可爱","date":"2016-11-09-03 22:09","index":"1","rbodyID":0,"rid":4354,"uicon":"http://121.42.31.133:8201/m/face/faA053[1].bmp","uid":"SF3494","unicename":"柠檬香味"}],"uicon":"http://121.42.31.133:8201/m/face/faA053[1].bmp","uid":"u34566","unicename":"听雨人生"}]
     * date : 2016-11-01 10:59
     * qid : 4
     * qusition : 我是问题详细描述,我是问题详细描述,我是问题详细描述,我是问题详细描述,我是问题详细描述,我是问题详细描述,
     * title : 我是问题标题
     * uicon : http://121.42.31.133:8201/m/face/faA052[1].bmp
     * uid : u4096
     * unicename : 吊儿郎当
     * viewCount : 999
     */

    private String date;
    private String qid;
    private String qusition;
    private String title;
    private String uicon;
    private String uid;
    private String unicename;
    private int viewCount;
    /**
     * aid : 1
     * answer : 我是回答的内容。我是回答的内容。我是回答的内容。我是回答的内容。我是回答的内容。我是回答的内容。我是回答的内容。我是回答的内容。我是回答的内容。
     * date : 2016-11-01 10:59
     * qid : 1
     * reviews : [{"content":"哈哈","date":"2016-11-09-03 22:09","index":"0","rbodyID":0,"rid":4354,"uicon":"http://121.42.31.133:8201/m/face/faA066[1].bmp","uid":"SF3494","unicename":"中桃小鹿"},{"content":"你的文章太好了，我喜欢","date":"2016-11-09-03 22:09","index":"1","rbodyID":0,"rid":4354,"uicon":"http://121.42.31.133:8201/m/face/faA032[1].bmp","uid":"SF3494","unicename":"快乐就好"}]
     * uicon : http://121.42.31.133:8201/m/face/faA003[1].bmp
     * uid : u34566
     * unicename : 听雨人生
     */

    private List<AnswerBean> answer;

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

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public List<AnswerBean> getAnswer() {
        return answer;
    }

    public void setAnswer(List<AnswerBean> answer) {
        this.answer = answer;
    }

    public static class AnswerBean {
        private int aid;
        private String answer;
        private String date;
        private int qid;
        private String uicon;
        private String uid;
        private String unicename;
        /**
         * content : 哈哈
         * date : 2016-11-09-03 22:09
         * index : 0
         * rbodyID : 0
         * rid : 4354
         * uicon : http://121.42.31.133:8201/m/face/faA066[1].bmp
         * uid : SF3494
         * unicename : 中桃小鹿
         */

        private List<ReviewsBean> reviews;

        public int getAid() {
            return aid;
        }

        public void setAid(int aid) {
            this.aid = aid;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public int getQid() {
            return qid;
        }

        public void setQid(int qid) {
            this.qid = qid;
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

        public List<ReviewsBean> getReviews() {
            return reviews;
        }

        public void setReviews(List<ReviewsBean> reviews) {
            this.reviews = reviews;
        }

        public static class ReviewsBean {
            private String content;
            private String date;
            private String index;
            private int rbodyID;
            private int rid;
            private String uicon;
            private String uid;
            private String unicename;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getIndex() {
                return index;
            }

            public void setIndex(String index) {
                this.index = index;
            }

            public int getRbodyID() {
                return rbodyID;
            }

            public void setRbodyID(int rbodyID) {
                this.rbodyID = rbodyID;
            }

            public int getRid() {
                return rid;
            }

            public void setRid(int rid) {
                this.rid = rid;
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
}
