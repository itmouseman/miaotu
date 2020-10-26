package com.widget.miaotu.http.bean;

import java.util.List;

public class HerUserInfoBean {

    /**
     * address : 浙江 杭州
     * seedlingCount : 0
     * hasCommunity : 0
     * sex : 1
     * jobTitle : 苗老板
     * companyName : 罗汉松育苗基地
     * showPics : []
     * avatar : http://oss-cn-beijing.aliyuncs.com/miaotu1/ess-1456302341609588662.jpeg
     * userId : 88469
     * isVIP : 2
     * companyId : 6254
     * phone : 15825504757
     * inviteCode : EID6OV
     * nickname : 往
     * isPersonalAuth : 0
     * isFriend : 1
     * covers : [{"t_height":"1334","t_url":"http://imgs.miaoto.net/1c6ac2d445f2c0333e425d2750bfc9d8_index_0.png","t_width":"1000"},{"t_height":"1334","t_url":"http://imgs.miaoto.net/e11c746e8ae36992b56a2a98ebad09f2_index_1.png","t_width":"1000"}]
     */

    private String address;
    private int seedlingCount;
    private int hasCommunity;
    private int sex;
    private String jobTitle;
    private String companyName;
    private String avatar;
    private int userId;
    private int isVIP;
    private String companyId;
    private String phone;
    private String inviteCode;
    private String nickname;
    private int isPersonalAuth;
    private int isFriend;
    private String covers;
    private List<?> showPics;

    @Override
    public String toString() {
        return "HerUserInfoBean{" +
                "address='" + address + '\'' +
                ", seedlingCount=" + seedlingCount +
                ", hasCommunity=" + hasCommunity +
                ", sex=" + sex +
                ", jobTitle='" + jobTitle + '\'' +
                ", companyName='" + companyName + '\'' +
                ", avatar='" + avatar + '\'' +
                ", userId=" + userId +
                ", isVIP=" + isVIP +
                ", companyId='" + companyId + '\'' +
                ", phone='" + phone + '\'' +
                ", inviteCode='" + inviteCode + '\'' +
                ", nickname='" + nickname + '\'' +
                ", isPersonalAuth=" + isPersonalAuth +
                ", isFriend=" + isFriend +
                ", covers='" + covers + '\'' +
                ", showPics=" + showPics +
                '}';
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getSeedlingCount() {
        return seedlingCount;
    }

    public void setSeedlingCount(int seedlingCount) {
        this.seedlingCount = seedlingCount;
    }

    public int getHasCommunity() {
        return hasCommunity;
    }

    public void setHasCommunity(int hasCommunity) {
        this.hasCommunity = hasCommunity;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public int getIsVIP() {
        return isVIP;
    }

    public void setIsVIP(int isVIP) {
        this.isVIP = isVIP;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getIsPersonalAuth() {
        return isPersonalAuth;
    }

    public void setIsPersonalAuth(int isPersonalAuth) {
        this.isPersonalAuth = isPersonalAuth;
    }

    public int getIsFriend() {
        return isFriend;
    }

    public void setIsFriend(int isFriend) {
        this.isFriend = isFriend;
    }

    public String getCovers() {
        return covers;
    }

    public void setCovers(String covers) {
        this.covers = covers;
    }

    public List<?> getShowPics() {
        return showPics;
    }

    public void setShowPics(List<?> showPics) {
        this.showPics = showPics;
    }
}
