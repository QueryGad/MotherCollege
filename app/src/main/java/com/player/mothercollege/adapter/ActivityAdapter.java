package com.player.mothercollege.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.player.mothercollege.R;
import com.player.mothercollege.bean.ActivityAddressBean;
import com.player.mothercollege.bean.ActivityBean;
import com.player.mothercollege.utils.DensityUtils;
import com.player.mothercollege.utils.ScreenUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/31.
 */
public class ActivityAdapter extends BaseAdapter{

    private Context context;
    private List<ActivityBean.ActivesBean> lists = new ArrayList<>();
    private List<ActivityAddressBean.CitysBean> citysList = new ArrayList<>();
    private List<String> city = new ArrayList<>();

    public ActivityAdapter(Context context,List lists,List citysList) {
        super();
        this.context = context;
        this.lists = lists;
        this.citysList = citysList;
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
        for (int i=0;i<citysList.size();i++){
            city.clear();
            String citys = citysList.get(i).getCity();
            String keys = citysList.get(i).getKey();
            city.add(citys);
        }
        View view = null;
        ActivityHolder ah = null;
        if (convertView==null){
            view = View.inflate(context, R.layout.item_unity_activity,null);
            ah = new ActivityHolder();
            ah.tv_recomm_activity = (TextView) view.findViewById(R.id.tv_recomm_activity);
            ah.tv_recomm_activity_title = (TextView) view.findViewById(R.id.tv_recomm_activity_title);
            ah.tv_recomm_activity_place = (TextView) view.findViewById(R.id.tv_recomm_activity_place);
            ah.tv_recomm_activity_viewCount = (TextView) view.findViewById(R.id.tv_recomm_activity_viewCount);
            ah.iv_recomm_activity = (ImageView) view.findViewById(R.id.iv_recomm_activity);
            ah.spinner = (Spinner) view.findViewById(R.id.spinner);
            ah.ll_recomm_activity_zhanwei = (LinearLayout) view.findViewById(R.id.ll_recomm_activity_zhanwei);
            view.setTag(ah);
        }else {
            view = convertView;
            ah = (ActivityHolder) view.getTag();
        }
        if (position==0){
            ah.tv_recomm_activity.setText("推荐活动");
            ah.ll_recomm_activity_zhanwei.setVisibility(View.VISIBLE);
            ah.spinner.setVisibility(View.VISIBLE);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,city);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            ah.spinner.setAdapter(adapter);
//            MyLog.testLog("城市:"+city.get(position));
        }else {
            ah.tv_recomm_activity.setText("更多活动");
            ah.ll_recomm_activity_zhanwei.setVisibility(View.GONE);
            ah.spinner.setVisibility(View.GONE);
        }
        Picasso.with(context).load(lists.get(position).getImg())
                .resize(DensityUtils.dip2px(context, ScreenUtils.getWidth(context)),DensityUtils.dip2px(context,350f))
                .centerCrop().into(ah.iv_recomm_activity);
        ah.tv_recomm_activity_title.setText(lists.get(position).getTitle());
        ah.tv_recomm_activity_place.setText(lists.get(position).getCity());
        ah.tv_recomm_activity_viewCount.setText(lists.get(position).getJoinCount()+"人");

        return view;
    }

    class ActivityHolder{
        private TextView tv_recomm_activity,tv_recomm_activity_title,tv_recomm_activity_place,tv_recomm_activity_viewCount;
        private ImageView iv_recomm_activity;
        private Spinner spinner;
        private LinearLayout ll_recomm_activity_zhanwei;

    }
}
