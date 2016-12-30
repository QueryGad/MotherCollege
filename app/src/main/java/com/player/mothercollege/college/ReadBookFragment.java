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
//    private ListView lv_readbook;
    private ListView lv_readbook;
    int lastIndex=0;
    boolean isRefresh = true;

    private RequestQueue requestQueue;
    private int endNo;
    private List<ReadBookBean.BooksBean> booksList;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.frg_college_readbook,null);
        requestQueue = NoHttp.newRequestQueue();
        initView();
        initData();
        if(isRefresh){
            initData();
        }

        return view;
    }

    private void initView() {
//        lv_readbook = (ListView) view.findViewById(R.id.lv_readbook);
        lv_readbook = (ListView) view.findViewById(R.id.lv_readbook);
//        lv_readbook.setMode(PullToRefreshBase.Mode.BOTH);
//        ILoadingLayout sra = lv_readbook.getLoadingLayoutProxy(true,false);
//        sra.setPullLabel("下来刷新");
//        sra.setRefreshingLabel("正在刷新");
//        sra.setReleaseLabel("释放刷新");
//
//        ILoadingLayout endst = lv_readbook.getLoadingLayoutProxy(false,true);
//        endst.setPullLabel("查看更多");
//        endst.setRefreshingLabel("正在加载...");
//        endst.setReleaseLabel("释放加载更多");


//        lv_readbook.setOnRefreshListener(refreshListener);
    }

//    private PullToRefreshBase.OnRefreshListener2<ListView> refreshListener= new PullToRefreshBase.OnRefreshListener2<ListView>(){
//
//        @Override
//        public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
//            netWork();
//        }
//
//        @Override
//        public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
//            lastIndex=endNo;
//            isRefresh = false;
//            netWork();
////            BFE4D518702CFF259B4C360E530B3B05
//        }
//    };


    private void initData() {
        netWork();
    }

    private void netWork() {
        final String apptoken = PrefUtils.getString(getActivity(), "apptoken", "");
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.COLLEGE_URL, RequestMethod.GET);
        request.add("op","ds");
        request.add("lastIndex",lastIndex+"");//++
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
        endNo = readBookBean.getEndNo();
        booksList = readBookBean.getBooks();
        ReadBookAdapter adapter = new ReadBookAdapter(getActivity(),booksList);
        lv_readbook.setAdapter(adapter);
    }
//    public void loadFinished(){
//        lv_readbook.onRefreshComplete();
//    }
}
