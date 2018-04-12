package com.miaxis.escort.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.miaxis.escort.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 一非 on 2018/4/12.
 */

public class VerifyBoxAdapter extends RecyclerView.Adapter<VerifyBoxAdapter.MyViewHolder> {

    private List<String> dataList;
    private List<Boolean> checkList;
    private LayoutInflater layoutInflater;
    private OnItemClickListener mOnItemClickListener;

    public VerifyBoxAdapter(Context context, List<String> dataList) {
        this.dataList = dataList;
        checkList = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            checkList.add(false);
        }
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.item_verify_box, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.tvVerifyBox.setText(dataList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(holder.itemView, holder.getLayoutPosition());
            }
        });
    }

    public Boolean getCheck(int position) {
        return checkList.get(position);
    }

    public void setCheck(int position, Boolean b) {
        checkList.set(position, b);
    }

    public boolean isAllCheck() {
        for (Boolean b : checkList) {
            if(!b.booleanValue()) {
                return false;
            }
        }
        return true;
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

        @BindView(R.id.tv_verify_box)
        TextView tvVerifyBox;
        @BindView(R.id.iv_check)
        ImageView imageView;

        MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

}
