package com.widget.miaotu.http.bean;

import java.util.List;

public class HomeReMenHuoDongjavaBean {
    /**
     * errorNo : 0
     * errorContent : 返回成功
     * content : [{"businessId":495,"title":"2020'中国园林苗木企业创新峰会","cover":"http://imgs.miaoto.net/activities/1592795502548.jpg","model":4,"price":"2980","startTime":"2020.08.07","endTime":"2020.08.08","state":1},{"businessId":487,"title":"中苗会·首届紫薇产业发展论坛","cover":"http://imgs.miaoto.net/activities/1587716184444.jpg","model":4,"price":"1980","startTime":"2020.07.07","endTime":"2020.07.07","state":1},{"businessId":492,"title":"2020'中苗会十大苗木经纪人投票评选","cover":"http://imgs.miaoto.net/activities/1592031965747.png","model":7,"price":"0","startTime":"2020.06.13","endTime":"2020.06.30","state":3},{"businessId":481,"title":"中苗会·园林企业家游学之旅【江西站】","cover":"http://imgs.miaoto.net/activities/1574665950405.jpg","model":4,"price":"980","startTime":"2019.12.21","endTime":"2019.12.22","state":4},{"businessId":383,"title":"城戈5·中苗会明星队-丝绸之路|第五届中国城市戈壁挑战赛","cover":"http://imgs.miaoto.net/activities/o_1d5r65e6e1q53a54108hpf21m60m.jpg","model":8,"price":"13999","startTime":"2019.05.11","endTime":"2019.05.14","state":4},{"businessId":444,"title":"2019中苗会十大苗木经纪人投票评选","cover":"http://imgs.miaoto.net/activities/1560992910081.jpg","model":7,"price":"0","startTime":"2019.06.21","endTime":"2019.06.28","state":4}]
     */

    private int errorNo;
    private String errorContent;
    private List<ContentBean> content;

    @Override
    public String toString() {
        return "HomeReMenHuoDongjavaBean{" +
                "errorNo=" + errorNo +
                ", errorContent='" + errorContent + '\'' +
                ", content=" + content +
                '}';
    }

    public HomeReMenHuoDongjavaBean(int errorNo, String errorContent, List<ContentBean> content) {
        this.errorNo = errorNo;
        this.errorContent = errorContent;
        this.content = content;
    }

    public int getErrorNo() {
        return errorNo;
    }

    public void setErrorNo(int errorNo) {
        this.errorNo = errorNo;
    }

    public String getErrorContent() {
        return errorContent;
    }

    public void setErrorContent(String errorContent) {
        this.errorContent = errorContent;
    }

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * businessId : 495
         * title : 2020'中国园林苗木企业创新峰会
         * cover : http://imgs.miaoto.net/activities/1592795502548.jpg
         * model : 4
         * price : 2980
         * startTime : 2020.08.07
         * endTime : 2020.08.08
         * state : 1
         */

        private int businessId;
        private String title;
        private String cover;
        private int model;
        private String price;
        private String startTime;
        private String endTime;
        private int state;


        @Override
        public String toString() {
            return "ContentBean{" +
                    "businessId=" + businessId +
                    ", title='" + title + '\'' +
                    ", cover='" + cover + '\'' +
                    ", model=" + model +
                    ", price='" + price + '\'' +
                    ", startTime='" + startTime + '\'' +
                    ", endTime='" + endTime + '\'' +
                    ", state=" + state +
                    '}';
        }

        public int getBusinessId() {
            return businessId;
        }

        public void setBusinessId(int businessId) {
            this.businessId = businessId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public int getModel() {
            return model;
        }

        public void setModel(int model) {
            this.model = model;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }
    }
}
