package com.player.mothercollege.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
public class JiLuAdapter extends BaseAdapter{
    private List<JiLuBean.MyPayClassBean> lists = new ArrayList<>();
    private Context context;

    public JiLuAdapter(Context context, List lists) {
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
            view = View.inflate(context, R.layout.item_me_jilu,null);
            holder = new ViewHolder();
            holder.iv_me_jilu = (ImageView) view.findViewById(R.id.iv_me_jilu);
            holder.tv_me_jilu_title = (TextView) view.findViewById(R.id.tv_me_jilu_title);
            holder.tv_me_jilu_viewCount = (TextView) view.findViewById(R.id.tv_me_jilu_viewCount);
            view.setTag(holder);
        }else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }

        Picasso.with(context).load(lists.get(position).getImg())
                .resize(231, 127)
                .centerCrop().into(holder.iv_me_jilu);
        holder.tv_me_jilu_title.setText(lists.get(position).getTitle());
        holder.tv_me_jilu_viewCount.setText(lists.get(position).getHasViewInfo());

        return view;
    }

    private class ViewHolder{
        private ImageView iv_me_jilu;
        private TextView tv_me_jilu_title;
        private TextView tv_me_jilu_viewCount;
    }
}
