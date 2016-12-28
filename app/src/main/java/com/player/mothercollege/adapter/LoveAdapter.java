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
import com.player.mothercollege.bean.LoveBean;
import com.player.mothercollege.view.GlideCircleTransform;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/13.
 */
public class LoveAdapter extends RecyclerView.Adapter<LoveAdapter.LoveViewHolder>{
    private List<LoveBean.UsersBean> userList = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context context;
    private RequestManager glideRequest;
    private OnItemClickListener listener;
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    // 2
    public void clearData(){
        userList.clear();
        notifyItemRangeChanged(0,userList.size());
    }

    // 1
    public void addData(List<LoveBean.UsersBean> userList){
        userList.addAll(userList);
        notifyItemChanged(0,userList.size());
    }

    public LoveAdapter(List list, Context context) {
        super();
        this.userList = list;
        this.context = context;
    }

    @Override
    public LoveAdapter.LoveViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mInflater = LayoutInflater.from(parent.getContext());
        View view = mInflater.inflate(R.layout.item_find_love,null);
        return new LoveViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LoveAdapter.LoveViewHolder holder, int position) {
        //只有前三名进行显示
        holder.tv_find_love_id.setText(userList.get(position).getIndex()+"");

        String uIcon = userList.get(position).getUIcon();
        if (uIcon==null){
            holder.iv_find_love.setImageResource(R.mipmap.head_group);
        }else {
            glideRequest = Glide.with(context);
            glideRequest.load(uIcon)
                    .transform(new GlideCircleTransform(context)).into(holder.iv_find_love);
        }
        holder.tv_find_love_name.setText(userList.get(position).getUniceName());
        holder.tv_find_love_viewCount.setText(userList.get(position).getTjrs()+"");
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class LoveViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_find_love_id;
        public ImageView iv_find_love;
        public TextView tv_find_love_name;
        public TextView tv_find_love_viewCount;
        public LoveViewHolder(View itemView) {
            super(itemView);
            tv_find_love_id = (TextView) itemView.findViewById(R.id.tv_find_love_id);
            iv_find_love = (ImageView) itemView.findViewById(R.id.iv_find_love);
            tv_find_love_name = (TextView) itemView.findViewById(R.id.tv_find_love_name);
            tv_find_love_viewCount = (TextView) itemView.findViewById(R.id.tv_find_love_viewCount);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener!=null){
                        listener.onClick(v,getLayoutPosition(),userList.get(getLayoutPosition()));
                    }
                }
            });
        }
    }
    public interface OnItemClickListener{
        void onClick(View v, int position, LoveBean.UsersBean data);
    }
}
