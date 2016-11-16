package com.player.mothercollege.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/11/16.
 */
public class CityBean {
    /**
     * city : 郑州
     * key : zhengzhou
     */

    private List<CitysBean> citys;

    public List<CitysBean> getCitys() {
        return citys;
    }

    public void setCitys(List<CitysBean> citys) {
        this.citys = citys;
    }

    public static class CitysBean {
        private String city;
        private String key;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }
    }
}
