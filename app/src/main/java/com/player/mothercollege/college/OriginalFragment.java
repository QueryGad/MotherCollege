package com.player.mothercollege.college;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.player.mothercollege.R;
import com.player.mothercollege.adapter.OriginalAdapter;
import com.player.mothercollege.bean.OriginalBean;
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
 * 原创页面
 */
public class OriginalFragment extends Fragment implements MyUpDownListView.OnRefreshListener{

    private View view;
    private static final int GET_ORIGINAL_DATA = 001;
    private MyUpDownListView lv_college_original;
    private RequestQueue requestQueue;

    int lastIndex=0;
    boolean isRefresh = true;

    private List<OriginalBean.BooksBean> booksList = new ArrayList<>();

    private OriginalAdapter adapter;
    private int endNo ;
    private String apptoken;
    private String uid;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.frg_college_original,null);
        requestQueue = NoHttp.newRequestQueue();
        initView();
        initData();
        if(isRefresh){
            initData();
        }

        return view;
    }

    private void initView() {
        lv_college_original = (MyUpDownListView) view.findViewById(R.id.lv_college_original);
        lv_college_original.setOnRefreshListener(this);
    }

    private void netWork() {
        lastIndex =0;
        apptoken = PrefUtils.getString(getActivity(), "apptoken", "");
        uid = PrefUtils.getString(getActivity(), "uid", "");
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.COLLEGE_URL, RequestMethod.GET);
        request.add("op","yc");
        request.add("apptoken", apptoken);
        request.add("uid", uid);
        request.add("lastIndex",lastIndex+"");
        requestQueue.add(GET_ORIGINAL_DATA, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                MyLog.testLog("原创页面:"+info);
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
        OriginalBean originalBean = gson.fromJson(info, OriginalBean.class);


        if (originalBean!=null){
            endNo = originalBean.getLastIndex();
            infos = originalBean.getBooks();
            adapter = new OriginalAdapter(infos,getActivity());
            lv_college_original.setAdapter(adapter);
        }

    }


    private void initData() {
        netWork();
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
                lv_college_original.hideHeaderView();
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

                lv_college_original.hideFooterView();
            }
        }.execute();
    }

    private void setAddMoreData() {
        lastIndex = endNo;
        MyLog.testLog("最初下拉加载更多:"+lastIndex);
        sendAddHomeLvRequest();
    }

    private List<OriginalBean.BooksBean> infos = new ArrayList<>();
    private void sendAddHomeLvRequest() {
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.COLLEGE_URL, RequestMethod.GET);
        request.add("op","yc");
        request.add("apptoken", apptoken);
        request.add("uid", uid);
        request.add("lastIndex",lastIndex+"");
        requestQueue.add(002, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                Gson gson = new Gson();
                OriginalBean originalBean = gson.fromJson(info, OriginalBean.class);
                MyLog.testLog("下拉拉到的数据:"+info);
                if (lastIndex!=0) {
                    endNo = originalBean.getLastIndex();
                    lastIndex = endNo;
                    MyLog.testLog("获取到下拉加载更多:"+lastIndex);
                    booksList = originalBean.getBooks();
                    originalBean.getBooks();
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
