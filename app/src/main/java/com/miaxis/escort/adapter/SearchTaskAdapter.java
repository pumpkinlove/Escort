package com.miaxis.escort.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.miaxis.escort.R;
import com.miaxis.escort.model.entity.TaskBean;
import com.miaxis.escort.util.StaticVariable;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 一非 on 2018/4/13.
 */

public class SearchTaskAdapter extends RecyclerView.Adapter<SearchTaskAdapter.MyViewHolder> {

    private List<TaskBean> dataList;

    private LayoutInflater layoutInflater;
    private OnItemClickListener mOnItemClickListener;

    public SearchTaskAdapter(Context context, List<TaskBean> dataList) {
        this.dataList = dataList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.item_search_task, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        TaskBean taskBean = dataList.get(position);
        holder.tvSearchTaskType.setText(StaticVariable.getTasktypeName(taskBean.getTasktype(), taskBean.getTasklevel()));
        holder.tvSearchTaskStatus.setText(taskBean.getStatusName());
        holder.tvSearchTaskBoxCount.setText("" + taskBean.getBoxList().size());
        holder.tvSearchTaskDate.setText(taskBean.getTaskdate());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(holder.itemView, holder.getLayoutPosition());
            }
        });
    }

    public List<TaskBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<TaskBean> dataList) {
        this.dataList = dataList;
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

        @BindView(R.id.tv_search_task_type)
        TextView tvSearchTaskType;
        @BindView(R.id.tv_search_task_status)
        TextView tvSearchTaskStatus;
        @BindView(R.id.tv_search_task_box_count)
        TextView tvSearchTaskBoxCount;
        @BindView(R.id.tv_search_task_date)
        TextView tvSearchTaskDate;

        MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

}