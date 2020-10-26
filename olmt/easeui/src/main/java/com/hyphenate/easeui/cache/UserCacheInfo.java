package com.hyphenate.easeui.cache;

import com.google.gson.Gson;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


/**
 * 用户缓存信息类
 * 需要ormlite库支持
 * Created by Martin on 2017/4/24.
 */
@DatabaseTable(tableName="UserCacheInfo")
public class UserCacheInfo{

    @DatabaseField(generatedId=true)
    private int id;

    /*环信ID*/
    @DatabaseField(index = true)
    private String userId;

    /*昵称*/
    @DatabaseField
    private String nickName;

    /*头像*/
    @DatabaseField
    private String avatarUrl;

    /*数据过期时间*/
    @DatabaseField(canBeNull = false)
    private long expiredDate;

    /*备注*/
    @DatabaseField
    private String remark;

    /*性别*/
    @DatabaseField
    private int sex;

    // 必须顶一个无参数的构造函数，否则会报【virtual method】异常
    UserCacheInfo(){}

    /*将json字符串转换成model*/
    public static UserCacheInfo parse(String jsonStr) {
        return (new Gson()).fromJson(jsonStr, UserCacheInfo.class);
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public long getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(long expiredDate) {
        this.expiredDate = expiredDate;
    }

}