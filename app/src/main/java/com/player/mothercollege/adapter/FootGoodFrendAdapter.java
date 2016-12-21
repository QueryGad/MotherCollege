package com.player.mothercollege.adapter;

import android.content.Context;
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
import com.player.mothercollege.utils.MyLog;
import com.player.mothercollege.view.GlideCircleTransform;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/21.
 */
public class FootGoodFrendAdapter extends BaseAdapter{

    private Context context;
    private List<FrendQunRecommBean.RecommendGroupBean> lists = new ArrayList<>();
    private RequestManager glideRequest;

    public FootGoodFrendAdapter(Context context,List lists) {
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
        View view = View.inflate(context, R.layout.item_frendqun_qun,null);
        LinearLayout ll_item_qun = (LinearLayout) view.findViewById(R.id.ll_item_qun);
        TextView tv_qun_type = (TextView) view.findViewById(R.id.tv_qun_type);
        ImageView iv_qun_icon = (ImageView) view.findViewById(R.id.iv_qun_icon);
        TextView tv_qun_name = (TextView) view.findViewById(R.id.tv_qun_name);
        final ImageView iv_qun_join = (ImageView) view.findViewById(R.id.iv_qun_join);
        MyLog.testLog("我是群推荐");
        if (position==0){
            tv_qun_type.setVisibility(View.VISIBLE);
            tv_qun_type.setText("群推荐");
        }else {
            tv_qun_type.setVisibility(View.GONE);
        }
        String groupIcon = lists.get(position).getGroupIcon();
        if (groupIcon==null){
            iv_qun_icon.setImageResource(R.mipmap.head_group);
        }else {
            glideRequest = Glide.with(context);
            glideRequest.load(groupIcon)
                    .transform(new GlideCircleTransform(context)).into(iv_qun_icon);
        }
        tv_qun_name.setText(lists.get(position).getGroupName());
        final int currentUserJoinState = lists.get(position).getCurrentUserJoinState();
        if (currentUserJoinState ==0){
            //未加入
            iv_qun_join.setImageResource(R.mipmap.icon_2_join);
            iv_qun_join.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iv_qun_join.setImageResource(R.mipmap.icon_apply_join);
                }
            });
        }else if (currentUserJoinState==1){
            //审核中
            iv_qun_join.setImageResource(R.mipmap.icon_apply_join);
        }else if (currentUserJoinState==2){
            //通过
            iv_qun_join.setImageResource(R.mipmap.icon_join);
        }
        return view;
    }
}
