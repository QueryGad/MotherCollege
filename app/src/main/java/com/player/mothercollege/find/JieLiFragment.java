package com.player.mothercollege.find;

import android.content.Intent;
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

import com.google.gson.Gson;
import com.player.mothercollege.R;
import com.player.mothercollege.adapter.JieLiAdapter;
import com.player.mothercollege.bean.JieLiBean;
import com.player.mothercollege.me.HeadIconActivity;
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
 * 爱心接力
 */
public class JieLiFragment extends Fragment{

    private static final int GET_JIELI_DATA = 001;
    private View view;
    private RequestQueue requestQueue;
    private RecyclerView rv_find_jieli;
    private ImageView iv_refresh;
    private Button btn_refrsh;
    private JieLiAdapter.OnItemClickListener JieLiItemListener = new JieLiAdapter.OnItemClickListener() {
        @Override
        public void onClick(View v, int position, JieLiBean.UsersBean data) {
            //点击头像进入他人主页
            Intent intent = new Intent(getActivity(), HeadIconActivity.class);
            startActivity(intent);
        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.frg_find_jieli,null);
        requestQueue = NoHttp.newRequestQueue();
        initView();
        initData();
        return view;
    }

    private void initView() {
        rv_find_jieli = (RecyclerView) view.findViewById(R.id.rv_find_jieli);
        iv_refresh = (ImageView) view.findViewById(R.id.iv_refresh);
        btn_refrsh = (Button) view.findViewById(R.id.btn_refrsh);

    }

    private void initData() {
        netWork();
    }

    private void netWork() {

        String apptoken = PrefUtils.getString(getActivity(), "apptoken", "");
        String uid = PrefUtils.getString(getActivity(), "uid", "null");
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.FIND_URL, RequestMethod.GET);
        request.add("apptoken",apptoken);
        request.add("uid",uid);
        request.add("op","myft_jl");
        request.add("lastindex","0");
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
        JieLiBean jieLiBean = gson.fromJson(info, JieLiBean.class);
        int currentPageSize = jieLiBean.getCurrentPageSize();//总页数
        int lastIndex = jieLiBean.getLastIndex();//最后一页
        List<JieLiBean.UsersBean> usersList = jieLiBean.getUsers();
        JieLiAdapter adapter = new JieLiAdapter(usersList,getActivity());
        rv_find_jieli.setAdapter(adapter);
        rv_find_jieli.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_find_jieli.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL_LIST));
        adapter.setOnItemClickListener(JieLiItemListener);
    }
}
