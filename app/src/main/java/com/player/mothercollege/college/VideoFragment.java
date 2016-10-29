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
    private String sid;

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
        String apptoken = PrefUtils.getString(getActivity(),"apptoken","");
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.COLLEGE_URL, RequestMethod.GET);
        request.add("op","zb");
        request.add("apptoken",apptoken);
        request.add("uid","null");
        requestQueue.add(GET_VIDEO_DATA, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                MyLog.testLog("直播页面:"+info);
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
        VideoBean videoBean = gson.fromJson(info, VideoBean.class);
        //本周直播
        bzzbBean = videoBean.getBzzb();
        //上期回看
        sqhgBean = videoBean.getSqhg();
        List<VideoBean.WqhkBean> wqhkBean = videoBean.getWqhk();//往期回看
        //本周直播绑定
        bzzbVideoBind();
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
        ImageView iv = (ImageView) oldView.findViewById(R.id.iv_video_old);
        TextView date = (TextView) oldView.findViewById(R.id.tv_videoOld_date);
        TextView title = (TextView) oldView.findViewById(R.id.tv_videoOld_title);
        TextView editor = (TextView) oldView.findViewById(R.id.tv_videoOld_editor);
        TextView viewCount = (TextView) oldView.findViewById(R.id.tv_videoOld_viewCount);
        Picasso.with(getActivity()).load(sqhgBean.getImg())
                .resize(DensityUtils.dip2px(getActivity(),116f),DensityUtils.dip2px(getActivity(),63.5f))
                .centerCrop().into(iv);
        date.setText(sqhgBean.getDate());
        title.setText(sqhgBean.getTitle());
        editor.setText(sqhgBean.getEditor());
        editor.setTextColor(Color.RED);
        viewCount.setText(sqhgBean.getViewCount());
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
        sid = bzzbBean.getSid();
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
