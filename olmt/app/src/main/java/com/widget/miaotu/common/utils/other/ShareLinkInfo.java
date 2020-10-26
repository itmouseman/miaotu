package com.widget.miaotu.common.utils.other;

/**
 * 分享链接信息
 */
public class ShareLinkInfo {
    public String title;
    public String titleUrl;
    public String content;

    public String imageUrl;
    public String contentUrl;



    public ShareLinkInfo(String title, String titleUrl, String content, String imageUrl, String contentUrl) {
        this.title = title;
        this.titleUrl = titleUrl;
        this.content = content;
        this.imageUrl = imageUrl;
        this.contentUrl = contentUrl;
    }
}
