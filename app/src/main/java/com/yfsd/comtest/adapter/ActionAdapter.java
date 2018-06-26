package com.yfsd.comtest.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yfsd.comtest.R;
import com.yfsd.comtest.mvp.bean.ActionBean;

import java.util.List;

/**
 * Created by wl
 * on 2018/6/14.
 * 作用:
 */

public class ActionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<ActionBean> list;

    public ActionAdapter(Context context, List<ActionBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getType() == 0) {
            return 0;
        } else if (list.get(position).getType() == 1) {
            return 1;
        } else {
            return -1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        if (viewType == 0) {
            view = LayoutInflater.from(context).inflate(R.layout.item_send, parent, false);
            return new SendHolder(view);
        } else if (viewType == 1) {
            view = LayoutInflater.from(context).inflate(R.layout.item_receive, parent, false);
            return new ReceiveHolder(view);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.item_fail, parent, false);
            return new FailHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SendHolder) {
            ((SendHolder) holder).tv_send.setText(list.get(position).toString());
        } else if (holder instanceof ReceiveHolder) {
            ((ReceiveHolder) holder).tv_receive.setText(list.get(position).toString());
        } else {
            ((FailHolder) holder).tv_fail.setText(list.get(position).toString());
        }

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class SendHolder extends RecyclerView.ViewHolder {
        TextView tv_send;

        SendHolder(View itemView) {
            super(itemView);
            tv_send = itemView.findViewById(R.id.item_tv_send);
        }
    }

    public class ReceiveHolder extends RecyclerView.ViewHolder {
        TextView tv_receive;

        ReceiveHolder(View itemView) {
            super(itemView);
            tv_receive = itemView.findViewById(R.id.item_tv_receive);
        }
    }

    public class FailHolder extends RecyclerView.ViewHolder {
        TextView tv_fail;

        FailHolder(View itemView) {
            super(itemView);
            tv_fail = itemView.findViewById(R.id.item_tv_fail);
        }
    }
}
