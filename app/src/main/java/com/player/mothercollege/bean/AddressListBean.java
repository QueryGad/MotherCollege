package com.player.mothercollege.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/11/19.
 */
public class AddressListBean {
    /**
     * ad1 : 浙江省
     * ad2 : 温州市
     * ad3 : 龙湾区
     * adFull : 浙江省温州市龙湾区 旅途路一号
     * ad_ext : 旅途路一号
     * aid : VR16111701
     * isdefault : false
     * linkname : 王国维
     * linkphone : 18203627230
     */

    private List<MyAddressBean> myAddress;

    public List<MyAddressBean> getMyAddress() {
        return myAddress;
    }

    public void setMyAddress(List<MyAddressBean> myAddress) {
        this.myAddress = myAddress;
    }

    public static class MyAddressBean {
        private String ad1;
        private String ad2;
        private String ad3;
        private String adFull;
        private String ad_ext;
        private String aid;
        private boolean isdefault;
        private String linkname;
        private String linkphone;

        public String getAd1() {
            return ad1;
        }

        public void setAd1(String ad1) {
            this.ad1 = ad1;
        }

        public String getAd2() {
            return ad2;
        }

        public void setAd2(String ad2) {
            this.ad2 = ad2;
        }

        public String getAd3() {
            return ad3;
        }

        public void setAd3(String ad3) {
            this.ad3 = ad3;
        }

        public String getAdFull() {
            return adFull;
        }

        public void setAdFull(String adFull) {
            this.adFull = adFull;
        }

        public String getAd_ext() {
            return ad_ext;
        }

        public void setAd_ext(String ad_ext) {
            this.ad_ext = ad_ext;
        }

        public String getAid() {
            return aid;
        }

        public void setAid(String aid) {
            this.aid = aid;
        }

        public boolean isIsdefault() {
            return isdefault;
        }

        public void setIsdefault(boolean isdefault) {
            this.isdefault = isdefault;
        }

        public String getLinkname() {
            return linkname;
        }

        public void setLinkname(String linkname) {
            this.linkname = linkname;
        }

        public String getLinkphone() {
            return linkphone;
        }

        public void setLinkphone(String linkphone) {
            this.linkphone = linkphone;
        }
    }
}
