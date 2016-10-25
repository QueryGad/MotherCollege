package com.player.mothercollege.me;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
 * 关注
 */
public class AttActivity extends BaseActivity implements View.OnClickListener {

    private static final int GET_ATT_DATA = 001;
    private Button btn_back;
    private TextView tv_details_title;
    private RecyclerView rv_att;
    private ImageView iv_refresh;
    private Button btn_refrsh;
    private List<AttBean.UsersBean> usersList = new ArrayList<>();
    private RequestQueue requestQueue;
    private AttAdapter.OnItemClickListener AttItemListener = new AttAdapter.OnItemClickListener() {
        @Override
        public void onClick(View v, int position, AttBean.UsersBean data) {
            Toast.makeText(AttActivity.this,position+"",Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    public void setContentView() {
        setContentView(R.layout.act_me_att);
        requestQueue = NoHttp.newRequestQueue();
    }

    @Override
    public void initViews() {
        btn_back = (Button) findViewById(R.id.btn_back);
        tv_details_title = (TextView) findViewById(R.id.tv_details_title);
        rv_att = (RecyclerView) findViewById(R.id.rv_att);
        iv_refresh = (ImageView) findViewById(R.id.iv_refresh);
        btn_refrsh = (Button) findViewById(R.id.btn_refrsh);

        tv_details_title.setText("我的关注");
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
        request.add("op","myfollow");
        request.add("uid","null");
        request.add("lastIndex","0");
        request.add("apptoken",apptoken);
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
        int currentPageSize = attBean.getCurrentPageSize();//总页数
        int lastIndex = attBean.getLastIndex();//最后一页
        List<AttBean.UsersBean> usersList = attBean.getUsers();
        AttAdapter adapter = new AttAdapter(this,usersList);
        rv_att.setAdapter(adapter);
        rv_att.setLayoutManager(new LinearLayoutManager(this));
        rv_att.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
        adapter.setOnItemClickListener(AttItemListener);
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
