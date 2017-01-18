package com.player.mothercollege.find;

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
import com.player.mothercollege.adapter.JieLiAdapter;
import com.player.mothercollege.bean.JieLiBean;
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
 * 爱心接力
 */
public class JieLiFragment extends Fragment implements MyUpDownListView.OnRefreshListener{

    private static final int GET_JIELI_DATA = 001;
    private static final int GET_MORE_DATA = 002;
    private View view;
    private RequestQueue requestQueue;
    private MyUpDownListView lv_find_jieli;
    private ImageView iv_refresh;
    private Button btn_refrsh;
    private String apptoken;
    private String uid;
    private LinearLayout ll_jieli_no;

    int lastIndex=0;
    boolean isRefresh = true;
    private int endNo;
    private JieLiAdapter adapter;
    private List<JieLiBean.UsersBean> usersList;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.frg_find_jieli,null);
        requestQueue = NoHttp.newRequestQueue();
        initView();
        initData();
        if(isRefresh){
            initData();
        }
        return view;
    }

    private void initView() {
        lv_find_jieli = (MyUpDownListView) view.findViewById(R.id.lv_find_jieli);
        iv_refresh = (ImageView) view.findViewById(R.id.iv_refresh);
        btn_refrsh = (Button) view.findViewById(R.id.btn_refrsh);
        ll_jieli_no = (LinearLayout) view.findViewById(R.id.ll_jieli_no);

        lv_find_jieli.setOnRefreshListener(this);
    }

    private void initData() {
        netWork();
    }

    private void netWork() {

        apptoken = PrefUtils.getString(getActivity(), "apptoken", "");
        uid = PrefUtils.getString(getActivity(), "uid", "");
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.FIND_URL, RequestMethod.GET);
        request.add("apptoken", apptoken);
        request.add("uid", uid);
        request.add("op","myft_jl");
        request.add("lastIndex",lastIndex+"");
        requestQueue.add(GET_JIELI_DATA, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                iv_refresh.setVisibility(View.GONE);
                btn_refrsh.setVisibility(View.GONE);
                String info = response.get();
                MyLog.testLog("爱心接力:"+info);
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
        JieLiBean jieLiBean = gson.fromJson(info, JieLiBean.class);

        if (jieLiBean!=null){
            endNo = jieLiBean.getLastIndex();//目标索引
            infos = jieLiBean.getUsers();
            if (infos.size()==0){
                ll_jieli_no.setVisibility(View.VISIBLE);
            }else {
                adapter = new JieLiAdapter(infos,getActivity());
                lv_find_jieli.setAdapter(adapter);
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
                lv_find_jieli.hideHeaderView();
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
                lv_find_jieli.hideFooterView();
            }
        }.execute();
    }

    private void setAddMoreData() {
        lastIndex = endNo;
        sendAddHomeLvRequest();
    }

    private List<JieLiBean.UsersBean> infos = new ArrayList<>();
    private void sendAddHomeLvRequest() {
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.FIND_URL, RequestMethod.GET);
        request.add("apptoken", apptoken);
        request.add("uid", uid);
        request.add("op","myft_jl");
        request.add("lastIndex",lastIndex+"");
        requestQueue.add(GET_MORE_DATA, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                Gson gson = new Gson();
                JieLiBean jieLiBean = gson.fromJson(info, JieLiBean.class);
                if (lastIndex!=0) {
                    endNo = jieLiBean.getLastIndex();
                    lastIndex = endNo;
                    usersList = jieLiBean.getUsers();
                    infos.addAll(usersList);
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
