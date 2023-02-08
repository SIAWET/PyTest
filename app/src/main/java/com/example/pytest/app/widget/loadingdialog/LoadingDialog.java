package com.example.pytest.app.widget.loadingdialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pytest.R;


/**
 * Created by tjy on 2017/6/19.
 */
public class LoadingDialog extends Dialog {

    public LoadingDialog(Context context) {
        super(context);
    }

    public LoadingDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public static class Builder{

        private Context context;
        private String message;
        private String info;
        private int gravity=Gravity.CENTER;
        private boolean isShowMessage=true;
        private boolean isShowInfo=true;
        private boolean isCancelable=false;
        private boolean isCancelOutside=false;
        private int size=8;
        private int mWidth=120;
        private int mHeight=120;


        public Builder(Context context) {
            this.context = context;
        }

        /**
         * 设置提示信息
         * @param message
         * @return
         */

        public Builder setMessage(String message){
            this.message=message;
            return this;
        }

        public Builder setInfo(String info){
            this.info=info;
            return this;
        }

        public Builder setMessageGravity(int gravity){
            this.gravity=gravity;
            return this;
        }

        public Builder setMessageSize(int size){
            this.size=size;
            return this;
        }

        public Builder setLayoutWidth(int size){
            this.mWidth=size;
            return this;
        }

        public Builder setLayoutHeight(int size){
            this.mHeight=size;
            return this;
        }

        /**
         * 设置是否显示提示信息
         * @param isShowMessage
         * @return
         */
        public Builder setShowMessage(boolean isShowMessage){
            this.isShowMessage=isShowMessage;
            return this;
        }


        public Builder setShowInfo(boolean isShowInfo){
            this.isShowInfo=isShowInfo;
            return this;
        }

        /**
         * 设置是否可以按返回键取消
         * @param isCancelable
         * @return
         */

        public Builder setCancelable(boolean isCancelable){
            this.isCancelable=isCancelable;
            return this;
        }

        /**
         * 设置是否可以取消
         * @param isCancelOutside
         * @return
         */
        public Builder setCancelOutside(boolean isCancelOutside){
            this.isCancelOutside=isCancelOutside;
            return this;
        }

        public LoadingDialog create(){
            LayoutInflater inflater = LayoutInflater.from(context);
            View view=inflater.inflate(R.layout.dialog_loading,null);
            LoadingDialog loadingDailog=new LoadingDialog(context,R.style.MyDialogStyle);
            TextView msgText= (TextView) view.findViewById(R.id.tipTextView);
            TextView msgTextTwo= (TextView) view.findViewById(R.id.tipTextViewTwo);
            LinearLayout layout= (LinearLayout) view.findViewById(R.id.layout);
            LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams)
                    layout.getLayoutParams();
            linearParams.width = mWidth;// 控件的宽强制设成30
            linearParams.height = mHeight;// 控件的宽强制设成30
            layout.setLayoutParams(linearParams); //使设置好的布局参数应用到控件

            if(isShowMessage){
                msgText.setVisibility(View.VISIBLE);
                msgText.setText(message);
                msgText.setTextSize(size);
                msgText.setGravity(gravity);
            }else{
                msgText.setVisibility(View.GONE);
            }
            if(isShowInfo){
                msgTextTwo.setVisibility(View.VISIBLE);
                msgTextTwo.setText(info);
                msgTextTwo.setTextSize(size);
            }else {
                msgTextTwo.setVisibility(View.GONE);
            }
            loadingDailog.setContentView(view);
            loadingDailog.setCancelable(isCancelable);
            loadingDailog.setCanceledOnTouchOutside(isCancelOutside);
            return  loadingDailog;
        }


    }
}
