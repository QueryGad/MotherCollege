package com.player.mothercollege.college.details;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.player.mothercollege.R;
import com.player.mothercollege.bean.ClassDetailsBean;
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

import java.util.ArrayList;
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

    private ItemListener itemListener;

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
        String uid = PrefUtils.getString(getActivity(), "uid", "");
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
                if (info!=null){
                    parseJson(info);
                }

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
        if (info!=null){
            Gson gson = new Gson();
            ClassDetailsBean classDetailsBean = gson.fromJson(info, ClassDetailsBean.class);
            ClassDetailsBean.CourseInfoBean courseInfo = classDetailsBean.getCourseInfo();
            List<ClassDetailsBean.VideosBean> videosList = classDetailsBean.getVideos();
            tv_video_item_title.setText(courseInfo.getTitle());
            tv_video_item_viewCount.setText(courseInfo.getViewCount()+"人已看");
            tv_video_item_zan.setText(courseInfo.getLikeCount()+"已赞");
            tv_video_item_info.setText(courseInfo.getInfo());
            ClassDetailsAdapter adapter = new ClassDetailsAdapter(getActivity(),videosList);
            lv_video_item.setAdapter(adapter);
        }

    }

    @Override
    public void onClick(View v) {
         switch (v.getId()){
             case R.id.ll_video_item_head:
                 initUpDown();
                 break;
         }
    }

    //展开与收起
    boolean isUp = false;
    private void initUpDown() {

        if (isUp){
            tv_video_item_info.setMaxLines(20);
            iv_rb_shrink.setVisibility(View.VISIBLE);
            iv_rb_pulldown.setVisibility(View.GONE);
            isUp = false;
        }else {
            tv_video_item_info.setMaxLines(1);
            iv_rb_shrink.setVisibility(View.GONE);
            iv_rb_pulldown.setVisibility(View.VISIBLE);
            isUp = true;
        }
    }


    public class ClassDetailsAdapter extends BaseAdapter {

        private Context context;
        private List<ClassDetailsBean.VideosBean> lists = new ArrayList<>();

        public ClassDetailsAdapter(Context context,List lists) {
            super();
            this.context = context;
            this.lists = lists;
        }

        @Override
        public int getCount() {
            return lists.size();
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
            View view =null;
            DetailsHolder holder = new DetailsHolder();
            if (convertView==null){
                view = View.inflate(context, R.layout.item_college_original,null);
                holder = new DetailsHolder();
                holder.iv_original = (ImageView) view.findViewById(R.id.iv_original);
                holder.tv_original_date = (TextView) view.findViewById(R.id.tv_original_date);
                holder.tv_original_title = (TextView) view.findViewById(R.id.tv_original_title);
                holder.tv_original_editor = (TextView) view.findViewById(R.id.tv_original_editor);
                holder.tv_original_viewCount = (TextView) view.findViewById(R.id.tv_original_viewCount);
                holder.iv_original_recomm = (ImageView) view.findViewById(R.id.iv_original_recomm);
                holder.ll_original_item = (LinearLayout) view.findViewById(R.id.ll_original_item);
                view.setTag(holder);
            }else {
                view = convertView;
                holder = (DetailsHolder) view.getTag();
            }
            Picasso.with(context).load(lists.get(position).getImg())
                    .resize(DensityUtils.dip2px(context,116f),DensityUtils.dip2px(context,63.5f))
                    .centerCrop().into(holder.iv_original);
            holder.tv_original_date.setText(lists.get(position).getDate());
            holder.tv_original_title.setText(lists.get(position).getTitle());
            holder.tv_original_editor.setText(lists.get(position).getEditor());
            holder.tv_original_editor.setTextColor(Color.RED);
            holder.tv_original_viewCount.setText(lists.get(position).getViewCount()+"");
            final String sid = lists.get(position).getSid();
            final String title = lists.get(position).getTitle();
            final String url = lists.get(position).getUrl();
            final String img = lists.get(position).getImg();
            holder.ll_original_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //在本页面内更换视频
//                    Intent intent = new Intent(context, ClassDetailsActivity.class);
//                    intent.putExtra("sid",sid);
//                    intent.putExtra("url",url);
//                    intent.putExtra("img",img);
//                    intent.putExtra("title",title);
//                    context.startActivity(intent);
                      itemListener.sendContent(sid,url,title,img);
                }
            });
            return view;
        }

        private class DetailsHolder{
            private ImageView iv_original;
            private TextView tv_original_date;
            private TextView tv_original_title;
            private TextView tv_original_editor;
            private TextView tv_original_viewCount;
            private ImageView iv_original_recomm;
            private LinearLayout ll_original_item;

        }
    }

    public interface ItemListener{
        public void  sendContent(String sid,String url,String title,String img);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        itemListener = (ItemListener) getActivity();
    }
}
