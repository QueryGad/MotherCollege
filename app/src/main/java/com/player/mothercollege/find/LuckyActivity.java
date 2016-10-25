package com.player.mothercollege.find;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.player.mothercollege.R;
import com.player.mothercollege.activity.BaseActivity;

/**
 * Created by Administrator on 2016/10/25.
 * 我的福田
 */
public class LuckyActivity extends BaseActivity implements View.OnClickListener {

    private Button btn_back;
    private TextView tv_details_title;

    @Override
    public void setContentView() {
        setContentView(R.layout.act_find_lucky);
    }

    @Override
    public void initViews() {
        btn_back = (Button) findViewById(R.id.btn_back);
        tv_details_title = (TextView) findViewById(R.id.tv_details_title);

        tv_details_title.setText("我的福田");
    }

    @Override
    public void initListeners() {
        btn_back.setOnClickListener(this);
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
        }
    }
}
