package com.widget.miaotu.master.home.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.SPStaticUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.orhanobut.logger.Logger;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.widget.miaotu.BaseApplication;
import com.widget.miaotu.R;
import com.widget.miaotu.base.MBaseActivity;
import com.widget.miaotu.base.MVCControl;
import com.widget.miaotu.common.constant.SPConstant;
import com.widget.miaotu.common.utils.AndPermissionUtils;
import com.widget.miaotu.common.utils.AndroidUtils;
import com.widget.miaotu.common.utils.FileUtil;
import com.widget.miaotu.common.utils.dialog.PermissionDialogUtils;
import com.widget.miaotu.common.utils.dialog.VipAPPDialog;
import com.widget.miaotu.common.utils.ui.GlideEngine;
import com.widget.miaotu.common.utils.ui.TakePhotoDialog;
import com.widget.miaotu.http.BaseObserver;
import com.widget.miaotu.http.bean.BaseEntity;
import com.widget.miaotu.http.bean.DemandSpecNewBean;
import com.widget.miaotu.http.bean.HomeMmSpecJavaBean;
import com.widget.miaotu.http.bean.NewsInfoCoverBean;
import com.widget.miaotu.http.bean.SaveSeedBean;
import com.widget.miaotu.http.bean.SeedChooseInfoBean;
import com.widget.miaotu.http.bean.SeedlingDetailJavaBean;
import com.widget.miaotu.http.bean.SeedlingInfo;
import com.widget.miaotu.http.bean.SendDemandSeedBean;
import com.widget.miaotu.http.bean.SpecAllJavaBean;
import com.widget.miaotu.http.retrofit.RetrofitFactory;
import com.widget.miaotu.http.retrofit.TransformerUi;
import com.widget.miaotu.master.home.adapter.FenLeitSecondTypeAdapter;
import com.widget.miaotu.master.home.adapter.FenLeitTypeAdapter;
import com.widget.miaotu.master.home.adapter.GuigeAddMiaomuAdapter;
import com.widget.miaotu.master.home.adapter.TreePlantTypeAdapter;
import com.widget.miaotu.master.mine.adapter.FullyGridLayoutManager;
import com.widget.miaotu.master.mine.adapter.GridImageAdapter;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * 添加苗木
 */
public class AddMiaoMuActivity extends MBaseActivity {

    @BindView(R.id.recycler_addMiaoMu_photo)
    RecyclerView recyclerViewAddMiaoMuPhoto;
    @BindView(R.id.ll_base_info_demand_name)
    LinearLayout llBaseInfoDemandName;
    @BindView(R.id.tv_base_info_demand_name)
    TextView tvBaseInfoDemandName;
    @BindView(R.id.tv_base_info_demand_alias_name)
    TextView tvBaseInfoDemandAliasName;
    @BindView(R.id.rcy_flow_addMm_fenLei)
    RecyclerView rcy_flow_addMm_fenLei;//分类选择
    @BindView(R.id.rcy_flow_addMm_fenLeiSecond)
    RecyclerView rcy_flow_addMm_fenLeiSecond;//分类选择二级
    @BindView(R.id.recycler_plantType)
    RecyclerView recycler_plantType;//种植类型
    @BindView(R.id.recycler_guige)
    RecyclerView recycler_guige;//规格
    @BindView(R.id.tv_quality_1)
    TextView tvQuality1;
    @BindView(R.id.tv_quality_2)
    TextView tvQuality2;
    @BindView(R.id.tv_quality_3)
    TextView tvQuality3;
    @BindView(R.id.rb_price_addmm)
    CheckBox rbPrice;
    @BindView(R.id.et_miaomu_describe)
    EditText et_miaomu_describe;
    @BindView(R.id.tv_mm_describe_max_num)
    TextView tv_mm_describe_max_num;
    @BindView(R.id.et_kuCun_Num)
    EditText et_kuCun_Num;
    @BindView(R.id.et_MiaoMu_price)
    EditText et_MiaoMu_price;
    @BindView(R.id.et_MiaoMu_code)//苗木编号
            EditText et_MiaoMu_code;
    @BindView(R.id.topbar)
    QMUITopBarLayout mTopBar;
    @BindView(R.id.ll_addmm_fenleixuanze)
    LinearLayout linearLayoutFenLeiXuanZhe;

    private GridImageAdapter imageMallAdapter;
    private List<LocalMedia> selectList = new ArrayList<>();
    private List<SendDemandSeedBean.ClassifyFirstsBean> fenLeiSelectFirsts = new ArrayList<>();
    private List<SendDemandSeedBean.ClassifyFirstsBean.ClassifySecondsBean> fenLeiSelectSeconds = new ArrayList<>();
    private List<SendDemandSeedBean.ClassifyFirstsBean.PlantTypeListsBean> plantTypeLists = new ArrayList<>();
    private List<SendDemandSeedBean.ClassifyFirstsBean.ClassifySecondsBean.SpecListsBean> specTypeLists = new ArrayList<>();//本来要用RecyclerView

    private SeedlingInfo sData;

    private FenLeitTypeAdapter fenLeiFirstAdapter;
    private FenLeitSecondTypeAdapter fenleiSecondAdapter;
    private TreePlantTypeAdapter plantTypeAdapter;
    private GridLayoutManager gridlayoutManager1;
    private GridLayoutManager gridlayoutManager2;
    private GridLayoutManager gridlayoutManager3;
    private LinearLayoutManager linearrLayoutmanager;
    private GuigeAddMiaomuAdapter guigeAdapter;
    private int positionFenLei = 0; //分类选择第一个按钮
    private int positionFenLeiSecond = 0;//分类选择第二个按钮
    private int zhongzhileixingPosition = 0;//种植类型
    private String quality = "2";
    private String type;//编辑，新增
    private String id = "";//苗木id
    private String gardenId;//苗圃id
    private String seedlingId;//	苗木名称id

