package com.widget.miaotu.http.bean;

import java.util.List;

public class StartupInfoBean {

    /**
     * industryInfoList : [{"i_type_id":1,"i_name":"苗木基地","i_code":1},{"i_type_id":2,"i_name":"景观设计","i_code":2},{"i_type_id":3,"i_name":"园林施工","i_code":3},{"i_type_id":4,"i_name":"园林资材","i_code":4},{"i_type_id":5,"i_name":"花木市场","i_code":5},{"i_type_id":6,"i_name":"其它","i_code":100}]
     * paymentMethodsInfoList : [{"payment_methods_dictionary_id":0,"payment_methods_dictionary_name":"无"},{"payment_methods_dictionary_id":1,"payment_methods_dictionary_name":"上车付款"},{"payment_methods_dictionary_id":2,"payment_methods_dictionary_name":"货到付款"},{"payment_methods_dictionary_id":3,"payment_methods_dictionary_name":"账期"}]
     * urgencyLevelList : [{"urgency_level_id":1,"urgency_level_name":"询价"},{"urgency_level_id":2,"urgency_level_name":"立即采购"},{"urgency_level_id":3,"urgency_level_name":"近期采购"}]
     * identityInfo : [{"identity_key":1,"identity_value":"普通用户"},{"identity_key":2,"identity_value":"认证用户"},{"identity_key":3,"identity_value":"VIP"},{"identity_key":4,"identity_value":"站长"},{"identity_key":5,"identity_value":"战略合作单位"},{"identity_key":6,"identity_value":"金种子用户"},{"identity_key":7,"identity_value":"会员"},{"identity_key":8,"identity_value":"理事单位"},{"identity_key":9,"identity_value":"小二"}]
     * myUserIdentityKeyList : [1]
     * lineItemSum : 4
     * purchaseUpdate : true
     * infomationProgramLsit : [{"program_id":1,"program_name":"园林头条","sort_order":1},{"program_id":20,"program_name":"热文推荐","sort_order":2},{"program_id":16,"program_name":"园林IP","sort_order":4},{"program_id":5,"program_name":"种养技术","sort_order":5}]
     * experienceNoticeInfo : {"experience_value":"","experience_msg":""}
     * urlInfo : {"url_read":"http://imgs.miaoto.net/","url_write":"https://oss-cn-beijing.aliyuncs.com"}
     * behavior_value : 597
     * residual_value : 597
     * startupImage : {"activities_pic":"http://imgs.miaoto.net/activities/o_1dbcjjfc3sq615fd5ea1ojb1muvc.jpg?x-oss-process=style/index_banner","model":4,"businessid":422,"activities_content":""}
     * configAdv :
     * configClientItemList : [{"id":36,"itemName":"爆款推荐","itemCode":"1015","itemIcon":"/icon/o_1d630q7ri1k812qhget12a6109mc.png","iconUrl":"http://imgs.miaoto.net/icon/o_1d630q7ri1k812qhget12a6109mc.png","status":1,"isJump":0,"jumpUrl":""},{"id":33,"itemName":"热门活动","itemCode":"1003","itemIcon":"/icon/o_1d5m4sg6e1r9f1fhgn0657ag36c.png","iconUrl":"http://imgs.miaoto.net/icon/o_1d5m4sg6e1r9f1fhgn0657ag36c.png","status":1,"isJump":0,"jumpUrl":""},{"id":35,"itemName":"苗木公社","itemCode":"1005","itemIcon":"/icon/o_1d630rael1s9581fanmb8p127dc.png","iconUrl":"http://imgs.miaoto.net/icon/o_1d630rael1s9581fanmb8p127dc.png","status":1,"isJump":0,"jumpUrl":""},{"id":32,"itemName":"苗木资讯","itemCode":"1001","itemIcon":"/icon/o_1d5m50erv1nv3105ssa41bl21rs1c.png","iconUrl":"http://imgs.miaoto.net/icon/o_1d5m50erv1nv3105ssa41bl21rs1c.png","status":1,"isJump":0,"jumpUrl":""},{"id":34,"itemName":"附近苗圃","itemCode":"1004","itemIcon":"/icon/o_1d5m4o65emh01hahup9l8s35rc.png","iconUrl":"http://imgs.miaoto.net/icon/o_1d5m4o65emh01hahup9l8s35rc.png","status":1,"isJump":0,"jumpUrl":""},{"id":41,"itemName":"苗木排行","itemCode":"1024","itemIcon":"/icon/o_1d630rnjktjt2vl1oac13pi1uouc.png","iconUrl":"http://imgs.miaoto.net/icon/o_1d630rnjktjt2vl1oac13pi1uouc.png","status":1,"isJump":1,"jumpUrl":"http://www.miaoto.net/zmh/H5Page/garden/gardenListIndex/gardenListIndex.html"},{"id":40,"itemName":"交流社区","itemCode":"1031","itemIcon":"/icon/o_1d5m4te411nh41mcumb2vfgf1fc.png","iconUrl":"http://imgs.miaoto.net/icon/o_1d5m4te411nh41mcumb2vfgf1fc.png","status":1,"isJump":0,"jumpUrl":""},{"id":39,"itemName":"会员","itemCode":"1033","itemIcon":"/icon/o_1d630t2v217v011k7104u14r7163mc.png","iconUrl":"http://imgs.miaoto.net/icon/o_1d630t2v217v011k7104u14r7163mc.png","status":1,"isJump":1,"jumpUrl":"https://www.miaoto.net/zmh/H5Page/points/fulishe.html"}]
     */

