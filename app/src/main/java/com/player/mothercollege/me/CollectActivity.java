package com.player.mothercollege.me;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.player.mothercollege.R;
import com.player.mothercollege.activity.BaseActivity;
import com.player.mothercollege.adapter.CollectAdapter;
import com.player.mothercollege.bean.CollectBean;
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
 * 我的收藏
 */
public class CollectActivity extends BaseActivity implements View.OnClickListener,MyUpDownListView.OnRefreshListener {

    private static final int GET_COLLECT_DATA = 001;
    private static final int GET_MORE_DATA = 002;
    private Button btn_back;
    private TextView tv_details_title;
    private RequestQueue requestQueue;
    private MyUpDownListView lv_college;

    int lastIndex=0;
    boolean isRefresh = true;
    private int endNo;
    private String apptoken;
    private String uid;
    private CollectAdapter adapter;
    private List<CollectBean.MyPayClassBean> myPayClassList;


    @Override
    public void setContentView() {
       setContentView(R.layout.act_me_college);
        requestQueue = NoHttp.newRequestQueue();
    }

    @Override
    public void initViews() {
        btn_back = (Button) findViewById(R.id.btn_back);
        tv_details_title = (TextView) findViewById(R.id.tv_details_title);
        lv_college = (MyUpDownListView) findViewById(R.id.lv_college);

        tv_details_title.setText("我的收藏");

        lv_college.setOnRefreshListener(this);
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
        request.add("apptoken", apptoken);
        request.add("op","mykeep");
        request.add("uid", uid);
        request.add("lastIndex",lastIndex+"");
        requestQueue.add(GET_COLLECT_DATA, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();

                if (info!=null){
                    parseJson(info);
                }
                MyLog.testLog("我的收藏"+info);
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
        CollectBean collectBean = gson.fromJson(info, CollectBean.class);

        if (collectBean!=null){
            endNo = collectBean.getLastIndex();//目标索引
            infos = collectBean.getMyPayClass();
            adapter =  new CollectAdapter(this,infos);
            lv_college.setAdapter(adapter);
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
                lv_college.hideHeaderView();
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
                lv_college.hideFooterView();
            }
        }.execute();
    }

    private void setAddMoreData() {
        lastIndex = endNo;
        sendAddHomeLvRequest();
    }

    private List<CollectBean.MyPayClassBean> infos = new ArrayList<>();
    private void sendAddHomeLvRequest() {
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.ME_URL, RequestMethod.GET);
        request.add("apptoken", apptoken);
        request.add("op","mykeep");
        request.add("uid", uid);
        request.add("lastIndex",lastIndex+"");
        requestQueue.add(GET_MORE_DATA, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                Gson gson = new Gson();
                CollectBean collectBean = gson.fromJson(info, CollectBean.class);
                if (lastIndex!=0) {
                    endNo = collectBean.getLastIndex();
                    lastIndex = endNo;
                    myPayClassList = collectBean.getMyPayClass();
                    infos.addAll(myPayClassList);
                    adapter.notifyDataSetChanged();
                } else {
//                    toast("没有更多数据");
                    Toast.makeText(CollectActivity.this,"没有更多数据了",Toast.LENGTH_SHORT).show();
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
