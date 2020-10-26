package com.widget.miaotu.master.mine;

import androidx.fragment.app.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPStaticUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.orhanobut.logger.Logger;
import com.widget.miaotu.R;
import com.widget.miaotu.base.BaseFragment;
import com.widget.miaotu.common.constant.BaseConstants;
import com.widget.miaotu.common.constant.SPConstant;
import com.widget.miaotu.common.utils.IntentUtils;
import com.widget.miaotu.common.utils.ToastUtil;
import com.widget.miaotu.common.utils.rxbus.HomeUpdateChange;
import com.widget.miaotu.common.utils.rxbus.RxBus;
import com.widget.miaotu.http.ApiService;
import com.widget.miaotu.http.BaseObserver;
import com.widget.miaotu.http.bean.BaseEntity;
import com.widget.miaotu.http.bean.SMSLoginBeanNew;
import com.widget.miaotu.http.bean.TokenBeanNew;
import com.widget.miaotu.http.retrofit.RetrofitFactory;
import com.widget.miaotu.http.retrofit.TransformerUi;
import com.widget.miaotu.master.home.activity.CompanyDetailActivity;
import com.widget.miaotu.master.home.activity.HomeTgAndMmActivity;
import com.widget.miaotu.master.mine.activity.CompanyInfoActivity;
import com.widget.miaotu.master.mine.activity.FeedBackActivity;
import com.widget.miaotu.master.mine.activity.InvitFirendActivity;
import com.widget.miaotu.master.mine.activity.MeInfoActivity;
import com.widget.miaotu.master.mine.activity.MyCollectionNewActivity;
import com.widget.miaotu.master.mine.activity.SettingActivity;
import com.widget.miaotu.master.mine.activity.UserInfoNewActivity;
import com.widget.miaotu.master.other.login.LoginCodeActivity;
import com.yanzhenjie.permission.AndPermission;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

public class MineFragment extends BaseFragment {


    @BindView(R.id.ll_user_status)
    LinearLayout ll_user_status;
    @BindView(R.id.tv_me_nick_name)
    TextView tv_me_nick_name;
    @BindView(R.id.niv_me_avatar)
    ImageView niv_me_avatar;
    @BindView(R.id.tv_me_real_name)
    TextView tv_me_real_name;
    @BindView(R.id.tv_me_vip_end_time)
    TextView tv_me_vip_end_time;

    @BindView(R.id.tv_me_renewal_fee)
    TextView tv_me_renewal_fee;
    @BindView(R.id.iv_me_info_sex)
    ImageView iv_me_info_sex;


    private String yearUrl = "";

    public MineFragment() {
        // Required empty public constructor
    }

    @Override
    protected void initViewAndData(View view) {
        Logger.e("进入我的页面");

        RxBus.getInstance().toObservableSticky(this, HomeUpdateChange.class).subscribe(new Consumer<HomeUpdateChange>() {
            @Override
            public void accept(HomeUpdateChange homeUpdateChange) throws Exception {
                if (isLogin()) {
                    Logger.e("登录啦！  刷新界面");
                    initBasisInfo();
                    ll_user_status.setVisibility(View.VISIBLE);
                    if (homeUpdateChange.isChange()){
                        Logger.e("没有登录啦1！  刷新界面");
                        tv_me_nick_name.setText("登录 / 注册");
                        tv_me_vip_end_time.setText("登录查看更多信息");
                        niv_me_avatar.setImageResource(R.mipmap.ic_default_avatar);
                        ll_user_status.setVisibility(View.GONE);
                    }
                }else {
                    Logger.e("没有登录啦！  刷新界面");
                    tv_me_nick_name.setText("登录 / 注册");
                    tv_me_vip_end_time.setText("登录查看更多信息");
                    niv_me_avatar.setImageResource(R.mipmap.ic_default_avatar);
                    ll_user_status.setVisibility(View.GONE);
                }
            }
        });
        if (isLogin()) {
            initBasisInfo();
            ll_user_status.setVisibility(View.VISIBLE);
        }else {
            tv_me_nick_name.setText("登录 / 注册");
            niv_me_avatar.setImageResource(R.mipmap.ic_default_avatar);
            ll_user_status.setVisibility(View.GONE);
        }

    }

    @Override
    protected int getFragmentId() {
        return R.layout.fragment_mine;
    }


