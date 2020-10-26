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

    private int priority;//必传 优先搜索（0:会员优先；1：关注优先；2：最近距离；3：最新发布），当搜索苗木时需要传,默认会员优先

    private String type;// 当搜索为类型时传（0：清货；1：精品；2：推广），筛选中选择标签传（0：清货；1：精品；选择不限不传），否则不传
    private String diameterFloor;//	起始杆径
    private String diameterUpper;//截止杆径
    private String heightFloor;//起始高度
    private String heightUpper;//	截止高度
    private String crownFloor;//起始冠幅
    private String crownUpper;//截止冠幅
    private String province;
    private String city;
    private String plantType;//	种植类型
    private String companyStatus; //	企业状态（0：不限；1：已认证）



    public HeadSearchDetailBean(int status, String searchWord, String classifyName, int page, int num, String lon, String lat, int priority, String type, String diameterFloor, String diameterUpper, String heightFloor, String heightUpper, String crownFloor, String crownUpper, String province, String city, String plantType, String companyStatus) {
        this.status = status;
        this.searchWord = searchWord;
        if (!TextUtils.isEmpty(classifyName)){
            this.classifyName = classifyName;
        }
        this.page = page;
        this.num = num;
        this.lon = lon;
        this.lat = lat;
        this.priority = priority;

        if (!TextUtils.isEmpty(type)){
            this.type = type;
        }
        if (!TextUtils.isEmpty(diameterFloor)){
            this.diameterFloor = diameterFloor;

        }
        if (!TextUtils.isEmpty(diameterUpper)){
            this.diameterUpper = diameterUpper;
        }
        if (!TextUtils.isEmpty(heightFloor)){
            this.heightFloor = heightFloor;
        }
        if (!TextUtils.isEmpty(heightUpper)){
            this.heightUpper = heightUpper;
        }
        if (!TextUtils.isEmpty(crownFloor)){
            this.crownFloor = crownFloor;
        }
        if (!TextUtils.isEmpty(crownUpper)){
            this.crownUpper = crownUpper;
        }
        if (!TextUtils.isEmpty(province)){
            this.province = province;
        }
        if (!TextUtils.isEmpty(city)){
            this.city = city;
        }
        if (!TextUtils.isEmpty(plantType)){
            this.plantType = plantType;
        }
        if (!TextUtils.isEmpty(companyStatus)){
            this.companyStatus = companyStatus;
        }
    }

//
//    public HeadSearchDetailBean(String status, String searchWord, String classifyName, String page, String num, String lon, String lat, String priority, int type, String diameterFloor, String diameterUpper, String heightFloor, String heightUpper, String crownFloor, String crownUpper, String province, String city, String plantType, int companyStatus) {
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
