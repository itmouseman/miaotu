package com.widget.miaotu.master.home.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.widget.miaotu.R;
import com.widget.miaotu.base.MBaseActivity;
import com.widget.miaotu.base.MVCControl;
import com.widget.miaotu.common.utils.AddresSelectUtils;
import com.widget.miaotu.common.utils.ShapeTextView;
import com.widget.miaotu.http.BaseObserver;
import com.widget.miaotu.http.bean.BaseEntity;
import com.widget.miaotu.http.bean.HeadPublishWantBuyBean;
import com.widget.miaotu.http.bean.SeedChooseInfoBean;
import com.widget.miaotu.http.bean.SeedlingInfo;
import com.widget.miaotu.http.bean.SendDemandSeedBean;
import com.widget.miaotu.http.bean.SpecAllJavaBean;
import com.widget.miaotu.http.retrofit.RetrofitFactory;
import com.widget.miaotu.http.retrofit.TransformerUi;
import com.widget.miaotu.master.home.adapter.GuigeAddMiaomuAdapter;
import com.widget.miaotu.master.home.adapter.TreePlantTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 发布求购页面
 */
public class HomeFaBuQiuGouActivity extends MBaseActivity {

    @BindView(R.id.tv_fa_bu_YongMiaoDi)
    TextView tvYongMiaoDi;
    @BindView(R.id.tv_fa_bu_Select_MiaoMu)
    TextView tvSelectMiaoMu;
    @BindView(R.id.recyclerView_fabu_miaomu_type)
    RecyclerView recyclerViewMiaoMuType;

    @BindView(R.id.iv_fa_bu_chandiXuanZhe)
    TextView ivFaBuChandiXuanZhe;
    @BindView(R.id.recyclerView_guige_fabu)
    RecyclerView recyclerViewGuigeFabu;
    @BindView(R.id.et_qiugou_num)
    EditText et_qiugou_num;
    @BindView(R.id.et_fabuqiugou_userName)
    EditText et_fabuqiugou_userName;
    @BindView(R.id.et_fabuqiugou_userPhone)
    EditText et_fabuqiugou_userPhone;


    private SeedlingInfo sData;
    String sid;//苗木分类id
    private List<SendDemandSeedBean.ClassifyFirstsBean> classifyFirsts = new ArrayList<>();
    private List<SendDemandSeedBean.ClassifyFirstsBean.PlantTypeListsBean> plantTypeLists = new ArrayList<>();
    private List<SendDemandSeedBean.ClassifyFirstsBean.ClassifySecondsBean.SpecListsBean> specTypeLists = new ArrayList<>();//本来要用RecyclerView
    private GridLayoutManager gridlayoutManager;
    private TreePlantTypeAdapter plantTypeAdapter;
    private GuigeAddMiaomuAdapter guigeAdapter;
    private String yonMiaoDiSheng;
    private String yonMiaoDiShi;
    private String chandiSheng;
    private String chandiSshi;
    private String selectMiaoMuType;
    private ArrayList<SpecAllJavaBean> specAllJavaBeanList = new ArrayList<SpecAllJavaBean>();
    private ArrayList<BaseViewHolder> mBaseViewHolderList = new ArrayList<BaseViewHolder>();


    @Override
    protected MVCControl createControl() {
        return null;
    }

    @Override
    protected void initView() {


    }

