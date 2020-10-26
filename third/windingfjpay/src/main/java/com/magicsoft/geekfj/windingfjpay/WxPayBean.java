package com.magicsoft.geekfj.windingfjpay;

import com.google.gson.annotations.SerializedName;

/**
 * @author 风鼎科技
 */

public class WxPayBean {
    /**
     * appid : wx7efd0146f0a682ea
     * noncestr : PwGMFEeNgIWW22iRFoI0vv3rGcTBruUj
     * notifyUrl : https://www.miaoto.net/zmh/wx/notify
     * outTradeNo : l8k6a7y1551085410680
     * package : Sign=WXPay
     * partnerid : 1503435751
     * prepayid : wx25170330888331b62d6b5da40895275309
     * sign : 785DED99E0874C8FCD83B4BAFC4AEB06
     * timestamp : 1551085410
     * totalAmount : 1
     */

    private String appid;
    private String noncestr;
    private String notifyUrl;
    private String outTradeNo;
    @SerializedName("package")
    private String packageX;
    private String partnerid;
    private String prepayid;
    private String sign;
    private String timestamp;
    private String totalAmount;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getPackageX() {
        return packageX;
    }

    public void setPackageX(String packageX) {
        this.packageX = packageX;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }


//    /**
//     * appid : wx18eaff444811186d
//     * partnerid : 1488654852
//     * prepayid : wx24092447509486e511cff3d51164964171
//     * package : Sign=WXPay
//     * noncestr : Rdv0Q8FGA76n2G9b
//     * timestamp : 1524533087
//     * sign : 3E4FA872CEB9A5BB2D75BAC388FD7E8F
//     */
//
//    private String appid;
//    private String partnerid;
//    private String prepayid;
//    @SerializedName("package")
//    private String packageX;
//    private String noncestr;
//    private int timestamp;
//    private String sign;
//
//    public String getAppid() {
//        return appid;
//    }
//
//    public void setAppid(String appid) {
//        this.appid = appid;
//    }
//
//    public String getPartnerid() {
//        return partnerid;
//    }
//
//    public void setPartnerid(String partnerid) {
//        this.partnerid = partnerid;
//    }
//
//    public String getPrepayid() {
//        return prepayid;
//    }
//
//    public void setPrepayid(String prepayid) {
//        this.prepayid = prepayid;
//    }
//
//    public String getPackageX() {
//        return packageX;
//    }
//
//    public void setPackageX(String packageX) {
//        this.packageX = packageX;
//    }
//
//    public String getNoncestr() {
//        return noncestr;
//    }
//
//    public void setNoncestr(String noncestr) {
//        this.noncestr = noncestr;
//    }
//
//    public int getTimestamp() {
//        return timestamp;
//    }
//
//    public void setTimestamp(int timestamp) {
//        this.timestamp = timestamp;
//    }
//
//    public String getSign() {
//        return sign;
//    }
//
//    public void setSign(String sign) {
//        this.sign = sign;
//    }
//
//    @Override
//    public String toString() {
//        return "WxPayBean{" +
//                "appid='" + appid + '\'' +
//                ", partnerid='" + partnerid + '\'' +
//                ", prepayid='" + prepayid + '\'' +
//                ", packageX='" + packageX + '\'' +
//                ", noncestr='" + noncestr + '\'' +
//                ", timestamp=" + timestamp +
//                ", sign='" + sign + '\'' +
//                '}';
//    }
}
