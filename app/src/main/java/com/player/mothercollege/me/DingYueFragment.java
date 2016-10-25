package com.player.mothercollege.me;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.player.mothercollege.R;
import com.player.mothercollege.adapter.DingYueAdapter;
import com.player.mothercollege.bean.DingYueBean;
import com.player.mothercollege.utils.ConfigUtils;
import com.player.mothercollege.utils.MyLog;
import com.player.mothercollege.utils.PrefUtils;
import com.player.mothercollege.view.DividerItemDecoration;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

import java.util.List;

/**
 * Created by Administrator on 2016/10/25.
 * 我的订阅
 */
public class DingYueFragment extends Fragment{

    private static final int GET_DINGYUE_DATA = 001;
    private View view;
    private RecyclerView rv_dingyue;
    private ImageView iv_refresh;
    private Button btn_refrsh;
    private RequestQueue requestQueue;
    private DingYueAdapter.OnItemClickListener DingYueItemListener = new DingYueAdapter.OnItemClickListener() {
        @Override
        public void onClick(View v, int position, DingYueBean.MyPayClassBean data) {
            Toast.makeText(getActivity(),position+"",Toast.LENGTH_SHORT).show();
        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.frg_me_dingyue,null);
        requestQueue = NoHttp.newRequestQueue();
        initView();
        initData();
        return view;
    }

    private void initView() {
        rv_dingyue = (RecyclerView) view.findViewById(R.id.rv_dingyue);
        iv_refresh = (ImageView) view.findViewById(R.id.iv_refresh);
        btn_refrsh = (Button) view.findViewById(R.id.btn_refrsh);
    }

    private void initData() {
        netWork();
    }

    private void netWork() {
        String apptoken = PrefUtils.getString(getActivity(), "apptoken", "");
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.ME_URL, RequestMethod.GET);
        request.add("apptoken",apptoken);
        request.add("uid","null");
        request.add("op","mysubscriber");
        request.add("lastindex","0");
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
        int currentPageSize = dingYueBean.getCurrentPageSize();//总页数
        int lastIndex = dingYueBean.getLastIndex();//最后一页
        List<DingYueBean.MyPayClassBean> myPayClassList = dingYueBean.getMyPayClass();
        DingYueAdapter adapter = new DingYueAdapter(getActivity(),myPayClassList);
        rv_dingyue.setAdapter(adapter);
        rv_dingyue.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_dingyue.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL_LIST));
        adapter.setOnItemClickListener(DingYueItemListener);
    }
}
