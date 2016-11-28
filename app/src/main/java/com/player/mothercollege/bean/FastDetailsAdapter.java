package com.player.mothercollege.bean;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.player.mothercollege.R;
import com.player.mothercollege.utils.DateUtils;
import com.player.mothercollege.view.GlideCircleTransform;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/1.
 */
public class FastDetailsAdapter extends BaseAdapter{

    private Context context;
    private List<FastDetailsBean.AnswerBean> lists = new ArrayList<>();
    private RequestManager glideRequest;

    public FastDetailsAdapter(Context context,List lists) {
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
        FastDetailsHolder holder = null;
        if (convertView==null){
            view = View.inflate(context, R.layout.item_unity_fastdetails,null);
            holder = new FastDetailsHolder();
            holder.iv_fastdetails_other = (ImageView) view.findViewById(R.id.iv_fastdetails_other);
            holder.tv_fastdetails_name = (TextView) view.findViewById(R.id.tv_fastdetails_name);
            holder.tv_fastdetails_time = (TextView) view.findViewById(R.id.tv_fastdetails_time);
            holder.tv_fastdetails_content = (TextView) view.findViewById(R.id.tv_fastdetails_content);
            view.setTag(holder);
        }else {
            view = convertView;
            holder = (FastDetailsHolder) view.getTag();
        }
        glideRequest = Glide.with(context);
        glideRequest.load(lists.get(position).getUicon())
                .transform(new GlideCircleTransform(context)).into(holder.iv_fastdetails_other);
        holder.tv_fastdetails_name.setText(lists.get(position).getUnicename());
        String time = lists.get(position).getDate();
        holder.tv_fastdetails_time.setText(DateUtils.getStandardDate(time));
        holder.tv_fastdetails_content.setText(lists.get(position).getAnswer());
        return view;
    }

    class FastDetailsHolder {
        private ImageView iv_fastdetails_other;
        private TextView tv_fastdetails_name,tv_fastdetails_time,tv_fastdetails_content;
    }
}
