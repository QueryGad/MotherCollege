package com.player.mothercollege.college;

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
import com.player.mothercollege.adapter.ReadBookAdapter;
import com.player.mothercollege.bean.ReadBookBean;
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
 * 读书页面
 */
public class ReadBookFragment extends Fragment implements MyUpDownListView.OnRefreshListener {

    private static final int GET_READBOOK_DATA = 001;
    private static final int GET_READBOOK_DATA2 = 002;
    private View view;
    private MyUpDownListView lv_readbook;
    int lastIndex=0;
    boolean isRefresh = true;

    private RequestQueue requestQueue;
    private int endNo;
    private List<ReadBookBean.BooksBean> booksList;
    private String apptoken;
    private ReadBookAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.frg_college_readbook,null);
        requestQueue = NoHttp.newRequestQueue();
        initView();
        initData();
        if(isRefresh){
            initData();
        }

        return view;
    }

    private void initView() {
        lv_readbook = (MyUpDownListView) view.findViewById(R.id.lv_readbook);
        lv_readbook.setOnRefreshListener(this);
    }



    private void initData() {
        String cacheJson = CacheUtils.getCache(getActivity(), ConfigUtils.COLLEGE_URL + "college_readbook");
        if (!TextUtils.isEmpty(cacheJson)){
            parseJson(cacheJson);
        }
        netWork();
    }

    private void netWork() {
        lastIndex =0;
        apptoken = PrefUtils.getString(getActivity(), "apptoken", "");
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.COLLEGE_URL, RequestMethod.GET);
        request.add("op","ds");
        request.add("lastIndex",lastIndex+"");//++
        request.add("apptoken", apptoken);
        requestQueue.add(GET_READBOOK_DATA, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                MyLog.testLog("读书页面"+info);
                parseJson(info);
                CacheUtils.saveCache(getActivity(),ConfigUtils.COLLEGE_URL + "college_readbook",info);
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
        ReadBookBean readBookBean = gson.fromJson(info, ReadBookBean.class);
        if (readBookBean!=null){
            endNo = readBookBean.getEndNo();
            infos = readBookBean.getBooks();
            adapter = new ReadBookAdapter(getActivity(),infos);
            lv_readbook.setAdapter(adapter);
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
                lv_readbook.hideHeaderView();
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

                lv_readbook.hideFooterView();
            }
        }.execute();
    }

    private void setAddMoreData() {
        lastIndex = endNo;
        sendAddHomeLvRequest();
    }

    private List<ReadBookBean.BooksBean> infos = new ArrayList<>();
    private void sendAddHomeLvRequest() {
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.COLLEGE_URL, RequestMethod.GET);
        request.add("op","ds");
        request.add("lastIndex",lastIndex+"");
        request.add("apptoken", apptoken);
        requestQueue.add(002, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                Gson gson = new Gson();
                ReadBookBean readBookBean = gson.fromJson(info, ReadBookBean.class);
                if (readBookBean !=null) {
                    endNo = readBookBean.getEndNo();
                    lastIndex = endNo;
                    booksList = readBookBean.getBooks();
                    infos.addAll(booksList);
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
