package com.widget.miaotu.master.home.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.blankj.utilcode.util.SPStaticUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.orhanobut.logger.Logger;
import com.widget.miaotu.R;
import com.widget.miaotu.base.MBaseActivity;
import com.widget.miaotu.base.MVCControl;
import com.widget.miaotu.common.constant.SPConstant;
import com.widget.miaotu.common.utils.AddresSelectUtils;
import com.widget.miaotu.common.utils.AndroidUtils;
import com.widget.miaotu.common.utils.dialog.CurrencyAPPDialog;
import com.widget.miaotu.common.utils.other.LoadingUtil;
import com.widget.miaotu.http.BaseObserver;
import com.widget.miaotu.http.bean.BaseEntity;
import com.widget.miaotu.http.bean.IdAndTokenBean;
import com.widget.miaotu.http.bean.IdBean;
import com.widget.miaotu.http.bean.SaveGardenBean;
import com.widget.miaotu.http.retrofit.RetrofitFactory;
import com.widget.miaotu.http.retrofit.TransformerUi;
import com.widget.miaotu.master.mvp.BaseInfoDemandControl;
import com.widget.miaotu.master.mvp.BaseInfoDemandView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 添加苗圃
 */
public class BaseInfoDemandTechAddActivity extends MBaseActivity<BaseInfoDemandControl> implements BaseInfoDemandView {


    @BindView(R.id.et_demand_tech_add_name)
    EditText etDemandTechAddName;
    @BindView(R.id.tv_demand_tech_add_address)
    TextView tvDemandTechAddAddress;
    @BindView(R.id.et_demand_tech_add_address_details)
    EditText etDemandTechAddAddressDetails;
    @BindView(R.id.et_demand_tech_add_area)
    EditText etDemandTechAddArea;
    @BindView(R.id.et_demand_tech_add_contacts)
    EditText etDemandTechAddContacts;
    @BindView(R.id.et_demand_tech_add_mobile)
    EditText etDemandTechAddMobile;

    @BindView(R.id.iv_base_info_demand_tech_add_back)
    ImageView ivBaseInfoDemandTechAddBack;
    @BindView(R.id.tv_base_info_demand_tech_add)
    TextView tvBaseInfoDemandTechAdd;
    @BindView(R.id.et_demand_comment_name)
    EditText etDemandCommentName;
    @BindView(R.id.tv_demand_comment_address)
    TextView tvDemandCommentAddress;
    @BindView(R.id.ll_comment)
    LinearLayout llComment;

    @BindView(R.id.et_demand_tech_add_contacts2)
    EditText etDemandTechAddContacts2;
    @BindView(R.id.et_demand_tech_add_mobile2)
    EditText etDemandTechAddMobile2;
    @BindView(R.id.ll_contact2)
    LinearLayout llContact2;
    @BindView(R.id.et_demand_tech_add_contacts3)
    EditText etDemandTechAddContacts3;
    @BindView(R.id.et_demand_tech_add_mobile3)
    EditText etDemandTechAddMobile3;
    @BindView(R.id.ll_contact3)
    LinearLayout llContact3;
    @BindView(R.id.ll_contact_add)
    LinearLayout llContactAdd;
    @BindView(R.id.ll_nursery_delete)
    LinearLayout llNurseryDelete;

    private String type = "0";
    private String id = "";

    String province;
    String city;
    String district;
    private String companyId = "";
    private int initContacts = 1;

    @Override
    protected BaseInfoDemandControl createControl() {
        return new BaseInfoDemandControl();
    }

    @Override
    protected void initView() {

        if (!"1".equals(SPStaticUtils.getString(SPConstant.ISCOMPANY))) {//判断是不是企业
            llComment.setVisibility(View.VISIBLE);
        } else {
            llComment.setVisibility(View.GONE);
        }
        //传参
        type = getIntent().getStringExtra("type");
        id = getIntent().getStringExtra("id");

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_base_info_demand_tech_add_new;
    }

