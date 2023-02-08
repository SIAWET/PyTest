package com.example.pytest.app.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.pytest.app.repository.Config;
import com.example.pytest.app.util.MemoryUtil;
import com.example.pytest.app.util.OSHelper;
import com.example.pytest.app.util.Util;
import com.example.pytest.app.util.statusbar.StatusBarUtil;
import com.example.pytest.app.widget.loadingdialog.LoadingDialog;
import com.example.pytest.app.widget.toast.ToastView;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;


/**
 *
 */

public abstract class BaseActivity<P extends BasePresenter> extends RxAppCompatActivity {
    public Activity mActivity;
    protected P mvpPresenter;
    private LoadingDialog dialogLoad = null;
    private final int mRequestCode = 1024;
    public RequestPermissionCallBack mRequestPermissionCallBack;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        //StatusBarUtil.setStatusBar(BaseActivity.this);
        //StatusBarUtil.StatusBarLightMode(BaseActivity.this, false);
//        StatusBarUtil.StatusBarLightMode(BaseActivity.this, true);
        mActivity = this;
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        mActivity = this;
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        mActivity = this;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mvpPresenter = createPresenter();

        dialogLoad = new LoadingDialog.Builder(this).setLayoutHeight(Util.dip2px(this, 120f))
                .setLayoutWidth(Util.dip2px(this, 120f))
                .setMessage("网络加载中...").setMessageSize(10)
                .setShowInfo(false)
                .setCancelable(true).create();

    }

    protected abstract P createPresenter();

    public void showLoading() {
        if (dialogLoad != null) {
            dialogLoad.show();
        }
    }

    public void hideLoading() {
        if (dialogLoad != null && dialogLoad.isShowing()) {
            dialogLoad.dismiss();
        }
    }

    public void toastShow(int resId) {
        ToastView.getToastView().toastShow(BaseApplication.get(), null, resId);
    }

    public void toastShow(String resId) {
        ToastView.getToastView().toastShow(BaseApplication.get(), null, resId);
    }

    @Override
    protected void onResume() {
        super.onResume();
     /*    boolean agreed = Config.getUserService();
      if (agreed) {
            MobclickAgent.onResume(this);
        }*/
    }

    @Override
    public void onPause() {
        super.onPause();
    /*    boolean agreed = Config.getUserService();
        if (agreed) {
            MobclickAgent.onPause(this);
        }*/
    }

    /**
     * 权限请求结果回调接口
     */
    public interface RequestPermissionCallBack {
        /**
         * 同意授权
         */
        void granted();

        /**
         * 取消授权
         */
        void denied();
    }

    public void requestPermissions(final Context context, final String[] permissions, RequestPermissionCallBack callback) {
        this.mRequestPermissionCallBack = callback;
        StringBuilder permissionNames = new StringBuilder();
        for (String s : permissions) {
            permissionNames = permissionNames.append(s + "\r\n");
        }
        //如果所有权限都已授权，则直接返回授权成功,只要有一项未授权，则发起权限请求
        boolean isAllGranted = true;
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED) {
                isAllGranted = false;
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, permission)) {
                    new AlertDialog.Builder(BaseActivity.this).setTitle("权限")//设置对话框标题
                            .setMessage("【用户曾经拒绝过你的请求，所以这次发起请求时解释一下】" +
                                    "您好，需要如下权限：" + permissionNames +
                                    " 请允许，否则将影响部分功能的正常使用。")//设置显示的内容
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
                                @Override
                                public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                                    ActivityCompat.requestPermissions(((Activity) context), permissions, mRequestCode);
                                }
                            }).show();//在按键响应事件中显示此对话框
                } else {
                    ActivityCompat.requestPermissions(((Activity) context), permissions, mRequestCode);
                }
                break;
            }
        }
        if (isAllGranted) {
            mRequestPermissionCallBack.granted();
            return;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean hasAllGranted = true;
        StringBuilder permissionName = new StringBuilder();
        for (String s : permissions) {
            permissionName = permissionName.append(s + "\r\n");
        }
        switch (requestCode) {
            case mRequestCode: {
                for (int i = 0; i < grantResults.length; ++i) {
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        hasAllGranted = false;
                        //在用户已经拒绝授权的情况下，如果shouldShowRequestPermissionRationale返回false则
                        // 可以推断出用户选择了“不在提示”选项，在这种情况下需要引导用户至设置页手动授权
                        if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) {
                            new AlertDialog.Builder(BaseActivity.this).setTitle("PermissionTest")//设置对话框标题
                                    .setMessage("【用户选择了不再提示按钮，或者系统默认不在提示（如MIUI）。" +
                                            "引导用户到应用设置页去手动授权,注意提示用户具体需要哪些权限】" +
                                            "获取相关权限失败:" + permissionName +
                                            "将导致部分功能无法正常使用，需要到设置页面手动授权")//设置显示的内容
                                    .setPositiveButton("去授权", new DialogInterface.OnClickListener() {//添加确定按钮
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                            Uri uri = Uri.fromParts("package", getApplicationContext().getPackageName(), null);
                                            intent.setData(uri);
                                            startActivity(intent);
                                            dialog.dismiss();
                                        }
                                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加返回按钮
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {//响应事件
                                            dialog.dismiss();
                                        }
                                    }).setOnCancelListener(new DialogInterface.OnCancelListener() {
                                        @Override
                                        public void onCancel(DialogInterface dialog) {
                                            mRequestPermissionCallBack.denied();
                                        }
                                    }).show();//在按键响应事件中显示此对话框
                        } else {
                            //用户拒绝权限请求，但未选中“不再提示”选项
                            mRequestPermissionCallBack.denied();
                        }
                        break;
                    }
                }
                if (hasAllGranted) {
                    mRequestPermissionCallBack.granted();
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (OSHelper.getSystem().equals(OSHelper.SYS_EMUI)) //处理HW特有内存问题
            MemoryUtil.fixLeak(BaseApplication.get());
        super.onDestroy();
    }
}
