package com.player.mothercollege.college;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.gson.Gson;
import com.player.mothercollege.R;
import com.player.mothercollege.bean.ClassBean;
import com.player.mothercollege.college.details.FuMuClassFragment;
import com.player.mothercollege.college.details.FuQiClassFragment;
import com.player.mothercollege.college.details.XinLingClassFragment;
import com.player.mothercollege.utils.ConfigUtils;
import com.player.mothercollege.utils.DensityUtils;
import com.player.mothercollege.utils.MyLog;
import com.player.mothercollege.utils.PrefUtils;
import com.player.mothercollege.utils.ScreenUtils;
import com.squareup.picasso.Picasso;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

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
    private ViewPager vp_class_head;
    private LinearLayout ll_class_dot;
    private List<ImageView> ImageList = new ArrayList<>();
    private List<ImageView> dots = new ArrayList<>();
    private int lastPosition;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case DELYER_CLASS_BANDER:
                    vp_class_head.setCurrentItem(vp_class_head.getCurrentItem()+1);
                    handler.sendEmptyMessageDelayed(DELYER_CLASS_BANDER, 2000);
                    break;
            }
        }
    };
    //课堂
    private FrameLayout fl_class;
    private RadioGroup rg_college_class;
    private RadioButton rb_fumu_class,rb_fuqi_class,rb_xinling_class;
    private List<Fragment> lists = new ArrayList<>();
    private FuMuClassFragment fumuFragment;
    private FuQiClassFragment fuqiFragment;
    private XinLingClassFragment xinlingFragment;
    private RequestQueue requestQueue;
    private List<ClassBean.BanerBean> banerList = new ArrayList<>();
    private ViewPager.OnPageChangeListener OnClassBanderListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
             //切换点
            position = position % ImageList.size();
            ll_class_dot.getChildAt(lastPosition).setBackgroundResource(R.mipmap.icon_recommend_banner_nomal);
            ll_class_dot.getChildAt(position).setBackgroundResource(R.mipmap.icon_recommend_banner_sel);
            lastPosition = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
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
        vp_class_head = (ViewPager) view.findViewById(R.id.vp_class_head);
        ll_class_dot = (LinearLayout) view.findViewById(R.id.ll_class_dot);
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
        netWork();
        //设置OnCheck监听事件
        rg_college_class.setOnCheckedChangeListener(ClassCheckListener);
    }

    private void netWork() {
        String apptoken = PrefUtils.getString(getActivity(),"apptoken","");
        String uid = PrefUtils.getString(getActivity(), "uid", "null");
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
        ClassBean classBean = gson.fromJson(info, ClassBean.class);
        banerList = classBean.getBaner();
        //添加轮播图数据
        initBaner();
    }

    private void initBaner() {
        ImageList.clear();

        if (banerList.size() == 2) {
            for (int i = 0; i < banerList.size() + 2; i++) {
                ImageView iv = new ImageView(getActivity());
                Picasso.with(getActivity()).load(banerList.get((i >= 2) ? i - 2 : i).getImg())
                        .resize(ScreenUtils.getWidth(getActivity()), DensityUtils.dip2px(getActivity(), 96))
                        .centerCrop().into(iv);
                ImageList.add(iv);
            }
        } else {

            for (int i = 0; i < banerList.size(); i++) {
                ImageView iv = new ImageView(getActivity());
                Picasso.with(getActivity()).load(banerList.get(i).getImg())
                        .resize(ScreenUtils.getWidth(getActivity()), DensityUtils.dip2px(getActivity(), 96))
                        .centerCrop().into(iv);
                ImageList.add(iv);
            }
        }
        //添加点
        //添加下方点
        addPoints();
        //设置界面改变监听
        vp_class_head.setOnPageChangeListener(OnClassBanderListener);
        ClassBanderAdapter adapter = new ClassBanderAdapter();
        vp_class_head.setAdapter(adapter);
        handler.removeCallbacksAndMessages(null);
        handler.sendEmptyMessageDelayed(DELYER_CLASS_BANDER,2000);
    }

    private void addPoints() {
        ll_class_dot.removeAllViews();
        dots.clear();
        for (int i=0;i<banerList.size();i++){
            //创建点，添加进线性布局
            ImageView iv = new ImageView(getActivity());
//            iv.setImageResource(R.mipmap.icon_recommend_banner_nomal);
            //第0个不可用（红色）
            if (i==0){
                iv.setBackgroundResource(R.mipmap.icon_recommend_banner_sel);
            }else {
                iv.setBackgroundResource(R.mipmap.icon_recommend_banner_nomal);
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-2,-2);
            params.leftMargin = 20;
            ll_class_dot.addView(iv,params);
            dots.add(iv);
        }
    }

    private class ClassBanderAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            position = position % ImageList.size();
            View view = ImageList.get(position);
            if (((ViewGroup)view.getParent())!=null){
                ((ViewGroup)view.getParent()).removeAllViews();}
            container.addView(view);
            return view;
        }
    }
}
