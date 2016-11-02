package com.player.mothercollege.college.details;

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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.gson.Gson;
import com.player.mothercollege.R;
import com.player.mothercollege.activity.BaseActivity;
import com.player.mothercollege.bean.ReadBookDetailsBean;
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
 * Created by Administrator on 2016/11/2.
 */
public class OriginalDetailsActivity extends BaseActivity implements View.OnClickListener {

    private static final int GET_TEXTDETAILS_DATA = 001;
    private Button btn_back;
    private TextView tv_details_title,tv_textdetails_title,tv_textdetails_time,
            tv_textdetails_viewCount,tv_textdetails_nocomment,tv_textdetails_zan,tv_textdetails_comment;
    private WebView web_textdetails;
    private GridView gr_textdetails_head;
    private ListView lv_textdetails;
    private ImageView iv_textdetails_nocomment;
    private LinearLayout ll_textdeatials_share,ll_textdeatials_comment,ll_textdeatials_zan,ll_textdeatials_collect;
    private String sid;
    private RequestQueue requestQueue;
    private ReadBookDetailsBean readBookDetailsBean;
    private RequestManager glideRequest;

    @Override
    public void setContentView() {
       setContentView(R.layout.act_college_textdetails);
        requestQueue = NoHttp.newRequestQueue();
    }

    @Override
    public void initViews() {
        sid = getIntent().getStringExtra("sid");

        btn_back = (Button) findViewById(R.id.btn_back);
        tv_details_title = (TextView) findViewById(R.id.tv_details_title);
        tv_textdetails_title = (TextView) findViewById(R.id.tv_textdetails_title);
        tv_textdetails_time = (TextView) findViewById(R.id.tv_textdetails_time);
        tv_textdetails_viewCount = (TextView) findViewById(R.id.tv_textdetails_viewCount);
        tv_textdetails_nocomment = (TextView) findViewById(R.id.tv_textdetails_nocomment);
        tv_textdetails_zan = (TextView) findViewById(R.id.tv_textdetails_zan);
        tv_textdetails_comment = (TextView) findViewById(R.id.tv_textdetails_comment);
        web_textdetails = (WebView) findViewById(R.id.web_textdetails);
        gr_textdetails_head = (GridView) findViewById(R.id.gr_textdetails_head);
        lv_textdetails = (ListView) findViewById(R.id.lv_textdetails);
        iv_textdetails_nocomment = (ImageView) findViewById(R.id.iv_textdetails_nocomment);
        ll_textdeatials_share = (LinearLayout) findViewById(R.id.ll_textdeatials_share);
        ll_textdeatials_comment = (LinearLayout) findViewById(R.id.ll_textdeatials_comment);
        ll_textdeatials_zan = (LinearLayout) findViewById(R.id.ll_textdeatials_zan);
        ll_textdeatials_collect = (LinearLayout) findViewById(R.id.ll_textdeatials_collect);

        tv_details_title.setText("详情");
    }

    @Override
    public void initListeners() {
        btn_back.setOnClickListener(this);
        ll_textdeatials_share.setOnClickListener(this);
        ll_textdeatials_comment.setOnClickListener(this);
        ll_textdeatials_zan.setOnClickListener(this);
        ll_textdeatials_collect.setOnClickListener(this);
    }

    @Override
    public void initData() {
        netWork();
    }

