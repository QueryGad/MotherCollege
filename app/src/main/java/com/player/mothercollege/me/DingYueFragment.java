package com.player.mothercollege.me;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.player.mothercollege.R;
import com.player.mothercollege.adapter.DingYueAdapter;
import com.player.mothercollege.bean.DingYueBean;
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
 * 我的订阅
 */
public class DingYueFragment extends Fragment implements MyUpDownListView.OnRefreshListener{

    private static final int GET_DINGYUE_DATA = 001;
    private static final int GET_MORE_DATA = 002;
    private View view;
    private MyUpDownListView lv_dingyue;
    private ImageView iv_refresh;
    private Button btn_refrsh;
    private LinearLayout ll_dingyue_no;
    private RequestQueue requestQueue;

    int lastIndex=0;
    boolean isRefresh = true;
    private int endNo;
    private String apptoken;
    private String uid;
    private DingYueAdapter adapter;
    private List<DingYueBean.MyPayClassBean> myPayClassList;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.frg_me_dingyue,null);
        requestQueue = NoHttp.newRequestQueue();
        initView();
        initData();
        if(isRefresh){
            initData();
        }
        return view;
    }

    private void initView() {
        lv_dingyue = (MyUpDownListView) view.findViewById(R.id.lv_dingyue);
        iv_refresh = (ImageView) view.findViewById(R.id.iv_refresh);
        btn_refrsh = (Button) view.findViewById(R.id.btn_refrsh);
        ll_dingyue_no = (LinearLayout) view.findViewById(R.id.ll_dingyue_no);

        lv_dingyue.setOnRefreshListener(this);
    }

    private void initData() {
        netWork();
    }

    private void netWork() {
        apptoken = PrefUtils.getString(getActivity(), "apptoken", "");
        uid = PrefUtils.getString(getActivity(), "uid", "");
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.ME_URL, RequestMethod.GET);
        request.add("apptoken", apptoken);
        request.add("uid", uid);
        request.add("op","mysubscriber");
        request.add("lastIndex",lastIndex+"");
        requestQueue.add(GET_DINGYUE_DATA, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                iv_refresh.setVisibility(View.GONE);
                btn_refrsh.setVisibility(View.GONE);
                String info = response.get();
                MyLog.testLog("我的订阅:"+info);
                if (info!=null){
                    parseJson(info);
                }

            }

            @Override
            public void onFailed(int what, Response<String> response) {
                iv_refresh.setVisibility(View.VISIBLE);
                btn_refrsh.setVisibility(View.VISIBLE);
                btn_refrsh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        netWork();
                    }
                });
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    private void parseJson(String info){
        Gson gson = new Gson();
        DingYueBean dingYueBean = gson.fromJson(info, DingYueBean.class);

        if (dingYueBean!=null){
            endNo = dingYueBean.getLastIndex();//目标索引
            infos = dingYueBean.getMyPayClass();
            if (infos.size()==0){
                ll_dingyue_no.setVisibility(View.VISIBLE);
            }else {
                adapter = new DingYueAdapter(getActivity(),infos);
                lv_dingyue.setAdapter(adapter);
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
                lv_dingyue.hideHeaderView();
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
                lv_dingyue.hideFooterView();
            }
        }.execute();
    }

    private void setAddMoreData() {
        lastIndex = endNo;
        sendAddHomeLvRequest();
    }

    private List<DingYueBean.MyPayClassBean> infos = new ArrayList<>();
    private void sendAddHomeLvRequest() {
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.ME_URL, RequestMethod.GET);
        request.add("apptoken", apptoken);
        request.add("uid", uid);
        request.add("op","mysubscriber");
        request.add("lastIndex",lastIndex+"");
        requestQueue.add(GET_MORE_DATA, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                Gson gson = new Gson();
                DingYueBean dingYueBean = gson.fromJson(info, DingYueBean.class);
                if (lastIndex!=0) {
                    endNo = dingYueBean.getLastIndex();
                    lastIndex = endNo;
                    myPayClassList = dingYueBean.getMyPayClass();
                    infos.addAll(myPayClassList);
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
