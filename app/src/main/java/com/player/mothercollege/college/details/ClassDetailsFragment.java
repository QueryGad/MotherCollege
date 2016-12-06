package com.player.mothercollege.college.details;

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
import com.player.mothercollege.adapter.ClassDetailsAdapter;
import com.player.mothercollege.bean.ClassDetailsBean;
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
 * Created by Administrator on 2016/12/6.
 */
public class ClassDetailsFragment extends Fragment implements View.OnClickListener {

    private static final int GET_CLASS_DETAILS_DATA = 001;
    private View view;
    private RequestQueue requestQueue;
    private ListView lv_video_item;
    private String sid;
    private LinearLayout ll_video_item_head;
    private TextView tv_video_item_title;
    private TextView tv_video_item_viewCount,tv_video_item_zan,tv_video_item_info;
    private ImageView iv_rb_shrink,iv_rb_pulldown;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.frg_video_item_recomm,null);
        requestQueue = NoHttp.newRequestQueue();
        initView();
        initData();
        return view;
    }

    private void initView() {
        sid = getActivity().getIntent().getStringExtra("sid");
        lv_video_item = (ListView) view.findViewById(R.id.lv_video_item);
        ll_video_item_head = (LinearLayout) view.findViewById(R.id.ll_video_item_head);
        tv_video_item_title = (TextView) view.findViewById(R.id.tv_video_item_title);
        tv_video_item_viewCount = (TextView) view.findViewById(R.id.tv_video_item_viewCount);
        tv_video_item_zan = (TextView) view.findViewById(R.id.tv_video_item_zan);
        tv_video_item_info = (TextView) view.findViewById(R.id.tv_video_item_info);
        iv_rb_shrink = (ImageView) view.findViewById(R.id.iv_rb_shrink);
        iv_rb_pulldown = (ImageView) view.findViewById(R.id.iv_rb_pulldown);

        ll_video_item_head.setOnClickListener(this);
    }

    private void initData() {
        netWork();
    }

    private void netWork() {
        String apptoken = PrefUtils.getString(getActivity(), "apptoken", "");
        String uid = PrefUtils.getString(getActivity(), "uid", "null");
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.COLLEGE_URL, RequestMethod.GET);
        request.add("op","courseInfo");
        request.add("apptoken",apptoken);
        request.add("uid",uid);
        request.add("courseid",sid);
        requestQueue.add(GET_CLASS_DETAILS_DATA, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                MyLog.testLog("课堂详情页:"+info);
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
        ClassDetailsBean classDetailsBean = gson.fromJson(info, ClassDetailsBean.class);
        ClassDetailsBean.CourseInfoBean courseInfo = classDetailsBean.getCourseInfo();
        List<ClassDetailsBean.VideosBean> videosList = classDetailsBean.getVideos();
        tv_video_item_title.setText(courseInfo.getTitle());
        tv_video_item_viewCount.setText(courseInfo.getViewCount()+"人已看");
        tv_video_item_zan.setText(courseInfo.getZlikes().size()+"已赞");
        tv_video_item_info.setText(courseInfo.getInfo());
        ClassDetailsAdapter adapter = new ClassDetailsAdapter(getActivity(),videosList);
        lv_video_item.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {

    }
}