    private void netWork() {
        String apptoken = PrefUtils.getString(OriginalDetailsActivity.this, "apptoken", "");
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.COLLEGE_URL, RequestMethod.GET);
        request.add("apptoken",apptoken);
        request.add("op","getStuInfo");
        request.add("uid","null");
        request.add("stype","2");
        request.add("sid",sid);
        requestQueue.add(GET_TEXTDETAILS_DATA, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                MyLog.testLog("读书详情页面:"+info);
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
        readBookDetailsBean = gson.fromJson(info, ReadBookDetailsBean.class);
        tv_textdetails_title.setText(readBookDetailsBean.getTitle());
        tv_textdetails_time.setText("发布时间:"+ readBookDetailsBean.getDate());
        tv_textdetails_viewCount.setText("浏览人数"+ readBookDetailsBean.getViewCount());
        tv_textdetails_zan.setText(readBookDetailsBean.getZlist().size()+"");
        tv_textdetails_comment.setText(readBookDetailsBean.getReviewCount());
        initH5();
        haveNoComment();

        int length = DensityUtils.dip2px(OriginalDetailsActivity.this,15);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        float density = dm.density;
        int gridviewWidth = (int) (readBookDetailsBean.getZlist().size() * (length + 4) * density);
        int itemWidth = (int) (length * density);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                gridviewWidth, LinearLayout.LayoutParams.FILL_PARENT);
        gr_textdetails_head.setLayoutParams(params); // 设置GirdView布局参数,横向布局的关键
        gr_textdetails_head.setColumnWidth(itemWidth); // 设置列表项宽
        gr_textdetails_head.setHorizontalSpacing(5); // 设置列表项水平间距
        gr_textdetails_head.setStretchMode(GridView.NO_STRETCH);
        gr_textdetails_head.setNumColumns(readBookDetailsBean.getZlist().size()); // 设置列数量=列表集合数

        TextDetailsAdapter adapter = new TextDetailsAdapter();
        gr_textdetails_head.setAdapter(adapter);
    }

    private void initH5() {
        WebSettings settings = web_textdetails.getSettings();
        settings.setJavaScriptEnabled(true);
        web_textdetails.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        web_textdetails.loadUrl("http://www.baidu.com");
        web_textdetails.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    private void haveNoComment() {
        List<ReadBookDetailsBean.ReveiwBean> reveiwList = readBookDetailsBean.getReveiw();
        if (reveiwList.size()==0){
            iv_textdetails_nocomment.setVisibility(View.VISIBLE);
            tv_textdetails_nocomment.setVisibility(View.VISIBLE);
            lv_textdetails.setVisibility(View.GONE);
        }else {
            iv_textdetails_nocomment.setVisibility(View.GONE);
            tv_textdetails_nocomment.setVisibility(View.GONE);
            lv_textdetails.setVisibility(View.VISIBLE);
            DetailsConmmentAdapter adapter = new DetailsConmmentAdapter();
            lv_textdetails.setAdapter(adapter);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                finish();
                break;
            case R.id.ll_textdeatials_share:
                Toast.makeText(OriginalDetailsActivity.this,"分享",Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_textdeatials_comment:
                Toast.makeText(OriginalDetailsActivity.this,"评论",Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_textdeatials_zan:
                Toast.makeText(OriginalDetailsActivity.this,"赞",Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_textdeatials_collect:
                Toast.makeText(OriginalDetailsActivity.this,"收藏",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    class TextDetailsAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return readBookDetailsBean.getZlist().size();
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
            View view = View.inflate(OriginalDetailsActivity.this, R.layout.item_details_personzan,null);
            ImageView iv_person_zan = (ImageView) view.findViewById(R.id.iv_person_zan);
            glideRequest = Glide.with(OriginalDetailsActivity.this);
            glideRequest.load(readBookDetailsBean.getZlist().get(position).getUicon())
                    .transform(new GlideCircleTransform(OriginalDetailsActivity.this)).into(iv_person_zan);
            return view;
        }
    }

    class DetailsConmmentAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return readBookDetailsBean.getReveiw().size();
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
            View view = View.inflate(OriginalDetailsActivity.this,R.layout.item_persondetails_comment,null);
            TextView tv_person_othername = (TextView) view.findViewById(R.id.tv_person_othername);
            TextView tv_person_othercontent = (TextView) view.findViewById(R.id.tv_person_othercontent);
            tv_person_othername.setText(readBookDetailsBean.getReveiw().get(position).getUnicename()+":");
            tv_person_othercontent.setText(readBookDetailsBean.getReveiw().get(position).getContent());
            return view;
        }
    }
}
