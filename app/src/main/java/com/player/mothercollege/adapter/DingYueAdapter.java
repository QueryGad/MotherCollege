package com.player.mothercollege.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.player.mothercollege.R;
import com.player.mothercollege.bean.DingYueBean;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/19.
 */
public class DingYueAdapter extends BaseAdapter{

    private List<DingYueBean.MyPayClassBean> lists = new ArrayList<>();
    private Context context;

    public DingYueAdapter(Context context, List lists) {
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
        ViewHolder holder = null;
        if (convertView==null){
            view = View.inflate(context, R.layout.item_me_dingyue,null);
            holder = new ViewHolder();
            holder.iv_me_dingyue = (ImageView) view.findViewById(R.id.iv_me_dingyue);
            holder.tv_me_dingyue_title = (TextView) view.findViewById(R.id.tv_me_dingyue_title);
            holder.tv_me_dingyue_viewCount = (TextView) view.findViewById(R.id.tv_me_dingyue_viewCount);
            holder.ll_me_dingyue = (LinearLayout) view.findViewById(R.id.ll_me_dingyue);
            view.setTag(holder);
        }else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }
        Picasso.with(context).load(lists.get(position).getImg())
                .resize(231, 127)
                .centerCrop().into(holder.iv_me_dingyue);
        holder.tv_me_dingyue_title.setText(lists.get(position).getTitle());

        holder.tv_me_dingyue_viewCount.setText(lists.get(position).getViewCount()+"人已订阅");
        final String sid = lists.get(position).getSid();
        holder.ll_me_dingyue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"跳转至课堂链接",Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    private class ViewHolder{
        private ImageView iv_me_dingyue;
        private TextView tv_me_dingyue_title;
        private TextView tv_me_dingyue_viewCount;
        private LinearLayout ll_me_dingyue;
    }
}
