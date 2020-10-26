package com.widget.miaotu.common.utils;

import android.content.Context;
import android.graphics.Color;

import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;


/**
 * Created by chenbin on 2016/10/13.
 */

public class ToastUtil {
    private static Toast toast;
    public static int orange = 0xffFF4A43;

    public static void showLong(String text, Context context) {
        if (toast == null) {
            toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
        }
        toast.setText(text);
        toast.show();
    }

    public static void showShort(String text, Context context) {
        if (toast == null) {
            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        }
        toast.setText(text);
        toast.show();
    }

    public static void snackBar(View view, String msg) {
        final Snackbar snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_LONG);
        setSnackbarColor(snackbar, orange);
        snackbar.setActionTextColor(Color.WHITE)
                .setAction("知道了", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        snackbar.dismiss();
                    }
                })
                .show();
    }

    /**
     * 设置Snackbar背景颜色
     *
     * @param snackbar
     * @param backgroundColor
     */
    private static void setSnackbarColor(Snackbar snackbar, int backgroundColor) {
        View view = snackbar.getView();
        if (view != null) {
            view.setBackgroundColor(backgroundColor);
        }
    }
}