    @Override
    protected void initDetailData() {


        if ("1".equals(type)) {
            //请求苗木详情

            RetrofitFactory.getInstence().API().gardenDetail(new IdBean(Integer.parseInt(id))).compose(TransformerUi.setThread()).subscribe(new BaseObserver<SaveGardenBean>(this) {
                @Override
                protected void onSuccess(BaseEntity<SaveGardenBean> t) throws Exception {
                    Logger.e(t.getData().toString());
                    if (t.getStatus() == 100) {
                        //赋值
                        initDataViewShow(t.getData());
                    } else {
                        LoadingUtil.loadingClose(false, t.getMessage());
                    }

                }

                @Override
                protected void onFail(Throwable throwable) throws Exception {
                    Logger.e(throwable.getMessage());
                    LoadingUtil.loadingClose(false, "请求出错");
                }
            });
        }




    }

    private void initDataViewShow(SaveGardenBean data) {
        llNurseryDelete.setVisibility(View.VISIBLE);
        if (!AndroidUtils.isNullOrEmpty(data.getCompanyId()))
            companyId = data.getCompanyId() + "";
        etDemandTechAddName.setText(data.getGardenName() + "");
        province = data.getProvince() + "";
        city = data.getCity() + "";
        district = data.getDistrict() + "";
        tvDemandTechAddAddress.setText(data.getProvince() + data.getCity() + data.getDistrict() + "");
        etDemandTechAddAddressDetails.setText(data.getAddressDetail() + "");
        etDemandTechAddArea.setText(data.getArea() + "");
        etDemandTechAddContacts.setText(data.getContactOne() + "");
        etDemandTechAddMobile.setText(data.getMobileOne() + "");

        if (!AndroidUtils.isNullOrEmpty(data.getContactTwo()) && !AndroidUtils.isNullOrEmpty(data.getMobileTwo())) {
            llContact2.setVisibility(View.VISIBLE);
            initContacts = 2;
            etDemandTechAddContacts2.setText(data.getContactTwo() + "");
            etDemandTechAddMobile2.setText(data.getMobileTwo() + "");
        }
        if (!AndroidUtils.isNullOrEmpty(data.getContactThree()) && !AndroidUtils.isNullOrEmpty(data.getMobileThree())) {
            llContact3.setVisibility(View.VISIBLE);
            llContactAdd.setVisibility(View.GONE);
            initContacts = 3;
            etDemandTechAddContacts3.setText(data.getContactThree() + "");
            etDemandTechAddMobile3.setText(data.getMobileThree() + "");
        }
    }



