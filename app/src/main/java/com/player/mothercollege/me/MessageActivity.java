package com.player.mothercollege.me;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.player.mothercollege.R;
import com.player.mothercollege.activity.BaseActivity;
import com.player.mothercollege.me.details.MyCommentActivity;
import com.player.mothercollege.me.details.MyZanActivity;
import com.player.mothercollege.me.details.SystemMessageActivity;

/**
 * Created by Administrator on 2016/10/25.
 * 我的消息
 */
public class MessageActivity extends BaseActivity implements View.OnClickListener {

    private Button btn_back;
    private TextView tv_details_title;
    private LinearLayout ll_me_message_comment,ll_me_message_zan,ll_me_message_sys;

    @Override
    public void setContentView() {
        setContentView(R.layout.act_me_message);
    }

    @Override
    public void initViews() {
        btn_back = (Button) findViewById(R.id.btn_back);
        tv_details_title = (TextView) findViewById(R.id.tv_details_title);
        ll_me_message_comment = (LinearLayout) findViewById(R.id.ll_me_message_comment);
        ll_me_message_zan = (LinearLayout) findViewById(R.id.ll_me_message_zan);
        ll_me_message_sys = (LinearLayout) findViewById(R.id.ll_me_message_sys);

        tv_details_title.setText("我的消息");
    }

    @Override
    public void initListeners() {
        btn_back.setOnClickListener(this);
        ll_me_message_comment.setOnClickListener(this);
        ll_me_message_zan.setOnClickListener(this);
        ll_me_message_sys.setOnClickListener(this);
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
            case R.id.ll_me_message_comment:
                Intent intent1 = new Intent(MessageActivity.this, MyCommentActivity.class);
                startActivity(intent1);
                break;
            case R.id.ll_me_message_zan:
                Intent intent2 = new Intent(MessageActivity.this, MyZanActivity.class);
                startActivity(intent2);
                break;
            case R.id.ll_me_message_sys:
                Intent intent3 = new Intent(MessageActivity.this, SystemMessageActivity.class);
                startActivity(intent3);
                break;
        }
    }
}