    @Override
    protected void initDetailData() {

        //种植类型设置
        gridlayoutManager = new GridLayoutManager(this, 12, GridLayoutManager.VERTICAL, false);
        recyclerViewMiaoMuType.setLayoutManager(gridlayoutManager);
        plantTypeAdapter = new TreePlantTypeAdapter(this, R.layout.item_tree_type_list, plantTypeLists);
        recyclerViewMiaoMuType.setAdapter(plantTypeAdapter);
        //种植类型点击事件
        plantTypeAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {

                for (int i = 0; i < plantTypeLists.size(); i++) {
                    plantTypeLists.get(i).setIsChose("0");
                }
                Logger.e("选择了 " + plantTypeLists.get(position).getName());
                selectMiaoMuType = plantTypeLists.get(position).getName();
                plantTypeLists.get(position).setIsChose("1");
                plantTypeAdapter.notifyDataSetChanged();
            }
        });

        //规格设置
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerViewGuigeFabu.setLayoutManager(linearLayoutManager);
        guigeAdapter = new GuigeAddMiaomuAdapter(this, R.layout.item_guige_add_miaomu, specTypeLists, new GuigeAddMiaomuAdapter.ViewAndPositionCallBack() {
            @Override
            public void viewBack(BaseViewHolder baseViewHolder, SendDemandSeedBean.ClassifyFirstsBean.ClassifySecondsBean.SpecListsBean mClassifyFirstsBean, int position) {
                SpecAllJavaBean specAllJavaBean = new SpecAllJavaBean();
                specAllJavaBean.setSpecName(specTypeLists.get(position).getName());
                specAllJavaBean.setUnit(specTypeLists.get(position).getUnit());

                specAllJavaBeanList.add(specAllJavaBean);
                mBaseViewHolderList.add(baseViewHolder);

            }
        });
        recyclerViewGuigeFabu.setAdapter(guigeAdapter);
    }

    @OnClick({R.id.ll_fa_bu_YongMiaoDi, R.id.ll_fa_bu_Select_MiaoMu, R.id.ll_fa_bu_chandiXuanZhe, R.id.stv_fabuqiugou})
    public void onClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_fa_bu_YongMiaoDi://用苗地选择
                AddresSelectUtils.selectPICKview(getWindow().getDecorView().findViewById(android.R.id.content), this, new AddresSelectUtils.SelectAddressCallBack() {
                    @Override
                    public void selectAddressBack(String province, String city, String address) {
                        yonMiaoDiSheng = province;
                        yonMiaoDiShi = city;
                        tvYongMiaoDi.setText(address);
                    }
                });
                break;
            case R.id.ll_fa_bu_Select_MiaoMu://选择苗木
                startActivityForResult(new Intent(HomeFaBuQiuGouActivity.this, SeedlingActivity.class), 3);
                break;
            case R.id.ll_fa_bu_chandiXuanZhe://产地选择
                AddresSelectUtils.selectPICKview(getWindow().getDecorView().findViewById(android.R.id.content), this, new AddresSelectUtils.SelectAddressCallBack() {
                    @Override
                    public void selectAddressBack(String province, String city, String address) {
                        chandiSheng = province;
                        chandiSshi = city;
                        ivFaBuChandiXuanZhe.setText(address);
                    }
                });
                break;
            case R.id.stv_fabuqiugou://发布
                fabuQiuGouMiaoMU();
                break;
        }
    }

    /**
     * 发布求购苗木
     */
    private void fabuQiuGouMiaoMU() {
        if (TextUtils.isEmpty(et_qiugou_num.getText().toString().trim())) {
            ToastUtils.showShort("求购数量不能为空");
            return;
        }
        if (TextUtils.isEmpty(tvYongMiaoDi.getText().toString().trim())) {
            ToastUtils.showShort("请选择用苗地");
            return;
        }

        if (TextUtils.isEmpty(et_fabuqiugou_userName.getText().toString().trim())) {
            ToastUtils.showShort("请输入用户名");
            return;
        }
        if (TextUtils.isEmpty(et_fabuqiugou_userPhone.getText().toString().trim())) {
            ToastUtils.showShort("请输入联系电话");
            return;
        }

        if (TextUtils.isEmpty(ivFaBuChandiXuanZhe.getText().toString().trim())) {
            ToastUtils.showShort("请选择产地");
            return;
        }
        showWaiteDialog("正在上传...");
        //[ { "specName" : "胸径", "unit" : "厘米", "interval" : "12-12" }, { "specName" : "高度", "unit" : "米", "interval" : "11-13" } ]

        for (int i = 0; i < specAllJavaBeanList.size(); i++) {
            specAllJavaBeanList.get(i).setInterval(((EditText) (mBaseViewHolderList.get(i).getView(R.id.et_guige_1))).getText().toString().trim() + "-" + ((EditText) (mBaseViewHolderList.get(i).getView(R.id.et_guige_2))).getText().toString().trim());
        }


        RetrofitFactory.getInstence().API().publishWantBuy(new HeadPublishWantBuyBean(et_qiugou_num.getText().toString().trim(), yonMiaoDiSheng, yonMiaoDiShi, selectMiaoMuType, specAllJavaBeanList.toString(), "", et_fabuqiugou_userName.getText().toString().trim(), et_fabuqiugou_userPhone.getText().toString().trim(), chandiSheng, chandiSshi))
                .compose(TransformerUi.setThread()).subscribe(new BaseObserver<Object>(HomeFaBuQiuGouActivity.this) {
            @Override
            protected void onSuccess(BaseEntity<Object> t) throws Exception {
                hideWaiteDialog();
                Logger.e(t.toString());
                if (t.getStatus() == 100) {
                    ToastUtils.showShort("上传成功");
                     finish();

                } else {
                    ToastUtils.showShort(t.getMessage());
                }


            }

            @Override
            protected void onFail(Throwable throwable) throws Exception {
                Logger.e(throwable.getMessage().toString());
                hideWaiteDialog();
                ToastUtils.showShort("上传失败");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3 && resultCode == 4) {
            assert data != null;
            sData = new Gson().fromJson(data.getStringExtra("info"), SeedlingInfo.class);


            tvSelectMiaoMu.setText(sData.getBaseName());


            //请求苗木子信息
            sid = sData.getId() + "";//SendDemandSeedBean 解析多级list

            getTypeSon();

        }
    }

    private void getTypeSon() {

        showWaiteDialog("正在加载...");
        RetrofitFactory.getInstence().API().seedlingChooseInfo(new SeedChooseInfoBean(sid)).compose(TransformerUi.setThread())
                .subscribe(new BaseObserver<SendDemandSeedBean>(this) {
                    @Override
                    protected void onSuccess(BaseEntity<SendDemandSeedBean> t) throws Exception {
                        hideWaiteDialog();
                        if (t.getStatus() == 100) {
                            Logger.e(t.getData().toString());
                            //各个子页面刷新
                            classifyFirsts.clear();
                            classifyFirsts.addAll(t.getData().getClassifyFirsts());
                            if (classifyFirsts.size() > 0) {
                                specAllJavaBeanList.clear();
                                mBaseViewHolderList.clear();
                                //刷新类型
                                reFreshTypeList(0, 0);
                                //刷新规格
                                refalshSecondSon(0, 0);
                            }

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

    private void reFreshTypeList(int position, int planTypePosition) {
        plantTypeLists.clear();


        if (classifyFirsts.size() > 0 && classifyFirsts.get(position).getPlantTypeLists().size() > 0) {
            plantTypeLists.addAll(classifyFirsts.get(position).getPlantTypeLists());
            selectMiaoMuType = classifyFirsts.get(position).getPlantTypeLists().get(planTypePosition).getName();

            //根据数量显示占位
            showTextWidth(gridlayoutManager, classifyFirsts.get(position).getPlantTypeLists().size());

            for (int i = 0; i < plantTypeLists.size(); i++) {
                plantTypeLists.get(i).setIsChose("0");
            }
            plantTypeLists.get(0).setIsChose("1");
        }
        plantTypeAdapter.notifyDataSetChanged();
    }


    //规格 第一个位置的第一个组合
    private void refalshSecondSon(int firstPosition, int secondPosition) {
        specTypeLists.clear();
        if (classifyFirsts.size() > 0 && 1 != classifyFirsts.get(firstPosition).getHasSecondClassify()) {
            if (classifyFirsts.size() > 0) {

                specTypeLists.addAll(classifyFirsts.get(firstPosition).getSpecLists());
            }
        } else {
            if (classifyFirsts.size() > 0)
                if (classifyFirsts.get(firstPosition).getClassifySeconds().size() > 0) {
                    specTypeLists.clear();
                    specTypeLists.addAll(classifyFirsts.get(firstPosition).getClassifySeconds().get(secondPosition).getSpecLists()); //获取类别信息

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


    @Override
    protected int getLayoutId() {
        return R.layout.activity_fa_bu_qiu_gou;
    }

    /**
     * {seedlingId=10, name='阿根廷嘉宝果', commonNames='',
     * classifyFirsts=[
     * ClassifyFirstsBean{
     *      firstClassifyId=4, seedlingId=10, hasSecondClassify=0, name='灌木',
     *      classifySeconds=[],
     *      plantTypeLists=[
     *                   PlantTypeListsBean{plantTypeId=4, name='容器苗', isChose='null'},
     *                   PlantTypeListsBean{plantTypeId=5, name='移栽苗', isChose='null'},
     *                   PlantTypeListsBean{plantTypeId=1, name='地栽苗', isChose='null'},
     *                    PlantTypeListsBean{plantTypeId=2, name='盆苗', isChose='null'}
     *                    ],
     *       specLists=[
     *                  SpecListsBean{specId=1, name='高度', mustWrite=1, unit='厘米'}, S
     *                  pecListsBean{specId=2, name='冠幅', mustWrite=1, unit='厘米'},
     *                  SpecListsBean{specId=11, name='地径', mustWrite=0, unit='厘米'},
     *                  SpecListsBean{specId=3, name='分枝数', mustWrite=0, unit='支'},
     *                  SpecListsBean{specId=10, name='土球尺寸', mustWrite=0, unit='厘米'}
     *                 ],
     * isChose='null'
     * },
     * ClassifyFirstsBean{
     *      firstClassifyId=1, seedlingId=10, hasSecondClassify=0, name='地被',
     *      classifySeconds=[],
     *     plantTypeLists=[
     * PlantTypeListsBean{plantTypeId=1, name='地栽苗', isChose='null'},
     * PlantTypeListsBean{plantTypeId=2, name='盆苗', isChose='null'},
     * PlantTypeListsBean{plantTypeId=3, name='袋苗', isChose='null'}
     * ],
     * specLists=[
     * SpecListsBean{specId=1, name='高度', mustWrite=1, unit='厘米'},
     * SpecListsBean{specId=2, name='冠幅', mustWrite=1, unit='厘米'},
     * SpecListsBean{specId=3, name='分枝数', mustWrite=0, unit='支'},
     * SpecListsBean{specId=4, name='袋重', mustWrite=0, unit='斤'},
     * SpecListsBean{specId=5, name='袋/盆尺寸', mustWrite=0, unit='厘米'}
     * ], isChose='null'
     * },
     * ClassifyFirstsBean{firstClassifyId=2, seedlingId=10, hasSecondClassify=1, name='乔木',
     * classifySeconds=[
     * ClassifySecondsBean{secondClassifyId=9540, seedlingId=10, name='单杆', specLists=[SpecListsBean{specId=6, name='胸径', mustWrite=1, unit='厘米'}, SpecListsBean{specId=7, name='高度', mustWrite=1, unit='米'}, SpecListsBean{specId=8, name='冠幅', mustWrite=0, unit='米'}, SpecListsBean{specId=9, name='分支点', mustWrite=0, unit='米'}, SpecListsBean{specId=10, name='土球尺寸', mustWrite=0, unit='厘米'}], isChose='null'}, ClassifySecondsBean{secondClassifyId=9541, seedlingId=10, name='丛生', specLists=[SpecListsBean{specId=11, name='地径', mustWrite=1, unit='厘米'}, SpecListsBean{specId=7, name='高度', mustWrite=1, unit='米'}, SpecListsBean{specId=8, name='冠幅', mustWrite=0, unit='米'}, SpecListsBean{specId=3, name='分枝数', mustWrite=0, unit='支'}, SpecListsBean{specId=10, name='土球尺寸', mustWrite=0, unit='厘米'}], isChose='null'}], plantTypeLists=[PlantTypeListsBean{plantTypeId=4, name='容器苗', isChose='null'}, PlantTypeListsBean{plantTypeId=5, name='移栽苗', isChose='null'}, PlantTypeListsBean{plantTypeId=1, name='地栽苗', isChose='null'}], specLists=[], isChose='null'}]}
     */
}
