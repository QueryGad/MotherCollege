package com.player.mothercollege.college.details;

import android.app.Dialog;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.gson.Gson;
import com.player.mothercollege.R;
import com.player.mothercollege.activity.BaseActivity;
import com.player.mothercollege.adapter.OriginalReveiwAdapter;
import com.player.mothercollege.bean.ReadBookDetailsBean;
import com.player.mothercollege.me.HeadIconActivity;
import com.player.mothercollege.utils.ConfigUtils;
import com.player.mothercollege.utils.DensityUtils;
import com.player.mothercollege.utils.MyLog;
import com.player.mothercollege.utils.PrefUtils;
import com.player.mothercollege.view.GlideCircleTransform;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.utils.Log;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

/**
 * Created by Administrator on 2016/11/2.
 */
public class OriginalDetailsActivity extends BaseActivity implements View.OnClickListener {

    private static final int GET_TEXTDETAILS_DATA = 001;
    private static final int POST_CANLE_ZAN = 002;
    private static final int POST_ZAN = 003;
    private static final int POST_COLLECT = 004;
    private static final int POST_CANLE_COLLECT = 005;
    private Button btn_back;
    private TextView tv_details_title;
    private LinearLayout ll_textdeatials_share,ll_textdeatials_comment,ll_textdeatials_zan,ll_textdeatials_collect;
    private String sid;
    private RequestQueue requestQueue;
    private ReadBookDetailsBean readBookDetailsBean;
    private RequestManager glideRequest;
    private WebView web_textdetails;
    private ListView lv_details;
    private String content;
    private ImageView iv_persondeatials_zan,iv_persondeatials_collect;

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
        lv_details = (ListView) findViewById(R.id.lv_details);

        ll_textdeatials_share = (LinearLayout) findViewById(R.id.ll_persondeatials_share);
        ll_textdeatials_comment = (LinearLayout) findViewById(R.id.ll_persondeatials_comment);
        ll_textdeatials_zan = (LinearLayout) findViewById(R.id.ll_persondeatials_zan);
        ll_textdeatials_collect = (LinearLayout) findViewById(R.id.ll_persondeatials_collect);

        iv_persondeatials_zan = (ImageView) findViewById(R.id.iv_persondeatials_zan);
        iv_persondeatials_collect = (ImageView) findViewById(R.id.iv_persondeatials_collect);