    @OnClick({R.id.iv_base_info_demand_tech_add_back, R.id.tv_base_info_demand_tech_add, R.id.ll_base_info_demand_tech_address,
            R.id.ll_base_info_demand_tech_address2, R.id.ll_contact_add,R.id.ll_nursery_delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_base_info_demand_tech_add_back:
                finish();
                break;
            case R.id.tv_base_info_demand_tech_add:
                String name = etDemandTechAddName.getText().toString().trim();
                String address = tvDemandTechAddAddress.getText().toString().trim();
                String addressDetails = etDemandTechAddAddressDetails.getText().toString().trim();
                String area = etDemandTechAddArea.getText().toString().trim();
                String contacts = etDemandTechAddContacts.getText().toString().trim();
                String mobile = etDemandTechAddMobile.getText().toString().trim();

                String commentName = etDemandCommentName.getText().toString().trim();
                String commentAddress = tvDemandCommentAddress.getText().toString().trim();

                String contacts2 = etDemandTechAddContacts2.getText().toString().trim();
                String mobile2 = etDemandTechAddMobile2.getText().toString().trim();

                String contacts3 = etDemandTechAddContacts3.getText().toString().trim();
                String mobile3 = etDemandTechAddMobile3.getText().toString().trim();

                if ("0".equals(SPStaticUtils.getString(SPConstant.ISCOMPANY))) {
                    if (StringUtils.isEmpty(commentName) || StringUtils.isEmpty(commentAddress)) {
                        ToastUtils.showShort("请确认所有信息已填写完整!");
                        return;
                    }
                }


                if (StringUtils.isEmpty(name) || StringUtils.isEmpty(address) || StringUtils.isEmpty(addressDetails)
                        || StringUtils.isEmpty(area) || StringUtils.isEmpty(contacts) || StringUtils.isEmpty(mobile)) {
                    ToastUtils.showShort("请确认所有信息已填写完整!");
                    return;
                }

//                presenter.addTechInfo(address, addressDetails, name, contacts, mobile, area);
                if("1".equals(type)){
                    SaveGardenBean bean = new SaveGardenBean(SPStaticUtils.getString(SPConstant.AUTHORIZATION), commentAddress, commentName, name, province,
                            city, district, addressDetails, area, contacts,
                            mobile, contacts2, mobile2, contacts3, mobile3,
                            companyId,id);
                    Logger.e(bean.toString());
                    RetrofitFactory.getInstence().API().editGarden(bean).compose(TransformerUi.setThread()).subscribe(new BaseObserver<Object>(this) {
                        @Override
                        protected void onSuccess(BaseEntity<Object> t) throws Exception {
                            Logger.e(t.toString());
                            if (t.getStatus()==100){
                                AndroidUtils.Toast(BaseInfoDemandTechAddActivity.this,"编辑成功!");
                                finish();
                            }else {
                                LoadingUtil.loadingClose(false, "" + t.getMessage());
                            }
                        }

                        @Override
                        protected void onFail(Throwable throwable) throws Exception {
                            Logger.e(throwable.getMessage());
                        }
                    });


                }else{
                    SaveGardenBean bean = new SaveGardenBean(SPStaticUtils.getString(SPConstant.AUTHORIZATION), commentAddress, commentName, name, province,
                            city, district, addressDetails, area, contacts,
                            mobile, contacts2, mobile2, contacts3, mobile3,
                            "","");
                    LoadingUtil.loadingShow(this,"添加中...");
                    mControl.addTechInfoNew(bean);
                }

                break;
            case R.id.ll_base_info_demand_tech_address:
                hideInput();

                AddresSelectUtils.selectPICKview(getWindow().getDecorView().findViewById(android.R.id.content), BaseInfoDemandTechAddActivity.this,new AddresSelectUtils.SelectAddressCallBack() {
                    @Override
                    public void selectAddressBack(String mprovince, String mcity, String address) {
                        tvDemandTechAddAddress.setText(address);
                        province = mprovince;
                        city = mcity;
                        district = "---";
                    }
                });
                break;
            case R.id.ll_base_info_demand_tech_address2:
                hideInput();
                AddresSelectUtils.selectPICKview(getWindow().getDecorView().findViewById(android.R.id.content), BaseInfoDemandTechAddActivity.this,new AddresSelectUtils.SelectAddressCallBack() {
                    @Override
                    public void selectAddressBack(String province, String city, String address) {
                        tvDemandCommentAddress.setText(address);
                    }
                });
                break;
            case R.id.ll_contact_add:
                if (1 == initContacts) {
                    initContacts++;
                    llContact2.setVisibility(View.VISIBLE);
                } else if (2 == initContacts) {
                    initContacts++;
                    llContact3.setVisibility(View.VISIBLE);
                    llContactAdd.setVisibility(View.GONE);
                }
                break;
            case R.id.ll_nursery_delete:
                //弹框 删除该苗圃
                new CurrencyAPPDialog(BaseInfoDemandTechAddActivity.this, R.style.dialog_center,"确定删除该苗圃吗？",
                        "删除后该苗圃中的所有苗木信息将不能找回","执意删除","取消", getResourceColor(R.color.text_color_99),getResourceColor(R.color.color00cab8))
                        .setOnClickListener(new CurrencyAPPDialog.OnClickListener() {
                            @Override
                            public void onClick() {

                                RetrofitFactory.getInstence().API().deleteGarden(new IdAndTokenBean(id,SPStaticUtils.getString(SPConstant.AUTHORIZATION)))
                                        .compose(TransformerUi.setThread()).subscribe(new BaseObserver<Object>(BaseInfoDemandTechAddActivity.this) {
                                    @Override
                                    protected void onSuccess(BaseEntity<Object> t) throws Exception {

                                        if (t.getStatus()==100){
                                            //删除成功
//                                                LoadingUtil.loadingClose(true, "删除成功!");
                                            AndroidUtils.Toast(BaseInfoDemandTechAddActivity.this,"删除成功!");
                                            finish();
                                        }else {
                                            LoadingUtil.loadingClose(false, "" + t.getMessage());
                                        }
                                    }

                                    @Override
                                    protected void onFail(Throwable throwable) throws Exception {

                                    }
                                });

                            }
                        });
                break;
        }
    }

    @Override
    public void addTechInfoSuccess() {
        LoadingUtil.loadingClose(true, "添加成功!");
        TimePicker timePicker = new TimePicker(this);
        timePicker.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        },500);

    }

    @Override
    public void addTechInfoFail(String message) {
        LoadingUtil.loadingClose(false, message+"!");
    }
}
