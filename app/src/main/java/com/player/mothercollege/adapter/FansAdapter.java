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
import com.player.mothercollege.bean.FansBean;
import com.player.mothercollege.view.GlideCircleTransform;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/18.
 */
public class FansAdapter extends RecyclerView.Adapter<FansAdapter.FansHolder>{
    private List<FansBean.UsersBean> lists = new ArrayList<>();
    private LayoutInflater mInflater;
    private RequestManager glideRequest;
    private Context context;
    private OnItemClickListener listener;
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public FansAdapter(Context context, List lists) {
        super();
        this.context = context;
        this.lists = lists;
    }

    @Override
    public FansHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mInflater = LayoutInflater.from(parent.getContext());
        View view = mInflater.inflate(R.layout.item_find_jieli,null);
        return new FansHolder(view);
    }

    @Override
    public void onBindViewHolder(FansHolder holder, int position) {
        String uIcon = lists.get(position).getUIcon();
        if (uIcon==null){
            holder.iv_find_jieli.setImageResource(R.mipmap.head_group);
        }else {
            glideRequest = Glide.with(context);
            glideRequest.load(lists.get(position).getUIcon())
                    .transform(new GlideCircleTransform(context)).into(holder.iv_find_jieli);
        }
        holder.tv_find_jieli.setText(lists.get(position).getUniceName());
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }


    class FansHolder extends RecyclerView.ViewHolder{
        private ImageView iv_find_jieli;
        private TextView tv_find_jieli;
        public FansHolder(View itemView) {
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
        void onClick(View v, int position, FansBean.UsersBean data);
    }
}
