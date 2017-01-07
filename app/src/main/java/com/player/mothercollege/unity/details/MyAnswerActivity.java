package com.player.mothercollege.unity.details;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
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
 * Created by Administrator on 2016/11/23.
 */
public class MyAnswerActivity extends BaseActivity implements View.OnClickListener {

    private static final int POST_ANSWER_DATA = 001;
    private Button btn_back;
    private TextView tv_answer_ok;
    private EditText et_myanswer;
    private AlertDialog alertDialog;
    private RequestQueue requestQueue;
    private String qid;

    @Override
    public void setContentView() {
        setContentView(R.layout.act_unity_myanswer);
        requestQueue = NoHttp.newRequestQueue();
    }

    @Override
    public void initViews() {
        qid = getIntent().getStringExtra("qid");
        MyLog.testLog("qid:"+qid);
        btn_back = (Button) findViewById(R.id.btn_back);
        tv_answer_ok = (TextView) findViewById(R.id.tv_answer_ok);
        et_myanswer = (EditText) findViewById(R.id.et_myanswer);

    }

    @Override
    public void initListeners() {
        btn_back.setOnClickListener(this);
        tv_answer_ok.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                finishAlert();
                break;
            case R.id.tv_answer_ok:
                //判空
                String content = et_myanswer.getText().toString().trim();
                if (TextUtils.isEmpty(content)){
                    Toast.makeText(MyAnswerActivity.this,"请输入回答内容!",Toast.LENGTH_SHORT).show();
                    break;
                }
                netWork(content);
                break;
        }
    }

    private void finishAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("温馨提示");
        builder.setMessage("确认放弃此次编辑?");
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        alertDialog = builder.show();
    }

    private void netWork(final String content){
        String apptoken = PrefUtils.getString(MyAnswerActivity.this, "apptoken", "");
        String uid = PrefUtils.getString(MyAnswerActivity.this, "uid", "null");
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.UNITY_URL, RequestMethod.POST);
        request.add("apptoken",apptoken);
        request.add("op","postanswer");
        request.add("qid",qid+"");
        request.add("uid",uid);
        request.add("info",content);
        requestQueue.add(POST_ANSWER_DATA, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                MyLog.testLog("是否回答成功:"+info);
                try {
                    JSONObject json = new JSONObject(info);
                    boolean isSuccess = json.getBoolean("isSuccess");
                    String resultInfo = json.getString("resultInfo");
                    if (isSuccess){
                        //回答成功
                        Toast.makeText(MyAnswerActivity.this,"回答成功!",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        intent.putExtra("content",content);
                        setResult(100,intent);
                        finish();
                    }else {
                        //回答失败
                        Toast.makeText(MyAnswerActivity.this,resultInfo,Toast.LENGTH_SHORT).show();
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

    @Override
    public void onBackPressed() {
        finishAlert();
    }
}
