package com.widget.miaotu.master.home.fragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.widget.miaotu.R;
import com.widget.miaotu.base.BaseFragment;
import com.widget.miaotu.common.utils.home.RecycleviewAdapterOfpopup;
import com.widget.miaotu.http.bean.CompanyInfoMeBean;
import com.widget.miaotu.http.bean.ImgUrlJavaBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * 首页的Fragment
 */

public class CompanyInfoFragment extends BaseFragment {


    private final CompanyInfoMeBean mData;
    @BindView(R.id.rv_image_qiyexingxiang_list)
    RecyclerView recyclerView;

    @BindView(R.id.tv_detail_contacts)
    TextView tvDetailContacts;
    @BindView(R.id.tv_detail_phone)
    TextView tvDetailPhone;
    @BindView(R.id.tv_detail_nursery_num)
    TextView tvDetailNurseryNum;
    @BindView(R.id.tv_detail_address)
    TextView tvDetailAddress;
    @BindView(R.id.tv_detail_desc)
    TextView tvDetailDesc;
    @BindView(R.id.ll_company_presentation)
    LinearLayout ll_company_presentation;//企业介绍
    @BindView(R.id.ll_company_visualize)
    LinearLayout ll_company_visualize;//企业形象


    public CompanyInfoFragment(CompanyInfoMeBean data) {
        mData = data;
    }

    @Override
    protected void initViewAndData(View view) {
        if (TextUtils.isEmpty(mData.getContact()) || mData.getContact() == null) {
            tvDetailContacts.setText("联系人：");

        } else {
            tvDetailContacts.setText("联系人：" + mData.getContact());
        }
        if (TextUtils.isEmpty(mData.getContact_mobile()) || mData.getContact_mobile() == null) {
            tvDetailPhone.setText("联系电话：");
        } else {
            tvDetailPhone.setText("联系电话：" + mData.getContact_mobile());

        }


        tvDetailNurseryNum.setText("苗圃数量：" + mData.getGardenCount() + "个");
        Logger.e(mData.toString());
        if (TextUtils.isEmpty(mData.getAddress()) || mData.getAddress() == null) {
            tvDetailAddress.setText("企业地址：");
        } else {
            tvDetailAddress.setText("企业地址：" + mData.getAddress());

        }

        if (TextUtils.isEmpty(mData.getIntroduction()) || mData.getIntroduction() == null) {
            tvDetailDesc.setText(" ");
            ll_company_presentation.setVisibility(View.GONE);
        } else {
            ll_company_presentation.setVisibility(View.VISIBLE);
            tvDetailDesc.setText("" + mData.getIntroduction());

        }

        if (!TextUtils.isEmpty(mData.getStyle_photos())) {
            ll_company_visualize.setVisibility(View.VISIBLE);
            Gson gson = new Gson();
            List<ImgUrlJavaBean> imgUrlJavaBeanList = gson.fromJson(mData.getStyle_photos(), new TypeToken<List<ImgUrlJavaBean>>() {
            }.getType());

            List<Object> list = new ArrayList<>();
            for (int i = 0; i < imgUrlJavaBeanList.size(); i++) {
                list.add(imgUrlJavaBeanList.get(i).getT_url());
            }
            //填充图片数据
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getFragmentContext());
            linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
            recyclerView.setLayoutManager(linearLayoutManager);


            recyclerView.setAdapter(new RecycleviewAdapterOfpopup(getFragmentContext(), R.layout.adapter_image2, list));

        } else {
            ll_company_visualize.setVisibility(View.GONE);
        }
    }


    @Override
    protected int getFragmentId() {
        return R.layout.fragment_company_info;
    }
}
