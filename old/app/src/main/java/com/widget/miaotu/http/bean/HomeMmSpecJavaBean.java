package com.widget.miaotu.http.bean;

public class HomeMmSpecJavaBean {
    private String specName;
    private String unit;
    private String interval;
    private int mustWrite;

    @Override
    public String toString() {
        return "HomeMmSpecJavaBean{" +
                "specName='" + specName + '\'' +
                ", unit='" + unit + '\'' +
                ", interval='" + interval + '\'' +
                ", mustWrite='" + mustWrite + '\'' +
                '}';
    }

    public HomeMmSpecJavaBean(String specName, String unit, String interval, int mustWrite) {
        this.specName = specName;
        this.unit = unit;
        this.interval = interval;
        this.mustWrite = mustWrite;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }


    public int getMustWrite() {
        return mustWrite;
    }

    public void setMustWrite(int mustWrite) {
        this.mustWrite = mustWrite;
    }
}
