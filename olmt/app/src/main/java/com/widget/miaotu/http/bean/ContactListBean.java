package com.widget.miaotu.http.bean;

import java.io.Serializable;

public class ContactListBean implements Serializable {


    /**
     * phone : 15825504757
     * looked : 0
     * name : 李云
     * nickname : 往前
     * isFriend : 2
     * id : 1807
     * avatar : http://oss-cn-beijing.aliyuncs.com/miaotu1/ess-8793177486874795252.jpeg
     * userId : 88469
     */

    private String phone;
    private Object looked;
    private String name;
    private String nickname;
    private int isFriend;
    private int id;
    private String avatar;
    private int userId;

    @Override
    public String toString() {
        return "ContactBean{" +
                "phone='" + phone + '\'' +
                ", looked=" + looked +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                ", isFriend=" + isFriend +
                ", id=" + id +
                ", avatar='" + avatar + '\'' +
                ", userId=" + userId +
                '}';
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Object getLooked() {
        return looked;
    }

    public void setLooked(Object looked) {
        this.looked = looked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getIsFriend() {
        return isFriend;
    }

    public void setIsFriend(int isFriend) {
        this.isFriend = isFriend;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

}
