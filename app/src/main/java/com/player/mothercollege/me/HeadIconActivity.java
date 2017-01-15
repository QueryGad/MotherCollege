package com.player.mothercollege.me;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.gson.Gson;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.exceptions.HyphenateException;
import com.player.mothercollege.R;
import com.player.mothercollege.activity.BaseActivity;
import com.player.mothercollege.adapter.PersonAdapter;
import com.player.mothercollege.bean.PersonDynamicBean;
import com.player.mothercollege.bean.PersonHead;
import com.player.mothercollege.unity.details.ChatActivity;
import com.player.mothercollege.utils.ConfigUtils;
import com.player.mothercollege.utils.MyLog;
import com.player.mothercollege.utils.PrefUtils;
import com.player.mothercollege.view.GlideCircleTransform;
import com.player.mothercollege.view.MyUpMoreListview;
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
 * Created by Administrator on 2016/10/25.
 * 个人主页
 */
public class HeadIconActivity extends BaseActivity implements View.OnClickListener,MyUpMoreListview.OnRefreshListener {

    private static final int POST_GUANZHU_DATA = 003;
    private static final int POST_CANLEGUANZHU_DATA = 004;
    private static final int GET_MORE_DATA = 005;
    private ImageView iv_refresh;
    private Button btn_refrsh,btn_back;
    private static final int GET_HEADICON_DATA = 001;
    private static final int GET_DYNAMIC_DATA = 002;
    private MyUpMoreListview lv_headicon;
    private RequestManager glideRequest;
    private RequestQueue requestQueue;
    private View personHeadView;
    private ProgressDialog pd;
    private TextView tv_otherperson_guanzhu,tv_otherperson_chat;
    private LinearLayout ll_other_zhuanchat;
    private String toUid;
    private String apptoken;
    private String uid;
    private boolean isGuan = false;

    int lastIndex=0;
    boolean isRefresh = true;
    private int endNo;
    private List<PersonDynamicBean.TrendsBean> trendsList;
    private PersonAdapter adapter;
    private String snsUid;
    private boolean isFollow;
    private String niceName;

    @Override
    public void setContentView() {
        setContentView(R.layout.act_me_headicon);
        requestQueue = NoHttp.newRequestQueue();
    }

    @Override
    public void initViews() {

        toUid = getIntent().getStringExtra("toUid");


        iv_refresh = (ImageView) findViewById(R.id.iv_refresh);
        btn_refrsh = (Button) findViewById(R.id.btn_refrsh);
        btn_back = (Button) findViewById(R.id.btn_back);
        lv_headicon = (MyUpMoreListview) findViewById(R.id.lv_headicon);
        tv_otherperson_guanzhu = (TextView) findViewById(R.id.tv_otherperson_guanzhu);
        tv_otherperson_chat = (TextView) findViewById(R.id.tv_otherperson_chat);
        ll_other_zhuanchat = (LinearLayout) findViewById(R.id.ll_other_zhuanchat);

        lv_headicon.setOnRefreshListener(this);


    }

    @Override
    public void initListeners() {
        btn_back.setOnClickListener(this);
    }

    @Override
    public void initData() {
        netWork1();//个人信息
    }

    private void netWork2() {
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.ME_URL, RequestMethod.GET);
        request.add("op","tiezilist");
        request.add("uid",toUid);
        request.add("apptoken",apptoken);
        request.add("lastIndex",lastIndex+"");
        requestQueue.add(GET_DYNAMIC_DATA, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
                pd = new ProgressDialog(HeadIconActivity.this);
                pd.show();
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                MyLog.testLog("帖子详情:"+info);
                parseJson2(info);
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }

