package com.player.mothercollege.me.details;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.player.mothercollege.R;
import com.player.mothercollege.activity.BaseActivity;
import com.player.mothercollege.bean.MessageNullBean;
import com.player.mothercollege.bean.SystemMessageBean;
import com.player.mothercollege.utils.ConfigUtils;
import com.player.mothercollege.utils.MyLog;
import com.player.mothercollege.utils.PrefUtils;
import com.tencent.smtt.sdk.WebView;
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
 * 系统通知
 */
public class SystemMessageActivity extends BaseActivity implements View.OnClickListener {

    private static final int GET_SYSMESSAGE_DATA = 001;
    private static final int POST_NULL_DATA = 002;
    private static final int POST_READ_DATA =003;
    private Button btn_mycomment_null;
    private TextView tv_mycomment_title,tv_mycomment_null;
    private ListView lv_mycomment;
    private RequestQueue requestQueue;
    private SystemMessageAdapter adapter;
    private List<SystemMessageBean.NoticesBean> noticesList = new ArrayList<>();
    private String apptoken;
    private String uid;

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

        tv_mycomment_title.setText("系统消息");
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
        apptoken = PrefUtils.getString(this, "apptoken", "");
        uid = PrefUtils.getString(this, "uid", "");
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.ME_URL, RequestMethod.GET);
        request.add("apptoken", apptoken);
        request.add("op","noticeSystem");
        request.add("uid", uid);
        requestQueue.add(GET_SYSMESSAGE_DATA, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                MyLog.testLog("系统消息:"+info);
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
        SystemMessageBean systemMessageBean = gson.fromJson(info, SystemMessageBean.class);
        noticesList = systemMessageBean.getNotices();
        adapter = new SystemMessageAdapter(SystemMessageActivity.this, noticesList);
        lv_mycomment.setAdapter(adapter);
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

    private ProgressDialog pd;
    private void postNull() {
        //把集合中的nids拿出来
        String nidd = "";
        for (int i =0;i<nids.size();i++){
            String nid =  nids.get(i);
            nidd = nidd+","+nid;
            MyLog.testLog("清空消息nid:"+nidd);
        }

        Request<String> request = NoHttp.createStringRequest(ConfigUtils.ME_URL, RequestMethod.POST);
        request.add("op","removenotice");
        request.add("apptoken",apptoken);
        request.add("uid",uid);
        request.add("nids",nidd);
        request.add("optype","2");
        request.add("ntype","1");
        requestQueue.add(POST_NULL_DATA, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
                pd = new ProgressDialog(SystemMessageActivity.this);
                pd.show();
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                Gson gson = new Gson();
                MessageNullBean messageNullBean = gson.fromJson(info, MessageNullBean.class);
                boolean isSuccess = messageNullBean.isIsSuccess();
                if (isSuccess){
                    noticesList.removeAll(noticesList);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(SystemMessageActivity.this,"已清空!",Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }else {
                    Toast.makeText(SystemMessageActivity.this,"处理失败，请稍候再试!",Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                pd.dismiss();
            }

            @Override
            public void onFinish(int what) {
                pd.dismiss();
            }
        });
    }

    private List<String> nids = new ArrayList<>();

    public class SystemMessageAdapter extends BaseAdapter {

        private Context context;
        private WebView webView;
        private List<SystemMessageBean.NoticesBean> lists = new ArrayList<>();
        private ImageView iv_sys_look;

        public SystemMessageAdapter(Context context,List lists) {
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
            SystemMessageHolder holder = null;
            if (convertView==null){
                view = View.inflate(context, R.layout.item_message_sys,null);
                holder = new SystemMessageHolder();
                holder.tv_sys_name = (TextView) view.findViewById(R.id.tv_sys_name);
                holder.tv_sys_time = (TextView) view.findViewById(R.id.tv_sys_time);
                holder.tv_sys_desc = (TextView) view.findViewById(R.id.tv_sys_desc);
                holder.iv_sys_look = (ImageView) view.findViewById(R.id.iv_sys_look);
                holder.rl_message_sys = (RelativeLayout) view.findViewById(R.id.rl_message_sys);
                view.setTag(holder);
            }else {
                view = convertView;
                holder = (SystemMessageHolder) view.getTag();
            }

            holder.tv_sys_name.setText(lists.get(position).getFromUniceName()+"说:");
            holder.tv_sys_time.setText(lists.get(position).getDatetime());
            holder.tv_sys_desc.setText(lists.get(position).getSourceText());
            int nid = lists.get(position).getNid();
            nids.add(nid+"");
            readItem(nid);
            final String rcontent = lists.get(position).getRcontent();
            holder.rl_message_sys.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, WebViewDeatilsActivity.class);
                    intent.putExtra("rcontent",rcontent);
                    context.startActivity(intent);
                }
            });
            return view;
        }

        class SystemMessageHolder{
            private TextView tv_sys_name;
            private TextView tv_sys_time;
            private TextView tv_sys_desc;
            private ImageView iv_sys_look;
            private RelativeLayout rl_message_sys;
        }
    }

    private void readItem(int nid) {
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.ME_URL, RequestMethod.POST);
        request.add("op","removenotice");
        request.add("apptoken",apptoken);
        request.add("uid",uid);
        request.add("optype","1");
        request.add("nids",nid+"");
        request.add("ntype","1");
        requestQueue.add(POST_READ_DATA, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                MyLog.testLog("已读:"+info);

            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

}
