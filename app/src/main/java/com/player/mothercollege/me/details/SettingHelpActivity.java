package com.player.mothercollege.me.details;

import android.app.AlertDialog;
import android.content.DialogInterface;
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


/**
 * Created by Administrator on 2016/10/5.
 * 帮助与反馈
 */
public class SettingHelpActivity extends BaseActivity implements View.OnClickListener {

    private static final int POST_HELP_DATA = 001;
    private Button btn_back,btn_help_send;
    private TextView tv_details_title;
    private AlertDialog dialog;
    private EditText et_help_content,et_help_phone;
    private RequestQueue requestQueue;

    @Override
    public void setContentView() {
        setContentView(R.layout.act_me_setting_help);
        requestQueue = NoHttp.newRequestQueue();
    }

    @Override
    public void initViews() {
        btn_back = (Button) findViewById(R.id.btn_back);
        tv_details_title = (TextView) findViewById(R.id.tv_details_title);
        btn_help_send = (Button) findViewById(R.id.btn_help_send);
        et_help_content = (EditText) findViewById(R.id.et_help_content);
        et_help_phone = (EditText) findViewById(R.id.et_help_phone);

        tv_details_title.setText("帮助与反馈");
    }

    @Override
    public void initListeners() {
        btn_back.setOnClickListener(this);
        btn_help_send.setOnClickListener(this);
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
            case R.id.btn_help_send:
                String content = et_help_content.getText().toString().trim();
                String phone = et_help_phone.getText().toString().trim();
                if (TextUtils.isEmpty(phone)){
                    phone = "";
                }else {
                    phone = et_help_phone.getText().toString().trim();
                }
                if (TextUtils.isEmpty(content)){
                    Toast.makeText(SettingHelpActivity.this,"请输入反馈内容",Toast.LENGTH_SHORT).show();
                }else {
                    postData(content,phone);
                }
                break;
        }
    }

    private void postData(String content,String phone) {
        String apptoken = PrefUtils.getString(this, "apptoken", "");
        String uid = PrefUtils.getString(this, "uid", "null");
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.ME_URL, RequestMethod.POST);
        request.add("apptoken",apptoken);
        request.add("op","bugReport");
        request.add("uid",uid);
        request.add("bug",content);
        request.add("link",phone);
        requestQueue.add(POST_HELP_DATA, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                MyLog.testLog("问题反馈:"+info);
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingHelpActivity.this);
                builder.setTitle("温馨提示");
                builder.setMessage("谢谢您的反馈");
                builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        et_help_phone.setText("");
                        et_help_content.setText("");
                    }
                });
                dialog = builder.show();
                dialog.setCanceledOnTouchOutside(false);
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
