package com.widget.miaotu.http.bean;

/**
 * @author tzy
 */
public class SeedListGetBean {

    /**
     * id : 6
     * userId : 73281
     * seedlingUrls : http://www.maioto.net/seedling/82382039jdds.jpg
     * seedlingName : 阿穆尔丁香
     * plantType : 容器苗
     * spec : [     				{     					'specName':'胸径',     					'interval':'10-20',     					'unit':'厘米'     				},     				{     					'specName':'高度',     					'interval':'10-20',     					'unit':'米''     				}     			]
     * price : 100.0
     * repertory : 100
     * province : null
     * city : null
     * status : 0
     * reason : null
     * "gardenName": "苗圃名称",
     * "quality": "品质标识；0：清货；1：精品；2：普通",
     * "isPromote": "是否是推广苗木；0：不是；1：是",
     */

    private int id;
    private int userId;
    private String gardenId;

    public String getGardenId() {
        return gardenId;
    }

    public void setGardenId(String gardenId) {
        this.gardenId = gardenId;
    }

    private String seedlingUrls;
    private String seedlingName;
    private String plantType;
    private String spec;
    private String price;
    private int repertory;//仓库数量
    private String province;
    private String city;
    private int status;
    private String reason;

    private String gardenName;
    private String quality;
    private int isPromote;
    private String companyName;
    private String seedlingId;

    private String isShow;


    @Override
    public String toString() {
        return "SeedListGetBean{" +
                "id=" + id +
                ", userId=" + userId +
                ", gardenId='" + gardenId + '\'' +
                ", seedlingUrls='" + seedlingUrls + '\'' +
                ", seedlingName='" + seedlingName + '\'' +
                ", plantType='" + plantType + '\'' +
                ", spec='" + spec + '\'' +
                ", price='" + price + '\'' +
                ", repertory=" + repertory +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", status=" + status +
                ", reason='" + reason + '\'' +
                ", gardenName='" + gardenName + '\'' +
                ", quality='" + quality + '\'' +
                ", isPromote='" + isPromote + '\'' +
                ", companyName='" + companyName + '\'' +
                ", seedlingId='" + seedlingId + '\'' +
                ", isShow='" + isShow + '\'' +
                '}';
    }

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    public String getSeedlingId() {
        return seedlingId;
    }

    public void setSeedlingId(String seedlingId) {
        this.seedlingId = seedlingId;
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

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public int getIsPromote() {
        return isPromote;
    }

    public void setIsPromote(int isPromote) {
        this.isPromote = isPromote;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getSeedlingUrls() {
        return seedlingUrls;
    }

    public void setSeedlingUrls(String seedlingUrls) {
        this.seedlingUrls = seedlingUrls;
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

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getRepertory() {
        return repertory;
    }

    public void setRepertory(int repertory) {
        this.repertory = repertory;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