    private int lineItemSum;
    private boolean purchaseUpdate;
    private ExperienceNoticeInfoBean experienceNoticeInfo;
    private UrlInfoBean urlInfo;
    private int behavior_value;
    private int residual_value;
    private StartupImageBean startupImage;
    private String configAdv;
    private List<IndustryInfoListBean> industryInfoList;
    private List<PaymentMethodsInfoListBean> paymentMethodsInfoList;
    private List<UrgencyLevelListBean> urgencyLevelList;
    private List<IdentityInfoBean> identityInfo;
    private List<Integer> myUserIdentityKeyList;
    private List<InfomationProgramLsitBean> infomationProgramLsit;
    private List<ConfigClientItemListBean> configClientItemList;

    public int getLineItemSum() {
        return lineItemSum;
    }

    public void setLineItemSum(int lineItemSum) {
        this.lineItemSum = lineItemSum;
    }

    public boolean isPurchaseUpdate() {
        return purchaseUpdate;
    }

    public void setPurchaseUpdate(boolean purchaseUpdate) {
        this.purchaseUpdate = purchaseUpdate;
    }

    public ExperienceNoticeInfoBean getExperienceNoticeInfo() {
        return experienceNoticeInfo;
    }

    public void setExperienceNoticeInfo(ExperienceNoticeInfoBean experienceNoticeInfo) {
        this.experienceNoticeInfo = experienceNoticeInfo;
    }

    public UrlInfoBean getUrlInfo() {
        return urlInfo;
    }

    public void setUrlInfo(UrlInfoBean urlInfo) {
        this.urlInfo = urlInfo;
    }

    public int getBehavior_value() {
        return behavior_value;
    }

    public void setBehavior_value(int behavior_value) {
        this.behavior_value = behavior_value;
    }

    public int getResidual_value() {
        return residual_value;
    }

    public void setResidual_value(int residual_value) {
        this.residual_value = residual_value;
    }

    public StartupImageBean getStartupImage() {
        return startupImage;
    }

    public void setStartupImage(StartupImageBean startupImage) {
        this.startupImage = startupImage;
    }

    public String getConfigAdv() {
        return configAdv;
    }

    public void setConfigAdv(String configAdv) {
        this.configAdv = configAdv;
    }

    public List<IndustryInfoListBean> getIndustryInfoList() {
        return industryInfoList;
    }

