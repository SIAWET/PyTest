package com.example.pytest.app.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;


public class CustomerDialog extends Dialog {
    private Context context;
    private int resId;

    public CustomerDialog(Context context, int resLayout) {
        this(context, 0, 0);
    }

    public CustomerDialog(Context context, int themeResId, int resLayout) {
        super(context, themeResId);
        this.context = context;
        this.resId = resLayout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(resId);
    }
}