        tv_details_title.setText("详情");
    }

    @Override
    public void initListeners() {
        btn_back.setOnClickListener(this);
        ll_textdeatials_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShareDialog();
            }
        });
        ll_textdeatials_comment.setOnClickListener(this);
        ll_textdeatials_zan.setOnClickListener(this);
        ll_textdeatials_collect.setOnClickListener(this);
    }

    private Dialog dialog;

    private void showShareDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_share, null);
        // 设置style 控制默认dialog带来的边距问题 
        dialog = new Dialog(this, R.style.common_dialog);
        dialog.setContentView(view);
        dialog.show();
        RelativeLayout pengyou = (RelativeLayout) view.findViewById(R.id.view_share_pengyou);
        RelativeLayout wechat = (RelativeLayout) view.findViewById(R.id.view_share_wechat);
        RelativeLayout sina = (RelativeLayout) view.findViewById(R.id.view_share_sina);
        RelativeLayout space = (RelativeLayout) view.findViewById(R.id.view_share_space);
        RelativeLayout qq = (RelativeLayout) view.findViewById(R.id.view_share_qq);
        RelativeLayout frend = (RelativeLayout) view.findViewById(R.id.view_share_frend);
        Button btn_canle = (Button) view.findViewById(R.id.btn_canle);
        pengyou.setOnClickListener(this);
        wechat.setOnClickListener(this);
        sina.setOnClickListener(this);
        space.setOnClickListener(this);
        qq.setOnClickListener(this);
        frend.setOnClickListener(this);
        btn_canle.setOnClickListener(this);
        // 设置相关位置，一定要在 show()之后  
        Window window = dialog.getWindow();
        window.getDecorView().setPadding(0,0,0,0);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
    }

    @Override
    public void initData() {
        netWork();
    }

    private void netWork() {
        String apptoken = PrefUtils.getString(OriginalDetailsActivity.this, "apptoken", "");
        String uid = PrefUtils.getString(OriginalDetailsActivity.this, "uid", "null");
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.COLLEGE_URL, RequestMethod.GET);
        request.add("apptoken",apptoken);
        request.add("op","getStuInfo");
        request.add("uid",uid);
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
        String hasLike = readBookDetailsBean.getHasLike();
        if (hasLike.equals("true")){
            iv_persondeatials_zan.setImageResource(R.mipmap.icon_favour_list);
        }else {
            iv_persondeatials_zan.setImageResource(R.mipmap.tab_favour);
        }
        String hasKeep = readBookDetailsBean.getHasKeep();
        if (hasKeep.equals(true)){
            iv_persondeatials_collect.setImageResource(R.mipmap.tab_collected);
        }else {
            iv_persondeatials_collect.setImageResource(R.mipmap.tab_collect);
        }
        initHead();
        OriginalReveiwAdapter adapter = new OriginalReveiwAdapter(OriginalDetailsActivity.this,readBookDetailsBean.getReveiw());
        lv_details.setAdapter(adapter);
    }

    private void initHead() {
        View view = View.inflate(OriginalDetailsActivity.this,R.layout.head_college_original,null);
        TextView tv_textdetails_title = (TextView) view.findViewById(R.id.tv_textdetails_title);
        TextView tv_textdetails_time = (TextView) view.findViewById(R.id.tv_textdetails_time);
        TextView tv_textdetails_viewCount = (TextView) view.findViewById(R.id.tv_textdetails_viewCount);
        TextView tv_textdetails_zan = (TextView) view.findViewById(R.id.tv_textdetails_zan);
        TextView tv_textdetails_comment = (TextView) view.findViewById(R.id.tv_textdetails_comment);
        GridView gr_textdetails_head = (GridView) view.findViewById(R.id.gr_textdetails_head);
        web_textdetails = (WebView) view.findViewById(R.id.web_textdetails);

        tv_textdetails_title.setText(readBookDetailsBean.getTitle());
        tv_textdetails_time.setText("发布时间:"+readBookDetailsBean.getDate());
        tv_textdetails_viewCount.setText("浏览人数:"+readBookDetailsBean.getViewCount());
        tv_textdetails_zan.setText(readBookDetailsBean.getZlist().size()+"");
        tv_textdetails_comment.setText(readBookDetailsBean.getReveiw().size()+"");
        content = readBookDetailsBean.getContent();
        initH5(content);

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

        lv_details.addHeaderView(view);
    }


    private void initH5(String content) {
    WebSettings settings = web_textdetails.getSettings();
        settings.setJavaScriptEnabled(true);
        web_textdetails.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        web_textdetails.loadUrl(content);
        web_textdetails.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    private boolean orZan=true;
    private boolean orCollect = true;

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                finish();
                break;
            case R.id.ll_persondeatials_comment:
                Toast.makeText(OriginalDetailsActivity.this,"评论",Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_persondeatials_zan:
                if (orZan){
                    iv_persondeatials_zan.setImageResource(R.mipmap.icon_favour_list);
                    Toast.makeText(OriginalDetailsActivity.this,"已赞!",Toast.LENGTH_SHORT).show();
                    postZan();
                    orZan = false;
                }else {
                    iv_persondeatials_zan.setImageResource(R.mipmap.tab_favour);
                    Toast.makeText(OriginalDetailsActivity.this,"已取消!",Toast.LENGTH_SHORT).show();
                    canleZan();
                    orZan = true;
                }
                break;
            case R.id.ll_persondeatials_collect:
                if (orCollect){
                    iv_persondeatials_collect.setImageResource(R.mipmap.tab_collected);
                    Toast.makeText(OriginalDetailsActivity.this,"已收藏!",Toast.LENGTH_SHORT).show();
                    postCollect();
                    orCollect = false;
                }else {
                    iv_persondeatials_collect.setImageResource(R.mipmap.tab_collect);
                    Toast.makeText(OriginalDetailsActivity.this,"已取消!",Toast.LENGTH_SHORT).show();
                    canleCollect();
                    orCollect = true;
                }
                break;
            case R.id.view_share_pengyou:
                new ShareAction(OriginalDetailsActivity.this).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                        .withTargetUrl(content)
                        .setCallback(umShareListener)
                        .share();
                break;
            case R.id.view_share_wechat:
                new ShareAction(OriginalDetailsActivity.this).setPlatform(SHARE_MEDIA.WEIXIN)
                        .withTargetUrl(content)
                        .setCallback(umShareListener)
                        .share();
                break;
            case R.id.view_share_sina:
                new ShareAction(OriginalDetailsActivity.this).setPlatform(SHARE_MEDIA.SINA)
                        .withTargetUrl(content)
                        .setCallback(umShareListener)
                        .share();
                break;
            case R.id.view_share_space:
                new ShareAction(OriginalDetailsActivity.this).setPlatform(SHARE_MEDIA.QZONE)
                        .withTargetUrl(content)
                        .setCallback(umShareListener)
                        .share();
                break;
            case R.id.view_share_qq:
                new ShareAction(OriginalDetailsActivity.this).setPlatform(SHARE_MEDIA.QQ)
                        .withTargetUrl(content)
                        .setCallback(umShareListener)
                        .share();
                break;
            case R.id.view_share_frend:
                Toast.makeText(OriginalDetailsActivity.this,"母亲大学堂",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_canle:
                dialog.dismiss();
                break;
        }
    }

    private void canleCollect() {
        String apptoken = PrefUtils.getString(OriginalDetailsActivity.this, "apptoken", "");
        String uid = PrefUtils.getString(OriginalDetailsActivity.this, "uid", "null");
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.POST_COMMON, RequestMethod.POST);
        request.add("apptoken",apptoken);
        request.add("op","PostUnKeep");
        request.add("btype","14");
        request.add("rid",sid);
        request.add("uid",uid);
        requestQueue.add(POST_CANLE_COLLECT, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                MyLog.testLog("取消收藏"+info);
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    private void postCollect() {
        String apptoken = PrefUtils.getString(OriginalDetailsActivity.this, "apptoken", "");
        String uid = PrefUtils.getString(OriginalDetailsActivity.this, "uid", "null");
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.POST_COMMON, RequestMethod.POST);
        request.add("apptoken",apptoken);
        request.add("op","postkeep");
        request.add("btype","14");
        request.add("rid",sid);
        MyLog.testLog("rid:"+sid);
        request.add("uid",uid);
        MyLog.testLog("uid:"+uid);
        requestQueue.add(POST_COLLECT, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                MyLog.testLog("收藏:"+info);
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    private void canleZan() {
        String apptoken = PrefUtils.getString(OriginalDetailsActivity.this, "apptoken", "");
        String uid = PrefUtils.getString(OriginalDetailsActivity.this, "uid", "null");
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.POST_COMMON, RequestMethod.POST);
        request.add("apptoken",apptoken);
        request.add("op","postUnZLike");
        request.add("btype","14");
        request.add("rid",sid);
        request.add("uid",uid);
        requestQueue.add(POST_CANLE_ZAN, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                MyLog.testLog("取消赞"+info);
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    private void postZan() {

        String apptoken = PrefUtils.getString(OriginalDetailsActivity.this, "apptoken", "");
        String uid = PrefUtils.getString(OriginalDetailsActivity.this, "uid", "null");
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.POST_COMMON, RequestMethod.POST);
        request.add("apptoken",apptoken);
        request.add("op","postZLike");
        request.add("btype","14");
        request.add("rid",sid);
        request.add("uid",uid);
        requestQueue.add(POST_ZAN, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                MyLog.testLog("点赞:"+info);
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat","platform"+platform);

            Toast.makeText(OriginalDetailsActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(OriginalDetailsActivity.this,platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if(t!=null){
                Log.d("throw","throw:"+t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(OriginalDetailsActivity.this,platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };

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
            iv_person_zan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //点击头像进入他人主页
                    Intent intent = new Intent(OriginalDetailsActivity.this, HeadIconActivity.class);
                    startActivity(intent);
                }
            });
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
