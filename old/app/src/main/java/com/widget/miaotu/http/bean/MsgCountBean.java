package com.widget.miaotu.http.bean;

public class MsgCountBean {
    private Number strangerCount;
    private Number systemCount;
    private Number verifyCount;
    private Number communityMessageCount;

    @Override
    public String toString() {
        return "MsgCountBean{" +
                "strangerCount=" + strangerCount +
                ", systemCount=" + systemCount +
                ", verifyCount=" + verifyCount +
                ", communityMessageCount=" + communityMessageCount +
                '}';
    }

    public Number getStrangerCount() {
        return strangerCount;
    }

    public void setStrangerCount(Number strangerCount) {
        this.strangerCount = strangerCount;
    }

    public Number getSystemCount() {
        return systemCount;
    }

    public void setSystemCount(Number systemCount) {
        this.systemCount = systemCount;
    }

    public Number getVerifyCount() {
        return verifyCount;
    }

    public void setVerifyCount(Number verifyCount) {
        this.verifyCount = verifyCount;
    }

    public Number getCommunityMessageCount() {
        return communityMessageCount;
    }

    public void setCommunityMessageCount(Number communityMessageCount) {
        this.communityMessageCount = communityMessageCount;
    }
}
