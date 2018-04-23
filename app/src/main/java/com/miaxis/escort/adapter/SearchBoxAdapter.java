package com.miaxis.escort.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.miaxis.escort.R;
import com.miaxis.escort.model.entity.BoxBean;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 一非 on 2018/4/13.
 */

public class SearchBoxAdapter extends RecyclerView.Adapter<SearchBoxAdapter.MyViewHolder> {

    private List<BoxBean> dataList;

    private LayoutInflater layoutInflater;
    private OnItemClickListener mOnItemClickListener;

    public SearchBoxAdapter(Context context, List<BoxBean> dataList) {
        this.dataList = dataList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.item_search_box, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        BoxBean boxBean = dataList.get(position);
        holder.tvSearchBoxId.setText(boxBean.getBoxname());
        holder.tvSearchBoxStatus.setText(boxBean.getStatusName());
        holder.tvSearchBoxCode.setText(boxBean.getBoxcode());
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

    public void setDataList(List<BoxBean> boxBeans) {
        this.dataList = boxBeans;
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_search_box_id)
        TextView tvSearchBoxId;
        @BindView(R.id.tv_search_box_status)
        TextView tvSearchBoxStatus;
        @BindView(R.id.tv_search_box_code)
        TextView tvSearchBoxCode;

        MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

}
