package com.player.mothercollege.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.player.mothercollege.R;
import com.player.mothercollege.bean.SmartListBean;
import com.player.mothercollege.pay.PayIndentActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/10/15.
 */
public class SmartPayAdapter extends BaseAdapter{

    private String muchs[];
    private String money[];
    List<SmartListBean.PayGoodListBean> lists = new ArrayList<>();
    private Context context;

    public SmartPayAdapter(List lists,String[] muchs, String[]money, Context context) {
        super();
        this.context = context;
        this.muchs = muchs;
        this.money = money;
        this.lists = lists;
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
        LinearLayout ll_gridview_money = (LinearLayout) view.findViewById(R.id.ll_gridview_money);
        tv_gridview_much.setText(muchs[position]);
        tv_gridview_money.setText(money[position]);
        final String goodID = lists.get(position).getGoodID();
        ll_gridview_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //调起充值接口
                Intent intent = new Intent(context, PayIndentActivity.class);
                intent.putExtra("goodID",goodID);
                context.startActivity(intent);
            }
        });
        return view;
    }
}
