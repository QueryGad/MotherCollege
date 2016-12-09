package com.player.mothercollege.me.details;

import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.player.mothercollege.R;
import com.player.mothercollege.activity.BaseActivity;
import com.player.mothercollege.adapter.MyZanAdapter;
import com.player.mothercollege.bean.MyZanBean;
import com.player.mothercollege.utils.ConfigUtils;
import com.player.mothercollege.utils.MyLog;
import com.player.mothercollege.utils.PrefUtils;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

import java.util.List;

/**
 * Created by Administrator on 2016/12/6.
 * 我的赞
 */
public class MyZanActivity extends BaseActivity implements View.OnClickListener {

    private static final int GET_MYZAN_DATA = 001;
    private Button btn_mycomment_null;
    private TextView tv_mycomment_title,tv_mycomment_null;
    private ListView lv_mycomment;
    private RequestQueue requestQueue;

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
        MyZanAdapter adapter = new MyZanAdapter(MyZanActivity.this,noticesList);
        lv_mycomment.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_mycomment_null:
                finish();
                break;
            case R.id.tv_mycomment_null:
                Toast.makeText(MyZanActivity.this,"我的赞要清空",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
