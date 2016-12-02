package com.player.mothercollege.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.player.mothercollege.R;
import com.player.mothercollege.bean.FastBean;
import com.player.mothercollege.unity.details.FastInquiryDetailsActivity;
import com.player.mothercollege.utils.DateUtils;
import com.player.mothercollege.utils.MyLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/1.
 */
public class FastInquiryAdapter extends BaseAdapter{

    private Context context;
    private List<FastBean.QustionsBean> lists = new ArrayList<>();

    public FastInquiryAdapter(Context context,List lists) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;
        FastInquiryHolder flh = null;
        if (convertView==null){
            view = View.inflate(context, R.layout.item_unity_fasstinquiry,null);
            flh = new FastInquiryHolder();
            flh.tv_fastinquiry_title = (TextView) view.findViewById(R.id.tv_fastinquiry_title);
            flh.tv_fastinquiry_time = (TextView) view.findViewById(R.id.tv_fastinquiry_time);
            flh.tv_fastinquiry_desc = (TextView) view.findViewById(R.id.tv_fastinquiry_desc);
            flh.tv_fastinquiry_conmment = (TextView) view.findViewById(R.id.tv_fastinquiry_conmment);
            flh.tv_fastinquiry_daname = (TextView) view.findViewById(R.id.tv_fastinquiry_daname);
            flh.tv_fastinquiry_dacontent = (TextView) view.findViewById(R.id.tv_fastinquiry_dacontent);
            flh.rl_item_fastinquiry = (RelativeLayout) view.findViewById(R.id.rl_item_fastinquiry);
            view.setTag(flh);
        }else {
            view = convertView;
            flh = (FastInquiryHolder) view.getTag();
        }

        flh.tv_fastinquiry_title.setText(lists.get(position).getTitle());
        String time = lists.get(position).getDate();
        flh.tv_fastinquiry_time.setText(DateUtils.getStandardDate(time));
        flh.tv_fastinquiry_desc.setText(lists.get(position).getQusition());
        flh.tv_fastinquiry_conmment.setText(lists.size()+"");
        flh.tv_fastinquiry_daname.setText(lists.get(position).getUnicename()+":");
        flh.tv_fastinquiry_dacontent.setText(lists.get(position).getAsw_content());
        final String qid = lists.get(position).getQid();
        MyLog.testLog("我的qid："+qid);
        flh.rl_item_fastinquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,FastInquiryDetailsActivity.class);
                intent.putExtra("qid",qid);
                context.startActivity(intent);
            }
        });
        return view;
    }

    class FastInquiryHolder {
        private TextView tv_fastinquiry_title,tv_fastinquiry_time,tv_fastinquiry_desc,
                tv_fastinquiry_conmment,tv_fastinquiry_daname,tv_fastinquiry_dacontent;
        private RelativeLayout rl_item_fastinquiry;
    }
}
