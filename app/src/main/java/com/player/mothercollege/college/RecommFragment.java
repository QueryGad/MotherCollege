package com.player.mothercollege.college;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.player.mothercollege.R;
import com.player.mothercollege.adapter.RecommAdapter;
import com.player.mothercollege.bean.RecommBean;
import com.player.mothercollege.college.details.BzzbDeatilsActivity;
import com.player.mothercollege.utils.ConfigUtils;
import com.player.mothercollege.utils.MyLog;
import com.player.mothercollege.utils.PrefUtils;
import com.player.mothercollege.view.GlideImageLoader;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/18.
 */
public class RecommFragment extends Fragment {

    private static final int GET_RECOMM_DATA = 001;
    private View view;
    private ListView lv_recomm;
    private RequestQueue requestQueue;
    private List<RecommBean.BanerBean> banerBean = new ArrayList<>();
    private List<Integer> rtypes = new ArrayList<>();
    private List<String> sidBanner = new ArrayList<>();
    private OnBannerClickListener RecommBannerListener = new OnBannerClickListener() {
        @Override
        public void OnBannerClick(int position) {
            int rtype = rtypes.get(position-1);
            MyLog.testLog("rtype"+rtype);
            String sid = sidBanner.get(position-1);
            if (rtype==11||rtype==12){
                //跳转直播页面 跳转课程
                Intent intent1 = new Intent(getActivity(), BzzbDeatilsActivity.class);
                intent1.putExtra("sid",sid);
                startActivity(intent1);
            }else if (rtype==13||rtype==14){
                //读书  原创
                Intent intent2 = new Intent(getActivity(), BzzbDeatilsActivity.class);
                intent2.putExtra("sid",sid);
                startActivity(intent2);
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.frg_college_recomm,null);
        requestQueue = NoHttp.newRequestQueue();
        initView();
        initData();
        return view;
    }

    private void initView() {
        lv_recomm = (ListView) view.findViewById(R.id.lv_recomm);

    }

    private void initData() {
        netWork();
    }

    private void netWork() {
        String apptoken = PrefUtils.getString(getActivity(), "apptoken", "");
        String uid = PrefUtils.getString(getActivity(), "uid", "null");
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.COLLEGE_URL, RequestMethod.GET);
        request.add("apptoken",apptoken);
        request.add("uid",uid);
        request.add("op","tj");
        requestQueue.add(GET_RECOMM_DATA, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                Log.e("推荐页面:",info);
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
        RecommBean recommBean = gson.fromJson(info, RecommBean.class);
        //轮播图
        banerBean = recommBean.getBaner();
        initBaner();
        List<RecommBean.ListBean> lists = recommBean.getList();
        RecommAdapter adapter = new RecommAdapter(getActivity(),lists);
        lv_recomm.setAdapter(adapter);
    }

    private List<String> banerList = new ArrayList<>();

    private void initBaner() {
        banerList.clear();
        rtypes.clear();
        sidBanner.clear();
        for (int i=0;i<banerBean.size();i++){
            String img = banerBean.get(i).getImg();
            banerList.add(img);
            String sid = banerBean.get(i).getSid();
            sidBanner.add(sid);
            int rtype = banerBean.get(i).getRtype();
            rtypes.add(rtype);
        }
        View banerView = View.inflate(getActivity(),R.layout.head_college_recomm,null);
        Banner banner = (Banner) banerView.findViewById(R.id.banner);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(banerList);

        banner.setIndicatorGravity(BannerConfig.CENTER);

        banner.setOnBannerClickListener(RecommBannerListener);

        //banner设置方法全部调用完毕时最后调用
        banner.start();
        lv_recomm.addHeaderView(banerView);
    }


}