    private HashSet<NewsInfoCoverBean> imagesUp = new HashSet<>();

    private List<DemandSpecNewBean> specData = new ArrayList<>();
    private String price;
    private int uploadNum = 0;
    private ArrayList<SpecAllJavaBean> specAllJavaBeanList = new ArrayList<>();
    private ArrayList<BaseViewHolder> mBaseViewHolderList = new ArrayList<>();

    private SendDemandSeedBean.ClassifyFirstsBean.ClassifySecondsBean.SpecListsBean itemClassifyFirstsBean;


    @Override
    protected MVCControl createControl() {
        return null;
    }

    @Override
    protected void initView() {
        type = getIntent().getStringExtra("type");
        gardenId = getIntent().getStringExtra("gardenId");

        seedlingId = getIntent().getStringExtra("seedlingId");

        initTopBar();


    }

    @Override
    protected boolean isUseFullScreenMode() {
        return true;
    }

    private void initTopBar() {
//        mTopBar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));

        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        mTopBar.setTitle("添加苗木").setTextColor(Color.parseColor("#FFFFFF"));

    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_activity_addmiaomu;
    }

    @Override
    protected void initDetailData() {

        initPhoneView();


        //设置分类选择 规格  种植类型
        initMiaoMuFenLeiAndTypeAndOther();

        editextChange();

        getMiaoMuDetail(seedlingId);


    }


    public void getMiaoMuDetail(String idMiaoMu) {
        //编辑
        if ("1".equals(type)) {
            mTopBar.setTitle("编辑苗木").setTextColor(Color.parseColor("#FFFFFF"));
            JSONObject root = new JSONObject();

            try {
                root.put("id", idMiaoMu);
                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), root.toString());
                RetrofitFactory.getInstence().API().seedlingDetail(requestBody)
                        .compose(TransformerUi.<BaseEntity<SeedlingDetailJavaBean>>setThread())
                        .subscribe(new BaseObserver<SeedlingDetailJavaBean>(BaseApplication.instance()) {
                            @Override
                            protected void onSuccess(BaseEntity<SeedlingDetailJavaBean> t) throws Exception {
                                Logger.i(t.toString());
                                if (t.getStatus() == 100) {
                                    initMyDataUI(t.getData());
                                }

                            }

                            @Override
                            protected void onFail(Throwable throwable) throws Exception {
                                Logger.e(throwable.getMessage());

                            }
                        });
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }


    }

    private void initMyDataUI(SeedlingDetailJavaBean data) {
        if (!AndroidUtils.isNullOrEmpty(data.getSeedlingUrls())) {

            imagesUp.clear();

            List<NewsInfoCoverBean> newsInfoCoverBeans = new Gson().fromJson(data.getSeedlingUrls(), new TypeToken<List<NewsInfoCoverBean>>() {
            }.getType());


            imagesUp.addAll(newsInfoCoverBeans);

            if (newsInfoCoverBeans != null)
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < newsInfoCoverBeans.size(); i++) {
                            Logger.i(newsInfoCoverBeans.get(i).getT_url());
                            Logger.i(FileUtil.getImaPathFromCache(newsInfoCoverBeans.get(i).getT_url(),AddMiaoMuActivity.this));
                            LocalMedia  localMedia =  new LocalMedia();
                            localMedia.setCut(false);
                            localMedia.setCompressed(false);
                            localMedia.setPath(FileUtil.getImaPathFromCache(newsInfoCoverBeans.get(i).getT_url(),AddMiaoMuActivity.this));
                            selectList.add(localMedia);
                        }
                        imageMallAdapter.setList(selectList);
                    }
                }).start();


            imageMallAdapter.notifyDataSetChanged();
        }
//
//        getTypeSon2(data.getFirstClassify() + "", data.getSecondClassify() + "", data.getPlantType() + "");
//
//        tvBaseInfoDemandName.setText(data.getSeedlingName() + "");
//        tvBaseInfoDemandAliasName.setVisibility(View.VISIBLE);
//        tvBaseInfoDemandAliasName.setText("常用名：" + data.getCommonNames() + "");
//        et_kuCun_Num.setText(data.getRepertory() + "");
//        if ("0".equals(data.getPrice()) || "0.0".equals(data.getPrice()) || "0.00".equals(data.getPrice())) {
//            rbPrice.setChecked(true);
//        } else {
//            rbPrice.setChecked(false);
//            et_MiaoMu_price.setText(data.getPrice() + "");
//        }
//
//        et_MiaoMu_code.setText(data.getSeedlingNum() + "");
//        et_miaomu_describe.setText(data.getDescribe() + "");
//
//        //苗木规格
//        Gson gson = new Gson();
//        List<HomeMmSpecJavaBean> homeMmSpecJavaBeanList = gson.fromJson(data.getSpec(), new TypeToken<List<HomeMmSpecJavaBean>>() {
//        }.getType());
//        specTypeLists.clear();
//        for (int i = 0; i < homeMmSpecJavaBeanList.size(); i++) {
//            SendDemandSeedBean.ClassifyFirstsBean.ClassifySecondsBean.SpecListsBean specListsBean = new SendDemandSeedBean.ClassifyFirstsBean.ClassifySecondsBean.SpecListsBean();
//            specListsBean.setName(homeMmSpecJavaBeanList.get(i).getSpecName());
//            specListsBean.setMustWrite(homeMmSpecJavaBeanList.get(i).getMustWrite());
//            specListsBean.setUnit(homeMmSpecJavaBeanList.get(i).getUnit());
//            specListsBean.setInterval(homeMmSpecJavaBeanList.get(i).getInterval());
//            specTypeLists.add(specListsBean);
//        }
//        guigeAdapter.notifyDataSetChanged();


    }




