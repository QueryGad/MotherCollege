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


/**
 * Created by Administrator on 2016/10/5.
 * 帮助与反馈
 */
public class SettingHelpActivity extends BaseActivity implements View.OnClickListener {

    private Button btn_back,btn_help_send;
    private TextView tv_details_title;
    private AlertDialog dialog;
    private EditText et_help_content;

    @Override
    public void setContentView() {
        setContentView(R.layout.act_me_setting_help);
    }

    @Override
    public void initViews() {
        btn_back = (Button) findViewById(R.id.btn_back);
        tv_details_title = (TextView) findViewById(R.id.tv_details_title);
        btn_help_send = (Button) findViewById(R.id.btn_help_send);
        et_help_content = (EditText) findViewById(R.id.et_help_content);

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
                if (TextUtils.isEmpty(content)){
                    Toast.makeText(SettingHelpActivity.this,"请输入反馈内容",Toast.LENGTH_SHORT).show();
                }else {
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingHelpActivity.this);
                builder.setTitle("温馨提示");
                builder.setMessage("谢谢您的反馈");
                builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog = builder.show();
                dialog.setCanceledOnTouchOutside(false);}
                break;
        }
    }
}
