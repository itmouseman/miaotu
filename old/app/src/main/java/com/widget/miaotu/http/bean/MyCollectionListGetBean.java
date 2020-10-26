package com.widget.miaotu.http.bean;

import java.util.List;

/**
 * @author tzy
 */
public class MyCollectionListGetBean {

    /**
     * total : 2
     * collectSeedlingList : [{"id":57,"userId":73059,"companyId":10,"companyName":"啦啦啦","gardenId":null,"gardenName":null,"seedlingId":55,"seedlingUrls":"[     \t\t\t\t\t{     \t\t\t\t\t\t\"t_height\":810,     \t\t\t\t\t\t\"t_url\":\"http:\\/\\/oss-cn-beijing.aliyuncs.com\\/miaotu1\\/55a3c85196aee084686ab45b2f73e5dd_index_0.png\",     \t\t\t\t\t\t\"t_width\":1080     \t\t\t\t\t}     \t\t\t\t]","seedlingName":"阿穆尔丁香","plantType":"容器苗","spec":"[     \t\t\t\t\t{     \t\t\t\t\t\t\"specName\":\"高度\",     \t\t\t\t\t\t\"interval\":\"1-2\",     \t\t\t\t\t\t\"unit\":\"厘米\"     \t\t\t\t\t},     \t\t\t\t\t{     \t\t\t\t\t\t\"specName\":\"冠幅\",     \t\t\t\t\t\t\"unit\":\"厘米\",     \t\t\t\t\t\t\"interval\":\"1-2\"     \t\t\t\t\t}     \t\t\t\t]","price":"1.0","repertory":1,"quality":null,"province":"北京市","city":"北京市","status":1,"reason":null,"isPromote":null,"createTime":"2019/11/25"},{"id":38,"userId":null,"companyId":null,"companyName":null,"gardenId":null,"gardenName":null,"seedlingId":12,"seedlingUrls":null,"seedlingName":null,"plantType":null,"spec":null,"price":null,"repertory":null,"quality":null,"province":null,"city":null,"status":null,"reason":null,"isPromote":null,"createTime":"2019/11/20"}]
     */

    private int total;
    private List<CollectSeedlingListBean> collectSeedlingList;



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

    public static class CollectSeedlingListBean {

        /**
         * id : 57
         * userId : 73059
         * companyId : 10
         * companyName : 啦啦啦
         * gardenId : null
         * gardenName : null
         * seedlingId : 55
         * seedlingUrls : [     					{     						"t_height":810,     						"t_url":"http:\/\/oss-cn-beijing.aliyuncs.com\/miaotu1\/55a3c85196aee084686ab45b2f73e5dd_index_0.png",     						"t_width":1080     					}     				]
         * seedlingName : 阿穆尔丁香
         * plantType : 容器苗
         * spec : [     					{     						"specName":"高度",     						"interval":"1-2",     						"unit":"厘米"     					},     					{     						"specName":"冠幅",     						"unit":"厘米",     						"interval":"1-2"     					}     				]
         * price : 1.0
         * repertory : 1
         * quality : null
         * province : 北京市
         * city : 北京市
         * status : 1
         * reason : null
         * isPromote : null
         * createTime : 2019/11/25
         */

        private String id;
        private String userId;
        private String companyId;
        private String companyName;
        private String gardenId;
        private String gardenName;
        private String seedlingId;
        private String seedlingUrls;
        private String seedlingName;
        private String plantType;
        private String spec;
        private String price;
        private String repertory;
        private String quality;
        private String province;
        private String city;
        private String status;
        private String reason;
        private String isPromote;
        private String createTime;
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
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

        public String getGardenId() {
            return gardenId;
        }

        public void setGardenId(String gardenId) {
            this.gardenId = gardenId;
        }

        public String getGardenName() {
            return gardenName;
        }

        public void setGardenName(String gardenName) {
            this.gardenName = gardenName;
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

        public String getRepertory() {
            return repertory;
        }

        public void setRepertory(String repertory) {
            this.repertory = repertory;
        }

        public String getQuality() {
            return quality;
        }

        public void setQuality(String quality) {
            this.quality = quality;
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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getIsPromote() {
            return isPromote;
        }

        public void setIsPromote(String isPromote) {
            this.isPromote = isPromote;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

    }
}
