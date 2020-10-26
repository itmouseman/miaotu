package com.widget.miaotu.http.bean;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity // @Entity 将我们的java普通类变为一个能够被greenDAO识别的数据库类型的实体类
public class SeedlingInfo {

    /**
     * id : 1
     * baseName : 0909号枸杞
     * beginLetter : 0
     * commonNames :
     * createTime : 2019-08-14 12:43:25.0
     * modifyTime : null
     */
    @Id(autoincrement = true)
    private Long id;
    private String baseName;
    private String beginLetter;
    private String commonNames;
    private String createTime;
    private String modifyTime;
    @Generated(hash = 205110993)
    public SeedlingInfo(Long id, String baseName, String beginLetter,
                        String commonNames, String createTime, String modifyTime) {
        this.id = id;
        this.baseName = baseName;
        this.beginLetter = beginLetter;
        this.commonNames = commonNames;
        this.createTime = createTime;
        this.modifyTime = modifyTime;
    }
    @Generated(hash = 329361175)
    public SeedlingInfo() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getBaseName() {
        return this.baseName;
    }
    public void setBaseName(String baseName) {
        this.baseName = baseName;
    }
    public String getBeginLetter() {
        return this.beginLetter;
    }
    public void setBeginLetter(String beginLetter) {
        this.beginLetter = beginLetter;
    }
    public String getCommonNames() {
        return this.commonNames;
    }
    public void setCommonNames(String commonNames) {
        this.commonNames = commonNames;
    }
    public String getCreateTime() {
        return this.createTime;
    }
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
    public String getModifyTime() {
        return this.modifyTime;
    }
    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }
}
