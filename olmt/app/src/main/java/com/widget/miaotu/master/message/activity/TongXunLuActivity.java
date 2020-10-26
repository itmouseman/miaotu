package com.widget.miaotu.master.message.activity;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.orhanobut.logger.Logger;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.widget.miaotu.R;
import com.widget.miaotu.base.MBaseActivity;
import com.widget.miaotu.base.MVCControl;
import com.widget.miaotu.common.utils.GlideUtils;
import com.widget.miaotu.common.utils.IntentUtils;
import com.widget.miaotu.common.utils.other.LetterBar;
import com.widget.miaotu.http.BaseObserver;
import com.widget.miaotu.http.bean.BaseEntity;
import com.widget.miaotu.http.bean.MessageListMainBean;
import com.widget.miaotu.http.retrofit.RetrofitFactory;
import com.widget.miaotu.http.retrofit.TransformerUi;

import org.jetbrains.annotations.NotNull;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;

/**
 * 通讯录界面
 */
public class TongXunLuActivity extends MBaseActivity {


    @BindView(R.id.letter_bar_tongxunlu)
    LetterBar letterBar;
    @BindView(R.id.recyclerView_tongxunlu)
    RecyclerView recyclerView;
    @BindView(R.id.ll_tongxunlu_yztx)
    LinearLayout ll_tongxunlu_yztx;
    @BindView(R.id.qui_top_bar)
    QMUITopBarLayout mTopBar;
    @BindView(R.id.ll_add_miaoyou)
    LinearLayout ll_add_miaoyou;


    List<String> letters = new ArrayList<>();

    private LinearLayoutManager mLayoutManager;
    private List<MessageListMainBean.MessageUserInfo> mNewMiaoYouList = new ArrayList<>();
    private BaseQuickAdapter<MessageListMainBean.MessageUserInfo, BaseViewHolder> mAdapter;


    @Override
    protected boolean isUseFullScreenMode() {
        return true;
    }

    @Override
    protected MVCControl createControl() {
        return null;
    }

    @Override
    protected void initView() {
        ll_tongxunlu_yztx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //验证提醒
                IntentUtils.startIntent(TongXunLuActivity.this, YanZhengTixingMessageActivity.class);
            }
        });
        mTopBar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTopBar.setTitle("通讯录").setTextColor(Color.parseColor("#EBF9FF"));
        ll_add_miaoyou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//添加苗友
                IntentUtils.startIntent(TongXunLuActivity.this, AddMiaoYouActivity.class);
            }
        });


        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new BaseQuickAdapter<MessageListMainBean.MessageUserInfo, BaseViewHolder>(R.layout.item_tongxunlu_msg, new ArrayList<>()) {
            @Override
            protected void convert(@NotNull BaseViewHolder baseViewHolder, MessageListMainBean.MessageUserInfo messageUserInfo) {
                baseViewHolder.setText(R.id.tv_tongxunl_name, messageUserInfo.getNickname());
                baseViewHolder.setText(R.id.tv_tongxunl_detail, messageUserInfo.getCompanyName());
                GlideUtils.loadUrl(TongXunLuActivity.this, messageUserInfo.getHeadUrl(), baseViewHolder.getView(R.id.iv_msg_head));
                baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.putExtra("userIdX", messageUserInfo.getUserIdX());
                        intent.setClass(TongXunLuActivity.this, FriendsDetailsActivity.class);
                        startActivity(intent);
                    }
                });
            }
        };

        recyclerView.setAdapter(mAdapter);
    }


    @Override
    protected void initDetailData() {
        mailList();
    }


    private int getItemPosition(List<MessageListMainBean.MessageUserInfo> txlDetailList, String zm) {
        for (int i = 0; i < txlDetailList.size(); i++) {
            if (zm.equals(letters.get(i))) {
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

        showWaiteDialog("正在加载中...");
        RetrofitFactory.getInstence().API().getChatList().compose(TransformerUi.setThread()).subscribe(new BaseObserver<MessageListMainBean>(this) {
            @Override
            protected void onSuccess(BaseEntity<MessageListMainBean> t) throws Exception {
                hideWaiteDialog();
//                List<MessageListMainBean.MessageUserInfo> listOldMY = new ArrayList<>();
                if (t.getStatus() == 100) {
                    Set<Map.Entry<String, List<MessageListMainBean.MessageUserInfo>>> entry = t.getData().getList().entrySet();
                    mNewMiaoYouList.clear();
//                    listOldMY.clear();
                    letters.clear();
                    for (Map.Entry<String, List<MessageListMainBean.MessageUserInfo>> m : entry) {
                        String key = m.getKey();
                        letters.add(key);
//                        listOldMY.addAll(m.getValue());
                    }

                    //按照字母排序开始
                    Collections.sort(letters, new Comparator<String>() {
                        //升序排序
                        public int compare(String o1,
                                           String o2) {
                            return o1.compareTo(o2);
                        }
                    });

                    for (int i = 0; i < letters.size(); i++) {
                        String keyT = letters.get(i);
                        for (Map.Entry<String, List<MessageListMainBean.MessageUserInfo>> m : entry) {
                            String key = m.getKey();
                            if (key.equals(keyT)) {
                                mNewMiaoYouList.addAll(m.getValue());
                            }
                        }
                    }


//                    List<String> list = new ArrayList<>();
//
//                    for (int i = 0; i < listOldMY.size(); i++) {
//                        list.add(listOldMY.get(i).getNickname());
//                    }
//                    //根据汉字拼音首字母排序
//                    Comparator<Object> com = Collator.getInstance(java.util.Locale.CHINA);
//                    Collections.sort(list, com);
//                    for (String s : list) {
//                        Logger.i(s);
//                    }
//                    for (int j = 0; j < list.size(); j++) {
//                        String mName = list.get(j);
//                        for (int w = 0; w < listOldMY.size(); w++) {
//                            if (listOldMY.get(w).getNickname().equals(mName)) {
//                                mNewMiaoYouList.add(listOldMY.get(w));
//                            }
//                        }
//                    }
                    //按照字母排序结束

                    letterBar.setLetters(letters);
                    letterBar.setOnLetterChangeListener(new LetterBar.OnLetterChangeListner() {
                        @Override
                        public void onLetterChanged(String letter) {
                            move(getItemPosition(mNewMiaoYouList, letter));
                        }

                        @Override
                        public void onLetterChoosed(String letter) {
                            move(getItemPosition(mNewMiaoYouList, letter));
                        }
                    });
                    mAdapter.setNewInstance(mNewMiaoYouList);
                } else {
                    ToastUtils.showShort(t.getMessage());
                }

            }

            @Override
            protected void onFail(Throwable throwable) throws Exception {
                hideWaiteDialog();
                Logger.e(throwable.getMessage());
            }
        });

    }
}
