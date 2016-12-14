package com.player.mothercollege.college.details;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import com.google.gson.Gson;
import com.player.mothercollege.R;
import com.player.mothercollege.activity.BaseActivity;
import com.player.mothercollege.bean.ClassDetailsBean;
import com.player.mothercollege.utils.ConfigUtils;
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
    private RequestQueue requestQueue;
    private Button btn_back;
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
    private String sid;
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

    @Override
    public void setContentView() {
        setContentView(R.layout.act_college_bzzbdetails);
        requestQueue = NoHttp.newRequestQueue();
    }

    @Override
    public void initViews() {
        sid = getIntent().getStringExtra("sid");
        btn_back = (Button) findViewById(R.id.btn_back);
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
        //设置默认
        getSupportFragmentManager().beginTransaction().add(R.id.fl_video,classDetails).commit();
        rb_frg_details.setChecked(true);
    }

    @Override
    public void initListeners() {
        btn_back.setOnClickListener(this);
        rg_video.setOnCheckedChangeListener(ClassDeatilsListener);
        ll_videodetails_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShareDialog();
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
        String apptoken = PrefUtils.getString(this, "apptoken", "");
        String uid = PrefUtils.getString(ClassDetailsActivity.this, "uid", "null");
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
    }

    private boolean orZan=true;
    private boolean orCollect = true;

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                finish();
                break;
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

                // 弹出输入法
                InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                // 显示评论框
                ll_persondeatials_line.setVisibility(View.GONE);
                rl_comment.setVisibility(View.VISIBLE);
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
                break;
            case R.id.ll_videodetails_collect:
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
                break;
        }
    }

    private void canleCollect() {
        String apptoken = PrefUtils.getString(ClassDetailsActivity.this, "apptoken", "");
        String uid = PrefUtils.getString(ClassDetailsActivity.this, "uid", "null");
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
        String apptoken = PrefUtils.getString(ClassDetailsActivity.this, "apptoken", "");
        String uid = PrefUtils.getString(ClassDetailsActivity.this, "uid", "null");
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
        String apptoken = PrefUtils.getString(ClassDetailsActivity.this, "apptoken", "");
        String uid = PrefUtils.getString(ClassDetailsActivity.this, "uid", "null");
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
        String apptoken = PrefUtils.getString(ClassDetailsActivity.this, "apptoken", "");
        String uid = PrefUtils.getString(ClassDetailsActivity.this, "uid", "null");
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
        String apptoken = PrefUtils.getString(ClassDetailsActivity.this, "apptoken", "");
        String uid = PrefUtils.getString(ClassDetailsActivity.this, "uid", "null");
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
}
