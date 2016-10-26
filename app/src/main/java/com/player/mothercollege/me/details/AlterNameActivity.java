package com.player.mothercollege.me.details;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.player.mothercollege.R;
import com.player.mothercollege.activity.BaseActivity;
import com.player.mothercollege.utils.PrefUtils;

/**
 * Created by Administrator on 2016/10/25.
 * 修改昵称
 */
public class AlterNameActivity extends BaseActivity implements View.OnClickListener {

    private Button btn_back;
    private TextView tv_me_alter_ok;
    private EditText et_me_alter_content;

    @Override
    public void setContentView() {
       setContentView(R.layout.act_me_altername);
    }

    @Override
    public void initViews() {
        btn_back = (Button) findViewById(R.id.btn_back);
        tv_me_alter_ok = (TextView) findViewById(R.id.tv_me_alter_ok);
        et_me_alter_content = (EditText) findViewById(R.id.et_me_alter_content);
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
                    finish();
                }
                break;
        }
    }
}
