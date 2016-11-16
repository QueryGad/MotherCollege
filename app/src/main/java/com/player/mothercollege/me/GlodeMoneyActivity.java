package com.player.mothercollege.me;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.player.mothercollege.R;
import com.player.mothercollege.activity.BaseActivity;
import com.player.mothercollege.bean.GlodeMoneyBean;
import com.player.mothercollege.me.details.GetGlodeActivity;
import com.player.mothercollege.me.details.GlodeDetailsActivity;
import com.player.mothercollege.utils.CacheUtils;
import com.player.mothercollege.utils.ConfigUtils;
import com.player.mothercollege.utils.MyLog;
import com.player.mothercollege.utils.PrefUtils;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

/**
 * Created by Administrator on 2016/10/25.
 * 金币
 */
public class GlodeMoneyActivity extends BaseActivity implements View.OnClickListener {

    private static final int GET_GLODE_DATA = 001;
    private Button btn_back;
    private TextView tv_details_title;
    private LinearLayout ll_me_glode_getmoney,ll_glode_minute;
    private TextView tv_glode_now,tv_glode_add,tv_glode_lessen;
    private GlodeMoneyBean glodeMoneyBean = new GlodeMoneyBean();
    private RequestQueue requestQueue;

    @Override
    public void setContentView() {
       setContentView(R.layout.act_me_glode);
        requestQueue = NoHttp.newRequestQueue();
    }

    @Override
    public void initViews() {
        btn_back = (Button) findViewById(R.id.btn_back);
        tv_details_title = (TextView) findViewById(R.id.tv_details_title);
        ll_me_glode_getmoney = (LinearLayout) findViewById(R.id.ll_me_glode_getmoney);
        ll_glode_minute = (LinearLayout) findViewById(R.id.ll_glode_minute);
        tv_glode_now = (TextView) findViewById(R.id.tv_glode_now);
        tv_glode_add = (TextView) findViewById(R.id.tv_glode_add);
        tv_glode_lessen = (TextView) findViewById(R.id.tv_glode_lessen);
        tv_details_title.setText("金币");
    }

    @Override
    public void initListeners() {
        btn_back.setOnClickListener(this);
        ll_me_glode_getmoney.setOnClickListener(this);
        ll_glode_minute.setOnClickListener(this);
    }

    @Override
    public void initData() {
        String cacheJson = CacheUtils.getCache(this, ConfigUtils.COLLEGE_URL + "glode");
        if (!TextUtils.isEmpty(cacheJson)){
            parseJson(cacheJson);
        }
        netWork();
    }

    private void netWork() {
        String apptoken = PrefUtils.getString(this, "apptoken", "");
        String uid = PrefUtils.getString(GlodeMoneyActivity.this, "uid", "null");
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.ME_URL, RequestMethod.GET);
        request.add("op","goldinfo");
        request.add("uid",uid);
        request.add("apptoken",apptoken);
        requestQueue.add(GET_GLODE_DATA, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                MyLog.testLog("金币页面"+info);
                CacheUtils.saveCache(GlodeMoneyActivity.this,ConfigUtils.COLLEGE_URL + "glode",info);
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

    private void parseJson(String info){
        Gson gson = new Gson();
        glodeMoneyBean = gson.fromJson(info, GlodeMoneyBean.class);
        int currentNum = glodeMoneyBean.getCurrentNum(); //余额
        int totalAdd = glodeMoneyBean.getTotalAdd(); //累计增加
        int totalPayOut = glodeMoneyBean.getTotalPayOut(); //累计支出
        //金币明细
        tv_glode_now.setText(currentNum+"");
        tv_glode_add.setText(totalAdd+"");
        tv_glode_lessen.setText(totalPayOut+"");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                finish();
                break;
            //明细
            case R.id.ll_glode_minute:
                Intent intent1 = new Intent(GlodeMoneyActivity.this, GlodeDetailsActivity.class);
                intent1.putExtra("glodeDetails", glodeMoneyBean);
                startActivity(intent1);
                break;
            //提现
            case R.id.ll_me_glode_getmoney:
                Intent intent2 = new Intent(GlodeMoneyActivity.this, GetGlodeActivity.class);
                startActivity(intent2);
                break;
        }
    }
}
