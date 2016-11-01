package com.player.mothercollege.unity.details;

import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.gson.Gson;
import com.player.mothercollege.R;
import com.player.mothercollege.activity.BaseActivity;
import com.player.mothercollege.bean.ActivityDetailsBean;
import com.player.mothercollege.utils.ConfigUtils;
import com.player.mothercollege.utils.DensityUtils;
import com.player.mothercollege.utils.MyLog;
import com.player.mothercollege.utils.PrefUtils;
import com.player.mothercollege.view.GlideCircleTransform;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

import java.util.List;

/**
 * Created by Administrator on 2016/10/31.
 */
public class ActivityDetailsActivity extends BaseActivity implements View.OnClickListener {

    private static final int GET_ACTIVITYDITAILS = 001;
    private Button btn_back;
    private TextView tv_details_title,tv_activitydetails_title,tv_activitydetails_time
            ,tv_activitydetails_address,tv_activitydetails_num
            ,tv_invit_zan,tv_invit_comment,tv_activitydetails_nocomment;
    private WebView webView_activitydetails;
    private GridView gr_activitydetails_head;
    private ImageView iv_activitydetails_nocomment;
    private ListView lv_activitydetails;
    private RequestQueue requestQueue;
    private RequestManager glideRequest;
    private int aid;
    private ActivityDetailsBean activityDetailsBean;

    @Override
    public void setContentView() {
       setContentView(R.layout.act_unity_activiydetails);
        requestQueue = NoHttp.newRequestQueue();
    }

    @Override
    public void initViews() {

        aid = getIntent().getIntExtra("aid", 0);


        btn_back = (Button) findViewById(R.id.btn_back);
        tv_details_title = (TextView) findViewById(R.id.tv_details_title);
        tv_activitydetails_title = (TextView) findViewById(R.id.tv_activitydetails_title);
        tv_activitydetails_time = (TextView) findViewById(R.id.tv_activitydetails_time);
        tv_activitydetails_address = (TextView) findViewById(R.id.tv_activitydetails_address);
        tv_activitydetails_num = (TextView) findViewById(R.id.tv_activitydetails_num);
        webView_activitydetails = (WebView) findViewById(R.id.webView_activitydetails);
        tv_invit_zan = (TextView) findViewById(R.id.tv_invit_zan);
        tv_invit_comment = (TextView) findViewById(R.id.tv_invit_comment);
        tv_activitydetails_nocomment = (TextView) findViewById(R.id.tv_activitydetails_nocomment);
        gr_activitydetails_head = (GridView) findViewById(R.id.gr_activitydetails_head);
        iv_activitydetails_nocomment = (ImageView) findViewById(R.id.iv_activitydetails_nocomment);
        lv_activitydetails = (ListView) findViewById(R.id.lv_activitydetails);

        tv_details_title.setText("活动详情");
    }

    @Override
    public void initListeners() {
        btn_back.setOnClickListener(this);
    }

    @Override
    public void initData() {
        netWork();
    }

    private void netWork() {
        String apptoken = PrefUtils.getString(ActivityDetailsActivity.this, "apptoken", "");
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.UNITY_URL, RequestMethod.GET);
        request.add("aid",aid+"");
        request.add("uid","null");
        request.add("apptoken",apptoken);
        request.add("op","activeInfo");
        requestQueue.add(GET_ACTIVITYDITAILS, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                MyLog.testLog("活动详情:"+info);
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
        activityDetailsBean = gson.fromJson(info, ActivityDetailsBean.class);
        String title = activityDetailsBean.getTitle(); //活动标题
        String endDate = activityDetailsBean.getEndDate();//活动日期
        String address = activityDetailsBean.getAddress();//活动地址
        int joinCount = activityDetailsBean.getJoinCount();//参加人数
        String content = activityDetailsBean.getContent();//活动内容 转H5
        List<ActivityDetailsBean.LikesBean> likesList = activityDetailsBean.getLikes();
        int likesSize = likesList.size();
        List<ActivityDetailsBean.ReviewsBean> reviewsList = activityDetailsBean.getReviews();
        int reviewsSize = reviewsList.size(); //评论人数
        netH5();//传入网络地址
        haveNoComment(reviewsList);

        tv_activitydetails_title.setText(title);
        tv_activitydetails_time.setText(endDate);
        tv_activitydetails_address.setText(address);
        tv_activitydetails_num.setText("已有"+joinCount+"个人报名");
        tv_invit_zan.setText(likesSize+"");
        tv_invit_comment.setText(reviewsSize+"");

        int length = DensityUtils.dip2px(ActivityDetailsActivity.this,15);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        float density = dm.density;
        int gridviewWidth = (int) (activityDetailsBean.getLikes().size() * (length + 4) * density);
        int itemWidth = (int) (length * density);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                gridviewWidth, LinearLayout.LayoutParams.FILL_PARENT);
        gr_activitydetails_head.setLayoutParams(params); // 设置GirdView布局参数,横向布局的关键
        gr_activitydetails_head.setColumnWidth(itemWidth); // 设置列表项宽
        gr_activitydetails_head.setHorizontalSpacing(5); // 设置列表项水平间距
        gr_activitydetails_head.setStretchMode(GridView.NO_STRETCH);
        gr_activitydetails_head.setNumColumns(activityDetailsBean.getLikes().size()); // 设置列数量=列表集合数

        ActivityDetailsAdapter adapter = new ActivityDetailsAdapter();
        gr_activitydetails_head.setAdapter(adapter);
    }

    private void netH5() {
        WebSettings settings = webView_activitydetails.getSettings();
        settings.setJavaScriptEnabled(true);
        webView_activitydetails.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView_activitydetails.loadUrl("http://www.baidu.com");
        webView_activitydetails.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    private void haveNoComment(List reviewsList) {

        if (reviewsList.size()==0){
            iv_activitydetails_nocomment.setVisibility(View.VISIBLE);
            tv_activitydetails_nocomment.setVisibility(View.VISIBLE);
            lv_activitydetails.setVisibility(View.GONE);
        }else {
            iv_activitydetails_nocomment.setVisibility(View.GONE);
            tv_activitydetails_nocomment.setVisibility(View.GONE);
            lv_activitydetails.setVisibility(View.VISIBLE);
            DetailsConmmentAdapter adapter = new DetailsConmmentAdapter();
            lv_activitydetails.setAdapter(adapter);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                finish();
                break;
        }
    }

    class DetailsConmmentAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return activityDetailsBean.getReviews().size();
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
            View view = View.inflate(ActivityDetailsActivity.this,R.layout.item_persondetails_comment,null);
            TextView tv_person_othername = (TextView) view.findViewById(R.id.tv_person_othername);
            TextView tv_person_othercontent = (TextView) view.findViewById(R.id.tv_person_othercontent);
            tv_person_othername.setText(activityDetailsBean.getReviews().get(position).getUnicename()+":");
            tv_person_othercontent.setText(activityDetailsBean.getReviews().get(position).getContent());
            return view;
        }
    }

    class ActivityDetailsAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return activityDetailsBean.getLikes().size();
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
            View view = View.inflate(ActivityDetailsActivity.this, R.layout.item_details_personzan,null);
            ImageView iv_person_zan = (ImageView) view.findViewById(R.id.iv_person_zan);
            glideRequest = Glide.with(ActivityDetailsActivity.this);
            glideRequest.load(activityDetailsBean.getLikes().get(position).getUicon())
                    .transform(new GlideCircleTransform(ActivityDetailsActivity.this)).into(iv_person_zan);
            return view;
        }
    }
}
