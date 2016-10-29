package com.player.mothercollege.college;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.player.mothercollege.R;
import com.player.mothercollege.adapter.RecommAdapter;
import com.player.mothercollege.bean.RecommBean;
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
 * 推荐页面
 */
public class RecommFragment extends Fragment{

    private static final int GET_RECOMM_DATA = 001;
    private View view;
    private View viewhead;
    private ListView lv_recomm;
    private RequestQueue requestQueue;
    private List<RecommBean.BanerBean> banerList = new ArrayList<>();
    private ViewPager mViewPager;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.frg_college_recomm,null);
        viewhead = View.inflate(getActivity(), R.layout.pager_recomm_head,null);
        requestQueue = NoHttp.newRequestQueue();
        initView();
        initData();
        return view;
    }

    private void initView() {
        lv_recomm = (ListView) view.findViewById(R.id.lv_recomm);
        mViewPager = (ViewPager) viewhead.findViewById(R.id.vp_recomm);

    }

    private void initData() {
        netWork();
    }

    private void netWork() {
        final String apptoken = PrefUtils.getString(getActivity(), "apptoken", "");
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.COLLEGE_URL, RequestMethod.GET);
        request.add("op","tj");
        request.add("apptoken",apptoken);
        request.add("uid","null");
        requestQueue.add(GET_RECOMM_DATA, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                MyLog.testLog("推荐页面:"+info);
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
        RecommBean recommBean = gson.fromJson(info, RecommBean.class);
        banerList = recommBean.getBaner();
        List<RecommBean.ListBean> list = recommBean.getList();
        RecommAdapter adapter = new RecommAdapter(getActivity(),list);
        lv_recomm.setAdapter(adapter);
        //TODO
//        TopRecommAdapter topAdapter = new TopRecommAdapter();
//        if (banerList!=null){
//           mViewPager.setAdapter(new TopRecommAdapter());
//        }
    }

    //顶部轮播图
    class TopRecommAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return banerList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView view = new ImageView(getActivity());
            view.setImageResource(R.mipmap.icon_jiazai);
            container.addView(view);
            return view;
        }
    }
}
