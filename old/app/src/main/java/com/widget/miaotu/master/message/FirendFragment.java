package com.widget.miaotu.master.message;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.widget.EaseConversationList;
import com.orhanobut.logger.Logger;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.widget.miaotu.R;
import com.widget.miaotu.base.BaseFragment;
import com.widget.miaotu.common.utils.IntentUtils;
import com.widget.miaotu.common.utils.ShapeTextView;
import com.widget.miaotu.common.utils.rxbus.HomeUpdateChange;
import com.widget.miaotu.common.utils.rxbus.RxBus;
import com.widget.miaotu.http.BaseObserver;
import com.widget.miaotu.http.bean.BaseEntity;
import com.widget.miaotu.http.bean.MsgCountBean;
import com.widget.miaotu.http.retrofit.RetrofitFactory;
import com.widget.miaotu.http.retrofit.TransformerUi;
import com.widget.miaotu.master.message.activity.ChatActivity;
import com.widget.miaotu.master.message.activity.MoShengRenMessageActivity;
import com.widget.miaotu.master.message.activity.SystemMessageActivity;
import com.widget.miaotu.master.message.activity.TongXunLuActivity;
import com.widget.miaotu.master.message.activity.YanZhengTixingMessageActivity;
import com.widget.miaotu.master.message.adapter.MessageListAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

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


    protected boolean isConflict;
    private final static int MSG_REFRESH = 2;
    protected List<EMConversation> conversationList = new ArrayList<EMConversation>();
    protected boolean hidden;
    private MessageListAdapter mAdapter;


    public static FirendFragment newInstance() {
        return new FirendFragment();
    }


    @Override
    protected void initViewAndData(View view) {

        //获取未读消息数量
        getMsgCount();

        //初始化消息列表数据
        conversationList();


        //初始化标题
        appBarInit();




    }

    /**
     * 初始化标题
     */
    private void appBarInit() {
        topBar.setTitle("消息").setTextColor(Color.parseColor("#FFFFFF"));
        Button rightButton = topBar.addRightTextButton("通讯录", 1);
        rightButton.setTextColor(Color.parseColor("#FFFFFF"));
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转到通讯录
                IntentUtils.startIntent(getFragmentContext(), TongXunLuActivity.class);

            }
        });
        topBar.setBackgroundColor(Color.parseColor("#03DAC5"));
    }

    /**
     * 初始化，参数为会话列表集合
     */
    private void conversationList() {


        conversationList.addAll(loadConversationList());

        mAdapter = new MessageListAdapter(R.layout.item_message, conversationList, getFragmentContext(), new MessageListAdapter.ItemClickCallBack() {
            @Override
            public void itemCLick(EMConversation emConversation) {

                startActivity(new Intent(getFragmentContext(), ChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID, emConversation.conversationId()));

            }
        });
        easeConversationList.setLayoutManager(new LinearLayoutManager(getFragmentContext()));
        easeConversationList.setAdapter(mAdapter);
        EMClient.getInstance().addConnectionListener(connectionListener);
    }


    protected EMConnectionListener connectionListener = new EMConnectionListener() {

        @Override
        public void onDisconnected(int error) {
            if (error == EMError.USER_REMOVED || error == EMError.USER_LOGIN_ANOTHER_DEVICE || error == EMError.SERVER_SERVICE_RESTRICTED
                    || error == EMError.USER_KICKED_BY_CHANGE_PASSWORD || error == EMError.USER_KICKED_BY_OTHER_DEVICE) {
                isConflict = true;
            } else {
                handler.sendEmptyMessage(0);
            }
        }

        @Override
        public void onConnected() {
            handler.sendEmptyMessage(1);
        }
    };

    protected Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    onConnectionDisconnected();
                    break;
                case 1:
                    onConnectionConnected();
                    break;

                case MSG_REFRESH: {
                    conversationList.clear();
                    conversationList.addAll(loadConversationList());
                    mAdapter.notifyDataSetChanged();
                    break;
                }
                default:
                    break;
            }
        }
    };

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        this.hidden = hidden;
        if (!hidden && !isConflict) {
            refresh();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!hidden) {
            refresh();
        }
    }

    /**
     * refresh ui
     */
    public void refresh() {
        if (!handler.hasMessages(MSG_REFRESH)) {
            handler.sendEmptyMessage(MSG_REFRESH);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().removeConnectionListener(connectionListener);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (isConflict) {
            outState.putBoolean("isConflict", true);
        }
    }

    protected void onConnectionDisconnected() {
        errorItemContainer.setVisibility(View.VISIBLE);
    }

    /**
     * connected to server
     */
    protected void onConnectionConnected() {
        errorItemContainer.setVisibility(View.GONE);
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
                            if (msgCountBean.getCommunityMessageCount().intValue() > 0) {
                                stv_msg_miaomushequ.setVisibility(View.VISIBLE);
                                stv_msg_miaomushequ.setText(msgCountBean.getCommunityMessageCount() + "");//社区唯独消息
                            }
                            if (msgCountBean.getStrangerCount().intValue() > 0) {
                                stv_msg_tongzhixiaoxi.setText(msgCountBean.getStrangerCount() + "");//系统消息
                                stv_msg_tongzhixiaoxi.setVisibility(View.VISIBLE);
                            }
                            if (msgCountBean.getVerifyCount().intValue() > 0) {
                                stv_msg_yanzhengtixing.setText(msgCountBean.getVerifyCount() + "");//验证消息
                                stv_msg_yanzhengtixing.setVisibility(View.VISIBLE);


                                String str = "您有<font color='#FF0000'>" + msgCountBean.getVerifyCount() + "</font>条验证消息";
                                tv_yanzhengtixing_detail.setText(str);
                            }

                            if (msgCountBean.getStrangerCount().intValue() > 0) {
                                stv_moshengren.setText(msgCountBean.getStrangerCount() + "");//陌生人
                                stv_moshengren.setVisibility(View.VISIBLE);
                                String str = "您有<font color='#FF0000'>" + msgCountBean.getStrangerCount() + "</font>条未读消息";
                                tv_moshengren_detail.setText(str);
                            }
                        } else {
                            ToastUtils.showShort(t.getMessage());
                        }
                    }

                    @Override
                    protected void onFail(Throwable throwable) throws Exception {
                        ToastUtils.showShort("请求出错");
                    }
                });
    }


    /**
     * 加载通讯录列表数据
     *
     * @return +
     */
    protected List<EMConversation> loadConversationList() {
        // get all conversations
        Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();

        List<Pair<Long, EMConversation>> sortList = new ArrayList<Pair<Long, EMConversation>>();
        /**
         * lastMsgTime will change if there is new message during sorting
         * so use synchronized to make sure timestamp of last message won't change.
         */
        synchronized (conversations) {
            for (EMConversation conversation : conversations.values()) {
                if (conversation.getAllMessages().size() != 0) {
                    sortList.add(new Pair<Long, EMConversation>(conversation.getLastMessage().getMsgTime(), conversation));
                }
            }
        }
        try {
            // Internal is TimSort algorithm, has bug
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


    /**
     * sort conversations according time stamp of last message
     *
     * @param conversationList
     */
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

                IntentUtils.startIntent(getFragmentContext(), MoShengRenMessageActivity.class);
                break;
            case R.id.ll_firend_zixunkefu://咨询客服
                startActivity(new Intent(getFragmentContext(), ChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID, "72873"));
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