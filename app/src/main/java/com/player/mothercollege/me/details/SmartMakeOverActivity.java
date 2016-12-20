package com.player.mothercollege.me.details;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.player.mothercollege.R;
import com.player.mothercollege.activity.BaseActivity;
import com.player.mothercollege.utils.ConfigUtils;
import com.player.mothercollege.utils.MyLog;
import com.player.mothercollege.utils.PrefUtils;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/10/7.
 * 转让智慧币
 */
public class SmartMakeOverActivity extends BaseActivity implements View.OnClickListener {

    private static final int GET_MAKEOVER_DATA = 001;
    private Button btn_back,btn_smart_markover;
    private TextView tv_details_title,tv_getSmart_currentNum;
    private EditText et_getSmart_phone,et_getSmart_number,et_getSmart_liuyan;
    private int currentNum;
    private RequestQueue requestQueue;

    @Override
    public void setContentView() {
         setContentView(R.layout.act_me_smartmarkover);
    }

    @Override
    public void initViews() {
        requestQueue = NoHttp.newRequestQueue();

        int smartCurrentNum = PrefUtils.getInt(SmartMakeOverActivity.this, "SmartCurrentNum", 0);
        //现有智慧币数用于判断转让智慧币必须小于现有智慧币数量
        currentNum = getIntent().getIntExtra("currentNum", smartCurrentNum);
        btn_back = (Button) findViewById(R.id.btn_back);
        btn_smart_markover = (Button) findViewById(R.id.btn_smart_markover);//转让
        tv_details_title = (TextView) findViewById(R.id.tv_details_title);
        tv_getSmart_currentNum = (TextView) findViewById(R.id.tv_getSmart_currentNum);
        et_getSmart_phone = (EditText) findViewById(R.id.et_getSmart_phone); //获赠人手机号
        et_getSmart_number = (EditText) findViewById(R.id.et_getSmart_number);//要转让的智慧币数
        et_getSmart_liuyan = (EditText) findViewById(R.id.et_getSmart_liuyan);//留言

        tv_details_title.setText("转让");

        tv_getSmart_currentNum.setText("当前智慧币余额："+ currentNum);
    }

    @Override
    public void initListeners() {
        btn_back.setOnClickListener(this);
        btn_smart_markover.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_smart_markover:
                String number = et_getSmart_number.getText().toString().trim();
                String phone = et_getSmart_phone.getText().toString().trim();
                String liuyan = et_getSmart_liuyan.getText().toString().trim();
                if (TextUtils.isEmpty(number)||TextUtils.isEmpty(phone)){
                    Toast.makeText(SmartMakeOverActivity.this,"请输入获赠人手机号和转让智慧币个数",Toast.LENGTH_SHORT).show();
                    break;
                }
                if (Integer.parseInt(number)>currentNum){
                    Toast.makeText(SmartMakeOverActivity.this,"转让智慧币数大于现有智慧币数，请重新输入",Toast.LENGTH_SHORT).show();
                    et_getSmart_number.setText("");
                    break;
                }
                netWork(number,phone,liuyan);
                break;
        }
    }

    private void netWork(String number, String phone, String liuyan) {
        String apptoken = PrefUtils.getString(this, "apptoken", "");
        String uid = PrefUtils.getString(SmartMakeOverActivity.this, "uid", "");
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.ME_URL, RequestMethod.GET);
        request.add("op","givezhb");
        request.add("uid",uid);
        request.add("apptoken",apptoken);
        request.add("receiveid",phone);
        request.add("desc",liuyan);
        request.add("gnum",number);
        requestQueue.add(GET_MAKEOVER_DATA, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                MyLog.testLog("转让页面数据:"+info);
                try {
                    JSONObject json = new JSONObject(info);
                    String result = json.getString("result");
                    String newCount = json.getString("newCount");
                    if (Integer.parseInt(result)==1){
                        tv_getSmart_currentNum.setText("当前智慧币余额："+ newCount);
                        Toast.makeText(SmartMakeOverActivity.this,"转让成功!",Toast.LENGTH_SHORT).show();
                        et_getSmart_number.setText("");
                        et_getSmart_phone.setText("");
                        et_getSmart_liuyan.setText("");
                    }else{
                        Toast.makeText(SmartMakeOverActivity.this,"转让失败，请稍候重试!",Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
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
