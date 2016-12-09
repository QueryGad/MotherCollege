package com.player.mothercollege.unity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.player.mothercollege.R;
import com.player.mothercollege.adapter.ActiveAdapter;
import com.player.mothercollege.bean.ActiveBean;
import com.player.mothercollege.bean.CityBean;
import com.player.mothercollege.utils.ConfigUtils;
import com.player.mothercollege.utils.PrefUtils;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

import java.util.List;

/**
 * Created by Administrator on 2016/11/16.
 */
public class ActivityFragment extends Fragment{

    private ListView lv_activty;
    private View view;
    private RequestQueue requestQueue;
    private Spinner spinner;
    private AdapterView.OnItemSelectedListener MyOnItemListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            parent.setVisibility(View.VISIBLE);
            String key = keyList[position];
            netWorkActivity(key);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    private String[] cityList;
    private String[] keyList;


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
        lv_activty = (ListView) view.findViewById(R.id.lv_activty);
        spinner = (Spinner) view.findViewById(R.id.spinner);
    }

    private void initData() {
        netWorkCity();
    }

    private void netWorkCity() {
        String apptoken = PrefUtils.getString(getActivity(), "apptoken", "");
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.UNITY_URL, RequestMethod.GET);
        request.add("apptoken",apptoken);
        request.add("op","cityItem");
        requestQueue.add(001, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                Log.e("========",info);
                parseJsonCity(info);
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    private void parseJsonCity(String info){
        Gson gson = new Gson();
        CityBean cityBean = gson.fromJson(info, CityBean.class);
        List<CityBean.CitysBean> citysList = cityBean.getCitys();
        cityList = new String[citysList.size()+1];
        keyList = new String[citysList.size()+1];
        cityList[0] = "全部";
        keyList[0] = "";
        for (int i=0;i<citysList.size();i++){
            CityBean.CitysBean citysBean = citysList.get(i);
            String city = citysBean.getCity();
            cityList[i+1]=city;
            String key = citysBean.getKey();
            keyList[i+1]=key;
        }
        ArrayAdapter<String> arrAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, cityList);
        spinner.setAdapter(arrAdapter);
        spinner.setOnItemSelectedListener(MyOnItemListener);
    }



    private void netWorkActivity(String key) {
        String apptoken = PrefUtils.getString(getActivity(), "apptoken", "");
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.UNITY_URL, RequestMethod.GET);
        request.add("op","active");
        request.add("apptoken",apptoken);
        request.add("lastIndex","0");
        request.add("city",key);
        requestQueue.add(002, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                Log.e("活动",info);
                parseJsonActive(info);
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    private void parseJsonActive(String info){
        Gson gson = new Gson();
        ActiveBean activeBean = gson.fromJson(info, ActiveBean.class);
        int lastIndex = activeBean.getLastIndex(); //下拉刷新控件
        List<ActiveBean.ActivesBean> activesLists = activeBean.getActives();
        ActiveAdapter adapter = new ActiveAdapter(getActivity(),activesLists);
        lv_activty.setAdapter(adapter);
    }

}
