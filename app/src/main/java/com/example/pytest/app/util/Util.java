package com.example.pytest.app.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;


import com.example.pytest.app.base.BaseApplication;
import com.example.pytest.app.repository.Config;
import com.example.pytest.app.util.statusbar.SystemBarTintManager;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InvalidClassException;
import java.io.LineNumberReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * common util
 */
public class Util {
    private static final int DEFAULT_READING_SIZE = 8192;

    public static String parseMoney(String tag) {
        DecimalFormat df = new DecimalFormat("#####0.00");
        if (TextUtils.isEmpty(tag))
            return "0.00";
        try {
            return df.format(Double.valueOf(tag));
        } catch (NumberFormatException e) {
            return "0.00";
        }
    }

    /**
     * ???????????????
     */
    public static void closeKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) BaseApplication.get().getSystemService(Context.INPUT_METHOD_SERVICE);
        // ??????InputMethodManager?????????
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);  //activity.getCurrentFocus().getWindowToken()
        }
    }

    /**
     * ???????????????
     */
    public static void closeKeyboard(Activity activity) {
        try {
            if (activity != null) {
                InputMethodManager imm = (InputMethodManager) BaseApplication.get().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm.isActive()) {
                    imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ????????????
    public static void clearAllCache(Context context) {
        deleteDir(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            deleteDir(context.getExternalCacheDir());
        }
    }

    private static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        if (dir != null) {
            return dir.delete();
        } else {
            return false;
        }

    }

    public static void RecursionDeleteFile(File file) {
        if (file.isFile()) {
            file.delete();
            return;
        }
        if (file.isDirectory()) {
            File[] childFile = file.listFiles();
            if (childFile == null || childFile.length == 0) {
                file.delete();
                return;
            }
            for (File f : childFile) {
                RecursionDeleteFile(f);
            }
            file.delete();
        }
    }

    // ????????????????????????
    public static String getTotalCacheSize(Context context) throws Exception {
        long cacheSize = getFolderSize(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheSize += getFolderSize(context.getExternalCacheDir());
        }
        return getFormatSize(cacheSize);
    }

    public static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            // return size + "Byte";
            return "0K";
        }
        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }
        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }
        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }

    // ?????????????????????
    public static String getMobileVersion() {
        // =5.1 / ASUS_Z00VD / 22
        return Build.VERSION.RELEASE + " / " + Build.MODEL + " / " + Build.VERSION.SDK;
    }

    // ??????????????????
    public static int getDeviceSDK() {
        //22
        return Build.VERSION.SDK_INT;
    }

    // ??????????????????
    public static String getMobileBusiness() {
        return Build.MANUFACTURER + " " + Build.MODEL;
    }

    // ????????????ID
    public static String getDeviceID() {
        TelephonyManager tm = (TelephonyManager) BaseApplication.get().getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = "";// tm.getDeviceId();
        return deviceId;
    }

    // ??????mac??????
    public static String getMac() {
        String macSerial = null;
        String str = "";
        try {
            Process pp = Runtime.getRuntime().exec("cat /sys/class/net/wlan0/address ");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            for (; null != str; ) {
                str = input.readLine();
                if (str != null) {
                    macSerial = str.trim();// ?????????
                    break;
                }
            }
        } catch (IOException ex) {
            // ???????????????
            ex.printStackTrace();
        }
        return macSerial;
    }

    // ??????????????????
    public static String getVersionName(Context context) {
        if (context == null) {
            return "0.0.0";
        }
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packInfo.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return "0.0.0";
        }
    }


    // ????????????view????????? bitmap??????
    public static Bitmap convertViewToBitmap(View view) {
        view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }

    /**
     * ????????????
     */
    public static boolean saveObject(Context context, Object object, String name) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = context.openFileOutput(name, Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(object);
            oos.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                oos.close();
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * ????????????
     */
    public static Object readObject(Context context, String name) {
        if (!isExistDataCache(context, name))
            return null;
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        Object object = null;
        try {
            fis = context.openFileInput(name);
            ois = new ObjectInputStream(fis);
            object = (Serializable) ois.readObject();
            return object;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            // ?????????????????? - ??????????????????
            if (e instanceof InvalidClassException) {
                File data = context.getFileStreamPath(name);
                data.delete();
            }
        } finally {
            try {
                ois.close();
                fis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return object;
    }

    /**
     * ????????????????????????
     */
    public static boolean isExistDataCache(Context context, String cachefile) {
        boolean exist = false;
        File data = context.getFileStreamPath(cachefile);
        if (data.exists())
            exist = true;
        return exist;
    }

    /**
     * ????????????
     *
     * @param context
     * @param cache
     */
    public static void clearDataCache(Context context, String cache) {
        File data = context.getFileStreamPath(cache);
        if (data.exists()) {
            data.delete();
        }
    }

    /**
     * ??????????????????????????? dp ????????? ????????? px(??????)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    // ?????????????????????????????????
    public static boolean isNumeric(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    //????????????????????????
    public static boolean isNumber(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        String reg = "\\d+(\\.\\d+)?";
        return str.matches(reg);
    }

    // ??????????????????
    private static long lastClickTime;

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long time2 = time - lastClickTime;
        if (time2 > 0 && time2 < 1000L) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    // ?????????????????????
    public static void setStatusBarColor(Activity context, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true, context);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(context);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(color);
    }

    @TargetApi(19)
    private static void setTranslucentStatus(boolean on, Activity context) {
        Window win = context.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    //???????????????????????????
    public static boolean isInMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    //????????????????????????????????????
    public static int getSDKVersionNumber() {
        int sdkVersion;
        try {
            sdkVersion = Integer.valueOf(Build.VERSION.SDK);
        } catch (NumberFormatException e) {
            sdkVersion = 0;
        }
        return sdkVersion;
    }

    /**
     * ?????????????????????????????????????????????????????????
     * ???????????????????????????????????????????????????????????????????????????????????????
     *
     * @return true ????????????
     */
    public static Boolean notHasLightSensorManager(Context context) {
        SensorManager sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor8 = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT); //???
        if (null == sensor8) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * ???????????????????????????????????????????????????
     *
     * @return true ????????????
     */
    public static boolean notHasBlueTooth() {
        BluetoothAdapter ba = BluetoothAdapter.getDefaultAdapter();
        if (ba == null) {
            return true;
        } else {
            // ??????????????????????????????????????????????????????????????????null ?????????????????????
            String name = ba.getName();
            if (TextUtils.isEmpty(name)) {
                return true;
            } else {
                return false;
            }
        }
    }

    public static String readCpuInfo() {
        String result = "";
        try {
            String[] args = {"/system/bin/cat", "/proc/cpuinfo"};
            ProcessBuilder cmd = new ProcessBuilder(args);

            Process process = cmd.start();
            StringBuffer sb = new StringBuffer();
            String readLine = "";
            BufferedReader responseReader = new BufferedReader(new InputStreamReader(process.getInputStream(), "utf-8"));
            while ((readLine = responseReader.readLine()) != null) {
                sb.append(readLine);
            }
            responseReader.close();
            result = sb.toString().toLowerCase();
        } catch (IOException ex) {
        }
        return result;
    }

    /**
     * ??????cpu???????????????????????? ?????????
     *
     * @return true ????????????
     */
    public static boolean checkIsNotRealPhone() {
        String cpuInfo = readCpuInfo();
        if ((cpuInfo.contains("intel") || cpuInfo.contains("amd"))) {
            return true;
        }
        return false;
    }

    /**
     * ?????????????????????
     *
     * @return
     */
    public static String getBuildModel() {
        return Build.MODEL;
    }

    /**
     * ?????????????????????
     *
     * @return
     */
    public static String getBuildMANUFACTURER() {
        return Build.MANUFACTURER;
    }


    /**
     * ????????????????????????,??????????????????????????????,???????????????????????????,???????????????0
     * version1 app????????????
     * version2 ?????????????????????
     */
    public static int compareVersion(String version1, String version2) throws Exception {
        if (version1 == null || version2 == null) {
            throw new Exception("compareVersion error:illegal params.");
        }
        String[] versionArray1 = version1.split("\\.");//???????????????????????????????????????"."???
        String[] versionArray2 = version2.split("\\.");
        int idx = 0;
        int minLength = Math.min(versionArray1.length, versionArray2.length);//??????????????????
        int diff = 0;
        while (idx < minLength
                && (diff = versionArray1[idx].length() - versionArray2[idx].length()) == 0//???????????????
                && (diff = versionArray1[idx].compareTo(versionArray2[idx])) == 0) {//???????????????
            ++idx;
        }
        //??????????????????????????????????????????????????????????????????????????????????????????????????????????????????
        diff = (diff != 0) ? diff : versionArray1.length - versionArray2.length;
        return diff;
    }

    /**
     * ????????????????????????
     * version1 ????????????  version2 ???????????????
     * 0 ????????? 1 ?????????  2 ?????????
     */
    public static int compareVersion2(String version1, String version2) throws Exception {
        if (version1 == null || version2 == null) {
            throw new Exception("compareVersion error:illegal params.");
        }
        if (compareVersion(version1, version2) < 0) {
            String[] versionArray1 = version1.split("\\.");//???????????????????????????????????????"."???
            String[] versionArray2 = version2.split("\\.");
            if (Integer.parseInt(versionArray1[0]) == Integer.parseInt(versionArray2[0]) && Integer.parseInt(versionArray1[1]) == Integer.parseInt(versionArray2[1])) {
                return 1;
            } else {
                return 2;
            }
        }
        return 0;
    }

    /**
     * ??????app?????????????????????application????????????meta-data
     *
     * @return ????????????????????????(??????????????? ??? ????????????)?????????????????????
     */
    public static String getAppMetaData(Context context, String key) {
        if (context == null || TextUtils.isEmpty(key)) {
            return null;
        }
        String channelNumber = null;
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager != null) {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(),
                        PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        channelNumber = applicationInfo.metaData.getString(key);
                    }
                }
            }
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return channelNumber;
    }

    /**
     * ????????????????????????
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isAppInstallen(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();
        boolean installed = false;
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            installed = true;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            installed = false;
        }
        return installed;

    }

    /**
     * ????????????????????????????????????
     *
     * @return
     */
