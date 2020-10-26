package com.widget.miaotu.master.home.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.SPStaticUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.widget.miaotu.R;
import com.widget.miaotu.common.constant.SPConstant;
import com.widget.miaotu.common.utils.GlideUtils;
import com.widget.miaotu.common.utils.IntentUtils;
import com.widget.miaotu.http.bean.GardenHeadlinesBean;
import com.widget.miaotu.http.bean.ImgUrlJavaBean;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * author : heyang
 * e-mail :
 * date   : 2020/8/1715:08
 * desc   :
 * version: 1.0
 */
public class GardenHeadlinesAdapter extends BaseMultiItemQuickAdapter<GardenHeadlinesBean.ContentBean.ListBean, BaseViewHolder> {

    private final Activity context;

    public GardenHeadlinesAdapter(List<GardenHeadlinesBean.ContentBean.ListBean> data, Activity context) {
        super(data);
        this.context = context;
        addItemType(GardenHeadlinesBean.ContentBean.ListBean.ImageOnly, R.layout.item_garden_headlines_image_only);
        addItemType(GardenHeadlinesBean.ContentBean.ListBean.ImageAndText, R.layout.item_garden_headlines_image_and_text);
        addItemType(GardenHeadlinesBean.ContentBean.ListBean.ImageMini, R.layout.item_garden_headlines_image_mini);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, GardenHeadlinesBean.ContentBean.ListBean item) {
        switch (baseViewHolder.getItemViewType()) {
            case GardenHeadlinesBean.ContentBean.ListBean.ImageOnly:
                Gson gson = new Gson();
                List<ImgUrlJavaBean> imgUrlJavaBean = gson.fromJson(item.getPicUrl(), new TypeToken<List<ImgUrlJavaBean>>() {
                }.getType());
                GlideUtils.loadUrl(context, imgUrlJavaBean.get(0).getT_url(), baseViewHolder.getView(R.id.iv_item_gh_image_only));
                baseViewHolder.setText(R.id.tv_item_gh_image_only, item.getTitle());
                baseViewHolder.setText(R.id.iv_item_gh_image_only_bottom, item.getInfoFrom() + "  " + item.getViews() + "阅读" + "  " + TimeUtils.getChineseWeek(item.getTime()));
                onItemClick(baseViewHolder, item);
                break;
            case GardenHeadlinesBean.ContentBean.ListBean.ImageAndText:
                baseViewHolder.setText(R.id.tv_item_gh_img_and_text1, item.getTitle());
                baseViewHolder.setText(R.id.tv_item_gh_img_and_text2, item.getInfoFrom() + "  " + item.getViews() + "阅读" + "  " + TimeUtils.getChineseWeek(item.getTime()));
                Gson gson1 = new Gson();
                List<ImgUrlJavaBean> imgUrlJavaBean1 = gson1.fromJson(item.getPicUrl(), new TypeToken<List<ImgUrlJavaBean>>() {
                }.getType());
                GlideUtils.loadUrl(context, imgUrlJavaBean1.get(0).getT_url(), baseViewHolder.getView(R.id.iv_item_gh_img_and_text));
                onItemClick(baseViewHolder, item);
                break;
            case GardenHeadlinesBean.ContentBean.ListBean.ImageMini:
                baseViewHolder.setText(R.id.tv_gh_img_mini_title, item.getTitle());
                Gson gson2 = new Gson();
                List<ImgUrlJavaBean> imgUrlJavaBean2 = gson2.fromJson(item.getPicUrl(), new TypeToken<List<ImgUrlJavaBean>>() {
                }.getType());
                if (!TextUtils.isEmpty(imgUrlJavaBean2.get(0).getT_url())) {
                    GlideUtils.loadUrl(context, imgUrlJavaBean2.get(0).getT_url(), baseViewHolder.getView(R.id.iv_tv_gh_img_mini1));
                }
                if (!TextUtils.isEmpty(imgUrlJavaBean2.get(1).getT_url())) {
                    GlideUtils.loadUrl(context, imgUrlJavaBean2.get(1).getT_url(), baseViewHolder.getView(R.id.iv_tv_gh_img_mini2));
                }
                if (!TextUtils.isEmpty(imgUrlJavaBean2.get(2).getT_url())) {
                    GlideUtils.loadUrl(context, imgUrlJavaBean2.get(2).getT_url(), baseViewHolder.getView(R.id.iv_tv_gh_img_mini3));
                }
                baseViewHolder.setText(R.id.tv_gh_img_mini_bottom, item.getInfoFrom() + "  " + item.getViews() + "阅读" + "  " + TimeUtils.getChineseWeek(item.getTime()));
                onItemClick(baseViewHolder, item);
                break;
        }
    }

    private void onItemClick(BaseViewHolder baseViewHolder, GardenHeadlinesBean.ContentBean.ListBean item) {
        baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mUrl = item.getDetailUrl() + "&userId=" + SPStaticUtils.getString(SPConstant.USER_ID) + "&modelType=android" + "&token=" + SPStaticUtils.getString(SPConstant.NEW_TOKEN);
                Logger.e(mUrl);
                IntentUtils.gotoWebView(context, mUrl, item.getTitle() + "");
            }
        });
    }
}
