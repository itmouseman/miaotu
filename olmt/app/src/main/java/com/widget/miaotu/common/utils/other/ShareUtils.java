package com.widget.miaotu.common.utils.other;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.blankj.utilcode.util.ToastUtils;
import com.mob.MobSDK;
import com.mob.tools.utils.BitmapHelper;
import com.orhanobut.logger.Logger;
import com.widget.miaotu.BaseApplication;
import com.widget.miaotu.R;
import com.widget.miaotu.master.mine.activity.InvitFirendActivity;


import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
import cn.sharesdk.wechat.friends.Wechat;

import static android.content.ContentValues.TAG;

/**
 * Created by jbwl on 2018/11/24 10:47.
 */

public class ShareUtils {
    /**
     * 一键分享
     */
    public static void shareLink(int id, ShareLinkInfo shareLinkInfo) {
        //一键分享
        OnekeyShare oks = new OnekeyShare();
// title标题，微信、QQ和QQ空间等平台使用
        oks.setTitle("分享");
// titleUrl QQ和QQ空间跳转链接
        oks.setTitleUrl("http://sharesdk.cn");
// text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
// setImageUrl是网络图片的url
        oks.setImageUrl("https://hmls.hfbank.com.cn/hfapp-api/9.png");
// url在微信、Facebook等平台中使用
        oks.setUrl("https://www.miaoto.net/zmh/H5Page/supplyDetail/supplyDetail.html?id=" + id);
// 启动分享GUI
        oks.show(MobSDK.getContext());

    }

