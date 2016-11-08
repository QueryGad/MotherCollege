package com.player.mothercollege.me.details;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.player.mothercollege.R;
import com.player.mothercollege.activity.BaseActivity;

/**
 * Created by Administrator on 2016/11/8.
 */
public class CirlePostMessageActivity extends BaseActivity implements View.OnClickListener {

    private Button btn_back;
    private TextView tv_details_title;
    private EditText et_postmessage_title,et_postmessage_content;
    private ImageView iv_postmessage_camer;
    private Button btn_postmessage;

    @Override
    public void setContentView() {
        setContentView(R.layout.act_me_postmessage);
    }

    @Override
    public void initViews() {
        btn_back = (Button) findViewById(R.id.btn_back);
        tv_details_title = (TextView) findViewById(R.id.tv_details_title);
        et_postmessage_title = (EditText) findViewById(R.id.et_postmessage_title);
        et_postmessage_content = (EditText) findViewById(R.id.et_postmessage_content);
        iv_postmessage_camer = (ImageView) findViewById(R.id.iv_postmessage_camer);
        btn_postmessage = (Button) findViewById(R.id.btn_postmessage);

        tv_details_title.setText("发帖");
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
                AlertDialog.Builder builder = new AlertDialog.Builder(CirlePostMessageActivity.this);
                builder.setTitle("放弃发帖");
                builder.setMessage("确定放弃此次操作?");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                builder.show();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CirlePostMessageActivity.this);
        builder.setTitle("放弃发帖");
        builder.setMessage("确定放弃此次操作?");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.show();
    }
}
