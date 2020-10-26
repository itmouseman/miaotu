package com.widget.miaotu.master.mine.activity;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.SPStaticUtils;
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
import com.widget.miaotu.base.BaseActivity;
import com.widget.miaotu.common.constant.BaseConstants;
import com.widget.miaotu.common.constant.SPConstant;
import com.widget.miaotu.common.utils.AndPermissionUtils;
import com.widget.miaotu.common.utils.rclayout.CustomEditText;
import com.widget.miaotu.common.utils.rclayout.RCImageView;

import com.widget.miaotu.common.utils.rxbus.HomeUpdateChange;
import com.widget.miaotu.common.utils.rxbus.RxBus;
import com.widget.miaotu.common.utils.ui.GlideEngine;
import com.widget.miaotu.common.utils.ui.TakePhotoDialog;
import com.widget.miaotu.http.BaseObserver;
import com.widget.miaotu.http.bean.BaseEntity;
import com.widget.miaotu.http.bean.EditUserInfoBean;
import com.widget.miaotu.http.retrofit.RetrofitFactory;
import com.widget.miaotu.http.retrofit.TransformerUi;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 个人信息编辑
 */

public class MeInfoActivity extends BaseActivity implements BaseConstants {

    private static final int REQUEST_CODE_SETTING = 112;
    @BindView(R.id.niv_me_info_avatar)
    RCImageView avatar;
    @BindView(R.id.tv_me_info_sex)
    TextView tvMeInfoSex;
    @BindView(R.id.tv_me_info_nickname)
    TextView tvMeInfoNickname;
    @BindView(R.id.tv_me_info_contact)
    TextView tvMeInfoContact;
    @BindView(R.id.tv_me_info_email)
    TextView tvMeInfoEmail;
    @BindView(R.id.tv_me_info_department)
    TextView tvMeInfoDepartment;
    @BindView(R.id.tv_me_info_position)
    TextView tvMeInfoPosition;
    @BindView(R.id.tv_me_info_title)
    TextView tvMeInfoTitle;
    @BindView(R.id.tv_me_info_position_type)
    TextView tvMeInfoPositionType;

