package com.player.mothercollege.college.details;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
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
import com.player.mothercollege.adapter.OriginalReveiwAdapter;
import com.player.mothercollege.bean.ReadBookDetailsBean;
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
 * Created by Administrator on 2016/11/2.
 */
public class ReadBookDetailsActivity extends BaseActivity implements View.OnClickListener {

    private static final int GET_TEXTDETAILS_DATA = 001;
    private static final int POST_ZAN = 002;
    private static final int POST_CANLE_ZAN = 003;
    private static final int POST_COLLECT = 004;
    private static final int POST_CANLE_COLLECT = 005;
    private static final int POST_SELF_COMMENT = 006;
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

    private TextView hide_down;
    private EditText comment_content;
    private Button comment_send;
    private RelativeLayout rl_comment;
    private LinearLayout ll_persondeatials_line;
    private OriginalReveiwAdapter adapter;
    private List<ReadBookDetailsBean.ReveiwBean> reveiwList;
    private ImageView iv_person_zan;
    private TextDetailsAdapter textAdapter;
    private List<ReadBookDetailsBean.ZlistBean> zlistList;
    private String uid;
    private String apptoken;

    @Override
    public void setContentView() {
       setContentView(R.layout.act_college_textdetails);
        requestQueue = NoHttp.newRequestQueue();
        getWindow().setFormat(PixelFormat.TRANSLUCENT);//（这个对宿主没什么影响，建议声明）
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE|WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
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

        hide_down = (TextView) findViewById(R.id.hide_down);
        comment_content = (EditText) findViewById(R.id.comment_content);
        comment_send = (Button) findViewById(R.id.comment_send);
        rl_comment = (RelativeLayout) findViewById(R.id.rl_comment);
        ll_persondeatials_line = (LinearLayout) findViewById(R.id.ll_persondeatials_line);

        tv_details_title.setText("详情");
    }

