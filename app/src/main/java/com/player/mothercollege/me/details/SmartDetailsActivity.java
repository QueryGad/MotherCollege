package com.player.mothercollege.me.details;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.player.mothercollege.R;
import com.player.mothercollege.activity.BaseActivity;
import com.player.mothercollege.adapter.SmartDetailsAdapter;
import com.player.mothercollege.bean.SmartMoneyBean;
import com.player.mothercollege.utils.MyLog;
import com.player.mothercollege.view.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/10/7.
 */
public class SmartDetailsActivity extends BaseActivity implements View.OnClickListener {

    private Button btn_back;
    private TextView tv_details_title;
    private RecyclerView rv_smartdetails;
    private SmartMoneyBean smartMoneyBean;
    private List<SmartMoneyBean.DetailBean> detail = new ArrayList<>();
    private LinearLayout ll_smart_no;
    private SmartDetailsAdapter.OnItemClickListener SmartDetailsItemListener = new SmartDetailsAdapter.OnItemClickListener() {
        @Override
        public void onClick(View v, int position, SmartMoneyBean.DetailBean data) {
//            Toast.makeText(SmartDetailsActivity.this,position+"",Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void setContentView() {
        setContentView(R.layout.act_me_smartdetails);
    }

    @Override
    public void initViews() {
        smartMoneyBean = (SmartMoneyBean) getIntent().getSerializableExtra("smartDetails");
        MyLog.testLog("智慧币明细:"+smartMoneyBean);
        btn_back = (Button) findViewById(R.id.btn_back);
        tv_details_title = (TextView) findViewById(R.id.tv_details_title);
        rv_smartdetails = (RecyclerView) findViewById(R.id.rv_smartdetails);
        ll_smart_no = (LinearLayout) findViewById(R.id.ll_smart_no);

        tv_details_title.setText("智慧币明细");
        initRecycler();
    }

    private void initRecycler() {
        detail = smartMoneyBean.getDetail();
        if (detail.size()==0){
            ll_smart_no.setVisibility(View.VISIBLE);
        }else {
            SmartDetailsAdapter adapter = new SmartDetailsAdapter(SmartDetailsActivity.this,detail);
            rv_smartdetails.setAdapter(adapter);
            rv_smartdetails.setLayoutManager(new LinearLayoutManager(SmartDetailsActivity.this));
            rv_smartdetails.addItemDecoration(new DividerItemDecoration(SmartDetailsActivity.this,DividerItemDecoration.VERTICAL_LIST));
            adapter.setOnItemClickListener(SmartDetailsItemListener);
        }

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
