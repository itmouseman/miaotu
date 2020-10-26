package com.widget.miaotu.master.home.other;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.contrarywind.view.WheelView;
import com.jaygoo.widget.OnRangeChangedListener;
import com.jaygoo.widget.RangeSeekBar;
import com.lxj.xpopup.core.DrawerPopupView;
import com.orhanobut.logger.Logger;
import com.widget.miaotu.R;
import com.widget.miaotu.common.utils.AddresSelectUtils;
import com.widget.miaotu.common.utils.FileUtil;
import com.widget.miaotu.common.utils.home.EditViewWithDel;
import com.widget.miaotu.http.bean.ProvinceBean;
import com.widget.miaotu.master.mine.activity.CompanyInfoActivity;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Description: 自定义抽屉弹窗
 * Create by dance, at 2018/12/20
 */
public class ShaixuanPopupView extends DrawerPopupView {

    private final Context mContext;
    private final DataChangeCallBack mDataChangeCallBack;
    private RangeSeekBar sbRange_1;
    private RangeSeekBar sbRange_2;
    private RangeSeekBar sbRange_3;
    private TextView tv_seekBar1_start;
    private TextView tv_seekBar1_end;
    private TextView tv_seekBar2_start;
    private TextView tv_seekBar2_end;
    private TextView tv_seekBar3_start;
    private TextView tv_seekBar3_end;


    private String city = "";
    private String province = "";
    private TextView textViewCity;


    private EditViewWithDel edViewShaixuanPop;
    private TextView tvRightDq1;
    private TagFlowLayout mFlowBiaoQian;

    private String[] mVals1 = new String[]
            {"不限", "清货", "精品"};
    private String[] mVals2 = new String[]
            {"不限", "容器苗", "地栽苗", "移栽苗"};
    private String[] mVals3 = new String[]
            {"不限", "认证企业"};
    private TagFlowLayout mFlowLeiXing;
    private TagFlowLayout mFlowQiyerenzheng;
    private TagAdapter<String> mFlowAdaapter1;
    private TagAdapter<String> mFlowAdaapter2;
    private TagAdapter<String> mFlowAdaapter3;
    private String mSelectTextBiaoQian = "不限";
    private String mSelectTextLeixing = "不限";
    private String mSelectTextQiye = "不限";
    private String mEditChangeText;
    private String seekBarGjStart;
    private String seekBarGjEnd;
    private String seekBarGdStart;
    private String seekBarGdEnd;
    private String seekBarGfStart;
    private String seekBarGfEnd;


    private double diameterFloor;//	起始杆径
    private double diameterUpper;//截止杆径
    private double heightFloor;//起始高度
    private double heightUpper;//截止高度
    private double crownFloor;//起始冠幅
    private double crownUpper;//截止冠幅
    private int type;
    private int companyStatus;


    public ShaixuanPopupView(@NonNull Context context, DataChangeCallBack dataChangeCallBack) {
        super(context);
        mContext = context;
        mDataChangeCallBack = dataChangeCallBack;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.shaixuan_popup;
    }


    @Override
    protected void onCreate() {
        super.onCreate();

        //地区选择
        locationSet();

        //滑动设置区间
        seekBarSet();


        //通过设置topMargin，可以让Drawer弹窗进行局部阴影展示
//        ViewGroup.MarginLayoutParams params = (MarginLayoutParams) getPopupContentView().getLayoutParams();
//        params.topMargin = 450;

        //确认和取消
        allClick();
    }