    /**
     * 单独分享
     *
     * 企业id
     */
    public static void shareSingeLine(String id, String mShareType, ShareLinkInfo shareLinkInfo) {

        Platform platform = ShareSDK.getPlatform(mShareType);

        Platform.ShareParams shareParams = new Platform.ShareParams();
        shareParams.setText(shareLinkInfo.content);
        shareParams.setTitle(shareLinkInfo.title);

        shareParams.setUrl("https://www.miaoto.net/zmh/H5Page/companyDetails/companyDetails.html?id=" + id + "&url=https://apps.apple.com/cn/app/%E8%8B%97%E9%80%94/id1080191917?uo=4");
        shareParams.setImageUrl(shareLinkInfo.imageUrl);

        shareParams.setShareType(Platform.SHARE_WEBPAGE);
        platform.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

                ToastUtils.showShort("分享成功");
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {

                ToastUtils.showShort("分享失败");
            }

            @Override
            public void onCancel(Platform platform, int i) {
                ToastUtils.showShort("分享取消");
            }
        });
        platform.share(shareParams);
    }


    /**
     * 分享小程序 --  单个分享
     * 注意
     * Wechat {
     * appId "wx7efd0146f0a682ea"
     * appSecret "2f56ee25e57ee797d797c10a01ab70ae"
     * userName "gh_afb25ac019c9"
     * path "pages/index/index.html?id=1"
     * withShareTicket true
     * miniprogramType 0
     * bypassApproval false
     * enable true
     * }
     */
    public static void shareMiniProgram(int id, ShareLinkInfo shareLinkInfo) {
        Logger.e("分享的ID为  = " + id);
        Platform sina2 = ShareSDK.getPlatform(Wechat.NAME);
        Platform.ShareParams SP2 = new Platform.ShareParams();
        SP2.setWxUserName("gh_e2f5ebc0d62c");  //小程序原始ID
        SP2.setWxPath("pages/my/collection_detail/collection_detail?id=" + id);//分享小程序页面路径
        SP2.setShareType(Platform.SHARE_WXMINIPROGRAM);//分享小程序类型
        SP2.setTitle(shareLinkInfo.title);
        SP2.setText(shareLinkInfo.content);
        SP2.setImageUrl(shareLinkInfo.imageUrl);
        SP2.setUrl("https://www.miaoto.net/zmh/H5Page/supplyDetail/supplyDetail.html?id=" + id + "&url=https://apps.apple.com/cn/app/%E8%8B%97%E9%80%94/id1080191917?uo=4");

        sina2.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                ToastUtils.showShort("分享成功");
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {

                ToastUtils.showShort("分享失败");
            }

            @Override
            public void onCancel(Platform platform, int i) {

                ToastUtils.showShort("分享取消");
            }
        });
        sina2.share(SP2);
    }

    /**
     * 分享小程序 --  一键分享
     * Wechat {
     * appId "wx7efd0146f0a682ea"
     * appSecret "2f56ee25e57ee797d797c10a01ab70ae"
     * userName "gh_afb25ac019c9"
     * path "pages/index/index.html?id=1"
     * withShareTicket true
     * miniprogramType 0
     * bypassApproval false
     * enable true
     * }
     */
    public static void shareMiniPrograms(int id, ShareLinkInfo shareLinkInfo) {
        OnekeyShare oks = new OnekeyShare();
        oks.setTitle("标题测试");
        oks.setText("l ");
        oks.setImageUrl("http://a3.att.hudong.com/68/61/300000839764127060614318218_950.jpg");
        oks.setUrl("https://www.miaoto.net/zmh/H5Page/supplyDetail/supplyDetail.html?id=" + id + "&url=https://apps.apple.com/cn/app/%E8%8B%97%E9%80%94/id1080191917?uo=4");

        oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
            @Override
            public void onShare(Platform platform, Platform.ShareParams shareParams) {
                Logger.e(platform.getName());
                if (platform.getName().equals("Wechat")) {
                    shareParams.setShareType(Platform.SHARE_WXMINIPROGRAM);//分享小程序类型
                    shareParams.setWxUserName("gh_e2f5ebc0d62c");//配置小程序原始ID，前面有截图说明
                    shareParams.setWxPath("pages/my/collection_detail/collection_detail?id=" + id);//分享小程序页面的具体路径，前面截图从微信小程序开发工具中可以直接复制
                }
            }
        });
        oks.show(MobSDK.getContext());
    }


    /**
     * 单独分享图片
     * <p>
     * https://www.mob.com/wiki/detailed/?wiki=ShareSDK_Android_APISHARE_title_dsfptfxcssm&id=14
     */
    public static void shareBitmap(Bitmap bitmap, String mShareType, ShareLinkInfo shareLinkInfo) {
        Platform platform = ShareSDK.getPlatform(mShareType);

        if (platform.isClientValid()) {
            Log.e(TAG, "QQshareImage: " + "有QQ客户端");
        } else {
            Log.e(TAG, "QQshareImage: " + "未安装QQ客户端");

        }
        Platform.ShareParams shareParams = new Platform.ShareParams();
        shareParams.setText("我这是测试的分享\n文本d当发送到发时发送到发大发\n什么的发送到\n发送到发\n大发发送到发送到发法萨法");
        shareParams.setTitle("好友邀请");
        shareParams.setFilePath(Environment.getExternalStoragePublicDirectory
                (Environment.DIRECTORY_DOWNLOADS) + "/werr.docx");
//        shareParams.setTitleUrl("https://www.baidu.com");
//        shareParams.setUrl("https://www.baidu.com");
        shareParams.setImageUrl("http://a3.att.hudong.com/68/61/300000839764127060614318218_950.jpg");
//      测试gif动图
//        shareParams.setImageUrl("http://qnwj.duomihongbao.cn/emoticon/su39Bzb0YYi9fKqxK2w.gif");
        shareParams.setImageData(bitmap);
        shareParams.setShareType(Platform.SHARE_IMAGE);
//        shareParams.setShareType(Platform.SHARE_FILE);
        platform.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                Log.e(TAG, "分享成功==》 " + hashMap.toString());
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                Log.e(TAG, "分享失败==》 " + throwable.getMessage());
            }

            @Override
            public void onCancel(Platform platform, int i) {
                Log.e(TAG, "分享取消==》 ");
            }
        });
        platform.share(shareParams);
    }


    /**
     * //一键分享
     */

    public static void shareBitmaps(Context context, Bitmap bitmap) {
//        Bitmap img = BitmapFactory.decodeResource(this.getResources(), R.drawable.linkcard);
        OnekeyShare oks = new OnekeyShare();
// title标题，微信、QQ和QQ空间等平台使用
//        oks.setTitle("分享");
//// titleUrl QQ和QQ空间跳转链接
//        oks.setTitleUrl("http://sharesdk.cn");
//// text是分享文本，所有平台都需要这个字段
//        oks.setText("我是分享文本");
//// setImageUrl是网络图片的url
//        oks.setImageUrl("https://hmls.hfbank.com.cn/hfapp-api/9.png");
//// url在微信、Facebook等平台中使用
//        oks.setUrl("https://www.miaoto.net/zmh/H5Page/supplyDetail/supplyDetail.html?id=" + id);

        try {
            String path = BitmapHelper.saveBitmap(context, bitmap);
            oks.setImagePath(path);
            // 启动分享GUI
            oks.show(MobSDK.getContext());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }


}
