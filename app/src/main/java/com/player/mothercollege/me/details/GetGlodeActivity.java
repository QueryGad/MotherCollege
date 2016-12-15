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
import com.player.mothercollege.pay.ClientPayActivity;


/**
 * Created by Administrator on 2016/10/7.
 * 金币提现
 */
public class GetGlodeActivity extends BaseActivity implements View.OnClickListener {

    private Button btn_back,btn_getMoney;
    private EditText et_getMoney;
    private TextView tv_details_title;
    private int currentNum;


    @Override
    public void setContentView() {
        setContentView(R.layout.act_me_getglode);
    }

    @Override
    public void initViews() {
        currentNum = getIntent().getIntExtra("currentNum", 0);
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
                int payMoney = 0;
                if (howMoney==null){
                    payMoney = Integer.parseInt(howMoney);
                }

                if (TextUtils.isEmpty(howMoney)){
                    Toast.makeText(GetGlodeActivity.this,"请输入提现金额",Toast.LENGTH_SHORT).show();
                    break;
                }else if (payMoney>currentNum){
                    //输入金额大于当前客户所有金额
                    Toast.makeText(GetGlodeActivity.this,"提现金额大于现有金额，请重新输入",Toast.LENGTH_SHORT).show();
                    et_getMoney.setText("");
                    break;
                }else {
                    Intent intent = new Intent(GetGlodeActivity.this, ClientPayActivity.class);
                    intent.putExtra("payMoney",payMoney);
                    startActivity(intent);
                }
                break;
        }
    }
}
