package com.player.mothercollege.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.player.mothercollege.R;
import com.player.mothercollege.bean.RecommBean;
import com.player.mothercollege.utils.DensityUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/26.
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
        //服务器传回8个数据，前端需要分成四组

        View view = null;
        RecommHolder rh = null;
        if (convertView==null){
            view = View.inflate(context, R.layout.item_college_recomm,null);
            rh = new RecommHolder();
            rh.iv_recomm_one = (ImageView) view.findViewById(R.id.iv_recomm_one);
            rh.tv_recomm_title_one = (TextView) view.findViewById(R.id.tv_recomm_title_one);
            rh.tv_recomm_editor_one = (TextView) view.findViewById(R.id.tv_recomm_editor_one);
            rh.tv_recomm_viewCount_one = (TextView) view.findViewById(R.id.tv_recomm_viewCount_one);
            view.setTag(rh);
        }else {
            view = convertView;
            rh = (RecommHolder) view.getTag();
        }
        Picasso.with(context).load(lists.get(position).getImg())
                .resize(DensityUtils.dip2px(context,116f),DensityUtils.dip2px(context,63.5f))
                .centerCrop().into(rh.iv_recomm_one);
        rh.tv_recomm_title_one.setText(lists.get(position).getTitle());
        rh.tv_recomm_editor_one.setText(lists.get(position).getEditor());
        rh.tv_recomm_viewCount_one.setText(lists.get(position).getViewCount());
        return view;
    }

    class RecommHolder{
        public ImageView iv_recomm_title,iv_recomm_one,iv_recomm_two;
        public TextView tv_recomm_title,tv_recomm_title_one,tv_recomm_title_two,tv_recomm_xm_one,tv_recomm_xm_two,
                tv_recomm_editor_one,tv_recomm_editor_two,tv_recomm_viewCount_one,tv_recomm_viewCount_two;

    }
}
