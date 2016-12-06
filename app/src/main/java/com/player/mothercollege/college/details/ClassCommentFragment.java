package com.player.mothercollege.college.details;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.player.mothercollege.R;
import com.player.mothercollege.bean.ClassDetailsBean;
import com.player.mothercollege.utils.ConfigUtils;
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
public class ClassCommentFragment extends Fragment{

    private static final int GET_VIDEODETAILS_DATA = 001;
    private ListView lv_video_comment;
    private View view;
    private RequestQueue requestQueue;
    private String sid;
    private List<ClassDetailsBean.CourseInfoBean.ReviewsBean> reviewList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.frg_college_video_comment,null);
        requestQueue = NoHttp.newRequestQueue();
        initView();
        initData();
        return view;
    }

    private void initView() {
        sid = getActivity().getIntent().getStringExtra("sid");
        lv_video_comment = (ListView) view.findViewById(R.id.lv_video_comment);
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
        requestQueue.add(GET_VIDEODETAILS_DATA, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
                
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
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
        reviewList = courseInfo.getReviews();
        ClassCommentAdapter adapter = new ClassCommentAdapter();
        lv_video_comment.setAdapter(adapter);
    }

    private class ClassCommentAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return reviewList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(getActivity(),R.layout.item_videocomment_review,null);
            TextView tv_videocomment_name = (TextView) view.findViewById(R.id.tv_videocomment_name);
            TextView tv_videocomment_conent = (TextView) view.findViewById(R.id.tv_videocomment_conent);
            tv_videocomment_name.setText(reviewList.get(position).getUnicename()+":");
            tv_videocomment_conent.setText(reviewList.get(position).getContent());
            return view;
        }
    }
}
