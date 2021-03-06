package com.player.mothercollege.college;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.player.mothercollege.R;
import com.player.mothercollege.adapter.VideoAdapter;
import com.player.mothercollege.bean.VideoBean;
import com.player.mothercollege.college.details.BzzbDeatilsActivity;
import com.player.mothercollege.utils.ConfigUtils;
import com.player.mothercollege.utils.DensityUtils;
import com.player.mothercollege.utils.MyLog;
import com.player.mothercollege.utils.PrefUtils;
import com.squareup.picasso.Picasso;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

import java.util.List;

/**
 * Created by Administrator on 2016/10/25.
 * 直播页面
 */
public class VideoFragment extends Fragment{

    private static final int GET_VIDEO_DATA = 001;
    private View view;
    private ListView lv_college_video;
    private RequestQueue requestQueue;
    private VideoBean.BzzbBean bzzbBean;
    private VideoBean.SqhgBean sqhgBean;
    private String apptoken;
    private String uid;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.frg_college_video,null);
        requestQueue = NoHttp.newRequestQueue();
        initView();
        initData();
        return view;
    }

    private void initView() {
        lv_college_video = (ListView) view.findViewById(R.id.lv_college_video);

    }

    private void initData() {
        netWork();
    }

    private void netWork() {
        apptoken = PrefUtils.getString(getActivity(),"apptoken","");
        uid = PrefUtils.getString(getActivity(), "uid", "");
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.COLLEGE_URL, RequestMethod.GET);
        request.add("op","zb");
        request.add("apptoken", apptoken);
        request.add("uid", uid);
        requestQueue.add(GET_VIDEO_DATA, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();

                MyLog.testLog("直播页面:"+info);
                if (info!=null){
                    parseJson(info);
                }
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

        VideoBean videoBean = gson.fromJson(info, VideoBean.class);
        //本周直播
        if (videoBean.getBzzb()==null){
            MyLog.testLog("服务器给我null了");
        }else {
            bzzbBean = videoBean.getBzzb();
            //本周直播绑定
            bzzbVideoBind();
        }


        //上期回看
        sqhgBean = videoBean.getSqhg();
        List<VideoBean.WqhkBean> wqhkBean = videoBean.getWqhk();//往期回看

        //上期回看绑定
        sqhgkVideoBind();
        //往期回看
        VideoAdapter adapter = new VideoAdapter(getActivity(),wqhkBean);
        lv_college_video.setAdapter(adapter);
    }

    /**
     * 上期回看
     */
    private void sqhgkVideoBind() {
        View oldView = View.inflate(getActivity(), R.layout.head_video_old,null);
        LinearLayout ll_old_video = (LinearLayout) oldView.findViewById(R.id.ll_old_video);
        ImageView iv = (ImageView) oldView.findViewById(R.id.iv_video_old);
        TextView date = (TextView) oldView.findViewById(R.id.tv_videoOld_date);
        TextView title = (TextView) oldView.findViewById(R.id.tv_videoOld_title);
        TextView editor = (TextView) oldView.findViewById(R.id.tv_videoOld_editor);
        TextView viewCount = (TextView) oldView.findViewById(R.id.tv_videoOld_viewCount);
        TextView tv_videoOld_money = (TextView) oldView.findViewById(R.id.tv_videoOld_money);
        ImageView iv_videoOld_money = (ImageView) oldView.findViewById(R.id.iv_videoOld_money);
        String pState = sqhgBean.getPState();
        int price =(int) sqhgBean.getPrice();

        if (pState.equals("0")){
            //免费
            tv_videoOld_money.setVisibility(View.GONE);
            iv_videoOld_money.setVisibility(View.GONE);
        }else if (pState.equals("1")){
            //限免
            tv_videoOld_money.setVisibility(View.VISIBLE);
            iv_videoOld_money.setVisibility(View.GONE);
            tv_videoOld_money.setText("限免");
        }else if (pState.equals("2")){
            //收费
            tv_videoOld_money.setVisibility(View.VISIBLE);
            iv_videoOld_money.setVisibility(View.VISIBLE);
            tv_videoOld_money.setText(price+"");
        }
        Picasso.with(getActivity()).load(sqhgBean.getImg())
                .resize(DensityUtils.dip2px(getActivity(),116f),DensityUtils.dip2px(getActivity(),63.5f))
                .centerCrop().into(iv);
        //切割字符串，得到遮罩时间
        String arrDate = sqhgBean.getDate();
        String[] split = arrDate.split(" ");
        date.setText(split[0]);
//        date.setText(sqhgBean.getDate());
        title.setText(sqhgBean.getTitle());
        editor.setText(sqhgBean.getEditor());
        editor.setTextColor(Color.RED);
        viewCount.setText(sqhgBean.getViewCount());
        final String sid = sqhgBean.getSid();
        ll_old_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BzzbDeatilsActivity.class);
                intent.putExtra("sid", sid);
                startActivity(intent);
            }
        });
        lv_college_video.addHeaderView(oldView);

    }

    /**
     * 本周直播
     */
    private void bzzbVideoBind() {
        View dayView = View.inflate(getActivity(), R.layout.head_video_day,null);
        LinearLayout ll_bzzb = (LinearLayout) dayView.findViewById(R.id.ll_bzzb);
        ImageView iv = (ImageView) dayView.findViewById(R.id.iv_video_day);
        TextView date = (TextView) dayView.findViewById(R.id.tv_videoDay_date);
        TextView title = (TextView) dayView.findViewById(R.id.tv_videoDay_title);
        TextView editor = (TextView) dayView.findViewById(R.id.tv_videoDay_editor);
        TextView startTime = (TextView) dayView.findViewById(R.id.tv_videoDay_startTime);

        Picasso.with(getActivity()).load(bzzbBean.getImg())
                .resize(DensityUtils.dip2px(getActivity(),116f),DensityUtils.dip2px(getActivity(),63.5f))
                .centerCrop().into(iv);
        title.setText(bzzbBean.getTitle());
        editor.setText(bzzbBean.getEditor());
        editor.setTextColor(Color.RED);
        //切割字符串，得到遮罩时间，与具体直播时间
        String arrDate = bzzbBean.getDate();
        String[] split = arrDate.split(" ");
        date.setText(split[0]);
        startTime.setText(split[1]);
        startTime.setTextColor(Color.RED);
        final String sid = bzzbBean.getSid();
        //点击进入详情页面
        ll_bzzb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BzzbDeatilsActivity.class);
                intent.putExtra("sid", sid);
                startActivity(intent);
            }
        });
        lv_college_video.addHeaderView(dayView);
    }
}
