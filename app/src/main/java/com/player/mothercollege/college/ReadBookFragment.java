package com.player.mothercollege.college;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.player.mothercollege.R;
import com.player.mothercollege.adapter.ReadBookAdapter;
import com.player.mothercollege.bean.ReadBookBean;
import com.player.mothercollege.utils.ConfigUtils;
import com.player.mothercollege.utils.MyLog;
import com.player.mothercollege.utils.PrefUtils;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

import java.util.List;

/**
 * Created by Administrator on 2016/10/25.
 * 读书页面
 */
public class ReadBookFragment extends Fragment{

    private static final int GET_READBOOK_DATA = 001;
    private View view;
    private ListView lv_readbook;
    private RequestQueue requestQueue;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.frg_college_readbook,null);
        requestQueue = NoHttp.newRequestQueue();
        initView();
        initData();
        return view;
    }

    private void initView() {
        lv_readbook = (ListView) view.findViewById(R.id.lv_readbook);

    }

    private void initData() {
        netWork();
    }

    private void netWork() {
        final String apptoken = PrefUtils.getString(getActivity(), "apptoken", "");
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.COLLEGE_URL, RequestMethod.GET);
        request.add("op","ds");
        request.add("lastIndex","0");
        request.add("apptoken",apptoken);
        requestQueue.add(GET_READBOOK_DATA, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                MyLog.testLog("读书页面"+info);
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
        ReadBookBean readBookBean = gson.fromJson(info, ReadBookBean.class);
        int endNo = readBookBean.getEndNo();
        List<ReadBookBean.BooksBean> booksList = readBookBean.getBooks();
        ReadBookAdapter adapter = new ReadBookAdapter(getActivity(),booksList);
        lv_readbook.setAdapter(adapter);
    }
}
