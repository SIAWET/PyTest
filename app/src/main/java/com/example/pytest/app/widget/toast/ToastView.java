package com.example.pytest.app.widget.toast;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pytest.R;
import com.example.pytest.app.util.Util;


/**
 * Toast view
 * 单例Toast:
 * 有两个地方弹吐司,A地方弹出之后,又马上切换到B地方,没有使用单例的话,那么当弹起第一个吐司的时候,时间就会有2-3S的间隔才会弹出第二个吐司.那么给用户的体验就会非常不好.
 */

public class ToastView {
    public static ToastView mToastView;
    private Toast toast;

    private ToastView() {
    }

    public static ToastView getToastView() {
        if (mToastView == null) {
            mToastView = new ToastView();
        }
        return mToastView;
    }

    public void toastShow(Context context, ViewGroup root, int str) {
        if (context == null) {
            return;
        }
        //Inflater意思是充气
        //LayoutInflater这个类用来实例化XML文件到其相应的视图对象的布局
        View view = LayoutInflater.from(context).inflate(R.layout.view_toast_layout, root);
        TextView text = view.findViewById(R.id.textToast);
        text.setText(context.getResources().getString(str));
        toast = new Toast(context);
        toast.setGravity(Gravity.CENTER, 0, Util.getHeightPixels(context) / 4); //SplashActivity.height/3
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }

    public void toastShow(Context context, ViewGroup root, String str) {
        try {
            if (context == null) {
                return;
            }
            View view = LayoutInflater.from(context).inflate(R.layout.view_toast_layout, root);
            TextView text = view.findViewById(R.id.textToast);
            text.setText(str);
            toast = new Toast(context);
            toast.setGravity(Gravity.CENTER, 0, Util.getHeightPixels(context) / 4);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(view);
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void toastShowWithIcon(Context context, ViewGroup root, String str) {
        try {
            if (context == null) {
                return;
            }
            View view = LayoutInflater.from(context).inflate(R.layout.view_toast_icon_layout, root);
            TextView text = view.findViewById(R.id.textToast);
            text.setText(str);
            toast = new Toast(context);
            toast.setGravity(Gravity.CENTER, 0, Util.getHeightPixels(context) / 3);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(view);
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void toastCancel() {
        if (toast != null) {
            toast.cancel();
        }
    }
}