    private void allClick() {
        //标签选择
        biaoqianSelect();

        //类型选择
        leixingSelect();

        //企业认证
        qiyerenzhengSelect();


        //返回
        findViewById(R.id.iv_shaixuanBack).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onDismiss();
            }
        });

        //填写的筛选内容
        edViewShaixuanPop = (EditViewWithDel) findViewById(R.id.edView_shaixuan_pop);
        edViewShaixuanPop.setText(mEditChangeText);

        //取消
        findViewById(R.id.tv_Cance_Sure).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        //确定
        findViewById(R.id.tv_ShaiXuan_Sure).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                //填写的筛选文本
                String searchWord = edViewShaixuanPop.getText().toString();


                //杆径 --文本
                TextView et_gj_start = findViewById(R.id.et_gj_start);
                TextView et_gj_end = findViewById(R.id.et_gj_end);
                if (!TextUtils.isEmpty(et_gj_start.getText().toString().trim())) {
                    diameterFloor = Double.parseDouble(et_gj_start.getText().toString().trim());
                }
                if (!TextUtils.isEmpty(et_gj_end.getText().toString().trim())) {
                    diameterUpper = Double.parseDouble(et_gj_end.getText().toString().trim());
                }


                //高度  --文本
                TextView et_gd_start = findViewById(R.id.et_gd_start);
                TextView et_gd_end = findViewById(R.id.et_gd_end);
                if (!TextUtils.isEmpty(et_gd_start.getText().toString().trim())) {
                    heightFloor = Double.parseDouble(et_gd_start.getText().toString().trim());
                }
                if (!TextUtils.isEmpty(et_gd_end.getText().toString().trim())) {
                    heightUpper = Double.parseDouble(et_gd_end.getText().toString().trim());
                }


                //冠幅  --文本
                TextView et_gf_start = findViewById(R.id.et_gf_start);
                TextView et_gf_end = findViewById(R.id.et_gf_end);

                if (!TextUtils.isEmpty(et_gf_start.getText().toString().trim())) {
                    crownFloor = Double.parseDouble(et_gf_start.getText().toString().trim());
                }
                if (!TextUtils.isEmpty(et_gf_end.getText().toString())) {
                    crownUpper = Double.parseDouble(et_gf_end.getText().toString().trim());
                }

                //杆径 --滚轮
                if (!TextUtils.isEmpty(seekBarGjStart)) {
                    diameterFloor = Double.parseDouble(seekBarGjStart);
                }
                if (!TextUtils.isEmpty(seekBarGjEnd)) {

                    diameterUpper = Double.parseDouble(seekBarGjEnd);
                }

                //高度  --滚轮
                if (!TextUtils.isEmpty(seekBarGdStart)) {

                    heightFloor = Double.parseDouble(seekBarGdStart);
                }
                if (!TextUtils.isEmpty(seekBarGdEnd)) {

                    heightUpper = Double.parseDouble(seekBarGdEnd);
                }

                //冠幅  --滚轮
                if (!TextUtils.isEmpty(seekBarGfStart)) {

                    crownFloor = Double.parseDouble(seekBarGfStart);
                }
                if (!TextUtils.isEmpty(seekBarGfEnd)) {
                    crownUpper = Double.parseDouble(seekBarGfEnd);
                }
                if (mSelectTextBiaoQian.equals("清货")) {
                    type = 0;
                } else if (mSelectTextBiaoQian.equals("精品")) {
                    type = 1;
                }

                if (mSelectTextQiye.equals("不限")) {
                    companyStatus = 0;
                } else if (mSelectTextQiye.equals("已认证")) {
                    companyStatus = 1;
                }


                Logger.e("searchWord = " + searchWord + " diameterFloor = " + diameterFloor + "  diameterUpper=" + diameterUpper + "  heightFloor = " + heightFloor + "  heightUpper = " + heightUpper +
                        "  crownFloor = " + crownFloor + "  crownUpper = " + crownUpper + "  province= " + province + "  city = " + city + "  type=" + type + "  plantType=" + mSelectTextLeixing + "  companyStatus = " + companyStatus);

                if (mDataChangeCallBack != null) {
                    mDataChangeCallBack.selectChangeData(searchWord, String.valueOf(diameterFloor), String.valueOf(diameterUpper), String.valueOf(heightFloor) ,String.valueOf(heightUpper)  ,String.valueOf(crownFloor) ,String.valueOf(crownUpper) , province, city, type, mSelectTextLeixing, companyStatus);
                    dismiss();
                }

            }
        });
        //去滑动区间
        findViewById(R.id.tv_go_huadong_qujian).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.ll_guiGe_seekBar).setVisibility(VISIBLE);
                findViewById(R.id.ll_guiGe_wenBen).setVisibility(GONE);
                findViewById(R.id.tv_go_shoudong_qujian).setVisibility(VISIBLE);
                findViewById(R.id.tv_go_huadong_qujian).setVisibility(GONE);
            }
        });
        //去手动区间
        findViewById(R.id.tv_go_shoudong_qujian).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.ll_guiGe_seekBar).setVisibility(GONE);
                findViewById(R.id.ll_guiGe_wenBen).setVisibility(VISIBLE);
                findViewById(R.id.tv_go_huadong_qujian).setVisibility(VISIBLE);
                findViewById(R.id.tv_go_shoudong_qujian).setVisibility(GONE);
            }
        });

        //选中地区文本
        tvRightDq1 = findViewById(R.id.tv_right_dq1);
    }


    private void biaoqianSelect() {

        final LayoutInflater mInflater = LayoutInflater.from(mContext);
        mFlowBiaoQian = (TagFlowLayout) findViewById(R.id.id_flow_biaoQian);
        mFlowBiaoQian.setMaxSelectCount(1);
        mFlowAdaapter1 = new TagAdapter<String>(mVals1) {

            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) mInflater.inflate(R.layout.tv,
                        mFlowBiaoQian, false);
                tv.setText(s);
                return tv;
            }
        };

        mFlowBiaoQian.setAdapter(mFlowAdaapter1);
        mFlowAdaapter1.setSelectedList(0);
        mFlowBiaoQian.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {

                return true;
            }
        });


        mFlowBiaoQian.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                if (!selectPosSet.isEmpty()) {

                    mSelectTextBiaoQian = mVals1[selectPosSet.iterator().next()];
                } else {
                    mFlowAdaapter1.setSelectedList(0);
                    mSelectTextBiaoQian = "不限";
                }
            }
        });
    }

    private void leixingSelect() {
        final LayoutInflater mInflater = LayoutInflater.from(mContext);
        mFlowLeiXing = (TagFlowLayout) findViewById(R.id.id_flow_leiXing);
        mFlowLeiXing.setMaxSelectCount(1);
        mFlowAdaapter2 = new TagAdapter<String>(mVals2) {

            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) mInflater.inflate(R.layout.tv,
                        mFlowLeiXing, false);
                tv.setText(s);
                return tv;
            }
        };
        mFlowLeiXing.setAdapter(mFlowAdaapter2);
        mFlowAdaapter2.setSelectedList(0);
        mFlowLeiXing.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {

                return true;
            }
        });


        mFlowLeiXing.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                if (!selectPosSet.isEmpty()) {

                    mSelectTextLeixing = mVals2[selectPosSet.iterator().next()];
                } else {
                    mFlowAdaapter2.setSelectedList(0);
                    mSelectTextLeixing = "不限";
                }

            }
        });

    }

    private void qiyerenzhengSelect() {

        final LayoutInflater mInflater = LayoutInflater.from(mContext);
        mFlowQiyerenzheng = (TagFlowLayout) findViewById(R.id.id_flow_QiyeRenzheng);
        mFlowQiyerenzheng.setMaxSelectCount(1);
        mFlowAdaapter3 = new TagAdapter<String>(mVals3) {

            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) mInflater.inflate(R.layout.tv,
                        mFlowQiyerenzheng, false);
                tv.setText(s);
                return tv;
            }
        };
        mFlowQiyerenzheng.setAdapter(mFlowAdaapter3);

        mFlowAdaapter3.setSelectedList(0);

        mFlowQiyerenzheng.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {

                return true;
            }
        });


        mFlowQiyerenzheng.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                if (!selectPosSet.isEmpty()) {
                    mSelectTextQiye = mVals3[selectPosSet.iterator().next()];
                } else {
                    mFlowAdaapter3.setSelectedList(0);
                    mSelectTextQiye = "不限";
                }
            }
        });
    }

    /**
     * 地区选择
     */
    private void locationSet() {
        textViewCity = findViewById(R.id.tv_right_dq1);
        textViewCity.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                selectPICKview();
            }
        });
        findViewById(R.id.tv_right_dq).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPICKview();
            }
        });
    }

    private void selectPICKview() {

        AddresSelectUtils.selectPICKview((ViewGroup) dialog.getWindow().getDecorView(), getContext(), new AddresSelectUtils.SelectAddressCallBack() {
            @Override
            public void selectAddressBack(String mprovince, String mcity, String maddress) {
                province = mprovince;
                city = mcity;
                textViewCity.setText(maddress);
            }
        });


    }


    /**
     * 滑动设置区间
     */
    private void seekBarSet() {
        sbRange_1 = (RangeSeekBar) findViewById(R.id.sb_range_1);
        tv_seekBar1_start = findViewById(R.id.tv_seekBar1_start);
        tv_seekBar1_end = findViewById(R.id.tv_seekBar1_end);
        sbRange_1.setProgress(0, 50);
        sbRange_1.setOnRangeChangedListener(new OnRangeChangedListener() {
            @Override
            public void onRangeChanged(RangeSeekBar view, float leftValue, float rightValue, boolean isFromUser) {

                tv_seekBar1_start.setText(String.valueOf((int) leftValue));
                tv_seekBar1_end.setText(String.valueOf((int) rightValue));

                seekBarGjStart = String.valueOf((int) leftValue);
                seekBarGjEnd = String.valueOf((int) rightValue);
            }

            @Override
            public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }

            @Override
            public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }
        });


        sbRange_2 = (RangeSeekBar) findViewById(R.id.sb_range_2);
        tv_seekBar2_start = findViewById(R.id.tv_seekBar2_start);
        tv_seekBar2_end = findViewById(R.id.tv_seekBar2_end);
        sbRange_2.setProgress(0, 2000);
        sbRange_2.setOnRangeChangedListener(new OnRangeChangedListener() {
            @Override
            public void onRangeChanged(RangeSeekBar view, float leftValue, float rightValue, boolean isFromUser) {

                tv_seekBar2_start.setText(String.valueOf((int) leftValue));
                tv_seekBar2_end.setText(String.valueOf((int) rightValue));

                seekBarGdStart = String.valueOf((int) leftValue);
                seekBarGdEnd = String.valueOf((int) rightValue);
            }

            @Override
            public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }

            @Override
            public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }
        });


        sbRange_3 = (RangeSeekBar) findViewById(R.id.sb_range_3);
        tv_seekBar3_start = findViewById(R.id.tv_seekBar3_start);
        tv_seekBar3_end = findViewById(R.id.tv_seekBar3_end);
        sbRange_3.setProgress(0, 5000);
        sbRange_3.setOnRangeChangedListener(new OnRangeChangedListener() {
            @Override
            public void onRangeChanged(RangeSeekBar view, float leftValue, float rightValue, boolean isFromUser) {
                tv_seekBar3_start.setText(String.valueOf((int) leftValue));
                tv_seekBar3_end.setText(String.valueOf((int) rightValue));

                seekBarGfStart = String.valueOf((int) leftValue);
                seekBarGfEnd = String.valueOf((int) rightValue);

            }

            @Override
            public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }

            @Override
            public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }
        });
    }

    @Override
    protected void onShow() {
        super.onShow();
    }

    @Override
    protected void onDismiss() {
        super.onDismiss();
    }


    public void setDataChange(String mEditChangeText) {
        this.mEditChangeText = mEditChangeText;
    }


    public interface DataChangeCallBack {

        void selectChangeData(String searchWord, String diameterFloor, String diameterUpper, String heightFloor, String heightUpper, String crownFloor,
                              String crownUpper, String province, String city, int type, String plantType, int companyStatus);
    }
}