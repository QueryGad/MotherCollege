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
import com.player.mothercollege.college.details.ClassDetailsActivity;
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
    private List<RecommBean.ListBean> reads = new ArrayList<>();
    private List<RecommBean.ListBean> videos = new ArrayList<>();
    private List<RecommBean.ListBean> classs = new ArrayList<>();
    private List<RecommBean.ListBean> originals = new ArrayList<>();


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

        View view = View.inflate(context, R.layout.item_college_recomm,null);
        ImageView iv_recomm_type = (ImageView) view.findViewById(R.id.iv_recomm_type);
        TextView tv_recomm_type = (TextView) view.findViewById(R.id.tv_recomm_type);
        LinearLayout ll_college_recomm = (LinearLayout) view.findViewById(R.id.ll_college_recomm);
        ImageView iv_recomm = (ImageView) view.findViewById(R.id.iv_recomm);
        TextView tv_recomm_date = (TextView) view.findViewById(R.id.tv_recomm_date);
        TextView tv_recomm_title = (TextView) view.findViewById(R.id.tv_recomm_title);
        TextView tv_recomm_editor = (TextView) view.findViewById(R.id.tv_recomm_editor);
        TextView tv_recomm_viewCount = (TextView) view.findViewById(R.id.tv_recomm_viewCount);
        TextView tv_recomm_money = (TextView) view.findViewById(R.id.tv_recomm_money);
        String aState = lists.get(position).getAState();
        if (aState.equals("0")){
            tv_recomm_money.setVisibility(View.GONE);
        }else if (aState.equals("1")){
            tv_recomm_money.setText("限免");
        }else if (aState.equals("2")){
            tv_recomm_money.setText("收费");
        }
        Picasso.with(context).load(lists.get(position).getImg())
                .resize(DensityUtils.dip2px(context,116f),DensityUtils.dip2px(context,63.5f))
                .centerCrop().into(iv_recomm);
        tv_recomm_title.setText(lists.get(position).getTitle());
        tv_recomm_date.setText(lists.get(position).getDate());
        tv_recomm_editor.setText(lists.get(position).getEditor());
        tv_recomm_editor.setTextColor(Color.RED);
        tv_recomm_viewCount.setText(lists.get(position).getViewCount()+"人已看");

        String type = lists.get(position).getType();
        final String url = lists.get(position).getUrl();
        final String img = lists.get(position).getImg();
        final String title = lists.get(position).getTitle();
        final String sid = lists.get(position).getSid();

        //分类
        //切割type
        type = type.substring(0,2);
        if (type.equals("a0")){
            reads.add(lists.get(position));
            if (reads.size()==1){
                iv_recomm_type.setVisibility(View.VISIBLE);
                tv_recomm_type.setVisibility(View.VISIBLE);
                iv_recomm_type.setImageResource(R.mipmap.ic_college_readbook);
                tv_recomm_type.setText("读书");
            }else {
                iv_recomm_type.setVisibility(View.GONE);
                tv_recomm_type.setVisibility(View.GONE);
            }

            ll_college_recomm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //跳转至读书详情
                    Intent intent = new Intent(context,ReadBookDetailsActivity.class);
                    intent.putExtra("sid",sid);
                    context.startActivity(intent);
                }
            });
        }else if (type.equals("a1")){
            videos.add(lists.get(position));
            if (videos.size()==1){
                iv_recomm_type.setVisibility(View.VISIBLE);
                tv_recomm_type.setVisibility(View.VISIBLE);
                iv_recomm_type.setImageResource(R.mipmap.ic_college_zhibo);
                tv_recomm_type.setText("直播");
            }else {
                iv_recomm_type.setVisibility(View.GONE);
                tv_recomm_type.setVisibility(View.GONE);
            }

            ll_college_recomm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //跳转至点播详情
                    Intent intent = new Intent(context,BzzbDeatilsActivity.class);
                    intent.putExtra("sid",sid);
                    context.startActivity(intent);
                }
            });
        }else if (type.equals("a2")){
            classs.add(lists.get(position));
            if (classs.size()==1){
                iv_recomm_type.setVisibility(View.VISIBLE);
                tv_recomm_type.setVisibility(View.VISIBLE);
                iv_recomm_type.setImageResource(R.mipmap.ic_college_class);
                tv_recomm_type.setText("课堂");
            }else {
                iv_recomm_type.setVisibility(View.GONE);
                tv_recomm_type.setVisibility(View.GONE);
            }

            ll_college_recomm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //跳转至点播详情
                    Intent intent = new Intent(context,ClassDetailsActivity.class);
                    intent.putExtra("sid",sid);
                    intent.putExtra("url",url);
                    intent.putExtra("img",img);
                    intent.putExtra("title",title);
                    context.startActivity(intent);
                }
            });
        }else if (type.equals("a3")){
            originals.add(lists.get(position));
            if (originals.size()==1){
                iv_recomm_type.setVisibility(View.VISIBLE);
                tv_recomm_type.setVisibility(View.VISIBLE);
                iv_recomm_type.setImageResource(R.mipmap.ic_college_yuanchuang);
                tv_recomm_type.setText("原创");
            }else {
                iv_recomm_type.setVisibility(View.GONE);
                tv_recomm_type.setVisibility(View.GONE);
            }

            ll_college_recomm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //跳转至读书详情
                    Intent intent = new Intent(context, OriginalDetailsActivity.class);
                    intent.putExtra("sid",sid);
                    context.startActivity(intent);
                }
            });
        }

        return view;
    }

}
