package com.example.pytest.app.base;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.example.pytest.app.repository.Config;
import com.example.pytest.app.util.Util;
import com.example.pytest.app.widget.loadingdialog.LoadingDialog;
import com.example.pytest.app.widget.toast.ToastView;



/**
 * 用于懒加载Fragment
 */
public abstract class BaseFragments<P extends BasePresenter> extends Fragment {
    protected P mvpPresenter;
    private LoadingDialog dialogLoad = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mvpPresenter = createPresenter();
        dialogLoad = new LoadingDialog.Builder(getActivity()).setLayoutHeight(Util.dip2px(getActivity(), 120f))
                .setLayoutWidth(Util.dip2px(getActivity(), 120f))
                .setMessage("网络加载中...").setMessageSize(10)
                .setShowInfo(false)
                .setCancelable(true).create();
    }

    protected abstract P createPresenter();

    @Override
    public void onStart() {
        super.onStart();
    }

    public void toastShow(int resId) {
        ToastView.getToastView().toastShow(BaseApplication.get(), null, resId);
    }

    public void toastShow(String resId) {
        ToastView.getToastView().toastShow(BaseApplication.get(), null, resId);
    }

    public void showLoading() {
        if (dialogLoad != null) {
            dialogLoad.show();
        }
    }

    public void hideLoading() {
        if (dialogLoad != null && dialogLoad.isShowing() && getActivity() != null) {
            dialogLoad.dismiss();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    /*     boolean agreed = Config.getUserService();
       if (agreed) {
            MobclickAgent.onPageStart(this.getClass().getSimpleName()); // 统计页面
        }*/
    }

    @Override
    public void onPause() {
        super.onPause();
    /*     boolean agreed = Config.getUserService();
       if (agreed) {
            MobclickAgent.onPageEnd(this.getClass().getSimpleName()); // 统计页面
        }*/
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}


