package com.widget.miaotu.http.bean.message;

public class AllMessages {


    @Override
    public String toString() {
        return "AllMessages{" +
                "acked=" + acked +
                ", body=" + body +
                ", chatType='" + chatType + '\'' +
                ", delivered=" + delivered +
                ", from='" + from + '\'' +
                ", listened=" + listened +
                ", msgId='" + msgId + '\'' +
                ", msgTime=" + msgTime +
                ", needGroupAck=" + needGroupAck +
                ", to='" + to + '\'' +
                ", type='" + type + '\'' +
                ", unread=" + unread +
                ", userName='" + userName + '\'' +
                '}';
    }

    /**
     * acked : false
     * body : {"message":"9999999"}
     * chatType : Chat
     * delivered : false
     * from : admin
     * listened : false
     * msgId : 765577097274984572
     * msgTime : 1595814608333
     * needGroupAck : false
     * to : 133632
     * type : TXT
     * unread : true
     * userName : admin
     */



    private boolean acked;
    private BodyBean body;
    private String chatType;
    private boolean delivered;
    private String from;
    private boolean listened;
    private String msgId;
    private long msgTime;
    private boolean needGroupAck;
    private String to;
    private String type;
    private boolean unread;
    private String userName;

    public boolean isAcked() {
        return acked;
    }

    public void setAcked(boolean acked) {
        this.acked = acked;
    }

    public BodyBean getBody() {
        return body;
    }

    public void setBody(BodyBean body) {
        this.body = body;
    }

    public String getChatType() {
        return chatType;
    }

    public void setChatType(String chatType) {
        this.chatType = chatType;
    }

    public boolean isDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public boolean isListened() {
        return listened;
    }

    public void setListened(boolean listened) {
        this.listened = listened;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public long getMsgTime() {
        return msgTime;
    }

    public void setMsgTime(long msgTime) {
        this.msgTime = msgTime;
    }

    public boolean isNeedGroupAck() {
        return needGroupAck;
    }

    public void setNeedGroupAck(boolean needGroupAck) {
        this.needGroupAck = needGroupAck;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isUnread() {
        return unread;
    }

    public void setUnread(boolean unread) {
        this.unread = unread;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public static class BodyBean {
        /**
         * message : 9999999
         */

        private String message;

        @Override
        public String toString() {
            return "BodyBean{" +
                    "message='" + message + '\'' +
                    '}';
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
