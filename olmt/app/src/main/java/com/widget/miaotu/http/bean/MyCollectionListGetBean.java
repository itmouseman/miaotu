package com.widget.miaotu.http.bean;

import java.util.List;

/**
 * @author tzy
 */
public class MyCollectionListGetBean {

    /**
     * city : 西安
     * companyAuth : null
     * companyId : 6227
     * companyName : 西安蓝田泽绿苗木
     * createTime : 2020/7/21
     * distance : 0
     * gardenId : null
     * gardenName :
     * id : 276
     * isForceOn : 0
     * isPromote : null
     * isVip : null
     * lat : 0
     * lon : 0
     * moreSeedling : 0
     * nameAuth : null
     * plantType : 地栽苗
     * price : 0
     * province : 陕西
     * quality : 1
     * reason :
     * repertory : 10000
     * secondClassify :
     * seedlingId : 4522
     * seedlingName : 白皮松
     * seedlingUrls : [{"t_height":"1334","t_url":"http://imgs.miaoto.net/58979e41a9e6e36347a717b35fe44603_index_0.png","t_width":"1000"},{"t_height":"1239","t_url":"http://imgs.miaoto.net/28ed78c635a4e010694116371aa69c60_index_1.png","t_width":"697"},{"t_height":"1334","t_url":"http://imgs.miaoto.net/48a3e392980c70d2e037186f0f05a45b_index_2.png","t_width":"1000"}]
     * spec : [{"interval":"6-8","unit":"厘米","specName":"胸径"},{"specName":"高度","interval":"3-3","unit":"米"},{"specName":"冠幅","interval":"2-3","unit":"米"},{"interval":"60-70","unit":"厘米","specName":"土球尺寸"}]
     * status : 1
     * total : 0
     * userId : 88409
     */


    /**
     * total : 2
     *        {
     *     				"city":"西安",
     *     				"companyAuth":null,
     *     				"companyId":6227,
     *     				"companyName":"西安蓝田泽绿苗木",
     *     				"createTime":"2020/7/21",
     *     				"distance":0.0,
     *     				"gardenId":null,
     *     				"gardenName":"",
     *     				"id":276,
     *     				"isForceOn":0,
     *     				"isPromote":null,
     *     				"isVip":null,
     *     				"lat":0.0,
     *     				"lon":0.0,
     *     				"moreSeedling":0,
     *     				"nameAuth":null,
     *     				"plantType":"地栽苗",
     *     				"price":"0",
     *     				"province":"陕西",
     *     				"quality":1,
     *     				"reason":"",
     *     				"repertory":10000,
     *     				"secondClassify":"",
     *     				"seedlingId":4522,
     *     				"seedlingName":"白皮松",
     *     				"seedlingUrls":"[{\"t_height\":\"1334\",\"t_url\":\"http://imgs.miaoto.net/58979e41a9e6e36347a717b35fe44603_index_0.png\",\"t_width\":\"1000\"},{\"t_height\":\"1239\",\"t_url\":\"http://imgs.miaoto.net/28ed78c635a4e010694116371aa69c60_index_1.png\",\"t_width\":\"697\"},{\"t_height\":\"1334\",\"t_url\":\"http://imgs.miaoto.net/48a3e392980c70d2e037186f0f05a45b_index_2.png\",\"t_width\":\"1000\"}]",
     *     				"spec":"[{\"interval\":\"6-8\",\"unit\":\"厘米\",\"specName\":\"胸径\"},{\"specName\":\"高度\",\"interval\":\"3-3\",\"unit\":\"米\"},{\"specName\":\"冠幅\",\"interval\":\"2-3\",\"unit\":\"米\"},{\"interval\":\"60-70\",\"unit\":\"厘米\",\"specName\":\"土球尺寸\"}]",
     *     				"status":1,
     *     				"total":0,
     *     				"userId":88409
     *                }
     */

    private int total;
    private List<CollectSeedlingListBean> collectSeedlingList;

    @Override
    public String toString() {
        return "MyCollectionListGetBean{" +
                "total=" + total +
                ", collectSeedlingList=" + collectSeedlingList +
                '}';
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<CollectSeedlingListBean> getCollectSeedlingList() {
        return collectSeedlingList;
    }

    public void setCollectSeedlingList(List<CollectSeedlingListBean> collectSeedlingList) {
        this.collectSeedlingList = collectSeedlingList;
    }

    public class  CollectSeedlingListBean{

        private String city;
        private Object companyAuth;
        private int companyId;
        private String companyName;
        private String createTime;
        private int distance;
        private Object gardenId;
        private String gardenName;
        private int id;
        private int isForceOn;
        private Object isPromote;
        private Object isVip;
        private int lat;
        private int lon;
        private int moreSeedling;
        private Object nameAuth;
        private String plantType;
        private String price;
        private String province;
        private int quality;
        private String reason;
        private int repertory;
        private String secondClassify;
        private int seedlingId;
        private String seedlingName;
        private String seedlingUrls;
        private String spec;
        private int status;
        private int total;
        private int userId;


        @Override
        public String toString() {
            return "CollectSeedlingListBean{" +
                    "city='" + city + '\'' +
                    ", companyAuth=" + companyAuth +
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
                    ", nameAuth=" + nameAuth +
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
                    ", spec='" + spec + '\'' +
                    ", status=" + status +
                    ", total=" + total +
                    ", userId=" + userId +
                    '}';
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public Object getCompanyAuth() {
            return companyAuth;
        }

        public void setCompanyAuth(Object companyAuth) {
            this.companyAuth = companyAuth;
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

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
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

        public Object getIsPromote() {
            return isPromote;
        }

        public void setIsPromote(Object isPromote) {
            this.isPromote = isPromote;
        }

        public Object getIsVip() {
            return isVip;
        }

        public void setIsVip(Object isVip) {
            this.isVip = isVip;
        }

        public int getLat() {
            return lat;
        }

        public void setLat(int lat) {
            this.lat = lat;
        }

        public int getLon() {
            return lon;
        }

        public void setLon(int lon) {
            this.lon = lon;
        }

        public int getMoreSeedling() {
            return moreSeedling;
        }

        public void setMoreSeedling(int moreSeedling) {
            this.moreSeedling = moreSeedling;
        }

        public Object getNameAuth() {
            return nameAuth;
        }

        public void setNameAuth(Object nameAuth) {
            this.nameAuth = nameAuth;
        }

        public String getPlantType() {
            return plantType;
        }

        public void setPlantType(String plantType) {
            this.plantType = plantType;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
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

        public String getSpec() {
            return spec;
        }

        public void setSpec(String spec) {
            this.spec = spec;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }



}
