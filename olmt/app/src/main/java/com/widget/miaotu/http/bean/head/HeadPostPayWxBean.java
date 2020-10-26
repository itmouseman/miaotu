package com.widget.miaotu.http.bean.head;

public class HeadPostPayWxBean {
    private String productId;
    private String openId;
    private String type;


    public HeadPostPayWxBean(String productId, String openId, String type) {
        this.productId = productId;
        this.openId = openId;
        this.type = type;
    }

    @Override
    public String toString() {
        return "HeadPostPayWxBean{" +
                "productId='" + productId + '\'' +
                ", openId='" + openId + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