    public void setIndustryInfoList(List<IndustryInfoListBean> industryInfoList) {
        this.industryInfoList = industryInfoList;
    }

    public List<PaymentMethodsInfoListBean> getPaymentMethodsInfoList() {
        return paymentMethodsInfoList;
    }

    public void setPaymentMethodsInfoList(List<PaymentMethodsInfoListBean> paymentMethodsInfoList) {
        this.paymentMethodsInfoList = paymentMethodsInfoList;
    }

    public List<UrgencyLevelListBean> getUrgencyLevelList() {
        return urgencyLevelList;
    }

    public void setUrgencyLevelList(List<UrgencyLevelListBean> urgencyLevelList) {
        this.urgencyLevelList = urgencyLevelList;
    }

    public List<IdentityInfoBean> getIdentityInfo() {
        return identityInfo;
    }

    public void setIdentityInfo(List<IdentityInfoBean> identityInfo) {
        this.identityInfo = identityInfo;
    }

    public List<Integer> getMyUserIdentityKeyList() {
        return myUserIdentityKeyList;
    }

    public void setMyUserIdentityKeyList(List<Integer> myUserIdentityKeyList) {
        this.myUserIdentityKeyList = myUserIdentityKeyList;
    }

    public List<InfomationProgramLsitBean> getInfomationProgramLsit() {
        return infomationProgramLsit;
    }

    public void setInfomationProgramLsit(List<InfomationProgramLsitBean> infomationProgramLsit) {
        this.infomationProgramLsit = infomationProgramLsit;
    }

    public List<ConfigClientItemListBean> getConfigClientItemList() {
        return configClientItemList;
    }

    public void setConfigClientItemList(List<ConfigClientItemListBean> configClientItemList) {
        this.configClientItemList = configClientItemList;
    }

    public static class ExperienceNoticeInfoBean {
        /**
         * experience_value :
         * experience_msg :
         */

        private String experience_value;
        private String experience_msg;

        public String getExperience_value() {
            return experience_value;
        }

        public void setExperience_value(String experience_value) {
            this.experience_value = experience_value;
        }

        public String getExperience_msg() {
            return experience_msg;
        }

        public void setExperience_msg(String experience_msg) {
            this.experience_msg = experience_msg;
        }
    }

    public static class UrlInfoBean {
        /**
         * url_read : http://imgs.miaoto.net/
         * url_write : https://oss-cn-beijing.aliyuncs.com
         */

        private String url_read;
        private String url_write;

        public String getUrl_read() {
            return url_read;
        }

        public void setUrl_read(String url_read) {
            this.url_read = url_read;
        }

        public String getUrl_write() {
            return url_write;
        }

        public void setUrl_write(String url_write) {
            this.url_write = url_write;
        }
    }

    public static class StartupImageBean {
        /**
         * activities_pic : http://imgs.miaoto.net/activities/o_1dbcjjfc3sq615fd5ea1ojb1muvc.jpg?x-oss-process=style/index_banner
         * model : 4
         * businessid : 422
         * activities_content :
         */

        private String activities_pic;
        private int model;
        private int businessid;
        private String activities_content;

        public String getActivities_pic() {
            return activities_pic;
        }

        public void setActivities_pic(String activities_pic) {
            this.activities_pic = activities_pic;
        }

        public int getModel() {
            return model;
        }

        public void setModel(int model) {
            this.model = model;
        }

        public int getBusinessid() {
            return businessid;
        }

        public void setBusinessid(int businessid) {
            this.businessid = businessid;
        }

        public String getActivities_content() {
            return activities_content;
        }

        public void setActivities_content(String activities_content) {
            this.activities_content = activities_content;
        }
    }

    public static class IndustryInfoListBean {
        /**
         * i_type_id : 1
         * i_name : 苗木基地
         * i_code : 1
         */

        private int i_type_id;
        private String i_name;
        private int i_code;

        public int getI_type_id() {
            return i_type_id;
        }

        public void setI_type_id(int i_type_id) {
            this.i_type_id = i_type_id;
        }

