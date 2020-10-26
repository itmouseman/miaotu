package com.widget.miaotu.common.utils.other;

import android.graphics.Bitmap;
import android.text.TextUtils;

import com.widget.miaotu.BaseApplication;
import com.widget.miaotu.common.utils.ToastUtil;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;

/**
 * Created by jbwl on 2018/11/24 10:47.
 */

public class ShareUtils {
    /**
     * 分享链接
     */
    public static void shareLink( String plat, ShareLinkInfo shareLinkInfo) {
        if (TextUtils.isEmpty(plat) || shareLinkInfo == null) {
            return;
        }
        Platform platform = ShareSDK.getPlatform(plat);
        Platform.ShareParams shareParams = new Platform.ShareParams();
        shareParams.setShareType(Platform.SHARE_WEBPAGE);
        shareParams.setTitle(shareLinkInfo.title);
        shareParams.setTitleUrl(shareLinkInfo.titleUrl);
        if (!TextUtils.isEmpty(shareLinkInfo.content)) {
            shareParams.setText(shareLinkInfo.content);
        } else {
            shareParams.setText(shareLinkInfo.title);
        }
        shareParams.setSite(TextUtils.isEmpty(shareLinkInfo.siteName) ? "苗途" : shareLinkInfo.siteName);
        // site是分享此内容的网站名称，仅在QQ空间使用
        shareParams.setSiteUrl(shareLinkInfo.url);
        shareParams.setImageUrl(shareLinkInfo.imageUrl);

        shareParams.setUrl(shareLinkInfo.url);
        platform.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                ToastUtil.showShort("分享成功", BaseApplication.instance());
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                ToastUtil.showShort("分享失败",BaseApplication.instance());
            }

            @Override
            public void onCancel(Platform platform, int i) {
                ToastUtil.showShort("取消分享",BaseApplication.instance());
            }
        });
        platform.share(shareParams);
    }


    /**
     * 分享链接
     */
    public static void shareLinkBitMap(Bitmap bitmap,String plat, ShareLinkInfo shareLinkInfo) {
        if (TextUtils.isEmpty(plat) || shareLinkInfo == null) {
            return;
        }
        Platform platform = ShareSDK.getPlatform(plat);
        Platform.ShareParams shareParams = new Platform.ShareParams();
        shareParams.setShareType(Platform.SHARE_WEBPAGE);
        shareParams.setTitle(shareLinkInfo.title);
        shareParams.setTitleUrl(shareLinkInfo.titleUrl);
        if (!TextUtils.isEmpty(shareLinkInfo.content)) {
            shareParams.setText(shareLinkInfo.content);
        } else {
            shareParams.setText(shareLinkInfo.title);
        }
        shareParams.setSite(TextUtils.isEmpty(shareLinkInfo.siteName) ? "苗途" : shareLinkInfo.siteName);
        // site是分享此内容的网站名称，仅在QQ空间使用
        shareParams.setSiteUrl(shareLinkInfo.url);
//        shareParams.setImageUrl(shareLinkInfo.imageUrl);
        shareParams.setImageData(bitmap);
        shareParams.setUrl(shareLinkInfo.url);
        platform.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                ToastUtil.showShort("分享成功", BaseApplication.instance());
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                ToastUtil.showShort("分享失败",BaseApplication.instance());
            }

            @Override
            public void onCancel(Platform platform, int i) {
                ToastUtil.showShort("取消分享",BaseApplication.instance());
            }
        });
        platform.share(shareParams);
    }
}
