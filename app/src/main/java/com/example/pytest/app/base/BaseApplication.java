package com.example.pytest.app.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.lifecycle.LifecycleObserver;
import androidx.multidex.MultiDexApplication;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import com.example.pytest.R;
import com.example.pytest.app.repository.Config;
import com.example.pytest.app.util.LogUtil;
import com.example.pytest.app.util.fileUtil.EmotionFileUtil;
import com.example.pytest.app.util.mathUtil.DynamicTimeFormat;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.util.SmartUtil;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;

/**
 * 此类继承自Application
 * 一般Application的作用就是在App启动的时候，
 * 加载那些只需要加载一次的东西
 * 比如：图片加载器
 * 该类需要在AndroidManifest.xml文件中进行配置
 * android:name=".App"
 */

public final class BaseApplication extends MultiDexApplication implements LifecycleObserver {
    private static BaseApplication instance;
    private final LogUtil log = LogUtil.getInstance();
    public static final String TAG = "BaseApplication";
    private AsyncDataTask asyncDataTask;

    static {
        //设置全局默认配置（优先级最低，会被其他设置覆盖）
        SmartRefreshLayout.setDefaultRefreshInitializer((context, layout) -> {
            //全局设置（优先级最低）
            layout.setEnableAutoLoadMore(true);
            layout.setEnableOverScrollDrag(false);
            layout.setEnableOverScrollBounce(true);
            layout.setEnableLoadMoreWhenContentNotFull(true);
            layout.setEnableScrollContentWhenRefreshed(true);
            layout.setPrimaryColorsId(R.color.bg_light, android.R.color.black);
            layout.getLayout().setTag("close egg");
        });

        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> {
            //全局设置主题颜色（优先级第二低，可以覆盖 DefaultRefreshInitializer 的配置，与下面的ClassicsHeader绑定）
            int delta = new Random().nextInt(7 * 24 * 60 * 60 * 1000);
            ClassicsHeader mClassicsHeader = new ClassicsHeader(context);
            mClassicsHeader.setLastUpdateTime(new Date(System.currentTimeMillis() - delta));
            mClassicsHeader.setTimeFormat(new SimpleDateFormat("更新于 MM-dd HH:mm", Locale.CHINA));
            mClassicsHeader.setTimeFormat(new DynamicTimeFormat("更新于 %s"));

            Drawable mDrawableProgress = ((ImageView) mClassicsHeader.findViewById(ClassicsHeader.ID_IMAGE_PROGRESS)).getDrawable();
            if (mDrawableProgress instanceof LayerDrawable) {
                mDrawableProgress = ((LayerDrawable) mDrawableProgress).getDrawable(0);
            }

            if (Build.VERSION.SDK_INT >= 21) {
                mDrawableProgress.setTint(0xffffffff);
            } else if (mDrawableProgress instanceof VectorDrawableCompat) {
                ((VectorDrawableCompat) mDrawableProgress).setTint(0xffffffff);
            }

            return mClassicsHeader;
        });
    }


    public static BaseApplication get() {
        return instance;
    }

    @Override
    public final void onCreate() {
        super.onCreate();
        instance = this;
        init();

    }

    private void init() {
        //fresco
        Fresco.initialize(this);
        setImageLoader();//九宫格图片加载器初始化
        //内存检测
//        refWatcher = LeakCanary.install(this); //2.0后不再需要加载
//        asyncData(); //异步加载Emotion数据
//        initSDK();
    }

    private void initSDK() {
        boolean agreed = Config.getUserService();
        if (agreed) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    initOtherSDK();
                }
            }).start();
        }
    }

    public void initOtherSDK() {
/*        //个推
        com.igexin.sdk.PushManager.getInstance().initialize(this);
        com.igexin.sdk.PushManager.getInstance().setDebugLogger(this, new IUserLoggerInterface() {
            @Override
            public void log(String s) {
            }
        });*/

        //友盟统计
        //初始化组件化基础库, 所有友盟业务SDK都必须调用此初始化接口。
        //建议在宿主App的Application.onCreate函数中调用基础组件库初始化函数。
    /*    UMConfigure.init(this, "5ee1acdfdbc2ec078743a6ee", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
        UMConfigure.setLogEnabled(false);

        //mob
        MobSDK.submitPolicyGrantResult(true, null);
        ShareSDK.prepareLoopShare(new LoopShareResultListener() {
            @Override
            public void onResult(Object var1) {
                String test = new Hashon().fromHashMap((HashMap<String, Object>) var1);
                HashMap<String, Object> map = (HashMap<String, Object>) var1;
                String path = map.get("path").toString();
                String startPage = map.get("startPage").toString();
                if (path.equals("/appGameDetail")) { //跳转游戏详情
                    int index = startPage.indexOf("?");
                    String str1 = startPage.substring(index + 1, startPage.length());
                    String[] str2 = str1.split("&");
                    String[] str3 = str2[0].split("=");
                    String[] str4 = str2[1].split("=");
                    String[] str5 = str2[2].split("=");
                    String[] str6 = str2[3].split("=");
                    String gameId = str3[1];
                    String area = str4[1];
                    String gameAva = str5[1];
                    String appId = str6[1];
                    String areaName = "";
                    if (area.equals("CN")) {
                        areaName = "国区";
                    } else if (area.equals("ARS")) {
                        areaName = "阿根廷区";
                    } else if (area.equals("RU")) {
                        areaName = "俄罗斯区";
                    }
                    Intent intent = new Intent(BaseApplication.get(), GameDetailActivity.class);
                    intent.putExtra("gameId", gameId);
                    intent.putExtra("gameAva", "");
                    intent.putExtra("appId", appId);
                    intent.putExtra("area", areaName);
                    startActivity(intent);
                } else if (path.equals("/appInviteFriend")) { //跳转游戏详情
                    String url = "https://steampy.com/appLottery?token=" + Config.getLoginToken();
                    Intent intent = new Intent(BaseApplication.get(), SteamWebActivity.class);
                    intent.putExtra("url", url);
                    intent.putExtra("title", "抽奖");
                    startActivity(intent);
                } else if (path.equals("/appRecommend")) { //跳转合集包
                    int index = startPage.indexOf("?");
                    String str1 = startPage.substring(index + 1, startPage.length());  //issueId=11111
                    String[] str3 = str1.split("=");
                    String issueId = str3[1];
                    Intent intent = new Intent(BaseApplication.get(), RecommendInfoActivity.class);
                    intent.putExtra("issueId", issueId);
                    intent.putExtra("ava", "");
                    intent.putExtra("title", "");
                    intent.putExtra("remark", "");
                    startActivity(intent);
                } else if (path.equals("/appRedEnvelopeUser")) {
                    int index = startPage.indexOf("?");
                    String str = startPage.substring(index + 1); //data=AAAAAA&data1=test0000001
                    Config.setShareInvite(str);
                }
            }

            @Override
            public void onError(Throwable t) {
            }
        });
        log.e("base application init sdk success");*/
    }

    private void setImageLoader() {
//        NineGridViewGroup.setImageLoader(new GlideImageLoader());//为九宫格设置图片加载器
    }

    public void asyncData() {
        if (TextUtils.isEmpty(Config.getEmotionList())) {
            asyncDataTask = new AsyncDataTask();
            asyncDataTask.execute();
        }
    }

    private final class AsyncDataTask extends AsyncTask<String, Integer, String> {

        public AsyncDataTask() {
        }

        protected String doInBackground(String... params) {
            try {
                return EmotionFileUtil.readAssetsFile(BaseApplication.get(), "emotion.json");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            if (result != null) {
                Config.setEmotionList(EmotionFileUtil.parseEmotionList(result));
            }
            asyncDataTask.cancel(true);
        }
    }


}