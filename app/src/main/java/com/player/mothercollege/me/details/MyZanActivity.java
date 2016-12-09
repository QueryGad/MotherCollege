package com.player.mothercollege.me.details;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.gson.Gson;
import com.player.mothercollege.R;
import com.player.mothercollege.activity.BaseActivity;
import com.player.mothercollege.bean.MessageNullBean;
import com.player.mothercollege.bean.MyZanBean;
import com.player.mothercollege.unity.details.HotArticleDetailsActivity;
import com.player.mothercollege.utils.ConfigUtils;
import com.player.mothercollege.utils.DensityUtils;
import com.player.mothercollege.utils.MyLog;
import com.player.mothercollege.utils.PrefUtils;
import com.player.mothercollege.view.GlideCircleTransform;
import com.squareup.picasso.Picasso;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/6.
 * 我的赞
 */
public class MyZanActivity extends BaseActivity implements View.OnClickListener {

    private static final int GET_MYZAN_DATA = 001;
    private static final int POST_NULL_DATA = 002;
    private static final int POST_READ_DATA = 003;
    private Button btn_mycomment_null;
    private TextView tv_mycomment_title,tv_mycomment_null;
    private ListView lv_mycomment;
    private RequestQueue requestQueue;
    private MyZanAdapter adapter;

    @Override
    public void setContentView() {
        setContentView(R.layout.act_me_mycomment);
        requestQueue = NoHttp.newRequestQueue();
    }

    @Override
    public void initViews() {
        btn_mycomment_null = (Button) findViewById(R.id.btn_mycomment_null);
        tv_mycomment_title = (TextView) findViewById(R.id.tv_mycomment_title);
        tv_mycomment_null = (TextView) findViewById(R.id.tv_mycomment_null);
        lv_mycomment = (ListView) findViewById(R.id.lv_mycomment);

        tv_mycomment_title.setText("赞");
    }

    @Override
    public void initListeners() {
        btn_mycomment_null.setOnClickListener(this);
        tv_mycomment_null.setOnClickListener(this);
    }

    @Override
    public void initData() {
        netWork();
    }

    private void netWork() {
        String apptoken = PrefUtils.getString(this, "apptoken", "");
        String uid = PrefUtils.getString(this, "uid", "null");
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.ME_URL, RequestMethod.GET);
        request.add("apptoken",apptoken);
        request.add("op","noticeZlike");
        request.add("uid",uid);
        requestQueue.add(GET_MYZAN_DATA, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                MyLog.testLog("我的赞:"+info);
                parseJson(info);
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    private void parseJson(String info) {
        Gson gson = new Gson();
        MyZanBean myZanBean = gson.fromJson(info, MyZanBean.class);
        List<MyZanBean.NoticesBean> noticesList = myZanBean.getNotices();
        adapter = new MyZanAdapter(MyZanActivity.this,noticesList);
        lv_mycomment.setAdapter(adapter);
        allRead();
    }

    private void allRead() {
        //把集合中的nids拿出来
        String nidd = "";
        MyLog.testLog("我已读了多少个参数:"+nids.size());
        for (int i =0;i<nids.size();i++){
            String nid =  nids.get(i);
            nidd = nidd+","+nid;
        }
        String apptoken = PrefUtils.getString(this, "apptoken", "");
        String uid = PrefUtils.getString(this, "uid", "null");
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.ME_URL, RequestMethod.POST);
        request.add("op","removenotice");
        request.add("apptoken",apptoken);
        request.add("uid",uid);
        request.add("optype","1");
        request.add("nids",nidd);
        MyLog.testLog("nids:"+nidd);
        request.add("ntype","3");
        requestQueue.add(POST_READ_DATA, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                MyLog.testLog("已读:"+info);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_mycomment_null:
                finish();
                break;
            case R.id.tv_mycomment_null:
                postNull();
                break;
        }
    }

    private void postNull() {

        //把集合中的nids拿出来
        String nidd = "";
        for (int i =0;i<nids.size();i++){
            String nid =  nids.get(i);
            nidd = nidd+","+nid;
            MyLog.testLog("清空消息nid:"+nidd);
        }

        String apptoken = PrefUtils.getString(this, "apptoken", "");
        String uid = PrefUtils.getString(this, "uid", "null");
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.ME_URL, RequestMethod.POST);
        request.add("op","removenotice");
        request.add("apptoken",apptoken);
        request.add("uid",uid);
        request.add("nids",nidd);
        request.add("optype","2");
        request.add("ntype","3");
        requestQueue.add(POST_NULL_DATA, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                Gson gson = new Gson();
                MessageNullBean messageNullBean = gson.fromJson(info, MessageNullBean.class);
                boolean isSuccess = messageNullBean.isIsSuccess();
                if (isSuccess){
                    adapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(MyZanActivity.this,"处理失败，请稍候再试!",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    private List<String> nids = new ArrayList<>();

    public class MyZanAdapter extends BaseAdapter {

        private Context context;
        private RequestManager glideRequest;
        private List<MyZanBean.NoticesBean> lists = new ArrayList<>();

        public MyZanAdapter(Context context,List lists) {
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
            MyZanHolder holder = null;
            if (convertView==null){
                view = View.inflate(context, R.layout.item_message_zan,null);
                holder = new MyZanHolder();
                holder.iv_myzan_icon = (ImageView) view.findViewById(R.id.iv_myzan_icon);
                holder.tv_myzan_name = (TextView) view.findViewById(R.id.tv_myzan_name);
                holder.tv_myzan_date = (TextView) view.findViewById(R.id.tv_myzan_date);
                holder.tv_myzan_desc = (TextView) view.findViewById(R.id.tv_myzan_desc);
                holder.iv_myzan_desc = (ImageView) view.findViewById(R.id.iv_myzan_desc);
                holder.ll_item_message = (LinearLayout) view.findViewById(R.id.ll_item_message);
                view.setTag(holder);
            }else {
                view = convertView;
                holder = (MyZanHolder) view.getTag();
            }
            glideRequest = Glide.with(context);
            glideRequest.load(lists.get(position).getFromUicon())
                    .transform(new GlideCircleTransform(context)).into(holder.iv_myzan_icon);
            holder.tv_myzan_name.setText(lists.get(position).getFromUniceName());
            holder.tv_myzan_date.setText(lists.get(position).getDatetime());
            String sourceText = lists.get(position).getSourceText();
            String sourcePic = lists.get(position).getSourcePic();
            holder.tv_myzan_desc.setText(sourceText);
            final String sourceID = lists.get(position).getSourceID();
            int sourceType = lists.get(position).getSourceType();
            if (sourceType==21){
                //帖子
                holder.ll_item_message.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, HotArticleDetailsActivity.class);
                        intent.putExtra("tid",sourceID);
                        startActivity(intent);
                    }
                });

            }else {
                //问题
                holder.ll_item_message.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, HotArticleDetailsActivity.class);
                        intent.putExtra("tid",sourceID);
                        startActivity(intent);
                    }
                });
            }
            int nid = lists.get(position).getNid();
            nids.add(nid+"");
            Picasso.with(context).load(sourcePic)
                    .resize(DensityUtils.dip2px(context,50),DensityUtils.dip2px(context,58))
                    .centerCrop().into(holder.iv_myzan_desc);
            return view;
        }

        class MyZanHolder{
            private ImageView iv_myzan_icon;
            private TextView tv_myzan_name,tv_myzan_date;
            private TextView tv_myzan_desc;
            private ImageView iv_myzan_desc;
            private LinearLayout ll_item_message;
        }
    }

}
