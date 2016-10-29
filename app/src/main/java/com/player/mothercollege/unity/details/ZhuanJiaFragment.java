package com.player.mothercollege.unity.details;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.player.mothercollege.R;
import com.player.mothercollege.adapter.ZhuanJiaAdapter;
import com.player.mothercollege.bean.ZhuanJiaBean;
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
 * Created by Administrator on 2016/9/21.
 */
public class ZhuanJiaFragment extends Fragment {

    private static final int GET_ZHUANJIA_DATA = 001;
    private View view;
    private ListView lv_unity_zhuanjia;
    private RequestQueue requestQueue;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.frg_zixun_zhuanjia,null);
        requestQueue = NoHttp.newRequestQueue();
        initView();
        initData();
        return view;
    }

    private void initView() {
        lv_unity_zhuanjia = (ListView) view.findViewById(R.id.lv_unity_zhuanjia);

    }

    private void initData() {
        netWork();
    }

    private void netWork() {
        String apptoken = PrefUtils.getString(getActivity(), "apptoken", "");
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.UNITY_URL, RequestMethod.GET);
        request.add("apptoken",apptoken);
        request.add("op","experts");
        request.add("lastIndex","0");
        requestQueue.add(GET_ZHUANJIA_DATA, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                MyLog.testLog("专家咨询"+info);
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
        ZhuanJiaBean zhuanJiaBean = gson.fromJson(info, ZhuanJiaBean.class);
        int lastIndex = zhuanJiaBean.getLastIndex();
        List<ZhuanJiaBean.ExpertsBean> expertsList = zhuanJiaBean.getExperts();
        ZhuanJiaAdapter adapter = new ZhuanJiaAdapter(getActivity(),expertsList);
        lv_unity_zhuanjia.setAdapter(adapter);
    }
}
