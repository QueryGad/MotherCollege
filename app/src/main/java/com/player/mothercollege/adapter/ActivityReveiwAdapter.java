package com.player.mothercollege.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.player.mothercollege.R;
import com.player.mothercollege.bean.ActivityDetailsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/2.
 */
public class ActivityReveiwAdapter extends BaseAdapter{
    private Context context;
    private List<ActivityDetailsBean.ReviewsBean> lists = new ArrayList<>();

    public ActivityReveiwAdapter(Context context,List lists) {
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
        View view = View.inflate(context, R.layout.item_reviews_details,null);
        TextView tv_reviews_name = (TextView) view.findViewById(R.id.tv_reviews_name);
        TextView tv_reviews_content = (TextView) view.findViewById(R.id.tv_reviews_content);
        tv_reviews_name.setText(lists.get(position).getUnicename()+":");
        tv_reviews_content.setText(lists.get(position).getContent());
        return view;
    }
}
