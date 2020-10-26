package com.widget.miaotu.http.bean;

public class RealCompanyBean {


    private String companyCardPhoto;

    private String companyBossCardIdPhoto;


    public RealCompanyBean(String companyCardPhoto, String companyBossCardIdPhoto) {
        this.companyCardPhoto = companyCardPhoto;
        this.companyBossCardIdPhoto = companyBossCardIdPhoto;
    }

    public String getCompanyCardPhoto() {
        return companyCardPhoto;
    }

    public void setCompanyCardPhoto(String companyCardPhoto) {
        this.companyCardPhoto = companyCardPhoto;
    }

    public String getCompanyBossCardIdPhoto() {
        return companyBossCardIdPhoto;
    }

    public void setCompanyBossCardIdPhoto(String companyBossCardIdPhoto) {
        this.companyBossCardIdPhoto = companyBossCardIdPhoto;
    }
}
