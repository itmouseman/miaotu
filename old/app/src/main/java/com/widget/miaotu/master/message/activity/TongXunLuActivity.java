package com.widget.miaotu.master.message.activity;

import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.orhanobut.logger.Logger;
import com.widget.miaotu.BaseApplication;
import com.widget.miaotu.R;
import com.widget.miaotu.base.BaseActivity;
import com.widget.miaotu.common.utils.GlideUtils;
import com.widget.miaotu.common.utils.IntentUtils;
import com.widget.miaotu.common.utils.other.LetterBar;
import com.widget.miaotu.http.BaseObserver;
import com.widget.miaotu.http.bean.BaseEntity;
import com.widget.miaotu.http.bean.SeedlingInfo;
import com.widget.miaotu.http.bean.TongXunLuBean;
import com.widget.miaotu.http.retrofit.RetrofitFactory;
import com.widget.miaotu.http.retrofit.TransformerUi;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 通讯录界面
 */
public class TongXunLuActivity extends BaseActivity {

    /**
     * data={
     * M=[{phone=13346182520, looked=1.0, name=马后-景博斯, nickname=管理员小虎, isFriend=0.0, id=1016.0, avatar=http://oss-cn-beijing.aliyuncs.com/miaotu1/ess-1839150790435884423.jpeg, userId=88610.0}],
     * H=[{phone=13396513832, looked=1.0, name=黄钰婷, nickname=中苗会-苗苗, isFriend=0.0, id=1017.0, avatar=http://imgs.miaoto.net/header/header_201901070135246583.jpg?x-oss-process=style/thumb_288_288, userId=24325.0}],
     * P=[{phone=15111495518, looked=1.0, name=裴总, nickname=裴小军, isFriend=3.0, id=1018.0, avatar=http://oss-cn-beijing.aliyuncs.com/miaotu1/ess-6008274061839577987.jpeg, userId=2801.0}],
     * C=[{phone=13918365754, looked=1.0, name=催总, nickname=崔, isFriend=0.0, id=1019.0, avatar=http://imgs.miaoto.net/header/header_1558541793703234240.png?x-oss-process=style/thumb_288_288, userId=73385.0}]}
     * }
     */

