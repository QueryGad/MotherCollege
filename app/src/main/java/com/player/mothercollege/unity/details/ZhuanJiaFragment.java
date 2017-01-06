package com.player.mothercollege.unity.details;

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
import android.widget.Toast;

import com.google.gson.Gson;
import com.player.mothercollege.R;
import com.player.mothercollege.adapter.ZhuanJiaAdapter;
import com.player.mothercollege.bean.ZhuanJiaBean;
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
 */
public class ZhuanJiaFragment extends Fragment implements MyUpDownListView.OnRefreshListener{

    private static final int GET_ZHUANJIA_DATA = 001;
    private static final int GET_MORE_DATA = 002;
    private View view;
    private MyUpDownListView lv_unity_zhuanjia;
    private RequestQueue requestQueue;
    private ImageView iv_refresh;
    private Button btn_refrsh;

    int lastIndex=0;
    boolean isRefresh = true;
    private int endNo;
    private ZhuanJiaAdapter adapter;
    private String apptoken;
    private List<ZhuanJiaBean.ExpertsBean> experts;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.frg_zixun_zhuanjia,null);
        requestQueue = NoHttp.newRequestQueue();
        initView();
        initData();
        if(isRefresh){
            initData();
        }
        return view;
    }

    private void initView() {
        lv_unity_zhuanjia = (MyUpDownListView) view.findViewById(R.id.lv_unity_zhuanjia);
        iv_refresh = (ImageView) view.findViewById(R.id.iv_refresh);
        btn_refrsh = (Button) view.findViewById(R.id.btn_refrsh);
        lv_unity_zhuanjia.setOnRefreshListener(this);
    }

    private void initData() {
        netWork();
    }

    private void netWork() {
        apptoken = PrefUtils.getString(getActivity(), "apptoken", "");
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.UNITY_URL, RequestMethod.GET);
        request.add("apptoken", apptoken);
        request.add("op","experts");
        request.add("lastIndex",lastIndex+"");
        requestQueue.add(GET_ZHUANJIA_DATA, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                iv_refresh.setVisibility(View.GONE);
                btn_refrsh.setVisibility(View.GONE);
                String info = response.get();
                MyLog.testLog("专家咨询"+info);
                parseJson(info);
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                iv_refresh.setVisibility(View.VISIBLE);
                btn_refrsh.setVisibility(View.VISIBLE);
                btn_refrsh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        netWork();
                        //TODO: 2016/10/29  点击未反馈
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
        ZhuanJiaBean zhuanJiaBean = gson.fromJson(info, ZhuanJiaBean.class);

        if (zhuanJiaBean!=null){
            endNo =  zhuanJiaBean.getLastIndex();//目标索引
            infos =zhuanJiaBean.getExperts();
            adapter = new ZhuanJiaAdapter(getActivity(),infos);
            lv_unity_zhuanjia.setAdapter(adapter);
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
                lv_unity_zhuanjia.hideHeaderView();
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
                lv_unity_zhuanjia.hideFooterView();
            }
        }.execute();
    }

    private void setAddMoreData() {
        lastIndex = endNo;
        sendAddHomeLvRequest();
    }

    private List<ZhuanJiaBean.ExpertsBean> infos = new ArrayList<>();

    private void sendAddHomeLvRequest() {
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.UNITY_URL, RequestMethod.GET);
        request.add("apptoken", apptoken);
        request.add("op","experts");
        request.add("lastIndex",lastIndex+"");
        requestQueue.add(GET_MORE_DATA, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                Gson gson = new Gson();
                ZhuanJiaBean zhuanJiaBean = gson.fromJson(info, ZhuanJiaBean.class);
                MyLog.testLog("下拉数据:"+info);
                if (lastIndex!=0) {
                    endNo = zhuanJiaBean.getLastIndex();
                    lastIndex = endNo;
                    experts = zhuanJiaBean.getExperts();

                    infos.addAll(experts);
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
