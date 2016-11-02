package com.player.mothercollege.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.player.mothercollege.R;
import com.player.mothercollege.bean.CirleBean;
import com.player.mothercollege.view.GlideCircleTransform;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/31.
 */
public class CirleAdapter extends BaseAdapter{

    private Context context;
    private List<CirleBean.MaybeLikeGroupsBean> lists = new ArrayList<>();
    private RequestManager glideRequest;

    public CirleAdapter(Context context,List lists) {
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
        CirleHolder holder = null;
        if (convertView==null){
            view = View.inflate(context, R.layout.item_cirle_maybe,null);
            holder = new CirleHolder();
            holder.btn_cirle_checkAll = (Button) view.findViewById(R.id.btn_cirle_checkAll);
            holder.btn_cirle_check = (Button) view.findViewById(R.id.btn_cirle_check);
            holder.iv_cirle_head = (ImageView) view.findViewById(R.id.iv_cirle_head);
            holder.tv_cirle_name = (TextView) view.findViewById(R.id.tv_cirle_name);
            holder.tv_cirle_viewCount = (TextView) view.findViewById(R.id.tv_cirle_viewCount);
            holder.tv_cirle_more = (TextView) view.findViewById(R.id.tv_cirle_more);
            holder.ll_circle_line = (LinearLayout) view.findViewById(R.id.ll_circle_line);
            view.setTag(holder);
        }else {
           view = convertView;
           holder = (CirleHolder) view.getTag();
        }
        glideRequest = Glide.with(context);
        glideRequest.load(lists.get(position).getIcon())
                .transform(new GlideCircleTransform(context)).into(holder.iv_cirle_head);
        holder.tv_cirle_name.setText(lists.get(position).getGroupName());
        holder.tv_cirle_viewCount.setText(lists.get(position).getJoinCount()+"");
        if (position==0){
            holder.ll_circle_line.setVisibility(View.VISIBLE);
        }else {
            holder.ll_circle_line.setVisibility(View.GONE);
        }
        if (position==lists.size()-1){
            holder.tv_cirle_more.setVisibility(View.VISIBLE);
        }else {
            holder.tv_cirle_more.setVisibility(View.GONE);
        }
        return view;
    }

    class CirleHolder{
        private Button btn_cirle_checkAll,btn_cirle_check;
        private ImageView iv_cirle_head;
        private TextView tv_cirle_name,tv_cirle_viewCount,tv_cirle_more;
        private LinearLayout ll_circle_line;
    }
}