//    public static String appDebug(Context context) {
//        String url = ApiStores.API_SERVER_URL;
//        if (url.contains("buytoken")) {
//            return "????????????-" + getVersionName(context);
//        } else if (url.contains("myhero9")) {
//            return "";
//        }
//        return null;
//    }

    /**
     * ????????????????????????
     *
     * @return
     */
//    public static boolean isNetworkConnected(Context context) {
//        if (context != null) {
//            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
//                    .getSystemService(Context.CONNECTIVITY_SERVICE);
//            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
//            if (mNetworkInfo != null) {
//                return mNetworkInfo.isAvailable();
//            }
//        }
//        return false;
//    }


    /**
     * ??????????????????
     *
     * @return
     */
    public static int getHeightPixels(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int height = dm.heightPixels;       // ????????????????????????
        return height;
    }

    /**
     * ??????????????????
     *
     * @return
     */
    public static int getWidthPixels(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;         // ????????????????????????
        return width;
    }

    public static Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // ???????????????????????????100????????????????????????????????????????????????baos???
        image.compress(Bitmap.CompressFormat.JPEG, 80, baos);
        // ?????????????????????????????????????????????100kb,??????????????????
        if (baos.toByteArray().length / 1024 > 1024) {
            baos.reset();
            image.compress(Bitmap.CompressFormat.JPEG, 80, baos);
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        float hh = 800f; //800f;
        float ww = 480f; //480f
        int be = 1;
        if (w > h && w > ww) {
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;
        isBm = new ByteArrayInputStream(baos.toByteArray());
        bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        return compressIm(bitmap);
    }

    private static Bitmap compressIm(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 80, baos);
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) {
            baos.reset();
            options -= 10;
            if (options < 0 || options > 100) {
                break;
            }
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
        return bitmap;
    }

    public static String formatTosepara(float data) {
        DecimalFormat df = new DecimalFormat("#,###.00");
        return df.format(data);
    }

    public static boolean isServiceRunning(String className) {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager) BaseApplication.get().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager.getRunningServices(Integer.MAX_VALUE);
        if (!(serviceList.size() > 0)) {
            return false;
        }
        for (int i = 0; i < serviceList.size(); i++) {
            if (serviceList.get(i).service.getClassName().equals(className) == true) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }

    //??????????????????????????????
    public static boolean isBlackGame(String data) {
        for (int i = 0; i < Config.blackGame.length; i++) {
            if (data.toUpperCase().contains(Config.blackGame[i])) {
                return true;
            }
        }
        return false;
    }

    //?????????????????? 100+
    public static String getDealNum(String data) {
        if (data.length() >= 3) {
            long num = Long.parseLong(data);
            for (int i = 0; i < data.length() - 1; i++) {
                num = num / 10;
            }
            return num * (int) Math.pow(10, data.length() - 1) + "+";
        } else {
            return data;
        }
    }

    public static String getDealNumDefault(String data) {
        BigDecimal data1 = new BigDecimal(data);
        if (data1.compareTo(new BigDecimal(1000)) > 0) {
            return "1000+";
        } else if (data1.compareTo(new BigDecimal(500)) > 0) {
            return "500+";
        } else if (data1.compareTo(new BigDecimal(100)) > 0) {
            return "100+";
        } else if (data1.compareTo(new BigDecimal(50)) > 0) {
            return "50+";
        } else {
            return data;
        }

    }

    public static Map<String, Object> objectToMap(Object obj) throws Exception {
        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            if (field.get(obj) != null)
                map.put(field.getName(), field.get(obj));
        }
        return map;
    }

    /**
     * ????????????????????????
     *
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }


    /**
     * ???????????????????????????debug??????
     */
    public static boolean isApkInDebug(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            return false;
        }
    }

    public static List<String> getStrKey(String master, String startSign, String endSign, List<String> srcLst) {
        int start = master.indexOf(startSign) + startSign.length();
        int end = master.indexOf(endSign, start);
        if (start < startSign.length() || end < 0 || start > end) {
            return srcLst;
        }
        String src = master.substring(start, end);
        srcLst.add(src);
        master = master.substring(end + 1);
        return getStrKey(master, startSign, endSign, srcLst);
    }
}