//    private void getTypeSon2(String firstClassify, String secondClassify, String plantType) {
//
//        showWaiteDialog("正在加载...");
//        RetrofitFactory.getInstence().API().seedlingChooseInfo(new SeedChooseInfoBean(sid)).compose(TransformerUi.setThread())
//                .subscribe(new BaseObserver<SendDemandSeedBean>(this) {
//                    @Override
//                    protected void onSuccess(BaseEntity<SendDemandSeedBean> t) throws Exception {
//                        hideWaiteDialog();
//                        if (t.getStatus() == 100) {
//                            Logger.e(t.getData().toString());
//                            SendDemandSeedBean data = t.getData();
//                            //各个子页面刷新
//                            classifyFirsts.clear();
//                            classifyFirsts.addAll(data.getClassifyFirsts());
//
//                            if (classifyFirsts.size() > 0)
//                                classifyFirsts.get(0).setIsChose("1");
//                            fenleiAdapter.notifyDataSetChanged();
//                            refalshList(0);
//
//                            for (int i = 0; i < classifyFirsts.size(); i++) {
//                                classifyFirsts.get(i).setIsChose("0");
//                                if (firstClassify != null && firstClassify.equals(classifyFirsts.get(i).getName())) {
//                                    positionFirst = i;
//                                    classifyFirsts.get(i).setIsChose("1");
//                                    refalshList(positionFirst);
//                                }
//                            }
//                            fenleiAdapter.notifyDataSetChanged();
//
//                            for (int i = 0; i < classifySeconds.size(); i++) {
//                                classifySeconds.get(i).setIsChose("0");
//                                if (secondClassify != null && secondClassify.equals(classifySeconds.get(i).getName())) {
//                                    positionSecond = i;
//                                    classifySeconds.get(i).setIsChose("1");
//                                }
//                            }
//                            fenleiSecondAdapter.notifyDataSetChanged();
//
//                            for (int i = 0; i < plantTypeLists.size(); i++) {
//                                plantTypeLists.get(i).setIsChose("0");
//                                if (plantType != null && plantType.equals(plantTypeLists.get(i).getName())) {
//                                    positionplant = i;
//                                    plantTypeLists.get(i).setIsChose("1");
//                                }
//                            }
//                            plantTypeAdapter.notifyDataSetChanged();
//
//
//                        } else {
//                            ToastUtils.showShort(t.getMessage());
//                        }
//                    }
//
//                    @Override
//                    protected void onFail(Throwable throwable) throws Exception {
//                        ToastUtils.showShort("网络异常");
//                    }
//                });
//
//    }


    /**
     * edittext可滑动
     */

    private void editextChange() {
        et_miaomu_describe.addTextChangedListener(new TextWatcher() { //对EditText进行监听
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                tv_mm_describe_max_num.setText((200 - editable.length()) + "/200");
            }
        });
        et_miaomu_describe.setMovementMethod(ScrollingMovementMethod.getInstance());
        et_miaomu_describe.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //这句话说的意思告诉父View我自己的事件我自己处理
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        et_MiaoMu_price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!AndroidUtils.isNullOrEmpty(et_MiaoMu_price.getText().toString())) {
                    rbPrice.setChecked(false);
                } else {
                    rbPrice.setChecked(true);
                }
            }
        });


        rbPrice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    et_MiaoMu_price.setText("");
                    et_MiaoMu_price.setFocusableInTouchMode(false);
                    et_MiaoMu_price.setEnabled(false);
                    et_MiaoMu_price.setBackgroundColor(Color.parseColor("#F7F7F7"));
                } else {
                    et_MiaoMu_price.setFocusableInTouchMode(true);
                    et_MiaoMu_price.setEnabled(true);
                    et_MiaoMu_price.setBackground(getResourceDrawable(R.drawable.et_send_demand_bg));
                }
            }
        });

    }


    @OnClick({R.id.ll_base_info_demand_name, R.id.tv_quality_1, R.id.tv_quality_2, R.id.tv_quality_3, R.id.tv_base_info_demand_send})
    public void onClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_base_info_demand_name:
                //选择苗木
                startActivityForResult(new Intent(AddMiaoMuActivity.this, SeedlingActivity.class), 3);
                break;

            case R.id.tv_quality_1://	苗木品质；0：清货；1：精品；2：普通
                quality = "2";
                tvQuality1.setTextColor(getResources().getColor(R.color.white));
                tvQuality1.setBackground(getResources().getDrawable(R.drawable.bg_green_demand_4));
                tvQuality2.setTextColor(getResources().getColor(R.color.black));
                tvQuality2.setBackground(getResources().getDrawable(R.drawable.bg_gray_demand_4));
                tvQuality3.setTextColor(getResources().getColor(R.color.black));
                tvQuality3.setBackground(getResources().getDrawable(R.drawable.bg_gray_demand_4));
                break;
            case R.id.tv_quality_2://是否会员: 0 未开通 1 已过期 2 是会员
                if (!SPStaticUtils.getString(SPConstant.IS_VIP).equals("2")) {
                    showDialogPay();
                    return;
                }

                quality = "0";
                tvQuality1.setTextColor(getResources().getColor(R.color.black));
                tvQuality1.setBackground(getResources().getDrawable(R.drawable.bg_gray_demand_4));
                tvQuality2.setTextColor(getResources().getColor(R.color.white));
                tvQuality2.setBackground(getResources().getDrawable(R.drawable.bg_blue_demand_4));
                tvQuality3.setTextColor(getResources().getColor(R.color.black));
                tvQuality3.setBackground(getResources().getDrawable(R.drawable.bg_gray_demand_4));
                break;
            case R.id.tv_quality_3:
                if (!SPStaticUtils.getString(SPConstant.IS_VIP).equals("2")) {
                    showDialogPay();
                    return;
                }
                quality = "1";
                tvQuality1.setTextColor(getResources().getColor(R.color.black));
                tvQuality1.setBackground(getResources().getDrawable(R.drawable.bg_gray_demand_4));
                tvQuality2.setTextColor(getResources().getColor(R.color.black));
                tvQuality2.setBackground(getResources().getDrawable(R.drawable.bg_gray_demand_4));
                tvQuality3.setTextColor(getResources().getColor(R.color.white));
                tvQuality3.setBackground(getResources().getDrawable(R.drawable.bg_red_demand_4));
                break;
            case R.id.tv_base_info_demand_send: //立即发布
                //对必选项判断

                if (AndroidUtils.isNullOrEmpty(tvBaseInfoDemandName.getText().toString())) {
                    AndroidUtils.Toast(AddMiaoMuActivity.this, "请选择苗木名称");
                    return;
                }
                if (AndroidUtils.isNullOrEmpty(et_kuCun_Num.getText().toString())) {
                    AndroidUtils.Toast(AddMiaoMuActivity.this, "库存不能为空");
                    return;
                } else {
                    if ("0".equals(et_kuCun_Num.getText().toString().substring(0, 1))) {
                        AndroidUtils.Toast(AddMiaoMuActivity.this, "库存不能为零");
                        return;
                    }
                }
                if (rbPrice.isChecked()) {
                    price = "0";
                } else {
                    if (AndroidUtils.isNullOrEmpty(et_MiaoMu_price.getText().toString())) {
                        AndroidUtils.Toast(AddMiaoMuActivity.this, "价格不能为空");
                        return;
                    }
                    price = et_MiaoMu_price.getText().toString();
                }
                if (AndroidUtils.isNullOrEmpty(quality)) {
                    AndroidUtils.Toast(AddMiaoMuActivity.this, "请选择苗木品质");
                    return;
                }

