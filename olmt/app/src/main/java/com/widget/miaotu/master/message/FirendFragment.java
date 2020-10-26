package com.widget.miaotu.master.message;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.text.Html;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMGroupReadAck;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.cache.UserCacheManager;
import com.hyphenate.easeui.model.EaseAtMessageHelper;
import com.hyphenate.easeui.model.EaseDingMessageHelper;
import com.orhanobut.logger.Logger;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.widget.miaotu.R;
import com.widget.miaotu.base.BaseFragment;
import com.widget.miaotu.common.utils.IntentUtils;
import com.widget.miaotu.common.utils.PermissionTool;
import com.widget.miaotu.common.utils.ShapeTextView;
import com.widget.miaotu.common.utils.rxbus.HomeUpdateChange;
import com.widget.miaotu.common.utils.rxbus.MessageUnReadNum;
import com.widget.miaotu.common.utils.rxbus.RxBus;
import com.widget.miaotu.http.BaseObserver;
import com.widget.miaotu.http.bean.BaseEntity;
import com.widget.miaotu.http.bean.MessageListMainBean;
import com.widget.miaotu.http.bean.MsgCountBean;
import com.widget.miaotu.http.retrofit.RetrofitFactory;
import com.widget.miaotu.http.retrofit.TransformerUi;
import com.widget.miaotu.master.message.activity.ChatActivity;
import com.widget.miaotu.master.message.activity.MoShengRenMessageActivity;
import com.widget.miaotu.master.message.activity.SystemMessageActivity;
import com.widget.miaotu.master.message.activity.TongXunLuActivity;
import com.widget.miaotu.master.message.activity.YanZhengTixingMessageActivity;
import com.widget.miaotu.master.message.adapter.MessageListAdapter;
import com.yanzhenjie.permission.runtime.Permission;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * 消息界面
 */
public class FirendFragment extends BaseFragment {

    @BindView(R.id.top_bar_firend_topBar)
    QMUITopBarLayout topBar;
    @BindView(R.id.stv_msg_miaomushequ)
    ShapeTextView stv_msg_miaomushequ;//苗木社区
    @BindView(R.id.stv_msg_kefuzixun)
    ShapeTextView stv_msg_kefuzixun;//咨询客服
    @BindView(R.id.stv_msg_tongzhixiaoxi)
    ShapeTextView stv_msg_tongzhixiaoxi;//通知消息
    @BindView(R.id.stv_msg_yanzhengtixing)
    ShapeTextView stv_msg_yanzhengtixing;//验证提醒
    @BindView(R.id.stv_msg_moshengren)
    ShapeTextView stv_moshengren;//陌生人

    @BindView(R.id.easeConversationList)
    RecyclerView easeConversationList;
    @BindView(R.id.fl_error_item1)
    FrameLayout errorItemContainer;

    @BindView(R.id.tv_yanzhengtixing_detail)
    TextView tv_yanzhengtixing_detail;
    @BindView(R.id.tv_moshengren_detail)
    TextView tv_moshengren_detail;


    private MessageListAdapter mAdapter;

    protected List<EMConversation> conversationList = new ArrayList<EMConversation>();
    List<MessageListMainBean.MessageUserInfo> messageUserInfoList = new ArrayList<>();
    List<MessageListMainBean.MessageUserInfo> moshengrenMessageUserInfoList = new ArrayList<>();

    public static FirendFragment newInstance() {
        return new FirendFragment();
    }


    @Override
    protected void initViewAndData(View view) {
        RxBus.getInstance().toObservableSticky(this, HomeUpdateChange.class).subscribe(new Consumer<HomeUpdateChange>() {
            @Override
            public void accept(HomeUpdateChange homeUpdateChange) throws Exception {
                if (!homeUpdateChange.isChange()) {
                    getMsgCount();
                    conversationList();
                    initData();
                }
            }
        });
        getMsgCount();
        conversationList();
        initData();


        EMClient.getInstance().chatManager().addMessageListener(new EMMessageListener() {
            @Override
            public void onMessageReceived(List<EMMessage> messages) {
                //收到消息
                EaseAtMessageHelper.get().parseMessages(messages);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(messages.get(0).getFrom());

                        for (int i = 0; i < messages.size(); i++) {
                            String from = messages.get(i).getFrom();
                            String lastMessage = messages.get(i).getBody().toString();
                            //普通消息列表
                            for (int j = 0; j < messageUserInfoList.size(); j++) {
                                if (from.equals(messageUserInfoList.get(j).getUserIdX())) {
                                    messageUserInfoList.get(j).setLastMessage(lastMessage);
                                    messageUserInfoList.get(j).setUnreadMsgCount(conversation.getUnreadMsgCount());
                                    mAdapter.setNewInstance(messageUserInfoList);
                                    mAdapter.notifyItemChanged(j);

                                }
                            }
                            //陌生人
                            for (int w = 0;w<moshengrenMessageUserInfoList.size();w++){
                                if (from.equals(moshengrenMessageUserInfoList.get(w).getUserIdX())){
                                    moshengrenMessageUserInfoList.get(w).setUnreadMsgCount(conversation.getUnreadMsgCount());
                                }
                            }
                        }
                        getMsgCount();
                    }
                });
            }

