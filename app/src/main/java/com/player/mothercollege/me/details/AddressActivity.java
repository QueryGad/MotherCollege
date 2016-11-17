package com.player.mothercollege.me.details;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.player.mothercollege.R;
import com.player.mothercollege.activity.BaseActivity;

/**
 * Created by Administrator on 2016/10/25.
 * 收货地址
 */
public class AddressActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout rl_add_address;
    private Button btn_back;
    private TextView tv_details_title;


    @Override
    public void setContentView() {
        setContentView(R.layout.act_me_address);
    }

    @Override
    public void initViews() {
        btn_back = (Button) findViewById(R.id.btn_back);
        tv_details_title = (TextView) findViewById(R.id.tv_details_title);
        rl_add_address = (RelativeLayout) findViewById(R.id.rl_add_address);

        tv_details_title.setText("收货地址");
    }

    @Override
    public void initListeners() {
        btn_back.setOnClickListener(this);
        rl_add_address.setOnClickListener(this);
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

            case R.id.rl_add_address:
                Intent intent = new Intent(AddressActivity.this,PickerActivity.class);
                startActivity(intent);
               break;
        }
    }
}
