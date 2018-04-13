package com.miaxis.escort.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jakewharton.rxbinding2.view.RxView;
import com.miaxis.escort.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 一非 on 2018/4/12.
 */

public class BoxAdapter extends RecyclerView.Adapter<BoxAdapter.MyViewHolder> {

    private List<String> dataList;
    private List<String> selectedList;

    private LayoutInflater layoutInflater;
    private OnItemClickListener mOnItemClickListener;

    public BoxAdapter(Context context, List<String> dataList) {
        this.dataList = dataList;
        selectedList = new ArrayList<>();
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.item_box, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.tvBoxId.setText(dataList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(holder.tvBoxId, holder.getLayoutPosition());
            }
        });
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void addSelectedBox(String box) {
        selectedList.add(box);
    }

    public void removeSelectedBox(String box) {
        selectedList.remove(box);
    }

    public String getData(int position) {
        return dataList.get(position);
    }

    public int getSelectedSize() {
        return selectedList.size();
    }

    public List<String> getSelectedItem() {
        return selectedList;
    }

    public String makeSelectedText() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String str : selectedList) {
            stringBuilder.append("    " + str + "\n");
        }
        return stringBuilder.toString();
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_box_id)
        TextView tvBoxId;

        MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

}