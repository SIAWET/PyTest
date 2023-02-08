package com.example.pytest.app.test;


import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.pytest.R;
import com.example.pytest.app.util.LogUtil;

import java.util.ArrayList;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.ViewHolder> {
    private Context context;
    private ArrayList<TestBean> list;
    public OnTestItemClickListener listener;
    private LogUtil log = LogUtil.getInstance();

    public TestAdapter(Context mContext) {
        this.context = mContext;
    }

    public interface OnTestItemClickListener {
        void onTestItem(int position);

        void onDelTestBean(int position);
    }

    public void setListener(OnTestItemClickListener listener) {
        this.listener = listener;
    }

    public void setData(ArrayList<TestBean> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_test_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        TestBean bean = list.get(position);
        if (!TextUtils.isEmpty(bean.getTitle())) {
            holder.title.setText(bean.getTitle());
        }
        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDelTestBean(holder.getAdapterPosition());
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onTestItem(holder.getAdapterPosition());
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView del;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.item_title);
            del = itemView.findViewById(R.id.item_deleted);
        }
    }

}
