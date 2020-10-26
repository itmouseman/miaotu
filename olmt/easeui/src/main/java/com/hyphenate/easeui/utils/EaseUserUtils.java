package com.hyphenate.easeui.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.EaseUI.EaseUserProfileProvider;
import com.hyphenate.easeui.cache.UserCacheInfo;
import com.hyphenate.easeui.cache.UserCacheManager;
import com.hyphenate.easeui.domain.EaseUser;

public class EaseUserUtils {
    
    static EaseUserProfileProvider userProvider;
    
    static {
        userProvider = EaseUI.getInstance().getUserProfileProvider();
    }

    /**
     * get EaseUser according username
     * @param username
     * @return
     */
    public static EaseUser getUserInfo(String username){
        if(userProvider != null)
            return userProvider.getUser(username);

        return null;
    }
    
    /**
     * set user avatar
     * @param username
     */
    public static void setUserAvatar(Context context, String username, ImageView imageView){
//    	EaseUser user = getUserInfo(username);
        UserCacheInfo user = UserCacheManager.get(username);
        //设置图片圆角角度
        RequestOptions mRequestOptions = RequestOptions.circleCropTransform();
        if(user != null && !TextUtils.isEmpty(user.getAvatarUrl())){
                Glide.with(context).load(user.getAvatarUrl())
                        .apply(RequestOptions.placeholderOf(R.drawable.ic_ease_default_avatar)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)).apply(mRequestOptions)
                        .into(imageView);
        }else{

            Glide.with(context).load(R.drawable.ic_ease_default_avatar).apply(mRequestOptions).into(imageView);
        }
    }
    
    /**
     * set user's nickname
     */
    public static void setUserNick(String username,TextView textView){
        if(textView != null){
        	EaseUser user = getUserInfo(username);
        	if(user != null && user.getNickname() != null){
        		textView.setText(user.getNickname());
        	}else{
        		textView.setText(username);
        	}
        }
    }



    /**
     *  自己添加的属性
     * set user sex
     * 根据UserName设置控件性别
     * @param username
     */
    public static void setUserSex(Context context, String username, ImageView imageView){
        EaseUser user = getUserInfo(username);
        if(user != null && user.getSex() != 0){
            //1是男，2是女
            if (user.getSex() == 1) {
                Glide.with(context).load(R.drawable.ic_launcher).into(imageView);
            }else if (user.getSex() == 2){
                Glide.with(context).load(R.drawable.ic_launcher).into(imageView);
            }
        }
    }


}