    @Override
    public void initListeners() {
        btn_back.setOnClickListener(this);
        ll_textdeatials_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断是否登录

                if (uid.equals("")){
                    //未登录  提示登录
                    Intent intent = new Intent(ReadBookDetailsActivity.this, LoginActivity.class);
                    startActivity(intent);

                }else {
                    showShareDialog();
                }

            }
        });
        ll_textdeatials_comment.setOnClickListener(this);
        ll_textdeatials_zan.setOnClickListener(this);
        ll_textdeatials_collect.setOnClickListener(this);
        hide_down.setOnClickListener(this);
        comment_send.setOnClickListener(this);
    }



    @Override
    public void initData() {
        netWork();
    }

    private void netWork() {
        apptoken = PrefUtils.getString(ReadBookDetailsActivity.this, "apptoken", "");
        uid = PrefUtils.getString(ReadBookDetailsActivity.this, "uid", "");
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.COLLEGE_URL, RequestMethod.GET);
        request.add("apptoken", apptoken);
        request.add("op","getStuInfo");
        request.add("uid",uid);
        request.add("stype","1");
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
        adapter = new OriginalReveiwAdapter(ReadBookDetailsActivity.this,readBookDetailsBean.getReveiw());
        lv_details.setAdapter(adapter);
    }

    private void initHead() {
        View view = View.inflate(ReadBookDetailsActivity.this,R.layout.head_college_original,null);
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
        reveiwList = readBookDetailsBean.getReveiw();
        zlistList = readBookDetailsBean.getZlist();
        content = readBookDetailsBean.getContent();
        initH5(content);

        int length = DensityUtils.dip2px(ReadBookDetailsActivity.this,15);
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

        textAdapter = new TextDetailsAdapter();
        gr_textdetails_head.setAdapter(textAdapter);

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

    private Dialog dialog;
    private boolean orZan=true;
    private boolean orCollect = true;
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                finish();
                break;
            case R.id.ll_persondeatials_comment://评论
                //判断是否登录
                if (uid.equals("")){
                    //未登录  提示登录
                    Intent intent = new Intent(ReadBookDetailsActivity.this, LoginActivity.class);
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
            case R.id.ll_persondeatials_zan:
                if (uid.equals("")){
                    //未登录  提示登录
                    Intent intent = new Intent(ReadBookDetailsActivity.this, LoginActivity.class);
                    startActivity(intent);
                }else {
                    if (orZan){
                        iv_persondeatials_zan.setImageResource(R.mipmap.icon_favour_list);
                        Toast.makeText(ReadBookDetailsActivity.this,"已赞!",Toast.LENGTH_SHORT).show();

                        String uicon = PrefUtils.getString(ReadBookDetailsActivity.this, "uicon", "");
                        MyLog.testLog("我的头像:"+uicon);
                        ReadBookDetailsBean.ZlistBean bean = new ReadBookDetailsBean.ZlistBean();
                        bean.setUicon(uicon);
                        zlistList.add(bean);
//                    glideRequest = Glide.with(ReadBookDetailsActivity.this);
//                    glideRequest.load(uicon)
//                            .transform(new GlideCircleTransform(ReadBookDetailsActivity.this)).into(iv_person_zan);
                        textAdapter.notifyDataSetChanged();
                        postZan();
                        orZan = false;
                    }else {
                        iv_persondeatials_zan.setImageResource(R.mipmap.tab_favour);
                        Toast.makeText(ReadBookDetailsActivity.this,"已取消!",Toast.LENGTH_SHORT).show();
                        canleZan();
                        orZan = true;
                    }
                    adapter.notifyDataSetChanged();
                }

                break;
            case R.id.ll_persondeatials_collect:
                if (uid.equals("")){
                    //未登录  提示登录
                    Intent intent = new Intent(ReadBookDetailsActivity.this, LoginActivity.class);
                    startActivity(intent);
                }else {
                    if (orCollect){
                        iv_persondeatials_collect.setImageResource(R.mipmap.tab_collected);
                        Toast.makeText(ReadBookDetailsActivity.this,"已收藏!",Toast.LENGTH_SHORT).show();
                        postCollect();
                        orCollect = false;
                    }else {
                        iv_persondeatials_collect.setImageResource(R.mipmap.tab_collect);
                        Toast.makeText(ReadBookDetailsActivity.this,"已取消!",Toast.LENGTH_SHORT).show();
                        canleCollect();
                        orCollect = true;
                    }
                }

                break;
            case R.id.view_share_pengyou:
                new ShareAction(ReadBookDetailsActivity.this).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                        .withTargetUrl(content)
                        .setCallback(umShareListener)
                        .share();
                break;
            case R.id.view_share_wechat:
                new ShareAction(ReadBookDetailsActivity.this).setPlatform(SHARE_MEDIA.WEIXIN)
                        .withTargetUrl(content)
                        .setCallback(umShareListener)
                        .share();
                break;
            case R.id.view_share_sina:
                new ShareAction(ReadBookDetailsActivity.this).setPlatform(SHARE_MEDIA.SINA)
                        .withTargetUrl(content)
                        .setCallback(umShareListener)
                        .share();
                break;
            case R.id.view_share_space:
                new ShareAction(ReadBookDetailsActivity.this).setPlatform(SHARE_MEDIA.QZONE)
                        .withTargetUrl(content)
                        .setCallback(umShareListener)
                        .share();
                break;
            case R.id.view_share_qq:
                new ShareAction(ReadBookDetailsActivity.this).setPlatform(SHARE_MEDIA.QQ)
                        .withTargetUrl(content)
                        .setCallback(umShareListener)
                        .share();
                break;
            case R.id.view_share_frend:
                Toast.makeText(ReadBookDetailsActivity.this,"母亲大学堂",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_canle:
                dialog.dismiss();
                break;
        }
//        dialog.dismiss();
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
        request.add("btype","13");
        request.add("rid",sid);
        request.add("uid",uid);
        request.add("rcontent",self_comment);
        requestQueue.add(POST_SELF_COMMENT, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
                pd = new ProgressDialog(ReadBookDetailsActivity.this);
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
                        Toast.makeText(ReadBookDetailsActivity.this,"评论成功!",Toast.LENGTH_SHORT).show();
                        ReadBookDetailsBean.ReveiwBean bean = new ReadBookDetailsBean.ReveiwBean();
                        String username = PrefUtils.getString(ReadBookDetailsActivity.this, "username", "");
                        MyLog.testLog("我拿到你的名字了:"+username);
                        bean.setUnicename(username);
                        bean.setContent(self_comment);
                        reveiwList.add(bean);
                        adapter.notifyDataSetChanged();
                        lv_details.setSelection(lv_details.getBottom());
                    }else {
                        pd.dismiss();
                        Toast.makeText(ReadBookDetailsActivity.this,"评论失败，请稍候重试!",Toast.LENGTH_SHORT).show();
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
        request.add("btype","13");
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
        request.add("btype","13");
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
        request.add("btype","13");
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
        request.add("btype","13");
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

            Toast.makeText(ReadBookDetailsActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(ReadBookDetailsActivity.this,platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if(t!=null){
                Log.d("throw","throw:"+t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(ReadBookDetailsActivity.this,platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };

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
            View view = View.inflate(ReadBookDetailsActivity.this, R.layout.item_details_personzan,null);
            iv_person_zan = (ImageView) view.findViewById(R.id.iv_person_zan);
            String uicon = readBookDetailsBean.getZlist().get(position).getUicon();
            if (uicon==null){
                iv_person_zan.setImageResource(R.mipmap.head_me_nor);
            }else {
                glideRequest = Glide.with(ReadBookDetailsActivity.this);
                glideRequest.load(uicon)
                        .transform(new GlideCircleTransform(ReadBookDetailsActivity.this)).into(iv_person_zan);
            }

            iv_person_zan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //点击头像进入他人主页
                    Intent intent = new Intent(ReadBookDetailsActivity.this, HeadIconActivity.class);

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
            View view = View.inflate(ReadBookDetailsActivity.this,R.layout.item_persondetails_comment,null);
            TextView tv_person_othername = (TextView) view.findViewById(R.id.tv_person_othername);
            TextView tv_person_othercontent = (TextView) view.findViewById(R.id.tv_person_othercontent);
            tv_person_othername.setText(readBookDetailsBean.getReveiw().get(position).getUnicename()+":");
            tv_person_othercontent.setText(readBookDetailsBean.getReveiw().get(position).getContent());
            return view;
        }
    }

    @Override
    protected void onDestroy() {
        web_textdetails.destroy();
        super.onDestroy();
    }
}
