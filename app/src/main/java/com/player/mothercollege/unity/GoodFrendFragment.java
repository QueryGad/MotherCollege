package com.player.mothercollege.unity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.player.mothercollege.R;
import com.player.mothercollege.adapter.FootGoodFrendAdapter;
import com.player.mothercollege.adapter.FrendQunAdatpter;
import com.player.mothercollege.bean.FrendQunRecommBean;
import com.player.mothercollege.unity.details.FrendMessageActivity;
import com.player.mothercollege.unity.details.ListAddressActivity;
import com.player.mothercollege.unity.details.QunChatActivity;
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
 * 好友页面
 */
public class GoodFrendFragment extends Fragment implements View.OnClickListener {

    private static final int GET_RECOMM_DATA = 001;
    private View view;
    private RequestQueue requestQueue;
    private ListView lv_goodfrend;
    private List<FrendQunRecommBean.RecommendGroupBean> recommendGroupList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.frg_unity_goodfrend,null);
        requestQueue = NoHttp.newRequestQueue();
        initView();
        initData();
        return view;
    }

    private void initView() {

        lv_goodfrend = (ListView) view.findViewById(R.id.lv_goodfrend);
        initHeader();
        initFoot();

    }

    private void initFoot() {
        View footView = View.inflate(getContext(),R.layout.foot_unity_frend,null);
        ListView lv_foot_frend = (ListView) footView.findViewById(R.id.lv_foot_frend);
        FootGoodFrendAdapter adapter = new FootGoodFrendAdapter(getActivity(),recommendGroupList);
        lv_foot_frend.setAdapter(adapter);
        lv_goodfrend.addFooterView(footView);
    }

    private void initData() {
        netWork();
    }

    private void netWork() {
        String apptoken = PrefUtils.getString(getActivity(), "apptoken", "");
        String uid = PrefUtils.getString(getActivity(), "uid", "");
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.UNITY_URL, RequestMethod.GET);
        request.add("apptoken",apptoken);
        request.add("op","FriendPage");
        request.add("uid",uid);
        requestQueue.add(GET_RECOMM_DATA, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                MyLog.testLog("好友群推荐页面:"+info);
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

    private void parseJson(String info) {
        Gson gson = new Gson();
        FrendQunRecommBean frendQunRecommBean = gson.fromJson(info, FrendQunRecommBean.class);
        List<FrendQunRecommBean.RecommendUsersBean> recommendUsersList = frendQunRecommBean.getRecommendUsers();
        recommendGroupList = frendQunRecommBean.getRecommendGroup();
        FrendQunAdatpter adatpter = new FrendQunAdatpter(getContext(),recommendUsersList);
        lv_goodfrend.setAdapter(adatpter);
    }

    private void initHeader(){
        View headerView = View.inflate(getContext(),R.layout.head_unity_frend,null);
        Button btn_unity_message = (Button) headerView.findViewById(R.id.btn_unity_message);
        Button btn_unity_addresslist = (Button) headerView.findViewById(R.id.btn_unity_addresslist);
        Button btn_unity_qunchat = (Button) headerView.findViewById(R.id.btn_unity_qunchat);

        btn_unity_message.setOnClickListener(this);
        btn_unity_addresslist.setOnClickListener(this);
        btn_unity_qunchat.setOnClickListener(this);
        lv_goodfrend.addHeaderView(headerView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_unity_message:
                Intent intent1 = new Intent(getActivity(), FrendMessageActivity.class);
                startActivity(intent1);
                break;
            case R.id.btn_unity_addresslist:
                Intent intent2 = new Intent(getActivity(), ListAddressActivity.class);
                startActivity(intent2);
                break;
            case R.id.btn_unity_qunchat:
                Intent intent3 = new Intent(getActivity(), QunChatActivity.class);
                startActivity(intent3);
                break;
        }
    }
}
