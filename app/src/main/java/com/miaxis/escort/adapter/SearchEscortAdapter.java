package com.miaxis.escort.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.miaxis.escort.R;
import com.miaxis.escort.model.entity.EscortBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 一非 on 2018/4/13.
 */

public class SearchEscortAdapter extends RecyclerView.Adapter<SearchEscortAdapter.MyViewHolder> {

    private List<EscortBean> dataList;

    private LayoutInflater layoutInflater;
    private OnItemClickListener mOnItemClickListener;

    public SearchEscortAdapter(Context context, List<EscortBean> dataList) {
        this.dataList = dataList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.item_search_escort, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        EscortBean escortBean = dataList.get(position);
        //TODO:押运员无法更新
        holder.civSearchEscortImage.setImageBitmap(BitmapFactory.decodeByteArray(escortBean.getPhoto(), 0, escortBean.getPhoto().length));
        holder.tvSearchEscortName.setText(escortBean.getName());
        holder.tvSearchEscortCode.setText(escortBean.getEscortno());
        holder.tvSearchEscortPhone.setText(escortBean.getPhone());
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

    public List<EscortBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<EscortBean> dataList) {
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

        @BindView(R.id.civ_search_escort_image)
        CircleImageView civSearchEscortImage;
        @BindView(R.id.tv_search_escort_name)
        TextView tvSearchEscortName;
        @BindView(R.id.tv_search_escort_code)
        TextView tvSearchEscortCode;
        @BindView(R.id.tv_search_escort_phone)
        TextView tvSearchEscortPhone;

        MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

}