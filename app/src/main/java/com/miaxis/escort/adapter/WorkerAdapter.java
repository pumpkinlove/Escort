package com.miaxis.escort.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.miaxis.escort.R;
import com.miaxis.escort.model.entity.WorkerBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 一非 on 2018/4/12.
 */

public class WorkerAdapter extends RecyclerView.Adapter<WorkerAdapter.MyViewHolder> {

    private List<WorkerBean> dataList;

    private LayoutInflater layoutInflater;
    private OnItemClickListener mOnItemClickListener;

    public WorkerAdapter(Context context, List<WorkerBean> dataList) {
        this.dataList = dataList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.item_worker, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        WorkerBean workerBean = dataList.get(position);
        holder.tvWorkerName.setText(workerBean.getName());
        holder.tvWorkerCode.setText(workerBean.getWorkno());
        holder.tvWorkerPhone.setText(workerBean.getPhone());
        holder.tvWorkerStatus.setText(workerBean.getStatus());
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

    public void setDataList(List<WorkerBean> workerBeanList) {
        this.dataList = workerBeanList;
    }

    public List<WorkerBean> getDataList() {
        return dataList;
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_worker_name)
        TextView tvWorkerName;
        @BindView(R.id.tv_worker_code)
        TextView tvWorkerCode;
        @BindView(R.id.tv_worker_phone)
        TextView tvWorkerPhone;
        @BindView(R.id.tv_worker_status)
        TextView tvWorkerStatus;

        MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

}
