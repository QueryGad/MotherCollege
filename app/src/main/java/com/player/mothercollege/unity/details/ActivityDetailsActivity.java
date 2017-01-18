package com.player.mothercollege.unity.details;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import com.player.mothercollege.adapter.ActivityReveiwAdapter;
import com.player.mothercollege.bean.ActivityApplyBean;
import com.player.mothercollege.bean.ActivityDetailsBean;
import com.player.mothercollege.login.LoginActivity;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2016/10/31.
 */
public class ActivityDetailsActivity extends BaseActivity implements View.OnClickListener {

    private static final int GET_ACTIVITYDITAILS = 001;
    private static final int POST_APPLAY_DATA = 002;
    private static final int POST_CANLE_ZAN = 003;
    private static final int POST_ZAN = 004;
    private static final int POST_COLLECT = 005;
    private static final int POST_CANLE_COLLECT = 006;
    private static final int POST_SELF_COMMENT = 007;
    private Button btn_back;
    private TextView tv_details_title;
    private ListView lv_activitydetails;
    private RequestQueue requestQueue;
    private RequestManager glideRequest;
    private ActivityDetailsBean activityDetailsBean;
    private AlertDialog alertDialog;

    private LinearLayout ll_activitydeatials_share,ll_activitydeatials_comment,ll_activitydeatials_zan,ll_activitydeatials_collect;
    private ImageView iv_activeydetails_zan,iv_activitydetails_baoming,iv_activitydeatials_collect;
    private String content;

    private TextView hide_down;
    private EditText comment_content;
    private Button comment_send;
    private RelativeLayout rl_comment;
    private LinearLayout ll_activitydeatials_line;
    private String aid;
    private List<ActivityDetailsBean.ReviewsBean> reviewsList;
    private ActivityReveiwAdapter adapter;
    private String apptoken;
    private String uid;

    @Override
    public void setContentView() {
       setContentView(R.layout.act_unity_activiydetails);
        requestQueue = NoHttp.newRequestQueue();
    }

    @Override
    public void initViews() {
        aid = getIntent().getStringExtra("aid");
        btn_back = (Button) findViewById(R.id.btn_back);
        tv_details_title = (TextView) findViewById(R.id.tv_details_title);

        lv_activitydetails = (ListView) findViewById(R.id.lv_activitydetails);
        iv_activitydetails_baoming = (ImageView) findViewById(R.id.iv_activitydetails_baoming);
        iv_activeydetails_zan = (ImageView) findViewById(R.id.iv_activeydetails_zan);
        iv_activitydeatials_collect = (ImageView) findViewById(R.id.iv_activitydeatials_collect);
        ll_activitydeatials_share = (LinearLayout) findViewById(R.id.ll_activitydeatials_share);
        ll_activitydeatials_comment = (LinearLayout) findViewById(R.id.ll_activitydeatials_comment);
        ll_activitydeatials_zan = (LinearLayout) findViewById(R.id.ll_activitydeatials_zan);
        ll_activitydeatials_collect = (LinearLayout) findViewById(R.id.ll_activitydeatials_collect);

        hide_down = (TextView) findViewById(R.id.hide_down);
        comment_content = (EditText) findViewById(R.id.comment_content);
        comment_send = (Button) findViewById(R.id.comment_send);
        rl_comment = (RelativeLayout) findViewById(R.id.rl_comment);
        ll_activitydeatials_line = (LinearLayout) findViewById(R.id.ll_activitydeatials_line);

        tv_details_title.setText("活动详情");
    }

