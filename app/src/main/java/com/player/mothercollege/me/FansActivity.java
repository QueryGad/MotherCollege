package com.player.mothercollege.me;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.player.mothercollege.R;
import com.player.mothercollege.activity.BaseActivity;
import com.player.mothercollege.adapter.FansAdapter;
import com.player.mothercollege.bean.FansBean;
import com.player.mothercollege.utils.ConfigUtils;
import com.player.mothercollege.utils.MyLog;
import com.player.mothercollege.utils.PrefUtils;
import com.player.mothercollege.view.DividerItemDecoration;
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
public class FansActivity extends BaseActivity implements View.OnClickListener {

    private static final int GET_FANS_DATA = 001;
    private Button btn_back;
    private TextView tv_details_title;
    private RecyclerView rv_fans;
    private ImageView iv_refresh;
    private Button btn_refrsh;
    private List<FansBean.UsersBean> usersList = new ArrayList<>();
    private RequestQueue requestQueue;
    private FansAdapter.OnItemClickListener FansItemListener = new FansAdapter.OnItemClickListener() {
        @Override
        public void onClick(View v, int position, FansBean.UsersBean data) {
            //点击头像进入他人主页
            Intent intent = new Intent(FansActivity.this, HeadIconActivity.class);
            startActivity(intent);
        }
    };

    @Override
    public void setContentView() {
        setContentView(R.layout.act_me_fans);
        requestQueue = NoHttp.newRequestQueue();
    }

    @Override
    public void initViews() {
        btn_back = (Button) findViewById(R.id.btn_back);
        tv_details_title = (TextView) findViewById(R.id.tv_details_title);
        rv_fans = (RecyclerView) findViewById(R.id.rv_fans);
        iv_refresh = (ImageView) findViewById(R.id.iv_refresh);
        btn_refrsh = (Button) findViewById(R.id.btn_refrsh);

        tv_details_title.setText("我的粉丝");
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
        String apptoken = PrefUtils.getString(this, "apptoken", "");
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.ME_URL, RequestMethod.GET);
        String uid = PrefUtils.getString(FansActivity.this, "uid", "null");
        request.add("op","myfans");
        request.add("uid",uid);
        request.add("lastIndex","0");
        request.add("apptoken",apptoken);
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
        FansBean fansBean = gson.fromJson(info, FansBean.class);
        int currentPageSize = fansBean.getCurrentPageSize();//总页数
        int lastIndex = fansBean.getLastIndex();//最后一页
        List<FansBean.UsersBean> usersList = fansBean.getUsers();
        FansAdapter adapter = new FansAdapter(this,usersList);
        rv_fans.setAdapter(adapter);
        rv_fans.setLayoutManager(new LinearLayoutManager(this));
        rv_fans.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
        adapter.setOnItemClickListener(FansItemListener);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                finish();
                break;
        }
    }
}
