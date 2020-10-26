package com.widget.miaotu.http.bean.head;

public class HeadHaoYouEdit {
    private String id; //好友申请ID
    private String isPass; ///	 是否通过 2. 通过 3. 忽略

    public HeadHaoYouEdit(String id, String isPass) {
        this.id = id;
        this.isPass = isPass;
    }
}
