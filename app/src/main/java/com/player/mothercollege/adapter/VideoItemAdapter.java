package com.player.mothercollege.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.player.mothercollege.R;
import com.player.mothercollege.bean.VideoDetailsRecommBean;
import com.player.mothercollege.college.details.BzzbDeatilsActivity;
import com.player.mothercollege.utils.DensityUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/21.
 */
public class VideoItemAdapter extends BaseAdapter{

    private Context context;
    private List<VideoDetailsRecommBean.VideoBean> lists = new ArrayList<>();


    public VideoItemAdapter(Context context,List lists) {
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
        VideoItemHolder holder = null;
        if (convertView==null){
            view = View.inflate(context, R.layout.item_college_original,null);
            holder = new VideoItemHolder();
            holder.iv_original = (ImageView) view.findViewById(R.id.iv_original);
            holder.tv_original_date = (TextView) view.findViewById(R.id.tv_original_date);
            holder.tv_original_title = (TextView) view.findViewById(R.id.tv_original_title);
            holder.tv_original_editor = (TextView) view.findViewById(R.id.tv_original_editor);
            holder.tv_original_viewCount = (TextView) view.findViewById(R.id.tv_original_viewCount);
            holder.tv_gameover = (TextView) view.findViewById(R.id.tv_gameover);
            holder.ll_original_item = (LinearLayout) view.findViewById(R.id.ll_original_item);
            view.setTag(holder);
        }else {
            view = convertView;
            holder = (VideoItemHolder) view.getTag();
        }

        Picasso.with(context).load(lists.get(position).getImg())
                .resize(DensityUtils.dip2px(context,116f),DensityUtils.dip2px(context,63.5f))
                .centerCrop().into(holder.iv_original);
        holder.tv_original_date.setText(lists.get(position).getDate());
        holder.tv_original_title.setText(lists.get(position).getTitle());
        holder.tv_original_editor.setText(lists.get(position).getEditor());
        holder.tv_original_editor.setTextColor(Color.RED);
        holder.tv_original_viewCount.setText(lists.get(position).getViewCount());
        //点击进入章节详情
        final String sid = lists.get(position).getSid();
        holder.ll_original_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BzzbDeatilsActivity.class);
                intent.putExtra("sid",sid);
                context.startActivity(intent);
                //TODO 关闭当前页面使用finish()或者Activity的四种启动方式
            }
        });

        return view;
    }

    private class VideoItemHolder{
        private ImageView iv_original;
        private TextView tv_original_date;
        private TextView tv_original_title;
        private TextView tv_original_editor;
        private TextView tv_original_viewCount;
        private TextView tv_gameover;
        private LinearLayout ll_original_item;
    }
}
