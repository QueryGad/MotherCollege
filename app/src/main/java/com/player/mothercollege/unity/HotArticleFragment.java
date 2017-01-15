package com.player.mothercollege.unity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.player.mothercollege.R;
import com.player.mothercollege.adapter.HotArticleAdapter;
import com.player.mothercollege.bean.HotArticleBean;
import com.player.mothercollege.utils.CacheUtils;
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
 * Created by Administrator on 2016/10/25.
 * 热帖页面
 */
public class HotArticleFragment extends Fragment implements MyUpDownListView.OnRefreshListener{

    private static final int GET_HOTARTICLE_DATA = 001;
    private static final int GET_MORE_DATA = 002;
    private View view;
    private MyUpDownListView lv_hotarticle;
    private RequestQueue requestQueue;
    private List<HotArticleBean.TrendsBean> trendsList;
    private String apptoken;
    private String uid;
    private HotArticleBean hotArticleBean;
    private HotArticleAdapter adapter;

    int lastIndex=0;
    boolean isRefresh = true;
    private int endNo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.frg_unity_hotarticle,null);
        requestQueue = NoHttp.newRequestQueue();
        initView();
        initData();
        if(isRefresh){
            initData();
        }
        return view;
    }

    private void initView() {
        lv_hotarticle = (MyUpDownListView) view.findViewById(R.id.lv_hotarticle);
        lv_hotarticle.setOnRefreshListener(this);
    }

    private void initData() {
        String cacheJson = CacheUtils.getCache(getActivity(), ConfigUtils.UNITY_URL + "unity_hotarticle");
        if (!TextUtils.isEmpty(cacheJson)){
            parseJson(cacheJson);
        }
        netWork();
    }

    private void netWork() {
        apptoken = PrefUtils.getString(getActivity(), "apptoken", "");
        uid = PrefUtils.getString(getActivity(), "uid", "");
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.UNITY_URL, RequestMethod.GET);
        request.add("apptoken", apptoken);
        request.add("op","retie");
        request.add("uid", uid);
        request.add("lastIndex",lastIndex+"");
        requestQueue.add(GET_HOTARTICLE_DATA, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                MyLog.testLog("热帖:"+info);
                parseJson(info);
                CacheUtils.saveCache(getActivity(),ConfigUtils.UNITY_URL + "unity_hotarticle",info);
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Toast.makeText(getActivity(),"网络已断开,请检查您的网络!",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    private void parseJson(String info){
        if (info!=null){
            Gson gson = new Gson();
            hotArticleBean = gson.fromJson(info, HotArticleBean.class);
            trendsList = hotArticleBean.getTrends();

            if (hotArticleBean!=null){
                endNo = hotArticleBean.getLastIndex();//目标索引
                infos = hotArticleBean.getTrends();
                adapter = new HotArticleAdapter(getActivity(),infos);
                lv_hotarticle.setAdapter(adapter);
            }
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
                lv_hotarticle.hideHeaderView();
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

                lv_hotarticle.hideFooterView();
            }
        }.execute();
    }

    private void setAddMoreData() {
        lastIndex = endNo;
        sendAddHomeLvRequest();
    }

    private List<HotArticleBean.TrendsBean> infos = new ArrayList<>();
    private void sendAddHomeLvRequest() {
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.UNITY_URL, RequestMethod.GET);
        request.add("apptoken", apptoken);
        request.add("op","retie");
        request.add("uid", uid);
        request.add("lastIndex",lastIndex+"");
        requestQueue.add(GET_MORE_DATA, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                Gson gson = new Gson();
                HotArticleBean hotArticleBean = gson.fromJson(info, HotArticleBean.class);
                MyLog.testLog("下拉数据:"+info);
                if (lastIndex!=0) {
                    endNo = hotArticleBean.getLastIndex();
                    lastIndex = endNo;
                    trendsList = hotArticleBean.getTrends();
                    hotArticleBean.getTrends();
                    infos.addAll(trendsList);
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
