package com.player.mothercollege.me.details;

import android.app.ProgressDialog;
import android.content.Intent;
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
 * Created by Administrator on 2016/10/25.
 * 修改昵称
 */
public class AlterNameActivity extends BaseActivity implements View.OnClickListener {

    private static final int POST_ALTERNAME_DATA = 001;
    private Button btn_back;
    private TextView tv_me_alter_ok;
    private EditText et_me_alter_content;
    private String uniceName;
    private RequestQueue requestQueue;
    private ProgressDialog pd;

    @Override
    public void setContentView() {
       setContentView(R.layout.act_me_altername);
        requestQueue = NoHttp.newRequestQueue();
    }

    @Override
    public void initViews() {

        uniceName = getIntent().getStringExtra("nuiceName");

        btn_back = (Button) findViewById(R.id.btn_back);
        tv_me_alter_ok = (TextView) findViewById(R.id.tv_me_alter_ok);
        et_me_alter_content = (EditText) findViewById(R.id.et_me_alter_content);

        //数据回显
        MyLog.testLog("拿到昵称"+ uniceName);
        et_me_alter_content.setText(uniceName);
        et_me_alter_content.setSelection(uniceName.length());
    }

    @Override
    public void initListeners() {
        btn_back.setOnClickListener(this);
        tv_me_alter_ok.setOnClickListener(this);
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
            case R.id.tv_me_alter_ok:
                //获取新的昵称
                String newName = et_me_alter_content.getText().toString().trim();
                PrefUtils.setString(AlterNameActivity.this,"newName",newName);
                if (TextUtils.isEmpty(newName)){
                    Toast.makeText(AlterNameActivity.this,"请输入新的昵称",Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent();
                    intent.putExtra("newName",newName);
                    setResult(004,intent);
                    postData(newName);
                }
                break;
        }
    }

    private void postData(String newName) {
        String apptoken = PrefUtils.getString(AlterNameActivity.this, "apptoken", "");
        String uid = PrefUtils.getString(AlterNameActivity.this, "uid", "null");
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.LOGIN_URL, RequestMethod.POST);
        request.add("apptoken",apptoken);
        request.add("op","changeUserInfo");
        request.add("uid",uid);
        request.add("ctype",uniceName);
        request.add("cvalue",newName);
        requestQueue.add(POST_ALTERNAME_DATA, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
                pd = new ProgressDialog(AlterNameActivity.this);
                pd.show();
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                MyLog.testLog("提交结果"+info);
                try {
                    JSONObject json = new JSONObject(info);
                    String resultCode = json.getString("resultCode");
                    int resultInt = Integer.parseInt(resultCode);
                    if (resultInt==1001){
                        Toast.makeText(AlterNameActivity.this,"ctype 不在内定的参数范围.",Toast.LENGTH_SHORT).show();
                    }else if (resultInt==1002){
                        Toast.makeText(AlterNameActivity.this,"提交的参数值不在规定的格式内或为空",Toast.LENGTH_SHORT).show();
                    }else if (resultInt==5001){
                        Toast.makeText(AlterNameActivity.this,"服务器内部错误",Toast.LENGTH_SHORT).show();
                    }else if (resultInt==1){
                        Toast.makeText(AlterNameActivity.this,"修改成功!",Toast.LENGTH_SHORT).show();
                    }
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

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
}
