package com.widget.miaotu.http.bean;

public class CompanyInfoMeBean {

    /**
     * followCount : 1
     * address : 浙江省杭州市余杭区
     * business : 1
     * gardenCount : 3
     * address_detail : 1
     * isFollow : 0
     * isAuth : -1
     * contact : 1
     * name : 鸬鸟苗圃园林公司
     * logo : 1
     * contact_mobile : 1
     * style_photos : {}
     * id : 9
     * introduction : 1
     * "seedlingWantBuyCount":2,
     *     		"gardenCount":2
     */

    private int followCount;
    private String address;
    private String business;
    private int gardenCount;
    private String address_detail;
    private int isFollow;
    private int isAuth;
    private String contact;
    private String name;
    private String logo;
    private String contact_mobile;
    private String style_photos;
    private int id;
    private String introduction;
    private int seedlingWantBuyCount;
    private int seedlingCount;//

    @Override
    public String toString() {
        return "CompanyInfoMeBean{" +
                "followCount=" + followCount +
                ", address='" + address + '\'' +
                ", business='" + business + '\'' +
                ", gardenCount=" + gardenCount +
                ", address_detail='" + address_detail + '\'' +
                ", isFollow=" + isFollow +
                ", isAuth=" + isAuth +
                ", contact='" + contact + '\'' +
                ", name='" + name + '\'' +
                ", logo='" + logo + '\'' +
                ", contact_mobile='" + contact_mobile + '\'' +
                ", style_photos='" + style_photos + '\'' +
                ", id=" + id +
                ", introduction='" + introduction + '\'' +
                ", seedlingWantBuyCount=" + seedlingWantBuyCount +
                ", seedlingCount=" + seedlingCount +
                '}';
    }

    public int getSeedlingWantBuyCount() {
        return seedlingWantBuyCount;
    }

    public void setSeedlingWantBuyCount(int seedlingWantBuyCount) {
        this.seedlingWantBuyCount = seedlingWantBuyCount;
    }

    public int getSeedlingCount() {
        return seedlingCount;
    }

    public void setSeedlingCount(int seedlingCount) {
        this.seedlingCount = seedlingCount;
    }

    public int getFollowCount() {
        return followCount;
    }

    public void setFollowCount(int followCount) {
        this.followCount = followCount;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public int getGardenCount() {
        return gardenCount;
    }

    public void setGardenCount(int gardenCount) {
        this.gardenCount = gardenCount;
    }

    public String getAddress_detail() {
        return address_detail;
    }

    public void setAddress_detail(String address_detail) {
        this.address_detail = address_detail;
    }

    public int getIsFollow() {
        return isFollow;
    }

    public void setIsFollow(int isFollow) {
        this.isFollow = isFollow;
    }

    public int getIsAuth() {
        return isAuth;
    }

    public void setIsAuth(int isAuth) {
        this.isAuth = isAuth;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getContact_mobile() {
        return contact_mobile;
    }

    public void setContact_mobile(String contact_mobile) {
        this.contact_mobile = contact_mobile;
    }

    public String getStyle_photos() {
        return style_photos;
    }

    public void setStyle_photos(String style_photos) {
        this.style_photos = style_photos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
}
