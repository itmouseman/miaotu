package com.widget.miaotu.master.mine.activity;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.CleanUtils;
import com.blankj.utilcode.util.SPStaticUtils;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.orhanobut.logger.Logger;
import com.widget.miaotu.R;
import com.widget.miaotu.base.MBaseActivity;
import com.widget.miaotu.base.MVCControl;
import com.widget.miaotu.common.DataCleanManager;
import com.widget.miaotu.common.constant.BaseConstants;
import com.widget.miaotu.common.constant.SPConstant;
import com.widget.miaotu.common.utils.AndroidUtils;
import com.widget.miaotu.common.utils.IntentUtils;
import com.widget.miaotu.common.utils.rxbus.HomeUpdateChange;
import com.widget.miaotu.common.utils.rxbus.RxBus;
import com.widget.miaotu.http.bean.SettingBean;
import com.widget.miaotu.master.other.login.ActivityModifyPassword;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SettingActivity extends MBaseActivity {

    @BindView(R.id.rv_setting)
    RecyclerView rvSetting;
    @BindView(R.id.rel_setting_out)
    RelativeLayout relSettingOut;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_right)
    Button btn_right;


    private List<SettingBean> data = new ArrayList<>();

    @Override
    protected MVCControl createControl() {
        return null;
    }

    @Override
    protected void initView() {
        tvTitle.setText("设置");
        data.add(new SettingBean("修改密码", R.mipmap.ic_setting_modify_pwd));
        data.add(new SettingBean("清除缓存", R.mipmap.ic_setting_clear_cache));
//        data.add(new SettingBean("意见反馈", R.mipmap.ic_setting_feedback));
        data.add(new SettingBean("版本更新", R.mipmap.ic_setting_feedback));
        data.add(new SettingBean("关于苗途", R.mipmap.ic_seting_about));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initDetailData() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rvSetting.setLayoutManager(linearLayoutManager);
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.shape_line));
        rvSetting.addItemDecoration(divider);
        rvSetting.setAdapter(new CommonAdapter<SettingBean>(this, R.layout.item_setting_list, data) {
            @Override
            protected void convert(ViewHolder holder, SettingBean settingBean, int position) {

                TextView title = holder.getView(R.id.tv_setting_title);
                title.setText(settingBean.getTitle());
                final TextView tvCacheSize = holder.getView(R.id.tv_cache_size);
                if (settingBean.getTitle().equals("清除缓存")) {
                    try {
                        tvCacheSize.setText("" + DataCleanManager.getTotalCacheSize(mContext));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    tvCacheSize.setVisibility(View.VISIBLE);
                } else if (settingBean.getTitle().equals("版本更新")) {
                    //版本号
                    try {
                        String pkName = mContext.getPackageName();
                        tvCacheSize.setText("V" + mContext.getPackageManager().getPackageInfo(pkName, 0).versionName);
                    } catch (Exception e) {
                        return;
                    }
                    tvCacheSize.setVisibility(View.VISIBLE);
                }

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (position) {
                            case 0:
                                // 修改密码
                                startActivity(new Intent(SettingActivity.this, ActivityModifyPassword.class));
                                break;
                            case 1:
                                //清除内部缓存
                                CleanUtils.cleanInternalCache();
                                //清除外部缓存
                                CleanUtils.cleanExternalCache();
                                //清除内部文件
                                CleanUtils.cleanInternalFiles();
                                notifyDataSetChanged();
                                break;
                            case 2:
//                        // 跳转至意见反馈界面
//                        startActivity(new Intent(SettingActivity.this, FeedBackActivity.class));
                                // 版本更新
                                AndroidUtils.Toast(SettingActivity.this, "版本更新开发中。。。");
                                break;
                            case 3:
                                // 跳转至关于我们页面
                                startActivity(new Intent(SettingActivity.this, AboutUSActivity.class));
                                break;
                            case 4:
                                // 退出APP
                                new XPopup.Builder(SettingActivity.this)
                                        .isDestroyOnDismiss(false) //对于只使用一次的弹窗，推荐设置这个
                                        .asConfirm("确定退出登陆吗", "",
                                                "取消", "确定",
                                                new OnConfirmListener() {
                                                    @Override
                                                    public void onConfirm() {
//                                                        LoginPresenter.isCompany=false;
                                                        int num = SPStaticUtils.getInt(SPConstant.OPEN_APP_COUNT, 0);
                                                        SPStaticUtils.clear();
                                                        //增加APP启动次数数量
                                                        SPStaticUtils.put(SPConstant.OPEN_APP_COUNT, num);
                                                        finish();
//                                                        EventBus.getDefault().post(new HomeMenuEvent(0));
                                                    }
                                                }, null, false)
                                        .show();


                                break;
                        }
                    }
                });
            }

        });

        relSettingOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 退出APP

                new XPopup.Builder(SettingActivity.this)
                        .isDestroyOnDismiss(false) //对于只使用一次的弹窗，推荐设置这个
                        .asConfirm("确定退出登陆吗", "",
                                "取消", "确定",
                                new OnConfirmListener() {
                                    @Override
                                    public void onConfirm() {
                                        EMClient.getInstance().logout(false, new EMCallBack() {

                                            @Override
                                            public void onSuccess() {
                                                Logger.e("退出登录成功");
                                                SPStaticUtils.clear();
                                                finish();
                                            }

                                            @Override
                                            public void onProgress(int progress, String status) {

                                            }

                                            @Override
                                            public void onError(int code, String error) {

                                            }
                                        });


                                        RxBus.getInstance().post(new HomeUpdateChange(true));
                                    }
                                }, null, false)
                        .show();


            }
        });
    }
}
