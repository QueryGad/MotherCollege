package com.player.mothercollege.me.details;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.player.mothercollege.R;
import com.player.mothercollege.activity.BaseActivity;
import com.player.mothercollege.adapter.SmartPayAdapter;


/**
 * Created by Administrator on 2016/10/7.
 */
public class SmartPayActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private Button btn_back;
    private TextView tv_details_title;
    private GridView gv_smartpay;
    private String [] muchs = {"50","100","200","300","500","1000","2000","5000","10000"};
    private String [] money = {"￥5","￥10","￥20","￥30","￥50","￥100","￥200","￥500","￥1000"};

    @Override
    public void setContentView() {
        setContentView(R.layout.act_me_smartpay);
    }

    @Override
    public void initViews() {
        btn_back = (Button) findViewById(R.id.btn_back);
        tv_details_title = (TextView) findViewById(R.id.tv_details_title);
        gv_smartpay = (GridView) findViewById(R.id.gv_smartpay);

        tv_details_title.setText("充值");
    }

    @Override
    public void initListeners() {
        btn_back.setOnClickListener(this);
        gv_smartpay.setOnItemClickListener(this);
    }

    @Override
    public void initData() {
        SmartPayAdapter adapter = new SmartPayAdapter(muchs,money,this);
        gv_smartpay.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this,"充值"+position,Toast.LENGTH_SHORT).show();
    }
}
