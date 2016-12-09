package com.player.mothercollege.activity;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.google.gson.Gson;
import com.player.mothercollege.R;
import com.player.mothercollege.bean.MyMessageNumBean;
import com.player.mothercollege.bean.Tab;
import com.player.mothercollege.fragment.CollegeFragment;
import com.player.mothercollege.fragment.FindFragment;
import com.player.mothercollege.fragment.MeFragment;
import com.player.mothercollege.fragment.UnityFragment;
import com.player.mothercollege.utils.ConfigUtils;
import com.player.mothercollege.utils.MyLog;
import com.player.mothercollege.utils.PrefUtils;
import com.player.mothercollege.view.MyFragmentTabHost;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/12.
 */
public class MainActivity extends BaseActivity{

    private static final int GET_MESSAGE_NUM = 001;
    private MyFragmentTabHost mTabhost;
    private LayoutInflater mInflater;
    private List<Tab> mTabs = new ArrayList<>(4);
    private boolean isPressedBackOnce = false;
    private long first =0;
    private long second = 0;
    private RequestQueue requestQueue;
    private TextView num;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            netWork();
            handler.sendEmptyMessageDelayed(11,60000);
        }
    };

    @Override
    public void setContentView() {
       setContentView(R.layout.activity_main);
        requestQueue = NoHttp.newRequestQueue();
    }

    @Override
    public void initViews() {
        netWork();
        handler.sendEmptyMessageAtTime(11,60000);
        initTab();
    }

    private void netWork() {
        String apptoken = PrefUtils.getString(MainActivity.this, "apptoken", "");
        String uid = PrefUtils.getString(MainActivity.this, "uid", "null");
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.ME_URL, RequestMethod.GET);
        request.add("apptoken",apptoken);
        request.add("uid",uid);
        request.add("op","newMsgCount");
        requestQueue.add(GET_MESSAGE_NUM, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                parseJsonMessageNum(info);
                MyLog.testLog("消息数量:"+info);
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    private void parseJsonMessageNum(String info) {
        Gson gson = new Gson();
        MyMessageNumBean myMessageNumBean =  gson.fromJson(info, MyMessageNumBean.class);
        int totalMsgCount = myMessageNumBean.getTotalMsgCount();
        if (totalMsgCount==0){
            num.setVisibility(View.GONE);
        }else {
            num.setVisibility(View.VISIBLE);
            num.setText(totalMsgCount+"");
            MyLog.testLog("我的消息总数:"+totalMsgCount);
        }
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
        num = (TextView) view.findViewById(R.id.tv_tab_num);
        img.setBackgroundResource(tab.getIcon());
        text.setText(tab.getTitle());
        if (tab==mTabs.get(3)){
            num.setVisibility(View.VISIBLE);
        }else {
            num.setVisibility(View.GONE);
        }

        return view;
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {

    }

}