            @Override
            public void onFinish(int what) {
                pd.dismiss();
            }
        });
    }

    private void netWork1() {
        apptoken = PrefUtils.getString(HeadIconActivity.this, "apptoken", "");
        uid = PrefUtils.getString(HeadIconActivity.this, "uid", "");
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.ME_URL, RequestMethod.GET);
        request.add("op","userinfo");
        request.add("uid", uid);
        request.add("toUid",toUid);
        request.add("apptoken", apptoken);
        requestQueue.add(GET_HEADICON_DATA, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                iv_refresh.setVisibility(View.GONE);
                btn_refrsh.setVisibility(View.GONE);
                String info = response.get();
                MyLog.testLog("个人主页:"+info);
                parseJson1(info);
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    private void parseJson1(String info){
        Gson gson = new Gson();
        PersonHead personHeadBean = gson.fromJson(info, PersonHead.class);
        initHead(personHeadBean);
        lv_headicon.addHeaderView(personHeadView);
        netWork2();//帖子
    }

    private void parseJson2(String info){
        Gson gson = new Gson();
        PersonDynamicBean personDynamicBean = gson.fromJson(info, PersonDynamicBean.class);
        initDynamic(personDynamicBean);
    }


    /**
     * 我的动态
     */
    private void initDynamic(PersonDynamicBean personDynamicBean) {

        if (personDynamicBean!=null){
            endNo = personDynamicBean.getLastIndex();//目标索引
            infos = personDynamicBean.getTrends();
            adapter = new PersonAdapter(this,infos,uid);
            lv_headicon.setAdapter(adapter);

        }

    }

    /**
     * 头布局
     */
    private void initHead(PersonHead personHeadBean) {
        personHeadView = View.inflate(HeadIconActivity.this, R.layout.item_me_pseron_head,null);
        ImageView iv_person_icon = (ImageView) personHeadView.findViewById(R.id.iv_person_icon);
        TextView tv_person_name = (TextView) personHeadView.findViewById(R.id.tv_person_name);
        ImageView iv_isVip = (ImageView) personHeadView.findViewById(R.id.iv_isVip);
        TextView tv_person_id = (TextView) personHeadView.findViewById(R.id.tv_person_id);
        TextView tv_person_guanzhu = (TextView) personHeadView.findViewById(R.id.tv_person_guanzhu);
        TextView tv_person_fans = (TextView) personHeadView.findViewById(R.id.tv_person_fans);
        TextView tv_person_sex = (TextView) personHeadView.findViewById(R.id.tv_person_sex);
        TextView tv_person_phone = (TextView) personHeadView.findViewById(R.id.tv_person_phone);
        TextView tv_person_style = (TextView) personHeadView.findViewById(R.id.tv_person_style);

        String uicon = personHeadBean.getUicon(); //头像
        //昵称
        niceName = personHeadBean.getNiceName();
        boolean isVip = personHeadBean.isIsVip();//是否VIP
        String guid = personHeadBean.getGuid();
        int followCount = personHeadBean.getFollowCount();//关注人数
        int fansCount = personHeadBean.getFansCount();//粉丝人数
        int sex = personHeadBean.getSex();//性别
        String phone = personHeadBean.getPhone();//手机号
        boolean isShowPhone = personHeadBean.isIsShowPhone();//是否显示手机号
        String autograph = personHeadBean.getAutograph();//个性签名
        //环信账号
        snsUid = personHeadBean.getSnsUid();
        isFollow = personHeadBean.isIsFollow();
        boolean isSelf = personHeadBean.isIsSelf();//是否是用户本人
        if (isSelf) {
            //是用户本人 隐藏关注、聊天两个按钮
            ll_other_zhuanchat.setVisibility(View.GONE);
        }else {
            //非本人
            ll_other_zhuanchat.setVisibility(View.VISIBLE);
            if (isFollow){
                //已关注
                tv_otherperson_guanzhu.setText("已关注");
                isGuan = true;
            }else {
                //未关注
                tv_otherperson_guanzhu.setText("关注");
                isGuan = false;
            }
            tv_otherperson_guanzhu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isGuan){
                        //点击未关注
                        initCanleGuanZhu(niceName);
                        isGuan = false;
                    }else {
                        //点击关注
                        initGuanZhu(niceName);
                        isGuan = true;
                    }
                }
            });
            tv_otherperson_chat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isFollow){
                        //聊天
                        Intent intent = new Intent(HeadIconActivity.this, ChatActivity.class);
                        intent.putExtra(EaseConstant.EXTRA_USER_ID,snsUid);
                        intent.putExtra("chatType",EaseConstant.CHATTYPE_SINGLE);
                        intent.putExtra("niceName",niceName);
                        startActivity(intent);
                    }else {
                        Toast.makeText(HeadIconActivity.this,"必须关注后才可以聊天哦!",Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }


        //赋值
        if (uicon==null){
            iv_person_icon.setImageResource(R.mipmap.head_homepage_others);
            iv_person_icon.setBackgroundResource(R.mipmap.head_me_nor);
        }else {
            glideRequest = Glide.with(this);
            glideRequest.load(uicon).transform(
                    new GlideCircleTransform(HeadIconActivity.this)).into(iv_person_icon);
        }

        tv_person_name.setText(niceName);
        if (isVip){
            iv_isVip.setVisibility(View.VISIBLE);
        }else {
            iv_isVip.setVisibility(View.GONE);
        }
        tv_person_id.setText("ID:"+guid);
        tv_person_guanzhu.setText("关注"+followCount);
        tv_person_fans.setText("粉丝"+fansCount);
        if (sex==0){
            tv_person_sex.setText("女");
        }else if (sex==1){
            tv_person_sex.setText("男");
        }else {
            tv_person_sex.setText("未公开");
        }
        if (isShowPhone){
            tv_person_phone.setText(phone);

        }else {
            tv_person_phone.setText("未公开");
        }
        tv_person_style.setText(autograph);
    }

    private void initCanleGuanZhu(final String niceName) {
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.ME_URL, RequestMethod.POST);
        request.add("apptoken",apptoken);
        request.add("op","tofollow");
        request.add("optype","unfollow");
        request.add("uid",uid);
        request.add("touid",toUid);
        requestQueue.add(POST_CANLEGUANZHU_DATA, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                try {
                    JSONObject json = new JSONObject(info);
                    boolean isSuccess = json.getBoolean("isSuccess");
                    if (isSuccess){
                        Toast.makeText(HeadIconActivity.this,"取消关注成功",Toast.LENGTH_SHORT).show();
                        tv_otherperson_chat.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //聊天
                                Toast.makeText(HeadIconActivity.this,"必须关注后才可以聊天哦!",Toast.LENGTH_SHORT).show();
                            }
                        });
                        tv_otherperson_guanzhu.setText("关注");
                        try {
//                            EMClient.getInstance().contactManager().addContact(niceName, "请求添加为好友");
                            EMClient.getInstance().contactManager().deleteContact(niceName);
                        } catch (HyphenateException e) {
                            e.printStackTrace();
                        }
