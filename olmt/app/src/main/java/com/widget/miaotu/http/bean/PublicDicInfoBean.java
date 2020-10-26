package com.widget.miaotu.http.bean;
import java.util.List;

public class PublicDicInfoBean {

    /**
     * data : [{"job_id":6,"parent_name":"","job_name":"苗木技术"},{"job_id":7,"parent_name":"","job_name":"苗木销售"},{"job_id":8,"parent_name":"","job_name":"苗圃管理"},{"job_id":9,"parent_name":"","job_name":"苗木其它"}]
     * title : 苗木
     */

    private String title;
    private List<DataBean> data;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * job_id : 6
         * parent_name :
         * job_name : 苗木技术
         */
        private int type;
        private int job_id;
        private String parent_name;
        private String job_name;

        public DataBean(int type, int job_id, String parent_name, String job_name) {
            this.type = type;
            this.job_id = job_id;
            this.parent_name = parent_name;
            this.job_name = job_name;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getJob_id() {
            return job_id;
        }

        public void setJob_id(int job_id) {
            this.job_id = job_id;
        }

        public String getParent_name() {
            return parent_name;
        }

        public void setParent_name(String parent_name) {
            this.parent_name = parent_name;
        }

        public String getJob_name() {
            return job_name;
        }

        public void setJob_name(String job_name) {
            this.job_name = job_name;
        }
    }
}

