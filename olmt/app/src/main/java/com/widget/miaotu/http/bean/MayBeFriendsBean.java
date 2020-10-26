package com.widget.miaotu.http.bean;

public class MayBeFriendsBean {
    /**
     * 		"headUrl":"http://imgs.miaoto.net/header/header_1558541793703234240.png?x-oss-process=style/thumb_288_288",
     *     			"isFriend":0,
     *     			"nickname":"崔",
     *     			"phone":"13918365754",
     *     			"userId":73385
     */

    private String headUrl;
    private int isFriend;
    private String nickname;
    private String phone;
    private  String userId;


    @Override
    public String toString() {
        return "MayBeFriendsBean{" +
                "headUrl='" + headUrl + '\'' +
                ", isFriend=" + isFriend +
                ", nickname='" + nickname + '\'' +
                ", phone='" + phone + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public int getIsFriend() {
        return isFriend;
    }

    public void setIsFriend(int isFriend) {
        this.isFriend = isFriend;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
