package com.player.mothercollege.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.player.mothercollege.R;
import com.player.mothercollege.bean.CirleBean;
import com.player.mothercollege.me.details.CirleDetailsActivity;
import com.player.mothercollege.view.GlideCircleTransform;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/3.
 */
public class MyCirleAdapter extends BaseAdapter{

    private Context context;
    private List<CirleBean.MyGroupsBean> myList = new ArrayList<>();
    private RequestManager glideRequest;

    public MyCirleAdapter(Context context,List lists) {
        super();
        this.context = context;
        this.myList = lists;
    }

    @Override
    public int getCount() {
        return myList.size();
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
        MyHolder holder = null;
        if (convertView==null){
            view = View.inflate(context, R.layout.item_me_mycirle,null);
            holder = new MyHolder();
            holder.tv_my_title = (TextView) view.findViewById(R.id.tv_mycirle_title);
            holder.tv_my_name = (TextView) view.findViewById(R.id.tv_mycirle_name);
            holder.tv_my_num = (TextView) view.findViewById(R.id.tv_mycirle_num);
            holder.iv_my_icon = (ImageView) view.findViewById(R.id.iv_mycirle_icon);
            holder.btn_join = (Button) view.findViewById(R.id.btn_join);
            holder.rl_mycirle = (RelativeLayout) view.findViewById(R.id.rl_mycirle);
            view.setTag(holder);
        }else {
            view = convertView;
            holder = (MyHolder) view.getTag();
        }
        if (position==0){
            holder.tv_my_title.setVisibility(View.VISIBLE);
        }else {
            holder.tv_my_title.setVisibility(View.GONE);
        }
        holder.tv_my_title.setText("已关注的圈子("+myList.size()+")");
        holder.tv_my_name.setText(myList.get(position).getGroupName());
        holder.tv_my_num.setText(myList.get(position).getJoinCount()+"");
        glideRequest = Glide.with(context);
        glideRequest.load(myList.get(position).getIcon())
                .transform(new GlideCircleTransform(context)).into(holder.iv_my_icon);
        //点击进去圈子详情页面
        final String groupId = myList.get(position).getGroupId();
        holder.rl_mycirle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CirleDetailsActivity.class);
                intent.putExtra("groupId",groupId);
                context.startActivity(intent);
            }
        });
        return view;
    }

    class MyHolder{
        private TextView tv_my_title,tv_my_name,tv_my_num;
        private ImageView iv_my_icon;
        private Button btn_join;
        private RelativeLayout rl_mycirle;
    }
}
