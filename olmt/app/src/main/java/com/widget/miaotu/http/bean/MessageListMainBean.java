package com.widget.miaotu.http.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class MessageListMainBean {
    /**
     * {count=0.0,
     * list={
     * Z=[
     * {companyName=测试,
     * headUrl=http://oss-cn-beijing.aliyuncs.com/miaotu1/ess-7226954544430175564.jpeg,
     * id=0.0, isPass=2.0,
     * looked=null,
     * nickname=重新出发, userId=88688.0}
     * ]
     * },
     * userId=91844.0}
     * }
     */
    private Double count;
    private String userId;

    private Map<String, List<MessageUserInfo>> list;



    @Override
    public String toString() {
        return "MessageListMainBean{" +
                "count=" + count +
                ", userId='" + userId + '\'' +
                ", list=" + list +
                '}';
    }

    public Double getCount() {
        return count;
    }

    public void setCount(Double count) {
        this.count = count;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public Map<String, List<MessageUserInfo>> getList() {
        return list;
    }

    public void setList(Map<String, List<MessageUserInfo>> list) {
        this.list = list;
    }

    public static class  MessageUserInfo  implements Serializable {

        /**
         * companyName : 测试
         * headUrl : http://oss-cn-beijing.aliyuncs.com/miaotu1/ess-7226954544430175564.jpeg
         * id : 0
         * isPass : 2
         * looked : null
         * nickname : 重新出发
         * userId : 88688
         */

        private String companyName;
        private String headUrl;
        private int id;
        private int isPass;
        private Object looked;
        private String nickname;
        @SerializedName("userId")
        private String userIdX;
        private String lastMessage;
        private int unreadMsgCount;
        private long msgTime;


        @Override
        public String toString() {
            return "MessageUserInfo{" +
                    "companyName='" + companyName + '\'' +
                    ", headUrl='" + headUrl + '\'' +
                    ", id=" + id +
                    ", isPass=" + isPass +
                    ", looked=" + looked +
                    ", nickname='" + nickname + '\'' +
                    ", userIdX=" + userIdX +
                    ", lastMessage='" + lastMessage + '\'' +
                    ", unreadMsgCount='" + unreadMsgCount + '\'' +
                    ", msgTime='" + msgTime + '\'' +
                    '}';
        }

        public int getUnreadMsgCount() {
            return unreadMsgCount;
        }

        public void setUnreadMsgCount(int unreadMsgCount) {
            this.unreadMsgCount = unreadMsgCount;
        }

        public long getMsgTime() {
            return msgTime;
        }

        public void setMsgTime(long msgTime) {
            this.msgTime = msgTime;
        }

        public String getLastMessage() {
            return lastMessage;
        }

        public void setLastMessage(String lastMessage) {
            this.lastMessage = lastMessage;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getHeadUrl() {
            return headUrl;
        }

        public void setHeadUrl(String headUrl) {
            this.headUrl = headUrl;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIsPass() {
            return isPass;
        }

        public void setIsPass(int isPass) {
            this.isPass = isPass;
        }

        public Object getLooked() {
            return looked;
        }

        public void setLooked(Object looked) {
            this.looked = looked;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getUserIdX() {
            return userIdX;
        }

        public void setUserIdX(String userIdX) {
            this.userIdX = userIdX;
        }
    }
}
