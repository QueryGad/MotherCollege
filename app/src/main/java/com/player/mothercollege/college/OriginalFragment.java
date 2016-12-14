package com.player.mothercollege.college;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
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

import java.util.ArrayList;
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

    private MaterialRefreshLayout mRefreshLayout;
    private List<OriginalBean.BooksBean> booksList = new ArrayList<>();
    private boolean isLoadMore = true;

    private OriginalAdapter.OnItemClickListener OriginalItemListener = new OriginalAdapter.OnItemClickListener() {
        @Override
        public void onClick(View v, int position, OriginalBean.BooksBean data) {

        }
    };
    private OriginalAdapter adapter;


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
        mRefreshLayout = (MaterialRefreshLayout) view.findViewById(R.id.mRefreshLayout);
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
        booksList = originalBean.getBooks();
        adapter = new OriginalAdapter(booksList,getActivity());
        rv_original.setAdapter(adapter);
        //下面可以自己设置默认动画
        rv_original.setItemAnimator(new DefaultItemAnimator());
        rv_original.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_original.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL_LIST));
        adapter.setOnItemClickListener(OriginalItemListener);
        /**
         * 设置是否上拉加载更多，默认是false，要手动改为true，要不然不会出现上拉加载
         */
        mRefreshLayout.setLoadMore(isLoadMore);
        mRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
               //todo
                Toast.makeText(getActivity(), "已经没有更多数据了", Toast.LENGTH_SHORT).show();
                /**
                 * 刷新完成后调用此方法，要不然刷新效果不消失
                 */
                mRefreshLayout.finishRefresh();
            }
            /**
             * 上拉加载更多的方法，在这里我只是简单的模拟了加载四条数据
             * 真正用的时候，就会去定义方法，获取数据，一般都是分页，在数据端获取的时候
             * 把页数去增加一，然后在去服务端去获取数据
             * @param materialRefreshLayout
             */
            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
//                super.onRefreshLoadMore(materialRefreshLayout);
               //todo
                Toast.makeText(getActivity(), "我拉到最底端了", Toast.LENGTH_SHORT).show();
                /**
                 * 刷新完成后调用此方法，要不然刷新效果不消失
                 */
                mRefreshLayout.finishRefreshLoadMore();

            }
        });



    }

    private void initData() {

    }
}
