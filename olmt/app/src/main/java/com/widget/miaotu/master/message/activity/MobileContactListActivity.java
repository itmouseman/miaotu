package com.widget.miaotu.master.message.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.collection.ArraySet;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseNodeAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.orhanobut.logger.Logger;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.widget.miaotu.R;
import com.widget.miaotu.base.MBaseActivity;
import com.widget.miaotu.base.MVCControl;
import com.widget.miaotu.common.utils.ContactUtils;
import com.widget.miaotu.common.utils.GlideUtils;
import com.widget.miaotu.common.utils.PermissionTool;
import com.widget.miaotu.common.utils.home.search.EditText_Clear;
import com.widget.miaotu.common.utils.other.LetterBar;
import com.widget.miaotu.http.BaseObserver;
import com.widget.miaotu.http.bean.BaseEntity;
import com.widget.miaotu.http.bean.ContactListBean;
import com.widget.miaotu.http.bean.HeadSearchInfoBean;
import com.widget.miaotu.http.bean.MayBeFriendsBean;
import com.widget.miaotu.http.bean.MessageListMainBean;
import com.widget.miaotu.http.bean.MyContacts;
import com.widget.miaotu.http.bean.head.HeadUserIdBean;
import com.widget.miaotu.http.retrofit.RetrofitFactory;
import com.widget.miaotu.http.retrofit.TransformerUi;
import com.widget.miaotu.master.message.adapter.AddMiaoYouAdapter;
import com.yanzhenjie.permission.runtime.Permission;

import org.jetbrains.annotations.NotNull;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import butterknife.BindView;

import static android.view.View.VISIBLE;

/**
 * 手机联系人列表
 */
public class MobileContactListActivity extends MBaseActivity {

    @BindView(R.id.es_mobile_contact_list)
    EditText_Clear editSearch;
    @BindView(R.id.qui_top_bar)
    QMUITopBarLayout mTopBar;
    @BindView(R.id.rcv_contactList)
    RecyclerView rcv_contactList;
    @BindView(R.id.tv_mobile_friend_num)
    TextView tv_mobile_friend_num;
    @BindView(R.id.srl_contactList_search1)
    SmartRefreshLayout smartRefreshLayout1;
    @BindView(R.id.srl_contactList_search2)
    SmartRefreshLayout smartRefreshLayout2;
    @BindView(R.id.rcv_contactList_search)
    RecyclerView rcv_contactList_search;
    @BindView(R.id.letter_bar_contactList)
    LetterBar letterBar;
    private List<ContactListBean> listContactMiaoYou = new ArrayList<>();
    private List<ContactListBean> listContactMiaoYou2 = new ArrayList<>();
    private BaseQuickAdapter<ContactListBean, BaseViewHolder> mAdapter;
    private BaseQuickAdapter<ContactListBean, BaseViewHolder> mAdapter2;
    List<String> letters = new ArrayList<>();
    private LinearLayoutManager mLayoutManager;

    @Override
    protected MVCControl createControl() {
        return null;
    }

