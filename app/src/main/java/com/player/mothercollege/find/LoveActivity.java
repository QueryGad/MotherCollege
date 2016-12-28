package com.player.mothercollege.find;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.google.gson.Gson;
import com.player.mothercollege.R;
import com.player.mothercollege.activity.BaseActivity;
import com.player.mothercollege.adapter.LoveAdapter;
import com.player.mothercollege.bean.LoveBean;
import com.player.mothercollege.me.HeadIconActivity;
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

import java.util.List;

/**
 * Created by Administrator on 2016/10/25.
 * 爱心大使
 */
public class LoveActivity extends BaseActivity implements View.OnClickListener {

    private static final int GET_LOVE_DATA = 001;
    private Button btn_back;
    private TextView tv_details_title;
    private RecyclerView rv_find_love;
    private ImageView iv_refresh;
    private Button btn_refrsh;
    private RequestQueue requestQueue;
    private String lastindex =0+"";

    private MaterialRefreshLayout mRefreshLayout;
    private static final int STATE_NORMAL = 0;
    private static final int STATE_REFREN = 1;
    private static final int STATE_MORE = 2;

    private int state = STATE_NORMAL;


    private LoveAdapter.OnItemClickListener LoveItemListener = new LoveAdapter.OnItemClickListener() {
        @Override
        public void onClick(View v, int position, LoveBean.UsersBean data) {
            //点击头像进入他人主页
            String uid = usersList.get(position).getUid();
            Intent intent = new Intent(LoveActivity.this, HeadIconActivity.class);
            intent.putExtra("toUid",uid);
            startActivity(intent);
        }
    };
    private List<LoveBean.UsersBean> usersList;
    private String lastSize;
    private LoveAdapter adapter;

    @Override
    public void setContentView() {
        setContentView(R.layout.act_find_love);
        requestQueue = NoHttp.newRequestQueue();
    }

    @Override
    public void initViews() {
        btn_back = (Button) findViewById(R.id.btn_back);
        tv_details_title = (TextView) findViewById(R.id.tv_details_title);
        rv_find_love = (RecyclerView) findViewById(R.id.rv_find_love);
        iv_refresh = (ImageView) findViewById(R.id.iv_refresh);
        btn_refrsh = (Button)findViewById(R.id.btn_refrsh);
        mRefreshLayout = (MaterialRefreshLayout) findViewById(R.id.refresh_love);

        tv_details_title.setText("爱心大使");
    }

    @Override
    public void initListeners() {
        btn_back.setOnClickListener(this);
    }

    @Override
    public void initData() {
        initRefreshLayout();
        netWork();
    }

    private void netWork() {
        String apptoken = PrefUtils.getString(this, "apptoken", "");
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.FIND_URL, RequestMethod.GET);
        request.add("op","axds");
        request.add("lastindex",lastindex);
        MyLog.testLog("两次lastindex:"+lastindex);
        request.add("apptoken",apptoken);
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
        int currentPageSize = loveBean.getCurrentPageSize(); //总共页面数
        //最后传入下标
        int lastIndex = loveBean.getLastIndex();
        lastSize = lastIndex+"";
        usersList = loveBean.getUsers();

        showData();

    }

    private void showData() {

        switch (state){
            case STATE_NORMAL://正常状态
                adapter = new LoveAdapter(usersList,LoveActivity.this);
                rv_find_love.setAdapter(adapter);
                rv_find_love.setLayoutManager(new LinearLayoutManager(LoveActivity.this));
                rv_find_love.addItemDecoration(new DividerItemDecoration(LoveActivity.this,DividerItemDecoration.VERTICAL_LIST));
                adapter.setOnItemClickListener(LoveItemListener);
                break;
            case STATE_REFREN: //刷新

                if (lastindex==lastSize){
                    Toast.makeText(LoveActivity.this,"没有更多数据了",Toast.LENGTH_SHORT).show();
                }else {
//                    adapter.clearData();
                    MyLog.testLog("刷新了:"+lastindex);
                    MyLog.testLog("lastSize刷新了:"+lastSize);
                    adapter.addData(usersList);
                    rv_find_love.scrollToPosition(0);
                    mRefreshLayout.finishRefresh();
                }

                break;
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

    private void initRefreshLayout(){
        mRefreshLayout.setLoadMore(true);//让它支持加载更多
        mRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                refreshData();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {


            }
        });
    }

    private void refreshData(){
        lastindex = lastSize;
        MyLog.testLog("lastindex:"+lastindex);
        state = STATE_REFREN;
        netWork();
    }
}
