package com.widget.miaotu.master.mine.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.orhanobut.logger.Logger;
import com.widget.miaotu.R;
import com.widget.miaotu.base.MBaseActivity;
import com.widget.miaotu.base.MVCControl;
import com.widget.miaotu.common.utils.AndPermissionUtils;
import com.widget.miaotu.common.utils.AndroidUtils;
import com.widget.miaotu.common.utils.ui.GlideEngine;
import com.widget.miaotu.common.utils.ui.TakePhotoDialog;
import com.widget.miaotu.http.BaseObserver;
import com.widget.miaotu.http.bean.BaseEntity;
import com.widget.miaotu.http.bean.RealCompanyBean;
import com.widget.miaotu.http.retrofit.RetrofitFactory;
import com.widget.miaotu.http.retrofit.TransformerUi;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


/**
 * 企业认证界面
 */
public class QiYeRenZhengActivity extends MBaseActivity {
    private static final int QIYE_CAMARA_REQUEST_CODE = 123;
    private static final int SHENGFENZHENG_CAMARA_REQUEST_CODE = 124;
    private static final int QIYE_PHONE_REQUESTCODE = 125;
    private static final int SHENGFENZHENG_PHONE_REQUESTCODE = 126;
    @BindView(R.id.iv_qiye_shengfenzheng)
    ImageView iv_qiye_shengfenzheng;
    @BindView(R.id.iv_qiye_yingyezhizhao)
    ImageView iv_qiye_yingyezhizhao;
    @BindView(R.id.view_renzhegn_setp2_show)
    View setp2_show;
    @BindView(R.id.rci_renzhegn_setp2_show)
    View rci_show;
    @BindView(R.id.rci_renzhegn_setp2_hide)
    View rci_hide;


    private List<LocalMedia> qiyeImages;
    private List<LocalMedia> shengfenzhengImages;
    private String sfzUrl;
    private String avatarUrl;