        public String getI_name() {
            return i_name;
        }

        public void setI_name(String i_name) {
            this.i_name = i_name;
        }

        public int getI_code() {
            return i_code;
        }

        public void setI_code(int i_code) {
            this.i_code = i_code;
        }
    }

    public static class PaymentMethodsInfoListBean {
        /**
         * payment_methods_dictionary_id : 0
         * payment_methods_dictionary_name : 无
         */

        private int payment_methods_dictionary_id;
        private String payment_methods_dictionary_name;

        public int getPayment_methods_dictionary_id() {
            return payment_methods_dictionary_id;
        }

        public void setPayment_methods_dictionary_id(int payment_methods_dictionary_id) {
            this.payment_methods_dictionary_id = payment_methods_dictionary_id;
        }

        public String getPayment_methods_dictionary_name() {
            return payment_methods_dictionary_name;
        }

        public void setPayment_methods_dictionary_name(String payment_methods_dictionary_name) {
            this.payment_methods_dictionary_name = payment_methods_dictionary_name;
        }
    }

    public static class UrgencyLevelListBean {
        /**
         * urgency_level_id : 1
         * urgency_level_name : 询价
         */

        private int urgency_level_id;
        private String urgency_level_name;

        public int getUrgency_level_id() {
            return urgency_level_id;
        }

        public void setUrgency_level_id(int urgency_level_id) {
            this.urgency_level_id = urgency_level_id;
        }

        public String getUrgency_level_name() {
            return urgency_level_name;
        }

        public void setUrgency_level_name(String urgency_level_name) {
            this.urgency_level_name = urgency_level_name;
        }
    }

    public static class IdentityInfoBean {
        /**
         * identity_key : 1
         * identity_value : 普通用户
         */

        private int identity_key;
        private String identity_value;

        public int getIdentity_key() {
            return identity_key;
        }

        public void setIdentity_key(int identity_key) {
            this.identity_key = identity_key;
        }

        public String getIdentity_value() {
            return identity_value;
        }

        public void setIdentity_value(String identity_value) {
            this.identity_value = identity_value;
        }
    }

    public static class InfomationProgramLsitBean {
        /**
         * program_id : 1
         * program_name : 园林头条
         * sort_order : 1
         */

        private int program_id;
        private String program_name;
        private int sort_order;

        public int getProgram_id() {
            return program_id;
        }

        public void setProgram_id(int program_id) {
            this.program_id = program_id;
        }

        public String getProgram_name() {
            return program_name;
        }

        public void setProgram_name(String program_name) {
            this.program_name = program_name;
        }

        public int getSort_order() {
            return sort_order;
        }

        public void setSort_order(int sort_order) {
            this.sort_order = sort_order;
        }
    }

    public static class ConfigClientItemListBean {
        /**
         * id : 36
         * itemName : 爆款推荐
         * itemCode : 1015
         * itemIcon : /icon/o_1d630q7ri1k812qhget12a6109mc.png
         * iconUrl : http://imgs.miaoto.net/icon/o_1d630q7ri1k812qhget12a6109mc.png
         * status : 1
         * isJump : 0
         * jumpUrl :
         */

        private int id;
        private String itemName;
        private String itemCode;
        private String itemIcon;
        private String iconUrl;
        private int status;
        private int isJump;
        private String jumpUrl;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public String getItemCode() {
            return itemCode;
        }

        public void setItemCode(String itemCode) {
            this.itemCode = itemCode;
        }

        public String getItemIcon() {
            return itemIcon;
        }

        public void setItemIcon(String itemIcon) {
            this.itemIcon = itemIcon;
        }

        public String getIconUrl() {
            return iconUrl;
        }

        public void setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getIsJump() {
            return isJump;
        }

        public void setIsJump(int isJump) {
            this.isJump = isJump;
        }

        public String getJumpUrl() {
            return jumpUrl;
        }

        public void setJumpUrl(String jumpUrl) {
            this.jumpUrl = jumpUrl;
        }
    }
}
