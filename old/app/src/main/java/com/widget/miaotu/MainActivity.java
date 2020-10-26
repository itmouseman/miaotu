package com.widget.miaotu;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.amap.api.location.AMapLocation;
import com.blankj.utilcode.util.SPStaticUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.widget.miaotu.base.BaseActivity;
import com.widget.miaotu.common.constant.SPConstant;
import com.widget.miaotu.common.utils.AndPermissionUtils;
import com.widget.miaotu.common.utils.dialog.UploadAPPDialog;
import com.widget.miaotu.common.utils.other.DownloadService;
import com.widget.miaotu.common.utils.other.LocationUtil;
import com.widget.miaotu.common.utils.rxbus.HomeUpdateChange;
import com.widget.miaotu.common.utils.rxbus.MyLocation;
import com.widget.miaotu.common.utils.rxbus.RxBus;
import com.widget.miaotu.http.BaseObserver;
import com.widget.miaotu.http.bean.BaseEntity;
import com.widget.miaotu.http.bean.SeedlingInfo;
import com.widget.miaotu.http.bean.UploadAPPBean;
import com.widget.miaotu.http.retrofit.RetrofitFactory;
import com.widget.miaotu.http.retrofit.TransformerUi;
import com.widget.miaotu.master.home.fragment.HomeFragment;
import com.widget.miaotu.master.message.FirendFragment;
import com.widget.miaotu.master.miaopu.MiaoPuFragment;
import com.widget.miaotu.master.mine.MineFragment;
import com.yanzhenjie.permission.AndPermission;
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

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            downloadBinder = (DownloadService.DownloadBinder) service;
            downloadBinder.getService().setOnTransProgressChangeListener(new DownloadService.OnTransProgressChangeListener() {
                @Override
                public void onProgressChange(long current, long max) {
                    if (dialog != null) {
                        dialog.getDownload().setProgress((int) current);
                    }

                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };
    private DownloadService.DownloadBinder downloadBinder;
    private UploadAPPDialog dialog;

    @Override
    protected void initData() {

        //升级检测
        updateCheck();


        //高德地图定位
        locationInit();


        if (BaseApplication.getmDaoSession().getSeedlingInfoDao().loadAll().size() == 0) {
            BaseApplication.getmDaoSession().getSeedlingInfoDao().insertInTx(getInfo(this));
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
        AndPermissionUtils andPermissionUtils = new AndPermissionUtils();
        andPermissionUtils.requestPermission(this, new AndPermissionUtils.PermissionsCallBack() {
            @Override
            public void permissionsSuc() {
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
            public void permissionFail(@NonNull List<String> permissions) {
                if (AndPermission.hasAlwaysDeniedPermission(MainActivity.this, permissions)) {
                    showSettingDialog(MainActivity.this, permissions);
                }
            }
        }, Permission.Group.LOCATION);
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
        AndPermission.with(MainActivity.this).runtime().setting().start(REQUEST_CODE_SETTING);
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

    private void updateCheck() {


        RetrofitFactory.getInstence().API().uploadAPP().compose(TransformerUi.setThread()).subscribe(new BaseObserver<UploadAPPBean>(this) {
            @Override
            protected void onSuccess(BaseEntity<UploadAPPBean> t) throws Exception {

                Logger.e(t.toString());
                UploadAPPBean uploadAPPBean = t.getData();
                if (uploadAPPBean.isNeedUpdate()) {
                    Intent intent = new Intent(MainActivity.this, DownloadService.class);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        startForegroundService(intent);
                    } else {
                        startService(intent);
                    }
                    //绑定服务
                    bindService(intent, connection, BIND_AUTO_CREATE);

                    dialog = new UploadAPPDialog(MainActivity.this, R.style.dialog_center);
                    dialog.getContentView().setText(Html.fromHtml(uploadAPPBean.getUpdateDesc()));
                    dialog.setForceUpdate(uploadAPPBean.isForceUpdate());
                    dialog.getTvDownload().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (downloadBinder == null) {
                                return;
                            }
                            //开启服务
                            downloadBinder.startDownload(uploadAPPBean.getUpdateUrl());
                            dialog.setLoading(true);
                        }
                    });
                    dialog.show();
                }
            }

            @Override
            protected void onFail(Throwable throwable) throws Exception {
                Logger.e(throwable.getMessage());
            }
        });


    }

    @Override
    protected void initView() {
        //使activity全屏
//        StatusBarUtil.fullScreen(this);
//        changeStatusWhite(true,R.color.colorAccent);
        QMUIStatusBarHelper.translucent(this);
//        setHalfTransparent();
        acInit();

        showRedPoint();


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
                        if (lastfragment != 1) {
                            switchFragment(lastfragment, 1);
                            lastfragment = 1;
                        }
                        return true;
                    case R.id.navigation_firends:
                        if (lastfragment != 2) {
                            switchFragment(lastfragment, 2);
                            lastfragment = 2;
                        }
                        return true;
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

        RxBus.getInstance().toObservableSticky(this, HomeUpdateChange.class).subscribe(new Consumer<HomeUpdateChange>() {
            @Override
            public void accept(HomeUpdateChange homeUpdateChange) throws Exception {
                    bottomNavigationView.setSelectedItemId(R.id.navigation_home);
            }
        });
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

    private void showRedPoint() {


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