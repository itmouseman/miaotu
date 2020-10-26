package com.widget.miaotu.http.bean;

import java.util.List;

public class HomeInitJavaBean {
    /**
     * 首页
     */
    private List<NewsBean> news;
    private List<SeedlingPromoteBean> seedlingPromote;
    private List<BannerBean> banner;
    private List<WantBuyBean> wantBuy;
    private List<String> hot;
    private List<SeedlingBean> seedling;

    @Override
    public String toString() {
        return "HomeInitJavaBean{" +
                "news=" + news +
                ", seedlingPromote=" + seedlingPromote +
                ", banner=" + banner +
                ", wantBuy=" + wantBuy +
                ", hot=" + hot +
                ", seedling=" + seedling +
                '}';
    }

    public List<NewsBean> getNews() {
        return news;
    }

    public void setNews(List<NewsBean> news) {
        this.news = news;
    }

    public List<SeedlingPromoteBean> getSeedlingPromote() {
        return seedlingPromote;
    }

    public void setSeedlingPromote(List<SeedlingPromoteBean> seedlingPromote) {
        this.seedlingPromote = seedlingPromote;
    }

    public List<BannerBean> getBanner() {
        return banner;
    }

    public void setBanner(List<BannerBean> banner) {
        this.banner = banner;
    }

    public List<WantBuyBean> getWantBuy() {
        return wantBuy;
    }

    public void setWantBuy(List<WantBuyBean> wantBuy) {
        this.wantBuy = wantBuy;
    }

    public List<String> getHot() {
        return hot;
    }

    public void setHot(List<String> hot) {
        this.hot = hot;
    }

    public List<SeedlingBean> getSeedling() {
        return seedling;
    }

    public void setSeedling(List<SeedlingBean> seedling) {
        this.seedling = seedling;
    }

    public static class NewsBean {
        @Override
        public String toString() {
            return "NewsBean{" +
                    "id=" + id +
                    ", newsUrl='" + newsUrl + '\'' +
                    ", title='" + title + '\'' +
                    '}';
        }

        /**
         * id : 7728
         * newsUrl : https://www.miaoto.net/zmh/H5Page/info/main.html?id=7728&userId=0
         * title : 重庆200多个生态保护修复试点项目见成效
         */



        private int id;
        private String newsUrl;
        private String title;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNewsUrl() {
            return newsUrl;
        }

        public void setNewsUrl(String newsUrl) {
            this.newsUrl = newsUrl;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public static class SeedlingPromoteBean {
        /**
         * id : 4661
         * name : 角茎野牡丹
         * seedlingUrls : [{"t_url":"http://imgs.miaoto.net/tmp_447d3a8639caf76664fb14526cc6cefb.jpg"},{"t_url":"http://imgs.miaoto.net/tmp_9a7824ad1318dcce6d640994d0cc5f06.jpg"},{"t_url":"http://imgs.miaoto.net/tmp_5b0b3034a2a96ee4fb778ac29eb17654.jpg"}]
         * userId : 88654
         */

        private int id;
        private String name;
        private String seedlingUrls;
        private int userId;


        @Override
        public String toString() {
            return "SeedlingPromoteBean{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", seedlingUrls='" + seedlingUrls + '\'' +
                    ", userId=" + userId +
                    '}';
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSeedlingUrls() {
            return seedlingUrls;
        }

        public void setSeedlingUrls(String seedlingUrls) {
            this.seedlingUrls = seedlingUrls;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }

    public static class BannerBean {
        /**
         * business : 492
         * clickType : 5
         * cover : http://imgs.miaoto.net/vote.png
         * id : 10014
         */

        private String business;
        private int clickType;
        private String cover;
        private int id;


        @Override
        public String toString() {
            return "BannerBean{" +
                    "business='" + business + '\'' +
                    ", clickType=" + clickType +
                    ", cover='" + cover + '\'' +
                    ", id=" + id +
                    '}';
        }

        public String getBusiness() {
            return business;
        }

        public void setBusiness(String business) {
            this.business = business;
        }

        public int getClickType() {
            return clickType;
        }

        public void setClickType(int clickType) {
            this.clickType = clickType;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    public static class WantBuyBean {
        /**
         * id : 2075
         * userId : 94524
         * wantBuyUrls : [{"t_height":"640","t_url":"http://imgs.miaoto.net/tmp_5a033ab1328f1bd9489d929671bf1a81e045afb728f766d4.jpg","t_width":"640"}]
         */

        private int id;
        private int userId;
        private String wantBuyUrls;


        @Override
        public String toString() {
            return "WantBuyBean{" +
                    "id=" + id +
                    ", userId=" + userId +
                    ", wantBuyUrls='" + wantBuyUrls + '\'' +
                    '}';
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

        public String getWantBuyUrls() {
            return wantBuyUrls;
        }

        public void setWantBuyUrls(String wantBuyUrls) {
            this.wantBuyUrls = wantBuyUrls;
        }
    }

    public static class SeedlingBean {
        /**
         * city : 西安
         * companyId : 6227
         * companyName : 西安蓝田泽绿苗木
         * id : 4526
         * plantType : 地栽苗
         * price : 0
         * province : 陕西
         * repertory : 100000
         * seedlingId : 320
         * seedlingName : 白皮松
         * seedlingUrls : [{"t_height":"1334","t_url":"http://imgs.miaoto.net/bd2ffbb2445d25c7866ffe6c629c6cb3_index_0.png","t_width":"750"},{"t_height":"1334","t_url":"http://imgs.miaoto.net/7aee720a18941c41dd724449cbcc13fd_index_1.png","t_width":"750"}]
         * spec : [{"interval":"1-3","specName":"胸径","unit":"厘米"},{"interval":"1-1","unit":"米","specName":"高度"},{"specName":"土球尺寸","interval":"25-30","unit":"厘米"}]
         * userId : 88409
         */

        private String city;
        private int companyId;
        private String companyName;
        private int id;
        private String plantType;
        private String price;
        private String province;
        private int repertory;
        private int seedlingId;
        private String seedlingName;
        private String seedlingUrls;
        private String spec;
        private int userId;


        @Override
        public String toString() {
            return "SeedlingBean{" +
                    "city='" + city + '\'' +
                    ", companyId=" + companyId +
                    ", companyName='" + companyName + '\'' +
                    ", id=" + id +
                    ", plantType='" + plantType + '\'' +
                    ", price='" + price + '\'' +
                    ", province='" + province + '\'' +
                    ", repertory=" + repertory +
                    ", seedlingId=" + seedlingId +
                    ", seedlingName='" + seedlingName + '\'' +
                    ", seedlingUrls='" + seedlingUrls + '\'' +
                    ", spec='" + spec + '\'' +
                    ", userId=" + userId +
                    '}';
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

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public int getRepertory() {
            return repertory;
        }

        public void setRepertory(int repertory) {
            this.repertory = repertory;
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

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }
}
