package com.player.mothercollege.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/29.
 */
public class ZhuanJiaBean {
    /**
     * experts : [{"add":"河南省郑州市","icon":"http://121.42.31.133:8201/m/face/faA004[1].bmp","id":"1","index":1,"info":"我是专家，这里是我的简介。。。。","name":"专家名字","price":"999元/小时"},{"add":"河南省郑州市","icon":"http://121.42.31.133:8201/m/face/faA007[1].bmp","id":"1","index":1,"info":"我是专家，这里是我的简介。。。。","name":"专家名字","price":"999元/小时"},{"add":"河南省郑州市","icon":"http://121.42.31.133:8201/m/face/faA010[1].bmp","id":"1","index":1,"info":"我是专家，这里是我的简介。。。。","name":"专家名字","price":"999元/小时"},{"add":"河南省郑州市","icon":"http://121.42.31.133:8201/m/face/faA024[1].bmp","id":"1","index":1,"info":"我是专家，这里是我的简介。。。。","name":"专家名字","price":"999元/小时"},{"add":"河南省郑州市","icon":"http://121.42.31.133:8201/m/face/faA045[1].bmp","id":"1","index":1,"info":"我是专家，这里是我的简介。。。。","name":"专家名字","price":"999元/小时"},{"add":"河南省郑州市","icon":"http://121.42.31.133:8201/m/face/faA050[1].bmp","id":"1","index":1,"info":"我是专家，这里是我的简介。。。。","name":"专家名字","price":"999元/小时"}]
     * lastIndex : 6
     */

    private int lastIndex;
    /**
     * add : 河南省郑州市
     * icon : http://121.42.31.133:8201/m/face/faA004[1].bmp
     * id : 1
     * index : 1
     * info : 我是专家，这里是我的简介。。。。
     * name : 专家名字
     * price : 999元/小时
     */

    private List<ExpertsBean> experts;

    public int getLastIndex() {
        return lastIndex;
    }

    public void setLastIndex(int lastIndex) {
        this.lastIndex = lastIndex;
    }

    public List<ExpertsBean> getExperts() {
        return experts;
    }

    public void setExperts(List<ExpertsBean> experts) {
        this.experts = experts;
    }

    public static class ExpertsBean {
        private String add;
        private String icon;
        private String id;
        private int index;
        private String info;
        private String name;
        private String price;

        public String getAdd() {
            return add;
        }

        public void setAdd(String add) {
            this.add = add;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }
}
