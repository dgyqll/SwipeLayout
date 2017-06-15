package com.etouse.swipelayout;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2017/6/14.
 */

public class MAdapter extends RecyclerView.Adapter<MAdapter.MViewHolder> {
    private Context context;
    private List<String> mDatas;
    private OnDeleteButtonClickListener onDeleteButtonClickListener;
    private OnItemClickListener onItemClickListener;

    public MAdapter(Context context, List<String> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
    }

    @Override

    public MViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_recycleview, null);
        MViewHolder holder = new MViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MViewHolder holder, final int position) {
        holder.tvTitle.setText(mDatas.get(position));
        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDeleteButtonClickListener.onDeleteClicked(position,mDatas);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClicked(position,mDatas);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvTitle;
        private final TextView tvOk;
        private final TextView tvDelete;
        private final SwipeLayout swipeLayout;

        public MViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvOk = (TextView) itemView.findViewById(R.id.tv_ok);
            tvDelete = (TextView) itemView.findViewById(R.id.tv_delete);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipeLayout);
        }
    }

    public void setOnDeleteButtonClickListener(OnDeleteButtonClickListener onDeleteButtonClickListener){
        this.onDeleteButtonClickListener = onDeleteButtonClickListener;
    }
    public interface OnDeleteButtonClickListener{
        void onDeleteClicked(int position, List<String> mDatas);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
    public interface OnItemClickListener{
        void onItemClicked(int position, List<String> mDatas);
    }




}
