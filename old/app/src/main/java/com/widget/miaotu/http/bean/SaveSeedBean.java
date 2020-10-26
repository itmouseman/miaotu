package com.widget.miaotu.http.bean;

public class SaveSeedBean {

    /**
     * gardenId : 苗圃id
     * seedlingId : 苗木名称id
     * seedlingUrls : 苗木图片url
     * firstClassify : 一级分类
     * secondClassify : 二级分类
     * spec : 规格
     * plantType : 种植类型
     * repertory : 库存
     * price : 价格
     * seedlingNum : 苗木编号
     * describe : 描述
     */

    private String gardenId;
    private String seedlingId;
    private String seedlingUrls;
    private String firstClassify;
    private String secondClassify;
    private String spec;
    private String plantType;
    private String repertory;
    private String price;
    private String seedlingNum;
    private String describe;
    private String id;
    private String quality;

    @Override
    public String toString() {
        return "SaveSeedBean{" +
                "gardenId='" + gardenId + '\'' +
                ", seedlingId='" + seedlingId + '\'' +
                ", seedlingUrls='" + seedlingUrls + '\'' +
                ", firstClassify='" + firstClassify + '\'' +
                ", secondClassify='" + secondClassify + '\'' +
                ", spec='" + spec + '\'' +
                ", plantType='" + plantType + '\'' +
                ", repertory='" + repertory + '\'' +
                ", price='" + price + '\'' +
                ", seedlingNum='" + seedlingNum + '\'' +
                ", describe='" + describe + '\'' +
                ", id='" + id + '\'' +
                ", quality='" + quality + '\'' +
                '}';
    }

    public SaveSeedBean(String gardenId, String seedlingId, String seedlingUrls, String firstClassify, String secondClassify, String spec,
                        String plantType, String repertory, String price, String seedlingNum, String describe, String quality) {
        this.gardenId = gardenId;
        this.seedlingId = seedlingId;
        this.seedlingUrls = seedlingUrls;
        this.firstClassify = firstClassify;
        this.secondClassify = secondClassify;
        this.spec = spec;
        this.plantType = plantType;
        this.repertory = repertory;
        this.price = price;
        this.seedlingNum = seedlingNum;
        this.describe = describe;
        this.quality = quality;
    }
    public SaveSeedBean(String id, String gardenId, String seedlingId, String seedlingUrls, String firstClassify, String secondClassify, String spec,
                        String plantType, String repertory, String price, String seedlingNum, String describe, String quality) {
        this.id = id;
        this.gardenId = gardenId;
        this.seedlingId = seedlingId;
        this.seedlingUrls = seedlingUrls;
        this.firstClassify = firstClassify;
        this.secondClassify = secondClassify;
        this.spec = spec;
        this.plantType = plantType;
        this.repertory = repertory;
        this.price = price;
        this.seedlingNum = seedlingNum;
        this.describe = describe;
        this.quality = quality;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGardenId() {
        return gardenId;
    }

    public void setGardenId(String gardenId) {
        this.gardenId = gardenId;
    }

    public String getSeedlingId() {
        return seedlingId;
    }

    public void setSeedlingId(String seedlingId) {
        this.seedlingId = seedlingId;
    }

    public String getSeedlingUrls() {
        return seedlingUrls;
    }

    public void setSeedlingUrls(String seedlingUrls) {
        this.seedlingUrls = seedlingUrls;
    }

    public String getFirstClassify() {
        return firstClassify;
    }

    public void setFirstClassify(String firstClassify) {
        this.firstClassify = firstClassify;
    }

    public String getSecondClassify() {
        return secondClassify;
    }

    public void setSecondClassify(String secondClassify) {
        this.secondClassify = secondClassify;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getPlantType() {
        return plantType;
    }

    public void setPlantType(String plantType) {
        this.plantType = plantType;
    }

    public String getRepertory() {
        return repertory;
    }

    public void setRepertory(String repertory) {
        this.repertory = repertory;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSeedlingNum() {
        return seedlingNum;
    }

    public void setSeedlingNum(String seedlingNum) {
        this.seedlingNum = seedlingNum;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
