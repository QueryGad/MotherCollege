package com.player.mothercollege.me.details;

import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.player.mothercollege.R;
import com.player.mothercollege.activity.BaseActivity;

/**
 * Created by Administrator on 2016/10/29.
 * 帖子详情
 */
public class PersonDetailsActivity extends BaseActivity implements View.OnClickListener {

    private Button btn_back;
    private TextView tv_details_title;

    private ListView lv_details;

    @Override
    public void setContentView() {
         setContentView(R.layout.act_me_persondetails);
    }

    @Override
    public void initViews() {


        btn_back = (Button) findViewById(R.id.btn_back);
        tv_details_title = (TextView) findViewById(R.id.tv_details_title);
        lv_details = (ListView) findViewById(R.id.lv_details);

        tv_details_title.setText("帖子详情");
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