//                for (int i = 0; i < specTypeLists.size(); i++) {
//                    if (1 == specTypeLists.get(i).getMustWrite()) {
//                        if (AndroidUtils.isNullOrEmpty(etGuige1.getText().toString()) || AndroidUtils.isNullOrEmpty(etGuige2.getText().toString())) {
//                            AndroidUtils.Toast(AddMiaoMuActivity.this, "必填项不能为空");
//                            return;
//                        }
//                    }
//                    if (!AndroidUtils.isNullOrEmpty(etGuige1.getText().toString()) && !AndroidUtils.isNullOrEmpty(etGuige2.getText().toString())) {
//                        String interval = etGuige1.getText().toString() + "-" + etGuige2.getText().toString();
//                        DemandSpecNewBean demandSpecNewBean = new DemandSpecNewBean(itemClassifyFirstsBean.getName(),
//                                interval, itemClassifyFirstsBean.getUnit());
//                        data.add(demandSpecNewBean);
//                    }
//
//                }


//                if ("1".equals(quality) || "0".equals(quality)) {
//                    if (!SPStaticUtils.getString("isVip").equals("2")) {
//                        //弹加入会员弹窗
//                        new VipAPPDialog(AddMiaoMuActivity.this, R.style.dialog_center)
//                                .setOnClickListener(new VipAPPDialog.OnClickListener() {
//                                    @Override
//                                    public void onClick() {
//                                        Intent intent = new Intent();
//                                        intent.putExtra("url", "https://www.miaoto.net/zmh/H5Page/vip/vip.html?token=" + SPStaticUtils.getString(SPConstant.NEW_TOKEN));
//                                        Logger.e("不是会员");
//                                        //                    intent.putExtra("title", "苗途VIP福利社");
////                                        intent.setClass(getContext(), AddVIPNewActivity.class);
////                                        getContext().startActivity(intent);
//                                    }
//                                });
//                        return;
//                    }
//                }

                if ("1".equals(type)) {  //重新编辑保存
//                    uploadImagesEdit();
                } else {//新上传的保存
                    uploadImages();
                }
                break;

        }

    }

    /**
     * 支付dialog
     */
    private void showDialogPay() {
        new VipAPPDialog(AddMiaoMuActivity.this, R.style.dialog_center)
                .setOnClickListener(new VipAPPDialog.OnClickListener() {
                    @Override
                    public void onClick() {
                        Intent intent = new Intent();
                        intent.putExtra("url", "https://www.miaoto.net/zmh/H5Page/vip/vip.html?token=" + SPStaticUtils.getString(SPConstant.NEW_TOKEN));
                        intent.setClass(AddMiaoMuActivity.this, AddVIPNewActivity.class);
                        startActivity(intent);
                    }
                });
    }


    public void uploadImages() {

        if (selectList.size() == 0) {
            ToastUtils.showShort("请上传至少一张苗木图!");
            return;
        }
        showWaiteDialog("正在发布");
        for (int i = 0; i < selectList.size(); i++) {
            LocalMedia media = selectList.get(i);

            String path;
            if (media.isCut() && !media.isCompressed()) {
                // 裁剪过
                path = media.getCutPath();
            } else if (media.isCompressed() || (media.isCut() && media.isCompressed())) {
                // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
                path = media.getCompressPath();
            } else {
                // 原图
                path = media.getPath();
            }

            Luban.with(this)
                    .load(FileUtils.getFileByPath(path))                                   // 传人要压缩的图片列表
                    .ignoreBy(100)                                  // 忽略不压缩图片的大小
//                    .setTargetDir(getPath())                        // 设置压缩后文件存储位置
                    .setCompressListener(new OnCompressListener() { //设置回调
                        @Override
                        public void onStart() {
                            //  压缩开始前调用，可以在方法内启动 loading UI
                        }

                        @Override
                        public void onSuccess(File file) {
                            //   压缩成功后调用，返回压缩后的图片文件
                            RequestBody photoRequestBody = RequestBody.create(MediaType.parse("image/*"), file);
                            if (file.getName().indexOf(".")>0){
                                String fname = System.currentTimeMillis() + file.getName().substring(0, file.getName().indexOf(".")) + ".jpg";
                                MultipartBody.Part pBody = MultipartBody.Part.createFormData("file", fname, photoRequestBody);
                                RetrofitFactory.getInstence().API().uploadImageNew("3", pBody).compose(TransformerUi.setThread()).subscribe(new BaseObserver<Object>(AddMiaoMuActivity.this) {
                                    @Override
                                    protected void onSuccess(BaseEntity<Object> t) throws Exception {
                                        hideWaiteDialog();
                                        if (t.getStatus() == 100) {
                                            uploadNum++;
                                            imagesUp.add(new NewsInfoCoverBean("", t.getData().toString(), ""));
                                            if (uploadNum >= selectList.size()) {
                                                uploadInfo();
                                            }
                                        }
                                    }

                                    @Override
                                    protected void onFail(Throwable throwable) throws Exception {
                                        hideWaiteDialog();
                                        ToastUtils.showShort("请求出错");
                                        uploadNum = 0;
                                    }
                                });
                            }else {
                                uploadNum = 0;
                            }

                        }

                        @Override
                        public void onError(Throwable e) {
                            //  当压缩过程出现问题时调用
                        }
                    }).launch();    //启动压缩
        }


    }


    public void uploadInfo() {
        //上传基本信息

        String secondClassify;
        if (fenLeiSelectSeconds.size() > positionFenLeiSecond) {
            secondClassify = fenLeiSelectSeconds.get(positionFenLeiSecond).getName();
        } else {
            secondClassify = "";
        }

        SaveSeedBean saveSeedBean;
        if ("1".equals(type)) {
            saveSeedBean = new SaveSeedBean(id, "", seedlingId, new Gson().toJson(imagesUp), fenLeiSelectFirsts.get(positionFenLei).getName(), secondClassify,
                    new Gson().toJson(specData), plantTypeLists.get(zhongzhileixingPosition).getName(), "" + et_kuCun_Num.getText().toString(), price,
                    et_MiaoMu_code.getText().toString(), et_miaomu_describe.getText().toString(), quality);
        } else {
            //保存
            for (int i = 0; i < specAllJavaBeanList.size(); i++) {
                String etGuiGe1 = ((EditText) (mBaseViewHolderList.get(i).getView(R.id.et_guige_1))).getText().toString().trim();
                String etGuiGe2 = ((EditText) (mBaseViewHolderList.get(i).getView(R.id.et_guige_2))).getText().toString().trim();


                if (i == 0 || i == 1) {
                    if (TextUtils.isEmpty(etGuiGe1) || TextUtils.isEmpty(etGuiGe2)) {
                        ToastUtils.showShort("必填项不能为空");
                        return;
                    }
                }
                Logger.e(etGuiGe1 + "---------" + etGuiGe2);
                if (!TextUtils.isEmpty(etGuiGe1) && !TextUtils.isEmpty(etGuiGe2)) {
                    if ((Integer.parseInt(etGuiGe2) < Integer.parseInt(etGuiGe1)) || Integer.parseInt(etGuiGe2) == Integer.parseInt(etGuiGe1)) {
                        ToastUtils.showShort("请输入正确规格");
                        return;
                    }
                }
                specAllJavaBeanList.get(i).setInterval(etGuiGe1 + "-" + etGuiGe2);

                if (TextUtils.isEmpty(etGuiGe1) || TextUtils.isEmpty(etGuiGe2)) {
                    specAllJavaBeanList.remove(i);
                    i--;//索引遍历,但是需要在删除之后保证索引的正常:
                }


            }


            saveSeedBean = new SaveSeedBean(gardenId, seedlingId, new Gson().toJson(imagesUp), fenLeiSelectFirsts.get(positionFenLei).getName(), secondClassify,
                    new Gson().toJson(specAllJavaBeanList), plantTypeLists.get(zhongzhileixingPosition).getName(), "" + et_kuCun_Num.getText().toString(), price,
                    et_MiaoMu_code.getText().toString(), et_miaomu_describe.getText().toString(), quality);
            Logger.e(saveSeedBean.toString());
        }

        Logger.e(saveSeedBean.toString());

        if ("1".equals(type)) {
            //编辑

            RetrofitFactory.getInstence().API().editSeed(saveSeedBean).compose(TransformerUi.setThread()).subscribe(new BaseObserver<Object>(AddMiaoMuActivity.this) {
                @Override
                protected void onSuccess(BaseEntity<Object> t) throws Exception {
                    Logger.e(t.toString());
                    specAllJavaBeanList.clear();
                    imagesUp.clear();
                    hideWaiteDialog();
                    if (t.getStatus() == 100) {
                        AndroidUtils.Toast(AddMiaoMuActivity.this, "编辑成功!");
                        finish();
                    } else {
                        ToastUtils.showShort(t.getMessage());
                    }
                }

                @Override
                protected void onFail(Throwable throwable) throws Exception {
                    Logger.e(throwable.toString());
                    specAllJavaBeanList.clear();
                    imagesUp.clear();
                    hideWaiteDialog();
                    ToastUtils.showShort("网络错误");
                }
            });

        } else {

            //保存
            RetrofitFactory.getInstence().API().saveSeed(saveSeedBean).compose(TransformerUi.setThread()).subscribe(new BaseObserver<Object>(AddMiaoMuActivity.this) {
                @Override
                protected void onSuccess(BaseEntity<Object> t) throws Exception {
                    hideWaiteDialog();
                    specAllJavaBeanList.clear();
                    mBaseViewHolderList.clear();
                    imagesUp.clear();
                    guigeAdapter.notifyDataSetChanged();
                    if (t.getStatus() == 100) {
                        AndroidUtils.Toast(AddMiaoMuActivity.this, "添加成功!");
                        finish();
                    } else {
                        uploadNum = 0;
                        ToastUtils.showShort(t.getMessage());
                    }
                }

                @Override
                protected void onFail(Throwable throwable) throws Exception {
                    specAllJavaBeanList.clear();
                    mBaseViewHolderList.clear();
                    imagesUp.clear();
                    guigeAdapter.notifyDataSetChanged();
                    ToastUtils.showShort("网络错误");
                    hideWaiteDialog();
                }
            });


        }


    }


    private void initMiaoMuFenLeiAndTypeAndOther() {

        gridlayoutManager1 = new GridLayoutManager(this, 12, GridLayoutManager.VERTICAL, false);
        rcy_flow_addMm_fenLei.setLayoutManager(gridlayoutManager1);
        fenLeiFirstAdapter = new FenLeitTypeAdapter(this, R.layout.item_tree_type_list, fenLeiSelectFirsts);
        rcy_flow_addMm_fenLei.setAdapter(fenLeiFirstAdapter);


        gridlayoutManager2 = new GridLayoutManager(this, 12, GridLayoutManager.VERTICAL, false);
        rcy_flow_addMm_fenLeiSecond.setLayoutManager(gridlayoutManager2);
        fenleiSecondAdapter = new FenLeitSecondTypeAdapter(this, R.layout.item_tree_type_list_second, fenLeiSelectSeconds);
        rcy_flow_addMm_fenLeiSecond.setAdapter(fenleiSecondAdapter);


        gridlayoutManager3 = new GridLayoutManager(this, 12, GridLayoutManager.VERTICAL, false);
        recycler_plantType.setLayoutManager(gridlayoutManager3);
        plantTypeAdapter = new TreePlantTypeAdapter(this, R.layout.item_tree_type_list, plantTypeLists);
        recycler_plantType.setAdapter(plantTypeAdapter);

        linearrLayoutmanager = new LinearLayoutManager(this);
        linearrLayoutmanager.setOrientation(RecyclerView.VERTICAL);
        recycler_guige.setLayoutManager(linearrLayoutmanager);
        guigeAdapter = new GuigeAddMiaomuAdapter(this, 1, R.layout.item_guige_add_miaomu, specTypeLists, new GuigeAddMiaomuAdapter.ViewAndPositionCallBack() {
            @Override
            public void viewBack(BaseViewHolder baseViewHolder, SendDemandSeedBean.ClassifyFirstsBean.ClassifySecondsBean.SpecListsBean mClassifyFirstsBean, int position) {
                SpecAllJavaBean specAllJavaBean = new SpecAllJavaBean();
                specAllJavaBean.setSpecName(specTypeLists.get(position).getName());
                specAllJavaBean.setUnit(specTypeLists.get(position).getUnit());

                specAllJavaBeanList.add(specAllJavaBean);
                mBaseViewHolderList.add(baseViewHolder);
            }
        });
        recycler_guige.setAdapter(guigeAdapter);


//分类点击事件
        fenLeiFirstAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                positionFenLei = position;
                Logger.e("分类点击的位置为 = " + position);
                for (int i = 0; i < fenLeiSelectFirsts.size(); i++) {
                    fenLeiSelectFirsts.get(i).setIsChose("0");
                }
                fenLeiSelectFirsts.get(position).setIsChose("1");
                fenLeiFirstAdapter.notifyDataSetChanged();
                fenLeiSelectSecond(position);

            }
        });
