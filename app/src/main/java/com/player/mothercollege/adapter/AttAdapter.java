package com.player.mothercollege.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.player.mothercollege.R;
import com.player.mothercollege.bean.AttBean;
import com.player.mothercollege.me.HeadIconActivity;
import com.player.mothercollege.view.GlideCircleTransform;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/18.
 */
public class AttAdapter extends BaseAdapter{
    private List<AttBean.UsersBean> lists = new ArrayList<>();
    private RequestManager glideRequest;
    private Context context;

    public AttAdapter(Context context, List lists) {
        super();
        this.context = context;
        this.lists = lists;
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
        View view =null;
        ViewHolder holder =null;
        if (convertView==null){
            view = View.inflate(context, R.layout.item_find_jieli,null);
            holder = new ViewHolder();
            holder.iv_find_jieli = (ImageView) view.findViewById(R.id.iv_find_jieli);
            holder.tv_find_jieli = (TextView) view.findViewById(R.id.tv_find_jieli);
            view.setTag(holder);
        }else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }
        String uIcon = lists.get(position).getUIcon();
        if (uIcon==null){
            holder.iv_find_jieli.setImageResource(R.mipmap.head_group);
        }else {
            glideRequest = Glide.with(context);
            glideRequest.load(uIcon)
                    .transform(new GlideCircleTransform(context)).into(holder.iv_find_jieli);
        }
        holder.tv_find_jieli.setText(lists.get(position).getUniceName());
        final String uid = lists.get(position).getUid();
        holder.iv_find_jieli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击头像进入他人主页

                Intent intent = new Intent(context, HeadIconActivity.class);
                intent.putExtra("toUid",uid);
                context.startActivity(intent);
            }
        });
        return view;
    }

    private class ViewHolder{
        private ImageView iv_find_jieli;
        private TextView tv_find_jieli;
    }
}
