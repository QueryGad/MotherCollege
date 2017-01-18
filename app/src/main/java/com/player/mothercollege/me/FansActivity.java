package com.player.mothercollege.me;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.player.mothercollege.R;
import com.player.mothercollege.activity.BaseActivity;
import com.player.mothercollege.adapter.FansAdapter;
import com.player.mothercollege.bean.FansBean;
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
 * 粉丝
 */
public class FansActivity extends BaseActivity implements View.OnClickListener,MyUpDownListView.OnRefreshListener {

    private static final int GET_FANS_DATA = 001;
    private static final int GET_MORE_DATA = 002;
    private Button btn_back;
    private TextView tv_details_title;
    private MyUpDownListView lv_fans;
    private ImageView iv_refresh;
    private Button btn_refrsh;
    private List<FansBean.UsersBean> usersList = new ArrayList<>();
    private RequestQueue requestQueue;
    private LinearLayout ll_fans_no;

    int lastIndex=0;
    boolean isRefresh = true;
    private int endNo;
    private FansAdapter adapter;
    private String apptoken;
    private String uid;

    @Override
    public void setContentView() {
        setContentView(R.layout.act_me_fans);
        requestQueue = NoHttp.newRequestQueue();
    }

    @Override
    public void initViews() {
        btn_back = (Button) findViewById(R.id.btn_back);
        tv_details_title = (TextView) findViewById(R.id.tv_details_title);
        lv_fans = (MyUpDownListView) findViewById(R.id.lv_fans);
        iv_refresh = (ImageView) findViewById(R.id.iv_refresh);
        btn_refrsh = (Button) findViewById(R.id.btn_refrsh);
        ll_fans_no = (LinearLayout) findViewById(R.id.ll_fans_no);

        tv_details_title.setText("我的粉丝");
        lv_fans.setOnRefreshListener(this);
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
        uid = PrefUtils.getString(FansActivity.this, "uid", "");
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.ME_URL, RequestMethod.GET);
        request.add("op","myfans");
        request.add("uid", uid);
        request.add("lastIndex",lastIndex+"");
        request.add("apptoken", apptoken);
        requestQueue.add(GET_FANS_DATA, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                iv_refresh.setVisibility(View.GONE);
                btn_refrsh.setVisibility(View.GONE);
                String info = response.get();
                MyLog.testLog("我的粉丝:"+info);
                if (info!=null){
                    parseJson(info);
                }

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
        FansBean fansBean = gson.fromJson(info, FansBean.class);

        if (fansBean!=null){
            endNo = fansBean.getLastIndex();//目标索引
            infos = fansBean.getUsers();
            if (infos.size()==0){
                ll_fans_no.setVisibility(View.VISIBLE);
            }else {
                adapter = new FansAdapter(this,infos);
                lv_fans.setAdapter(adapter);
            }

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
                lv_fans.hideHeaderView();
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
                lv_fans.hideFooterView();
            }
        }.execute();
    }

    private void setAddMoreData() {
        lastIndex = endNo;
        sendAddHomeLvRequest();
    }

    private List<FansBean.UsersBean> infos = new ArrayList<>();
    private void sendAddHomeLvRequest() {
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.ME_URL, RequestMethod.GET);
        request.add("op","myfans");
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
                FansBean fansBean = gson.fromJson(info, FansBean.class);
                if (lastIndex!=0) {
                    endNo = fansBean.getLastIndex();
                    lastIndex = endNo;
                    usersList = fansBean.getUsers();
                    infos.addAll(usersList);
                    adapter.notifyDataSetChanged();
                } else {
//                    toast("没有更多数据");
                    Toast.makeText(FansActivity.this,"没有更多数据了",Toast.LENGTH_SHORT).show();
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
