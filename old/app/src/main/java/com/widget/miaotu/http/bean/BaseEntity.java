package com.widget.miaotu.http.bean;

import java.io.Serializable;

/**
 * Created by jbwl on 2018/10/10 18:46.
 */

public class BaseEntity<T> implements Serializable {
    private int status;


    private String message;
    private T data;

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

    public T getData() {
        if (data==null){
            data = (T) "data等于Null";
        }
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    @Override
    public String toString() {
        return "BaseEntity{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
