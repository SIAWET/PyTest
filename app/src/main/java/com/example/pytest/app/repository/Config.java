package com.example.pytest.app.repository;


import android.os.Environment;

import com.alibaba.fastjson.JSON;
import com.example.pytest.app.base.BaseApplication;
import com.example.pytest.app.entity.chatEntity.EmojiEntity;

import java.util.List;


/**
 * 数据存储，数据配置工具类
 */
public class Config {
    public static boolean testFlag = false;  //true 测试版  false 正式版
    public static boolean logFlag = false;  //log测试项 true打印 false 不打印
    public static String DAPP_PAGE = "10";  //dapp 页面每页显示个数
    public static String EMPTY = "";  //置空字符串
    public static String NETURL = "https://www.steampy.com";  //官网


    public static String CHAT_URL = "xilipy.com";
    public static String CHAT_PORT = "";
    public static String CHAT_ALL_URL = "https://xilipy.com";
    public static String CHAT_ALL_URL_TYPE = "wss";
    public static String CHAT_ROBOT_NAME = "steampybot";

    /*public static String CHAT_URL = "47.100.60.2";
    public static String CHAT_PORT = ":8081";
    public static String CHAT_ALL_URL = "http://47.100.60.2:8081";
    public static String CHAT_ALL_URL_TYPE = "ws";
    public static String CHAT_ROBOT_NAME = "steampybot";  */

    /*public static String CHAT_URL = "170.106.33.162";
    public static String CHAT_PORT = ":3000";
    public static String CHAT_ALL_URL = "http://170.106.33.162:3000";
    public static String CHAT_ALL_URL_TYPE = "ws";
    public static String CHAT_ROBOT_NAME = "mybotuser";*/


    public static int CHAT_MESSAGE_PAGE_SIZE = 20;  //消息列表加载个数
    public static int CHAT_DISCUSS_PAGE_SIZE = 10;  //讨论列表加载个数
    public static int CHAT_ATTENTION_PAGE_SIZE = 10;  //关注加载个数
    public static int VIBRATE_TIME = 150;  //消息点击震动时间

    public static String MONEY = "¥ ";  //人民币
    public static String ARSMONEY = "ARS $";  //阿根廷币
    public static String RUMONEY = "pуб. ";  //俄罗斯币

    public static String CHINA_AREA = "国区";  //国区
    public static String ARS_AREA = "阿根廷区";  //阿根廷区
    public static String RU_AREA = "俄罗斯区";  //俄罗斯区
    public static String US_AREA = "美区";  //美区

    public static final String DEFAULT_NAME = "SteamPY";
    public static final String DEFAULT_PASSWORD = "SteamPY";
    public static final String filePath = Environment.getExternalStorageDirectory() + "/SteamPY/";
    //网络请求打印黑名单
    public static final String[] blackNet = {"steamUser/bindFirstLoginV2", "cardOrder/cardCharge", "arsSteamUser/bindFirstLoginV2", "ruSteamUser/bindFirstLoginV2", "steamUser/bindTokenLoginV2",
            "arsSteamUser/bindTokenLoginV2", "ruSteamUser/bindTokenLoginV2",
            "steamUser/bindCaptchaLoginV2", "arsSteamUser/bindCaptchaLoginV2", "ruSteamUser/bindCaptchaLoginV2", "steamKeyOrder/keyFirst", "steamKeyOrder/keyFirstV2",
            "steamKeyOrder/keyToken", "steamKeyOrder/keyTokenV2", "steamKeyOrder/keyCaptcha", "steamKeyOrder/keyCaptchaV2"};
    //搜索游戏黑名单
    public static final String[] blackGame = {"消失的光芒", "消光", "Dying", "Dying Light", "DyingLight",
            "Dying Light Enhanced Edition", "https://store.steampowered.com/app/239140/Dying_Light/",
            "吃鸡", "绝地求生", "PLAYERUNKNOWN'S BATTLEGROUNDS", "PLAYERUNKNOWN'SBATTLEGROUNDS", "PUBG",
            "https://store.steampowered.com/app/578080/PLAYERUNKNOWNS_BATTLEGROUNDS/"};

    public static String apiKey = "yLloKTac7MYxhGGdLi2zQlRq";
    public static String secretKey = "8rzmU5YTdYfj0T4bvukPYz3IUSpBSmPN";
    public static String licenseID = "wayi-face-android";
    public static String licenseFileName = "idl-license.face-android";

