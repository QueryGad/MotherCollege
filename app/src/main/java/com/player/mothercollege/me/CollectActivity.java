package com.player.mothercollege.me;

import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.player.mothercollege.R;
import com.player.mothercollege.activity.BaseActivity;
import com.player.mothercollege.adapter.CollectAdapter;
import com.player.mothercollege.bean.CollectBean;
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
 * Created by Administrator on 2016/10/25.
 * 我的收藏
 */
public class CollectActivity extends BaseActivity implements View.OnClickListener {

    private static final int GET_COLLECT_DATA = 001;
    private Button btn_back;
    private TextView tv_details_title;
    private RequestQueue requestQueue;
    private ListView lv_college;

    @Override
    public void setContentView() {
       setContentView(R.layout.act_me_college);
        requestQueue = NoHttp.newRequestQueue();
    }

    @Override
    public void initViews() {
        btn_back = (Button) findViewById(R.id.btn_back);
        tv_details_title = (TextView) findViewById(R.id.tv_details_title);
        lv_college = (ListView) findViewById(R.id.lv_college);

        tv_details_title.setText("我的收藏");
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
        String uid = PrefUtils.getString(this, "uid", "null");
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.ME_URL, RequestMethod.GET);
        request.add("apptoken",apptoken);
        request.add("op","mykeep");
        request.add("uid",uid);
        request.add("lastindex","0");
        requestQueue.add(GET_COLLECT_DATA, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                parseJson(info);
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
        int currentPageSize = collectBean.getCurrentPageSize();
        int lastIndex = collectBean.getLastIndex();
        List<CollectBean.MyPayClassBean> myPayClass = collectBean.getMyPayClass();
        CollectAdapter adapter = new CollectAdapter(CollectActivity.this,myPayClass);
        lv_college.setAdapter(adapter);
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
