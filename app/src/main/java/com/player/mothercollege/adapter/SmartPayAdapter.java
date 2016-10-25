package com.player.mothercollege.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.player.mothercollege.R;


/**
 * Created by Administrator on 2016/10/15.
 */
public class SmartPayAdapter extends BaseAdapter{

    private String muchs[];
    private String money[];
    private Context context;

    public SmartPayAdapter(String[] muchs, String[]money, Context context) {
        super();
        this.context = context;
        this.muchs = muchs;
        this.money = money;
    }

    @Override
    public int getCount() {
        return muchs.length;
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
        View view = View.inflate(context, R.layout.item_gridview,null);
        TextView tv_gridview_much = (TextView) view.findViewById(R.id.tv_gridview_much);
        TextView tv_gridview_money = (TextView) view.findViewById(R.id.tv_gridview_money);
        tv_gridview_much.setText(muchs[position]);
        tv_gridview_money.setText(money[position]);
        return view;
    }
}
