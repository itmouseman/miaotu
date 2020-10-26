package com.widget.miaotu.http;

import com.alibaba.fastjson.JSON;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.widget.miaotu.http.bean.AddFeedBackSeedBean;
import com.widget.miaotu.http.bean.AuthSubmitBean;
import com.widget.miaotu.http.bean.BaseEntity;
import com.widget.miaotu.http.bean.BindMobileJavaBeanOne;
import com.widget.miaotu.http.bean.BindMobileJavaBeanTwo;
import com.widget.miaotu.http.bean.ChangePassWordJavaBean;
import com.widget.miaotu.http.bean.CollectionOrCanceBean;
import com.widget.miaotu.http.bean.CompanyInfoMeBean;
import com.widget.miaotu.http.bean.ContactListBean;
import com.widget.miaotu.http.bean.EditUserInfoBean;
import com.widget.miaotu.http.bean.GardenListBean;
import com.widget.miaotu.http.bean.HeadPublishWantBuyBean;
import com.widget.miaotu.http.bean.HeadSearchInfoBean;
import com.widget.miaotu.http.bean.HeadSeedlingListBean;
import com.widget.miaotu.http.bean.HerUserInfoBean;
import com.widget.miaotu.http.bean.HomeFenLeiJavaBean;
import com.widget.miaotu.http.bean.HomeInitJavaBean;
import com.widget.miaotu.http.bean.HomeSearchDetailJavaBean;
import com.widget.miaotu.http.bean.HomeSearchJavaBean;
import com.widget.miaotu.http.bean.IdAndPageBean;
import com.widget.miaotu.http.bean.IdAndTokenBean;
import com.widget.miaotu.http.bean.IdBean;
import com.widget.miaotu.http.bean.LoginTokenBean;
import com.widget.miaotu.http.bean.MayBeFriendsBean;
import com.widget.miaotu.http.bean.MessageListMainBean;
import com.widget.miaotu.http.bean.MsgCountBean;
import com.widget.miaotu.http.bean.MsgVerifyBean;
import com.widget.miaotu.http.bean.MyCollectionListGetBean;
import com.widget.miaotu.http.bean.MyContacts;
import com.widget.miaotu.http.bean.MyFollowListBean;
import com.widget.miaotu.http.bean.PhoneLoginSendNewBean;
import com.widget.miaotu.http.bean.PostV5UserVipInfoBean;
import com.widget.miaotu.http.bean.PostWxPayBean;
import com.widget.miaotu.http.bean.QiuGouHeadsjavaBean;
import com.widget.miaotu.http.bean.QiuGouMiaoMuJavaBean;
import com.widget.miaotu.http.bean.RealCompanyBean;
import com.widget.miaotu.http.bean.SListByTypeJavaBean;
import com.widget.miaotu.http.bean.SMSLoginBeanNew;
import com.widget.miaotu.http.bean.SMSLoginSendBeanNew;
import com.widget.miaotu.http.bean.SaveGardenBean;
import com.widget.miaotu.http.bean.SaveSeedBean;
import com.widget.miaotu.http.bean.SeedChooseInfoBean;
import com.widget.miaotu.http.bean.SeedCompanyInfoBean;
import com.widget.miaotu.http.bean.SeedListGetBean;
import com.widget.miaotu.http.bean.SeedlingDetailJavaBean;
import com.widget.miaotu.http.bean.SendDemandSeedBean;
import com.widget.miaotu.http.bean.SendSMSSendBeanNew;
import com.widget.miaotu.http.bean.SysMessageBean;
import com.widget.miaotu.http.bean.TokenBeanNew;
import com.widget.miaotu.http.bean.UploadAPPBean;
import com.widget.miaotu.http.bean.WantBuyFillInfoBean;
import com.widget.miaotu.http.bean.WantBuySeedListGetBean;
import com.widget.miaotu.http.bean.WeXinLoginJavaBean;
import com.widget.miaotu.http.bean.head.HeadCompanyFollowBean;
import com.widget.miaotu.http.bean.head.HeadHaoYouEdit;
import com.widget.miaotu.http.bean.head.HeadHerUserIdBean;
import com.widget.miaotu.http.bean.head.HeadPostPayWxBean;
import com.widget.miaotu.http.bean.head.HeadSearchDetailBean;
import com.widget.miaotu.http.bean.head.HeadSearchThinkBean;
import com.widget.miaotu.http.bean.head.HeadSelectInfoListBean;
import com.widget.miaotu.http.bean.head.HeadStatusBean;
import com.widget.miaotu.http.bean.head.HeadSysMessageBean;
import com.widget.miaotu.http.bean.head.HeadUserIdBean;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiService {
    //正式服务器
    String BASE_URL = "https://api.miaoto.net/";
    String TEST_BASE_URL = "http://123.57.58.117:9988/";
    String OLD_URl = "https://www.miaoto.net/";

    //活动列表
    String HuoDongLieBIao = "https://www.miaoto.net/zmh/activities/v3/list";
    //注册协议
    String REGISTER_AGREEMENT = "http://www.miaoto.net/zmh/H5Page/protocol/protocol.html";

    /**
     * 用户主页分享URL
     */
    String USER_INFO_SHARE_URL = "https://www.miaoto.net/zmh/H5Page/community/home/home.html?userId=";
    /**
     * 供应详情分享URL
     */
    String DEMAND_DETAIL_SHARE_URL = "https://www.miaoto.net/zmh/H5Page/community/gyDetail/gyDetail.html?businessId=";

    /**
     * 社区详情分享URL
     */
    String COMMUNITY_DETAIL_SHARE_URL = "https://www.miaoto.net/zmh/H5Page/community/detail/detail.html?businessId=";

    /**
     * 邀请好友
     */
    String INVITE_FRIEND_URL = "https://www.miaoto.net/zmh/H5Page/points/inviteFriends.html";


    //获取版本信息

    @GET("v5/system/openModel/update")
    Observable<BaseEntity<UploadAPPBean>> uploadAPP();


    //首页初始化页面

    @GET("v5/home/init")
    Observable<BaseEntity<HomeInitJavaBean>> getHomeInitInfo();

    //首页根据类型查询对应的供应苗木

    @POST("v5/seedling/seedlingsListByType")
    Observable<BaseEntity<List<SListByTypeJavaBean>>> getSeedlingsListByType(@Body RequestBody requestBody);


    //分类页面---获取一级苗木分类信息

    @POST("v5/seedling/getSeedlingClassifyFirst")
    Observable<BaseEntity<List<HomeFenLeiJavaBean>>> getSeedlingClassifyFirst();


    //根据搜索词联想出苗木名或企业名

    @POST("v5/seedling/search")
    Observable<BaseEntity<List<HomeSearchJavaBean>>> getSearch(@Body HeadSearchThinkBean headSearchThinkBean);


    //首页搜索苗木

    @POST("v5/seedling/seedlingSearch")
    Observable<BaseEntity<HomeSearchDetailJavaBean>> seedlingSearch(@Body HeadSearchDetailBean headSearchDetailBean);

    //根据搜索条件搜索该企业下的所有满足条件的苗木

    @POST("v5/seedling/searchMoreSeedling")
    Observable<BaseEntity<Object>> searchMoreSeedling(@Body RequestBody requestBody);


    //根据苗木id获取苗木详情

    @POST("v5/seedling/seedlingDetail")
    Observable<BaseEntity<SeedlingDetailJavaBean>> seedlingDetail(@Body RequestBody requestBody);

    // 5.0获取企业信息

    @POST("v5/company/info")
    Observable<BaseEntity<CompanyInfoMeBean>> getCompanyInfo(@Body RequestBody requestBody);

    // 5.0获取企业信息  不传参数默认获取自己的信息
    @POST("v5/company/info")
    Observable<BaseEntity<CompanyInfoMeBean>> getCompanyInfo();


    //获取登记苗木列表信息

    @POST("v5/seedling/seedlingList")
    Observable<BaseEntity<List<SeedListGetBean>>> getSeedlingList(@Body HeadSeedlingListBean headSeedlingListBean);


    //获取求购苗木列表信息     *

    @POST("v5/wantBuy/wantBuyDetail")
    Observable<BaseEntity<List<WantBuySeedListGetBean>>> getWantBuySeedList(@Body RequestBody requestBody);

    //手机号登录 密码登录

    @POST("v5/user/login/pwd")
    Observable<BaseEntity<LoginTokenBean>> getPhoneLoginNewList(@Body PhoneLoginSendNewBean bean);

    // 获取用户基本信息

    @POST("v5/user/info")
    Observable<BaseEntity<SMSLoginBeanNew>> getUserInfo(@Body TokenBeanNew bean);

    //     * 新短信登录    验证码 *

    @POST("v5/user/login/code")
    Observable<BaseEntity<LoginTokenBean>> getSMSLoginNewList(@Body SMSLoginSendBeanNew bean);


    @POST("other/sms")
    Observable<BaseEntity<Object>> getSendSMSNewList(
            @Body SendSMSSendBeanNew bean
    );


    @POST("v5/user/reset/password")
    Observable<BaseEntity<Object>> resetPassword(@Body ChangePassWordJavaBean bean);


    //微信登录
    @POST("v5/user/login/wx")
    Observable<BaseEntity<LoginTokenBean>> wxLogin(@Body WeXinLoginJavaBean bean);


    //5.0换绑手机号

    @POST("v5/user/modify/phone")
    Observable<BaseEntity<Object>> modifyPhone(@Body BindMobileJavaBeanOne bindMobileJavaBeanOne);


    //5.0手机号绑定

    @POST("v5/user/bind/phone")
    Observable<BaseEntity<LoginTokenBean>> bindPhone(@Body BindMobileJavaBeanTwo bindMobileJavaBeanTwo);

    // 5.0编辑用户资料
    @POST("v5/user/edit")
    Observable<BaseEntity<Object>> editUserInfo(@Body EditUserInfoBean bean);

    //5.0上传文件     *
    @Multipart
    @POST("other/upload/file")
    Observable<BaseEntity<Object>> uploadImageNew(@Part("data") String type,
//            @Part("file\"; filename=\"test.jpg") RequestBody fileBody,
                                                  @Part MultipartBody.Part imageFile);

    //获取全部职位
    @POST("v5/user/jobTitle/all")
    Observable<BaseEntity<List<String>>> userJobTitle();


    //实名认证
    @POST("v5/user/auth/submit")
    Observable<BaseEntity<Object>> userAuthSubmit(@Body AuthSubmitBean authSubmitBean);


    // 意见反馈
    @POST("v5/user/feedback")
    Observable<BaseEntity<Object>> getAddFeedBackList(@Body AddFeedBackSeedBean addFeedBackSeedBean);

    //获取二维码
    @POST("v5/wx/getShareCode")
    Observable<BaseEntity<Object>> getShareCode();

    //获取苗圃列表
    @POST("v5/wantBuy/wantBuyList")
    Observable<BaseEntity<List<QiuGouMiaoMuJavaBean>>> getWantBuyList(@Body QiuGouHeadsjavaBean qiuGouHeadsjavaBean);


    //5.0个人收藏的苗木列表
    @POST("v5/seedling/collectSeedlingList")
    Observable<BaseEntity<MyCollectionListGetBean>> getCollectSeedList(@Body IdAndPageBean idAndPageBean);

    //5.0个人关注的企业列表
    @POST("v5/company/myFollow/list")
    Observable<BaseEntity<List<MyFollowListBean>>> myFollowList(@Body IdAndPageBean idAndPageBean);

    //编辑(创建)企业信息
    @POST("v5/company/edit")
    Observable<BaseEntity<Object>> companyEdit(@Body SeedCompanyInfoBean bean);

    //企业认证
    @POST("v5/company/auth")
    Observable<BaseEntity<Object>> companyAuth(@Body RealCompanyBean bean);

    //获取苗圃列表信息
    @POST("v5/garden/gardenList")
    Observable<BaseEntity<List<GardenListBean>>> gardenList();

    //5.0根据苗圃id获取苗圃详情
    @POST("v5/garden/gardenDetail")
    Observable<BaseEntity<SaveGardenBean>> gardenDetail(@Body IdBean idBean);

    //5.0保存编辑的苗圃
    @POST("v5/garden/editGarden")
    Observable<BaseEntity<Object>> editGarden(@Body SaveGardenBean bean);

    //5.0添加苗圃
    @POST("v5/garden/saveGarden")
    Observable<BaseEntity<Object>> saveGarden(@Body SaveGardenBean bean);

    //5.0根据苗圃id删除对应的苗圃
    @POST("v5/garden/deleteGarden")
    Observable<BaseEntity<Object>> deleteGarden(@Body IdAndTokenBean bean);

    //5.0苗木名称id获取分类、规格、种植类型选择
    @POST("v5/seedling/seedlingChooseInfo")
    Observable<BaseEntity<SendDemandSeedBean>> seedlingChooseInfo(@Body SeedChooseInfoBean seedChooseInfoBean);

    //5.0重新编辑苗木信息
    @POST("v5/seedling/editSeedling")
    Observable<BaseEntity<Object>> editSeed(@Body SaveSeedBean saveSeedBean);

    //5.0保存登记苗木
    @POST("v5/seedling/saveSeedling")
    Observable<BaseEntity<Object>> saveSeed(@Body SaveSeedBean saveSeedBean);

    //收藏/取消收藏 供应的苗木
    @POST("v5/seedling/updateCollectSeedling")
    Observable<BaseEntity<Object>> updateCollectSeedling(@Body CollectionOrCanceBean collectionOrCanceBean);

    //发布求购
    @POST("v5/wantBuy/publishWantBuy")
    Observable<BaseEntity<Object>> publishWantBuy(@Body HeadPublishWantBuyBean headPublishWantBuyBean);


    //获取通知消息
    @POST("v5/msg/send/list")
    Observable<BaseEntity<List<SysMessageBean>>> getSysMessage(@Body HeadSysMessageBean headSysMessageBean);

    //获取通讯录好友
    @POST("v5/msg/contacts/friends")
    Observable<BaseEntity<ContactListBean>> getFriendsList();

    //获取未读消息数量
    @POST("v5/msg/msgCount")
    Observable<BaseEntity<MsgCountBean>> msgCount();

    //验证提醒
    @POST("v5/msg/friends/verify")
    Observable<BaseEntity<List<MsgVerifyBean>>> msgVerify(@Body HeadSysMessageBean headSysMessageBean);

    //可能认识的人
    @POST("v5/msg/friends/mayBeFriends")
    Observable<BaseEntity<List<MayBeFriendsBean>>> mayBeFriends();

    //添加好友
    @POST("v5/msg/friends/send")
    Observable<BaseEntity<Object>> addFriends(@Body HeadUserIdBean userId);

    //搜索用户信息
    @POST("v5/user/searchUserInfo")
    Observable<BaseEntity<List<MayBeFriendsBean>>> searchUserInfo(@Body HeadSearchInfoBean headSearchInfoBean);


    //微信支付
    @POST("pay/wx")
    Observable<BaseEntity<PostWxPayBean>> postPayWx(@Body HeadPostPayWxBean headPostPayWxBean);

    //获取VIP页个人信息
    @POST("v5/user/vip/info")
    Observable<BaseEntity<PostV5UserVipInfoBean>> postV5UserVipInfo();

    //5.0企业关注与取消关注
    @POST("v5/company/follow")
    Observable<BaseEntity<Object>> getCompanyFollow(@Body HeadCompanyFollowBean headCompanyFollowBean);

    //更改供应状态（下架或上架或删除）
    @POST("v5/seedling/optionSeedlingStatus")
    Observable<BaseEntity<Object>> optionSeedlingStatus(@Body HeadStatusBean headStatusBean);

    //修改密码
    @POST("v5/user/modify/password")
    Observable<BaseEntity<Object>> modifyPassword(@Body ChangePassWordJavaBean changePassWordJavaBean);

    //5.0聊天消息列表
    @POST("v5/msg/friends/list")
    Observable<BaseEntity<MessageListMainBean>> getChatList();

    //操作好友申请
    //是否通过 2. 通过 3. 忽略
    @POST("v5/msg/friends/edit")
    Observable<BaseEntity<Object>> tongGuoHaoYou(@Body HeadHaoYouEdit headHaoYouEdit);

    //更新求购苗木状态（结束求购、删除求购）
    @POST("v5/wantBuy/updateWantBuyStatus")
    Observable<BaseEntity<Object>> updateWantBuyStatus(@Body HeadStatusBean headStatusBean);

    //获取上一次发布求购的基本用户信息
    @GET("v5/wantBuy/getWantBuyFillInfo")
    Observable<BaseEntity<WantBuyFillInfoBean>> getWantBuyFillInfo();

    //获取他人信息
    @POST("v5/user/her/info")
    Observable<BaseEntity<HerUserInfoBean>> getHerInfo(@Body HeadHerUserIdBean headHerUserIdBean);

    //获取通讯录
    @POST("v5/msg/upload/contacts/new")
    Observable<BaseEntity< Map<String, List<ContactListBean>>>> uploadContacts(@Body  ArrayList<MyContacts> myContacts );

    //苗木推广/取消推广
    @POST("v5/seedling/promoteSeedling")
    Observable<BaseEntity<Object>> promoteSeedling(@Body HeadStatusBean headStatusBean);

    //苗木推广/取消推广
    @POST("v5/zmh/Information/selectInfoList")
    Observable<BaseEntity<Object>> selectInfoList(@Body HeadSelectInfoListBean headSelectInfoListBean);
}
