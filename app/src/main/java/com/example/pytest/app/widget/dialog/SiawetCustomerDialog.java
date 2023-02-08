package com.example.pytest.app.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDialog;

public class SiawetCustomerDialog extends AppCompatDialog {
    private Context context;
    private int resId;

    public SiawetCustomerDialog(Context context, int resLayout) {
        this(context, 0, 0);
    }

    public SiawetCustomerDialog(Context context, int themeResId, int resLayout) {
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