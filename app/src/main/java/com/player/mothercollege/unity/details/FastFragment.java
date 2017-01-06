package com.player.mothercollege.unity.details;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.player.mothercollege.R;
import com.player.mothercollege.adapter.FastInquiryAdapter;
import com.player.mothercollege.bean.FastBean;
import com.player.mothercollege.login.LoginActivity;
import com.player.mothercollege.utils.ConfigUtils;
import com.player.mothercollege.utils.MyLog;
import com.player.mothercollege.utils.PrefUtils;
import com.player.mothercollege.view.MyUpDownListView;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/9/21.
 * 快速咨询页面
 */
public class FastFragment extends Fragment implements View.OnClickListener,MyUpDownListView.OnRefreshListener{

    private static final int GET_FAST_DATA = 001;
    private static final int GET_MORE_DATA = 002;
    private View view;
    private MyUpDownListView lv_unity_fast;
    private ImageView iv_fast_public;
    private RequestQueue requestQueue;
    private String apptoken;
    private String uid;

    int lastIndex=0;
    boolean isRefresh = true;
    private int endNo ;
    private FastInquiryAdapter adapter;
    private List<FastBean.QustionsBean> qustions;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = View.inflate(getActivity(), R.layout.frg_zixun_fast,null);
        requestQueue = NoHttp.newRequestQueue();
        initView();
        initData();
        if(isRefresh){
            initData();
        }
        return view;
    }

    private void initView() {
        lv_unity_fast = (MyUpDownListView) view.findViewById(R.id.lv_unity_fast);
        iv_fast_public = (ImageView) view.findViewById(R.id.iv_fast_public);
        lv_unity_fast.setOnRefreshListener(this);
        iv_fast_public.setOnClickListener(this);
    }

    private void initData() {
       netWork();
    }

    private void netWork() {
        apptoken = PrefUtils.getString(getActivity(), "apptoken", "");
        uid = PrefUtils.getString(getActivity(), "uid", "");
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.UNITY_URL, RequestMethod.GET);
        request.add("apptoken", apptoken);
        request.add("op","quickqustion");
        request.add("lastIndex",lastIndex+"");
        requestQueue.add(GET_FAST_DATA, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                MyLog.testLog("快速咨询:"+info);
                parseJson(info);
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    private void parseJson(String info){
        Gson gson = new Gson();
        FastBean fastBean = gson.fromJson(info, FastBean.class);
        if (fastBean!=null){
            endNo = fastBean.getLastIndex();//目标索引
            infos = fastBean.getQustions();
            adapter = new FastInquiryAdapter(getActivity(),infos);
            lv_unity_fast.setAdapter(adapter);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_fast_public:
                //点击进入提问页面
                if (uid.equals("")){
                    //未登录  提示登录
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(getActivity(),RequestActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }

    @Override
    public void onDownPullRefresh() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                SystemClock.sleep(500);
                lastIndex = 0;
                netWork();
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                lv_unity_fast.hideHeaderView();
            }
        }.execute();
    }

    @Override
    public void onLoadingMore() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                SystemClock.sleep(500);
                setAddMoreData();
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {

                lv_unity_fast.hideFooterView();
            }
        }.execute();
    }

    private void setAddMoreData() {
        lastIndex = endNo;
        sendAddHomeLvRequest();
    }

    private List<FastBean.QustionsBean> infos = new ArrayList<>();
    private void sendAddHomeLvRequest() {
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.UNITY_URL, RequestMethod.GET);
        request.add("apptoken", apptoken);
        request.add("op","quickqustion");
        request.add("lastIndex",lastIndex+"");
        requestQueue.add(GET_MORE_DATA, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                Gson gson = new Gson();
                FastBean fastBean = gson.fromJson(info, FastBean.class);
                MyLog.testLog("下拉数据:"+info);
                if (lastIndex!=0) {
                    endNo = fastBean.getLastIndex();
                    lastIndex = endNo;
                    qustions = fastBean.getQustions();
                    fastBean.getQustions();
                    infos.addAll(qustions);
                    adapter.notifyDataSetChanged();
                } else {
//                    toast("没有更多数据");
                    Toast.makeText(getActivity(),"没有更多数据了",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }
}
