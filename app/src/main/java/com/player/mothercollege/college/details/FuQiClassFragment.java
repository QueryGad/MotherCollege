package com.player.mothercollege.college.details;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.player.mothercollege.R;
import com.player.mothercollege.bean.ClassBean;
import com.player.mothercollege.utils.CacheUtils;
import com.player.mothercollege.utils.ConfigUtils;
import com.player.mothercollege.utils.DensityUtils;
import com.player.mothercollege.utils.MyLog;
import com.player.mothercollege.utils.PrefUtils;
import com.squareup.picasso.Picasso;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/7.
 */
public class FuQiClassFragment extends Fragment{
    private static final int GET_CLASS_DATA = 001;
    private View view;
    private RequestQueue requestQueue;
    private ListView lv_class;
    private List<ClassBean.TabClassBean.VediosBean> fuQiList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.frg_college_classcontent,null);
        requestQueue = NoHttp.newRequestQueue();
        initView();
        initData();
        return view;
    }

    private void initView() {
        lv_class = (ListView) view.findViewById(R.id.lv_class);
    }

    private void initData() {
        String cacheJson = CacheUtils.getCache(getActivity(), ConfigUtils.COLLEGE_URL + "class_fuqi");
        if (!TextUtils.isEmpty(cacheJson)){
            parseJson(cacheJson);
        }
        netWork();
    }

    private void netWork() {

        String apptoken = PrefUtils.getString(getActivity(),"apptoken","");
        String uid = PrefUtils.getString(getActivity(), "uid", "null");
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.COLLEGE_URL);
        request.add("op","class");
        request.add("apptoken",apptoken);
        request.add("udi",uid);
        requestQueue.add(GET_CLASS_DATA, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                MyLog.testLog("课堂数据"+info);
                parseJson(info);
                CacheUtils.saveCache(getActivity(),ConfigUtils.COLLEGE_URL + "class_fuqi",info);
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    private void parseJson(String info){
        Gson gson = new Gson();
        ClassBean classBean = gson.fromJson(info, ClassBean.class);
        List<ClassBean.TabClassBean> tabClass = classBean.getTabClass();
        fuQiList = tabClass.get(1).getVedios();
        FuQiClassAdapter adapter = new FuQiClassAdapter();
        lv_class.setAdapter(adapter);
    }

    class FuQiClassAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return fuQiList.size();
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
            FuQiClassHolder holder = null;
            if (convertView==null){
                view = View.inflate(getActivity(),R.layout.item_college_class,null);
                holder = new FuQiClassHolder();
                holder.ll_class_item = (LinearLayout) view.findViewById(R.id.ll_class_item);
                holder.iv_classitem = (ImageView) view.findViewById(R.id.iv_classitem);
                holder.tv_classitem_date = (TextView) view.findViewById(R.id.tv_classitem_date);
                holder.tv_classitem_title = (TextView) view.findViewById(R.id.tv_classitem_title);
                holder.tv_classitem_money = (TextView) view.findViewById(R.id.tv_classitem_money);
                holder.tv_classitem_editor = (TextView) view.findViewById(R.id.tv_classitem_editor);
                holder.tv_classitem_viewCount = (TextView) view.findViewById(R.id.tv_classitem_viewCount);
                view.setTag(holder);
            }else {
                view = convertView;
                holder = (FuQiClassHolder) view.getTag();
            }
            Picasso.with(getActivity()).load(fuQiList.get(position).getImg())
                    .resize(DensityUtils.dip2px(getActivity(),116f),DensityUtils.dip2px(getActivity(),63.5f))
                    .centerCrop().into(holder.iv_classitem);
            //切割字符串，得到遮罩时间
            String arrDate = fuQiList.get(position).getDate();
            String[] split = arrDate.split(" ");
            holder.tv_classitem_date.setText(split[0]);
//            holder.tv_classitem_date.setText(fuQiList.get(position).getDate());
            holder.tv_classitem_title.setText(fuQiList.get(position).getTitle());
            holder.tv_classitem_money.setText(fuQiList.get(position).getPrice()+"");
            holder.tv_classitem_editor.setText(fuQiList.get(position).getEditor());
            holder.tv_classitem_editor.setTextColor(Color.RED);
            holder.tv_classitem_viewCount.setText(fuQiList.get(position).getViewCount()+"");
            final String url = fuQiList.get(position).getUrl();
            final String title = fuQiList.get(position).getTitle();
            final String img = fuQiList.get(position).getImg();
            holder.ll_class_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String sid = fuQiList.get(position).getSid();
                    Intent intent = new Intent(getActivity(),ClassDetailsActivity.class);
                    intent.putExtra("sid",sid);
                    intent.putExtra("url",url);
                    intent.putExtra("img",img);
                    intent.putExtra("title",title);
                    startActivity(intent);
                }
            });
            return view;
        }
    }

    class FuQiClassHolder{
        private LinearLayout ll_class_item;
        private ImageView iv_classitem;
        private TextView tv_classitem_date,tv_classitem_title,tv_classitem_money,tv_classitem_editor,tv_classitem_viewCount;
    }
}
