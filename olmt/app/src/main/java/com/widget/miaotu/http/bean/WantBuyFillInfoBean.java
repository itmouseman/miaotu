package com.widget.miaotu.http.bean;

public class WantBuyFillInfoBean {


    /**
     * city : 不限
     * fromCity : 天津市
     * fromProvince :
     * id : 2128
     * name : 贺杨
     * phone : 17706539351
     * province : 全国
     */

    private String city;
    private String fromCity;
    private String fromProvince;
    private int id;
    private String name;
    private String phone;
    private String province;


    @Override
    public String toString() {
        return "WantBuyFillInfoBean{" +
                "city='" + city + '\'' +
                ", fromCity='" + fromCity + '\'' +
                ", fromProvince='" + fromProvince + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", province='" + province + '\'' +
                '}';
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getFromCity() {
        return fromCity;
    }

    public void setFromCity(String fromCity) {
        this.fromCity = fromCity;
    }

    public String getFromProvince() {
        return fromProvince;
    }

    public void setFromProvince(String fromProvince) {
        this.fromProvince = fromProvince;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}
