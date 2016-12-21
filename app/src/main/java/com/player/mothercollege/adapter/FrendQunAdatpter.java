package com.player.mothercollege.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.player.mothercollege.R;
import com.player.mothercollege.bean.FrendQunRecommBean;
import com.player.mothercollege.me.HeadIconActivity;
import com.player.mothercollege.view.GlideCircleTransform;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/21.
 */
public class FrendQunAdatpter extends BaseAdapter{

    private Context context;
    private List<FrendQunRecommBean.RecommendUsersBean> lists = new ArrayList<>();
    private RequestManager glideRequest;


    public FrendQunAdatpter(Context context,List lists) {
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
        View view = View.inflate(context, R.layout.item_frendqun_recomm,null);
        LinearLayout ll_item_frend = (LinearLayout) view.findViewById(R.id.ll_item_frend);
        TextView tv_frendqun_type = (TextView) view.findViewById(R.id.tv_frend_type);
        ImageView iv_frend_icon = (ImageView) view.findViewById(R.id.iv_frend_icon);
        TextView tv_frend_name = (TextView) view.findViewById(R.id.tv_frend_name);
        ImageView iv_frendqun_vip = (ImageView) view.findViewById(R.id.iv_frendqun_vip);
        if (position==0){
            tv_frendqun_type.setVisibility(View.VISIBLE);
        }else {
            tv_frendqun_type.setVisibility(View.GONE);
        }
        String icon = lists.get(position).getIcon();
        if (icon==null){
            iv_frend_icon.setImageResource(R.mipmap.head_group);
        }else {
            glideRequest = Glide.with(context);
            glideRequest.load(icon)
                    .transform(new GlideCircleTransform(context)).into(iv_frend_icon);
        }
        tv_frend_name.setText(lists.get(position).getNiceName());
        boolean isVip = lists.get(position).isIsVip();
        if (isVip){
            iv_frendqun_vip.setVisibility(View.VISIBLE);
            iv_frendqun_vip.setImageResource(R.mipmap.icon_angel);
        }else {
            iv_frendqun_vip.setVisibility(View.GONE);
        }

        final String uid = lists.get(position).getUid();
        final String snsUid = lists.get(position).getSnsUid();

        ll_item_frend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HeadIconActivity.class);
                intent.putExtra("toUid",uid);
                intent.putExtra("toUid",uid);
                context.startActivity(intent);
            }
        });
        return view;
    }
}