    public static String DEFAULT_AVATAR = "https://buntoy.oss-cn-shanghai.aliyuncs.com/f45fda84344a4b69a7e95dd6c87275ac.png"; //默认头像地址
    public static String EMOTION_LIST = "chat_emotion_list"; //收藏表情地址列表
    public static String MESSGE_LIST = "message_list"; //消息缓存地址
    public static String EMOTION_DISCUSS_LIST = "emotion_discuss_list"; //表情讨论串地址


    //保存用户隐私条款
    public static boolean getUserPrivate() {
        return new Conf(BaseApplication.get()).getBooleanConfigPara("login_private");
    }

    public static void setUserPrivate(boolean login_private) {
        new Conf(BaseApplication.get()).createBoolean("login_private", login_private);
    }

    //保存登录token
    public static String getLoginToken() {
        return new Conf(BaseApplication.get()).getConfigString("login_token");
    }

    public static void setLoginToken(String token) {
        new Conf(BaseApplication.get()).createString("login_token", token);
    }

    //保存登录phone
    public static String getLoginPhone() {
        return new Conf(BaseApplication.get()).getConfigString("login_phone");
    }

    public static void setLoginPhone(String phone) {
        new Conf(BaseApplication.get()).createString("login_phone", phone);
    }

    //保存QQ群
    public static String getQQ() {
        return new Conf(BaseApplication.get()).getConfigString("PY_QQ");
    }

    public static void setQQ(String QQ) {
        new Conf(BaseApplication.get()).createString("PY_QQ", QQ);
    }

    //保存登录name
    public static String getLoginName() {
        return new Conf(BaseApplication.get()).getConfigString("login_name");
    }

    public static void setLoginName(String name) {
        new Conf(BaseApplication.get()).createString("login_name", name);
    }

    //保存用户PY 余额
    public static String getPYBalance() {
        return new Conf(BaseApplication.get()).getConfigString("PY_Banlance");
    }

    public static void setPYBalance(String balance) {
        new Conf(BaseApplication.get()).createString("PY_Banlance", balance);
    }

    //保存登录avator
    public static String getLoginAvator() {
        return new Conf(BaseApplication.get()).getConfigString("login_avator");
    }

    public static void setLoginAvator(String avator) {
        new Conf(BaseApplication.get()).createString("login_avator", avator);
    }

    //保存登录用户邀请码
    public static String getLoginInvite() {
        return new Conf(BaseApplication.get()).getConfigString("login_invite");
    }

    public static void setLoginInvite(String avator) {
        new Conf(BaseApplication.get()).createString("login_invite", avator);
    }

    //保存当前版本 用户点击不立即更新时间
    public static String getVersionTime() {
        return new Conf(BaseApplication.get()).getConfigString("updatetime");
    }

    public static void setVersionTime(String updatetime) {
        new Conf(BaseApplication.get()).createString("updatetime", updatetime);
    }

    //保存最新版本
    public static String getNewVersion() {
        return new Conf(BaseApplication.get()).getConfigString("version");
    }

    public static void setNewVersion(String version) {
        new Conf(BaseApplication.get()).createString("version", version);
    }

    //保存最新版本下载链接
    public static String getNewVersionUrl() {
        return new Conf(BaseApplication.get()).getConfigString("versionUrl");
    }

    public static void setNewVersionUrl(String versionUrl) {
        new Conf(BaseApplication.get()).createString("versionUrl", versionUrl);
    }

    //保存当前版本是否更新 true 立即更新 false 不立即更新
    public static boolean getVersionUpdate() {
        return new Conf(BaseApplication.get()).getBooleanConfigPara("versionUpdate");
    }

    public static void setVersionUpdate(boolean flag) {
        new Conf(BaseApplication.get()).createBoolean("versionUpdate", flag);
    }

    //保存用户登录时间
    public static String getUserLastLogin() {
        return new Conf(BaseApplication.get()).getConfigString("lastLogin");
    }

    public static void setUserLastLogin(String time) {
        new Conf(BaseApplication.get()).createString("lastLogin", time);
    }

    //保存Client id
    public static String getClientId() {
        return new Conf(BaseApplication.get()).getConfigString("clientId");
    }

