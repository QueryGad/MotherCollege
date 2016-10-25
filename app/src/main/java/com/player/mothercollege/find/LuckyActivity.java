package com.player.mothercollege.find;

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
 * Created by Administrator on 2016/10/25.
 * 我的福田
 */
public class LuckyActivity extends BaseActivity implements View.OnClickListener {

    private Button btn_back;
    private RadioGroup rg_find_lucky;
    private RadioButton rb_find_gengzhong,rb_find_jieli;
    private TextView tv_details_title;
    private ViewPager vp_find_lucky;
    private GengZhongFragment gengZhongFragment;
    private JieLiFragment jieLiFragment;
    private List<Fragment> lists = new ArrayList<>();
    private ViewPager.OnPageChangeListener LuckyPageListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            int check = getResources().getColor(R.color.indicator_true);
            int moren = getResources().getColor(R.color.indicator_false);
            rb_find_gengzhong.setTextColor(position == 0 ? check : moren);
            rb_find_jieli.setTextColor(position == 1 ? check : moren);
            if (position==0){
                rb_find_gengzhong.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
                rb_find_jieli.getPaint().setFlags(0);
            }else {
                rb_find_jieli.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
                rb_find_gengzhong.getPaint().setFlags(0);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
    private RadioGroup.OnCheckedChangeListener LuckyCheckListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId){
                case R.id.rb_find_gengzhong:
                    vp_find_lucky.setCurrentItem(0);
                    break;
                case R.id.rb_find_jieli:
                    vp_find_lucky.setCurrentItem(1);
                    break;
            }
        }
    };

    @Override
    public void setContentView() {
        setContentView(R.layout.act_find_lucky);

    }

    @Override
    public void initViews() {
        btn_back = (Button) findViewById(R.id.btn_back);
        tv_details_title = (TextView) findViewById(R.id.tv_details_title);
        rg_find_lucky = (RadioGroup) findViewById(R.id.rg_find_lucky);
        rb_find_gengzhong = (RadioButton) findViewById(R.id.rb_find_gengzhong);
        rb_find_jieli= (RadioButton) findViewById(R.id.rb_find_jieli);
        vp_find_lucky = (ViewPager) findViewById(R.id.vp_find_lucky);

        gengZhongFragment = new GengZhongFragment();
        jieLiFragment = new JieLiFragment();
        lists.clear();
        lists.add(gengZhongFragment);
        lists.add(jieLiFragment);

        tv_details_title.setText("我的福田");
    }

    @Override
    public void initListeners() {
        btn_back.setOnClickListener(this);
        vp_find_lucky.setOnPageChangeListener(LuckyPageListener);
        rg_find_lucky.setOnCheckedChangeListener(LuckyCheckListener);
    }

    @Override
    public void initData() {
        //设置默认
        rb_find_gengzhong.setChecked(true);
        rb_find_gengzhong.setTextColor(Color.RED);
        rb_find_gengzhong.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);


        LuckyAdapter adapter = new LuckyAdapter(getSupportFragmentManager());
        vp_find_lucky.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                finish();
                break;
        }
    }

    class LuckyAdapter extends FragmentPagerAdapter{

        public LuckyAdapter(FragmentManager fm) {
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
