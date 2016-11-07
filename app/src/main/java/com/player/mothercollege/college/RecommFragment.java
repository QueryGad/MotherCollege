package com.player.mothercollege.college;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.player.mothercollege.R;
import com.player.mothercollege.bean.RecommBean;
import com.player.mothercollege.utils.CacheUtils;
import com.player.mothercollege.utils.ConfigUtils;
import com.player.mothercollege.utils.DensityUtils;
import com.player.mothercollege.utils.PrefUtils;
import com.player.mothercollege.utils.ScreenUtils;
import com.squareup.picasso.Picasso;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/19.
 */
public class RecommFragment extends Fragment {

    private static final int DELAYED_TIME = 2000;
    private static final int GET_RECOMM_DATA = 001 ;

    //初始化数据
    private View view;
    private ViewPager vp_recomm_head;
    private LinearLayout ll_recomm_dot;
    private List<ImageView> dots = new ArrayList<>();
    private ImageView iv_recomm_ds_one,iv_recomm_ds_two,iv_recomm_zb_one,iv_recomm_zb_two,iv_recomm_kt_one,iv_recomm_kt_two,iv_recomm_yc_one,iv_recomm_yc_two;
    private TextView tv_recomm_ds_title_one,tv_recomm_ds_title_two,tv_recomm_zb_title_one,tv_recomm_zb_title_two,tv_recomm_kt_title_one,tv_recomm_kt_title_two,tv_recomm_yc_title_one,tv_recomm_yc_title_two,
                      tv_recomm_ds_xm_one,tv_recomm_ds_xm_two,tv_recomm_zb_xm_one,tv_recomm_zb_xm_two,tv_recomm_kt_xm_one,tv_recomm_kt_xm_two,tv_recomm_yc_xm_one,tv_recomm_yc_xm_two,
                      tv_recomm_ds_editor_one,tv_recomm_ds_editor_two,tv_recomm_zb_editor_one,tv_recomm_zb_editor_two,tv_recomm_kt_editor_one,tv_recomm_kt_editor_two,tv_recomm_yc_editor_one,tv_recomm_yc_editor_two,
                      tv_recomm_ds_viewCount_one,tv_recomm_ds_viewCount_two,tv_recomm_zb_viewCount_one,tv_recomm_zb_viewCount_two,tv_recomm_kt_viewCount_one,tv_recomm_kt_viewCount_two,tv_recomm_yc_viewCount_one,tv_recomm_yc_viewCount_two,
                      tv_ds_data_one,tv_ds_data_two,tv_zb_data_one,tv_zb_data_two,tv_kt_data_one,tv_kt_data_two,tv_yc_data_one,tv_yc_data_two;
    private RequestQueue requestQueue;
    private String apptoken;
    private List<RecommBean.BanerBean> banerBean;

    private List<View> lists = new ArrayList<>();
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DELAYED_TIME:
                    vp_recomm_head.setCurrentItem(vp_recomm_head.getCurrentItem()+1);
                    handler.sendEmptyMessageDelayed(DELAYED_TIME, 2000);
                    break;

                default:
                    break;
            }
        }
    };
    private List<RecommBean.ListBean> beanList = new ArrayList<>();
    private int lastPosition;
    private ViewPager.OnPageChangeListener OnRecommBinderListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            //切换点
