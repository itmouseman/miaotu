package com.widget.miaotu.common.utils;

import android.content.Context;
import android.os.Build;

import com.blankj.utilcode.util.ToastUtils;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.widget.miaotu.R;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.PermissionDef;

import java.util.List;

public class PermissionTool {


    public static void requestPermission(final Context context, final PermissionQuestListener listener,@PermissionDef String... permissionGroup) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (AndPermission.hasPermissions(context, permissionGroup)) {
                if (listener != null)
                    listener.onGranted();
                return;
            }

            // 没有权限，申请权限
            AndPermission.with(context)
                    .runtime()
                    .permission(permissionGroup)
                    .onGranted(new Action<List<String>>() {
                        @Override
                        public void onAction(List<String> data) {
                            if (listener != null)
                                listener.onGranted();
                        }
                    })
                    .onDenied(new Action<List<String>>() {
                        @Override
                        public void onAction(final List<String> data) {
                            if (AndPermission.hasAlwaysDeniedPermission(context, permissionGroup)) {
                                //替换为自己的对话框
                                new QMUIDialog.MessageDialogBuilder(context)
                                        .setTitle("权限申请")
                                        .setMessage(listener.onAlwaysDeniedData())
                                        .addAction("取消", new QMUIDialogAction.ActionListener() {
                                            @Override
                                            public void onClick(QMUIDialog dialog, int index) {
                                                dialog.dismiss();
                                                if (listener != null)
                                                    listener.onDenied(data);
                                            }
                                        })
                                        .addAction("确定", new QMUIDialogAction.ActionListener() {
                                            @Override
                                            public void onClick(QMUIDialog dialog, int index) {
                                                dialog.dismiss();
                                                AndPermission.with(context).runtime().setting().start(
                                                        1);
//                                                AndPermission.with(context)
//                                                        .runtime()
//                                                        .setting()
//                                                        .onComeback(new Setting.Action() {
//                                                            @Override
//                                                            public void onAction() {
//                                                                requestPermission(context, listener, permissionGroup);
//                                                            }
//                                                        })
//                                                        .start();
                                            }
                                        })
                                        .setCancelable(false)
                                        .setCanceledOnTouchOutside(false)
                                        .create(R.style.DialogTheme2).show();
                            } else {
                                //拒绝后重复请求
//                                requestPermission(context, listener, permissionGroup);
                                ToastUtils.showShort(listener.onAlwaysDeniedData());
                            }
                        }
                    })
                    .start();
        } else {
            if (listener != null)
                listener.onGranted();
        }
    }

    /**
     * 权限申请监听
     */
    public interface PermissionQuestListener{
        void onGranted(); //允许获得权限后操作
        void onDenied(List<String> data); //拒绝权限后操作
        String onAlwaysDeniedData(); //拒绝后不再提示信息
    }

}