    @Override
    protected MVCControl createControl() {
        return null;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected boolean isUseFullScreenMode() {
        return true;
    }

    @OnClick({R.id.iv_qiye_yingyezhizhao, R.id.iv_qiye_shengfenzheng, R.id.bt_qiye_renzheng})
    public void onClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_qiye_yingyezhizhao:

                tackPhoto(1);
                break;
            case R.id.iv_qiye_shengfenzheng:
                tackPhoto(2);
                break;
            case R.id.bt_qiye_renzheng:
                clickRenZheng();
                break;
        }
    }

    private void clickRenZheng() {
        // 提交实名认证信息
        if (AndroidUtils.isNullOrEmpty(avatarUrl)) {
            AndroidUtils.Toast(QiYeRenZhengActivity.this, "请上传营业执照");
        }
        if (AndroidUtils.isNullOrEmpty(sfzUrl)) {
            AndroidUtils.Toast(QiYeRenZhengActivity.this, "请上传身份证");
        }
        showWaiteDialog("正在加载...");
        RetrofitFactory.getInstence().API().companyAuth(new RealCompanyBean(avatarUrl, sfzUrl)).compose(TransformerUi.setThread()).subscribe(new BaseObserver<Object>(QiYeRenZhengActivity.this) {
            @Override
            protected void onSuccess(BaseEntity<Object> t) throws Exception {
                hideWaiteDialog();
                if (t.getStatus() == 100) {

                    Logger.e(t.getData().toString());

                    ToastUtils.showShort("提交成功！");
                    finish();
                } else {
                    AndroidUtils.Toast(QiYeRenZhengActivity.this, "" + t.getMessage());
                }
            }

            @Override
            protected void onFail(Throwable throwable) throws Exception {
                hideWaiteDialog();
               ToastUtils.showShort("认证出错");
            }
        });
    }

    /**
     * 拍照
     */
    private void tackPhoto(int phoneType) {
        AndPermissionUtils andPermissionUtils = new AndPermissionUtils();
        andPermissionUtils.requestPermission(this, new AndPermissionUtils.PermissionsCallBack() {
            @Override
            public void permissionsSuc() {
                //拍照和相册选择Dialog
                new TakePhotoDialog(QiYeRenZhengActivity.this, R.style.dialog_center)
                        .setOnTakeClickListener(new TakePhotoDialog.OnTakeClickListener() {
                            @Override
                            public void onClick(int type) {
                                if (type == 1) {
                                    //相机
                                    AndPermissionUtils cameraPermission = new AndPermissionUtils();

                                    cameraPermission.requestPermission(QiYeRenZhengActivity.this, new AndPermissionUtils.PermissionsCallBack() {
                                        @Override
                                        public void permissionsSuc() {
                                            if (phoneType == 1) {//企业拍照
                                                //拍照
                                                PictureSelector.create(QiYeRenZhengActivity.this)
                                                        .openCamera(PictureMimeType.ofImage())
                                                        .forResult(QIYE_CAMARA_REQUEST_CODE);
                                            } else {
                                                //拍照
                                                PictureSelector.create(QiYeRenZhengActivity.this)
                                                        .openCamera(PictureMimeType.ofImage())
                                                        .forResult(SHENGFENZHENG_CAMARA_REQUEST_CODE);
                                            }

                                        }

                                        @Override
                                        public void permissionFail(@NonNull List<String> permissions) {
                                            if (AndPermission.hasAlwaysDeniedPermission(QiYeRenZhengActivity.this, permissions)) {
                                                showSettingDialog(QiYeRenZhengActivity.this, permissions);
                                            }
                                        }
                                    }, Permission.CAMERA);


                                } else {
                                    if (phoneType == 1) {
                                        //相册
                                        PictureSelector.create(QiYeRenZhengActivity.this)
                                                .openGallery(PictureMimeType.ofImage())
                                                .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                                                .maxSelectNum(1)
                                                .minSelectNum(1)
                                                .imageSpanCount(4)
                                                .selectionMode(PictureConfig.MULTIPLE)
                                                .forResult(QIYE_PHONE_REQUESTCODE);
                                    } else {
                                        //相册
                                        PictureSelector.create(QiYeRenZhengActivity.this)
                                                .openGallery(PictureMimeType.ofImage())
                                                .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                                                .maxSelectNum(1)
                                                .minSelectNum(1)
                                                .imageSpanCount(4)
                                                .selectionMode(PictureConfig.MULTIPLE)
                                                .forResult(SHENGFENZHENG_PHONE_REQUESTCODE);
                                    }

                                }
                            }
                        });
            }

            @Override
            public void permissionFail(@NonNull List<String> permissions) {
                if (AndPermission.hasAlwaysDeniedPermission(QiYeRenZhengActivity.this, permissions)) {
                    showSettingDialog(QiYeRenZhengActivity.this, permissions);
                }
            }
        }, Permission.WRITE_EXTERNAL_STORAGE);
    }


    /**
     * Display setting dialog.
     */
    private void showSettingDialog(Context context, final List<String> permissions) {
        List<String> permissionNames = Permission.transformText(context, permissions);
        String message = context.getString(R.string.message_permission_always_failed,
                TextUtils.join("\n", permissionNames));

        new AlertDialog.Builder(context).setCancelable(false)
                .setTitle("权限申请")
                .setMessage(message)
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setPermission();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }

    /**
     * Set permissions.
     */
    private void setPermission() {
        AndPermission.with(QiYeRenZhengActivity.this).runtime().setting().start(
                11);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == QIYE_CAMARA_REQUEST_CODE && resultCode == RESULT_OK) {//企业照相机拍照
            qiyeImages = PictureSelector.obtainMultipleResult(data);

            getAndShowImgUrl(1, qiyeImages, iv_qiye_yingyezhizhao);
        } else if (requestCode == SHENGFENZHENG_CAMARA_REQUEST_CODE && resultCode == RESULT_OK) {//身份证照相机拍照
            shengfenzhengImages = PictureSelector.obtainMultipleResult(data);
            getAndShowImgUrl(2, shengfenzhengImages, iv_qiye_shengfenzheng);

        } else if (requestCode == QIYE_PHONE_REQUESTCODE && resultCode == RESULT_OK) {//企业相册
            List<LocalMedia> mPics = PictureSelector.obtainMultipleResult(data);
            getAndShowImgUrl(1, mPics, iv_qiye_yingyezhizhao);
        } else if (requestCode == SHENGFENZHENG_PHONE_REQUESTCODE && resultCode == RESULT_OK) {//身份证相册
            List<LocalMedia> mPics = PictureSelector.obtainMultipleResult(data);
            getAndShowImgUrl(2, mPics, iv_qiye_shengfenzheng);
        }

    }


    private void getAndShowImgUrl(int type, List<LocalMedia> logImages, ImageView imageView) {
        LocalMedia localMedia = logImages.get(0);
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
        showWaiteDialog("正在加载...");
        RetrofitFactory.getInstence().API().uploadImageNew("3", pBody).compose(TransformerUi.setThread()).subscribe(new BaseObserver<Object>(QiYeRenZhengActivity.this) {
            @Override
            protected void onSuccess(BaseEntity<Object> t) throws Exception {
                hideWaiteDialog();
                if (t.getStatus() == 100) {

                    Logger.e(t.getData().toString());
                    if (type == 1) {
                        avatarUrl = t.getData().toString();
                    } else if (type == 2) {
                        sfzUrl = t.getData().toString();
                    }

                    RequestOptions options = new RequestOptions()
                            .centerCrop()
                            .placeholder(R.color.colorGrayF2F6F9)
                            .diskCacheStrategy(DiskCacheStrategy.ALL);
                    Glide.with(QiYeRenZhengActivity.this)
                            .load(camaraPath)
                            .apply(options)
                            .into(imageView);
                }
            }

            @Override
            protected void onFail(Throwable throwable) throws Exception {
             hideWaiteDialog();
             ToastUtils.showShort("头像上传失败");
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_qiye_renzheng;
    }

    @Override
    protected void initDetailData() {

    }
}