//            for (int i =0;i<dots.size();i++){
//                //当前position的点变为红色
//                if (i==position){
//                    dots.get(i).setEnabled(false);
//                }else {
//                    dots.get(i).setEnabled(true);
//                }
//            }
            position = position % banerBean.size();
            ll_recomm_dot.getChildAt(lastPosition).setBackgroundResource(R.mipmap.icon_recommend_banner_nomal);
            ll_recomm_dot.getChildAt(position).setBackgroundResource(R.mipmap.icon_recommend_banner_sel);
            lastPosition = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.fragment_college_recomm,null);
        requestQueue = NoHttp.newRequestQueue();
        initView();
        initData();
        return view;
    }

    private void initView() {

        vp_recomm_head = (ViewPager) view.findViewById(R.id.vp_recomm_head);
        ll_recomm_dot = (LinearLayout) view.findViewById(R.id.ll_recomm_dot);

        iv_recomm_ds_one = (ImageView) view.findViewById(R.id.iv_recomm_ds_one);
        iv_recomm_ds_two = (ImageView) view.findViewById(R.id.iv_recomm_ds_two);
        iv_recomm_zb_one = (ImageView) view.findViewById(R.id.iv_recomm_zb_one);
        iv_recomm_zb_two = (ImageView) view.findViewById(R.id.iv_recomm_zb_two);
        iv_recomm_kt_one = (ImageView) view.findViewById(R.id.iv_recomm_kt_one);
        iv_recomm_kt_two = (ImageView) view.findViewById(R.id.iv_recomm_kt_two);
        iv_recomm_yc_one = (ImageView) view.findViewById(R.id.iv_recomm_yc_one);
        iv_recomm_yc_two = (ImageView) view.findViewById(R.id.iv_recomm_yc_two);

        tv_recomm_ds_title_one = (TextView) view.findViewById(R.id.tv_recomm_ds_title_one);
        tv_recomm_ds_title_two = (TextView) view.findViewById(R.id.tv_recomm_ds_title_two);
        tv_recomm_zb_title_one = (TextView) view.findViewById(R.id.tv_recomm_zb_title_one);
        tv_recomm_zb_title_two = (TextView) view.findViewById(R.id.tv_recomm_zb_title_two);
        tv_recomm_kt_title_one = (TextView) view.findViewById(R.id.tv_recomm_kt_title_one);
        tv_recomm_kt_title_two = (TextView) view.findViewById(R.id.tv_recomm_kt_title_two);
        tv_recomm_yc_title_one = (TextView) view.findViewById(R.id.tv_recomm_yc_title_one);
        tv_recomm_yc_title_two = (TextView) view.findViewById(R.id.tv_recomm_yc_title_two);

        tv_recomm_ds_xm_one = (TextView) view.findViewById(R.id.tv_recomm_ds_xm_one);
        tv_recomm_ds_xm_two = (TextView) view.findViewById(R.id.tv_recomm_ds_xm_two);
        tv_recomm_zb_xm_one = (TextView) view.findViewById(R.id.tv_recomm_zb_xm_one);
        tv_recomm_zb_xm_two = (TextView) view.findViewById(R.id.tv_recomm_zb_xm_two);
        tv_recomm_kt_xm_one = (TextView) view.findViewById(R.id.tv_recomm_kt_xm_one);
        tv_recomm_kt_xm_two = (TextView) view.findViewById(R.id.tv_recomm_kt_xm_two);
        tv_recomm_yc_xm_one = (TextView) view.findViewById(R.id.tv_recomm_yc_xm_one);
        tv_recomm_yc_xm_two = (TextView) view.findViewById(R.id.tv_recomm_yc_xm_two);

        tv_recomm_ds_editor_one = (TextView) view.findViewById(R.id.tv_recomm_ds_editor_one);
        tv_recomm_ds_editor_two = (TextView) view.findViewById(R.id.tv_recomm_ds_editor_two);
        tv_recomm_zb_editor_one = (TextView) view.findViewById(R.id.tv_recomm_zb_editor_one);
        tv_recomm_zb_editor_two = (TextView) view.findViewById(R.id.tv_recomm_zb_editor_two);
        tv_recomm_kt_editor_one = (TextView) view.findViewById(R.id.tv_recomm_kt_editor_one);
        tv_recomm_kt_editor_two = (TextView) view.findViewById(R.id.tv_recomm_kt_editor_two);
        tv_recomm_yc_editor_one = (TextView) view.findViewById(R.id.tv_recomm_yc_editor_one);
        tv_recomm_yc_editor_two = (TextView) view.findViewById(R.id.tv_recomm_yc_editor_two);

        tv_recomm_ds_viewCount_one = (TextView) view.findViewById(R.id.tv_recomm_ds_viewCount_one);
        tv_recomm_ds_viewCount_two = (TextView) view.findViewById(R.id.tv_recomm_ds_viewCount_two);
        tv_recomm_zb_viewCount_one = (TextView) view.findViewById(R.id.tv_recomm_zb_viewCount_one);
        tv_recomm_zb_viewCount_two = (TextView) view.findViewById(R.id.tv_recomm_zb_viewCount_two);
        tv_recomm_kt_viewCount_one = (TextView) view.findViewById(R.id.tv_recomm_kt_viewCount_one);
        tv_recomm_kt_viewCount_two = (TextView) view.findViewById(R.id.tv_recomm_kt_viewCount_two);
        tv_recomm_yc_viewCount_one = (TextView) view.findViewById(R.id.tv_recomm_yc_viewCount_one);
        tv_recomm_yc_viewCount_two = (TextView) view.findViewById(R.id.tv_recomm_yc_viewCount_two);

        tv_ds_data_one = (TextView) view.findViewById(R.id.tv_ds_data_one);
        tv_ds_data_two = (TextView) view.findViewById(R.id.tv_ds_data_two);
        tv_zb_data_one = (TextView) view.findViewById(R.id.tv_zb_data_one);
        tv_zb_data_two = (TextView) view.findViewById(R.id.tv_zb_data_two);
        tv_kt_data_one = (TextView) view.findViewById(R.id.tv_kt_data_one);
        tv_kt_data_two = (TextView) view.findViewById(R.id.tv_kt_data_two);
        tv_yc_data_one = (TextView) view.findViewById(R.id.tv_yc_data_one);
        tv_yc_data_two = (TextView) view.findViewById(R.id.tv_yc_data_two);
    }

    private void initData() {
        //获取缓存数据
        String cacheJson = CacheUtils.getCache(getActivity(), ConfigUtils.COLLEGE_URL + "tj");
        if (!TextUtils.isEmpty(cacheJson)){
            parseJson(cacheJson);
        }else {
            initHttp();
        }

    }

    private void initHttp() {
        //获取公钥
        apptoken = PrefUtils.getString(getActivity(),"apptoken","");
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
                Log.e("推荐页面数据:",info);//拿到数据
                CacheUtils.saveCache(getActivity(),ConfigUtils.COLLEGE_URL + "tj",info);
                parseJson(info);
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Toast.makeText(getActivity(),"网络错误,请检查您的网络",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    private void parseJson(String info){
        Gson gson = new Gson();
        RecommBean recommBean = gson.fromJson(info, RecommBean.class);
        //拿到轮播图数据
        banerBean = recommBean.getBaner();
        //拿到下方列表数据
        beanList = recommBean.getList();
        //将拿到的数据添加到本地
        initBindBaner();
        initListBean();
    }

    /**
     * 解析轮播图下方数据
     */
    private void initListBean() {
       //第一个  写的SB了
        Picasso.with(getActivity()).load(beanList.get(0).getImg())
                    .resize(231, 127)
                    .centerCrop().into(iv_recomm_ds_one);
        tv_recomm_ds_title_one.setText(beanList.get(0).getTitle());
        tv_recomm_ds_editor_one.setText(beanList.get(0).getEditor());
        tv_recomm_ds_editor_one.setTextColor(Color.RED);
        tv_recomm_ds_viewCount_one.setText(beanList.get(0).getViewCount());
        tv_ds_data_one.setText(beanList.get(0).getDate());
        //第二个
        Picasso.with(getActivity()).load(beanList.get(1).getImg())
                .resize(231, 127)
                .centerCrop().into(iv_recomm_ds_two);
        tv_recomm_ds_title_two.setText(beanList.get(1).getTitle());
        tv_recomm_ds_editor_two.setTextColor(Color.RED);
        tv_recomm_ds_editor_two.setText(beanList.get(1).getEditor());
        tv_recomm_ds_viewCount_two.setText(beanList.get(1).getViewCount());
        tv_ds_data_two.setText(beanList.get(1).getDate());
        //第三个
        Picasso.with(getActivity()).load(beanList.get(2).getImg())
                .resize(231, 127)
                .centerCrop().into(iv_recomm_zb_one);
        tv_recomm_zb_title_one.setText(beanList.get(2).getTitle());
        tv_recomm_zb_editor_one.setText(beanList.get(2).getEditor());
        tv_recomm_zb_editor_one.setTextColor(Color.RED);
        tv_recomm_zb_viewCount_one.setText(beanList.get(2).getViewCount());
        tv_zb_data_one.setText(beanList.get(2).getDate());
        //第四个
        Picasso.with(getActivity()).load(beanList.get(3).getImg())
                .resize(231, 127)
                .centerCrop().into(iv_recomm_zb_two);
        tv_recomm_zb_title_two.setText(beanList.get(3).getTitle());
        tv_recomm_zb_editor_two.setText(beanList.get(3).getEditor());
        tv_recomm_zb_editor_two.setTextColor(Color.RED);
        tv_recomm_zb_viewCount_two.setText(beanList.get(3).getViewCount());
        tv_zb_data_two.setText(beanList.get(3).getDate());
        //第五个
        Picasso.with(getActivity()).load(beanList.get(4).getImg())
                .resize(231, 127)
                .centerCrop().into(iv_recomm_kt_one);
        tv_recomm_kt_title_one.setText(beanList.get(4).getTitle());
        tv_recomm_kt_editor_one.setText(beanList.get(4).getEditor());
        tv_recomm_kt_editor_one.setTextColor(Color.RED);
        tv_recomm_kt_viewCount_one.setText(beanList.get(4).getViewCount());
        tv_kt_data_one.setText(beanList.get(4).getDate());
        //第六个
        Picasso.with(getActivity()).load(beanList.get(5).getImg())
                .resize(231, 127)
                .centerCrop().into(iv_recomm_kt_two);
        tv_recomm_kt_title_two.setText(beanList.get(5).getTitle());
        tv_recomm_kt_editor_two.setText(beanList.get(5).getEditor());
        tv_recomm_kt_editor_two.setTextColor(Color.RED);
        tv_recomm_kt_viewCount_two.setText(beanList.get(5).getViewCount());
        tv_kt_data_two.setText(beanList.get(5).getDate());
//        //第七个
        Picasso.with(getActivity()).load(beanList.get(6).getImg())
                .resize(231, 127)
                .centerCrop().into(iv_recomm_yc_one);
        tv_recomm_yc_title_one.setText(beanList.get(6).getTitle());
        tv_recomm_yc_editor_one.setText(beanList.get(6).getEditor());
        tv_recomm_yc_editor_one.setTextColor(Color.RED);
        tv_recomm_yc_viewCount_one.setText(beanList.get(6).getViewCount());
        tv_yc_data_one.setText(beanList.get(6).getDate());
//        //第八个
        Picasso.with(getActivity()).load(beanList.get(7).getImg())
                .resize(231, 127)
                .centerCrop().into(iv_recomm_yc_two);
        tv_recomm_yc_title_two.setText(beanList.get(7).getTitle());
        tv_recomm_yc_editor_two.setText(beanList.get(7).getEditor());
        tv_recomm_yc_editor_two.setTextColor(Color.RED);
        tv_recomm_yc_viewCount_two.setText(beanList.get(7).getViewCount());
        tv_yc_data_two.setText(beanList.get(7).getDate());
    }

    private void initBindBaner() {
        lists.clear();
        if (banerBean.size()==2){
            for (int i =0;i<banerBean.size()+2;i++){
                ImageView iv = new ImageView(getActivity());
                Picasso.with(getActivity()).load(banerBean.get((i>=2)?i-2:i).getImg())
                        .resize(ScreenUtils.getWidth(getActivity()), DensityUtils.dip2px(getActivity(),148.5f))
                        .centerCrop().into(iv);
                lists.add(iv);
            }
        }else {
            for (int i = 0; i < banerBean.size(); i++) {
                ImageView iv = new ImageView(getActivity());
                Picasso.with(getActivity()).load(banerBean.get(i).getImg())
                        .resize(ScreenUtils.getWidth(getActivity()), DensityUtils.dip2px(getActivity(),148.5f))
                        .centerCrop().into(iv);
                lists.add(iv);
            }
        }
        //添加下方点
        addPoints();
        //设置界面改变监听
        vp_recomm_head.setOnPageChangeListener(OnRecommBinderListener);
        //放入布局中
        MyHeadAdapter adapter = new MyHeadAdapter();
        vp_recomm_head.setAdapter(adapter);
        handler.removeCallbacksAndMessages(null);
        handler.sendEmptyMessageDelayed(DELAYED_TIME,2000);

    }



    private void addPoints() {
        ll_recomm_dot.removeAllViews();
        dots.clear();
        for (int i=0;i<banerBean.size();i++){
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
            ll_recomm_dot.addView(iv,params);
            dots.add(iv);
        }
    }



    class MyHeadAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            position = position % lists.size();
            View view = lists.get(position);
            if (((ViewGroup)view.getParent())!=null){
                ((ViewGroup)view.getParent()).removeAllViews();}
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

}
