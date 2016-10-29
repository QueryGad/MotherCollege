package com.player.mothercollege.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.player.mothercollege.R;
import com.player.mothercollege.bean.ZhuanJiaBean;
import com.player.mothercollege.view.GlideCircleTransform;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/29.
 */
public class ZhuanJiaAdapter extends BaseAdapter{

    private Context context;
    private List<ZhuanJiaBean.ExpertsBean> lists = new ArrayList<>();
    private RequestManager glideRequest;

    public ZhuanJiaAdapter(Context context,List lists) {
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
        ZhuanJiaHolder zh = null;
        if (convertView==null){
            view = View.inflate(context, R.layout.item_zixun_zhuanjia,null);
            zh = new ZhuanJiaHolder();
            zh.iv_zhuanjia_head = (ImageView) view.findViewById(R.id.iv_zhuanjia_head);
            zh.tv_zhuanjia_name = (TextView) view.findViewById(R.id.tv_zhuanjia_name);
            zh.tv_zhuanjia_money = (TextView) view.findViewById(R.id.tv_zhuanjia_money);
            zh.tv_zhuanjia_info = (TextView) view.findViewById(R.id.tv_zhuanjia_info);
            zh.tv_zhuanjia_location = (TextView) view.findViewById(R.id.tv_zhuanjia_location);
            view.setTag(zh);
        }else {
            view = convertView;
            zh = (ZhuanJiaHolder) view.getTag();
        }
        glideRequest = Glide.with(context);
        glideRequest.load(lists.get(position).getIcon())
                .transform(new GlideCircleTransform(context)).into(zh.iv_zhuanjia_head);
        zh.tv_zhuanjia_name.setText(lists.get(position).getName());
        zh.tv_zhuanjia_money.setText(lists.get(position).getPrice());
        zh.tv_zhuanjia_info.setText(lists.get(position).getInfo());
        zh.tv_zhuanjia_location.setText(lists.get(position).getAdd());
        return view;
    }

    class ZhuanJiaHolder{
        public ImageView iv_zhuanjia_head;
        public TextView tv_zhuanjia_name,tv_zhuanjia_money,tv_zhuanjia_info,tv_zhuanjia_location;
    }
}
