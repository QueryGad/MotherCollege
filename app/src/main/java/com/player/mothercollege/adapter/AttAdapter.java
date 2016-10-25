package com.player.mothercollege.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.player.mothercollege.R;
import com.player.mothercollege.bean.AttBean;
import com.player.mothercollege.view.GlideCircleTransform;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/18.
 */
public class AttAdapter extends RecyclerView.Adapter<AttAdapter.AttHolder>{
    private List<AttBean.UsersBean> lists = new ArrayList<>();
    private LayoutInflater mInflater;
    private RequestManager glideRequest;
    private Context context;
    private OnItemClickListener listener;
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public AttAdapter(Context context, List lists) {
        super();
        this.context = context;
        this.lists = lists;
    }

    @Override
    public AttHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mInflater = LayoutInflater.from(parent.getContext());
        View view = mInflater.inflate(R.layout.item_find_jieli,null);
        return new AttHolder(view);
    }

    @Override
    public void onBindViewHolder(AttHolder holder, int position) {
        glideRequest = Glide.with(context);
        glideRequest.load(lists.get(position).getUIcon())
                .transform(new GlideCircleTransform(context)).into(holder.iv_find_jieli);
        holder.tv_find_jieli.setText(lists.get(position).getUniceName());
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    class AttHolder extends RecyclerView.ViewHolder{
        private ImageView iv_find_jieli;
        private TextView tv_find_jieli;
        public AttHolder(View itemView) {
            super(itemView);
            iv_find_jieli = (ImageView) itemView.findViewById(R.id.iv_find_jieli);
            tv_find_jieli = (TextView) itemView.findViewById(R.id.tv_find_jieli);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener!=null){
                        listener.onClick(v,getLayoutPosition(),lists.get(getLayoutPosition()));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onClick(View v, int position, AttBean.UsersBean data);
    }
}