    @OnClick({R.id.rel_me_release, R.id.ll_me_head_info, R.id.rel_me_contact_customer, R.id.rel_me_business_management,
            R.id.rel_me_account, R.id.rel_me_invite,
            R.id.rel_me_collection, R.id.iv_me_info_set, R.id.rel_me_feed_back, R.id.rel_me_party, R.id.rel_me_purchase_offer})
    public void onClicked(View view) {
        switch (view.getId()) {
            case R.id.rel_me_release://我的发布
                if (!isLogin()) {
                    IntentUtils.startIntent(getActivity(), LoginCodeActivity.class);
                } else {
                    Intent userIntent = new Intent();
                    userIntent.putExtra("userId", SPStaticUtils.getString(SPConstant.USER_ID));
                    userIntent.setClass(getActivity(), UserInfoNewActivity.class);
                    startActivity(userIntent);
                }
                break;
            case R.id.ll_me_head_info://点击头像
                if (!isLogin()) {
                    startActivity(new Intent(getActivity(), LoginCodeActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), MeInfoActivity.class));
                }
                break;
            case R.id.rel_me_contact_customer://联系客服
                callPhone();
                break;
            case R.id.iv_me_info_set://设置界面
                if (!isLogin()) {
                    startActivity(new Intent(getFragmentContext(), LoginCodeActivity.class));
                } else {
                    startActivity(new Intent(getFragmentContext(), SettingActivity.class));
                }
                break;
            case R.id.rel_me_feed_back://意见反馈
                if (!isLogin()) {
                    startActivity(new Intent(getFragmentContext(), LoginCodeActivity.class));
                } else {
                    startActivity(new Intent(getFragmentContext(), FeedBackActivity.class));
                }
                break;
            case R.id.rel_me_collection://收藏关注
                if (!isLogin()) {
                    startActivity(new Intent(getFragmentContext(), LoginCodeActivity.class));
                } else {
                    startActivity(new Intent(getFragmentContext(), MyCollectionNewActivity.class));
                }

                break;
            case R.id.rel_me_business_management://企业管理
                if (!isLogin()) {
                    startActivity(new Intent(getFragmentContext(), LoginCodeActivity.class));
                } else {

                    if ("1" != SPStaticUtils.getString(SPConstant.ISCOMPANY)) {

                        IntentUtils.startIntent(getFragmentContext(), CompanyDetailActivity.class );
                    } else {
                        startActivity(new Intent(getFragmentContext(), CompanyInfoActivity.class));
                    }
                }


                break;
            case R.id.rel_me_invite://邀请好友
                if (!isLogin()) {
                    startActivity(new Intent(getFragmentContext(), LoginCodeActivity.class));
                } else {

                    startActivity(new Intent(getFragmentContext(), InvitFirendActivity.class));
                }
                break;
            case R.id.rel_me_purchase_offer://参与报价
            case R.id.rel_me_party://我的活动
            case R.id.rel_me_account://我的账户
                ToastUtils.showShort("敬请期待");
                break;
        }
    }


    /**
     * 打电话弹窗
     */
    @SuppressLint("WrongConstant")
    private void callPhone() {
        AndPermission.with(this)
                .runtime()
                .permission(Manifest.permission.CALL_PHONE)
                .onGranted(permissions -> {
                    // Storage permission are allowed.

                    new XPopup.Builder(getActivity())
                            .isDestroyOnDismiss(false) //对于只使用一次的弹窗，推荐设置这个
                            .asConfirm("电话咨询", BaseConstants.CUSTOMER_SERVICE_PHONE,
                                    "取消", "拨打电话",
                                    new OnConfirmListener() {
                                        @Override
                                        public void onConfirm() {
                                            IntentUtils.openCall(getFragmentContext(), BaseConstants.CUSTOMER_SERVICE_PHONE);
                                        }
                                    }, null, false)
                            .show();
                })
                .onDenied(permissions -> {
                    // Storage permission are not allowed.
                    ToastUtil.showShort("需要电话权限", getFragmentContext());
                })
                .start();

    }

    /**
     * 加载基础信息
     */
    private void initBasisInfo() {

        RetrofitFactory.getInstence().API().getUserInfo(new TokenBeanNew(""))
                .compose(TransformerUi.setThread())
                .subscribe(new BaseObserver<SMSLoginBeanNew>(getActivity()) {
                    @Override
                    protected void onSuccess(BaseEntity<SMSLoginBeanNew> t) throws Exception {
                        //获取用户信息成功之后登录环信
                        Logger.e("数据请求成功" + t.getData());
                        SMSLoginBeanNew data = t.getData();
//                        EMClient.getInstance().login(data.userId.toString(), MD5Util.md5Encode(data.userId.toString()), object : EMCallBack {
//                            override fun onSuccess() {
//                                EMClient.getInstance().groupManager().loadAllGroups()
//                                EMClient.getInstance().chatManager().loadAllConversations()
//                            }
//
//                            override fun onError(i: Int, s: String) {
//                                XLog.e("SLL:$s")
//                            }
//
//                            override fun onProgress(i: Int, s: String) {
//                                XLog.e("SLL:$s")
//                            }
//                        })

                        //最后统一服务后台配
                        yearUrl = "https://www.miaoto.net/zmh/H5Page/points/huiyuantequan.html?type=2";
                        // 邀请分享地址
                        SPStaticUtils.put(SPConstant.INVITE_SHARE_URL, "https://www.miaoto.net/zmh/H5Page/points/sharehaoyou.html");
                        // 邀请好友URL
                        SPStaticUtils.put(SPConstant.INVITE_FRIENDS_URL, "https://www.miaoto.net/zmh/H5Page/points/sharehaoyou.html" + "?yzm=" + data.getUserId());//data.userInfo.shareCode
                        // 加入VIP地址
                        SPStaticUtils.put(SPConstant.ADD_VIP_URL, "https://www.miaoto.net/zmh/H5Page/points/jiaruvip.html");
                        // 合伙人规则URL
                        SPStaticUtils.put(SPConstant.PARTNER_RULE_URL, "https://www.miaoto.net/zmh/H5Page/points/PartnerRules.html");
                        // 苗途币使用规则
                        SPStaticUtils.put(SPConstant.GET_MONEY_HELP, "https://www.miaoto.net/zmh/H5Page/points/getMoneyhelp.html");
                        // VIP年费价格
//                        SPStaticUtils.put(SPConstant.VIP_BUY_YEAR_PRICE, "2998")

                        //昵称
                        tv_me_nick_name.setText(data.getNickname());
                        //头像
//                        Glide.with(context).load(data.avatar).placeholder(R.mipmap.ic_default_avatar).into(niv_me_avatar!!)
                        Glide.with(getFragmentContext()).load(data.getAvatar()).into(niv_me_avatar);
//                        //保存用户ID
//                        SPStaticUtils.put(SPConstant.USER_ID, data.userId+"")
//                        //保存用户名
                        SPStaticUtils.put(SPConstant.USER_NAME, data.getNickname() + "");
                        //保存性别
                        SPStaticUtils.put(SPConstant.SEX, data.getSex() + "");//if ( "1".equals(data.sex)) "男" else "女"
//                        //联系方式
                        SPStaticUtils.put(SPConstant.MOBILE, data.getPhone() + "");
//                        //保存昵称
                        SPStaticUtils.put(SPConstant.NICK_NAME, data.getNickname() + "");
//                        //头像
                        SPStaticUtils.put(SPConstant.AVATAR, data.getAvatar() + "");
//                        //是否VIP
                        SPStaticUtils.put(SPConstant.IS_VIP, data.getIsVIP() + "");
                        //公司
                        SPStaticUtils.put(SPConstant.POSITION, data.getCompany() + "");
                        //职位
                        SPStaticUtils.put(SPConstant.POSITION_TYPE, data.getJobTitle() + "");
                        //邀请码
                        SPStaticUtils.put(SPConstant.INVITE_CODE, data.getInviteCode() + "");
                        //是否实名
                        SPStaticUtils.put(SPConstant.ISAUTH, data.getIsAuth() + "");
                        //vip到期时间
                        SPStaticUtils.put(SPConstant.VIP_END_TIME, data.getVipEndTime() + "");
                        //邀请码
                        SPStaticUtils.put(SPConstant.BALANCE, data.getBalance() + "");
                        switch (data.getIsAuth()) {
                            case "-1":
                                tv_me_real_name.setText("去认证");
                                break;
                            case "1":
                                tv_me_real_name.setText("审核中");
                                break;
                            case "2":
                                tv_me_real_name.setText("已认证");
                                tv_me_real_name.setBackgroundResource(R.drawable.shape_me_real);
                                tv_me_real_name.setTextColor(Color.parseColor("#ffffff"));
                                break;
                            case "3":
                                tv_me_real_name.setText("去认证");
                                break;
                        }

                        if ("1" == data.getIsCompany()) {
                            SPStaticUtils.put(SPConstant.ISCOMPANY, "1");
                        } else {
                            SPStaticUtils.put(SPConstant.ISCOMPANY, "0");
                        }
                        if ("2" == data.getIsVIP()) {
                            tv_me_vip_end_time.setText("会员有效期至" + data.getVipEndTime());
                            tv_me_nick_name.setTextColor(Color.parseColor("#F25900"));
//                            iv_me_vip_icon!!.visibility = View.VISIBLE
                            tv_me_nick_name.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.mipmap.ic_me_vip), null);
                            tv_me_renewal_fee.setText("立即续费");
                        } else if ("1" == data.getIsVIP()) {
                            tv_me_vip_end_time.setText("会员已过期");
                            tv_me_nick_name.setTextColor(Color.parseColor("#333333"));
//                            iv_me_vip_icon!!.visibility = View.VISIBLE
                            tv_me_nick_name.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.mipmap.ic_me_vip), null);
                            tv_me_renewal_fee.setText("立即续费");
                        } else {
                            tv_me_vip_end_time.setText("您还不是VIP会员");
                            tv_me_nick_name.setTextColor(Color.parseColor("#333333"));
//                            iv_me_vip_icon!!.visibility = View.GONE
                            tv_me_nick_name.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                            tv_me_renewal_fee.setText("立即开通");
                        }
                        tv_me_renewal_fee.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //加入会员
//                                Intent intent =new  Intent();
//                                intent.putExtra("url", yearUrl);
//                                intent.setClass(getFragmentContext(), BuyVIPActivity.class);
//                                startActivity(intent);
                            }
                        });
                        if (1 == Integer.parseInt(data.getSex())) {
                            iv_me_info_sex.setImageResource(R.mipmap.ic_me_man);
                        } else {
                            iv_me_info_sex.setImageResource(R.mipmap.ic_me_woman);
                        }


                    }

                    @Override
                    protected void onFail(Throwable throwable) throws Exception {
                        Logger.e("数据请求失败" + throwable.getMessage());
                    }
                });


    }
}