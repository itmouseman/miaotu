package com.widget.miaotu.master.mine.activity;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
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
import com.widget.miaotu.common.utils.other.LoadingUtil;
import com.widget.miaotu.common.utils.rclayout.CustomEditText;
import com.widget.miaotu.common.utils.rclayout.RCImageView;
import com.widget.miaotu.common.utils.ui.TakePhotoDialog;
import com.widget.miaotu.http.BaseObserver;
import com.widget.miaotu.http.bean.BaseEntity;
import com.widget.miaotu.http.bean.EditUserInfoBean;
import com.widget.miaotu.http.retrofit.RetrofitFactory;
import com.widget.miaotu.http.retrofit.TransformerUi;
import com.widget.miaotu.master.other.login.BindMobileActivity;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


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


    private String[] sex = new String[]{"男", "女"};


    private List<Uri> mSelected;

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


    private String type = "0";


    @Override
    protected void initData() {

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
        Logger.e("性别  = " + SPStaticUtils.getString(SPConstant.SEX));
        if ("1".equals(SPStaticUtils.getString(SPConstant.SEX))) {
            btnMan.setChecked(true);
            btnWoman.setChecked(false);
        } else {
            btnWoman.setChecked(true);
            btnMan.setChecked(false);
        }
        tvMeInfoContact.setText(SPStaticUtils.getString(SPConstant.MOBILE));
//        etInputOldphone.setInputText(SPStaticUtils.getString(SPConstant.MOBILE) + "");
//        tvMeInfoEmail.setText(SPStaticUtils.getString(SPConstant.EMAIL));
//        tvMeInfoTitle.setText(SPStaticUtils.getString(SPConstant.TITLE_LABEL));
//        tvMeInfoDepartment.setText(SPStaticUtils.getString(SPConstant.DEPARTMENT));
//        tvMeInfoPositionType.setText(SPStaticUtils.getString(SPConstant.POSITION_TYPE));
//        tvMeInfoPosition.setText(SPStaticUtils.getString(SPConstant.POSITION));
        if ("-1".equals(SPStaticUtils.getString(SPConstant.ISAUTH))) {
            tvMeRealName.setText("去认证");//
        } else if ("0".equals(SPStaticUtils.getString(SPConstant.ISAUTH))) {
            tvMeRealName.setText("审核中"); //
        } else if ("1".equals(SPStaticUtils.getString(SPConstant.ISAUTH))) {
            tvMeRealName.setText("已认证");//
        } else if ("2".equals(SPStaticUtils.getString(SPConstant.ISAUTH))) {
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
            //            //昵称设置
//            case R.id.ll_me_info_name:
//                new InputTextMsgDialog(this, R.style.dialog_center)
//                        .setHint("请输入昵称")
//                        .setBtnText("保存")
//                        .setMaxNumber(15)
//                        .setmOnTextSendListener(new InputTextMsgDialog.OnTextSendListener() {
//                            @Override
//                            public void onTextSend(String msg) {
//                                tvMeInfoNickname.setText(msg);
//                            }
//                        });
//                break;
            //性别选择
//            case R.id.ll_me_info_sex:
////                OptionsPickerView pvOptions = new OptionsPickerBuilder(MeInfoActivity.this, new OnOptionsSelectListener() {
////                    @Override
////                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
////                        String tx = sexs.get(options1);
////                        tvMeInfoSex.setText(tx);
////                    }
////                }).build();
////                pvOptions.setPicker(sexs);
////                pvOptions.show();
//                break;

            //实名认证
            case R.id.ll_me_real_name:
                if ("0".equals(SPStaticUtils.getString(SPConstant.ISAUTH)) || "1".equals(SPStaticUtils.getString(SPConstant.ISAUTH))) {
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
                intent5.putExtra("type", "1");
                intent5.setClass(MeInfoActivity.this, BindMobileActivity.class);
                startActivity(intent5);
                break;
//            //电子邮箱设置
//            case R.id.ll_me_info_email:
//                new InputTextMsgDialog(this, R.style.dialog_center)
//                        .setHint("请输入电子邮箱")
//                        .setBtnText("保存")
//                        .setMaxNumber(25)
//                        .setmOnTextSendListener(new InputTextMsgDialog.OnTextSendListener() {
//                            @Override
//                            public void onTextSend(String msg) {
//                                if (RegexUtils.isEmail(msg)) {
//                                    tvMeInfoEmail.setText(msg);
//                                } else {
//                                    ToastUtils.showShort("邮箱格式不正确!");
//                                    return;
//                                }
//                            }
//                        });
//                break;
//            //所属部门设置
//            case R.id.ll_me_info_department:
//                new InputTextMsgDialog(this, R.style.dialog_center)
//                        .setHint("请输入所属部门")
//                        .setBtnText("保存")
//                        .setMaxNumber(15)
//                        .setmOnTextSendListener(new InputTextMsgDialog.OnTextSendListener() {
//                            @Override
//                            public void onTextSend(String msg) {
//                                tvMeInfoDepartment.setText(msg);
//                            }
//                        });
//                break;
//            //职位设置
//            case R.id.ll_me_info_position:
//                new InputTextMsgDialog(this, R.style.dialog_center)
//                        .setHint("职位设置")
//                        .setBtnText("保存")
//                        .setMaxNumber(15)
//                        .setmOnTextSendListener(new InputTextMsgDialog.OnTextSendListener() {
//                            @Override
//                            public void onTextSend(String msg) {
//                                tvMeInfoPosition.setText(msg);
//                            }
//                        });
//                break;
            //职位类型设置
            case R.id.ll_me_info_position_type:
                Intent intent = new Intent();
//                intent.putExtra("dicType", 1);
//                intent.setClass(MeInfoActivity.this, PositionListActivity.class);
//                startActivityForResult(intent, REQUEST_CODE_POSITION);

                intent.setClass(MeInfoActivity.this, MyPositionListActivity.class);
                startActivityForResult(intent, REQUEST_CODE_POSITION);
                break;

            //头衔标签
            case R.id.ll_me_info_position_title:
                Intent intent2 = new Intent();
                intent2.putExtra("dicType", 2);
//                intent2.setClass(MeInfoActivity.this, PositionListActivity.class);
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
            int mimeType = localMedia.getMimeType();
            String path;

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
                    .placeholder(R.color.color_4d)
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
                        LoadingUtil.dissmiss();
                        Glide.with(MeInfoActivity.this).load(fileByUri).into(avatar);
                        avatarUrl = t.getData().toString();
                    } else {
                        LoadingUtil.loadingClose(false, "头像上传失败!" + t.getMessage());
                    }
                }

                @Override
                protected void onFail(Throwable throwable) throws Exception {
                    LoadingUtil.loadingClose(false, "头像上传失败!");
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
                    finish();
                } else {
//                    ToastUtils.showShort(t.getMessage());
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