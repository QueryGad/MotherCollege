package com.player.mothercollege.unity.details;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.player.mothercollege.R;
import com.player.mothercollege.adapter.FastInquiryAdapter;
import com.player.mothercollege.bean.FastBean;
import com.player.mothercollege.login.LoginActivity;
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
 * 快速咨询页面
 */
public class FastFragment extends Fragment implements View.OnClickListener {

    private static final int GET_FAST_DATA = 001;
    private View view;
    private ListView lv_unity_fast;
    private ImageView iv_fast_public;
    private RequestQueue requestQueue;
    private String apptoken;
    private String uid;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = View.inflate(getActivity(), R.layout.frg_zixun_fast,null);
        requestQueue = NoHttp.newRequestQueue();
        initView();
        initData();
        return view;
    }

    private void initView() {
        lv_unity_fast = (ListView) view.findViewById(R.id.lv_unity_fast);
        iv_fast_public = (ImageView) view.findViewById(R.id.iv_fast_public);

        iv_fast_public.setOnClickListener(this);
    }

    private void initData() {
       netWork();
    }

    private void netWork() {
        apptoken = PrefUtils.getString(getActivity(), "apptoken", "");
        uid = PrefUtils.getString(getActivity(), "uid", "");
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.UNITY_URL, RequestMethod.GET);
        request.add("apptoken", apptoken);
        request.add("op","quickqustion");
        request.add("lastIndex","0");
        requestQueue.add(GET_FAST_DATA, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                MyLog.testLog("快速咨询:"+info);
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
        FastBean fastBean = gson.fromJson(info, FastBean.class);
        int lastIndex = fastBean.getLastIndex();//数据索引
        List<FastBean.QustionsBean> qustionsList = fastBean.getQustions();
        FastInquiryAdapter adapter = new FastInquiryAdapter(getActivity(),qustionsList);
        lv_unity_fast.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_fast_public:
                //点击进入提问页面
                if (uid.equals("")){
                    //未登录  提示登录
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(getActivity(),RequestActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }
}
