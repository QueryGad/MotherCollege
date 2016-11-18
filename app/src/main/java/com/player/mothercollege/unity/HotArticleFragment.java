package com.player.mothercollege.unity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.player.mothercollege.R;
import com.player.mothercollege.adapter.HotArticleAdapter;
import com.player.mothercollege.bean.HotArticleBean;
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
 * 热帖页面
 */
public class HotArticleFragment extends Fragment{

    private static final int GET_HOTARTICLE_DATA = 001;
    private View view;
    private ListView lv_hotarticle;
    private RequestQueue requestQueue;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.frg_unity_hotarticle,null);
        requestQueue = NoHttp.newRequestQueue();
        initView();
        initData();
        return view;
    }

    private void initView() {
        lv_hotarticle = (ListView) view.findViewById(R.id.lv_hotarticle);

    }

    private void initData() {
        netWork();
    }

    private void netWork() {
        String apptoken = PrefUtils.getString(getActivity(), "apptoken", "");
        String uid = PrefUtils.getString(getActivity(), "uid", "null");
        MyLog.testLog("uid:"+uid);
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.UNITY_URL, RequestMethod.GET);
        request.add("apptoken",apptoken);
        request.add("op","retie");
        request.add("uid",uid);
        request.add("lastIndex","0");
        requestQueue.add(GET_HOTARTICLE_DATA, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                MyLog.testLog("热帖:"+info);
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
        HotArticleBean hotArticleBean = gson.fromJson(info, HotArticleBean.class);
        int lastIndex = hotArticleBean.getLastIndex();//目标索引
        List<HotArticleBean.TrendsBean> trendsList = hotArticleBean.getTrends();
        HotArticleAdapter adapter = new HotArticleAdapter(getActivity(),trendsList);
        lv_hotarticle.setAdapter(adapter);

    }


}
