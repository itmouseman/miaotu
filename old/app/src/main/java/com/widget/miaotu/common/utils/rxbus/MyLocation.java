package com.widget.miaotu.common.utils.rxbus;

/**
 * 触摸事件分发消息
 */
public class MyLocation {
    public String location;
    public String lon;
    public String lat;

    public MyLocation(String location, String lon, String lat) {
        this.location = location;
        this.lon = lon;
        this.lat = lat;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }
}
