package com.player.mothercollege.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.player.mothercollege.R;
import com.player.mothercollege.bean.ActiveBean;
import com.player.mothercollege.unity.details.ActivityDetailsActivity;
import com.player.mothercollege.utils.DensityUtils;
import com.player.mothercollege.utils.ScreenUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/16.
 */
public class ActiveAdapter extends BaseAdapter{

    private Context context;
    private List<ActiveBean.ActivesBean> lists = new ArrayList<>();

    public ActiveAdapter(Context context, List lists) {
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
        View view = null;
        ActiveHolder holder = null;
        if (convertView==null){
            view = View.inflate(context, R.layout.item_activity_list,null);
            holder = new ActiveHolder();
            holder.tv_active_more = (TextView) view.findViewById(R.id.tv_active_more);
            holder.tv_active_title = (TextView) view.findViewById(R.id.tv_active_title);
            holder.tv_active_address = (TextView) view.findViewById(R.id.tv_active_address);
            holder.tv_active_person = (TextView) view.findViewById(R.id.tv_active_person);
            holder.iv_active = (ImageView) view.findViewById(R.id.iv_active);
            holder.view_active = view.findViewById(R.id.view_active);
            view.setTag(holder);
        }else {
            view = convertView;
            holder = (ActiveHolder) view.getTag();
        }

        holder.tv_active_title.setText(lists.get(position).getTitle());
        Picasso.with(context).load(lists.get(position).getImg())
                .resize(DensityUtils.dip2px(context, ScreenUtils.getWidth(context)),DensityUtils.dip2px(context,300))
                .centerCrop().into(holder.iv_active);
        holder.tv_active_address.setText(lists.get(position).getCity());
        holder.tv_active_person.setText(lists.get(position).getJoinCount()+"");
        if (position==0){
            holder.tv_active_more.setVisibility(View.VISIBLE);
            holder.view_active.setVisibility(View.VISIBLE);
            holder.tv_active_more.setText("推荐活动");
        }else if (position==1){
            holder.tv_active_more.setVisibility(View.VISIBLE);
            holder.view_active.setVisibility(View.GONE);
            holder.tv_active_more.setText("更多活动");
        }else {
            holder.tv_active_more.setVisibility(View.GONE);
            holder.view_active.setVisibility(View.GONE);
        }

        final String aid = lists.get(position).getAid();
        holder.iv_active.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ActivityDetailsActivity.class);
                intent.putExtra("aid",aid);
                context.startActivity(intent);
            }
        });
        return view;
    }

    class ActiveHolder{
        private TextView tv_active_more,tv_active_title,tv_active_address,tv_active_person;
        private ImageView iv_active;
        private View view_active;
    }
}
