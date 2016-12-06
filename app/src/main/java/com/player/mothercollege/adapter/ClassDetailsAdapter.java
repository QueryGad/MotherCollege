package com.player.mothercollege.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.player.mothercollege.R;
import com.player.mothercollege.bean.ClassDetailsBean;
import com.player.mothercollege.utils.DensityUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/6.
 */
public class ClassDetailsAdapter extends BaseAdapter{

    private Context context;
    private List<ClassDetailsBean.VideosBean> lists = new ArrayList<>();

    public ClassDetailsAdapter(Context context,List lists) {
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
        DetailsHolder holder = new DetailsHolder();
        if (convertView==null){
            view = View.inflate(context, R.layout.item_college_original,null);
            holder = new DetailsHolder();
            holder.iv_original = (ImageView) view.findViewById(R.id.iv_original);
            holder.tv_original_date = (TextView) view.findViewById(R.id.tv_original_date);
            holder.tv_original_title = (TextView) view.findViewById(R.id.tv_original_title);
            holder.tv_original_editor = (TextView) view.findViewById(R.id.tv_original_editor);
            holder.tv_original_viewCount = (TextView) view.findViewById(R.id.tv_original_viewCount);
            holder.iv_original_recomm = (ImageView) view.findViewById(R.id.iv_original_recomm);
            holder.ll_original_item = (LinearLayout) view.findViewById(R.id.ll_original_item);
            view.setTag(holder);
        }else {
            view = convertView;
            holder = (DetailsHolder) view.getTag();
        }
        Picasso.with(context).load(lists.get(position).getImg())
                .resize(DensityUtils.dip2px(context,116f),DensityUtils.dip2px(context,63.5f))
                .centerCrop().into(holder.iv_original);
        holder.tv_original_date.setText(lists.get(position).getDate());
        holder.tv_original_title.setText(lists.get(position).getTitle());
        holder.tv_original_editor.setText(lists.get(position).getEditor());
        holder.tv_original_editor.setTextColor(Color.RED);
        holder.tv_original_viewCount.setText(lists.get(position).getViewCount()+"");
        final String sid = lists.get(position).getSid();
        holder.ll_original_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //在本页面内更换视频
                //TODO

            }
        });
        return view;
    }

    private class DetailsHolder{
        private ImageView iv_original;
        private TextView tv_original_date;
        private TextView tv_original_title;
        private TextView tv_original_editor;
        private TextView tv_original_viewCount;
        private ImageView iv_original_recomm;
        private LinearLayout ll_original_item;

    }
}
