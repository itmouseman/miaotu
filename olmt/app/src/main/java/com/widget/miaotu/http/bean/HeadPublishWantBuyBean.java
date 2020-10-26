package com.widget.miaotu.http.bean;

public class HeadPublishWantBuyBean {
    private String seedlingId;//苗木名称id，当苗木名称是库中所选的，需要传
    private String seedlingName;//苗木名称，当自己所填的苗木名称，需要传
    private String wantBuyNum;//求购数量  必填
    private String province; //必填
    private String city;  //必填
    private String plantType;//种植类型
    private String quality;//品质要求
    private String spec;//规格 必填
    private String wantBuyUrls;//图片
    private String timeLimit;//求购时间；1：一天；7:7天；30：30天  必填
    private String describe;//求购描述
    private String name;   //必填
    private String phone;  //必填
    private String fromProvince;//产地省 必填
    private String fromCity;//产地市  必填


    public HeadPublishWantBuyBean(String seedlingName, String wantBuyNum, String province, String city, String plantType, String spec, String timeLimit, String name, String phone, String fromProvince, String fromCity) {
        this.seedlingName = seedlingName;
        this.wantBuyNum = wantBuyNum;
        this.province = province;
        this.city = city;
        this.spec = spec;
        this.timeLimit = timeLimit;
        this.name = name;
        this.phone = phone;
        this.plantType = plantType;
        this.fromProvince = fromProvince;
        this.fromCity = fromCity;
    }

    public HeadPublishWantBuyBean(String seedlingId, String seedlingName, String wantBuyNum, String province, String city, String plantType, String quality, String spec, String wantBuyUrls, String timeLimit, String describe, String name, String phone, String fromProvince, String fromCity) {
        this.seedlingId = seedlingId;
        this.seedlingName = seedlingName;
        this.wantBuyNum = wantBuyNum;
        this.province = province;
        this.city = city;
        this.plantType = plantType;
        this.quality = quality;
        this.spec = spec;
        this.wantBuyUrls = wantBuyUrls;
        this.timeLimit = timeLimit;
        this.describe = describe;
        this.name = name;
        this.phone = phone;
        this.fromProvince = fromProvince;
        this.fromCity = fromCity;
    }
}
