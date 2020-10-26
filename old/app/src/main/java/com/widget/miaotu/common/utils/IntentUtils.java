package com.widget.miaotu.common.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.widget.miaotu.BaseApplication;
import com.widget.miaotu.common.CommonWebViewActivity;
import com.widget.miaotu.common.constant.SPConstant;

import java.io.File;

/**
 * 页面跳转
 */
public class IntentUtils {


    public static void gotoWebView(Activity activity, String url, String title) {
        String[] key = {SPConstant.WEB_URL, SPConstant.WEB_TITLE};
        String[] value = {url, title};
        IntentUtils.startIntent(activity, CommonWebViewActivity.class, key, value);
    }

    public static void gotoPersonalPage(final Context context, ImageView iv, final String userId, final int sourceType) {
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mAppToken = SpUtil.getStringSF(BaseApplication.instance(), SPConstant.AUTHORIZATION);
//                if (TextUtils.isEmpty(mAppToken)) {
//                    IntentUtils.startIntent(context, FirstLoginActivity.class);
//                } else{
////                    String[] key = {Key_Name.USERID, Key_Name.VISITOR_TYPE};
////                    String[] value = {userId, sourceType + ""};
////                    startIntent(context, PersonalPageActivity.class, key, value);
//                    String[] key = {Key_Name.USERID};
//                    String[] value = {userId};
//                    IntentUtils.startIntent(context, BmroActivity.class, key, value);
//                }


            }
        });
    }

    /**
     * 进行页面跳转
     *
     * @param clzz
     */
    public static void startIntent(Activity context, Class<?> clzz, String[] keys, String[] values) {
        Intent intent = new Intent(context, clzz);
        if (values != null && values.length > 0) {
            for (int i = 0; i < values.length; i++) {
                if (!TextUtils.isEmpty(keys[i]) && !TextUtils.isEmpty(values[i])) {
                    intent.putExtra(keys[i], values[i]);
                }
            }
        }
        context.startActivity(intent);
    }

    public static void startIntent1(Activity context, Class<?> clzz, String key, String value) {
        Intent intent = new Intent(context, clzz);
        intent.putExtra(key, value);
        context.startActivity(intent);
    }

    public static void startIntent1(Context context, Class<?> clzz, String key, String value) {
        Intent intent = new Intent(context, clzz);
        intent.putExtra(key, value);
        context.startActivity(intent);
    }

    /**
     * 进行页面跳转
     *
     * @param clzz
     */
    public static void startIntent(Context context, Class<?> clzz, String[] keys, String[] values) {
        Intent intent = new Intent(context, clzz);
        if (values != null && values.length > 0) {
            for (int i = 0; i < values.length; i++) {
                if (!TextUtils.isEmpty(keys[i]) && !TextUtils.isEmpty(values[i])) {
                    intent.putExtra(keys[i], values[i]);
                }
            }
        }
        context.startActivity(intent);
    }

    /**
     * startActivityForResult的返回
     */
    public static void setResult(Activity context, String[] keys, String[] values, int requestCode) {

        Intent intent = new Intent();
        if (values != null && values.length > 0) {
            for (int i = 0; i < values.length; i++) {
                if (!TextUtils.isEmpty(keys[i]) && !TextUtils.isEmpty(values[i])) {
                    intent.putExtra(keys[i], values[i]);
                }
            }
        }
        context.setResult(requestCode, intent);
        context.finish();

    }

    /**
     * 以startActivityForResult的方式进行页面跳转
     *
     * @param clzz
     */
    public static void startIntent(Activity context, Class<?> clzz, String[] keys, String[] values, int requestCode) {
        Intent intent = new Intent(context, clzz);
        if (values != null && values.length > 0) {
            for (int i = 0; i < values.length; i++) {
                if (!TextUtils.isEmpty(keys[i]) && !TextUtils.isEmpty(values[i])) {
                    intent.putExtra(keys[i], values[i]);
                }
            }
        }
        context.startActivityForResult(intent, requestCode);
    }

    public static void startIntent(Activity context, Class<?> clzz) {
        startIntent(context, clzz, null, null);
    }

    public static void startIntent(Context context, Class<?> clzz) {
        startIntent(context, clzz, null, null);
    }

    /**
     * 打电话
     *
     * @param context
     * @param tel
     */
    public static void openCall(Context context, String tel) {


        tel = tel.replaceAll("-", "");

        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + tel);
        intent.setData(data);
        context.startActivity(intent);

    }

    /**
     * 拨打电话（直接拨打电话）
     * @param phoneNum 电话号码
     */


    /**
     * 根据Uri返回文件绝对路径
     * 兼容了file:///开头的 和 content://开头的情况
     */
    public static String getRealFilePathFromUri(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_FILE.equalsIgnoreCase(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equalsIgnoreCase(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    /**
     * 检查文件是否存在
     */
    public static String checkDirPath(String dirPath) {
        if (TextUtils.isEmpty(dirPath)) {
            return "";
        }
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dirPath;
    }

    /**
     * 删除文件
     */
    public static void deleteDirPath(String dirPath) {
        if (!TextUtils.isEmpty(dirPath)) {
            File dir = new File(dirPath);
            if (dir.exists()) {
                dir.delete();
            }
        }
    }
}
