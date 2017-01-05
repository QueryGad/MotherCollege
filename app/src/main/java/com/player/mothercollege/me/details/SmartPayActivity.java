package com.player.mothercollege.me.details;

import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.player.mothercollege.R;
import com.player.mothercollege.activity.BaseActivity;
import com.player.mothercollege.adapter.SmartPayAdapter;
import com.player.mothercollege.bean.SmartListBean;
import com.player.mothercollege.utils.ConfigUtils;
import com.player.mothercollege.utils.PrefUtils;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

import java.util.List;


/**
 * Created by Administrator on 2016/10/7.
 */
public class SmartPayActivity extends BaseActivity implements View.OnClickListener{

    private static final int POST_LIST_DATA = 001;
    private Button btn_back;
    private TextView tv_details_title;
    private GridView gv_smartpay;
    private String [] muchs = {"300","600","1500","3500","6000","10000"};
    private String [] money = {"￥30","￥50","￥98","￥198","￥298","￥488"};
    private RequestQueue requestQueue;
    private String apptoken;

    @Override
    public void setContentView() {
        setContentView(R.layout.act_me_smartpay);
        requestQueue = NoHttp.newRequestQueue();
    }

    @Override
    public void initViews() {
        btn_back = (Button) findViewById(R.id.btn_back);
        tv_details_title = (TextView) findViewById(R.id.tv_details_title);
        gv_smartpay = (GridView) findViewById(R.id.gv_smartpay);

        tv_details_title.setText("充值");
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
        apptoken = PrefUtils.getString(SmartPayActivity.this, "apptoken", "");
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.PAY, RequestMethod.POST);
        request.add("apptoken",apptoken);
        request.add("op","goodlist");
        requestQueue.add(POST_LIST_DATA, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
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
        SmartListBean smartListBean = gson.fromJson(info, SmartListBean.class);
        List<SmartListBean.PayGoodListBean> payGoodList = smartListBean.getPayGoodList();

        SmartPayAdapter adapter = new SmartPayAdapter(payGoodList,muchs,money,this);
        gv_smartpay.setAdapter(adapter);
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