    public static void setClientId(String id) {
        new Conf(BaseApplication.get()).createString("clientId", id);
    }

    //保存download url
    public static long getDownloadInfo(String url) {
        return new Conf(BaseApplication.get()).getConfigLong(url);
    }

    public static void setDownloadInfo(String url, long value) {
        new Conf(BaseApplication.get()).createLong(url, value);
    }

    //保存用户平台ID
    public static String getUserId() {
        return new Conf(BaseApplication.get()).getConfigString("user_id");
    }

    public static void setUserId(String id) {
        new Conf(BaseApplication.get()).createString("user_id", id);
    }

    //保存用户是否绑定steam 国区
    public static boolean getUserBind() {
        return new Conf(BaseApplication.get()).getBooleanConfigPara("user_bind");
    }

    public static void setUserBind(boolean flag) {
        new Conf(BaseApplication.get()).createBoolean("user_bind", flag);
    }

    //保存用户是否绑定steam 阿根廷区
    public static boolean getUserBindArs() {
        return new Conf(BaseApplication.get()).getBooleanConfigPara("user_bind_ars");
    }

    public static void setUserBindArs(boolean flag) {
        new Conf(BaseApplication.get()).createBoolean("user_bind_ars", flag);
    }

    //保存用户是否绑定steam 俄罗斯区
    public static boolean getUserBindRU() {
        return new Conf(BaseApplication.get()).getBooleanConfigPara("user_bind_ru");
    }

    public static void setUserBindRU(boolean flag) {
        new Conf(BaseApplication.get()).createBoolean("user_bind_ru", flag);
    }

    //保存用户当前区
    public static String getAreaName() {
        return new Conf(BaseApplication.get()).getConfigString("steam_area");
    }

    public static void setAreaName(String name) {
        new Conf(BaseApplication.get()).createString("steam_area", name);
    }

    //保存用户当前区所在币种
    public static String getAreaMoney() {
        return new Conf(BaseApplication.get()).getConfigString("steam_area_money");
    }

    public static void setAreaMoney(String name) {
        new Conf(BaseApplication.get()).createString("steam_area_money", name);
    }

    //保存用户昵称
    public static String getNickName() {
        return new Conf(BaseApplication.get()).getConfigString("nick_name");
    }

    public static void setNickName(String name) {
        new Conf(BaseApplication.get()).createString("nick_name", name);
    }

    //保存用户个性签名
    public static String getUserSign() {
        return new Conf(BaseApplication.get()).getConfigString("user_sign");
    }

    public static void setUserSign(String name) {
        new Conf(BaseApplication.get()).createString("user_sign", name);
    }

    //保存用户登录Session
    public static String getSteamSession() {
        return new Conf(BaseApplication.get()).getConfigString("steam_session");
    }

    public static void setSteamSession(String session) {
        new Conf(BaseApplication.get()).createString("steam_session", session);
    }

    //保存用户登录steamLoginSecure
    public static String getSteamLoginSecure() {
        return new Conf(BaseApplication.get()).getConfigString("steam_loginSecure");
    }

    public static void setSteamLoginSecure(String steamLoginSecure) {
        new Conf(BaseApplication.get()).createString("steam_loginSecure", steamLoginSecure);
    }

    //保存用户是否进入确定用户协议
    public static boolean getUserService() {
        return new Conf(BaseApplication.get()).getBooleanConfigPara("user_service");
    }

    public static void setUserService(boolean flag) {
        new Conf(BaseApplication.get()).createBoolean("user_service", flag);
    }

    //保存用户卡券个数
    public static String getUserCoupon() {
        return new Conf(BaseApplication.get()).getConfigString("user_coupon");
    }

    public static void setUserCoupon(String coupon) {
        new Conf(BaseApplication.get()).createString("user_coupon", coupon);
    }

    //保存用户选择优惠券是否再次提示
    public static boolean getCouponTip() {
        return new Conf(BaseApplication.get()).getBooleanConfigPara("user_coupon_tip");
    }

    public static void setCouponTip(boolean flag) {
        new Conf(BaseApplication.get()).createBoolean("user_coupon_tip", flag);
    }

    //保存用户从分享出获取的邀请码等
    public static String getShareInvite() {
        return new Conf(BaseApplication.get()).getConfigString("share_invite");
    }

