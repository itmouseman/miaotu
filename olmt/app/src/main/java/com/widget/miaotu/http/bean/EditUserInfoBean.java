package com.widget.miaotu.http.bean;

public class EditUserInfoBean {
    String nickname;// 昵称 - 可选
    String sex;//  性别 - 可选
    String avatar;// 头像 - 可选
    String company;// 所属公司 - 可选
    String jobTitle;//职称
//    String phone;//手机号

    public EditUserInfoBean(String avatar, String nickname, String sex, String company, String jobTitle) {
        this.avatar = avatar;
        this.nickname = nickname;
        this.sex = sex;
        this.company = company;
        this.jobTitle = jobTitle;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

}
