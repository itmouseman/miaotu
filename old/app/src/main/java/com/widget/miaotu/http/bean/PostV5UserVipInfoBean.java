package com.widget.miaotu.http.bean;

import java.util.List;

public class PostV5UserVipInfoBean {

    /**
     * userInfo : {"nickname":"会飞的企鹅","vipEndTime":null,"avatar":"http://imgs.miaoto.net/miaotuWxAppImg/default_avatar_green.png","isVIP":0}
     * productInfo : [{"contactMobile":"13396578980","month":12,"price":1980,"id":10001,"title":"VIP年费会员"}]
     */

    private UserInfoBean userInfo;
    private List<ProductInfoBean> productInfo;

    @Override
    public String toString() {
        return "PostV5UserVipInfoBean{" +
                "userInfo=" + userInfo +
                ", productInfo=" + productInfo +
                '}';
    }

    public UserInfoBean getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoBean userInfo) {
        this.userInfo = userInfo;
    }

    public List<ProductInfoBean> getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(List<ProductInfoBean> productInfo) {
        this.productInfo = productInfo;
    }

    public static class UserInfoBean {
        /**
         * nickname : 会飞的企鹅
         * vipEndTime : null
         * avatar : http://imgs.miaoto.net/miaotuWxAppImg/default_avatar_green.png
         * isVIP : 0
         */

        private String nickname;
        private Object vipEndTime;
        private String avatar;
        private int isVIP;


        @Override
        public String toString() {
            return "UserInfoBean{" +
                    "nickname='" + nickname + '\'' +
                    ", vipEndTime=" + vipEndTime +
                    ", avatar='" + avatar + '\'' +
                    ", isVIP=" + isVIP +
                    '}';
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public Object getVipEndTime() {
            return vipEndTime;
        }

        public void setVipEndTime(Object vipEndTime) {
            this.vipEndTime = vipEndTime;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getIsVIP() {
            return isVIP;
        }

        public void setIsVIP(int isVIP) {
            this.isVIP = isVIP;
        }
    }

    public static class ProductInfoBean {
        /**
         * contactMobile : 13396578980
         * month : 12
         * price : 1980
         * id : 10001
         * title : VIP年费会员
         */

        private String contactMobile;
        private int month;
        private int price;
        private int id;
        private String title;


        @Override
        public String toString() {
            return "ProductInfoBean{" +
                    "contactMobile='" + contactMobile + '\'' +
                    ", month=" + month +
                    ", price=" + price +
                    ", id=" + id +
                    ", title='" + title + '\'' +
                    '}';
        }

        public String getContactMobile() {
            return contactMobile;
        }

        public void setContactMobile(String contactMobile) {
            this.contactMobile = contactMobile;
        }

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
