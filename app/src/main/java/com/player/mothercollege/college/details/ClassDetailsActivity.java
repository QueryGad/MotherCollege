package com.player.mothercollege.college.details;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.PowerManager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dou361.ijkplayer.bean.VideoijkBean;
import com.dou361.ijkplayer.listener.OnShowThumbnailListener;
import com.dou361.ijkplayer.widget.PlayStateParams;
import com.dou361.ijkplayer.widget.PlayerView;
import com.google.gson.Gson;
import com.player.mothercollege.R;
import com.player.mothercollege.activity.BaseActivity;
import com.player.mothercollege.bean.ClassDetailsBean;
import com.player.mothercollege.bean.TakeVideoBean;
import com.player.mothercollege.login.LoginActivity;
import com.player.mothercollege.utils.ConfigUtils;
import com.player.mothercollege.utils.MediaUtils;
import com.player.mothercollege.utils.MyLog;
import com.player.mothercollege.utils.PrefUtils;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/6.
 */
public class ClassDetailsActivity extends BaseActivity implements View.OnClickListener {


    private static final int GET_CLASS_DETAILS_DATA = 001;
    private static final int POST_SELF_COMMENT = 002;
    private static final int POST_ZAN = 003;
    private static final int POST_CANLE_ZAN = 004;
    private static final int POST_COLLECT = 005;
    private static final int POST_CANLE_COLLECT = 006;
    private static final int POST_SMARTMONEY_DATA = 007;
    private static final int POST_TAKEVIDEO_DATA = 100;
    private RequestQueue requestQueue;
    private RadioGroup rg_video;
    private RadioButton rb_frg_details,rb_frg_comment;
    private FrameLayout fl_video;
    private Dialog dialog;
    private LinearLayout ll_videodetails_share,ll_videodetails_comment,ll_videodetails_zan,ll_videodetails_collect;
    private ClassCommentFragment classComment;
    private ClassDetailsFragment classDetails;
    private ImageView iv_videodetails_zan,iv_videodetails_collect;

    private TextView hide_down;
    private EditText comment_content;
    private Button comment_send;
    private RelativeLayout rl_comment;
    private LinearLayout ll_persondeatials_line;

    //播放器
    private PlayerView player;
    private List<VideoijkBean> list;
    private PowerManager.WakeLock wakeLock;
    private View rootView;

    private LinearLayout ll_bzzb_dingyue;
    private Button btn_bzzb_dingyue;

