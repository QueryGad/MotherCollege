package com.player.mothercollege.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.player.mothercollege.R;
import com.player.mothercollege.bean.LoveBean;
import com.player.mothercollege.me.HeadIconActivity;
import com.player.mothercollege.view.GlideCircleTransform;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/13.
 */
public class LoveAdapter extends BaseAdapter{
    private List<LoveBean.UsersBean> lists = new ArrayList<>();
    private Context context;
    private RequestManager glideRequest;

    public LoveAdapter(List lists, Context context) {
        super();
        this.lists = lists;
        this.context = context;
    }


    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        ViewHolder holder =null;
        if (convertView==null){
            view = View.inflate(context, R.layout.item_find_love,null);
            holder = new ViewHolder();
            holder.iv_find_love = (ImageView) view.findViewById(R.id.iv_find_love);
            holder.tv_find_love_id = (TextView) view.findViewById(R.id.tv_find_love_id);
            holder.tv_find_love_name = (TextView) view.findViewById(R.id.tv_find_love_name);
            holder.tv_find_love_viewCount = (TextView) view.findViewById(R.id.tv_find_love_viewCount);
            holder.ll_find_love_item = (LinearLayout) view.findViewById(R.id.ll_find_love_itme);
            view.setTag(holder);
        }else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }
        holder.tv_find_love_id.setText(lists.get(position).getIndex()+"");

        String uIcon = lists.get(position).getUIcon();
        if (uIcon==null){
            holder.iv_find_love.setImageResource(R.mipmap.head_group);
        }else {
            glideRequest = Glide.with(context);
            glideRequest.load(uIcon)
                    .transform(new GlideCircleTransform(context)).into(holder.iv_find_love);
        }
        holder.tv_find_love_name.setText(lists.get(position).getUniceName());
        holder.tv_find_love_viewCount.setText(lists.get(position).getTjrs()+"");
        final String uid = lists.get(position).getUid();
        holder.ll_find_love_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击头像进入他人主页
                Intent intent = new Intent(context, HeadIconActivity.class);
                intent.putExtra("toUid", uid);
                context.startActivity(intent);
            }
        });
        return view;
    }

    private class ViewHolder{
        public TextView tv_find_love_id;
        public ImageView iv_find_love;
        public TextView tv_find_love_name;
        public TextView tv_find_love_viewCount;
        public LinearLayout ll_find_love_item;
    }
}