    @BindView(R.id.et_input_username)
    CustomEditText etInputUsername;
    @BindView(R.id.btnMan)
    RadioButton btnMan;
    @BindView(R.id.btnWoman)
    RadioButton btnWoman;
    @BindView(R.id.et_input_oldphone)
    CustomEditText etInputOldphone;
    @BindView(R.id.et_input_position)
    CustomEditText etInputPosition;
    @BindView(R.id.bt_me_info_save)
    TextView btMeInfoSave;
    @BindView(R.id.tv_me_real_name)
    TextView tvMeRealName;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_right)
    Button btn_right;


    /**
     * 职位类型ID
     */
    private int job_type = 0;

    /**
     * 职位类型名称
     */
    private String job_name = "";

    /**
     * 头衔标签
     */
    private String title = "";

    /**
     * 头像URL
     */
    private String avatarUrl = "";


    @Override
    protected void initData() {

//        RxBus.getInstance().toObservableSticky(this, EditUserInfoChange.class).subscribe(new Consumer<EditUserInfoChange>() {
//            @Override
//            public void accept(EditUserInfoChange editUserInfoChange) throws Exception {
//                if (!TextUtils.isEmpty(editUserInfoChange.getPhone())) {
//                    etInputOldphone.setInputText(editUserInfoChange.getPhone());
//                }
//
//            }
//        });
    }

    @Override
    protected boolean isUseFullScreenMode() {
        return true;
    }

    @Override
    protected void initView() {

        tvTitle.setText("编辑个人信息");
        btn_right.setVisibility(View.VISIBLE);
        btn_right.setText("保存");
        btn_right.setTextColor(Color.parseColor("#03DAC5"));


        Glide.with(this).load(SPStaticUtils.getString(SPConstant.AVATAR)).into(avatar);
        avatarUrl = SPStaticUtils.getString(SPConstant.AVATAR);
        etInputUsername.setInputText(SPStaticUtils.getString(SPConstant.NICK_NAME) + "");
        tvMeInfoSex.setText(SPStaticUtils.getString(SPConstant.SEX));

        if ("1".equals(SPStaticUtils.getString(SPConstant.SEX))) {
            btnMan.setChecked(true);
            btnWoman.setChecked(false);
        } else {
            btnWoman.setChecked(true);
            btnMan.setChecked(false);
        }
        tvMeInfoContact.setText(SPStaticUtils.getString(SPConstant.MOBILE));

        if ("-1".equals(SPStaticUtils.getString(SPConstant.ISAUTH))) {//是否认证: -1 未提交 1 审核中 2 已通过 3 未通过
            tvMeRealName.setText("去认证");//
        } else if ("1".equals(SPStaticUtils.getString(SPConstant.ISAUTH))) {
            tvMeRealName.setText("审核中"); //
        } else if ("2".equals(SPStaticUtils.getString(SPConstant.ISAUTH))) {
            tvMeRealName.setText("已认证");//
        } else if ("3".equals(SPStaticUtils.getString(SPConstant.ISAUTH))) {
            tvMeRealName.setText("未通过");
        }

        tvMeInfoPositionType.setText(SPStaticUtils.getString(SPConstant.POSITION_TYPE));
        etInputPosition.setInputText(SPStaticUtils.getString(SPConstant.POSITION).equals("null") ? "" : SPStaticUtils.getString(SPConstant.POSITION));

    }


    @SuppressLint("WrongConstant")
    @OnClick({R.id.btn_back, R.id.btn_right, R.id.ll_me_real_name, R.id.rl_me_avatar, R.id.ll_me_info_contact, R.id.ll_me_info_position_type, R.id.ll_me_info_position_title})
    public void onClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_right:
                saveEUserInfo();
                break;


            //实名认证
            case R.id.ll_me_real_name:
                if ("1".equals(SPStaticUtils.getString(SPConstant.ISAUTH)) || "2".equals(SPStaticUtils.getString(SPConstant.ISAUTH))) {
                    return;
                }
                Intent intent_real = new Intent();
                intent_real.setClass(MeInfoActivity.this, RealNameActivity.class);
                startActivity(intent_real);
                break;
            //头像设置
            case R.id.rl_me_avatar:
                AndPermissionUtils andPermissionUtils = new AndPermissionUtils();
                andPermissionUtils.requestPermission(this, new AndPermissionUtils.PermissionsCallBack() {
                    @Override
                    public void permissionsSuc() {
                        //拍照和相册选择Dialog
                        new TakePhotoDialog(MeInfoActivity.this, R.style.dialog_center)
                                .setOnTakeClickListener(new TakePhotoDialog.OnTakeClickListener() {
                                    @Override
                                    public void onClick(int type) {
                                        if (type == 1) {
                                            //相机
                                            AndPermissionUtils cameraPermission = new AndPermissionUtils();

                                            cameraPermission.requestPermission(MeInfoActivity.this, new AndPermissionUtils.PermissionsCallBack() {
                                                @Override
                                                public void permissionsSuc() {
                                                    //拍照
                                                    PictureSelector.create(MeInfoActivity.this)
                                                            .openCamera(PictureMimeType.ofImage())
                                                            .forResult(33);
                                                }

                                                @Override
                                                public void permissionFail(@NonNull List<String> permissions) {
                                                    if (AndPermission.hasAlwaysDeniedPermission(MeInfoActivity.this, permissions)) {
                                                        showSettingDialog(MeInfoActivity.this, permissions);
                                                    }
                                                }
                                            }, Permission.CAMERA);


                                        } else {
                                            //相册
                                            PictureSelector.create(MeInfoActivity.this)
                                                    .openGallery(PictureMimeType.ofImage())
                                                    .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
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
                        if (AndPermission.hasAlwaysDeniedPermission(MeInfoActivity.this, permissions)) {
                            showSettingDialog(MeInfoActivity.this, permissions);
                        }
                    }
                }, Permission.WRITE_EXTERNAL_STORAGE);

                break;

            //换手机号  有点问题
            case R.id.ll_me_info_contact:
                Intent intent5 = new Intent();
                intent5.setClass(MeInfoActivity.this, ChangePhoneOneActivity.class);
                startActivity(intent5);
                break;

            //职位类型设置
            case R.id.ll_me_info_position_type:
                Intent intent = new Intent();
                intent.setClass(MeInfoActivity.this, MyPositionListActivity.class);
                startActivityForResult(intent, REQUEST_CODE_POSITION);
                break;

            //头衔标签
            case R.id.ll_me_info_position_title:
                Intent intent2 = new Intent();
                intent2.putExtra("dicType", 2);
                startActivityForResult(intent2, REQUEST_CODE_TITLE_LABEL);
                break;
            case R.id.bt_me_info_save:

                break;
        }
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
        AndPermission.with(MeInfoActivity.this).runtime().setting().start(REQUEST_CODE_SETTING);
    }


    /**
     * 图片选择回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == REQUEST_CODE_POSITION && resultCode == RESULT_OK) {//职位选择返回
            job_name = data.getStringExtra("title");

            tvMeInfoPositionType.setText(job_name);
            job_type = data.getIntExtra("titleId", 0);

        } else if (requestCode == REQUEST_CODE_TITLE_LABEL && resultCode == RESULT_OK) {
            title = data.getStringExtra("title");
            tvMeInfoTitle.setText(title);
        } else if (requestCode == 33 && resultCode == RESULT_OK) {//照相机
            List<LocalMedia> logImages = PictureSelector.obtainMultipleResult(data);
            LocalMedia localMedia = logImages.get(0);

            if (localMedia.isCut() && !localMedia.isCompressed()) {
                // 裁剪过
                avatarUrl = localMedia.getCutPath();
            } else if (localMedia.isCompressed() || (localMedia.isCut() && localMedia.isCompressed())) {
                // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
                avatarUrl = localMedia.getCompressPath();
            } else {
                // 原图
                avatarUrl = localMedia.getPath();
            }
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.color.colorGrayF2F6F9)
                    .diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(MeInfoActivity.this)
                    .load(avatarUrl)
                    .apply(options)
                    .into(avatar);


            final File fileByUri = FileUtils.getFileByPath(avatarUrl);
            RequestBody photoRequestBody = RequestBody.create(MediaType.parse("image/*"), fileByUri);

            String fname = System.currentTimeMillis() + fileByUri.getName().substring(0, fileByUri.getName().indexOf(".")) + ".jpg";
            MultipartBody.Part pBody = MultipartBody.Part.createFormData("file", fname, photoRequestBody);

            RetrofitFactory.getInstence().API().uploadImageNew("3", pBody).compose(TransformerUi.setThread()).subscribe(new BaseObserver<Object>(this) {
                @Override
                protected void onSuccess(BaseEntity<Object> t) throws Exception {
                    if (t.getStatus() == 100) {

                        Logger.e("HyResult =  " + t.toString());

                        Glide.with(MeInfoActivity.this).load(fileByUri).into(avatar);
                        avatarUrl = t.getData().toString();
                    } else {
                        ToastUtils.showShort(t.getMessage());
                    }
                }

                @Override
                protected void onFail(Throwable throwable) throws Exception {
                    ToastUtils.showShort("头像上传失败");
                }
            });
        } else if (requestCode == REQUEST_CODE_SETTING) {

            Toast.makeText(MeInfoActivity.this, "申请权限", Toast.LENGTH_SHORT).show();

        }
    }


    /**
     * 保存用户信息
     */
    private void saveEUserInfo() {
        String nickname = etInputUsername.getInputText().trim();
        String sex;
        if (btnMan.isChecked()) {
            sex = "1";
        } else {
            sex = "2";
        }

        String department = etInputPosition.getInputText().trim();//公司
//                String mobile = etInputOldphone.getInputText().toString().trim();
        String position = tvMeInfoPositionType.getText().toString().trim();//职位

        //保存新接口
        EditUserInfoBean bean = new EditUserInfoBean(avatarUrl, nickname, sex, department, position);

        RetrofitFactory.getInstence().API().editUserInfo(bean).compose(TransformerUi.setThread()).subscribe(new BaseObserver<Object>(this) {
            @Override
            protected void onSuccess(BaseEntity<Object> t) throws Exception {
                if (t.getStatus() == 100) {
                    ToastUtils.showShort("保存成功！");
                    RxBus.getInstance().post(new HomeUpdateChange(false));
                    finish();
                } else {
                    ToastUtils.showShort(t.getMessage());
                }
            }

            @Override
            protected void onFail(Throwable throwable) throws Exception {
                ToastUtils.showShort("保存失败");
            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_me_info;
    }
}