package com.player.mothercollege.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/10/26.
 */
public class PersonDynamicBean {
    /**
     * lastIndex : 10
     * trends : [{"content":"我在写内容","datetime":"2016-12-01 15:54:24","from":"90后的空间","hasKeep":false,"hasLike":false,"pics":["http://121.42.31.133:8201/AppUserSpace/U00000001/images/UserTrends/20161201/20161201155423_87700.png"],"reviews":[],"tid":"89","title":"我是标题","ttype":1,"uicon":"http://121.42.31.133:8201/AppUserSpace/U00000001/images/UserFace/62e55c81-72d5-4dc3-b170-6530d01beeb9.png","uid":"U00000001","uniceName":"哈阿啊","uvipType":0,"viewCount":31,"zlikes":[]},{"content":"你好啊","datetime":"2016-11-25 18:47:02","from":"家有宝宝","hasKeep":false,"hasLike":false,"pics":["http://121.42.31.133:8201/AppUserSpace/U00000001/images/UserTrends/20161125/20161125184702_42040.png","http://121.42.31.133:8201/AppUserSpace/U00000001/images/UserTrends/20161125/20161125184702_54541.png","http://121.42.31.133:8201/AppUserSpace/U00000001/images/UserTrends/20161125/20161125184702_68602.png"],"reviews":[],"tid":"64","title":"你好","ttype":1,"uicon":"http://121.42.31.133:8201/AppUserSpace/U00000001/images/UserFace/62e55c81-72d5-4dc3-b170-6530d01beeb9.png","uid":"U00000001","uniceName":"哈阿啊","uvipType":0,"viewCount":0,"zlikes":[]},{"content":"我要做第一次测试！","datetime":"2016-11-25 15:09:08","from":"家有宝宝","hasKeep":false,"hasLike":false,"pics":["http://121.42.31.133:8201/AppUserSpace/U00000001/images/UserTrends/20161125/20161125150907_68050.jpg","http://121.42.31.133:8201/AppUserSpace/U00000001/images/UserTrends/20161125/20161125150907_99301.png","http://121.42.31.133:8201/AppUserSpace/U00000001/images/UserTrends/20161125/20161125150908_00862.jpg"],"reviews":[],"tid":"55","title":"我是安卓","ttype":1,"uicon":"http://121.42.31.133:8201/AppUserSpace/U00000001/images/UserFace/62e55c81-72d5-4dc3-b170-6530d01beeb9.png","uid":"U00000001","uniceName":"哈阿啊","uvipType":0,"viewCount":0,"zlikes":[]},{"content":"被窝","datetime":"2016-11-25 10:54:16","from":"家有宝宝","hasKeep":false,"hasLike":false,"pics":["http://121.42.31.133:8201/AppUserSpace/U00000001/images/UserTrends/20161125/20161125105416_46550.png","http://121.42.31.133:8201/AppUserSpace/U00000001/images/UserTrends/20161125/20161125105416_79361.png","http://121.42.31.133:8201/AppUserSpace/U00000001/images/UserTrends/20161125/20161125105416_93422.png","http://121.42.31.133:8201/AppUserSpace/U00000001/images/UserTrends/20161125/20161125105416_94983.jpg"],"reviews":[],"tid":"42","title":"老爷子","ttype":1,"uicon":"http://121.42.31.133:8201/AppUserSpace/U00000001/images/UserFace/62e55c81-72d5-4dc3-b170-6530d01beeb9.png","uid":"U00000001","uniceName":"哈阿啊","uvipType":0,"viewCount":0,"zlikes":[]},{"content":"控制","datetime":"2016-11-25 10:22:42","from":"家有宝宝","hasKeep":false,"hasLike":false,"pics":["http://121.42.31.133:8201/AppUserSpace/U00000001/images/UserTrends/20161125/20161125102242_19790.png"],"reviews":[],"tid":"41","title":"","ttype":1,"uicon":"http://121.42.31.133:8201/AppUserSpace/U00000001/images/UserFace/62e55c81-72d5-4dc3-b170-6530d01beeb9.png","uid":"U00000001","uniceName":"哈阿啊","uvipType":0,"viewCount":0,"zlikes":[]},{"content":"俺有","datetime":"2016-11-25 10:16:35","from":"家有宝宝","hasKeep":false,"hasLike":false,"pics":["http://121.42.31.133:8201/AppUserSpace/U00000001/images/UserTrends/20161125/20161125101635_57160.jpg"],"reviews":[],"tid":"40","title":"Knoxville","ttype":1,"uicon":"http://121.42.31.133:8201/AppUserSpace/U00000001/images/UserFace/62e55c81-72d5-4dc3-b170-6530d01beeb9.png","uid":"U00000001","uniceName":"哈阿啊","uvipType":0,"viewCount":0,"zlikes":[]},{"content":"mood","datetime":"2016-11-25 10:03:12","from":"家有宝宝","hasKeep":false,"hasLike":false,"pics":["http://121.42.31.133:8201/AppUserSpace/U00000001/images/UserTrends/20161125/20161125100312_14720.png"],"reviews":[],"tid":"39","title":"","ttype":1,"uicon":"http://121.42.31.133:8201/AppUserSpace/U00000001/images/UserFace/62e55c81-72d5-4dc3-b170-6530d01beeb9.png","uid":"U00000001","uniceName":"哈阿啊","uvipType":0,"viewCount":0,"zlikes":[]},{"content":"alumni","datetime":"2016-11-25 10:02:23","from":"家有宝宝","hasKeep":false,"hasLike":false,"pics":["http://121.42.31.133:8201/AppUserSpace/U00000001/images/UserTrends/20161125/20161125100223_78780.jpg"],"reviews":[],"tid":"38","title":"","ttype":1,"uicon":"http://121.42.31.133:8201/AppUserSpace/U00000001/images/UserFace/62e55c81-72d5-4dc3-b170-6530d01beeb9.png","uid":"U00000001","uniceName":"哈阿啊","uvipType":0,"viewCount":0,"zlikes":[]},{"content":"李玉婷","datetime":"2016-11-25 10:01:43","from":"家有宝宝","hasKeep":false,"hasLike":false,"pics":["http://121.42.31.133:8201/AppUserSpace/U00000001/images/UserTrends/20161125/20161125100143_60030.png"],"reviews":[],"tid":"37","title":"","ttype":1,"uicon":"http://121.42.31.133:8201/AppUserSpace/U00000001/images/UserFace/62e55c81-72d5-4dc3-b170-6530d01beeb9.png","uid":"U00000001","uniceName":"哈阿啊","uvipType":0,"viewCount":0,"zlikes":[]},{"content":"恶魔与","datetime":"2016-11-25 09:53:33","from":"家有宝宝","hasKeep":false,"hasLike":false,"pics":["http://121.42.31.133:8201/AppUserSpace/U00000001/images/UserTrends/20161125/20161125095333_44410.png"],"reviews":[],"tid":"36","title":"","ttype":1,"uicon":"http://121.42.31.133:8201/AppUserSpace/U00000001/images/UserFace/62e55c81-72d5-4dc3-b170-6530d01beeb9.png","uid":"U00000001","uniceName":"哈阿啊","uvipType":0,"viewCount":0,"zlikes":[]}]
     */

