package com.miaxis.escort.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.miaxis.escort.R;
import com.miaxis.escort.app.EscortApp;
import com.miaxis.escort.model.entity.TaskBean;
import com.miaxis.escort.util.StaticVariable;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 一非 on 2018/4/12.
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.MyViewHolder> {

    private List<TaskBean> dataList;

    private LayoutInflater layoutInflater;
    private OnItemClickListener mOnItemClickListener;

    public TaskAdapter(Context context, List<TaskBean> dataList) {
        this.dataList = dataList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.item_task, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        TaskBean taskBean = dataList.get(position);
        taskBean.__setDaoSession(EscortApp.getInstance().getDaoSession());
        holder.tvTaskType.setText(StaticVariable.getTasktypeName(taskBean.getTasktype(), taskBean.getTasklevel()));
        holder.tvTaskCarId.setText(taskBean.getCarid());
        holder.tvTaskBoxCount.setText("" + taskBean.getBoxList().size());
        holder.tvTaskDate.setText(taskBean.getOpdate());
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

    public List<TaskBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<TaskBean> dataList) {
        this.dataList = dataList;
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_task_type)
        TextView tvTaskType;
        @BindView(R.id.tv_task_car_id)
        TextView tvTaskCarId;
        @BindView(R.id.tv_task_box_count)
        TextView tvTaskBoxCount;
        @BindView(R.id.tv_task_date)
        TextView tvTaskDate;

        MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

}