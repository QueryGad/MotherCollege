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
import com.player.mothercollege.bean.SmartMoneyBean;
import com.player.mothercollege.find.FrendActivity;
import com.player.mothercollege.me.details.SmartDetailsActivity;
import com.player.mothercollege.me.details.SmartMakeOverActivity;
import com.player.mothercollege.me.details.SmartPayActivity;
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
 * 智慧币
 */
public class SmartMoneyActivity extends BaseActivity implements View.OnClickListener {

    private static final int GET_SMARTMONEY_DATA = 001;
    private Button btn_back;
    private LinearLayout ll_me_smart_pay,ll_me_smart_makeOver,ll_me_smart_erweima,ll_me_smart_minute;
    private TextView tv_details_title,tv_smart_num,tv_smart_add,tv_smart_lessen;
    private SmartMoneyBean smartMoneyBean;
    private int currentNum;
    private RequestQueue requestQueue;

    @Override
    public void setContentView() {
         setContentView(R.layout.act_me_smartmoeny);
        requestQueue = NoHttp.newRequestQueue();
    }

    @Override
    public void initViews() {
        btn_back = (Button) findViewById(R.id.btn_back);
        tv_details_title = (TextView) findViewById(R.id.tv_details_title);
        ll_me_smart_minute = (LinearLayout) findViewById(R.id.ll_me_smart_minute);
        ll_me_smart_pay = (LinearLayout) findViewById(R.id.ll_me_smart_pay);
        ll_me_smart_makeOver = (LinearLayout) findViewById(R.id.ll_me_smart_makeOver);
        ll_me_smart_erweima = (LinearLayout) findViewById(R.id.ll_me_smart_erweima);
        tv_smart_num = (TextView) findViewById(R.id.tv_smart_num);
        tv_smart_add = (TextView) findViewById(R.id.tv_smart_add);
        tv_smart_lessen = (TextView) findViewById(R.id.tv_smart_lessen);

        tv_details_title.setText("智慧币");


    }

    @Override
    public void initListeners() {
        btn_back.setOnClickListener(this);
        ll_me_smart_minute.setOnClickListener(this);
        ll_me_smart_pay.setOnClickListener(this);
        ll_me_smart_makeOver.setOnClickListener(this);
        ll_me_smart_erweima.setOnClickListener(this);
    }

    @Override
    public void initData() {
        String cacheJson = CacheUtils.getCache(this, ConfigUtils.COLLEGE_URL + "smart");
        if (!TextUtils.isEmpty(cacheJson)){
            parseJson(cacheJson);
        }
        netWork();
    }

    private void netWork() {
        String apptoken = PrefUtils.getString(this, "apptoken", "");
        String uid = PrefUtils.getString(SmartMoneyActivity.this, "uid", "null");
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.ME_URL, RequestMethod.GET);
        request.add("op","zhbinfo");
        request.add("uid",uid);
        request.add("apptoken",apptoken);
        requestQueue.add(GET_SMARTMONEY_DATA, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                MyLog.testLog("智慧币页面"+info);
                CacheUtils.saveCache(SmartMoneyActivity.this,ConfigUtils.COLLEGE_URL + "smart",info);
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
        smartMoneyBean = gson.fromJson(info,SmartMoneyBean.class);
        //余额
        currentNum = smartMoneyBean.getCurrentNum();
        int totalAdd = smartMoneyBean.getTotalAdd();//累计增加
        int totalPayOut = smartMoneyBean.getTotalPayOut();//累计支出
        //智慧币明细
        tv_smart_num.setText(currentNum +"");
        tv_smart_add.setText(totalAdd+"");
        tv_smart_lessen.setText(totalPayOut+"");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                finish();
                break;
            //明细
            case R.id.ll_me_smart_minute:
                Intent intent1 = new Intent(SmartMoneyActivity.this, SmartDetailsActivity.class);
                intent1.putExtra("smartDetails", smartMoneyBean);
                startActivity(intent1);
                break;
            //充值
            case R.id.ll_me_smart_pay:
                Intent intent2 = new Intent(SmartMoneyActivity.this, SmartPayActivity.class);
                startActivity(intent2);
                break;
            //转让
            case R.id.ll_me_smart_makeOver:
                Intent intent3 = new Intent(SmartMoneyActivity.this, SmartMakeOverActivity.class);
                intent3.putExtra("currentNum",currentNum);
                PrefUtils.setInt(SmartMoneyActivity.this,"SmartCurrentNum",0);
                startActivity(intent3);
                break;
            //邀请好友
            case R.id.ll_me_smart_erweima:
                Intent intent4 = new Intent(SmartMoneyActivity.this, FrendActivity.class);
                startActivity(intent4);
                break;
        }
    }
}