    private int lastIndex;
    /**
     * content : 我在写内容
     * datetime : 2016-12-01 15:54:24
     * from : 90后的空间
     * hasKeep : false
     * hasLike : false
     * pics : ["http://121.42.31.133:8201/AppUserSpace/U00000001/images/UserTrends/20161201/20161201155423_87700.png"]
     * reviews : []
     * tid : 89
     * title : 我是标题
     * ttype : 1
     * uicon : http://121.42.31.133:8201/AppUserSpace/U00000001/images/UserFace/62e55c81-72d5-4dc3-b170-6530d01beeb9.png
     * uid : U00000001
     * uniceName : 哈阿啊
     * uvipType : 0
     * viewCount : 31
     * zlikes : []
     */

    private List<TrendsBean> trends;

    public int getLastIndex() {
        return lastIndex;
    }

    public void setLastIndex(int lastIndex) {
        this.lastIndex = lastIndex;
    }

    public List<TrendsBean> getTrends() {
        return trends;
    }

    public void setTrends(List<TrendsBean> trends) {
        this.trends = trends;
    }

    public static class TrendsBean {
        private String content;
        private String datetime;
        private String from;
        private boolean hasKeep;
        private boolean hasLike;
        private String tid;
        private String title;
        private int ttype;
        private String uicon;
        private String uid;
        private String uniceName;
        private int uvipType;
        private int viewCount;
        private List<String> pics;
        private List<ReviewsBean> reviews;
        private List<ZlikesBean> zlikes;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getDatetime() {
            return datetime;
        }

        public void setDatetime(String datetime) {
            this.datetime = datetime;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public boolean isHasKeep() {
            return hasKeep;
        }

        public void setHasKeep(boolean hasKeep) {
            this.hasKeep = hasKeep;
        }

        public boolean isHasLike() {
            return hasLike;
        }

        public void setHasLike(boolean hasLike) {
            this.hasLike = hasLike;
        }

        public String getTid() {
            return tid;
        }

        public void setTid(String tid) {
            this.tid = tid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getTtype() {
            return ttype;
        }

        public void setTtype(int ttype) {
            this.ttype = ttype;
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

        public String getUniceName() {
            return uniceName;
        }

        public void setUniceName(String uniceName) {
            this.uniceName = uniceName;
        }

        public int getUvipType() {
            return uvipType;
        }

        public void setUvipType(int uvipType) {
            this.uvipType = uvipType;
        }

        public int getViewCount() {
            return viewCount;
        }

        public void setViewCount(int viewCount) {
            this.viewCount = viewCount;
        }

        public List<String> getPics() {
            return pics;
        }

        public void setPics(List<String> pics) {
            this.pics = pics;
        }

        public List<ReviewsBean> getReviews() {
            return reviews;
        }

        public void setReviews(List<ReviewsBean> reviews) {
            this.reviews = reviews;
        }

        public List<ZlikesBean> getZlikes() {
            return zlikes;
        }

        public void setZlikes(List<ZlikesBean> zlikes) {
            this.zlikes = zlikes;
        }

        public static class ReviewsBean implements Serializable {
            private String uid;
            private String content;
            private String index;
            private int rid;
            private int rbodyID;
            private String uicon;
            private String unicename;
            private String date;

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getIndex() {
                return index;
            }

            public void setIndex(String index) {
                this.index = index;
            }

            public int getRid() {
                return rid;
            }

            public void setRid(int rid) {
                this.rid = rid;
            }

            public int getRbodyID() {
                return rbodyID;
            }

            public void setRbodyID(int rbodyID) {
                this.rbodyID = rbodyID;
            }

            public String getUicon() {
                return uicon;
            }

            public void setUicon(String uicon) {
                this.uicon = uicon;
            }

            public String getUnicename() {
                return unicename;
            }

            public void setUnicename(String unicename) {
                this.unicename = unicename;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }
        }

        public static class ZlikesBean implements Serializable{
            private int zindex;
            private String uid;
            private String uicon;
            private String unicename;

            public int getZindex() {
                return zindex;
            }

            public void setZindex(int zindex) {
                this.zindex = zindex;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getUicon() {
                return uicon;
            }

            public void setUicon(String uicon) {
                this.uicon = uicon;
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
