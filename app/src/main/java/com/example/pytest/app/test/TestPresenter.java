package com.example.pytest.app.test;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.example.pytest.R;
import com.example.pytest.app.base.BasePresenter;
import com.example.pytest.app.util.LogUtil;
import com.example.pytest.app.widget.dialog.CustomerDialog;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.List;

public class TestPresenter extends BasePresenter {

    private final TestView view;

    private LogUtil log = LogUtil.getInstance();
    private CustomerDialog dialog;

    public TestPresenter(TestView view, LifecycleProvider<ActivityEvent> provider) {
        super(provider);
        this.view = view;

    }

    public TestPresenter(TestView view) {
        this.view = view;
    }

    /*      log.e(backInt(1 | 3) + "siaww");
            log.e(backInt(100 | 3) + "siaww2");
            log.e(backInt(100 | 102 | 101) + "siaww2");
            log.e(backInt(7 | 3) + "siaww2");
            log.e(backInt(7 | 3 | 2) + "siaww2");
            log.e(backInt(7 | 3 | 5) + "siaww2");
            log.e(backInt(1 | 1) + "siaww3");
            log.e(backInt(2 | 3) + "siaww3");
            log.e(backInt(4 | 3) + "siaww3");
            log.e(backInt(4 | 3) + "siaww3");
            */
    private int backInt(int a) {
        return a;
    }

    public void showExitDialog(Activity activity, int position) {
        if (dialog == null) {
            dialog = new CustomerDialog(activity, R.style.customDialog, R.layout.dialog_draft_confirm_info);
        }
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        TextView tvOk = dialog.findViewById(R.id.ok);
        TextView content = dialog.findViewById(R.id.content);
        content.setText("确认删除？");
        TextView cancel = dialog.findViewById(R.id.cancel);
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                view.delDraft(position);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}
