package com.player.mothercollege.college;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.player.mothercollege.R;
import com.player.mothercollege.adapter.OriginalAdapter;
import com.player.mothercollege.bean.OriginalBean;
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
 * 原创页面
 */
public class OriginalFragment extends Fragment{

    private View view;
    private static final int GET_ORIGINAL_DATA = 001;
    private RecyclerView rv_original;
    private RequestQueue requestQueue;
    private OriginalAdapter.OnItemClickListener OriginalItemListener = new OriginalAdapter.OnItemClickListener() {
        @Override
        public void onClick(View v, int position, OriginalBean.BooksBean data) {

        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.frg_college_original,null);
        requestQueue = NoHttp.newRequestQueue();
        initView();
        initData();
        return view;
    }

    private void initView() {
        rv_original = (RecyclerView) view.findViewById(R.id.rv_original);

        initRecycler();
    }

    private void initRecycler() {
        netWork();
    }

    private void netWork() {
        String apptoken = PrefUtils.getString(getActivity(), "apptoken", "");
        String uid = PrefUtils.getString(getActivity(), "uid", "null");
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.COLLEGE_URL, RequestMethod.GET);
        request.add("op","yc");
        request.add("apptoken",apptoken);
        request.add("uid",uid);
        request.add("lastIndex","0");
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
        int lastIndex = originalBean.getLastIndex();
        List<OriginalBean.BooksBean> booksList = originalBean.getBooks();
        OriginalAdapter adapter = new OriginalAdapter(booksList,getActivity());
        rv_original.setAdapter(adapter);
        rv_original.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_original.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL_LIST));
        adapter.setOnItemClickListener(OriginalItemListener);
    }

    private void initData() {

    }
}
