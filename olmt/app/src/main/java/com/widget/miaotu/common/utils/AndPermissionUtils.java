package com.widget.miaotu.common.utils;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.widget.miaotu.common.utils.other.RuntimeRationale;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.PermissionDef;

import java.util.List;

public class AndPermissionUtils {
    PermissionsCallBack permissionsCallBack;
    Activity mActivity;



    public void requestPermission(Activity mActivity, PermissionsCallBack permissionsCallBack,@PermissionDef String... permissions) {
        this.mActivity = mActivity;
        this.permissionsCallBack = permissionsCallBack;
        AndPermission.with(mActivity)
                .runtime()
                .permission(permissions)
                .rationale(new RuntimeRationale())
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
                        if (permissionsCallBack!=null){
                            permissionsCallBack.permissionsSuc();
                        }
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(@NonNull List<String> permissions) {
                        if (permissionsCallBack!=null){
                            permissionsCallBack.permissionFail(permissions);
                        }

                    }
                })
                .start();
    }






    public interface PermissionsCallBack {
        void permissionsSuc();
        void permissionFail(@NonNull List<String> permissions);
    }
}
