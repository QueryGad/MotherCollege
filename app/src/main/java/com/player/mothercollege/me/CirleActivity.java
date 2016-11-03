package com.player.mothercollege.me;

import android.app.ProgressDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.player.mothercollege.R;
import com.player.mothercollege.activity.BaseActivity;
import com.player.mothercollege.adapter.MaybeAdapter;
import com.player.mothercollege.adapter.MyCirleAdapter;
import com.player.mothercollege.bean.CirleBean;
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
 * 我的圈子
 */
public class CirleActivity extends BaseActivity implements View.OnClickListener {

    private static final int GET_CIRLE_DATA = 001;
    private Button btn_back;
    private TextView tv_details_title;
    private ListView lv_mygroup,lv_maybegruop;
    private RequestQueue requestQueue;
    private ProgressDialog pd;

    @Override
    public void setContentView() {
        setContentView(R.layout.act_me_cirle);
        requestQueue = NoHttp.newRequestQueue();
    }

    @Override
    public void initViews() {
        btn_back = (Button) findViewById(R.id.btn_back);
        tv_details_title = (TextView) findViewById(R.id.tv_details_title);
        lv_mygroup = (ListView) findViewById(R.id.lv_mygroup);
        lv_maybegruop = (ListView) findViewById(R.id.lv_maybegruop);

        tv_details_title.setText("我的圈子");
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
        request.add("apptoken",apptoken);
        request.add("op","myGroup");
        request.add("uid","null");
        requestQueue.add(GET_CIRLE_DATA, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
                pd = new ProgressDialog(CirleActivity.this);
                pd.show();
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                MyLog.testLog("我的圈子:"+info);
                parseJson(info);
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }

            @Override
            public void onFinish(int what) {
                pd.dismiss();
            }
        });
    }

    private void parseJson(String info){
        Gson gson = new Gson();
        CirleBean cirleBean = gson.fromJson(info, CirleBean.class);
        List<CirleBean.MaybeLikeGroupsBean> maybeList = cirleBean.getMaybeLikeGroups();//可能感兴趣的圈子
        List<CirleBean.MyGroupsBean> myGroupsList =  cirleBean.getMyGroups(); //我的圈子
        MyCirleAdapter myAdapter = new MyCirleAdapter(CirleActivity.this,myGroupsList);
        lv_mygroup.setAdapter(myAdapter);
        MaybeAdapter maybeAdapter = new MaybeAdapter(CirleActivity.this,maybeList);
        lv_maybegruop.setAdapter(maybeAdapter);
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
