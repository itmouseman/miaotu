package com.widget.miaotu.http.bean;

public class SeedCompanyInfoBean {

//    address string
////    公司基本地址
////    addressDetail string
////    详细地址
////    business string
////    业务
////    contact string
////    联系人
////    contactMobile string
////    联系方式
////    introduction string
////    简介
////    logo string
////    LOGO
////    name string
////    公司名称
////    stylePhotos string
////    风采照片

    private String address;
    private String addressDetail;
    private String business;
    private String contact;
    private String contactMobile;
    private String introduction;
    private String logo;
    private String name;
    private String stylePhotos;

    @Override
    public String toString() {
        return "SeedCompanyInfoBean{" +
                "address='" + address + '\'' +
                ", addressDetail='" + addressDetail + '\'' +
                ", business='" + business + '\'' +
                ", contact='" + contact + '\'' +
                ", contactMobile='" + contactMobile + '\'' +
                ", introduction='" + introduction + '\'' +
                ", logo='" + logo + '\'' +
                ", name='" + name + '\'' +
                ", stylePhotos='" + stylePhotos + '\'' +
                '}';
    }

    public SeedCompanyInfoBean(String address, String addressDetail, String business, String contact, String contactMobile, String introduction, String logo, String name, String stylePhotos) {
        this.address = address;
        this.addressDetail = addressDetail;
        this.business = business;
        this.contact = contact;
        this.contactMobile = contactMobile;
        this.introduction = introduction;
        this.logo = logo;
        this.name = name;
        this.stylePhotos = stylePhotos;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContactMobile() {
        return contactMobile;
    }

    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStylePhotos() {
        return stylePhotos;
    }

    public void setStylePhotos(String stylePhotos) {
        this.stylePhotos = stylePhotos;
    }
}