    public static void setShareInvite(String data) {
        new Conf(BaseApplication.get()).createString("share_invite", data);
    }

    //clientId
    public static String getChatClientId() {
        return new Conf(BaseApplication.get()).getConfigString("chat_clientId");
    }

    public static void setChatClientId(String clientId) {
        new Conf(BaseApplication.get()).createString("chat_clientId", clientId);
    }

    //保存社区中用户userId
    public static String getChatUserId() {
        return new Conf(BaseApplication.get()).getConfigString("chat_userId");
    }

    public static void setChatUserId(String userId) {
        new Conf(BaseApplication.get()).createString("chat_userId", userId);
    }

    //保存社区中用户userName
    public static String getChatUserName() {
        return new Conf(BaseApplication.get()).getConfigString("chat_userName");
    }

    public static void setChatUserName(String userName) {
        new Conf(BaseApplication.get()).createString("chat_userName", userName);
    }

    //保存社区中用户AuthToken
    public static String getChatAuthToken() {
        return new Conf(BaseApplication.get()).getConfigString("chat_authToken");
    }

    public static void setChatAuthToken(String authToken) {
        new Conf(BaseApplication.get()).createString("chat_authToken", authToken);
    }

    //保存社区中用户accessToken
    public static String getChatAccessToken() {
        return new Conf(BaseApplication.get()).getConfigString("chat_accessToken");
    }

    public static void setChatAccessToken(String accessToken) {
        new Conf(BaseApplication.get()).createString("chat_accessToken", accessToken);
    }

    //保存社区中用户expireIn
    public static long getChatExpire() {
        return new Conf(BaseApplication.get()).getConfigLong("chat_expire");
    }

    public static void setChatExpire(long expire) {
        new Conf(BaseApplication.get()).createLong("chat_expire", expire);
    }

    //保存社区键盘弹出的高度
    public static String getSoftInputHeight() {
        return new Conf(BaseApplication.get()).getConfigString("chat_soft_input_height");
    }

    public static void setSoftInputHeight(String height) {
        new Conf(BaseApplication.get()).createString("chat_soft_input_height", height);
    }

    //保存社区点击表情，最近使用过得
    public static String getEmotionRecent() {
        return new Conf(BaseApplication.get()).getConfigString("chat_emotion_recent");
    }

    public static void setEmotionRecent(String data) {
        new Conf(BaseApplication.get()).createString("chat_emotion_recent", data);
    }

    //保存社区用户权限
    public static String getChartUserRole() {
        return new Conf(BaseApplication.get()).getConfigString("chat_user_role");
    }

    public static void setChartUserRole(String data) {
        new Conf(BaseApplication.get()).createString("chat_user_role", data);
    }

    //保存社区用户权限
    public static String getMessageId() {
        return new Conf(BaseApplication.get()).getConfigString("py_message_id");
    }

    public static void setMessageId(String data) {
        new Conf(BaseApplication.get()).createString("py_message_id", data);
    }

    //保存首页优惠券弹框是否显示
    public static boolean getCouponStatus() {
        return new Conf(BaseApplication.get()).getBooleanConfigPara("coupon_dialog_status");
    }

    public static void setCouponStatus(boolean data) {
        new Conf(BaseApplication.get()).createBoolean("coupon_dialog_status", data);
    }

    //保存 首页优惠券弹框,一个月时间
    public static String getCouponStatusTime() {
        return new Conf(BaseApplication.get()).getConfigString("coupon_dialog_time");
    }

    public static void setCouponStatusTime(String couponTime) {
        new Conf(BaseApplication.get()).createString("coupon_dialog_time", couponTime);
    }

    //保存 首页优惠券弹框,一个月时间
    public static String getGetuiClientId() {
        return new Conf(BaseApplication.get()).getConfigString("getui_ClientId");
    }

    public static void setGetuiClientId(String ClientId) {
        new Conf(BaseApplication.get()).createString("getui_ClientId", ClientId);
    }

    public static String getEmotionList() {
        return new Conf(BaseApplication.get()).getConfigString("emotion_list");
    }

    public static void setEmotionList(List<EmojiEntity> list) {
        String json = JSON.toJSONString(list);
        new Conf(BaseApplication.get()).createString("emotion_list", json);
    }
}