//分类二级点击事件
        fenleiSecondAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Logger.e("分类点击的位置为 = " + position);
                positionFenLeiSecond = position;
                for (int i = 0; i < fenLeiSelectSeconds.size(); i++) {
                    fenLeiSelectSeconds.get(i).setIsChose("0");
                }
                fenLeiSelectSeconds.get(position).setIsChose("1");
                fenleiSecondAdapter.notifyDataSetChanged();

                refalshSecondSon(positionFenLei, position);

            }
        });
//种植类型点击事件
        plantTypeAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {

                Logger.e("种植类型的位置为 = " + position);
                zhongzhileixingPosition = position;
                for (int i = 0; i < plantTypeLists.size(); i++) {
                    plantTypeLists.get(i).setIsChose("0");
                }
                plantTypeLists.get(position).setIsChose("1");
                plantTypeAdapter.notifyDataSetChanged();
            }
        });
    }


    /**
     * 添加照片的相关数据
     */
    private void initPhoneView() {
        FullyGridLayoutManager manager = new FullyGridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        recyclerViewAddMiaoMuPhoto.setLayoutManager(manager);
        imageMallAdapter = new GridImageAdapter(this, onAddPicClickListener);
        imageMallAdapter.setList(selectList);
        imageMallAdapter.setSelectMax(3);
        recyclerViewAddMiaoMuPhoto.setAdapter(imageMallAdapter);
        imageMallAdapter.setOnItemClickListener(new com.luck.picture.lib.listener.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                if (selectList.size() > 0) {
                    LocalMedia media = selectList.get(position);
                    String mimeType = media.getMimeType();
                    int mediaType = PictureMimeType.getMimeType(mimeType);
                    switch (mediaType) {
                        case 1:
                            PictureSelector.create(AddMiaoMuActivity.this)
                                    .themeStyle(R.style.picture_default_style) // xml设置主题
                                    //.setPictureWindowAnimationStyle(animationStyle)// 自定义页面启动动画
                                    .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)// 设置相册Activity方向，不设置默认使用系统
                                    .isNotPreviewDownload(true)// 预览图片长按是否可以下载
                                    //.bindCustomPlayVideoCallback(new MyVideoSelectedPlayCallback(getContext()))// 自定义播放回调控制，用户可以使用自己的视频播放界面
                                    .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                                    .openExternalPreview(position, selectList);
                            break;
                        case 2:
                            // 预览视频

                            PictureSelector.create(AddMiaoMuActivity.this)
                                    .themeStyle(R.style.picture_default_style)
                                    .externalPictureVideo(TextUtils.isEmpty(media.getAndroidQToPath()) ? media.getPath() : media.getAndroidQToPath());
                            break;
                        case 3:
                            // 预览音频
                            PictureSelector.create(AddMiaoMuActivity.this)
                                    .externalPictureAudio(PictureMimeType.isContent(media.getPath()) ? media.getAndroidQToPath() : media.getPath());
                            break;
                    }
                }
            }
        });
    }


    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {

        @SuppressLint("CheckResult")
        @Override
        public void onAddPicClick() {
            //获取写的权限
            AndPermissionUtils andPermissionUtils = new AndPermissionUtils();
            andPermissionUtils.requestPermission(AddMiaoMuActivity.this, new AndPermissionUtils.PermissionsCallBack() {
                @Override
                public void permissionsSuc() {
                    //拍照和相册选择Dialog
                    new TakePhotoDialog(AddMiaoMuActivity.this, R.style.dialog_center)
                            .setOnTakeClickListener(new TakePhotoDialog.OnTakeClickListener() {
                                @Override
                                public void onClick(int type) {
                                    if (type == 1) {
                                        //相机
                                        AndPermissionUtils cameraPermission = new AndPermissionUtils();

                                        cameraPermission.requestPermission(AddMiaoMuActivity.this, new AndPermissionUtils.PermissionsCallBack() {
                                            @Override
                                            public void permissionsSuc() {
                                                //拍照
                                                PictureSelector.create(AddMiaoMuActivity.this)
                                                        .openCamera(PictureMimeType.ofImage())
                                                        .forResult(PictureConfig.CHOOSE_REQUEST);

                                            }

                                            @Override
                                            public void permissionFail(@NonNull List<String> permissions) {
                                                if (AndPermission.hasAlwaysDeniedPermission(AddMiaoMuActivity.this, permissions)) {
                                                    PermissionDialogUtils.showSettingDialog(AddMiaoMuActivity.this, permissions, new PermissionDialogUtils.PermissionSetCallback() {
                                                        @Override
                                                        public void setPermission() {
                                                            myermission();
                                                        }
                                                    });
                                                }
                                            }
                                        }, Permission.CAMERA);


                                    } else {
                                        //相册
                                        PictureSelector.create(AddMiaoMuActivity.this)
                                                .openGallery(PictureMimeType.ofImage())
                                                .maxSelectNum(selectList.size() <= 3 ? (3 - selectList.size()) : 0)
                                                .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                                                .minSelectNum(1)
                                                .imageSpanCount(4)
                                                .selectionMode(PictureConfig.MULTIPLE)
                                                .forResult(PictureConfig.CHOOSE_REQUEST);
                                    }
                                }
                            });
                }

                @Override
                public void permissionFail(@NonNull List<String> permissions) {
                    if (AndPermission.hasAlwaysDeniedPermission(AddMiaoMuActivity.this, permissions)) {
                        PermissionDialogUtils.showSettingDialog(AddMiaoMuActivity.this, permissions, new PermissionDialogUtils.PermissionSetCallback() {
                            @Override
                            public void setPermission() {
                                myermission();
                            }
                        });
                    }
                }
            }, Permission.WRITE_EXTERNAL_STORAGE);
        }
    };

    /**
     * Set permissions.
     */
    private void myermission() {
        AndPermission.with(AddMiaoMuActivity.this).runtime().setting().start(
                11);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PictureConfig.CHOOSE_REQUEST && resultCode == RESULT_OK) {
            List<LocalMedia> images = PictureSelector.obtainMultipleResult(data);
            selectList.addAll(images);

            imageMallAdapter.setList(selectList);
            imageMallAdapter.notifyDataSetChanged();
        } else if (requestCode == 11) {
            Toast.makeText(AddMiaoMuActivity.this, "申请权限", Toast.LENGTH_SHORT).show();

        } else if (requestCode == 3 && resultCode == 4) {
            assert data != null;
            sData = new Gson().fromJson(data.getStringExtra("info"), SeedlingInfo.class);

            tvBaseInfoDemandName.setText(sData.getBaseName());
            linearLayoutFenLeiXuanZhe.setVisibility(View.VISIBLE);
            if (StringUtils.isEmpty(sData.getCommonNames())) {
                tvBaseInfoDemandAliasName.setVisibility(View.GONE);
            } else {
                tvBaseInfoDemandAliasName.setVisibility(View.VISIBLE);
                tvBaseInfoDemandAliasName.setText("常用名：" + sData.getCommonNames());
            }

            //	苗木名称id
            seedlingId = sData.getId() + "";//SendDemandSeedBean 解析多级list

            fenLeiSelectInfo();

        }
    }


    private void fenLeiSelectInfo() {

        showWaiteDialog("正在加载...");
        RetrofitFactory.getInstence().API().seedlingChooseInfo(new SeedChooseInfoBean(seedlingId)).compose(TransformerUi.setThread())
                .subscribe(new BaseObserver<SendDemandSeedBean>(this) {
                    @Override
                    protected void onSuccess(BaseEntity<SendDemandSeedBean> t) throws Exception {
                        hideWaiteDialog();
                        if (t.getStatus() == 100) {
                            //各个子页面刷新
                            fenLeiSelectFirsts.clear();
                            fenLeiSelectFirsts.addAll(t.getData().getClassifyFirsts());
                            if (fenLeiSelectFirsts.size() > 0)
                                fenLeiSelectFirsts.get(0).setIsChose("1");
                            //根据数量显示占位
                            showTextWidth(gridlayoutManager1, fenLeiSelectFirsts.size());
                            fenLeiFirstAdapter.notifyDataSetChanged();

                            fenLeiSelectSecond(0);
                        } else {
                            ToastUtils.showShort(t.getMessage());
                        }
                    }

                    @Override
                    protected void onFail(Throwable throwable) throws Exception {
                        ToastUtils.showShort("网络异常");
                    }
                });


    }

    private void fenLeiSelectSecond(int position) {
        fenLeiSelectSeconds.clear();

        if (fenLeiSelectFirsts.size() > 0 && 1 == fenLeiSelectFirsts.get(position).getHasSecondClassify()) {
            fenLeiSelectSeconds.addAll(fenLeiSelectFirsts.get(position).getClassifySeconds());

            //根据数量显示占位
            showTextWidth(gridlayoutManager2, fenLeiSelectSeconds.size());
            for (int i = 0; i < fenLeiSelectSeconds.size(); i++) {
                fenLeiSelectSeconds.get(i).setIsChose("0");
            }
            fenLeiSelectSeconds.get(0).setIsChose("1");
        }
        fenleiSecondAdapter.notifyDataSetChanged();


        zhongzhileixing(position);


    }

    private void zhongzhileixing(int position) {
        plantTypeLists.clear();
        if (fenLeiSelectFirsts.size() > 0) {
            plantTypeLists.addAll(fenLeiSelectFirsts.get(position).getPlantTypeLists());
            //根据数量显示占位
            showTextWidth(gridlayoutManager3, plantTypeLists.size());
            for (int i = 0; i < plantTypeLists.size(); i++) {
                plantTypeLists.get(i).setIsChose("0");
            }
            plantTypeLists.get(0).setIsChose("1");
        }
        plantTypeAdapter.notifyDataSetChanged();

        //赋值 规格
        refalshSecondSon(position, 0);
    }

    //规格 第一个位置的第一个组合
    private void refalshSecondSon(int firstPosition, int secondPosition) {
        specTypeLists.clear();
        if (fenLeiSelectFirsts.size() > 0 && 1 != fenLeiSelectFirsts.get(firstPosition).getHasSecondClassify()) {
            if (fenLeiSelectFirsts.size() > 0) {
                specTypeLists.clear();
                specTypeLists.addAll(fenLeiSelectFirsts.get(firstPosition).getSpecLists());
            }
        } else {
            if (fenLeiSelectFirsts.size() > 0)
                if (fenLeiSelectFirsts.get(firstPosition).getClassifySeconds().size() > 0) {
                    specTypeLists.clear();
                    specTypeLists.addAll(fenLeiSelectFirsts.get(firstPosition).getClassifySeconds().get(secondPosition).getSpecLists()); //获取类别信息
                }
        }
        guigeAdapter.notifyDataSetChanged();

    }


    //根据数量显示占位
    private void showTextWidth(GridLayoutManager gridLayoutManager, int size) {
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (size == 1) {
                    return 12;
                } else if (size == 2) {
                    return 6;
                } else if (size == 3) {
                    return 4;
                } else if (size == 4) {
                    return 3;
                }
                return 3;
            }
        });
    }
}
