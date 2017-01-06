package com.player.mothercollege.me;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.player.mothercollege.R;
import com.player.mothercollege.activity.BaseActivity;
import com.player.mothercollege.adapter.AttAdapter;
import com.player.mothercollege.bean.AttBean;
import com.player.mothercollege.utils.ConfigUtils;
import com.player.mothercollege.utils.MyLog;
import com.player.mothercollege.utils.PrefUtils;
import com.player.mothercollege.view.MyUpDownListView;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/25.
 * 关注
 */
public class AttActivity extends BaseActivity implements View.OnClickListener,MyUpDownListView.OnRefreshListener {

    private static final int GET_ATT_DATA = 001;
    private static final int GET_MORE_DATA = 002;
    private Button btn_back;
    private TextView tv_details_title;
    private MyUpDownListView lv_att;
    private ImageView iv_refresh;
    private Button btn_refrsh;
    private List<AttBean.UsersBean> usersList = new ArrayList<>();
    private RequestQueue requestQueue;

    int lastIndex=0;
    boolean isRefresh = true;
    private int endNo;
    private String apptoken;
    private String uid;
    private AttAdapter adapter;



    @Override
    public void setContentView() {
        setContentView(R.layout.act_me_att);
        requestQueue = NoHttp.newRequestQueue();
    }

    @Override
    public void initViews() {
        btn_back = (Button) findViewById(R.id.btn_back);
        tv_details_title = (TextView) findViewById(R.id.tv_details_title);
        lv_att = (MyUpDownListView) findViewById(R.id.lv_att);
        iv_refresh = (ImageView) findViewById(R.id.iv_refresh);
        btn_refrsh = (Button) findViewById(R.id.btn_refrsh);

        tv_details_title.setText("我的关注");
        lv_att.setOnRefreshListener(this);
        if(isRefresh){
            initData();
        }
    }

    @Override
    public void initListeners() {
        btn_back.setOnClickListener(this);

    }

    @Override
    public void initData() {
        netWork();
    }

    private void netWork() {
        apptoken = PrefUtils.getString(this, "apptoken", "");
        uid = PrefUtils.getString(this, "uid", "");
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.ME_URL, RequestMethod.GET);
        request.add("op","myfollow");
        request.add("uid", uid);
        request.add("lastIndex",lastIndex+"");
        request.add("apptoken", apptoken);
        requestQueue.add(GET_ATT_DATA, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                iv_refresh.setVisibility(View.GONE);
                btn_refrsh.setVisibility(View.GONE);
                String info = response.get();
                MyLog.testLog("我的关注:"+info);
                parseJson(info);
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                iv_refresh.setVisibility(View.VISIBLE);
                btn_refrsh.setVisibility(View.VISIBLE);
                btn_refrsh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        netWork();
                    }
                });
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    private void parseJson(String info){
        Gson gson = new Gson();
        AttBean attBean = gson.fromJson(info, AttBean.class);
        if (attBean!=null){
            endNo = attBean.getLastIndex();//目标索引
            infos = attBean.getUsers();
            adapter = new AttAdapter(this,infos);
            lv_att.setAdapter(adapter);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                finish();
                break;
        }
    }

    @Override
    public void onDownPullRefresh() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                SystemClock.sleep(500);
                lastIndex = 0;
                netWork();
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                lv_att.hideHeaderView();
            }
        }.execute();
    }

    @Override
    public void onLoadingMore() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                SystemClock.sleep(500);
                setAddMoreData();
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                lv_att.hideFooterView();
            }
        }.execute();
    }

    private void setAddMoreData() {
        lastIndex = endNo;
        sendAddHomeLvRequest();
    }

    private List<AttBean.UsersBean> infos = new ArrayList<>();
    private void sendAddHomeLvRequest() {
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.ME_URL, RequestMethod.GET);
        request.add("op","myfollow");
        request.add("uid", uid);
        request.add("lastIndex",lastIndex+"");
        request.add("apptoken", apptoken);
        requestQueue.add(GET_MORE_DATA, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                Gson gson = new Gson();
                AttBean attBean = gson.fromJson(info, AttBean.class);
                if (lastIndex!=0) {
                    endNo = attBean.getLastIndex();
                    lastIndex = endNo;
                    usersList = attBean.getUsers();
                    infos.addAll(usersList);
                    adapter.notifyDataSetChanged();
                } else {
//                    toast("没有更多数据");
                    Toast.makeText(AttActivity.this,"没有更多数据了",Toast.LENGTH_SHORT).show();
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
}
