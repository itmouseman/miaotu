package com.widget.miaotu.http.bean.head;

public class VipOrderSendTypeBean {
    private int type;
    private int vipType;
    private String userId;

    public VipOrderSendTypeBean(int type, int vipType, String userId) {
        this.type = type;
        this.vipType = vipType;
        this.userId = userId;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getVipType() {
        return vipType;
    }

    public void setVipType(int vipType) {
        this.vipType = vipType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
