package com.widget.miaotu.http.bean;

public class HomeAdvertisingBean {
    private int status;
    private String message;
    private MData data;

    @Override
    public String toString() {
        return "HomeAdvertisingBean{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MData getData() {
        return data;
    }

    public void setData(MData data) {
        this.data = data;
    }

    public class MData{
        private int id;
        private String link;
        private int type;

        @Override
        public String toString() {
            return "MData{" +
                    "id=" + id +
                    ", link='" + link + '\'' +
                    ", type=" + type +
                    '}';
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
