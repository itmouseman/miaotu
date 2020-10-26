package com.widget.miaotu.http.bean;

public class MsgVerifyBean {
    /**
     * {
     * "id": 1,
     * "userId": 73281,
     * "nickname": "哈哈哈",
     * "companyName": "鸬鸟苗圃",
     * "headUrl": "http://imgs.miaoto.net/miaotuWxAppImg/default_avatar_green.png",
     * "isPass": 1,
     * "looked": 1
     * }
     */
    private Number id;
    private Number userId;
    private String nickname;
    private String companyName;
    private String headUrl;
    private Number isPass;
    private Number looked;

    @Override
    public String toString() {
        return "MsgVerifyBean{" +
                "id=" + id +
                ", userId=" + userId +
                ", nickname='" + nickname + '\'' +
                ", companyName='" + companyName + '\'' +
                ", headUrl='" + headUrl + '\'' +
                ", isPass=" + isPass +
                ", looked=" + looked +
                '}';
    }

    public Number getId() {
        return id;
    }

    public void setId(Number id) {
        this.id = id;
    }

    public Number getUserId() {
        return userId;
    }

    public void setUserId(Number userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public Number getIsPass() {
        return isPass;
    }

    public void setIsPass(Number isPass) {
        this.isPass = isPass;
    }

    public Number getLooked() {
        return looked;
    }

    public void setLooked(Number looked) {
        this.looked = looked;
    }
}