//                        tv_otherperson_guanzhu.setVisibility(View.GONE);
                    }else {
                        Toast.makeText(HeadIconActivity.this,"关注失败",Toast.LENGTH_SHORT).show();
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

    private void initGuanZhu(final String name) {
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.ME_URL, RequestMethod.POST);
        request.add("apptoken",apptoken);
        request.add("op","tofollow");
        request.add("optype","follow");
        request.add("uid",uid);
        request.add("touid",toUid);
        requestQueue.add(POST_GUANZHU_DATA, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                try {
                    JSONObject json = new JSONObject(info);
                    boolean isSuccess = json.getBoolean("isSuccess");
                    if (isSuccess){
                        Toast.makeText(HeadIconActivity.this,"关注成功",Toast.LENGTH_SHORT).show();
                        tv_otherperson_guanzhu.setText("已关注");
                        tv_otherperson_chat.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //聊天
                                Intent intent = new Intent(HeadIconActivity.this, ChatActivity.class);
                                intent.putExtra(EaseConstant.EXTRA_USER_ID,snsUid);
                                intent.putExtra("chatType",EaseConstant.CHATTYPE_SINGLE);
                                intent.putExtra("niceName",niceName);
                                startActivity(intent);
                            }
                        });
                        try {
                            EMClient.getInstance().contactManager().addContact(name, "请求添加为好友");
                        } catch (HyphenateException e) {
                            e.printStackTrace();
                        }
//                        tv_otherperson_guanzhu.setVisibility(View.GONE);
                    }else {
                        Toast.makeText(HeadIconActivity.this,"关注失败",Toast.LENGTH_SHORT).show();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                finish();
                break;
        }
    }

    @Override
    public void onLoadingMore() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                SystemClock.sleep(500);
                setAddMoreData();
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                lv_headicon.hideFooterView();
            }
        }.execute();
    }

    private void setAddMoreData() {
        lastIndex = endNo;
        sendAddHomeLvRequest();
    }

    private List<PersonDynamicBean.TrendsBean> infos = new ArrayList<>();
    private void sendAddHomeLvRequest() {
            Request<String> request = NoHttp.createStringRequest(ConfigUtils.ME_URL, RequestMethod.GET);
            request.add("op","tiezilist");
            request.add("uid",toUid);
            request.add("apptoken",apptoken);
            request.add("lastIndex",lastIndex+"");
        requestQueue.add(GET_MORE_DATA, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                Gson gson = new Gson();
                PersonDynamicBean personDynamicBean = gson.fromJson(info, PersonDynamicBean.class);
                if (lastIndex!=0) {
                    endNo = personDynamicBean.getLastIndex();
                    lastIndex = endNo;
                    trendsList = personDynamicBean.getTrends();
                    infos.addAll(trendsList);
                    adapter.notifyDataSetChanged();
                } else {
//                    toast("没有更多数据");
                    Toast.makeText(HeadIconActivity.this,"没有更多数据了",Toast.LENGTH_SHORT).show();
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
}
