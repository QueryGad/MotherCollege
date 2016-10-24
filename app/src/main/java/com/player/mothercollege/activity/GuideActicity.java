package com.player.mothercollege.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.player.mothercollege.R;
import com.player.mothercollege.adapter.GuidePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/7.
 */
public class GuideActicity extends Activity{

    private ViewPager vp;
    private Button start;
    private List<Integer> lists = new ArrayList<>();
    private ViewPager.OnPageChangeListener GuidePageListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            //如果是最后一个界面  显示启动按钮
            if (position==lists.size()-1){
                start.setVisibility(View.VISIBLE);
                start.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(GuideActicity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }else {
                start.setVisibility(View.GONE);
            }
        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initView();
        initData();
    }

    private void initView() {
        vp = (ViewPager) findViewById(R.id.guide_vp);
        start = (Button) findViewById(R.id.btn_start);
        lists.add(R.mipmap.guide_a);
        lists.add(R.mipmap.guide_b);
        lists.add(R.mipmap.guide_c);
    }

    private void initData() {
        GuidePagerAdapter adapter = new GuidePagerAdapter(this,lists);
        vp.setAdapter(adapter);
        //使最后一个界面有Button按钮有启动界面  添加一个页面改变监听
        vp.setOnPageChangeListener(GuidePageListener);
    }


}
