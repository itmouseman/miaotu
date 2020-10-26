package com.widget.miaotu.common.utils.dialog;

import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialog;

import com.widget.miaotu.R;
import com.widget.miaotu.common.utils.ui.NumberProgressBar;


/**
 * @author jiefu
 */
public class UploadAPPDialog extends AppCompatDialog {

    private TextView content, tvDownload;

    private LinearLayout llOne, llTwo;

    private NumberProgressBar download;

    public UploadAPPDialog(Context context, int theme) {
        super(context, theme);
        setContentView(R.layout.dialog_upload_app);
        setLayout();
        initView();
    }


    public TextView getContentView() {
        return content;
    }


    public NumberProgressBar getDownload() {
        return download;
    }

    public TextView getTvDownload() {
        return tvDownload;
    }

    /**
     * @param forceUpdate
     */
    public void setForceUpdate(boolean forceUpdate) {
        if (forceUpdate) {
            setCanceledOnTouchOutside(false);
            setCancelable(false);
        }
    }

    public void setLoading(boolean isLoading) {
        if (isLoading) {
            llOne.setVisibility(View.GONE);
            llTwo.setVisibility(View.VISIBLE);
        } else {
            llTwo.setVisibility(View.GONE);
            llOne.setVisibility(View.VISIBLE);
        }
    }


    private void setLayout() {
        getWindow().setGravity(Gravity.CENTER);
        getWindow().setBackgroundDrawable(null);
        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = WindowManager.LayoutParams.MATCH_PARENT;
        p.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(p);
    }

    private void initView() {
        content = findViewById(R.id.tv_upload_app_content);
        download = findViewById(R.id.download);
        tvDownload = findViewById(R.id.tv_download);
        llOne = findViewById(R.id.ll_one);
        llTwo = findViewById(R.id.ll_two);
    }
}
