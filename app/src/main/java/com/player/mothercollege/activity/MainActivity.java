package com.player.mothercollege.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.player.mothercollege.R;
import com.player.mothercollege.bean.Tab;
import com.player.mothercollege.fragment.CollegeFragment;
import com.player.mothercollege.fragment.FindFragment;
import com.player.mothercollege.fragment.MeFragment;
import com.player.mothercollege.fragment.UnityFragment;
import com.player.mothercollege.view.MyFragmentTabHost;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/12.
 */
public class MainActivity extends BaseActivity{

    private MyFragmentTabHost mTabhost;
    private LayoutInflater mInflater;
    private List<Tab> mTabs = new ArrayList<>(4);
    private boolean isPressedBackOnce = false;
    private long first =0;
    private long second = 0;

    @Override
    public void setContentView() {
       setContentView(R.layout.activity_main);
    }

    @Override
    public void initViews() {
        initTab();
    }

    private void initTab() {
        mInflater = LayoutInflater.from(this);
        mTabhost = (MyFragmentTabHost) findViewById(R.id.frg_tabhost);
        mTabhost.setup(this,getSupportFragmentManager(),R.id.realtabcontent);

        Tab tab_college = new Tab(R.string.tab_college,R.drawable.tab_selector_college,CollegeFragment.class);
        Tab tab_unity = new Tab(R.string.tab_unity,R.drawable.tab_selector_unity,UnityFragment.class);
        Tab tab_find = new Tab(R.string.tab_find,R.drawable.tab_selector_find,FindFragment.class);
        Tab tab_me = new Tab(R.string.tab_me,R.drawable.tab_selector_me,MeFragment.class);
        mTabs.add(tab_college);
        mTabs.add(tab_unity);
        mTabs.add(tab_find);
        mTabs.add(tab_me);

        for (Tab tab: mTabs){
            TabHost.TabSpec tabSpec = mTabhost.newTabSpec(getString(tab.getTitle()));
            tabSpec.setIndicator(buildIndicator(tab));
            mTabhost.addTab(tabSpec, tab.getFragment(),null);
        }
        mTabhost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
        mTabhost.setCurrentTab(0);
    }

    private View buildIndicator(Tab tab){
        View view = mInflater.inflate(R.layout.tab_indicator,null);
        ImageView img = (ImageView) view.findViewById(R.id.icon_tab);
        TextView text = (TextView) view.findViewById(R.id.txt_indicator);
        TextView num = (TextView) view.findViewById(R.id.tv_tab_num);
        img.setBackgroundResource(tab.getIcon());
        text.setText(tab.getTitle());
        return view;
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {

    }

}
