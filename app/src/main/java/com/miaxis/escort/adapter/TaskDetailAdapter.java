package com.miaxis.escort.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.miaxis.escort.R;
import com.miaxis.escort.app.EscortApp;
import com.miaxis.escort.model.entity.BoxBean;
import com.miaxis.escort.model.entity.TaskBoxBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 一非 on 2018/4/13.
 */

public class TaskDetailAdapter extends RecyclerView.Adapter<TaskDetailAdapter.MyViewHolder> {

    private List<TaskBoxBean> dataList;

    private LayoutInflater layoutInflater;
    private OnItemClickListener mOnItemClickListener;

    public TaskDetailAdapter(Context context, List<TaskBoxBean> dataList) {
        this.dataList = dataList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.item_task_detail, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        TaskBoxBean taskBoxBean = dataList.get(position);
        taskBoxBean.__setDaoSession(EscortApp.getInstance().getDaoSession());
        holder.tvTaskDetail.setText(taskBoxBean.getBox().getBoxname());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(holder.itemView, holder.getLayoutPosition());
            }
        });
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_task_detail)
        TextView tvTaskDetail;

        MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

}
