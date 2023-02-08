package com.example.pytest.app.base;

import android.content.Context;

import com.example.pytest.app.model.event.MessageEvent;
import com.example.pytest.app.repository.Config;
import com.example.pytest.app.util.LogUtil;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.observers.DefaultObserver;

public abstract class BaseObserver<T> extends DefaultObserver<T> {
    private Context context;
    private final LogUtil log = LogUtil.getInstance();

    public BaseObserver(Context context) {
        this.context = context;
    }

    @Override
    public void onNext(T t) {
        //log.e("进入了是否登录检测--onNext");
        if (t instanceof BaseModel) {
            if (!((BaseModel<?>) t).isSuccess()) {  // 处理401.500 等
                if (((BaseModel<?>) t).getCode() == 401) { //未登录
                    onNetNext((BaseModel<?>) t);
                    EventBus.getDefault().post(new MessageEvent("USER_UNLOGIN")); //登录失效,界面刷新
                    LogUtil.getInstance().e("登录失效");

                    Config.setLoginPhone("");
                    Config.setLoginToken("");
                    Config.setLoginAvator("");
                    Config.setNickName("");
                    Config.setUserSign("");
                    Config.setUserId("");
                    Config.setLoginName("");

                    Config.setUserBind(false);
                    Config.setUserBindArs(false);
                } else if (((BaseModel<?>) t).getCode() == 500) {
                    onNetNext((BaseModel<?>) t);
                }
            }
        }
    }

    @Override
    public void onError(Throwable e) {
        LogUtil.getInstance().e(e.toString());
    }

    @Override
    public void onComplete() {
    }

    public abstract void onNetNext(BaseModel model);
}
