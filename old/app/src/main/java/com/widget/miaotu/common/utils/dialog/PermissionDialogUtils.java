package com.widget.miaotu.common.utils.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;

import androidx.appcompat.app.AlertDialog;

import com.widget.miaotu.R;
import com.yanzhenjie.permission.runtime.Permission;

import java.util.List;

public class PermissionDialogUtils {


    /**
     * Display setting dialog.
     */
    public static void showSettingDialog(Context context, final List<String> permissions,PermissionSetCallback permissionSetCallback) {
        List<String> permissionNames = Permission.transformText(context, permissions);
        String message = context.getString(R.string.message_permission_always_failed,
                TextUtils.join("\n", permissionNames));

        new AlertDialog.Builder(context).setCancelable(false)
                .setTitle("权限申请")
                .setMessage(message)
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (permissionSetCallback!=null){
                            permissionSetCallback.setPermission();
                        }

                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }


    public interface PermissionSetCallback {
        void setPermission();
    }

}
