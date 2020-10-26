package com.widget.miaotu.master.mine.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.SPStaticUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.orhanobut.logger.Logger;
import com.widget.miaotu.BaseApplication;
import com.widget.miaotu.R;
import com.widget.miaotu.base.MBaseActivity;
import com.widget.miaotu.base.MVCControl;
import com.widget.miaotu.common.constant.SPConstant;
import com.widget.miaotu.common.utils.AddresSelectUtils;
import com.widget.miaotu.common.utils.AndPermissionUtils;
import com.widget.miaotu.common.utils.AndroidUtils;
import com.widget.miaotu.common.utils.IntentUtils;
import com.widget.miaotu.common.utils.dialog.PermissionDialogUtils;
import com.widget.miaotu.common.utils.other.LoadingUtil;
import com.widget.miaotu.common.utils.rclayout.RCImageView;
import com.widget.miaotu.common.utils.ui.TakePhotoDialog;
import com.widget.miaotu.http.BaseObserver;
import com.widget.miaotu.http.bean.BaseEntity;
import com.widget.miaotu.http.bean.CompanyInfoMeBean;
import com.widget.miaotu.http.bean.NewsInfoCoverBean;
import com.widget.miaotu.http.bean.SeedCompanyInfoBean;
import com.widget.miaotu.http.retrofit.RetrofitFactory;
import com.widget.miaotu.http.retrofit.TransformerUi;
import com.widget.miaotu.master.home.activity.AddMiaoMuActivity;
import com.widget.miaotu.master.mine.adapter.FullyGridLayoutManager;
import com.widget.miaotu.master.mine.adapter.GridImageAdapter;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * 企业信息设置
 */
public class CompanyInfoActivity extends MBaseActivity {