            @Override
            public void onMessageRead(List<EMMessage> messages) {
                //收到已读回执
                Logger.i("-----333-----" + messages.toString());
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getMsgCount();
                    }
                });
            }

            @Override
            public void onMessageDelivered(List<EMMessage> messages) {
                //收到已送达回执
                Logger.i("-----3334-----" + messages.toString());
            }

            @Override
            public void onMessageRecalled(List<EMMessage> messages) {
                //消息被撤回
                Logger.i("-----3335-----" + messages.toString());
            }

            @Override
            public void onMessageChanged(EMMessage message, Object change) {
                //消息状态变动
                Logger.i("-----3336-----" + message.toString());
            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> messages) {
                //收到透传消息
                Logger.i("-----3337-----" + messages.toString());
            }

            @Override
            public void onGroupMessageRead(List<EMGroupReadAck> groupReadAcks) {
                for (EMGroupReadAck ack : groupReadAcks) {
                    EaseDingMessageHelper.get().handleGroupReadAck(ack);
                }
            }
        });
        //初始化标题
        appBarInit();
    }

    /**
     * 初始化标题
     */
    private void appBarInit() {
        topBar.setTitle("消息").setTextColor(Color.parseColor("#FFFFFF"));

        topBar.setBackgroundColor(Color.parseColor("#03DAC5"));

        Button rightButton = topBar.addRightTextButton("通讯录", 1);
        rightButton.setTextColor(Color.parseColor("#FFFFFF"));
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转到通讯录
                IntentUtils.startIntent(getFragmentContext(), TongXunLuActivity.class);

            }
        });

        //更新唯独消息数量
        RxBus.getInstance().toObservableSticky(this, HomeUpdateChange.class).subscribe(new Consumer<HomeUpdateChange>() {
            @Override
            public void accept(HomeUpdateChange homeUpdateChange) throws Exception {
                if (!homeUpdateChange.isChange()) {
                    getMsgCount();
                }
            }
        });

    }

    /**
     * 初始化，参数为会话列表集合
     */
    @SuppressLint("WrongConstant")
    private void conversationList() {
        if (easeConversationList!=null){
            mAdapter = new MessageListAdapter(getActivity());//   startActivity(new Intent(getFragmentContext(), ChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID, emConversation.conversationId()));
            easeConversationList.setLayoutManager(new LinearLayoutManager(getFragmentContext()));
            easeConversationList.setAdapter(mAdapter);
            mAdapter.setOnItemClickListener((adapter, view, position) -> {
                if (messageUserInfoList.size() > 0) {
                    PermissionTool.requestPermission(getActivity(), new PermissionTool.PermissionQuestListener() {
                        @Override
                        public void onGranted() {
                            Intent intent = new Intent(getFragmentContext(), ChatActivity.class);
                            intent.putExtra(EaseConstant.EXTRA_USER_ID, messageUserInfoList.get(position).getUserIdX());
                            intent.putExtra("chartTitle", messageUserInfoList.get(position).getNickname());
                            startActivity(intent);
                        }

                        @Override
                        public void onDenied(List<String> data) {

                            Logger.e(data.toString());
                        }

                        @Override
                        public String onAlwaysDeniedData() {
                            return "我们需要获得相机权限，是否前往设置？";
                        }
                    }, Permission.CAMERA, Permission.WRITE_EXTERNAL_STORAGE, Permission.RECORD_AUDIO);


                }
            });
        }

    }




    private void initData() {
        if (mAdapter != null) {
            conversationList.clear();
            mAdapter.getData().clear();

            EMClient.getInstance().chatManager().loadAllConversations();

            conversationList.addAll(loadConversationList());


            RetrofitFactory.getInstence().API().getChatList().compose(TransformerUi.setThread()).subscribe(new BaseObserver<MessageListMainBean>(getFragmentContext()) {
                @Override
                protected void onSuccess(BaseEntity<MessageListMainBean> t) throws Exception {
                    if (t.getStatus() == 100) {

                        Set<Map.Entry<String, List<MessageListMainBean.MessageUserInfo>>> entry = t.getData().getList().entrySet();
                        messageUserInfoList.clear();
                        moshengrenMessageUserInfoList.clear();
                        for (Map.Entry<String, List<MessageListMainBean.MessageUserInfo>> m : entry) {
                            String key = m.getKey();
                            List<MessageListMainBean.MessageUserInfo> value = m.getValue();
                            messageUserInfoList.addAll(value);
                        }

                        for (int j = 0; j < conversationList.size(); j++) {

                            MessageListMainBean.MessageUserInfo messageUserInfo = new MessageListMainBean.MessageUserInfo();
                            messageUserInfo.setLastMessage(conversationList.get(j).getLastMessage().getBody().toString());
                            messageUserInfo.setUnreadMsgCount(conversationList.get(j).getUnreadMsgCount());
                            messageUserInfo.setMsgTime(conversationList.get(j).getLastMessage().getMsgTime());
                            messageUserInfo.setUserIdX(conversationList.get(j).getLastMessage().getUserName());

                            moshengrenMessageUserInfoList.add(messageUserInfo);
                            String hxUserId = conversationList.get(j).getLastMessage().getUserName();
                            for (int s = 0; s < messageUserInfoList.size(); s++) {
                                if (hxUserId.equals(messageUserInfoList.get(s).getUserIdX())) {
                                    moshengrenMessageUserInfoList.remove(messageUserInfo);
                                }
                            }
                        }


                        for (int k = 0; k < messageUserInfoList.size(); k++) {
                            //保存缓存
                            String chatUserId = messageUserInfoList.get(k).getUserIdX();
                            String avatarUrl = messageUserInfoList.get(k).getHeadUrl();
                            String nickName = messageUserInfoList.get(k).getNickname();
                            UserCacheManager.save(chatUserId, nickName, avatarUrl);


                            for (int m = 0; m < conversationList.size(); m++) {
                                if (conversationList.get(m).getLastMessage().getUserName().equals(messageUserInfoList.get(k).getUserIdX())) {
                                    messageUserInfoList.get(k).setLastMessage(conversationList.get(m).getLastMessage().getBody().toString());
                                    messageUserInfoList.get(k).setUnreadMsgCount(conversationList.get(m).getUnreadMsgCount());
                                    messageUserInfoList.get(k).setMsgTime(conversationList.get(m).getLastMessage().getMsgTime());
                                }
                            }
                        }


                        Collections.sort(messageUserInfoList, new Comparator<MessageListMainBean.MessageUserInfo>() {
                            //升序排序
                            public int compare(MessageListMainBean.MessageUserInfo o1,
                                               MessageListMainBean.MessageUserInfo o2) {
                                return String.valueOf(o2.getMsgTime()).compareTo(String.valueOf(o1.getMsgTime()));
                            }

                        });

                        mAdapter.setNewInstance(messageUserInfoList);
                    }

                }

                @Override
                protected void onFail(Throwable throwable) throws Exception {
                    Logger.e(throwable.getMessage());

                }
            });
        }

    }





    /**
     * 获取未读消息数量
     */
    private void getMsgCount() {
        RetrofitFactory.getInstence().API().msgCount().compose(TransformerUi.setThread())
                .subscribe(new BaseObserver<MsgCountBean>(getFragmentContext()) {
                    @Override
                    protected void onSuccess(BaseEntity<MsgCountBean> t) throws Exception {
                        if (t.getStatus() == 100) {
                            MsgCountBean msgCountBean = t.getData();
//                            int shequ = msgCountBean.getCommunityMessageCount().intValue();
//                            if (shequ > 0) {
//                                stv_msg_miaomushequ.setVisibility(View.VISIBLE);
//                                stv_msg_miaomushequ.setText(msgCountBean.getCommunityMessageCount() + "");//社区唯独消息
//                            }
                            int xitong = msgCountBean.getSystemCount().intValue();
                            if (xitong > 0) {
                                stv_msg_tongzhixiaoxi.setText(msgCountBean.getSystemCount() + "");//系统消息
                                stv_msg_tongzhixiaoxi.setVisibility(View.VISIBLE);
                            } else {
                                stv_msg_tongzhixiaoxi.setVisibility(View.GONE);
                            }
                            int yanzheng = msgCountBean.getVerifyCount().intValue();
                            if (yanzheng > 0) {
                                stv_msg_yanzhengtixing.setText(msgCountBean.getVerifyCount() + "");//验证消息
                                stv_msg_yanzhengtixing.setVisibility(View.VISIBLE);
                                tv_yanzhengtixing_detail.setVisibility(View.VISIBLE);
                                String str = "您有<font color='#FF0000'>" + msgCountBean.getVerifyCount() + "</font>条验证消息";
                                tv_yanzhengtixing_detail.setText(Html.fromHtml(str));
                            } else {
                                stv_msg_yanzhengtixing.setVisibility(View.GONE);
                                tv_yanzhengtixing_detail.setVisibility(View.GONE);
                            }
                            int moshengren = msgCountBean.getStrangerCount().intValue();
                            if (moshengren > 0) {
                                stv_moshengren.setText(msgCountBean.getStrangerCount() + "");//陌生人
                                stv_moshengren.setVisibility(View.VISIBLE);
                                tv_moshengren_detail.setVisibility(View.VISIBLE);
                                String str = "您有<font color='#FF0000'>" + msgCountBean.getStrangerCount() + "</font>条未读消息";
                                tv_moshengren_detail.setText(Html.fromHtml(str));
                            } else {
                                if (moshengrenMessageUserInfoList.size() > 0) {
                                    int msrUnReadNum = 0;
                                    for (int i = 0; i < moshengrenMessageUserInfoList.size(); i++) {
                                        msrUnReadNum += moshengrenMessageUserInfoList.get(i).getUnreadMsgCount();
                                    }
                                    if (msrUnReadNum == 0) {
                                        stv_moshengren.setVisibility(View.GONE);
                                        tv_moshengren_detail.setVisibility(View.GONE);
                                    }else {
                                        stv_moshengren.setVisibility(View.VISIBLE);
                                        tv_moshengren_detail.setVisibility(View.VISIBLE);
                                        stv_moshengren.setText(msrUnReadNum + "");//陌生人
                                        stv_moshengren.setVisibility(View.VISIBLE);
                                        tv_moshengren_detail.setVisibility(View.VISIBLE);
                                        String str = "您有<font color='#FF0000'>" + msrUnReadNum + "</font>条未读消息";
                                        tv_moshengren_detail.setText(Html.fromHtml(str));
                                    }

                                } else {
                                    stv_moshengren.setVisibility(View.GONE);
                                    tv_moshengren_detail.setVisibility(View.GONE);
                                }

                            }

                            int numMessageSize = EMClient.getInstance().chatManager().getUnreadMessageCount();

                            RxBus.getInstance().post(new MessageUnReadNum(numMessageSize + xitong + yanzheng + moshengren));


                        } else {
                            ToastUtils.showShort(t.getMessage());
                        }
                    }

                    @Override
                    protected void onFail(Throwable throwable) throws Exception {
//                        ToastUtils.showShort("请求出错");
                    }
                });
    }


    /**
     * 加载通讯录列表数据
     *
     * @return +
     */
    protected List<EMConversation> loadConversationList() {

        Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
        List<Pair<Long, EMConversation>> sortList = new ArrayList<Pair<Long, EMConversation>>();
        synchronized (conversations) {
            for (EMConversation conversation : conversations.values()) {
                if (conversation.getAllMessages().size() != 0) {
                    sortList.add(new Pair<Long, EMConversation>(conversation.getLastMessage().getMsgTime(), conversation));
                }
            }
        }
        try {
            sortConversationByLastChatTime(sortList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<EMConversation> list = new ArrayList<EMConversation>();
        for (Pair<Long, EMConversation> sortItem : sortList) {
            list.add(sortItem.second);
        }
        return list;
    }


    private void sortConversationByLastChatTime(List<Pair<Long, EMConversation>> conversationList) {
        Collections.sort(conversationList, new Comparator<Pair<Long, EMConversation>>() {
            @Override
            public int compare(final Pair<Long, EMConversation> con1, final Pair<Long, EMConversation> con2) {

                if (con1.first.equals(con2.first)) {
                    return 0;
                } else if (con2.first.longValue() > con1.first.longValue()) {
                    return 1;
                } else {
                    return -1;
                }
            }

        });
    }


    @OnClick({R.id.ll_message_tongzhi, R.id.rl_yanzhengtixing, R.id.ll_sys_moshengren, R.id.ll_firend_zixunkefu, R.id.ll_firend_miaomushequ})
    public void onClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_message_tongzhi://通知消息
                IntentUtils.startIntent(getFragmentContext(), SystemMessageActivity.class);
                break;
            case R.id.rl_yanzhengtixing://验证提醒
                IntentUtils.startIntent(getFragmentContext(), YanZhengTixingMessageActivity.class);
                break;
            case R.id.ll_sys_moshengren://陌生人

                Intent intentMoS = new Intent();
                intentMoS.putExtra("UserBeanMoS", (Serializable) moshengrenMessageUserInfoList);
                intentMoS.setClass(getFragmentContext(), MoShengRenMessageActivity.class);
                startActivity(intentMoS);

                break;
            case R.id.ll_firend_zixunkefu://咨询客服

                Intent intent = new Intent(getFragmentContext(), ChatActivity.class);
                intent.putExtra(EaseConstant.EXTRA_USER_ID, "72873");
                intent.putExtra("chartTitle", "苗小二");
                startActivity(intent);

                break;
            case R.id.ll_firend_miaomushequ://苗木社区
                ToastUtils.showShort("暂未开放");

                break;
        }
    }

    @Override
    protected int getFragmentId() {
        return R.layout.firend_fragment;
    }


}