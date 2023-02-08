package com.example.pytest.app.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.content.FileProvider;


import com.example.pytest.R;
import com.example.pytest.app.base.BaseApplication;
import com.example.pytest.app.model.callback.DownloadCallBack;
import com.example.pytest.app.repository.Config;
import com.example.pytest.app.util.LogUtil;

import java.io.File;


public class DownloadIntentService extends IntentService {

    private static final String TAG = "DownloadIntentService";
    private NotificationManager mNotifyManager;
    private String mDownloadFileName;
    private Notification mNotification;
    private LogUtil log = LogUtil.getInstance();
    private String downloadUrl;
    private int downloadId;

    public DownloadIntentService() {
        super("InitializeService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        try {
            downloadUrl = intent.getStringExtra("download_url");
            downloadId = intent.getIntExtra("download_id", 0);
            mDownloadFileName = intent.getStringExtra("download_file");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (TextUtils.isEmpty(downloadUrl) || TextUtils.isEmpty(mDownloadFileName)) {
            return;
        }
        final File file = new File(Constant.APP_ROOT_PATH + Constant.DOWNLOAD_DIR + mDownloadFileName);
        long range = 0;
        int progress = 0;
        if (file.exists()) {
            if (file.length() > 0) {
                range = Config.getDownloadInfo(downloadUrl);
                progress = (int) (range * 100 / file.length());
                if (range == file.length()) {
                    installApp(file);
                    return;
                }
            } else {
                return;
            }
        }
        final RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notify_download);
        remoteViews.setProgressBar(R.id.pb_progress, 100, progress, false);
        remoteViews.setTextViewText(R.id.tv_progress, "已下载" + progress + "%");

        final NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this,"id")
                        .setContent(remoteViews)
                        .setTicker("正在下载")
                        .setSmallIcon(R.mipmap.icon_me_about);
        mNotification = builder.build();
        mNotifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotifyManager.notify(downloadId, mNotification);

        RetrofitHttp.getInstance().downloadFile(range, downloadUrl, mDownloadFileName, new DownloadCallBack() {
            @Override
            public void onProgress(int progress) {
                remoteViews.setProgressBar(R.id.pb_progress, 100, progress, false);
                remoteViews.setTextViewText(R.id.tv_progress, "已下载" + progress + "%");
                mNotifyManager.notify(downloadId, mNotification);
                final Intent intent = new Intent();
                intent.setAction("android.action.download.apk");
                intent.putExtra("progress", progress);
                sendBroadcast(intent);
            }

            @Override
            public void onCompleted() {
                log.e("下载完成");
                mNotifyManager.cancel(downloadId);
                installApp(file);
            }

            @Override
            public void onError(String msg) {
                mNotifyManager.cancel(downloadId);
                log.d("下载发生错误--" + msg);
            }
        });
    }


    // 安装apk
    private void installApp(File file) {
        //判读版本是否在7.0以上这里是7.0安装是会出现解析包的错误
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            //intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(BaseApplication.get(),
                    BaseApplication.get().getPackageName() + ".provider", file);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
            startActivity(intent);
        } else {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            startActivity(intent);
        }

    }

}