    private String sid;
    private String url;
    private RadioGroup.OnCheckedChangeListener ClassDeatilsListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId){
                case R.id.rb_frg_details:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fl_video,classDetails).commit();
                    break;
                case R.id.rb_frg_comment:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fl_video,classComment).commit();
                    break;
            }
        }
    };
    private List<ClassDetailsBean.CourseInfoBean.ReviewsBean> reviewsList = new ArrayList<>();
    private String apptoken;
    private String uid;
    private List<ClassDetailsBean.VideosBean> videosList = new ArrayList<>();
    private String imgVideo;

    @Override
    public void setContentView() {
        rootView = getLayoutInflater().from(this).inflate(R.layout.act_college_bzzbdetails,null);
        setContentView(rootView);
        requestQueue = NoHttp.newRequestQueue();
        /**虚拟按键的隐藏方法*/
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {

                //比较Activity根布局与当前布局的大小
                int heightDiff = rootView.getRootView().getHeight() - rootView.getHeight();
                if (heightDiff > 100) {
                    //大小超过100时，一般为显示虚拟键盘事件
                    rootView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                } else {
                    //大小小于100时，为不显示虚拟键盘或虚拟键盘隐藏
                    rootView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

                }
            }
        });
        /**常亮*/
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "liveTAG");
        wakeLock.acquire();
        list = new ArrayList<VideoijkBean>();


    }

    @Override
    public void initViews() {
        sid = getIntent().getStringExtra("sid");
        url = getIntent().getStringExtra("url");
        imgVideo = getIntent().getStringExtra("img");
        titleVideo = getIntent().getStringExtra("title");

        rg_video = (RadioGroup) findViewById(R.id.rg_video);
        rb_frg_details = (RadioButton) findViewById(R.id.rb_frg_details);
        rb_frg_comment = (RadioButton) findViewById(R.id.rb_frg_comment);
        fl_video = (FrameLayout) findViewById(R.id.fl_video);
        //下方点赞四个按钮
        ll_videodetails_share = (LinearLayout) findViewById(R.id.ll_videodetails_share);
        ll_videodetails_comment = (LinearLayout) findViewById(R.id.ll_videodetails_comment);
        ll_videodetails_zan = (LinearLayout) findViewById(R.id.ll_videodetails_zan);
        ll_videodetails_collect = (LinearLayout) findViewById(R.id.ll_videodetails_collect);
        classComment = new ClassCommentFragment();
        classDetails = new ClassDetailsFragment();
        hide_down = (TextView) findViewById(R.id.hide_down);
        comment_content = (EditText) findViewById(R.id.comment_content);
        comment_send = (Button) findViewById(R.id.comment_send);
        rl_comment = (RelativeLayout) findViewById(R.id.rl_comment);
        ll_persondeatials_line = (LinearLayout) findViewById(R.id.ll_persondeatials_line);

        iv_videodetails_zan = (ImageView) findViewById(R.id.iv_videodetails_zan);
        iv_videodetails_collect = (ImageView) findViewById(R.id.iv_videodetails_collect);

        ll_bzzb_dingyue = (LinearLayout) findViewById(R.id.ll_bzzb_dingyue);
        btn_bzzb_dingyue = (Button) findViewById(R.id.btn_bzzb_dingyue);
        //设置默认
        getSupportFragmentManager().beginTransaction().add(R.id.fl_video,classDetails).commit();
        rb_frg_details.setChecked(true);
    }

    @Override
    public void initListeners() {
        rg_video.setOnCheckedChangeListener(ClassDeatilsListener);
        ll_videodetails_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uid.equals("")){
                    //未登录  提示登录
                    Intent intent = new Intent(ClassDetailsActivity.this, LoginActivity.class);
                    startActivity(intent);
                }else {
                showShareDialog();
                }
            }
        });
        ll_videodetails_comment.setOnClickListener(this);
        ll_videodetails_zan.setOnClickListener(this);
        ll_videodetails_collect.setOnClickListener(this);
        hide_down.setOnClickListener(this);
        comment_send.setOnClickListener(this);
    }

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
        apptoken = PrefUtils.getString(this, "apptoken", "");
        uid = PrefUtils.getString(ClassDetailsActivity.this, "uid", "");
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.COLLEGE_URL, RequestMethod.GET);
        request.add("op","courseInfo");
        request.add("apptoken", apptoken);
        request.add("uid", uid);
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
        videosList = classDetailsBean.getVideos();
        reviewsList = courseInfo.getReviews();
        boolean hasLike = courseInfo.isHasLike();
        if (hasLike){
            iv_videodetails_zan.setImageResource(R.mipmap.icon_favour_list);
        }else {
            iv_videodetails_zan.setImageResource(R.mipmap.tab_favour);
        }
        boolean hasKeep = courseInfo.isHasKeep();
        if (hasKeep){
            iv_videodetails_collect.setImageResource(R.mipmap.tab_collected);
        }else {
            iv_videodetails_collect.setImageResource(R.mipmap.tab_collect);
        }

        int payType = courseInfo.getPayType();
        boolean hasBuy = courseInfo.isHasBuy();
        final int price = courseInfo.getPrice();
        if (payType==0){
            //免费
            ll_bzzb_dingyue.setVisibility(View.GONE);
            initVideo();
        }else if (payType==1){
            //限免
            ll_bzzb_dingyue.setVisibility(View.GONE);
            initVideo();
        }else if (payType==2){
            //智慧币
            if (hasBuy){
                ll_bzzb_dingyue.setVisibility(View.GONE);
                initVideo();
            }else {
                //订阅
                ll_bzzb_dingyue.setVisibility(View.VISIBLE);
                btn_bzzb_dingyue.setOnClickListener(new View.OnClickListener() {
                    AlertDialog dialog = null;
                    @Override
                    public void onClick(View v) {
                        //弹对话框 是否确定订阅
                        AlertDialog.Builder builder = new AlertDialog.Builder(ClassDetailsActivity.this);
                        builder.setTitle("温馨提示");
                        builder.setMessage("是否选择花费"+price+"智慧币订阅此课程?");
                        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //付费凭证
                                postSmartMoney();
                            }
                        });
                        dialog = builder.show();
                    }
                });
            }
        }
        initVideo();
    }

    /**
     * 请求资源付费凭证
     */
    private void postSmartMoney() {
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.COLLEGE_URL, RequestMethod.POST);
        request.add("apptoken",apptoken);
        request.add("op","GetPayBillno");
        request.add("uid",uid);
        request.add("sid",sid);
        request.add("stype","12");
        requestQueue.add(POST_SMARTMONEY_DATA, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                MyLog.testLog("付费凭证:"+info);
                try {
                    JSONObject json = new JSONObject(info);
                    String billNo = json.getString("billNo");
                    takeVideo(billNo);
                } catch (JSONException e) {
                    e.printStackTrace();
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

    /**
     * 订阅课程
     * @param billno
     */
    private void takeVideo(final String billno) {
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.COLLEGE_URL, RequestMethod.POST);
        request.add("apptoken",apptoken);
        request.add("op","BuyStu");
        request.add("sid",sid);
        request.add("stype","11");
        request.add("uid",uid);
        request.add("billno",billno);
        requestQueue.add(POST_TAKEVIDEO_DATA, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                MyLog.testLog("订阅是否成功:"+info);
                Gson gson = new Gson();
                TakeVideoBean takeVideoBean = gson.fromJson(info, TakeVideoBean.class);
                boolean isSuccess = takeVideoBean.isIsSuccess();
                String resultCode = takeVideoBean.getResultCode();
                if (resultCode.equals("1")){
                    ll_bzzb_dingyue.setVisibility(View.GONE);
                    initVideo();
                }else if (resultCode.equals("2004")){
                    //未登录  提示登录
                    Intent intent = new Intent(ClassDetailsActivity.this, LoginActivity.class);
                    startActivity(intent);
                }else if (resultCode.equals("3003")){
                    Toast.makeText(ClassDetailsActivity.this,"智慧币不足,请转至-我的-智慧币-充值,进行充值服务!",Toast.LENGTH_SHORT).show();
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

    private String titleVideo;
    private void initVideo() {

        VideoijkBean ml = new VideoijkBean();
        ml.setUrl(url);
        list.add(ml);
        player = new PlayerView(this){
            @Override
            public PlayerView toggleProcessDurationOrientation() {
                hideSteam(getScreenOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                return setProcessDurationOrientation(getScreenOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT ? PlayStateParams.PROCESS_PORTRAIT : PlayStateParams.PROCESS_LANDSCAPE);
            }

            @Override
            public PlayerView setPlaySource(List<VideoijkBean> list) {
                return super.setPlaySource(list);
            }
        }
                .setTitle(titleVideo)
                .setProcessDurationOrientation(PlayStateParams.PROCESS_PORTRAIT)
                .setScaleType(PlayStateParams.fillparent)
                .forbidTouch(false)
                .hideSteam(true)
                .hideCenterPlayer(true)
                .showThumbnail(new OnShowThumbnailListener() {
                    @Override
                    public void onShowThumbnail(ImageView ivThumbnail) {
                        Glide.with(ClassDetailsActivity.this)
                                .load(imgVideo)
                                .placeholder(R.color.cl_default)
                                .error(R.color.cl_error)
                                .into(ivThumbnail);
                    }
                })
                .setPlaySource(list)
                .setChargeTie(true,60)
                .startPlay();
    }

    private boolean orZan=true;
    private boolean orCollect = true;

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.view_share_pengyou:
                new ShareAction(ClassDetailsActivity.this).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                        .withText("母亲大学堂视频分享")
                        .setCallback(umShareListener)
                        .share();
                break;
            case R.id.view_share_wechat:
                new ShareAction(ClassDetailsActivity.this).setPlatform(SHARE_MEDIA.WEIXIN)
                        .withText("母亲大学堂视频分享")
                        .setCallback(umShareListener)
                        .share();
                break;
            case R.id.view_share_sina:
                new ShareAction(ClassDetailsActivity.this).setPlatform(SHARE_MEDIA.SINA)
                        .withText("母亲大学堂视频分享")
                        .setCallback(umShareListener)
                        .share();
                break;
            case R.id.view_share_space:
                new ShareAction(ClassDetailsActivity.this).setPlatform(SHARE_MEDIA.QZONE)
                        .withText("母亲大学堂视频分享")
                        .setCallback(umShareListener)
                        .share();
                break;
            case R.id.view_share_qq:
                new ShareAction(ClassDetailsActivity.this).setPlatform(SHARE_MEDIA.QQ)
                        .withText("母亲大学堂视频分享")
                        .setCallback(umShareListener)
                        .share();
                break;
            case R.id.view_share_frend:
                Toast.makeText(ClassDetailsActivity.this,"母亲大学堂",Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_videodetails_comment: //评论
                if (uid.equals("")){
                    //未登录  提示登录
                    Intent intent = new Intent(ClassDetailsActivity.this, LoginActivity.class);
                    startActivity(intent);
                }else {
                // 弹出输入法
                InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                // 显示评论框
                ll_persondeatials_line.setVisibility(View.GONE);
                rl_comment.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.hide_down:
                // 隐藏评论框
                ll_persondeatials_line.setVisibility(View.VISIBLE);
                rl_comment.setVisibility(View.GONE);
                // 隐藏输入法，然后暂存当前输入框的内容，方便下次使用
                InputMethodManager im = (InputMethodManager)getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(comment_content.getWindowToken(), 0);
                break;
            case R.id.comment_send:
                sendComment();
                break;
            case R.id.ll_videodetails_zan: //赞
                if (uid.equals("")){
                        //未登录  提示登录
                        Intent intent = new Intent(ClassDetailsActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }else {
                    if (orZan){
                        iv_videodetails_zan.setImageResource(R.mipmap.icon_favour_list);
                        Toast.makeText(ClassDetailsActivity.this,"已赞!",Toast.LENGTH_SHORT).show();
                        postZan();
                        orZan = false;
                    }else {
                        iv_videodetails_zan.setImageResource(R.mipmap.tab_favour);
                        Toast.makeText(ClassDetailsActivity.this,"已取消!",Toast.LENGTH_SHORT).show();
                        canleZan();
                        orZan = true;
                    }
                }
                break;
            case R.id.ll_videodetails_collect:
                if (uid.equals("")){
                    //未登录  提示登录
                    Intent intent = new Intent(ClassDetailsActivity.this, LoginActivity.class);
                    startActivity(intent);
                }else {
                    if (orCollect){
                        iv_videodetails_collect.setImageResource(R.mipmap.tab_collected);
                        Toast.makeText(ClassDetailsActivity.this,"已收藏!",Toast.LENGTH_SHORT).show();
                        postCollect();
                        orCollect = false;
                    }else {
                        iv_videodetails_collect.setImageResource(R.mipmap.tab_collect);
                        Toast.makeText(ClassDetailsActivity.this,"已取消!",Toast.LENGTH_SHORT).show();
                        canleCollect();
                        orCollect = true;
                    }
                }
                break;
        }
    }

    private void canleCollect() {
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.POST_COMMON, RequestMethod.POST);
        request.add("apptoken",apptoken);
        request.add("op","PostUnKeep");
        request.add("btype","12");
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
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.POST_COMMON, RequestMethod.POST);
        request.add("apptoken",apptoken);
        request.add("op","postkeep");
        request.add("btype","12");
        request.add("rid",sid);
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

    private void canleZan() {
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.POST_COMMON, RequestMethod.POST);
        request.add("apptoken",apptoken);
        request.add("op","postUnZLike");
        request.add("btype","12");
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
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.POST_COMMON, RequestMethod.POST);
        request.add("apptoken",apptoken);
        request.add("op","postZLike");
        request.add("btype","12");
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

    private void sendComment() {
        String self_comment = comment_content.getText().toString().trim();
        if (TextUtils.isEmpty(self_comment)){
            Toast.makeText(getApplicationContext(), "评论不能为空！", Toast.LENGTH_SHORT).show();
        }else {
            postComment(self_comment);
        }
    }

    private ProgressDialog pd;
    private void postComment(final String self_comment) {
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.POST_COMMON, RequestMethod.POST);
        request.add("apptoken",apptoken);
        request.add("op","PostReview");
        request.add("btype","12");
        request.add("rid",sid);
        request.add("uid",uid);
        request.add("rcontent",self_comment);
        requestQueue.add(POST_SELF_COMMENT, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
                pd = new ProgressDialog(ClassDetailsActivity.this);
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
                        ll_persondeatials_line.setVisibility(View.VISIBLE);
                        rl_comment.setVisibility(View.GONE);
                        // 隐藏输入法，然后暂存当前输入框的内容，方便下次使用
                        InputMethodManager im = (InputMethodManager)getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        im.hideSoftInputFromWindow(comment_content.getWindowToken(), 0);
                        Toast.makeText(ClassDetailsActivity.this,"评论成功!",Toast.LENGTH_SHORT).show();
                        rb_frg_comment.setChecked(true);
                    }else {
                        pd.dismiss();
                        Toast.makeText(ClassDetailsActivity.this,"评论失败，请稍候重试!",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
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


    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat","platform"+platform);
            Toast.makeText(ClassDetailsActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(ClassDetailsActivity.this,platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if(t!=null){
                Log.d("throw","throw:"+t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(ClassDetailsActivity.this,platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        if (player != null) {
            player.onPause();
        }
        /**demo的内容，恢复系统其它媒体的状态*/
        MediaUtils.muteAudioFocus(ClassDetailsActivity.this, true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (player != null) {
            player.onResume();
        }
        /**demo的内容，暂停系统其它媒体的状态*/
        MediaUtils.muteAudioFocus(ClassDetailsActivity.this, false);
        /**demo的内容，激活设备常亮状态*/
        if (wakeLock != null) {
            wakeLock.acquire();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.onDestroy();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (player != null) {
            player.onConfigurationChanged(newConfig);
        }
    }

    @Override
    public void onBackPressed() {
        if (player != null && player.onBackPressed()) {
            return;
        }
        super.onBackPressed();
        /**demo的内容，恢复设备亮度状态*/
        if (wakeLock != null) {
            wakeLock.release();
        }
    }


}