    @Override
    public void initListeners() {
        btn_back.setOnClickListener(this);
        iv_activitydetails_baoming.setOnClickListener(this);
        ll_activitydeatials_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uid.equals("")){
                    //未登录  提示登录
                    Intent intent = new Intent(ActivityDetailsActivity.this, LoginActivity.class);
                    startActivity(intent);
                }else {
                     showShareDialog();
                }
            }
        });
        ll_activitydeatials_comment.setOnClickListener(this);
        ll_activitydeatials_zan.setOnClickListener(this);
        ll_activitydeatials_collect.setOnClickListener(this);
        hide_down.setOnClickListener(this);
        comment_send.setOnClickListener(this);
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
        Button btn_canle = (Button) view.findViewById(R.id.btn_canle);
        pengyou.setOnClickListener(this);
        wechat.setOnClickListener(this);
        sina.setOnClickListener(this);
        space.setOnClickListener(this);
        qq.setOnClickListener(this);
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
        apptoken = PrefUtils.getString(ActivityDetailsActivity.this, "apptoken", "");
        uid = PrefUtils.getString(ActivityDetailsActivity.this, "uid", "");
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.UNITY_URL, RequestMethod.GET);
        request.add("aid",aid+"");
        request.add("uid", uid);
        request.add("apptoken", apptoken);
        request.add("op","activeInfo");
        requestQueue.add(GET_ACTIVITYDITAILS, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                MyLog.testLog("活动详情:"+info);
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

    private void parseJson(String info){
        Gson gson = new Gson();
        activityDetailsBean = gson.fromJson(info, ActivityDetailsBean.class);
        boolean isLike = activityDetailsBean.isIsLike();
        boolean isKeep = activityDetailsBean.isIsKeep();
        if (isLike){
            iv_activeydetails_zan.setImageResource(R.mipmap.icon_favour_list);
        }else {
            iv_activeydetails_zan.setImageResource(R.mipmap.tab_favour);
        }
        if (isKeep){
            iv_activitydeatials_collect.setImageResource(R.mipmap.tab_collected);
        }else {
            iv_activitydeatials_collect.setImageResource(R.mipmap.tab_collect);
        }
        content = activityDetailsBean.getContent();
        reviewsList = activityDetailsBean.getReviews();
        initHead();
        adapter = new ActivityReveiwAdapter(ActivityDetailsActivity.this,activityDetailsBean.getReviews());
        lv_activitydetails.setAdapter(adapter);
    }

    private void initHead() {
        View view = View.inflate(ActivityDetailsActivity.this,R.layout.head_unity_activitydetails,null);
        TextView tv_activitydetails_title = (TextView) view.findViewById(R.id.tv_activitydetails_title);
        TextView tv_activitydetails_time = (TextView) view.findViewById(R.id.tv_activitydetails_time);
        TextView tv_activitydetails_address = (TextView) view.findViewById(R.id.tv_activitydetails_address);
        TextView tv_activitydetails_num = (TextView) view.findViewById(R.id.tv_activitydetails_num);
        WebView webView_activitydetails = (WebView) view.findViewById(R.id.webView_activitydetails);
        TextView tv_invit_zan = (TextView) view.findViewById(R.id.tv_invit_zan);
        TextView tv_invit_comment = (TextView) view.findViewById(R.id.tv_invit_comment);
        GridView gr_activitydetails_head = (GridView) view.findViewById(R.id.gr_activitydetails_head);

        String title = activityDetailsBean.getTitle(); //活动标题
        String endDate = activityDetailsBean.getEndDate();//活动日期
        String address = activityDetailsBean.getAddress();//活动地址
        int joinCount = activityDetailsBean.getJoinCount();//参加人数
        String content = activityDetailsBean.getContent();//活动内容 转H5
        List<ActivityDetailsBean.LikesBean> likesList = activityDetailsBean.getLikes();
        int likesSize = likesList.size();
        List<ActivityDetailsBean.ReviewsBean> reviewsList = activityDetailsBean.getReviews();
        int reviewsSize = reviewsList.size(); //评论人数

        tv_activitydetails_title.setText(title);
        tv_activitydetails_time.setText(endDate);
        tv_activitydetails_address.setText(address);
        tv_activitydetails_num.setText("已有"+joinCount+"个人报名");
        tv_invit_zan.setText(likesSize+"");
        tv_invit_comment.setText(reviewsSize+"");

        netH5(content,webView_activitydetails);//传入网络地址

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

        lv_activitydetails.addHeaderView(view);
    }

    private void netH5(String content, WebView webView_activitydetails) {
        WebSettings settings = webView_activitydetails.getSettings();
        settings.setJavaScriptEnabled(true);
        webView_activitydetails.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView_activitydetails.loadUrl(content);
        webView_activitydetails.setWebViewClient(new WebViewClient(){
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
            case R.id.iv_activitydetails_baoming:   //报名
                if (uid.equals("")){
                    //未登录  提示登录
                    Intent intent = new Intent(ActivityDetailsActivity.this, LoginActivity.class);
                    startActivity(intent);
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    View view = View.inflate(this,R.layout.alert_apply,null);
                    final EditText et_applay_name = (EditText) view.findViewById(R.id.et_applay_name);
                    final EditText et_applay_phone = (EditText) view.findViewById(R.id.et_applay_phone);
                    final EditText et_applay_join = (EditText) view.findViewById(R.id.et_applay_join);
                    final EditText et_applay_qq = (EditText) view.findViewById(R.id.et_applay_qq);
                    Button btn_applay_ok = (Button) view.findViewById(R.id.btn_applay_ok);
                    btn_applay_ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String name = et_applay_name.getText().toString().trim();
                            String phone = et_applay_phone.getText().toString().trim();
                            String join = et_applay_join.getText().toString().trim();
                            String qq = et_applay_qq.getText().toString().trim();
                            if (TextUtils.isEmpty(name)||TextUtils.isEmpty(phone)||TextUtils.isEmpty(join)){
                                Toast.makeText(ActivityDetailsActivity.this,"请补全您的个人信息!",Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if (TextUtils.isEmpty(qq)){
                                qq = "null";
                            }
                            applayNetWork(name,phone,join,qq);
                        }
                    });
                    builder.setView(view);
                    alertDialog = builder.show();
    //                alertDialog.setCancelable(false);
                }
                break;
            case R.id.ll_activitydeatials_comment://评论
                if (uid.equals("")){
                    //未登录  提示登录
                    Intent intent = new Intent(ActivityDetailsActivity.this, LoginActivity.class);
                    startActivity(intent);
                }else {
                    // 弹出输入法
                    InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    // 显示评论框
                    comment_content.requestFocus();
                    ll_activitydeatials_line.setVisibility(View.GONE);
                    rl_comment.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.hide_down:
                // 隐藏评论框
                ll_activitydeatials_line.setVisibility(View.VISIBLE);
                rl_comment.setVisibility(View.GONE);
                // 隐藏输入法，然后暂存当前输入框的内容，方便下次使用
                InputMethodManager im = (InputMethodManager)getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(comment_content.getWindowToken(), 0);
                break;
            case R.id.comment_send:
                sendComment();
                break;
            case R.id.ll_activitydeatials_zan:
                if (uid.equals("")){
                    //未登录  提示登录
                    Intent intent = new Intent(ActivityDetailsActivity.this, LoginActivity.class);
                    startActivity(intent);
                }else {
                    if (orZan){
                        iv_activeydetails_zan.setImageResource(R.mipmap.icon_favour_list);
                        Toast.makeText(ActivityDetailsActivity.this,"已赞!",Toast.LENGTH_SHORT).show();
                        postZan();
                        orZan = false;
                    }else {
                        iv_activeydetails_zan.setImageResource(R.mipmap.tab_favour);
                        Toast.makeText(ActivityDetailsActivity.this,"已取消!",Toast.LENGTH_SHORT).show();
                        canleZan();
                        orZan = true;
                    }
                }
                break;
            case R.id.ll_activitydeatials_collect:
                if (uid.equals("")){
                    //未登录  提示登录
                    Intent intent = new Intent(ActivityDetailsActivity.this, LoginActivity.class);
                    startActivity(intent);
                }else {
                    if (orCollect){
                        iv_activitydeatials_collect.setImageResource(R.mipmap.tab_collected);
                        Toast.makeText(ActivityDetailsActivity.this,"已收藏!",Toast.LENGTH_SHORT).show();
                        postCollect();
                        orCollect = false;
                    }else {
                        iv_activitydeatials_collect.setImageResource(R.mipmap.tab_collect);
                        Toast.makeText(ActivityDetailsActivity.this,"已取消!",Toast.LENGTH_SHORT).show();
                        canleCollect();
                        orCollect = true;
                    }
                }
                break;
            case R.id.view_share_pengyou:
                new ShareAction(ActivityDetailsActivity.this).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                        .withTargetUrl(content)
                        .setCallback(umShareListener)
                        .share();
                break;
            case R.id.view_share_wechat:
                new ShareAction(ActivityDetailsActivity.this).setPlatform(SHARE_MEDIA.WEIXIN)
                        .withTargetUrl(content)
                        .setCallback(umShareListener)
                        .share();
                break;
            case R.id.view_share_sina:
                new ShareAction(ActivityDetailsActivity.this).setPlatform(SHARE_MEDIA.SINA)
                        .withTargetUrl(content)
                        .setCallback(umShareListener)
                        .share();
                break;
            case R.id.view_share_space:
                new ShareAction(ActivityDetailsActivity.this).setPlatform(SHARE_MEDIA.QZONE)
                        .withTargetUrl(content)
                        .setCallback(umShareListener)
                        .share();
                break;
            case R.id.view_share_qq:
                new ShareAction(ActivityDetailsActivity.this).setPlatform(SHARE_MEDIA.QQ)
                        .withTargetUrl(content)
                        .setCallback(umShareListener)
                        .share();
                break;
            case R.id.btn_canle:
                dialog.dismiss();
                break;

        }
    }

    private void sendComment() {
        String self_comment = comment_content.getText().toString().trim();
        if (TextUtils.isEmpty(self_comment)){
            Toast.makeText(getApplicationContext(), "评论不能为空！", Toast.LENGTH_SHORT).show();
        }else {
            postComment(self_comment);
        }
    }

    private ProgressDialog pd ;
    private void postComment(final String self_comment) {
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.POST_COMMON, RequestMethod.POST);
        request.add("apptoken",apptoken);
        request.add("op","PostReview");
        request.add("btype","23");
        request.add("rid",aid+"");
        request.add("uid",uid);
        request.add("rcontent",self_comment);
        requestQueue.add(POST_SELF_COMMENT, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
                pd = new ProgressDialog(ActivityDetailsActivity.this);
                pd.show();
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                try {
                    JSONObject json = new JSONObject(info);
                    boolean isSuccess = json.getBoolean("isSuccess");
                    if (isSuccess){
                        pd.dismiss();
                        comment_content.setText("");
                        // 隐藏评论框
                        ll_activitydeatials_line.setVisibility(View.VISIBLE);
                        rl_comment.setVisibility(View.GONE);
                        // 隐藏输入法，然后暂存当前输入框的内容，方便下次使用
                        InputMethodManager im = (InputMethodManager)getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        im.hideSoftInputFromWindow(comment_content.getWindowToken(), 0);
                        Toast.makeText(ActivityDetailsActivity.this,"评论成功!",Toast.LENGTH_SHORT).show();
                        ActivityDetailsBean.ReviewsBean bean = new ActivityDetailsBean.ReviewsBean();
                        String username = PrefUtils.getString(ActivityDetailsActivity.this, "username", "");
                        bean.setUnicename(username);
                        bean.setContent(self_comment);
                        reviewsList.add(bean);
                        adapter.notifyDataSetChanged();
                        lv_activitydetails.setSelection(lv_activitydetails.getBottom());
                    }else {
                        pd.dismiss();
                        Toast.makeText(ActivityDetailsActivity.this,"评论失败，请稍候重试!",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                pd.dismiss();
            }

            @Override
            public void onFinish(int what) {
                pd.dismiss();
            }
        });
    }

    private void canleCollect() {
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.POST_COMMON, RequestMethod.POST);
        request.add("apptoken",apptoken);
        request.add("op","PostUnKeep");
        request.add("btype","23");
        request.add("rid",aid);
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
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.POST_COMMON, RequestMethod.POST);
        request.add("apptoken",apptoken);
        request.add("op","postkeep");
        request.add("btype","23");
        request.add("rid",aid);
        request.add("uid",uid);
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

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat","platform"+platform);

            Toast.makeText(ActivityDetailsActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(ActivityDetailsActivity.this,platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if(t!=null){
                Log.d("throw","throw:"+t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(ActivityDetailsActivity.this,platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };

    private void canleZan() {
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.POST_COMMON, RequestMethod.POST);
        request.add("apptoken",apptoken);
        request.add("op","postUnZLike");
        request.add("btype","23");
        request.add("rid",aid+"");
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
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.POST_COMMON, RequestMethod.POST);
        request.add("apptoken",apptoken);
        request.add("op","postZLike");
        request.add("btype","23");
        request.add("rid",aid+"");
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

    private void applayNetWork(String name,String phone,String join,String qq) {
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.UNITY_URL, RequestMethod.POST);
        request.add("op","singUpActive");
        request.add("apptoken",apptoken);
        request.add("uid",uid);
        request.add("aid",aid+"");
        request.add("name",name);
        request.add("phoneNo",phone);
        request.add("joinCount",join);
        request.add("qq",qq);
        requestQueue.add(POST_APPLAY_DATA, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
                pd = new ProgressDialog(ActivityDetailsActivity.this);
                pd.show();
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                MyLog.testLog("报名:"+info);
                pd.dismiss();
                Gson gson = new Gson();
                ActivityApplyBean activityApplyBean = gson.fromJson(info, ActivityApplyBean.class);
                String resultInfo = activityApplyBean.getResultInfo();
                if (resultInfo.equals("成功")){
                    Toast.makeText(ActivityDetailsActivity.this,"报名成功",Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                }else if (resultInfo.equals("用户已报名")){
                    Toast.makeText(ActivityDetailsActivity.this,"您已经报过名了!",Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                }else {
                    Toast.makeText(ActivityDetailsActivity.this,resultInfo,Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                pd.dismiss();
                MyLog.testLog("onFailed");
                alertDialog.dismiss();
            }

            @Override
            public void onFinish(int what) {
                pd.dismiss();
                MyLog.testLog("onFinish");
                alertDialog.dismiss();
            }
        });

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
            String uicon = activityDetailsBean.getLikes().get(position).getUicon();
            final String toUid = activityDetailsBean.getLikes().get(position).getUid();
            if (uicon==null){
                iv_person_zan.setImageResource(R.mipmap.head_me_nor);
            }else {
                glideRequest = Glide.with(ActivityDetailsActivity.this);
                glideRequest.load(uicon)
                        .transform(new GlideCircleTransform(ActivityDetailsActivity.this)).into(iv_person_zan);
            }

            iv_person_zan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ActivityDetailsActivity.this, HeadIconActivity.class);
                    intent.putExtra("toUid",toUid);
                    startActivity(intent);
                }
            });

            return view;
        }
    }
}
