package com.widget.miaotu.http.bean;

public class DemandSpecNewBean {
    private String specName;
    private String interval;
    private String unit;

    public DemandSpecNewBean(String specName, String interval, String unit) {
        this.specName = specName;
        this.interval = interval;
        this.unit = unit;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