    @BindView(R.id.btn_back)
    ImageButton buttonBack;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.btn_right)
    Button btn_right;
    @BindView(R.id.rciv_company_log)
    RCImageView rciv_company_log;
    @BindView(R.id.company_Background)
    LinearLayout company_Background;

    @BindView(R.id.et_companyJs_info)
    EditText et_companyJs_info;
    @BindView(R.id.tv_company_max_num)
    TextView tv_company_max_num;

    @BindView(R.id.tv_company_address)
    TextView tv_company_address;
    @BindView(R.id.tv_company_renzheng_result)
    TextView tv_company_renzheng_result;

    @BindView(R.id.cusEt_company_manage)
    EditText cusEt_company_manage;

    @BindView(R.id.cusEt_company_detail_address)
    EditText cusEt_company_detail_address;
    @BindView(R.id.etDetailBusiness)
    EditText etDetailBusiness;

    @BindView(R.id.recycler_company_photo)
    RecyclerView recycler_company_photo;
    @BindView(R.id.cusEt_companyName)
    EditText cusEt_companyName;
    @BindView(R.id.cusEt_company_phone)
    EditText cusEt_company_phone;


    private int MAX_COUNT = 200;
    private GridImageAdapter adapter;
    private List<LocalMedia> selectList = new ArrayList<>();
    private int isAuth;
    private String avatarUrl;

    private List<NewsInfoCoverBean> mImages = new ArrayList<>();

    @Override
    protected MVCControl createControl() {
        return null;
    }

    @Override
    protected void initView() {
        tv_title.setText("企业信息设置");
        btn_right.setVisibility(View.VISIBLE);
        btn_right.setText("保存");

        initdataView();
        editextChange();

        initWidget();
    }

    private void initdataView() {
        RetrofitFactory.getInstence().API().getCompanyInfo( )
                .compose(TransformerUi.<BaseEntity<CompanyInfoMeBean>>setThread())
                .subscribe(new BaseObserver<CompanyInfoMeBean>(BaseApplication.instance()) {
                    @Override
                    protected void onSuccess(BaseEntity<CompanyInfoMeBean> t) throws Exception {
                        Logger.e(t.getData().toString());
                        initDataInfo(t.getData());
                    }

                    @Override
                    protected void onFail(Throwable throwable) throws Exception {
                        Logger.e(throwable.getMessage());

                    }
                });
    }


    /**
     * 初始化信息
     *
     * @param data
     */
    private void initDataInfo(CompanyInfoMeBean data) {
        Glide.with(this).load(data.getLogo()).into(rciv_company_log);
        cusEt_companyName.setText(data.getName() + "");
        isAuth = data.getIsAuth();
        if (-1 == data.getIsAuth()) {
            tv_company_renzheng_result.setText("未认证");
        } else if (1 == data.getIsAuth()) {
            tv_company_renzheng_result.setText("审核中");
        } else if (2 == data.getIsAuth()) {
            tv_company_renzheng_result.setText("已认证");
        } else if (3 == data.getIsAuth()) {
            tv_company_renzheng_result.setText("未通过");
        }
        if (!AndroidUtils.isNullOrEmpty(data.getAddress()))
            tv_company_address.setText(data.getAddress() + "");
        if (!AndroidUtils.isNullOrEmpty(data.getAddress_detail()))
            cusEt_company_detail_address.setText(data.getAddress_detail() + "");
        if (!AndroidUtils.isNullOrEmpty(data.getBusiness()))
            etDetailBusiness.setText(data.getBusiness() + "");
        if (!AndroidUtils.isNullOrEmpty(data.getContact()))
            cusEt_company_manage.setText(data.getContact() + "");
        if (!AndroidUtils.isNullOrEmpty(data.getContact_mobile()))
            cusEt_company_phone.setText(data.getContact_mobile() + "");
        if (!AndroidUtils.isNullOrEmpty(data.getIntroduction()))
            et_companyJs_info.setText(data.getIntroduction() + "");

        if (!AndroidUtils.isNullOrEmpty(data.getStyle_photos())) {
            Gson gson = new Gson();
            List<NewsInfoCoverBean> newsInfoCoverBeans = gson.fromJson(data.getStyle_photos(), new TypeToken<List<NewsInfoCoverBean>>() {
            }.getType());
            //如果图片不为空的话，暂时不做处理

            Logger.e(newsInfoCoverBeans.toString());

        }
    }

    /**
     * edittext可滑动
     */

    private void editextChange() {
        et_companyJs_info.addTextChangedListener(new TextWatcher() { //对EditText进行监听
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                tv_company_max_num.setText((MAX_COUNT - editable.length()) + "/200");
            }
        });
        et_companyJs_info.setMovementMethod(ScrollingMovementMethod.getInstance());
        et_companyJs_info.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //这句话说的意思告诉父View我自己的事件我自己处理
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });


    }


    private void initWidget() {
        FullyGridLayoutManager manager = new FullyGridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false);
        recycler_company_photo.setLayoutManager(manager);
        adapter = new GridImageAdapter(this, onAddPicClickListener);
        adapter.setList(selectList);
        adapter.setSelectMax(8);
        recycler_company_photo.setAdapter(adapter);
        adapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if (selectList.size() > 0) {
                    LocalMedia media = selectList.get(position);
                    String pictureType = media.getPictureType();
                    int mediaType = PictureMimeType.pictureToVideo(pictureType);
                    switch (mediaType) {
                        case 1:
                            // 预览图片 可自定长按保存路径
                            //PictureSelector.create(MainActivity.this).externalPicturePreview(position, "/custom_file", selectList);
                            PictureSelector.create(CompanyInfoActivity.this).externalPicturePreview(position, selectList);
                            break;
                        case 2:
                            // 预览视频
                            PictureSelector.create(CompanyInfoActivity.this).externalPictureVideo(media.getPath());
                            break;
                        case 3:
                            // 预览音频
                            PictureSelector.create(CompanyInfoActivity.this).externalPictureAudio(media.getPath());
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
            andPermissionUtils.requestPermission(CompanyInfoActivity.this, new AndPermissionUtils.PermissionsCallBack() {
                @Override
                public void permissionsSuc() {
                    //拍照和相册选择Dialog
                    new TakePhotoDialog(CompanyInfoActivity.this, R.style.dialog_center)
                            .setOnTakeClickListener(new TakePhotoDialog.OnTakeClickListener() {
                                @Override
                                public void onClick(int type) {
                                    if (type == 1) {
                                        //相机
                                        AndPermissionUtils cameraPermission = new AndPermissionUtils();

                                        cameraPermission.requestPermission(CompanyInfoActivity.this, new AndPermissionUtils.PermissionsCallBack() {
                                            @Override
                                            public void permissionsSuc() {
                                                //拍照
                                                PictureSelector.create(CompanyInfoActivity.this)
                                                        .openCamera(PictureMimeType.ofImage())
                                                        .forResult(PictureConfig.CHOOSE_REQUEST);
                                            }

                                            @Override
                                            public void permissionFail(@NonNull List<String> permissions) {
                                                if (AndPermission.hasAlwaysDeniedPermission(CompanyInfoActivity.this, permissions)) {
                                                    PermissionDialogUtils.showSettingDialog(CompanyInfoActivity.this, permissions, new PermissionDialogUtils.PermissionSetCallback() {
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
                                        PictureSelector.create(CompanyInfoActivity.this)
                                                .openGallery(PictureMimeType.ofImage())
                                                .maxSelectNum(selectList.size() <= 8 ? (8 - selectList.size()) : 0)
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
                    if (AndPermission.hasAlwaysDeniedPermission(CompanyInfoActivity.this, permissions)) {
                        PermissionDialogUtils.showSettingDialog(CompanyInfoActivity.this, permissions, new PermissionDialogUtils.PermissionSetCallback() {
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


    @OnClick({R.id.btn_back, R.id.btn_right, R.id.ll_company_log, R.id.ll_company_address_select, R.id.ll_company_renzheng})
    public void onClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                new XPopup.Builder(CompanyInfoActivity.this)
                        .isDestroyOnDismiss(false) //对于只使用一次的弹窗，推荐设置这个
                        .asConfirm("确定放弃已编辑内容吗？", "",
                                "取消", "确定",
                                new OnConfirmListener() {
                                    @Override
                                    public void onConfirm() {
                                        finish();
                                    }
                                }, null, false)
                        .show();

                break;
            case R.id.btn_right://保存
                saveConpanyInfo();
                break;
            case R.id.ll_company_log://企业Log
                selectLog();
                break;
            case R.id.ll_company_address_select://地址选择器
                selectAddress();
                break;
            case R.id.ll_company_renzheng://企业认证
                if(isAuth==1||isAuth==2){
                    return;
                }
                IntentUtils.startIntent(CompanyInfoActivity.this, QiYeRenZhengActivity.class);
                break;
        }
    }

    /**
     * 保存填写的企业用户信息
     */
    private void saveConpanyInfo() {

        if (AndroidUtils.isNullOrEmpty(cusEt_companyName.getText().toString())) {
            AndroidUtils.Toast(CompanyInfoActivity.this, "企业名称不能为空");
            return;
        }
        if (AndroidUtils.isNullOrEmpty(tv_company_address.getText().toString())) {
            AndroidUtils.Toast(CompanyInfoActivity.this, "企业地址不能为空");
            return;
        }

        //先上传图片
//        if ("1".equals(SPStaticUtils.getString(SPConstant.ISCOMPANY))) {
////            uploadImagesEdit();
//        } else {
//            uploadImages();
//        }
        uploadImages();
    }


    public void uploadImages() {

        if (selectList.size() == 0) {
            ToastUtils.showShort("请上传至少一张企业照片!");
            return;
        }
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
                            // TODO 压缩开始前调用，可以在方法内启动 loading UI
                        }

                        @Override
                        public void onSuccess(File file) {
                            // TODO 压缩成功后调用，返回压缩后的图片文件
                            RequestBody photoRequestBody = RequestBody.create(MediaType.parse("image/*"), file);
                            String fname = System.currentTimeMillis() + file.getName().substring(0, file.getName().indexOf(".")) + ".jpg";
                            MultipartBody.Part pBody = MultipartBody.Part.createFormData("file", fname, photoRequestBody);

                            RetrofitFactory.getInstence().API().uploadImageNew("3", pBody).compose(TransformerUi.setThread()).subscribe(new BaseObserver<Object>(CompanyInfoActivity.this) {
                                @Override
                                protected void onSuccess(BaseEntity<Object> t) throws Exception {
                                    if (t.getStatus() == 100) {
                                        Logger.e(t.getData().toString());
                                        mImages.add(new NewsInfoCoverBean("", t.getData().toString(), ""));
                                        uploadInfo();
                                    } else {
                                        LoadingUtil.loadingClose(false, "上传失败!");
                                    }
                                }

                                @Override
                                protected void onFail(Throwable throwable) throws Exception {
                                    LoadingUtil.loadingClose(false, "头像上传失败!");
                                }
                            });


                        }

                        @Override
                        public void onError(Throwable e) {
                            // TODO 当压缩过程出现问题时调用
                        }
                    }).launch();    //启动压缩
        }

        LoadingUtil.loadingShow(this, "上传中...");
    }


    public void uploadInfo() {
        Gson gson = new Gson();
        //保存新接口
        SeedCompanyInfoBean bean = new SeedCompanyInfoBean(tv_company_address.getText().toString(), cusEt_company_detail_address.getText().toString(),
                etDetailBusiness.getText().toString(), cusEt_company_manage.getText().toString(), cusEt_company_phone.getText().toString(),
                et_companyJs_info.getText().toString(), avatarUrl, cusEt_companyName.getText().toString(), gson.toJson(mImages));
        Logger.e(bean.toString());


        RetrofitFactory.getInstence().API().companyEdit(bean).compose(TransformerUi.setThread()).subscribe(new BaseObserver<Object>(CompanyInfoActivity.this) {
            @Override
            protected void onSuccess(BaseEntity<Object> t) throws Exception {
                Logger.e(t.toString());
                if (t.getStatus() == 100) {
                    LoadingUtil.dissmiss();
                    SPStaticUtils.put(SPConstant.ISCOMPANY, "1");
                    ToastUtils.showShort("保存成功！");
                    finish();
                } else {
                    LoadingUtil.loadingClose(false, "" + t.getMessage());
                }
            }

            @Override
            protected void onFail(Throwable throwable) throws Exception {
                Logger.e(throwable.getMessage());
                LoadingUtil.loadingClose(false, "请求错误");
            }
        });

    }

//    public void uploadImagesEdit() {
//
//        if (selectList.size() == 0) {
//            ToastUtils.showShort("请上传至少一张企业照片!");
//            return;
//        }
//        int jishu = 0;
////        List<String> imgNew = new ArrayList<>();
////        for (int i = 0; i < selectList.size(); i++) {
////            for (int j = 0; j < imagesOld.size(); j++) {
////                if (imgs.get(i).equals(imagesOld.get(j).getT_url())) {
////                    jishu = jishu + 1;
////                    continue;
////                }
////                if (j == imagesOld.size() - 1) {
////                    imgNew.add(imgs.get(i));
////                }
////            }
////        }
//        if (jishu == 0) {
//            for (int i = 0; i < imgs.size(); i++) {
//                Luban.with(this)
//                        .load(FileUtils.getFileByPath(imgs.get(i)))                                   // 传人要压缩的图片列表
//                        .ignoreBy(100)                                  // 忽略不压缩图片的大小
////                    .setTargetDir(getPath())                        // 设置压缩后文件存储位置
//                        .setCompressListener(new OnCompressListener() { //设置回调
//                            @Override
//                            public void onStart() {
//                                // TODO 压缩开始前调用，可以在方法内启动 loading UI
//                            }
//
//                            @Override
//                            public void onSuccess(File file) {
//                                // TODO 压缩成功后调用，返回压缩后的图片文件
//                                RequestBody photoRequestBody = RequestBody.create(MediaType.parse("image/*"), file);
//
//                                String fname = System.currentTimeMillis() + file.getName().substring(0, file.getName().indexOf(".")) + ".jpg";
//                                MultipartBody.Part pBody = MultipartBody.Part.createFormData("file", fname, photoRequestBody);
//                                RxHttpUtils.createApi(ApiService.class)
//                                        .uploadImageNew("3", pBody)
//                                        .compose(Transformer.switchSchedulers())
//                                        .subscribe(new DataObserverNew<String>() {
//                                            @Override
//                                            protected void onError(String errorMsg) {
//                                                XLog.e(errorMsg);
//                                                LoadingUtil.loadingClose(false, "上传失败!");
//                                                uploadNum = 0;
//                                            }
//
//                                            @Override
//                                            protected void onSuccess(String data) {
//                                                uploadNum++;
//                                                images.add(new NewsInfoCoverBean("", data, ""));
//                                                if (uploadNum == getImages().size()) {
//                                                    uploadInfo();
//                                                }
//                                            }
//                                        });
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                // TODO 当压缩过程出现问题时调用
//                            }
//                        }).launch();    //启动压缩
//            }
//            LoadingUtil.loadingShow(this, "上传中...");
//        } else if (jishu == selectList.size()) {
//            uploadInfo();
//        } else {
//            for (int i = 0; i < imgNew.size(); i++) {
//                Luban.with(this)
//                        .load(FileUtils.getFileByPath(imgNew.get(i)))                                   // 传人要压缩的图片列表
//                        .ignoreBy(100)                                  // 忽略不压缩图片的大小
////                    .setTargetDir(getPath())                        // 设置压缩后文件存储位置
//                        .setCompressListener(new OnCompressListener() { //设置回调
//                            @Override
//                            public void onStart() {
//                                // TODO 压缩开始前调用，可以在方法内启动 loading UI
//                            }
//
//                            @Override
//                            public void onSuccess(File file) {
//                                // TODO 压缩成功后调用，返回压缩后的图片文件
//                                RequestBody photoRequestBody = RequestBody.create(MediaType.parse("image/*"), file);
//
//                                String fname = System.currentTimeMillis() + file.getName().substring(0, file.getName().indexOf(".")) + ".jpg";
//                                MultipartBody.Part pBody = MultipartBody.Part.createFormData("file", fname, photoRequestBody);
//                                RxHttpUtils.createApi(ApiService.class)
//                                        .uploadImageNew("3", pBody)
//                                        .compose(Transformer.switchSchedulers())
//                                        .subscribe(new DataObserverNew<String>() {
//                                            @Override
//                                            protected void onError(String errorMsg) {
//                                                XLog.e(errorMsg);
//                                                LoadingUtil.loadingClose(false, "上传失败!");
//                                                uploadNum = 0;
//                                            }
//
//                                            @Override
//                                            protected void onSuccess(String data) {
//                                                uploadNum++;
//                                                images.add(new NewsInfoCoverBean("", data, ""));
//                                                if (uploadNum == imgNew.size()) {
//                                                    uploadInfo();
//                                                }
//                                            }
//                                        });
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                // TODO 当压缩过程出现问题时调用
//                            }
//                        }).launch();    //启动压缩
//            }
//            LoadingUtil.loadingShow(this, "上传中...");
//        }
//
//    }


    public <String> ArrayList convert(List<Object> a) {
        return (ArrayList) a;
    }

    /**
     * 选择地址
     */
    private void selectAddress() {
        AddresSelectUtils.selectPICKview(getWindow().getDecorView().findViewById(android.R.id.content), CompanyInfoActivity.this, new AddresSelectUtils.SelectAddressCallBack() {
            @Override
            public void selectAddressBack(String province, String city, String address) {
                tv_company_address.setText(province + "\n" + city);
            }
        });
    }

    /**
     * 选择企业Log
     */
    private void selectLog() {
        AndPermissionUtils andPermissionUtils = new AndPermissionUtils();
        andPermissionUtils.requestPermission(this, new AndPermissionUtils.PermissionsCallBack() {
            @Override
            public void permissionsSuc() {
                //拍照和相册选择Dialog
                new TakePhotoDialog(CompanyInfoActivity.this, R.style.dialog_center)
                        .setOnTakeClickListener(new TakePhotoDialog.OnTakeClickListener() {
                            @Override
                            public void onClick(int type) {
                                if (type == 1) {
                                    //相机
                                    AndPermissionUtils cameraPermission = new AndPermissionUtils();

                                    cameraPermission.requestPermission(CompanyInfoActivity.this, new AndPermissionUtils.PermissionsCallBack() {
                                        @Override
                                        public void permissionsSuc() {
                                            //拍照
                                            PictureSelector.create(CompanyInfoActivity.this)
                                                    .openCamera(PictureMimeType.ofImage())
                                                    .forResult(33);
                                        }

                                        @Override
                                        public void permissionFail(@NonNull List<String> permissions) {
                                            if (AndPermission.hasAlwaysDeniedPermission(CompanyInfoActivity.this, permissions)) {
                                                PermissionDialogUtils.showSettingDialog(CompanyInfoActivity.this, permissions, new PermissionDialogUtils.PermissionSetCallback() {
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
                                    PictureSelector.create(CompanyInfoActivity.this)
                                            .openGallery(PictureMimeType.ofImage())
                                            .maxSelectNum(1)
                                            .minSelectNum(1)
                                            .imageSpanCount(4)
                                            .selectionMode(PictureConfig.MULTIPLE)
                                            .forResult(33);
                                }
                            }
                        });
            }

            @Override
            public void permissionFail(@NonNull List<String> permissions) {
                if (AndPermission.hasAlwaysDeniedPermission(CompanyInfoActivity.this, permissions)) {
                    PermissionDialogUtils.showSettingDialog(CompanyInfoActivity.this, permissions, new PermissionDialogUtils.PermissionSetCallback() {
                        @Override
                        public void setPermission() {
                            myermission();
                        }
                    });
                }
            }
        }, Permission.WRITE_EXTERNAL_STORAGE);
    }




    /**
     * Set permissions.
     */
    private void myermission() {
        AndPermission.with(CompanyInfoActivity.this).runtime().setting().start(
                11);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 33 && resultCode == RESULT_OK) {//照相机
            List<LocalMedia> logImages = PictureSelector.obtainMultipleResult(data);

            LocalMedia localMedia = logImages.get(0);
            int mimeType = localMedia.getMimeType();
            String path;
            String camaraPath;
            if (localMedia.isCut() && !localMedia.isCompressed()) {
                // 裁剪过
                camaraPath = localMedia.getCutPath();
            } else if (localMedia.isCompressed() || (localMedia.isCut() && localMedia.isCompressed())) {
                // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
                camaraPath = localMedia.getCompressPath();
            } else {
                // 原图
                camaraPath = localMedia.getPath();
            }

            final File fileByUri = FileUtils.getFileByPath(camaraPath);
            RequestBody photoRequestBody = RequestBody.create(MediaType.parse("image/*"), fileByUri);
            String fname = System.currentTimeMillis() + fileByUri.getName().substring(0, fileByUri.getName().indexOf(".")) + ".jpg";
            MultipartBody.Part pBody = MultipartBody.Part.createFormData("file", fname, photoRequestBody);
            RetrofitFactory.getInstence().API().uploadImageNew("3", pBody).compose(TransformerUi.setThread()).subscribe(new BaseObserver<Object>(CompanyInfoActivity.this) {
                @Override
                protected void onSuccess(BaseEntity<Object> t) throws Exception {
                    if (t.getStatus() == 100) {

                        Logger.e(t.getData().toString());
                        avatarUrl = t.getData().toString();
                        RequestOptions options = new RequestOptions()
                                .centerCrop()
                                .placeholder(R.color.color_4d)
                                .diskCacheStrategy(DiskCacheStrategy.ALL);
                        Glide.with(CompanyInfoActivity.this)
                                .load(camaraPath)
                                .apply(options)
                                .into(rciv_company_log);
                    }
                }

                @Override
                protected void onFail(Throwable throwable) throws Exception {
                    LoadingUtil.loadingClose(false, "头像上传失败!");
                }
            });
        } else if (requestCode == PictureConfig.CHOOSE_REQUEST && resultCode == RESULT_OK) {
            List<LocalMedia> images = PictureSelector.obtainMultipleResult(data);
            selectList.addAll(images);

            //selectList = PictureSelector.obtainMultipleResult(data);

            // 例如 LocalMedia 里面返回三种path
            // 1.media.getPath(); 为原图path
            // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
            // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
            // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
            adapter.setList(selectList);
            adapter.notifyDataSetChanged();
        } else if (requestCode == 11) {
            Toast.makeText(CompanyInfoActivity.this, "申请权限", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_company_info;
    }

    @Override
    protected void initDetailData() {

    }
}
