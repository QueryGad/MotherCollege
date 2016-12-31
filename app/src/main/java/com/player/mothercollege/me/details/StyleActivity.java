package com.player.mothercollege.me.details;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
 * 个性签名
 */
public class StyleActivity extends BaseActivity implements View.OnClickListener {

    private static final int POST_STYLE_DATA = 001;
    private Button btn_back;
    private TextView tv_me_style_ok,tv_style_num;
    private EditText et_me_style_content;
    private RequestQueue requestQueue;
    private ProgressDialog pd;
    private TextWatcher EditTextInPutListener = new TextWatcher() {

        private CharSequence temp;
        private int editStart;
        private int editEnd;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            temp = s;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            editStart = et_me_style_content.getSelectionStart();
            editEnd = et_me_style_content.getSelectionEnd();
            tv_style_num.setText(temp.length()+"/30");
            tv_style_num.setTextColor(Color.RED);
            if (temp.length()>30){
                Toast.makeText(StyleActivity.this,"您输入的字数已经超过了限制!",Toast.LENGTH_SHORT).show();
                s.delete(editStart-1,editEnd);
                int tempSelection = editStart;
                et_me_style_content.setText(s);
                et_me_style_content.setSelection(tempSelection);
            }
        }
    };

    @Override
    public void setContentView() {
        setContentView(R.layout.act_me_style);
        requestQueue = NoHttp.newRequestQueue();
    }

    @Override
    public void initViews() {
        btn_back = (Button) findViewById(R.id.btn_back);
        tv_me_style_ok = (TextView) findViewById(R.id.tv_me_style_ok);
        tv_style_num = (TextView) findViewById(R.id.tv_style_num);
        et_me_style_content = (EditText) findViewById(R.id.et_me_style_content);

        //数据回显
        String autograph = PrefUtils.getString(StyleActivity.this, "autograph", "");
        et_me_style_content.setText(autograph);
    }

    @Override
    public void initListeners() {
        btn_back.setOnClickListener(this);
        tv_me_style_ok.setOnClickListener(this);
        et_me_style_content.addTextChangedListener(EditTextInPutListener);
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
            case R.id.tv_me_style_ok:
                String content = et_me_style_content.getText().toString().trim();
                PrefUtils.setString(StyleActivity.this,"style","");
                if (TextUtils.isEmpty(content)){
                    Toast.makeText(StyleActivity.this,"请输入个性签名",Toast.LENGTH_SHORT).show();
                    break;
                }else {
                    Intent intent = new Intent();
                    intent.putExtra("newStyle",content);
                    setResult(005,intent);
                    postData(content);
                };
                break;

        }
    }

    private void postData(String content) {
        String apptoken = PrefUtils.getString(StyleActivity.this, "apptoken", "");
        String uid = PrefUtils.getString(StyleActivity.this, "uid", "");
//        String style = PrefUtils.getString(StyleActivity.this, "style", "");
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.LOGIN_URL, RequestMethod.POST);
        request.add("apptoken",apptoken);
        request.add("op","changeUserInfo");
        request.add("uid",uid);
        request.add("ctype","6");
        request.add("cvalue",content);
        requestQueue.add(POST_STYLE_DATA, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
                pd = new ProgressDialog(StyleActivity.this);
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
                        Toast.makeText(StyleActivity.this,"ctype 不在内定的参数范围.",Toast.LENGTH_SHORT).show();
                    }else if (resultInt==1002){
                        Toast.makeText(StyleActivity.this,"提交的参数值不在规定的格式内或为空",Toast.LENGTH_SHORT).show();
                    }else if (resultInt==5001){
                        Toast.makeText(StyleActivity.this,"服务器内部错误",Toast.LENGTH_SHORT).show();
                    }else if (resultInt==1){
                        Toast.makeText(StyleActivity.this,"修改成功!",Toast.LENGTH_SHORT).show();
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
