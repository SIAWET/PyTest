package com.example.pytest.app.util.statusbar;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;

/**
 * Android4.4（API19 KitKat）以前：无法做任何事，是的，就是一坨黑色。
 * Android4.4～Android5.0（API21 Lollipop）：可以实现状态栏的变色，但是效果还不是很好，
 * 主要实现方式是通过FLAG_TRANSLUCENT_STATUS这个属性设置状态栏为透明并且为全屏模式，然后通过添加一个与StatusBar 一样大小的View，将View 设置为我们想要的颜色，从而来实现状态栏变色。
 * Android5.0～Android6.0（API23 Marshmallow）: 系统才真正的支持状态栏变色，
 * 系统加入了一个重要的属性和方法 android:statusBarColor （对应方法为 setStatusBarColor），通过这个属性可以直接设置状态栏的颜色
 * Android6.0以后：主要就是添加了一个功能可以修改状态栏上内容和图标的颜色（黑色和白色）
 */
public class StatusBarUtilSiawet {

    /**
     * 修改状态栏为全透明 基础
     * 4.4~5.0
     *
     * @param activity Activity
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void setTransparencyBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = activity.getWindow();
            //FLAG_TRANSLUCENT_STATUS这个属性实现状态栏变色，当使用这个flag时SYSTEM_UI_FLAG_LAYOUT_STABLE和SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN会被自动添加。
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN(4.1+):配合SYSTEM_UI_FLAG_FULLSCREEN一起使用，效果使得状态栏出现的时候不会挤压activity高度，状态栏会覆盖在activity之上
        }
    }

    /**
     * 更改改状态栏颜色
     * setStatusBarColor是专门用来设置状态栏颜色的，但是让这个方法生效有一个前提条件：
     * 你必须给window添加FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS并且取消FLAG_TRANSLUCENT_STATUS
     * 5.0~6.0
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void setColorfulBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);//设置为透明色
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * 更改改状态栏颜色
     * Android 系统状态栏的字色和图标颜色为白色，当我的主题色或者图片接近白色或者为浅色的时候，状态栏上的内容就看不清了
     * Android 6.0 新添加了一个属性SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
     * 6.0
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void setColorfulLightBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);//设置为透明色
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
    }

    public static void setStatusBarAndNavigationBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
            setTransparencyBar(activity);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
            setColorfulBar(activity);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setColorfulLightBar(activity);
        }
    }
}
