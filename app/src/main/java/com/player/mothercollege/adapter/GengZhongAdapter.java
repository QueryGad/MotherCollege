package com.player.mothercollege.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.player.mothercollege.R;
import com.player.mothercollege.bean.GengZhongBean;
import com.player.mothercollege.me.HeadIconActivity;
import com.player.mothercollege.view.GlideCircleTransform;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/10.
 */
public class GengZhongAdapter extends RecyclerView.Adapter<GengZhongAdapter.GengZhongViewHolder>{
    private List<GengZhongBean.UsersBean> gengZhongList = new ArrayList<>();
    private LayoutInflater mInflater;
    private RequestManager glideRequest;
    private Context context;
    private OnItemClickListener listener;
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public GengZhongAdapter(List list, Context context) {
        super();
        this.gengZhongList = list;
        this.context = context;
    }

    @Override
    public GengZhongViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mInflater = LayoutInflater.from(parent.getContext());
        View view = mInflater.inflate(R.layout.item_find_jieli,null);
        return new GengZhongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GengZhongViewHolder holder, int position) {
        String uIcon = gengZhongList.get(position).getUIcon();
        if (uIcon==null){
            holder.iv_find_jieli.setImageResource(R.mipmap.head_group);
        }else {
            glideRequest = Glide.with(context);
            glideRequest.load(uIcon)
                    .transform(new GlideCircleTransform(context)).into(holder.iv_find_jieli);
        }

        final String uid = gengZhongList.get(position).getUid();
        holder.iv_find_jieli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HeadIconActivity.class);
                intent.putExtra("toUid",uid);
                context.startActivity(intent);
            }
        });
        holder.tv_find_jieli.setText(gengZhongList.get(position).getUniceName());
    }

    @Override
    public int getItemCount() {
        return gengZhongList.size();
    }

    class GengZhongViewHolder extends RecyclerView.ViewHolder{
        private ImageView iv_find_jieli;
        private TextView tv_find_jieli;
        public GengZhongViewHolder(View itemView) {
            super(itemView);
            iv_find_jieli = (ImageView) itemView.findViewById(R.id.iv_find_jieli);
            tv_find_jieli = (TextView) itemView.findViewById(R.id.tv_find_jieli);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener!=null){
                        listener.onClick(v,getLayoutPosition(),gengZhongList.get(getLayoutPosition()));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onClick(View v, int position, GengZhongBean.UsersBean data);
    }
}
