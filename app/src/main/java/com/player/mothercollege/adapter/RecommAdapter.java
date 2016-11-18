package com.player.mothercollege.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.player.mothercollege.R;
import com.player.mothercollege.bean.RecommBean;
import com.player.mothercollege.college.details.BzzbDeatilsActivity;
import com.player.mothercollege.college.details.OriginalDetailsActivity;
import com.player.mothercollege.college.details.ReadBookDetailsActivity;
import com.player.mothercollege.utils.DensityUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/18.
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
        View view = null;
        RecommHolder holder = null;
        if (convertView==null){
            view = View.inflate(context, R.layout.item_college_recomm,null);
            holder = new RecommHolder();
            holder.ll_college_recomm = (LinearLayout) view.findViewById(R.id.ll_college_recomm);
            holder.iv_recomm_title = (ImageView) view.findViewById(R.id.iv_recomm_title);
            holder.iv_recomm = (ImageView) view.findViewById(R.id.iv_recomm);
            holder.tv_recomm_type = (TextView) view.findViewById(R.id.tv_recomm_type);
            holder.tv_recomm_title = (TextView) view.findViewById(R.id.tv_recomm_title);
            holder.tv_recomm_date = (TextView) view.findViewById(R.id.tv_recomm_date);
            holder.tv_recomm_money = (TextView) view.findViewById(R.id.tv_recomm_money);
            holder.tv_recomm_editor = (TextView) view.findViewById(R.id.tv_recomm_editor);
            holder.tv_recomm_viewCount = (TextView) view.findViewById(R.id.tv_recomm_viewCount);
            view.setTag(holder);
        }else {
            view = convertView;
            holder = (RecommHolder) view.getTag();
        }
        String type = lists.get(position).getType();
        String sid = lists.get(position).getSid();
        //分类
        type = jsonType(type, holder,sid);
        Picasso.with(context).load(lists.get(position).getImg())
                .resize(DensityUtils.dip2px(context,116f),DensityUtils.dip2px(context,63.5f))
                .centerCrop().into(holder.iv_recomm);
        holder.tv_recomm_title.setText(lists.get(position).getTitle());
        holder.tv_recomm_date.setText(lists.get(position).getDate());
        String aState = lists.get(position).getAState();
        if (aState.equals("0")){
            holder.tv_recomm_money.setVisibility(View.GONE);
        }else if (aState.equals("1")){
            holder.tv_recomm_money.setText("限免");
        }else if (aState.equals("2")){
            holder.tv_recomm_money.setText("收费");
        }
        holder.tv_recomm_editor.setText(lists.get(position).getEditor());
        holder.tv_recomm_editor.setTextColor(Color.RED);
        holder.tv_recomm_viewCount.setText(lists.get(position).getViewCount()+"人已看");
        return view;
    }

    private String jsonType(String type, RecommHolder holder, final String sid) {
        //切割type
        type = type.substring(0,2);
        if (type.equals("a0")){
            //读书
            holder.iv_recomm_title.setImageResource(R.mipmap.ic_college_readbook);
            holder.tv_recomm_type.setText("读书");
            holder.ll_college_recomm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //跳转至读书详情
                    Intent intent = new Intent(context,ReadBookDetailsActivity.class);
                    intent.putExtra("sid",sid);
                    context.startActivity(intent);
                }
            });
        }else if (type.equals("a1")){
            //点播
            holder.iv_recomm_title.setImageResource(R.mipmap.ic_college_zhibo);
            holder.tv_recomm_type.setText("直播");
            holder.ll_college_recomm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //跳转至点播详情
                    Intent intent = new Intent(context,BzzbDeatilsActivity.class);
                    intent.putExtra("sid",sid);
                    context.startActivity(intent);
                }
            });
        }else if (type.equals("a2")){
            //课堂
            holder.iv_recomm_title.setImageResource(R.mipmap.ic_college_class);
            holder.tv_recomm_type.setText("课堂");
            holder.ll_college_recomm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //跳转至点播详情
                    Intent intent = new Intent(context,BzzbDeatilsActivity.class);
                    intent.putExtra("sid",sid);
                    context.startActivity(intent);
                }
            });
        }else if (type.equals("a3")){
            //原创
            holder.iv_recomm_title.setImageResource(R.mipmap.ic_college_yuanchuang);
            holder.tv_recomm_type.setText("原创");
            holder.ll_college_recomm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //跳转至读书详情
                    Intent intent = new Intent(context, OriginalDetailsActivity.class);
                    intent.putExtra("sid",sid);
                    context.startActivity(intent);
                }
            });
        }

        return type;
    }


    class RecommHolder{
        private LinearLayout ll_college_recomm;
        private ImageView iv_recomm_title,iv_recomm;
        private TextView tv_recomm_type,tv_recomm_title,tv_recomm_date,tv_recomm_money,tv_recomm_editor,tv_recomm_viewCount;
    }
}
