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
import com.player.mothercollege.bean.JieLiBean;
import com.player.mothercollege.view.GlideCircleTransform;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/10.
 */
public class JieLiAdapter extends RecyclerView.Adapter<JieLiAdapter.JieLiViewHolder>{
    private List<JieLiBean.UsersBean> jieLilist = new ArrayList<>();
    private LayoutInflater mInflater;
    private RequestManager glideRequest;
    private Context context;
    private OnItemClickListener listener;
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public JieLiAdapter(List list,Context context) {
        super();
        this.jieLilist = list;
        this.context = context;
    }

    @Override
    public JieLiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mInflater = LayoutInflater.from(parent.getContext());
        View view = mInflater.inflate(R.layout.item_find_jieli,null);
        return new JieLiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(JieLiViewHolder holder, int position) {
        glideRequest = Glide.with(context);
        glideRequest.load(jieLilist.get(position).getUIcon())
                .transform(new GlideCircleTransform(context)).into(holder.iv_find_jieli);
        holder.tv_find_jieli.setText(jieLilist.get(position).getUniceName());
    }

    @Override
    public int getItemCount() {
        return jieLilist.size();
    }

    class JieLiViewHolder extends RecyclerView.ViewHolder{
        private ImageView iv_find_jieli;
        private TextView tv_find_jieli;
        public JieLiViewHolder(View itemView) {
            super(itemView);
            iv_find_jieli = (ImageView) itemView.findViewById(R.id.iv_find_jieli);
            tv_find_jieli = (TextView) itemView.findViewById(R.id.tv_find_jieli);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener!=null){
                        listener.onClick(v,getLayoutPosition(),jieLilist.get(getLayoutPosition()));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onClick(View v, int position, JieLiBean.UsersBean data);
    }
}
