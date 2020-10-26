package com.widget.miaotu.http.bean;

public class SeedlingDetailJavaBean {

    /**
     * {commonNames=木犀,桂花,岩桂,月月桂,
     * companyAddress=湖南省 长沙市 浏阳市,
     * companyId=6371.0,
     * companyName=彩微苗圃,
     * createTime=2020-6-28,
     * describe=,
     * firstClassify=地被,
     * headUrl=http://oss-cn-beijing.aliyuncs.com/miaotu1/ess-4547207624959989620.jpeg?x-oss-process=style/thumb_288_288,
     * id=4858.0,
     * isCollection=0.0,
     * lat=28.049269,
     * logo=http://imgs.miaoto.net/company_logo.png,
     * lon=113.234842,
     * lookHot=3.0,
     * mobile=15111053955,
     * nickname=彩溦苗木15111053955,
     * plantType=地栽苗, price=0,
     * quality=2.0, reason=, repertory=5000.0, secondClassify=, seedlingId=6420.0,
     * seedlingName=四季桂,
     * seedlingNum=016,
     * seedlingUrls=[{"t_url":"http://imgs.miaoto.net/tmp_e2ceec8e78b563a6a911f2dbec2117f126373d0d1a4ab020.jpg"},
     * {"t_url":"http://imgs.miaoto.net/tmp_3fb1ef542655c3e34448554abb9dfc5cb3f2b6d105049adb.jpg"}],
     * spec=[{"specName":"高度","unit":"厘米","interval":"30-40","mustWrite":1},{"specName":"冠幅","unit":"厘米","interval":"1-1","mustWrite":1},
     * {"specName":"分枝数","unit":"支","interval":"1-1","mustWrite":0},
     * {"specName":"袋重","unit":"斤","interval":"1-1","mustWrite":0}],
     * status=1.0,
     * userId=126723.0}
     */
    private String commonNames;

    private String companyAddress;

    private String companyId;
    private String companyName;
    private String createTime;
    private String describe;
    private String firstClassify;
    private String headUrl;
    private int id;
    private int isCollection;
    private double lat;
    private String logo;
    private double lon;
    private double lookHot;
    private String mobile;
    private String nickname;
    private String plantType;

    private double price;
    private double quality;

    private String reason;
    private String repertory;
    private String secondClassify;
    private int seedlingId;

    private String seedlingName;
    private double seedlingNum;
    private String seedlingUrls;
    private String spec;
    private String status;
    private int userId;

    @Override
    public String toString() {
        return "SeedlingDetailJavaBean{" +
                "companyAddress='" + companyAddress + '\'' +
                ", companyId=" + companyId +
                ", companyName='" + companyName + '\'' +
                ", createTime='" + createTime + '\'' +
                ", describe='" + describe + '\'' +
                ", firstClassify='" + firstClassify + '\'' +
                ", headUrl='" + headUrl + '\'' +
                ", id=" + id +
                ", isCollection=" + isCollection +
                ", lat=" + lat +
                ", logo='" + logo + '\'' +
                ", lon=" + lon +
                ", lookHot=" + lookHot +
                ", mobile=" + mobile +
                ", nickname='" + nickname + '\'' +
                ", plantType='" + plantType + '\'' +
                ", price=" + price +
                ", quality=" + quality +
                ", reason='" + reason + '\'' +
                ", repertory='" + repertory + '\'' +
                ", secondClassify='" + secondClassify + '\'' +
                ", seedlingId=" + seedlingId +
                ", seedlingName='" + seedlingName + '\'' +
                ", seedlingNum=" + seedlingNum +
                ", seedlingUrls='" + seedlingUrls + '\'' +
                ", spec='" + spec + '\'' +
                ", status='" + status + '\'' +
                ", userId=" + userId +
                '}';
    }


        public String getCommonNames() {

        return commonNames;
    }

    public void setCommonNames(String commonNames) {
        this.commonNames = commonNames;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getFirstClassify() {
        return firstClassify;
    }

    public void setFirstClassify(String firstClassify) {
        this.firstClassify = firstClassify;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsCollection() {
        return isCollection;
    }

    public void setIsCollection(int isCollection) {
        this.isCollection = isCollection;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLookHot() {
        return lookHot;
    }

    public void setLookHot(double lookHot) {
        this.lookHot = lookHot;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPlantType() {
        return plantType;
    }

    public void setPlantType(String plantType) {
        this.plantType = plantType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getQuality() {
        return quality;
    }

    public void setQuality(double quality) {
        this.quality = quality;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getRepertory() {
        return repertory;
    }

    public void setRepertory(String repertory) {
        this.repertory = repertory;
    }

    public String getSecondClassify() {
        return secondClassify;
    }

    public void setSecondClassify(String secondClassify) {
        this.secondClassify = secondClassify;
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

    public double getSeedlingNum() {
        return seedlingNum;
    }

    public void setSeedlingNum(double seedlingNum) {
        this.seedlingNum = seedlingNum;
    }

    public String getSeedlingUrls() {
        return seedlingUrls;
    }

    public void setSeedlingUrls(String seedlingUrls) {
        this.seedlingUrls = seedlingUrls;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}


