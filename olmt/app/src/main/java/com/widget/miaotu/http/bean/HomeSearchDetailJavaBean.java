package com.widget.miaotu.http.bean;

import java.io.Serializable;
import java.util.List;

public class HomeSearchDetailJavaBean {

    /**
     * classifyCountInfo : [{"classifyName":"造型","count":50},{"classifyName":"单杆","count":260}]
     * seedlingBaseInfos : [{"city":"徐州市","companyId":5738,"companyName":"张磊15051876396的企业","createTime":"","distance":506,"gardenId":null,"gardenName":"","id":3455,"isForceOn":0,"isPromote":0,"isVip":1,"lat":34.205768,"lon":117.284124,"moreSeedling":1,"plantType":"容器苗","price":"0","province":"江苏省","quality":2,"reason":"","repertory":100,"secondClassify":"单杆","seedlingId":4817,"seedlingName":"美国红枫","seedlingUrls":"","status":1,"userId":22782}]
     * companyBaseInfos : [{"companyAddress":"","companyId":null,"companyLogo":"","companyName":"","fansCount":null,"isForceOn":null,"isVip":null,"nickname":""}]
     * type : 0
     */

    private int type;
    private List<ClassifyCountInfoBean> classifyCountInfo;
    private List<SeedlingBaseInfosBean> seedlingBaseInfos;
    private List<CompanyBaseInfosBean> companyBaseInfos;

