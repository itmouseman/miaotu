package com.widget.miaotu.master.home.fragment;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.widget.miaotu.R;
import com.widget.miaotu.base.BaseFragment;

import butterknife.BindView;


/**
 * 首页的Fragment
 */

public class CompanyInfoFragment extends BaseFragment {




    @BindView(R.id.rv_list)
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
    String userId, contacts, phone, num, address, desc;



    public CompanyInfoFragment(String contacts, String phone, String num, String address, String desc, String pic) {
        this.contacts = contacts;
        this.phone = phone;
        this.num = num;
        this.address = address;
        this.desc = desc;
//        if (!AndroidUtils.isNullOrEmpty(pic)) {
//            mData = JSON.parseArray(pic, NewsInfoCoverBean.class);
//        }
    }

    @Override
    protected void initViewAndData(View view) {
        tvDetailContacts.setText("联系人："+contacts);
        tvDetailPhone.setText("联系电话："+phone);
        tvDetailNurseryNum.setText("苗圃数量："+num+"个");
        tvDetailAddress.setText("苗圃地址："+address);
        tvDetailDesc.setText(""+desc);
    }

    @Override
    protected int getFragmentId() {
        return R.layout.fragment_company_info;
    }
}
