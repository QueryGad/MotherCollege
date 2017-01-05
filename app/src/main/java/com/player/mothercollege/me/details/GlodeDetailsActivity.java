package com.player.mothercollege.me.details;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.player.mothercollege.R;
import com.player.mothercollege.activity.BaseActivity;
import com.player.mothercollege.adapter.GlodeDetailsAdapter;
import com.player.mothercollege.bean.GlodeMoneyBean;
import com.player.mothercollege.view.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/10/7.
 * 金币明细
 */
public class GlodeDetailsActivity extends BaseActivity implements View.OnClickListener {

    private static final int GET_GLODEDETAILS_DATA = 001;
    private Button btn_back;
    private TextView tv_details_title;
    private RecyclerView rv_glodedetails;
    private GlodeMoneyBean glodeMoneyBean;
    private List<GlodeMoneyBean.DetailBean> detail = new ArrayList<>();
    private GlodeDetailsAdapter.OnItemClickListener GlodeDetailsItemListener = new GlodeDetailsAdapter.OnItemClickListener() {
        @Override
        public void onClick(View v, int position, GlodeMoneyBean.DetailBean data) {
//            Toast.makeText(GlodeDetailsActivity.this,position+"",Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void setContentView() {
        setContentView(R.layout.act_me_glodedetsils);
    }

    @Override
    public void initViews() {

        glodeMoneyBean = (GlodeMoneyBean) getIntent().getSerializableExtra("glodeDetails");

        btn_back = (Button) findViewById(R.id.btn_back);
        tv_details_title = (TextView) findViewById(R.id.tv_details_title);
        rv_glodedetails = (RecyclerView) findViewById(R.id.rv_glodedetails);

        tv_details_title.setText("金币明细");
        initRecycler();
    }

    private void initRecycler() {
        detail = glodeMoneyBean.getDetail();
        GlodeDetailsAdapter adapter = new GlodeDetailsAdapter(GlodeDetailsActivity.this,detail);
        rv_glodedetails.setAdapter(adapter);
        rv_glodedetails.setLayoutManager(new LinearLayoutManager(GlodeDetailsActivity.this));
        rv_glodedetails.addItemDecoration(new DividerItemDecoration(GlodeDetailsActivity.this,DividerItemDecoration.VERTICAL_LIST));
        adapter.setOnItemClickListener(GlodeDetailsItemListener);
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
