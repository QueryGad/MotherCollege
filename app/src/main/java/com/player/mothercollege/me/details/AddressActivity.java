package com.player.mothercollege.me.details;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;

import com.player.mothercollege.R;
import com.player.mothercollege.activity.BaseActivity;

/**
 * Created by Administrator on 2016/10/25.
 * 收货地址
 */
public class AddressActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout rl_add_address;


    @Override
    public void setContentView() {
        setContentView(R.layout.act_me_address);
    }

    @Override
    public void initViews() {
        rl_add_address = (RelativeLayout) findViewById(R.id.rl_add_address);
    }

    @Override
    public void initListeners() {
        rl_add_address.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_add_address:
                Intent intent = new Intent(AddressActivity.this,PickerActivity.class);
                startActivity(intent);
               break;
        }
    }
}
