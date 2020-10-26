package com.widget.miaotu.common.utils.other;

public interface DownloadListener {

    void onProgress(int progress);

    void onSuccess(String url);

    void onFailed();

    void onPaused();

    void onCanceled();
}
