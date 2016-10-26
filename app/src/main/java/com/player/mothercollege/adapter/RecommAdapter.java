package com.player.mothercollege.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.player.mothercollege.bean.RecommBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/26.
 */
public class RecommAdapter extends BaseAdapter{

    private Context context;
    private List<RecommBean.ListBean> lists = new ArrayList<>();

    public RecommAdapter(Context context, List lists) {
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

        return null;
    }
}