    @BindView(R.id.letter_bar_tongxunlu)
    LetterBar letterBar;
    @BindView(R.id.recyclerView_tongxunlu)
    RecyclerView recyclerView;
    @BindView(R.id.ll_tongxunlu_yztx)
    LinearLayout ll_tongxunlu_yztx;
    @BindView(R.id.btn_back)
    ImageButton buttonBack;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.ll_add_miaoyou)
    LinearLayout ll_add_miaoyou;


    List<String> letters = new ArrayList<>();
    List<TongXunLuBean.TXLDetail> txlDetailList = new ArrayList<>();
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void initData() {
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(new CommonAdapter<TongXunLuBean.TXLDetail>(this, R.layout.item_tongxunlu_msg, txlDetailList) {
            @Override
            protected void convert(ViewHolder holder, TongXunLuBean.TXLDetail o, int position) {

                holder.setText(R.id.tv_tongxunl_name, o.getName());

                GlideUtils.loadUrl(TongXunLuActivity.this, o.getAvatar(), holder.getView(R.id.iv_msg_head));
            }

        });


        mailList();


    }

    @Override
    protected void initView() {
        ll_tongxunlu_yztx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentUtils.startIntent(TongXunLuActivity.this, YanZhengTixingMessageActivity.class);
            }
        });
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv_title.setText("通讯录");
        ll_add_miaoyou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//添加苗友
                IntentUtils.startIntent(TongXunLuActivity.this, AddMiaoYouActivity.class);
            }
        });

    }

    private int getItemPosition(List<TongXunLuBean.TXLDetail> txlDetailList, String zm) {
        for (int i = 0; i < txlDetailList.size(); i++) {
            if (zm.equals(txlDetailList.get(i).getmLetter())) {
                return i;
            }
        }
        return 0;
    }


    private void move(int position) {
        if (position != -1) {
            recyclerView.scrollToPosition(position);

            mLayoutManager.scrollToPositionWithOffset(position, 0);

        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_tongxunlu;
    }


    private void mailList() {
        RetrofitFactory.getInstence().API().getFriendsList().compose(TransformerUi.setThread()).subscribe(new BaseObserver<TongXunLuBean>(this) {
            @Override
            protected void onSuccess(BaseEntity<TongXunLuBean> t) throws Exception {

                if (t.getData().getA() != null) {
                    letters.add("A");
                    List<TongXunLuBean.TXLDetail> listA = t.getData().getA();
                    for (int i = 0; i < listA.size(); i++) {
                        listA.get(i).setmLetter("A");
                    }
                    txlDetailList.addAll(listA);
                }
                if (t.getData().getB() != null) {
                    letters.add("B");
                    List<TongXunLuBean.TXLDetail> list = t.getData().getB();
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setmLetter("B");
                    }
                    txlDetailList.addAll(list);

                }
                if (t.getData().getC() != null) {
                    letters.add("C");
                    List<TongXunLuBean.TXLDetail> list = t.getData().getC();
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setmLetter("C");
                    }
                    txlDetailList.addAll(list);
                }
                if (t.getData().getD() != null) {
                    letters.add("D");
                    List<TongXunLuBean.TXLDetail> list = t.getData().getD();
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setmLetter("D");
                    }
                    txlDetailList.addAll(list);
                }
                if (t.getData().getE() != null) {
                    letters.add("E");
                    List<TongXunLuBean.TXLDetail> list = t.getData().getE();
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setmLetter("E");
                    }
                    txlDetailList.addAll(list);
                }
                if (t.getData().getF() != null) {
                    letters.add("F");
                    List<TongXunLuBean.TXLDetail> list = t.getData().getF();
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setmLetter("F");
                    }
                    txlDetailList.addAll(list);
                }
                if (t.getData().getG() != null) {
                    letters.add("G");
                    List<TongXunLuBean.TXLDetail> list = t.getData().getG();
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setmLetter("G");
                    }
                    txlDetailList.addAll(list);
                }
                if (t.getData().getH() != null) {
                    letters.add("H");
                    List<TongXunLuBean.TXLDetail> list = t.getData().getH();
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setmLetter("H");
                    }
                    txlDetailList.addAll(list);
                }
                if (t.getData().getI() != null) {
                    letters.add("I");
                    List<TongXunLuBean.TXLDetail> list = t.getData().getI();
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setmLetter("I");
                    }
                    txlDetailList.addAll(list);
                }
                if (t.getData().getJ() != null) {
                    letters.add("J");
                    List<TongXunLuBean.TXLDetail> list = t.getData().getJ();
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setmLetter("J");
                    }
                    txlDetailList.addAll(list);
                }
                if (t.getData().getK() != null) {
                    letters.add("K");
                    List<TongXunLuBean.TXLDetail> list = t.getData().getK();
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setmLetter("K");
                    }
                    txlDetailList.addAll(list);
                }
                if (t.getData().getL() != null) {
                    letters.add("L");
                    List<TongXunLuBean.TXLDetail> list = t.getData().getL();
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setmLetter("L");
                    }
                    txlDetailList.addAll(list);
                }
                if (t.getData().getM() != null) {
                    letters.add("M");
                    List<TongXunLuBean.TXLDetail> list = t.getData().getM();
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setmLetter("M");
                    }
                    txlDetailList.addAll(list);
                }
                if (t.getData().getO() != null) {
                    letters.add("O");
                    List<TongXunLuBean.TXLDetail> list = t.getData().getO();
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setmLetter("O");
                    }
                    txlDetailList.addAll(list);
                }
                if (t.getData().getP() != null) {
                    letters.add("P");
                    List<TongXunLuBean.TXLDetail> list = t.getData().getP();
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setmLetter("P");
                    }
                    txlDetailList.addAll(list);
                }
                if (t.getData().getQ() != null) {
                    letters.add("Q");
                    List<TongXunLuBean.TXLDetail> list = t.getData().getQ();
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setmLetter("Q");
                    }
                    txlDetailList.addAll(list);
                }
                if (t.getData().getR() != null) {
                    letters.add("R");
                    List<TongXunLuBean.TXLDetail> list = t.getData().getR();
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setmLetter("R");
                    }
                    txlDetailList.addAll(list);
                }
                if (t.getData().getS() != null) {
                    letters.add("S");
                    List<TongXunLuBean.TXLDetail> list = t.getData().getS();
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setmLetter("S");
                    }
                    txlDetailList.addAll(list);
                }
                if (t.getData().getT() != null) {
                    letters.add("T");
                    List<TongXunLuBean.TXLDetail> list = t.getData().getT();
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setmLetter("T");
                    }
                    txlDetailList.addAll(list);
                }
                if (t.getData().getU() != null) {
                    letters.add("U");
                    List<TongXunLuBean.TXLDetail> list = t.getData().getU();
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setmLetter("U");
                    }
                    txlDetailList.addAll(list);
                }
                if (t.getData().getV() != null) {
                    letters.add("V");
                    List<TongXunLuBean.TXLDetail> list = t.getData().getV();
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setmLetter("V");
                    }
                    txlDetailList.addAll(list);
                }
                if (t.getData().getW() != null) {
                    letters.add("W");
                    List<TongXunLuBean.TXLDetail> list = t.getData().getW();
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setmLetter("W");
                    }
                    txlDetailList.addAll(list);
                }
                if (t.getData().getX() != null) {
                    letters.add("X");
                    List<TongXunLuBean.TXLDetail> list = t.getData().getX();
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setmLetter("X");
                    }
                    txlDetailList.addAll(list);
                }
                if (t.getData().getY() != null) {
                    letters.add("Y");
                    List<TongXunLuBean.TXLDetail> list = t.getData().getY();
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setmLetter("Y");
                    }
                    txlDetailList.addAll(list);
                }
                if (t.getData().getZ() != null) {
                    letters.add("Z");
                    List<TongXunLuBean.TXLDetail> list = t.getData().getZ();
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setmLetter("Z");
                    }
                    txlDetailList.addAll(list);
                }

                Logger.e(letters.toString());
                Logger.e(txlDetailList.toString());

                //自定义字母
                letterBar.setLetters(letters);

                //触摸后的监听回调
                letterBar.setOnLetterChangeListener(new LetterBar.OnLetterChangeListner() {
                    @Override
                    public void onLetterChanged(String letter) {
                        Log.e("SLL-onLetterChanged:", letter);
                        move(getItemPosition(txlDetailList, letter));

                    }

                    @Override
                    public void onLetterChoosed(String letter) {
                        Log.e("SLL-onLetterChoosed:", letter);
                        move(getItemPosition(txlDetailList, letter));
                    }
                });
            }

            @Override
            protected void onFail(Throwable throwable) throws Exception {
                Logger.e(throwable.getMessage());
            }
        });

    }
}
