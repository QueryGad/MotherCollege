package com.player.mothercollege.unity.details;

import android.graphics.Color;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.player.mothercollege.R;
import com.player.mothercollege.activity.BaseActivity;
import com.player.mothercollege.bean.RequestBean;
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
 * Created by Administrator on 2016/11/1.
 */
public class RequestActivity extends BaseActivity implements View.OnClickListener {

    private static final int POST_REQUEST_DATA = 001;
    private Button btn_back;
    private TextView tv_details_title,tv_send,tv_request_xianzhi;
    private EditText et_request_title,et_request_content;
    private RequestQueue requestQueue;
    private TextWatcher EditTextInPutListener = new TextWatcher() {

        private CharSequence temp;
        private int editStart;
        private int editEnd;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            temp = s;
        }

        @Override
        public void afterTextChanged(Editable s) {
            editStart = et_request_title.getSelectionStart();
            editEnd = et_request_title.getSelectionEnd();
            tv_request_xianzhi.setText(temp.length()+"/15");
            tv_request_xianzhi.setTextColor(Color.RED);
            if (temp.length()>15){
                Toast.makeText(RequestActivity.this,"您输入的字数已经超过了限制!",Toast.LENGTH_SHORT).show();
                s.delete(editStart-1,editEnd);
                int tempSelection = editStart;
                et_request_title.setText(s);
                et_request_title.setSelection(tempSelection);
            }
        }
    };


    @Override
    public void setContentView() {
        setContentView(R.layout.act_request_page);
        requestQueue = NoHttp.newRequestQueue();
    }

    @Override
    public void initViews() {
        btn_back = (Button) findViewById(R.id.btn_back);
        tv_details_title = (TextView) findViewById(R.id.tv_details_title);
        tv_send = (TextView) findViewById(R.id.tv_send);
        et_request_title = (EditText) findViewById(R.id.et_request_title);
        et_request_content = (EditText) findViewById(R.id.et_request_content);
        tv_request_xianzhi = (TextView) findViewById(R.id.tv_request_xianzhi);

        tv_details_title.setText("提问");
    }

    @Override
    public void initListeners() {
        btn_back.setOnClickListener(this);
        tv_send.setOnClickListener(this);
        et_request_title.addTextChangedListener(EditTextInPutListener);
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
            case R.id.tv_send:
                String qtitle = et_request_title.getText().toString().trim();
                String qustion = et_request_content.getText().toString().trim();
                if (TextUtils.isEmpty(qtitle)||TextUtils.isEmpty(qustion)){
                    Toast.makeText(RequestActivity.this,"标题或内容为空，请重新填写!",Toast.LENGTH_SHORT).show();
                    et_request_title.setText("");
                    et_request_content.setText("");
                    break;
                }
                postData(qtitle,qustion);
                break;
        }
    }

    private void postData(String qtitle,String qustion) {
        String apptoken = PrefUtils.getString(RequestActivity.this, "apptoken", "");
        String uid = PrefUtils.getString(RequestActivity.this, "uid", "null");
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.UNITY_URL, RequestMethod.POST);
        request.add("apptoken",apptoken);
        request.add("op","postQustion");
        request.add("uid",uid);
        request.add("qtitle",qtitle);
        request.add("qustion",qustion);
        requestQueue.add(POST_REQUEST_DATA, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                MyLog.testLog("提交问题"+info);
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
        RequestBean requestBean = gson.fromJson(info, RequestBean.class);
        boolean isSuccess = requestBean.isIsSuccess();
        if (isSuccess){//提交成功
            Toast.makeText(RequestActivity.this,"问题提交成功!",Toast.LENGTH_SHORT).show();
            et_request_content.setText("");
            et_request_title.setText("");
            finish();
        }else {
            Toast.makeText(RequestActivity.this,"问题提交失败!",Toast.LENGTH_SHORT).show();
        }
    }
}
