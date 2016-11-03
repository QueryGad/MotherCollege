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
 * Created by Administrator on 2016/11/3.
 */
public class MaybeAdapter extends BaseAdapter{

    private Context context;
    private List<CirleBean.MaybeLikeGroupsBean> maybeList = new ArrayList<>();
    private RequestManager glideRequest;

    public MaybeAdapter(Context context,List lists) {
        super();
        this.context = context;
        this.maybeList = lists;
    }

    @Override
    public int getCount() {
        return maybeList.size();
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
        MaybeHolder holder = null;
        if (convertView==null){
            view = View.inflate(context, R.layout.item_me_maybecirle,null);
            holder = new MaybeHolder();
            holder.tv_maybe_title = (TextView) view.findViewById(R.id.tv_maybecirle_title);
            holder.tv_maybe_name = (TextView) view.findViewById(R.id.tv_maybecirle_name);
            holder.tv_maybe_num = (TextView) view.findViewById(R.id.tv_maybecirle_num);
            holder.iv_maybe_icon = (ImageView) view.findViewById(R.id.iv_maybecirle_icon);
            holder.btn_join = (Button) view.findViewById(R.id.btn_join);
            holder.ll_maybe = (LinearLayout) view.findViewById(R.id.ll_maybecirle);
            holder.btn_moreCirle = (Button) view.findViewById(R.id.btn_moreCirle);
            holder.btn_maybecirle_alljoin = (Button) view.findViewById(R.id.btn_maybecirle_alljoin);

            view.setTag(holder);
        }else {
            view = convertView;
            holder = (MaybeHolder) view.getTag();
        }
        if (position==0){
            holder.ll_maybe.setVisibility(View.VISIBLE);
        }else {
            holder.ll_maybe.setVisibility(View.GONE);
        }
        holder.tv_maybe_name.setText(maybeList.get(position).getGroupName());
        holder.tv_maybe_num.setText(maybeList.get(position).getJoinCount()+"");
        glideRequest = Glide.with(context);
        glideRequest.load(maybeList.get(position).getIcon())
                .transform(new GlideCircleTransform(context)).into(holder.iv_maybe_icon);
        if (position==maybeList.size()-1){
            holder.btn_moreCirle.setVisibility(View.VISIBLE);
        }else {
            holder.btn_moreCirle.setVisibility(View.GONE);
        }
        return view;
    }

    class MaybeHolder{
        private TextView tv_maybe_title,tv_maybe_name,tv_maybe_num;
        private ImageView iv_maybe_icon;
        private Button btn_join,btn_moreCirle,btn_maybecirle_alljoin;
        private LinearLayout ll_maybe;
    }
}
