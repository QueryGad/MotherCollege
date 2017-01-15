package com.player.mothercollege.find;

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
import com.player.mothercollege.adapter.LoveAdapter;
import com.player.mothercollege.bean.LoveBean;
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
 * 爱心大使
 */
public class LoveActivity extends BaseActivity implements View.OnClickListener,MyUpDownListView.OnRefreshListener {

    private static final int GET_LOVE_DATA = 001;
    private static final int GET_MORE_DATA = 002;
    private Button btn_back;
    private TextView tv_details_title;
    private MyUpDownListView lv_find_love;
    private ImageView iv_refresh;
    private Button btn_refrsh;
    private RequestQueue requestQueue;

    int lastIndex=0;
    boolean isRefresh = true;
    private int endNo;

    private LoveAdapter adapter;
    private String apptoken;
    private List<LoveBean.UsersBean> usersList;

    @Override
    public void setContentView() {
        setContentView(R.layout.act_find_love);
        requestQueue = NoHttp.newRequestQueue();
    }

    @Override
    public void initViews() {
        btn_back = (Button) findViewById(R.id.btn_back);
        tv_details_title = (TextView) findViewById(R.id.tv_details_title);
        lv_find_love = (MyUpDownListView) findViewById(R.id.lv_find_love);
        iv_refresh = (ImageView) findViewById(R.id.iv_refresh);
        btn_refrsh = (Button)findViewById(R.id.btn_refrsh);
        lv_find_love.setOnRefreshListener(this);

        tv_details_title.setText("爱心大使");

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
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.FIND_URL, RequestMethod.GET);
        request.add("op","axds");
        request.add("lastindex",lastIndex+"");
        request.add("apptoken", apptoken);
        requestQueue.add(GET_LOVE_DATA, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                iv_refresh.setVisibility(View.GONE);
                btn_refrsh.setVisibility(View.GONE);
                String info = response.get();
                MyLog.testLog("爱心大使:"+info);
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
        LoveBean loveBean = gson.fromJson(info, LoveBean.class);
        //最后传入下标
        lastIndex = loveBean.getLastIndex();


        if (loveBean!=null){
            endNo = loveBean.getLastIndex();//目标索引
            infos = loveBean.getUsers();
            adapter = new LoveAdapter(infos,LoveActivity.this);
            lv_find_love.setAdapter(adapter);
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
                lv_find_love.hideHeaderView();
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
                lv_find_love.hideFooterView();
            }
        }.execute();
    }

    private void setAddMoreData() {
        lastIndex = endNo;
        sendAddHomeLvRequest();
    }

    private List<LoveBean.UsersBean> infos = new ArrayList<>();
    private void sendAddHomeLvRequest() {
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.FIND_URL, RequestMethod.GET);
        request.add("op","axds");
        request.add("lastindex",lastIndex+"");
        request.add("apptoken", apptoken);
        requestQueue.add(GET_MORE_DATA, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                Gson gson = new Gson();
                LoveBean loveBean = gson.fromJson(info, LoveBean.class);
                if (lastIndex!=0) {
                    endNo = loveBean.getLastIndex();
                    lastIndex = endNo;
                    usersList = loveBean.getUsers();
                    infos.addAll(usersList);
                    adapter.notifyDataSetChanged();
                } else {
//                    toast("没有更多数据");
                    Toast.makeText(LoveActivity.this,"没有更多数据了",Toast.LENGTH_SHORT).show();
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
