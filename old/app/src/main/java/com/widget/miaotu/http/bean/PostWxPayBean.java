package com.widget.miaotu.http.bean;

import com.google.gson.annotations.SerializedName;

public class PostWxPayBean {


    /**
     * timeStamp : 1594969500
     * package : prepay_id=null
     * appId : wx3475ac33beeafdf4
     * sign : E3D5EDDC3900C36B90D2C4DE2F0B2F01
     * signType : MD5
     * mch_id : 1387372402
     * nonceStr : lqnUAzHCCgK2lNdrDnJolIksxBfJuPKR
     * timestamp : 1594969500
     */

    private String timeStamp;
    @SerializedName("package")
    private String packageX;
    private String appId;
    private String sign;
    private String signType;
    private String mch_id;
    private String nonceStr;
    private String timestamp;

    private String prepayid;


    @Override
    public String toString() {
        return "PostWxPayBean{" +
                "timeStamp='" + timeStamp + '\'' +
                ", packageX='" + packageX + '\'' +
                ", appId='" + appId + '\'' +
                ", sign='" + sign + '\'' +
                ", signType='" + signType + '\'' +
                ", mch_id='" + mch_id + '\'' +
                ", nonceStr='" + nonceStr + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", prepayid='" + prepayid + '\'' +
                '}';
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }
    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getPackageX() {
        return packageX;
    }

    public void setPackageX(String packageX) {
        this.packageX = packageX;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