    @Override
    public String toString() {
        return "HomeSearchDetailJavaBean{" +
                "type=" + type +
                ", classifyCountInfo=" + classifyCountInfo +
                ", seedlingBaseInfos=" + seedlingBaseInfos +
                ", companyBaseInfos=" + companyBaseInfos +
                '}';
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<ClassifyCountInfoBean> getClassifyCountInfo() {
        return classifyCountInfo;
    }

    public void setClassifyCountInfo(List<ClassifyCountInfoBean> classifyCountInfo) {
        this.classifyCountInfo = classifyCountInfo;
    }

    public List<SeedlingBaseInfosBean> getSeedlingBaseInfos() {
        return seedlingBaseInfos;
    }

    public void setSeedlingBaseInfos(List<SeedlingBaseInfosBean> seedlingBaseInfos) {
        this.seedlingBaseInfos = seedlingBaseInfos;
    }

    public List<CompanyBaseInfosBean> getCompanyBaseInfos() {
        return companyBaseInfos;
    }

    public void setCompanyBaseInfos(List<CompanyBaseInfosBean> companyBaseInfos) {
        this.companyBaseInfos = companyBaseInfos;
    }

    public static class ClassifyCountInfoBean implements Serializable {
        @Override
        public String toString() {
            return "ClassifyCountInfoBean{" +
                    "classifyName='" + classifyName + '\'' +
                    ", count=" + count +
                    '}';
        }

        /**
         * classifyName : 造型
         * count : 50
         */



        private String classifyName;
        private int count;

        public String getClassifyName() {
            return classifyName;
        }

        public void setClassifyName(String classifyName) {
            this.classifyName = classifyName;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }

    public static class SeedlingBaseInfosBean {
        /**
         * city : 徐州市
         * companyId : 5738
         * companyName : 张磊15051876396的企业
         * createTime :
         * distance : 506
         * gardenId : null
         * gardenName :
         * id : 3455
         * isForceOn : 0
         * isPromote : 0
         * isVip : 1
         * lat : 34.205768
         * lon : 117.284124
         * moreSeedling : 1
         * plantType : 容器苗
         * price : 0
         * province : 江苏省
         * quality : 2
         * reason :
         * repertory : 100
         * secondClassify : 单杆
         * seedlingId : 4817
         * seedlingName : 美国红枫
         * seedlingUrls :
         * status : 1
         * userId : 22782
         */

        private String city;
        private int companyId;
        private String companyName;
        private String createTime;
        private Double distance;
        private Object gardenId;
        private String gardenName;
        private int id;
        private int isForceOn;
        private int isPromote;
        private int isVip;
        private double lat;
        private double lon;
        private int moreSeedling;
        private String plantType;
        private Double price;
        private String province;
        private int quality;
        private String reason;
        private int repertory;
        private String secondClassify;
        private int seedlingId;
        private String seedlingName;
        private String seedlingUrls;
        private int status;
        private int userId;
        private String spec;
        private int nameAuth;


        @Override
        public String toString() {
            return "SeedlingBaseInfosBean{" +
                    "city='" + city + '\'' +
                    ", companyId=" + companyId +
                    ", companyName='" + companyName + '\'' +
                    ", createTime='" + createTime + '\'' +
                    ", distance=" + distance +
                    ", gardenId=" + gardenId +
                    ", gardenName='" + gardenName + '\'' +
                    ", id=" + id +
                    ", isForceOn=" + isForceOn +
                    ", isPromote=" + isPromote +
                    ", isVip=" + isVip +
                    ", lat=" + lat +
                    ", lon=" + lon +
                    ", moreSeedling=" + moreSeedling +
                    ", plantType='" + plantType + '\'' +
                    ", price='" + price + '\'' +
                    ", province='" + province + '\'' +
                    ", quality=" + quality +
                    ", reason='" + reason + '\'' +
                    ", repertory=" + repertory +
                    ", secondClassify='" + secondClassify + '\'' +
                    ", seedlingId=" + seedlingId +
                    ", seedlingName='" + seedlingName + '\'' +
                    ", seedlingUrls='" + seedlingUrls + '\'' +
                    ", status=" + status +
                    ", userId=" + userId +
                    ", spec='" + spec + '\'' +
                    '}';
        }

        public int getNameAuth() {
            return nameAuth;
        }

        public void setNameAuth(int nameAuth) {
            this.nameAuth = nameAuth;
        }

        public String getSpec() {
            return spec;
        }

        public void setSpec(String spec) {
            this.spec = spec;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public int getCompanyId() {
            return companyId;
        }

        public void setCompanyId(int companyId) {
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

        public Double getDistance() {
            return distance;
        }

        public void setDistance(Double distance) {
            this.distance = distance;
        }

        public Object getGardenId() {
            return gardenId;
        }

        public void setGardenId(Object gardenId) {
            this.gardenId = gardenId;
        }

        public String getGardenName() {
            return gardenName;
        }

        public void setGardenName(String gardenName) {
            this.gardenName = gardenName;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIsForceOn() {
            return isForceOn;
        }

        public void setIsForceOn(int isForceOn) {
            this.isForceOn = isForceOn;
        }

        public int getIsPromote() {
            return isPromote;
        }

        public void setIsPromote(int isPromote) {
            this.isPromote = isPromote;
        }

        public int getIsVip() {
            return isVip;
        }

        public void setIsVip(int isVip) {
            this.isVip = isVip;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLon() {
            return lon;
        }

        public void setLon(double lon) {
            this.lon = lon;
        }

        public int getMoreSeedling() {
            return moreSeedling;
        }

        public void setMoreSeedling(int moreSeedling) {
            this.moreSeedling = moreSeedling;
        }

        public String getPlantType() {
            return plantType;
        }

        public void setPlantType(String plantType) {
            this.plantType = plantType;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public int getQuality() {
            return quality;
        }

        public void setQuality(int quality) {
            this.quality = quality;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public int getRepertory() {
            return repertory;
        }

        public void setRepertory(int repertory) {
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

        public String getSeedlingUrls() {
            return seedlingUrls;
        }

        public void setSeedlingUrls(String seedlingUrls) {
            this.seedlingUrls = seedlingUrls;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }

    public static class CompanyBaseInfosBean {
        /**
         * companyAddress :
         * companyId : null
         * companyLogo :
         * companyName :
         * fansCount : null
         * isForceOn : null
         * isVip : null
         * nickname :
         */

        private String companyAddress;
        private Object companyId;
        private String companyLogo;
        private String companyName;
        private Object fansCount;
        private Object isForceOn;
        private Object isVip;
        private String nickname;


        @Override
        public String toString() {
            return "CompanyBaseInfosBean{" +
                    "companyAddress='" + companyAddress + '\'' +
                    ", companyId=" + companyId +
                    ", companyLogo='" + companyLogo + '\'' +
                    ", companyName='" + companyName + '\'' +
                    ", fansCount=" + fansCount +
                    ", isForceOn=" + isForceOn +
                    ", isVip=" + isVip +
                    ", nickname='" + nickname + '\'' +
                    '}';
        }

        public String getCompanyAddress() {
            return companyAddress;
        }

        public void setCompanyAddress(String companyAddress) {
            this.companyAddress = companyAddress;
        }

        public Object getCompanyId() {
            return companyId;
        }

        public void setCompanyId(Object companyId) {
            this.companyId = companyId;
        }

        public String getCompanyLogo() {
            return companyLogo;
        }

        public void setCompanyLogo(String companyLogo) {
            this.companyLogo = companyLogo;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public Object getFansCount() {
            return fansCount;
        }

        public void setFansCount(Object fansCount) {
            this.fansCount = fansCount;
        }

        public Object getIsForceOn() {
            return isForceOn;
        }

        public void setIsForceOn(Object isForceOn) {
            this.isForceOn = isForceOn;
        }

        public Object getIsVip() {
            return isVip;
        }

        public void setIsVip(Object isVip) {
            this.isVip = isVip;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }
    }

}
