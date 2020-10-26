package com.widget.miaotu.http.bean;

public class HomeSearchJavaBean {
    private String baseName;
    private String beginLetter;

    private String classify;
    private String commonNames;
    private String createTime;
    private String id;
    private String name;
    private String type;


    @Override
    public String toString() {
        return "HomeSearchJavaBean{" +
                "baseName='" + baseName + '\'' +
                ", beginLetter='" + beginLetter + '\'' +
                ", classify='" + classify + '\'' +
                ", commonNames='" + commonNames + '\'' +
                ", createTime='" + createTime + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public String getBaseName() {
        return baseName;
    }

    public void setBaseName(String baseName) {
        this.baseName = baseName;
    }

    public String getBeginLetter() {
        return beginLetter;
    }

    public void setBeginLetter(String beginLetter) {
        this.beginLetter = beginLetter;
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public String getCommonNames() {
        return commonNames;
    }

    public void setCommonNames(String commonNames) {
        this.commonNames = commonNames;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
