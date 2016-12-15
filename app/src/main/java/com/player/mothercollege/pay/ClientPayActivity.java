package com.player.mothercollege.pay;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.player.mothercollege.R;
import com.player.mothercollege.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/15.
 */
public class ClientPayActivity extends BaseActivity implements View.OnClickListener {

    private Button btn_back;
    private RadioGroup rg_client;
    private RadioButton rb_client_bank,rb_client_wechat;
    private ViewPager vp_me_client;
    private TextView tv_details_let;
    private BankClientFragment bankClientFragment;
    private WeChatClientFragment weChatClientFragment;
    private List<Fragment> lists = new ArrayList<>();
    private ViewPager.OnPageChangeListener ClientPageListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            int check = getResources().getColor(R.color.indicator_true);
            int moren = getResources().getColor(R.color.indicator_false);
            rb_client_bank.setTextColor(position == 0 ? check : moren);
            rb_client_wechat.setTextColor(position == 1 ? check : moren);
            if (position==0){
                rb_client_bank.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
                rb_client_wechat.getPaint().setFlags(0);
            }else {
                rb_client_wechat.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
                rb_client_bank.getPaint().setFlags(0);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
    private RadioGroup.OnCheckedChangeListener ClientCheckListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId){
                case R.id.rb_client_bank:
                    vp_me_client.setCurrentItem(0);
                    break;
                case R.id.rb_client_wechat:
                    vp_me_client.setCurrentItem(1);
                    break;
            }
        }
    };
    private int payMoney;


    @Override
    public void setContentView() {
       setContentView(R.layout.act_me_client);
    }

    @Override
    public void initViews() {

        payMoney = getIntent().getIntExtra("payMoney", 0);

        btn_back = (Button) findViewById(R.id.btn_back);
        rg_client = (RadioGroup) findViewById(R.id.rg_client);
        rb_client_bank = (RadioButton) findViewById(R.id.rb_client_bank);
        rb_client_wechat = (RadioButton) findViewById(R.id.rb_client_wechat);
        vp_me_client = (ViewPager) findViewById(R.id.vp_me_client);
        tv_details_let = (TextView) findViewById(R.id.tv_details_let);

        bankClientFragment = new BankClientFragment();
        weChatClientFragment = new WeChatClientFragment();

        lists.clear();
        lists.add(bankClientFragment);
        lists.add(weChatClientFragment);

    }

    @Override
    public void initListeners() {
        btn_back.setOnClickListener(this);
        tv_details_let.setOnClickListener(this);
        vp_me_client.setOnPageChangeListener(ClientPageListener);
        rg_client.setOnCheckedChangeListener(ClientCheckListener);
    }

    @Override
    public void initData() {
        //设置默认
        rb_client_bank.setChecked(true);
        rb_client_bank.setTextColor(Color.RED);
        rb_client_bank.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);


        ClientPayAdapter adapter = new ClientPayAdapter(getSupportFragmentManager());
        vp_me_client.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                finish();
                break;
            case R.id.tv_details_let:
                //提现说明
                Intent intent = new Intent(ClientPayActivity.this,ClientExplainActivity.class);
                startActivity(intent);
                break;
        }
    }


    class ClientPayAdapter extends FragmentPagerAdapter {

        public ClientPayAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return lists.get(position);
        }

        @Override
        public int getCount() {
            return lists.size();
        }
    }
}
