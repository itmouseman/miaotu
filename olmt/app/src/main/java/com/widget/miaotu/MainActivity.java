package com.widget.miaotu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.amap.api.location.AMapLocation;
import com.blankj.utilcode.util.SPStaticUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hyphenate.chat.EMClient;
import com.orhanobut.logger.Logger;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.widget.miaotu.base.BaseActivity;
import com.widget.miaotu.common.constant.SPConstant;
import com.widget.miaotu.common.utils.AndroidUtils;
import com.widget.miaotu.common.utils.IntentUtils;
import com.widget.miaotu.common.utils.PermissionTool;
import com.widget.miaotu.common.utils.ShapeTextView;
import com.widget.miaotu.common.utils.other.LocationUtil;
import com.widget.miaotu.common.utils.rxbus.HomeUpdateChange;
import com.widget.miaotu.common.utils.rxbus.MessageUnReadNum;
import com.widget.miaotu.common.utils.rxbus.MyLocation;
import com.widget.miaotu.common.utils.rxbus.RxBus;
import com.widget.miaotu.http.BaseObserver;
import com.widget.miaotu.http.bean.BaseEntity;
import com.widget.miaotu.http.bean.MsgCountBean;
import com.widget.miaotu.http.bean.SeedlingInfo;
import com.widget.miaotu.http.retrofit.RetrofitFactory;
import com.widget.miaotu.http.retrofit.TransformerUi;
import com.widget.miaotu.master.home.fragment.HomeFragment;
import com.widget.miaotu.master.message.FirendFragment;
import com.widget.miaotu.master.miaopu.MiaoPuFragment;
import com.widget.miaotu.master.mine.MineFragment;
import com.widget.miaotu.master.other.login.LoginCodeActivity;
import com.yanzhenjie.permission.runtime.Permission;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

public class MainActivity extends BaseActivity {

    @BindView(R.id.navigation)
    BottomNavigationView bottomNavigationView;
    @BindView(R.id.mainFrame)
    FrameLayout mainFrame;
    private HomeFragment homeFragment;
    private MiaoPuFragment miaopuFragment;
    private FirendFragment firendFragment;
    private MineFragment mineFragment;
    private Fragment[] fragments;
    private int lastfragment = 0;

    private static final int REQUEST_CODE_SETTING = 1;
    private ShapeTextView mMsgCountTextView;


    @Override
    protected void initData() {

        //升级检测
        AndroidUtils.updateCheck(this);
        //高德地图定位
        locationInit();
        if (BaseApplication.getmDaoSession().getSeedlingInfoDao().loadAll().size() == 0) {
            BaseApplication.getmDaoSession().getSeedlingInfoDao().insertInTx(getInfo(this));
        }

        if (!StringUtils.isEmpty(SPStaticUtils.getString(SPConstant.USER_ID))) {
            //设置未读消息
            getMsgCount();
        }

        //获取整个的NavigationView
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
        //这里就是获取所添加的每一个Tab(或者叫menu)，
        View tab = menuView.getChildAt(2);
        BottomNavigationItemView itemView = (BottomNavigationItemView) tab;
        //加载我们的角标View，新创建的一个布局
        View badge = LayoutInflater.from(this).inflate(R.layout.menu_badge, menuView, false);
        //添加到Tab上
        itemView.addView(badge);
        mMsgCountTextView = (ShapeTextView) badge.findViewById(R.id.tv_msg_count);

        RxBus.getInstance().toObservableSticky(this, MessageUnReadNum.class).subscribe(new Consumer<MessageUnReadNum>() {
            @Override
            public void accept(MessageUnReadNum messageUnReadNum) throws Exception {
                showUnMessageNum(messageUnReadNum.getUnReadMessage());
            }
        });


    }

    @Override
    protected boolean isUseFullScreenMode() {
        return true;
    }

    /**
     * 获取未读消息数量
     */
    private void getMsgCount() {
        RetrofitFactory.getInstence().API().msgCount().compose(TransformerUi.setThread())
                .subscribe(new BaseObserver<MsgCountBean>(MainActivity.this) {
                    @Override
                    protected void onSuccess(BaseEntity<MsgCountBean> t) throws Exception {
                        if (t.getStatus() == 100) {
                            MsgCountBean msgCountBean = t.getData();
                            int shequ = msgCountBean.getCommunityMessageCount().intValue();
                            int xitong = msgCountBean.getStrangerCount().intValue();
                            int yanzheng = msgCountBean.getVerifyCount().intValue();
                            int moshengren = msgCountBean.getStrangerCount().intValue();
                            int numMessageSize = EMClient.getInstance().chatManager().getUnreadMessageCount();
                            showUnMessageNum(numMessageSize + xitong + yanzheng + moshengren);
                        }
                    }

                    @Override
                    protected void onFail(Throwable throwable) throws Exception {

                    }
                });
    }

    private void showUnMessageNum(int messageNum) {

        if (messageNum == 0) {
            mMsgCountTextView.setText("0");
            mMsgCountTextView.setVisibility(View.GONE);
        } else {
            mMsgCountTextView.setVisibility(View.VISIBLE);
        }
        if (messageNum > 99) {
            mMsgCountTextView.setText("99+");
        } else {
            mMsgCountTextView.setText(String.valueOf(messageNum));
        }

    }


