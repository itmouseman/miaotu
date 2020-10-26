package com.widget.miaotu.http.bean;

public class AddFeedBackSeedBean {

    /**
     * user_id : 123
     * phone : 13454100020
     * feed_back_content : 提交的内容
     */

    private String image;
    private String phone;
    private String feed_back_content;


    public AddFeedBackSeedBean(String image, String phone, String feed_back_content) {
        this.image = image;
        this.phone = phone;
        this.feed_back_content = feed_back_content;
    }


    public String getimage() {
        return image;
    }


    public void setimage(String image) {
        this.image = image;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFeed_back_content() {
        return feed_back_content;
    }

    public void setFeed_back_content(String feed_back_content) {
        this.feed_back_content = feed_back_content;
    }
}