    @Override
    protected void initView() {
        mTopBar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTopBar.setTitle("通讯录").setTextColor(Color.parseColor("#EBF9FF"));


        mLayoutManager = new LinearLayoutManager(this);
        rcv_contactList.setLayoutManager(mLayoutManager);
        mAdapter = new BaseQuickAdapter<ContactListBean, BaseViewHolder>(R.layout.item_tongxunlu_msg, new ArrayList<>()) {
            @Override
            protected void convert(@NotNull BaseViewHolder baseViewHolder, ContactListBean messageUserInfo) {
                baseViewHolder.setText(R.id.tv_tongxunl_name, messageUserInfo.getNickname());
                baseViewHolder.setText(R.id.tv_tongxunl_detail, messageUserInfo.getPhone());
                GlideUtils.loadUrl(MobileContactListActivity.this, messageUserInfo.getAvatar(), baseViewHolder.getView(R.id.iv_msg_head));
                baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.putExtra("userIdX",String.valueOf( messageUserInfo.getUserId()));
                        intent.setClass(MobileContactListActivity.this, FriendsDetailsActivity.class);
                        startActivity(intent);
                    }
                });
            }
        };

        rcv_contactList.setAdapter(mAdapter);


        mAdapter2 = new BaseQuickAdapter<ContactListBean, BaseViewHolder>(R.layout.item_add_miao_you, new ArrayList<>()) {
            @Override
            protected void convert(@NotNull BaseViewHolder baseViewHolder, ContactListBean contactListBean) {

                if (contactListBean.getAvatar().contains("http")) {
                    GlideUtils.loadUrl(MobileContactListActivity.this, contactListBean.getAvatar(), baseViewHolder.getView(R.id.rctv_add_miaoyou_head));
                } else {
                    GlideUtils.loadUrl(MobileContactListActivity.this, "http://oss-cn-beijing.aliyuncs.com/miaotu1" + contactListBean.getAvatar(), baseViewHolder.getView(R.id.rctv_add_miaoyou_head));
                }

                baseViewHolder.setText(R.id.tv_add_miao_you_1, contactListBean.getPhone());
                if (contactListBean.getIsFriend() == 0 || contactListBean.getIsFriend() == 3 || contactListBean.getIsFriend() == 4) {
                    baseViewHolder.getView(R.id.tv_add_miao_you_2).setVisibility(View.VISIBLE);
                    baseViewHolder.setText(R.id.tv_add_miao_you_2, contactListBean.getIsFriend() + "个共同好友");//0 3 4 是非好友
                } else {
                    baseViewHolder.getView(R.id.tv_add_miao_you_2).setVisibility(View.GONE);
                }
                baseViewHolder.setText(R.id.tv_add_miao_you_3, "手机联系人: " + contactListBean.getNickname());
                baseViewHolder.getView(R.id.stv_add_miaoyou).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {//加好友
                        //添加好友
                        addMyFirend(String.valueOf(contactListBean.getUserId()), baseViewHolder.getAdapterPosition(), 2);
                    }
                });


            }
        };
        rcv_contactList_search.setLayoutManager(new LinearLayoutManager(MobileContactListActivity.this));
        rcv_contactList_search.setAdapter(mAdapter2);

    }


    @Override
    protected void initDetailData() {
        getAddressBook();

        editSearchChange();
    }

    private void editSearchChange() {
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                smartRefreshLayout1.setVisibility(View.GONE);
                smartRefreshLayout2.setVisibility(VISIBLE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                listContactMiaoYou2.clear();
                mAdapter2.notifyDataSetChanged();
                String editable = editSearch.getText().toString();
                String str = stringFilter(editable.toString());
                if (!editable.equals(str)) {
                    editSearch.setText(str);
                    //设置新的光标所在位置
                    editSearch.setSelection(str.length());
                }
            }

            // 输入文本后调用该方法
            @Override
            public void afterTextChanged(Editable s) {

                String tempName = editSearch.getText().toString();
                if (!TextUtils.isEmpty(tempName)) {
                    searchFriend(tempName);
                } else {
                    smartRefreshLayout1.setVisibility(VISIBLE);
                    smartRefreshLayout2.setVisibility(View.GONE);
                }

            }
        });
    }

    public static String stringFilter(String str) throws PatternSyntaxException {
// 只允许字母、数字和汉字
        String regEx = "[^a-zA-Z0-9\u4E00-\u9FA5]";//正则表达式
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    private void searchFriend(String tempName) {
        for (int i = 0; i < listContactMiaoYou.size(); i++) {
            if (listContactMiaoYou.get(i).getName().contains(tempName) || listContactMiaoYou.get(i).getNickname().contains(tempName)
                    || listContactMiaoYou.get(i).getPhone().contains(tempName)
            ) {
                listContactMiaoYou2.add(listContactMiaoYou.get(i));
            }
        }
        mAdapter2.setNewInstance(listContactMiaoYou2);
        if (listContactMiaoYou2.size() == 0) {
            View emptyView = getLayoutInflater().inflate(R.layout.layout_empty_coment, null);
            emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            mAdapter2.setEmptyView(emptyView);
        }
    }


    //获取通讯录
    @SuppressLint("WrongConstant")
    private void getAddressBook() {
        PermissionTool.requestPermission(this, new PermissionTool.PermissionQuestListener() {
            @Override
            public void onGranted() {
                ArrayList<MyContacts> myContacts = ContactUtils.getAllContacts(MobileContactListActivity.this);
                showWaiteDialog("正在加载中...");
                RetrofitFactory.getInstence().API().uploadContacts(myContacts).compose(TransformerUi.setThread()).subscribe(new BaseObserver<Map<String, List<ContactListBean>>>(MobileContactListActivity.this) {
                    @Override
                    protected void onSuccess(BaseEntity<Map<String, List<ContactListBean>>> t) throws Exception {
                        hideWaiteDialog();
//                        List<ContactListBean> listOldMY = new ArrayList<>();
                        if (t.getStatus() == 100) {
                            letters.clear();
//                            listOldMY.clear();
                            listContactMiaoYou.clear();
                            if (t.getStatus() == 100) {
                                Set<Map.Entry<String, List<ContactListBean>>> entry = t.getData().entrySet();
                                for (Map.Entry<String, List<ContactListBean>> m : entry) {
                                    String key = m.getKey();
                                    letters.add(key);
//                                    listOldMY.addAll(m.getValue());
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
                                    for (Map.Entry<String, List<ContactListBean>> m : entry) {
                                        String key = m.getKey();
                                        if (key.equals(keyT)) {
                                            listContactMiaoYou.addAll(m.getValue());
                                        }
                                    }
                                }
                                tv_mobile_friend_num.setText("联系人中" + listContactMiaoYou.size() + "人加入了苗途");

//                                List<String> list = new ArrayList<>();
//                                for (int i = 0; i < listOldMY.size(); i++) {
//                                    list.add(listOldMY.get(i).getNickname());
//                                }
//                                //根据汉字拼音首字母排序
//                                Comparator<Object> com = Collator.getInstance(java.util.Locale.CHINA);
//                                Collections.sort(list, com);
//                                for (int j = 0; j < list.size(); j++) {
//                                    String mName = list.get(j);
//                                    for (int w = 0; w < listOldMY.size(); w++) {
//                                        if (listOldMY.get(w).getNickname().equals(mName)) {
//                                            listContactMiaoYou.add(listOldMY.get(w));
//                                        }
//                                    }
//                                }
//                                //按照字母排序结束


                                letterBar.setLetters(letters);
                                letterBar.setOnLetterChangeListener(new LetterBar.OnLetterChangeListner() {
                                    @Override
                                    public void onLetterChanged(String letter) {
                                        Logger.i("letter1   = " + letter);
                                        move(getItemPosition(listContactMiaoYou, letter));
                                    }

                                    @Override
                                    public void onLetterChoosed(String letter) {
                                        Logger.i("letter2   = " + letter);
                                        move(getItemPosition(listContactMiaoYou, letter));
                                    }
                                });

                                mAdapter.setNewInstance(listContactMiaoYou);
                            } else {
                                ToastUtils.showShort(t.getMessage());
                            }
                        }

                    }

                    @Override
                    protected void onFail(Throwable throwable) throws Exception {
                        hideWaiteDialog();
                    }
                });
            }

            @Override
            public void onDenied(List<String> data) {

                Logger.e(data.toString());
                MobileContactListActivity.this.finish();
            }

            @Override
            public String onAlwaysDeniedData() {
                return "我们需要获取通讯录权限，是否前往设置？";
            }
        }, Permission.Group.CONTACTS);
    }

    private void move(int position) {
        if (position != -1) {
            rcv_contactList.scrollToPosition(position);
            mLayoutManager.scrollToPositionWithOffset(position, 0);

        }
    }

    private int getItemPosition(List<ContactListBean> txlDetailList, String zm) {
        for (int i = 0; i < txlDetailList.size(); i++) {
            if (zm.equals(letters.get(i))) {
                return i;
            }
        }
        return 0;
    }

    /**
     * 添加好友
     *
     * @param userId
     */
    private void addMyFirend(String userId, int position, int type) {
        showWaiteDialog("正在发送...");
        RetrofitFactory.getInstence().API().addFriends(new HeadUserIdBean(userId)).compose(TransformerUi.setThread())
                .subscribe(new BaseObserver<Object>(MobileContactListActivity.this) {
                    @Override
                    protected void onSuccess(BaseEntity<Object> t) throws Exception {
                        hideWaiteDialog();
                        if (t.getStatus() == 100) {
                            ToastUtils.showShort("请求发送成功,待通过");
                            if (type == 1) {
                                mAdapter.removeAt(position);
                                mAdapter.notifyDataSetChanged();
                            } else {
                                mAdapter2.removeAt(position);
                                mAdapter2.notifyDataSetChanged();
                            }

                        } else {
                            ToastUtils.showShort(t.getMessage());
                        }
                    }

                    @Override
                    protected void onFail(Throwable throwable) throws Exception {
                        hideWaiteDialog();
                        Logger.i(throwable.getMessage());
                    }
                });
    }

    @Override
    protected boolean isUseFullScreenMode() {
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mobile_contact_list;
    }


}