    public static List<SeedlingInfo> getInfo(Context context) {
        InputStream is = null;
        ByteArrayOutputStream bos = null;
        try {
            is = context.getAssets().open("info.json");
            bos = new ByteArrayOutputStream();
            byte[] bytes = new byte[4 * 1024];
            int len = 0;
            while ((len = is.read(bytes)) != -1) {
                bos.write(bytes, 0, len);
            }
            final String json = new String(bos.toByteArray());
//            final List<SeedlingInfo> info = JSON.parseArray(json, SeedlingInfo.class);
            final List<SeedlingInfo> info = new Gson().fromJson(json, new TypeToken<List<SeedlingInfo>>() {
            }.getType());
            return info;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
                if (bos != null)
                    bos.close();
            } catch (IOException e) {
                Log.e("INFO", "getStates", e);
            }
        }
        return null;
    }

    @SuppressLint("WrongConstant")
    private void locationInit() {

        PermissionTool.requestPermission(this, new PermissionTool.PermissionQuestListener() {
            @Override
            public void onGranted() {
                LocationUtil util = new LocationUtil();
                util.startLocate(MainActivity.this);
                util.setLocationCallBack(new LocationUtil.ILocationCallBack() {
                    @Override
                    public void callBack(String str, double lat, double lgt, AMapLocation aMapLocation) {
                        SPStaticUtils.put(SPConstant.LONGITUDE, String.valueOf(lgt));
                        SPStaticUtils.put(SPConstant.LATITUDE, String.valueOf(lat));
                        SPStaticUtils.put(SPConstant.LOCALHOST_CITY, str);
                        RxBus.getInstance().post(new MyLocation(str, String.valueOf(lgt), String.valueOf(lat)));
                    }
                });
            }

            @Override
            public void onDenied(List<String> data) {

                Logger.e(data.toString());
            }

            @Override
            public String onAlwaysDeniedData() {
                return "我们需要获得位置权限，是否前往设置？";
            }
        }, Permission.Group.LOCATION);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_SETTING: {
                Toast.makeText(MainActivity.this, "申请权限", Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }


    @Override
    protected void initView() {

        acInit();


    }


    @SuppressLint("WrongConstant")
    private void acInit() {

        homeFragment = new HomeFragment();
        miaopuFragment = new MiaoPuFragment();
        firendFragment = new FirendFragment();
        mineFragment = new MineFragment();
        fragments = new Fragment[]{homeFragment, miaopuFragment, firendFragment, mineFragment};
        mainFrame = (FrameLayout) findViewById(R.id.mainFrame);
        //设置fragment到布局
        getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, homeFragment).show(homeFragment).commit();


        //为了设置文字和图标都显示
        bottomNavigationView.setLabelVisibilityMode(1);
        //这里是bottomnavigationview的点击事件
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        //这里因为需要对3个fragment进行切换
                        //start
                        if (lastfragment != 0) {
                            switchFragment(lastfragment, 0);
                            lastfragment = 0;
                        }
                        //end
                        //如果只是想测试按钮点击，不管fragment的切换，可以把start到end里面的内容去掉
                        return true;
                    case R.id.navigation_miaopu:
                        if (StringUtils.isEmpty(SPStaticUtils.getString(SPConstant.USER_ID))) {
                            IntentUtils.startIntent(MainActivity.this, LoginCodeActivity.class);
                            return false;

                        } else {
                            if (lastfragment != 1) {
                                switchFragment(lastfragment, 1);
                                lastfragment = 1;
                            }
                            return true;
                        }


                    case R.id.navigation_firends:
                        if (StringUtils.isEmpty(SPStaticUtils.getString(SPConstant.USER_ID))) {
                            IntentUtils.startIntent(MainActivity.this, LoginCodeActivity.class);
                            return false;
                        } else {
                            if (lastfragment != 2) {
                                switchFragment(lastfragment, 2);
                                lastfragment = 2;
                            }
                            return true;
                        }

                    case R.id.navigation_mine:
                        if (lastfragment != 3) {
                            switchFragment(lastfragment, 3);
                            lastfragment = 3;
                        }
                        return true;
                    default:
                        break;
                }
                return false;
            }
        });

//        RxBus.getInstance().toObservableSticky(this, HomeUpdateChange.class).subscribe(new Consumer<HomeUpdateChange>() {
//            @Override
//            public void accept(HomeUpdateChange homeUpdateChange) throws Exception {
//                    bottomNavigationView.setSelectedItemId(R.id.navigation_home);
//            }
//        });
    }


    /**
     * 切换fragment
     */
    private void switchFragment(int lastfragment, int index) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //隐藏上个Fragment
        transaction.hide(fragments[lastfragment]);
        if (fragments[index].isAdded() == false) {
            transaction.add(R.id.mainFrame, fragments[index]);
        }
        transaction.show(fragments[index]).commitAllowingStateLoss();
    }


    /**
     * 半透明状态栏
     */
    protected void setHalfTransparent() {
        if (Build.VERSION.SDK_INT >= 21) {//21表示5.0
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else if (Build.VERSION.SDK_INT >= 19) {//19表示4.4
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //虚拟键盘也透明
            // getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }


}