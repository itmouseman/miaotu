package com.widget.miaotu.http.bean.head;

import android.text.TextUtils;

public class HeadSearchDetailBean {
    private int status; //必传  0：搜索苗木；1：搜索企业；2：根据一级分类查询对应的登记苗木
    private String searchWord;//必传  搜索词
    private String classifyName;//分类名称
    private int page;//必传
    private int num;//必传
    private String lon;//必传 经度（当搜索苗木，status传0或者2时
    private String lat;//必传 经度（当搜索苗木，status传0或者2时

    private Number priority;//必传 优先搜索（0:会员优先；1：关注优先；2：最近距离；3：最新发布），当搜索苗木时需要传,默认会员优先

    private Number type;// 当搜索为类型时传（0：清货；1：精品；2：推广），筛选中选择标签传（0：清货；1：精品；选择不限不传），否则不传
    private Number diameterFloor;//	起始杆径
    private Number diameterUpper;//截止杆径
    private Number heightFloor;//起始高度
    private Number heightUpper;//	截止高度
    private Number crownFloor;//起始冠幅
    private Number crownUpper;//截止冠幅
    private String province;
    private String city;
    private String plantType;//	种植类型
    private Number companyStatus; //	企业状态（0：不限；1：已认证）



    public HeadSearchDetailBean(int status, String searchWord, String classifyName, int page, int num, String lon, String lat, Number priority, Number type, Number diameterFloor, Number diameterUpper, Number heightFloor, Number heightUpper, Number crownFloor, Number crownUpper, String province, String city, String plantType, Number companyStatus) {
        this.status = status;
        this.searchWord = searchWord;
        this.classifyName = classifyName;
        this.page = page;
        this.num = num;
        this.lon = lon;
        this.lat = lat;
        this.priority = priority;
        this.type = type;
        this.diameterFloor = diameterFloor;
        this.diameterUpper = diameterUpper;
        this.heightFloor = heightFloor;
        this.heightUpper = heightUpper;
        this.crownFloor = crownFloor;
        this.crownUpper = crownUpper;
        this.province = province;
        this.city = city;
        this.plantType = plantType;
        this.companyStatus = companyStatus;
    }

//
//    public HeadSearchDetailBean(Number status, String searchWord, String classifyName, Number page, Number num, String lon, String lat, String priority, int type, String diameterFloor, String diameterUpper, String heightFloor, String heightUpper, String crownFloor, String crownUpper, String province, String city, String plantType, int companyStatus) {
//        this.status = status;
//        if (TextUtils.isEmpty(searchWord)){
//            this.searchWord = searchWord;
//        }
//        if (TextUtils.isEmpty(classifyName)){
//            this.classifyName = classifyName;
//        }
//
//        this.page = page;
//        this.num = num;
//        this.lon = lon;
//        this.lat = lat;
//        if (TextUtils.isEmpty(priority)){
//            this.priority = priority;
//
//        }
//        if (TextUtils.isEmpty(priority)){
//            this.priority = priority;
//        }
//        if (type!=3){
//            this.type = type;
//        }
//        if (TextUtils.isEmpty(diameterFloor)){
//            this.diameterFloor = diameterFloor;
//
//        }
//        if (TextUtils.isEmpty(diameterUpper)){
//            this.diameterUpper = diameterUpper;
//        }
//        if (TextUtils.isEmpty(heightFloor)){
//            this.heightFloor = heightFloor;
//        }
//        if (TextUtils.isEmpty(heightUpper)){
//            this.heightUpper = heightUpper;
//        }
//
//        if (TextUtils.isEmpty(crownFloor)){
//            this.crownFloor = crownFloor;
//        }
//        if (TextUtils.isEmpty(crownUpper)){
//            this.crownUpper = crownUpper;
//        }
//        if (TextUtils.isEmpty(province)){
//            this.province = province;
//
//        }
//        if (TextUtils.isEmpty(city)){
//            this.city = city;
//        }
//        if (TextUtils.isEmpty(plantType)){
//            this.plantType = plantType;
//
//        }
//        if (companyStatus!=3){
//            this.companyStatus = companyStatus;
//        }
//
//    }
}
