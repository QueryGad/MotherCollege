package com.player.mothercollege.me;

import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.player.mothercollege.R;
import com.player.mothercollege.activity.BaseActivity;
import com.player.mothercollege.adapter.TakeAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/25.
 * 我的订阅
 */
public class TakeActivity extends BaseActivity implements View.OnClickListener {

    private Button btn_back;
    private RadioGroup rg_me_take;
    private RadioButton rb_me_take_dingyue,rb_me_take_jilu;
    private ViewPager vp_me_take;
    private DingYueFragment dingYue;
    private JiLuFragment jiLu;
    private List<Fragment> lists = new ArrayList<>();
    private RadioGroup.OnCheckedChangeListener TakeCheckedListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId){
                case R.id.rb_me_take_dingyue:
                    vp_me_take.setCurrentItem(0);
                    break;
                case R.id.rb_me_take_jilu:
                    vp_me_take.setCurrentItem(1);
                    break;
            }
        }
    };
    private ViewPager.OnPageChangeListener TakePageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            int check = getResources().getColor(R.color.indicator_true);
            int moren = getResources().getColor(R.color.indicator_false);
            rb_me_take_dingyue.setTextColor(position == 0 ? check : moren);
            rb_me_take_jilu.setTextColor(position == 1 ? check : moren);
            if (position==0){
                rb_me_take_dingyue.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
                rb_me_take_jilu.getPaint().setFlags(0);
            }else {
                rb_me_take_jilu.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
                rb_me_take_dingyue.getPaint().setFlags(0);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    public void setContentView() {
       setContentView(R.layout.act_me_take);
    }

    @Override
    public void initViews() {
        btn_back = (Button) findViewById(R.id.btn_back);
        rg_me_take = (RadioGroup) findViewById(R.id.rg_me_take);
        rb_me_take_dingyue = (RadioButton) findViewById(R.id.rb_me_take_dingyue);
        rb_me_take_jilu = (RadioButton) findViewById(R.id.rb_me_take_jilu);
        vp_me_take = (ViewPager) findViewById(R.id.vp_me_take);

        dingYue = new DingYueFragment();
        jiLu = new JiLuFragment();
        lists.clear();
        lists.add(dingYue);
        lists.add(jiLu);

        //默认耕种福田
        rb_me_take_dingyue.setChecked(true);
        rb_me_take_dingyue.setTextColor(Color.RED);
        rb_me_take_dingyue.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

    }

    @Override
    public void initListeners() {
        btn_back.setOnClickListener(this);
        rg_me_take.setOnCheckedChangeListener(TakeCheckedListener);
        vp_me_take.setOnPageChangeListener(TakePageChangeListener);
    }

    @Override
    public void initData() {
        //设置适配器
        TakeAdapter adapter = new TakeAdapter(getSupportFragmentManager(),lists);
        vp_me_take.setAdapter(adapter);
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
