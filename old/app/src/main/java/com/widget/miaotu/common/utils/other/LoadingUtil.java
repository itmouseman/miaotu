package com.widget.miaotu.common.utils.other;

import android.app.Activity;

import com.xiasuhuei321.loadingdialog.view.LoadingDialog;


public class LoadingUtil {

    private static LoadingDialog dialog;


    /**
     * 加载加载框
     */
    public static void loadingShow(Activity activity,String hint) {
        dialog = new LoadingDialog(activity);
        dialog.closeFailedAnim();
        dialog.closeSuccessAnim();
        dialog.setLoadingText(hint);
        dialog.show();
    }

    public static void loadingClose(final boolean isSuccess, final String hint) {
        if (dialog != null) {
            if (isSuccess) {
                dialog.setSuccessText(hint);
                dialog.loadSuccess();
            } else {
                dialog.setFailedText(hint);
                dialog.loadFailed();
            }
        }
    }

    public static void dissmiss() {
        if (dialog != null) {
            dialog.close();
        }
    }
}
