package com.widget.miaotu.http.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * author : heyang
 * e-mail :
 * date   : 2020/8/1711:52
 * desc   :
 * version: 1.0
 */
public class GardenHeadlinesBean {

    /**
     * errorNo : 0
     * errorContent : 返回成功
     * content : [{"programId":1,"programName":"园林头条","list":[{"id":8600,"title":"2020'中国园林苗木企业创新峰会暨【第四届华南花卉苗木产业发展论坛】隆重启动！","infoFrom":"中苗会","views":8,"time":"2020-08-12 08:57:20.0","timeStamp":1597193840000,"picUrl":"[{\"t_height\":\"0\",\"t_url\":\"http://imgs.miaoto.net/company/company_1591753135329.jpg\",\"t_width\":\"0\"}]","imgType":1,"detailUrl":"https://www.miaoto.net/zmh/H5Page/info/main.html?id=8600&userId=0","labelName":"活动","labelColor":"232, 121, 117, 1"},{"id":8537,"title":"报名倒计时，他们都来了！！！你还等什么呢？","infoFrom":"中苗会","views":161,"time":"2020-08-12 08:56:49.0","timeStamp":1597193809000,"picUrl":"[{\"t_height\":\"0\",\"t_url\":\"http://imgs.miaoto.net/company/company_1587630265720.jpg\",\"t_width\":\"0\"}]","imgType":1,"detailUrl":"https://www.miaoto.net/zmh/H5Page/info/main.html?id=8537&userId=0","labelName":null,"labelColor":null},{"id":8369,"title":"7月7日演讲嘉宾出炉，你pick谁？","infoFrom":"中苗会","views":213,"time":"2020-08-12 08:56:30.0","timeStamp":1597193790000,"picUrl":"[{\"t_height\":\"0\",\"t_url\":\"http://imgs.miaoto.net/company/company_1588051804731.jpg\",\"t_width\":\"0\"}]","imgType":1,"detailUrl":"https://www.miaoto.net/zmh/H5Page/info/main.html?id=8369&userId=0","labelName":"独家","labelColor":"68,148,239"},{"id":8370,"title":"紫薇发展正当时","infoFrom":"中苗会","views":267,"time":"2020-08-12 08:56:12.0","timeStamp":1597193772000,"picUrl":"[{\"t_height\":\"0\",\"t_url\":\"http://imgs.miaoto.net/company/company_1588052833489.jpg\",\"t_width\":\"0\"}]","imgType":1,"detailUrl":"https://www.miaoto.net/zmh/H5Page/info/main.html?id=8370&userId=0","labelName":"独家","labelColor":"68,148,239"},{"id":8861,"title":"六周年庆典结束，下一站在哪里？","infoFrom":"中苗会","views":10,"time":"2020-08-11 09:59:59.0","timeStamp":1597111199000,"picUrl":"[{\"t_height\":\"0\",\"t_url\":\"http://imgs.miaoto.net/company/company_1597111182283.jpg\",\"t_width\":\"0\"}]","imgType":0,"detailUrl":"https://www.miaoto.net/zmh/H5Page/info/main.html?id=8861&userId=0","labelName":"活动","labelColor":"232, 121, 117, 1"},{"id":8860,"title":"��苗会6周年庆典，8月7日分享嘉宾、分享主题揭晓！！","infoFrom":"中苗会","views":18,"time":"2020-08-03 14:15:51.0","timeStamp":1596435351000,"picUrl":"[{\"t_height\":\"0\",\"t_url\":\"http://imgs.miaoto.net/company/company_1596435270594.jpg\",\"t_width\":\"0\"}]","imgType":0,"detailUrl":"https://www.miaoto.net/zmh/H5Page/info/main.html?id=8812&userId=0","labelName":null,"labelColor":null}]},
     * {"programId":16,"programName":"园林IP","list":null},
     * {"programId":5,"programName":"种养技术","list":null},
     * {"programId":22,"programName":"百强联盟","list":null}]
     */

    private int errorNo;
    private String errorContent;
    private List<ContentBean> content;

