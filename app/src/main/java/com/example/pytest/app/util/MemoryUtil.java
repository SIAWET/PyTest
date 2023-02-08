package com.example.pytest.app.util;

import android.app.Activity;
import android.content.Context;
import android.os.IBinder;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * memory
 */

public class MemoryUtil {

    //这个方法会出现后台异常报错
    public static void releaseInputMethodManagerFocus(Activity paramActivity) {
        if (paramActivity == null) return;
        int count = 0;
        while (true) {
            //给个5次机会 省得无限循环
            count++;
            if (count == 5) return;
            try {
                InputMethodManager localInputMethodManager = (InputMethodManager)
                        paramActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (localInputMethodManager != null) {
                    Method localMethod = InputMethodManager.class.getMethod("windowDismissed", new Class[]{IBinder.class});
                    if (localMethod != null) {
                        localMethod.invoke(localInputMethodManager, new Object[]{paramActivity.getWindow().getDecorView().getWindowToken()});
                    }
                    Field mLastSrvView = InputMethodManager.class.getDeclaredField("mLastSrvView");
                    if (mLastSrvView != null) {
                        mLastSrvView.setAccessible(true);
                        mLastSrvView.set(localInputMethodManager, null);
                        return;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 解决输入法内存泄漏
     *
     * @param destContext
     */
    public static void fixInputMethodManagerLeak(Context destContext) {
        if (destContext == null) {
            return;
        }

        InputMethodManager inputMethodManager = (InputMethodManager) destContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager == null) {
            return;
        }
        String[] viewArray = new String[]{"mCurRootView", "mServedView", "mNextServedView"}; //"mLastSrvView",
        Field filed;
        Object filedObject;

        for (String view : viewArray) {
            try {
                filed = inputMethodManager.getClass().getDeclaredField(view);
                if (!filed.isAccessible()) {
                    filed.setAccessible(true);
                }
                filedObject = filed.get(inputMethodManager);
                if (filedObject != null && filedObject instanceof View) {
                    View fileView = (View) filedObject;
                    if (fileView.getContext() == destContext) { // 被InputMethodManager持有引用的context是想要目标销毁的
                        // 置空，破坏掉path to gc节点
                        filed.set(inputMethodManager, null);
                    } else {
                        // 不是想要目标销毁的，即为又进了另一层界面了，不要处理，避免影响原逻辑,也就不用继续for循环了
                        break;
                    }
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }


    //此处专门解决HW手机泄漏 https://www.jianshu.com/p/95242060320f
    private static Field field;
    private static boolean hasField = true;
    public static void fixLeak(Context context) {
        if (!hasField) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }
        String[] arr = new String[]{"mLastSrvView"};
        for (String param : arr) {
            try {
                if (field == null) {
                    field = imm.getClass().getDeclaredField(param);
                }
                if (field == null) {
                    hasField = false;
                }
                if (field != null) {
                    field.setAccessible(true);
                    field.set(imm, null);
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }

    static void fixLeakCanary696(Context context) {
        /*if (!isEmui()) {
            Log.w(TAG, "not emui");
            return;
        }*/
        try {
            Class clazz = Class.forName("android.gestureboost.GestureBoostManager");

            Field _sGestureBoostManager = clazz.getDeclaredField("sGestureBoostManager");
            _sGestureBoostManager.setAccessible(true);
            Field _mContext = clazz.getDeclaredField("mContext");
            _mContext.setAccessible(true);

            Object sGestureBoostManager = _sGestureBoostManager.get(null);
            if (sGestureBoostManager != null) {
                _mContext.set(sGestureBoostManager, context);
            }
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }

    }


}