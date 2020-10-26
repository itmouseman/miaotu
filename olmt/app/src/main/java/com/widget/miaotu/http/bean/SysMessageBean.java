package com.widget.miaotu.http.bean;

public class SysMessageBean {
    /**
     *     			"create_time":"2020-07-06",
     *     			"is_detail":0,
     *     			"looked":1,
     *     			"type":5,
     *     			"title":"编辑个人信息",
     *     			"business_id":10001,
     *     			"content":"编辑个人信息成功"
     */
    private  String create_time;
    private  Number is_detail;
    private String looked;
    private String type;

    private String title;
    private String business_id;
    private String content;
    private String cover;


    @Override
    public String toString() {
        return "SysMessageBean{" +
                "create_time='" + create_time + '\'' +
                ", is_detail=" + is_detail +
                ", looked='" + looked + '\'' +
                ", type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", business_id='" + business_id + '\'' +
                ", content='" + content + '\'' +
                ", cover='" + cover + '\'' +
                '}';
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public Number getIs_detail() {
        return is_detail;
    }

    public void setIs_detail(Number is_detail) {
        this.is_detail = is_detail;
    }

    public String getLooked() {
        return looked;
    }

    public void setLooked(String looked) {
        this.looked = looked;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBusiness_id() {
        return business_id;
    }

    public void setBusiness_id(String business_id) {
        this.business_id = business_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