    @Override
    public String toString() {
        return "GardenHeadlinesBean{" +
                "errorNo=" + errorNo +
                ", errorContent='" + errorContent + '\'' +
                ", content=" + content +
                '}';
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
         * programId : 1
         * programName : 园林头条
         * list : [{"id":8600,"title":"2020'中国园林苗木企业创新峰会暨【第四届华南花卉苗木产业发展论坛】隆重启动！","infoFrom":"中苗会","views":8,"time":"2020-08-12 08:57:20.0","timeStamp":1597193840000,"picUrl":"[{\"t_height\":\"0\",\"t_url\":\"http://imgs.miaoto.net/company/company_1591753135329.jpg\",\"t_width\":\"0\"}]","imgType":1,"detailUrl":"https://www.miaoto.net/zmh/H5Page/info/main.html?id=8600&userId=0","labelName":"活动","labelColor":"232, 121, 117, 1"},{"id":8537,"title":"报名倒计时，他们都来了！！！你还等什么呢？","infoFrom":"中苗会","views":161,"time":"2020-08-12 08:56:49.0","timeStamp":1597193809000,"picUrl":"[{\"t_height\":\"0\",\"t_url\":\"http://imgs.miaoto.net/company/company_1587630265720.jpg\",\"t_width\":\"0\"}]","imgType":1,"detailUrl":"https://www.miaoto.net/zmh/H5Page/info/main.html?id=8537&userId=0","labelName":null,"labelColor":null},{"id":8369,"title":"7月7日演讲嘉宾出炉，你pick谁？","infoFrom":"中苗会","views":213,"time":"2020-08-12 08:56:30.0","timeStamp":1597193790000,"picUrl":"[{\"t_height\":\"0\",\"t_url\":\"http://imgs.miaoto.net/company/company_1588051804731.jpg\",\"t_width\":\"0\"}]","imgType":1,"detailUrl":"https://www.miaoto.net/zmh/H5Page/info/main.html?id=8369&userId=0","labelName":"独家","labelColor":"68,148,239"},{"id":8370,"title":"紫薇发展正当时","infoFrom":"中苗会","views":267,"time":"2020-08-12 08:56:12.0","timeStamp":1597193772000,"picUrl":"[{\"t_height\":\"0\",\"t_url\":\"http://imgs.miaoto.net/company/company_1588052833489.jpg\",\"t_width\":\"0\"}]","imgType":1,"detailUrl":"https://www.miaoto.net/zmh/H5Page/info/main.html?id=8370&userId=0","labelName":"独家","labelColor":"68,148,239"},{"id":8861,"title":"六周年庆典结束，下一站在哪里？","infoFrom":"中苗会","views":10,"time":"2020-08-11 09:59:59.0","timeStamp":1597111199000,"picUrl":"[{\"t_height\":\"0\",\"t_url\":\"http://imgs.miaoto.net/company/company_1597111182283.jpg\",\"t_width\":\"0\"}]","imgType":0,"detailUrl":"https://www.miaoto.net/zmh/H5Page/info/main.html?id=8861&userId=0","labelName":"活动","labelColor":"232, 121, 117, 1"},{"id":8860,"title":"��苗会6周年庆典，8月7日分享嘉宾、分享主题揭晓！！","infoFrom":"中苗会","views":18,"time":"2020-08-03 14:15:51.0","timeStamp":1596435351000,"picUrl":"[{\"t_height\":\"0\",\"t_url\":\"http://imgs.miaoto.net/company/company_1596435270594.jpg\",\"t_width\":\"0\"}]","imgType":0,"detailUrl":"https://www.miaoto.net/zmh/H5Page/info/main.html?id=8812&userId=0","labelName":null,"labelColor":null}]
         */

        private int programId;
        private String programName;
        private List<ListBean> list;

        @Override
        public String toString() {
            return "ContentBean{" +
                    "programId=" + programId +
                    ", programName='" + programName + '\'' +
                    ", list=" + list +
                    '}';
        }

        public int getProgramId() {
            return programId;
        }

        public void setProgramId(int programId) {
            this.programId = programId;
        }

        public String getProgramName() {
            return programName;
        }

        public void setProgramName(String programName) {
            this.programName = programName;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean implements MultiItemEntity {
            /**
             * id : 8600
             * title : 2020'中国园林苗木企业创新峰会暨【第四届华南花卉苗木产业发展论坛】隆重启动！
             * infoFrom : 中苗会
             * views : 8
             * time : 2020-08-12 08:57:20.0
             * timeStamp : 1597193840000
             * picUrl : [{"t_height":"0","t_url":"http://imgs.miaoto.net/company/company_1591753135329.jpg","t_width":"0"}]
             * imgType : 1
             * detailUrl : https://www.miaoto.net/zmh/H5Page/info/main.html?id=8600&userId=0
             * labelName : 活动
             * labelColor : 232, 121, 117, 1
             */

            private int id;
            private String title;
            private String infoFrom;
            private int views;
            private String time;
            private long timeStamp;
            private String picUrl;
            private int imgType;
            private String detailUrl;
            private String labelName;
            private String labelColor;

            public static final int ImageOnly = 0;
            public static final int ImageAndText = 1;
            public static final int ImageMini = 2;


            @Override
            public String toString() {
                return "ListBean{" +
                        "id=" + id +
                        ", title='" + title + '\'' +
                        ", infoFrom='" + infoFrom + '\'' +
                        ", views=" + views +
                        ", time='" + time + '\'' +
                        ", timeStamp=" + timeStamp +
                        ", picUrl='" + picUrl + '\'' +
                        ", imgType=" + imgType +
                        ", detailUrl='" + detailUrl + '\'' +
                        ", labelName='" + labelName + '\'' +
                        ", labelColor='" + labelColor + '\'' +
                        '}';
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getInfoFrom() {
                return infoFrom;
            }

            public void setInfoFrom(String infoFrom) {
                this.infoFrom = infoFrom;
            }

            public int getViews() {
                return views;
            }

            public void setViews(int views) {
                this.views = views;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public long getTimeStamp() {
                return timeStamp;
            }

            public void setTimeStamp(long timeStamp) {
                this.timeStamp = timeStamp;
            }

            public String getPicUrl() {
                return picUrl;
            }

            public void setPicUrl(String picUrl) {
                this.picUrl = picUrl;
            }

            public int getImgType() {
                return imgType;
            }

            public void setImgType(int imgType) {
                this.imgType = imgType;
            }

            public String getDetailUrl() {
                return detailUrl;
            }

            public void setDetailUrl(String detailUrl) {
                this.detailUrl = detailUrl;
            }

            public String getLabelName() {
                return labelName;
            }

            public void setLabelName(String labelName) {
                this.labelName = labelName;
            }

            public String getLabelColor() {
                return labelColor;
            }

            public void setLabelColor(String labelColor) {
                this.labelColor = labelColor;
            }

            @Override
            public int getItemType() {
                return imgType;
            }
        }
    }
}
