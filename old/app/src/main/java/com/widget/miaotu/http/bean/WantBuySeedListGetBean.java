package com.widget.miaotu.http.bean;

/**
 * @author tzy
 */
public class WantBuySeedListGetBean {

    /**
     * wantBuyId : 6
     * seedlingId : 5
     * seedlingName : 阿穆尔丁香
     * plantType : 容器苗
     * quality : 良好
     * spec : null
     * wantBuyNum : 100
     * offerNum : 0
     * companyId : null
     * companyName : null
     * city : 杭州市
     * province : 浙江省
     * createTime : 2019-11-08 13:49:08
     * dueTime : 2019-11-15 13:49:08
     * status : 0
     */

    private int wantBuyId;
    private int seedlingId;
    private String seedlingName;
    private String plantType;
    private String quality;
    private String spec;
    private int wantBuyNum;
    private int offerNum;
    private String companyId;
    private String companyName;
    private String city;
    private String province;
    private String createTime;
    private String dueTime;
    private int status;
    private int isOffered;

    public int getIsOffered() {
        return isOffered;
    }

    public void setIsOffered(int isOffered) {
        this.isOffered = isOffered;
    }

    private String timeInterval;

    public String getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(String timeInterval) {
        this.timeInterval = timeInterval;
    }

    public int getWantBuyId() {
        return wantBuyId;
    }

    public void setWantBuyId(int wantBuyId) {
        this.wantBuyId = wantBuyId;
    }

    public int getSeedlingId() {
        return seedlingId;
    }

    public void setSeedlingId(int seedlingId) {
        this.seedlingId = seedlingId;
    }

    public String getSeedlingName() {
        return seedlingName;
    }

    public void setSeedlingName(String seedlingName) {
        this.seedlingName = seedlingName;
    }

    public String getPlantType() {
        return plantType;
    }

    public void setPlantType(String plantType) {
        this.plantType = plantType;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public int getWantBuyNum() {
        return wantBuyNum;
    }

    public void setWantBuyNum(int wantBuyNum) {
        this.wantBuyNum = wantBuyNum;
    }

    public int getOfferNum() {
        return offerNum;
    }

    public void setOfferNum(int offerNum) {
        this.offerNum = offerNum;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDueTime() {
        return dueTime;
    }

    public void setDueTime(String dueTime) {
        this.dueTime = dueTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
