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
import com.player.mothercollege.utils.MyLog;
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

        View view = View.inflate(context, R.layout.item_college_recomm,null);

        LinearLayout ll_recomm_ds_title = (LinearLayout) view.findViewById(R.id.ll_recomm_ds_title);
        LinearLayout ll_recomm_zb_title = (LinearLayout) view.findViewById(R.id.ll_recomm_zb_title);
        LinearLayout ll_recomm_kt_title = (LinearLayout) view.findViewById(R.id.ll_recomm_kt_title);
        LinearLayout ll_recomm_yc_title = (LinearLayout) view.findViewById(R.id.ll_recomm_yc_title);

        LinearLayout ll_recomm_dsview = (LinearLayout) view.findViewById(R.id.ll_recomm_dsview);
        LinearLayout ll_recomm_zbview = (LinearLayout) view.findViewById(R.id.ll_recomm_zbview);
        LinearLayout ll_recomm_ktview = (LinearLayout) view.findViewById(R.id.ll_recomm_ktview);
        LinearLayout ll_recomm_ycview = (LinearLayout) view.findViewById(R.id.ll_recomm_ycview);


        View viewChild = View.inflate(context,R.layout.adapter_recommd_list,null);
        ImageView iv_recomm = (ImageView) viewChild.findViewById(R.id.iv_recomm);
        TextView tv_recomm_title = (TextView) viewChild.findViewById(R.id.tv_recomm_title);
        TextView tv_recomm_date = (TextView) viewChild.findViewById(R.id.tv_recomm_date);
        TextView tv_recomm_money = (TextView) viewChild.findViewById(R.id.tv_recomm_money);
        TextView tv_recomm_editor = (TextView) viewChild.findViewById(R.id.tv_recomm_editor);
        TextView tv_recomm_viewCount = (TextView) viewChild.findViewById(R.id.tv_recomm_viewCount);
        LinearLayout ll_college_recomm = (LinearLayout) viewChild.findViewById(R.id.ll_college_recomm);
        Picasso.with(context).load(lists.get(position).getImg())
                .resize(DensityUtils.dip2px(context,116f),DensityUtils.dip2px(context,63.5f))
                .centerCrop().into(iv_recomm);
        tv_recomm_title.setText(lists.get(position).getTitle());
        tv_recomm_date.setText(lists.get(position).getDate());
        String aState = lists.get(position).getAState();
        if (aState.equals("0")){
            tv_recomm_money.setVisibility(View.GONE);
        }else if (aState.equals("1")){
            tv_recomm_money.setText("限免");
        }else if (aState.equals("2")){
            tv_recomm_money.setText("收费");
        }
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

            ll_recomm_ds_title.setVisibility(View.VISIBLE);
            ll_recomm_zb_title.setVisibility(View.GONE);
            ll_recomm_kt_title.setVisibility(View.GONE);
            ll_recomm_yc_title.setVisibility(View.GONE);

            ll_recomm_dsview.addView(viewChild);
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

            ll_recomm_ds_title.setVisibility(View.GONE);
            ll_recomm_zb_title.setVisibility(View.VISIBLE);
            ll_recomm_kt_title.setVisibility(View.GONE);
            ll_recomm_yc_title.setVisibility(View.GONE);

            ll_recomm_zbview.addView(viewChild);
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

            ll_recomm_ds_title.setVisibility(View.GONE);
            ll_recomm_zb_title.setVisibility(View.GONE);
            ll_recomm_kt_title.setVisibility(View.VISIBLE);
            ll_recomm_yc_title.setVisibility(View.GONE);

            ll_recomm_ktview.addView(viewChild);
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
            MyLog.testLog("消除");
            ll_recomm_ds_title.setVisibility(View.GONE);
            ll_recomm_zb_title.setVisibility(View.GONE);
            ll_recomm_kt_title.setVisibility(View.GONE);
            ll_recomm_yc_title.setVisibility(View.VISIBLE);

            ll_recomm_ycview.addView(viewChild);
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
