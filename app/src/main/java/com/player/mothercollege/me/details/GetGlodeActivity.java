package com.player.mothercollege.me.details;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.player.mothercollege.R;
import com.player.mothercollege.activity.BaseActivity;


/**
 * Created by Administrator on 2016/10/7.
 * 金币提现
 */
public class GetGlodeActivity extends BaseActivity implements View.OnClickListener {

    private Button btn_back,btn_getMoney;
    private EditText et_getMoney;
    private TextView tv_details_title;

    @Override
    public void setContentView() {
        setContentView(R.layout.act_me_getglode);
    }

    @Override
    public void initViews() {
        btn_back = (Button) findViewById(R.id.btn_back);
        btn_getMoney = (Button) findViewById(R.id.btn_getMoney);
        et_getMoney = (EditText) findViewById(R.id.et_getMoney);
        tv_details_title = (TextView) findViewById(R.id.tv_details_title);

        tv_details_title.setText("提现");
    }

    @Override
    public void initListeners() {
        btn_back.setOnClickListener(this);
        btn_getMoney.setOnClickListener(this);
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
            case R.id.btn_getMoney:
                String howMoney = et_getMoney.getText().toString().trim();
                if (TextUtils.isEmpty(howMoney)){
                    Toast.makeText(GetGlodeActivity.this,"请输入提现金额",Toast.LENGTH_SHORT).show();
                    break;
                }
                break;
        }
    }
}
