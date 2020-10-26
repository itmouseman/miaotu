package com.widget.miaotu.http.bean;

public class UploadAPPBean {

    /**
     * modelType : ios
     * minVersion : 3.1.0
     * newVersion : 3.1.0
     * updateDesc : 更新内容
     * updateUrl : https://itunes.apple.com/cn/app/id1080191917?mt=8
     * needUpdate : true
     * forceUpdate : true
     */

    private String modelType;
    private String minVersion;
    private String newVersion;
    private String updateDesc;
    private String updateUrl;
    private boolean needUpdate;
    private boolean forceUpdate;

    @Override
    public String toString() {
        return "UploadAPPBean{" +
                "modelType='" + modelType + '\'' +
                ", minVersion='" + minVersion + '\'' +
                ", newVersion='" + newVersion + '\'' +
                ", updateDesc='" + updateDesc + '\'' +
                ", updateUrl='" + updateUrl + '\'' +
                ", needUpdate=" + needUpdate +
                ", forceUpdate=" + forceUpdate +
                '}';
    }

    public String getModelType() {
        return modelType;
    }

    public void setModelType(String modelType) {
        this.modelType = modelType;
    }

    public String getMinVersion() {
        return minVersion;
    }

    public void setMinVersion(String minVersion) {
        this.minVersion = minVersion;
    }

    public String getNewVersion() {
        return newVersion;
    }

    public void setNewVersion(String newVersion) {
        this.newVersion = newVersion;
    }

    public String getUpdateDesc() {
        return updateDesc;
    }

    public void setUpdateDesc(String updateDesc) {
        this.updateDesc = updateDesc;
    }

    public String getUpdateUrl() {
        return updateUrl;
    }

    public void setUpdateUrl(String updateUrl) {
        this.updateUrl = updateUrl;
    }

    public boolean isNeedUpdate() {
        return needUpdate;
    }

    public void setNeedUpdate(boolean needUpdate) {
        this.needUpdate = needUpdate;
    }

    public boolean isForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(boolean forceUpdate) {
        this.forceUpdate = forceUpdate;
    }
}
