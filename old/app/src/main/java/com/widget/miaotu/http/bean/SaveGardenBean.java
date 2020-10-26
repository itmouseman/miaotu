package com.widget.miaotu.http.bean;

public class SaveGardenBean {


    /**
     * token : 获取userId的token
     * companyAddress : 企业地址(若没有企业，第一次需要填写)
     * companyName : 企业名称(若没有企业，第一次需要填写)
     * gardenName : 苗圃名称
     * province : 苗圃所在省
     * city : 苗圃所在市
     * district : 苗圃所在区
     * addressDetail : 苗圃详细地址
     * area : 苗圃面积
     * contactOne : 联系人1
     * mobileOne : 联系人1手机号
     * contactTwo : 联系人2
     * mobileTwo : 联系人2手机号
     * contactThree : 联系人3
     * mobileThree : 联系人3手机号
     */

    private String token;
    private String companyAddress;
    private String companyName;
    private String gardenName;
    private String province;
    private String city;
    private String district;
    private String addressDetail;
    private String area;
    private String contactOne;
    private String mobileOne;
    private String contactTwo;
    private String mobileTwo;
    private String contactThree;
    private String mobileThree;

    private String companyId;
    private String id;

    public SaveGardenBean(String token, String companyAddress, String companyName, String gardenName, String province,
                          String city, String district, String addressDetail, String area, String contactOne,
                          String mobileOne, String contactTwo, String mobileTwo, String contactThree, String mobileThree
                          , String companyId, String id ) {
        this.token =token;
        this.companyAddress =companyAddress;
        this.companyName = companyName;
        this.gardenName =gardenName;
        this.province = province;
        this.city = city;
        this.district =district;
        this.addressDetail = addressDetail;
        this.area=area;
        this.contactOne = contactOne;
        this.mobileOne = mobileOne;
        this.contactTwo =contactTwo;
        this.mobileTwo = mobileTwo;
        this.contactThree=contactThree;
        this.mobileThree = mobileThree;
        this.companyId = companyId;
        this.id = id;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getGardenName() {
        return gardenName;
    }

    public void setGardenName(String gardenName) {
        this.gardenName = gardenName;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getContactOne() {
        return contactOne;
    }

    public void setContactOne(String contactOne) {
        this.contactOne = contactOne;
    }

    public String getMobileOne() {
        return mobileOne;
    }

    public void setMobileOne(String mobileOne) {
        this.mobileOne = mobileOne;
    }

    public String getContactTwo() {
        return contactTwo;
    }

    public void setContactTwo(String contactTwo) {
        this.contactTwo = contactTwo;
    }

    public String getMobileTwo() {
        return mobileTwo;
    }

    public void setMobileTwo(String mobileTwo) {
        this.mobileTwo = mobileTwo;
    }

    public String getContactThree() {
        return contactThree;
    }

    public void setContactThree(String contactThree) {
        this.contactThree = contactThree;
    }

    public String getMobileThree() {
        return mobileThree;
    }

    public void setMobileThree(String mobileThree) {
        this.mobileThree = mobileThree;
    }
}
