package com.widget.miaotu.common.utils.other;

/**
 * 分享链接信息
 */
public class ShareLinkInfo {
    public String title;
    public String titleUrl;
    public String content;
    public String siteName;
    public String siteUrl;
    public String imageUrl;
    public String url;

    public static ShareLinkInfo create(String title, String titleUrl, String content, String imageUrl, String url) {
        ShareLinkInfo shareLinkInfo = new ShareLinkInfo();
        shareLinkInfo.title = title;
        shareLinkInfo.titleUrl = titleUrl;
        shareLinkInfo.content = content;
        shareLinkInfo.imageUrl = imageUrl;
        shareLinkInfo.url = url;
        return shareLinkInfo;
    }
}
