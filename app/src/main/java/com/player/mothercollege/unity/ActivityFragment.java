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
import com.player.mothercollege.adapter.ActivityAdapter;
import com.player.mothercollege.bean.ActivityAddressBean;
import com.player.mothercollege.bean.ActivityBean;
import com.player.mothercollege.utils.ConfigUtils;
import com.player.mothercollege.utils.MyLog;
import com.player.mothercollege.utils.PrefUtils;
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
 * 活动页面
 */
public class ActivityFragment extends Fragment{

    private static final int GET_ACTIVITY_DATA = 001;
    private static final int GET_ACTIV_DATA = 002;
    private View view;
    private ListView lv_unity_activity;
    private RequestQueue requestQueue;
    private List<ActivityAddressBean.CitysBean> citysList = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.frg_unity_activity,null);
        requestQueue = NoHttp.newRequestQueue();
        initView();
        initData();
        return view;
    }

    private void initView() {
        lv_unity_activity = (ListView) view.findViewById(R.id.lv_unity_activity);
    }

    private void initData() {
        netWorkAddress();
        netWorkActivity();
    }

    private void netWorkActivity() {
        String apptoken = PrefUtils.getString(getActivity(), "apptoken", "");
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.UNITY_URL, RequestMethod.GET);
        request.add("op","active");
        request.add("lastIndex","0");
        request.add("city","");//空表示全部显示
        request.add("apptoken",apptoken);
        requestQueue.add(GET_ACTIV_DATA, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                MyLog.testLog("活动页面"+info);
                parseJsonActivity(info);
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    private void netWorkAddress() {
        String apptoken = PrefUtils.getString(getActivity(), "apptoken", "");
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.UNITY_URL, RequestMethod.GET);
        request.add("op","cityItem");
        request.add("apptoken",apptoken);
        requestQueue.add(GET_ACTIVITY_DATA, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                MyLog.testLog("活动地址:"+info);
                parseJsonAddress(info);
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    private void parseJsonAddress(String info){
        Gson gson = new Gson();
        ActivityAddressBean activityAddressBean = gson.fromJson(info, ActivityAddressBean.class);
        citysList = activityAddressBean.getCitys();
        for (int i=0;i<citysList.size();i++){
            String city = citysList.get(i).getCity();
            String key = citysList.get(i).getKey();
        }
    }

    private void parseJsonActivity(String info){
        Gson gson = new Gson();
        ActivityBean activityBean = gson.fromJson(info, ActivityBean.class);
        int lastIndex = activityBean.getLastIndex();//刷新下标
        List<ActivityBean.ActivesBean> activesList = activityBean.getActives();
        initActivityData(activesList);
    }

    private void initActivityData(List activesList) {
        ActivityAdapter adapter = new ActivityAdapter(getActivity(),activesList,citysList);
        lv_unity_activity.setAdapter(adapter);
    }


}
