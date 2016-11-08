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
 * 个性签名
 */
public class StyleActivity extends BaseActivity implements View.OnClickListener {

    private static final int POST_STYLE_DATA = 001;
    private Button btn_back;
    private TextView tv_me_style_ok;
    private EditText et_me_style_content;
    private RequestQueue requestQueue;
    private ProgressDialog pd;

    @Override
    public void setContentView() {
        setContentView(R.layout.act_me_style);
        requestQueue = NoHttp.newRequestQueue();
    }

    @Override
    public void initViews() {
        btn_back = (Button) findViewById(R.id.btn_back);
        tv_me_style_ok = (TextView) findViewById(R.id.tv_me_style_ok);
        et_me_style_content = (EditText) findViewById(R.id.et_me_style_content);

        //数据回显
        String style = PrefUtils.getString(StyleActivity.this, "style", "");
        et_me_style_content.setText(style);
    }

    @Override
    public void initListeners() {
        btn_back.setOnClickListener(this);
        tv_me_style_ok.setOnClickListener(this);
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
        String style = PrefUtils.getString(StyleActivity.this, "style", "");
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.LOGIN_URL, RequestMethod.POST);
        request.add("apptoken",apptoken);
        request.add("op","changeUserInfo");
        request.add("uid","null");
        request.add("ctype",style);
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
