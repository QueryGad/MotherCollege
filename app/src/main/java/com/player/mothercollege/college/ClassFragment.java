package com.player.mothercollege.college;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.player.mothercollege.R;
import com.player.mothercollege.bean.ClassBean;
import com.player.mothercollege.college.details.ClassDetailsActivity;
import com.player.mothercollege.college.details.FuMuClassFragment;
import com.player.mothercollege.college.details.FuQiClassFragment;
import com.player.mothercollege.college.details.XinLingClassFragment;
import com.player.mothercollege.utils.CacheUtils;
import com.player.mothercollege.utils.ConfigUtils;
import com.player.mothercollege.utils.MyLog;
import com.player.mothercollege.utils.PrefUtils;
import com.player.mothercollege.view.GlideImageLoader;
import com.yolanda.nohttp.NoHttp;
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
 * Created by Administrator on 2016/10/25.
 * 课堂页面
 */
public class ClassFragment extends Fragment{

    private View view;
    private static final int GET_CLASS_DATA = 001;
    private static final int DELYER_CLASS_BANDER = 002 ;
    //轮播图
    private Banner banner;
    //课堂
    private FrameLayout fl_class;
    private RadioGroup rg_college_class;
    private RadioButton rb_fumu_class,rb_fuqi_class,rb_xinling_class;
    private List<Fragment> lists = new ArrayList<>();
    private FuMuClassFragment fumuFragment;
    private FuQiClassFragment fuqiFragment;
    private XinLingClassFragment xinlingFragment;
    private RequestQueue requestQueue;
    private List<ClassBean.BanerBean> banerBean = new ArrayList<>();

    private RadioGroup.OnCheckedChangeListener ClassCheckListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId){
                case R.id.rb_fumu_class:
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_class,fumuFragment).commit();
                    break;
                case R.id.rb_fuqi_class:
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_class,fuqiFragment).commit();
                    break;
                case R.id.rb_xinling_class:
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_class,xinlingFragment).commit();
                    break;
            }
        }
    };
    private OnBannerClickListener ClassBannerListener = new OnBannerClickListener() {
        @Override
        public void OnBannerClick(int position) {
            String sid = banerBean.get(position-1).getSid();
            String title = banerBean.get(position - 1).getTitle();
            String url = banerBean.get(position - 1).getVedioUrl();
            String img = banerBean.get(position - 1).getImg();
            Intent intent = new Intent(getActivity(), ClassDetailsActivity.class);
            intent.putExtra("sid",sid);
            intent.putExtra("url",url);
            intent.putExtra("img",img);
            intent.putExtra("title",title);
            startActivity(intent);
        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.frg_college_class,null);
        requestQueue = NoHttp.newRequestQueue();
        initView();
        initData();
        return view;
    }

    private void initView() {
        //轮播图
        banner = (Banner) view.findViewById(R.id.banner_class);

        //课堂
        rg_college_class = (RadioGroup) view.findViewById(R.id.rg_college_class);
        rb_fumu_class = (RadioButton) view.findViewById(R.id.rb_fumu_class);
        rb_fuqi_class = (RadioButton) view.findViewById(R.id.rb_fuqi_class);
        rb_xinling_class = (RadioButton) view.findViewById(R.id.rb_xinling_class);
        fl_class = (FrameLayout) view.findViewById(R.id.fl_class);

        fumuFragment = new FuMuClassFragment();
        fuqiFragment = new FuQiClassFragment();
        xinlingFragment = new XinLingClassFragment();
        lists.clear();
        lists.add(fumuFragment);
        lists.add(fuqiFragment);
        lists.add(xinlingFragment);
        //设置默认
        rb_fumu_class.setChecked(true);
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fl_class,fumuFragment).commit();
    }

    private void initData() {
        String cacheJson = CacheUtils.getCache(getActivity(), ConfigUtils.COLLEGE_URL + "college_class");
        if (!TextUtils.isEmpty(cacheJson)){
            parseJson(cacheJson);
        }
        netWork();
        //设置OnCheck监听事件
        rg_college_class.setOnCheckedChangeListener(ClassCheckListener);
    }

    private void netWork() {
        String apptoken = PrefUtils.getString(getActivity(),"apptoken","");
        String uid = PrefUtils.getString(getActivity(), "uid", "");
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.COLLEGE_URL);
        request.add("op","class");
        request.add("apptoken",apptoken);
        request.add("udi",uid);
        requestQueue.add(GET_CLASS_DATA, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                MyLog.testLog("课堂数据"+info);
                if (info!=null){
                    parseJson(info);
                }
                CacheUtils.saveCache(getActivity(),ConfigUtils.COLLEGE_URL + "college_class",info);
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Toast.makeText(getActivity(),"网络已断开,请检查您的网络!",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    private void parseJson(String info){
        Gson gson = new Gson();
        ClassBean classBean = gson.fromJson(info, ClassBean.class);
        banerBean = classBean.getBaner();
        //添加轮播图数据
        initBaner();
    }

    private List<String> banerList = new ArrayList<>();

    private void initBaner() {
        banerList.clear();
        for (int i=0;i<banerBean.size();i++){
            String img = banerBean.get(i).getImg();
            banerList.add(img);
        }
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(banerList);

        banner.setIndicatorGravity(BannerConfig.CENTER);

        banner.setOnBannerClickListener(ClassBannerListener);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }
}
