package com.player.mothercollege.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.player.mothercollege.R;
import com.player.mothercollege.bean.JiLuBean;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/19.
 */
public class JiLuAdapter extends RecyclerView.Adapter<JiLuAdapter.JiLuHolder>{
    private List<JiLuBean.MyPayClassBean> lists = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context context;
    private OnItemClickListener listener;
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public JiLuAdapter(Context context, List lists) {
        super();
        this.context = context;
        this.lists = lists;
    }

    @Override
    public JiLuHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mInflater = LayoutInflater.from(parent.getContext());
        View view = mInflater.inflate(R.layout.item_me_jilu,null);
        return new JiLuHolder(view);
    }

    @Override
    public void onBindViewHolder(JiLuHolder holder, int position) {
        Picasso.with(context).load(lists.get(position).getImg())
                .resize(231, 127)
                .centerCrop().into(holder.iv_me_jilu);
        holder.tv_me_jilu_title.setText(lists.get(position).getTitle());
        holder.tv_me_jilu_viewCount.setText(lists.get(position).getHasViewInfo());
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    class JiLuHolder extends RecyclerView.ViewHolder{
        private ImageView iv_me_jilu;
        private TextView tv_me_jilu_title;
        private TextView tv_me_jilu_viewCount;
        public JiLuHolder(View itemView) {
            super(itemView);
            iv_me_jilu = (ImageView) itemView.findViewById(R.id.iv_me_jilu);
            tv_me_jilu_title = (TextView) itemView.findViewById(R.id.tv_me_jilu_title);
            tv_me_jilu_viewCount = (TextView) itemView.findViewById(R.id.tv_me_jilu_viewCount);
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
        void onClick(View v, int position, JiLuBean.MyPayClassBean data);
    }
}
