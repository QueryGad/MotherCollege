package com.player.mothercollege.me;

import android.app.ProgressDialog;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
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
import com.player.mothercollege.adapter.PersonAdapter;
import com.player.mothercollege.bean.PersonDynamicBean;
import com.player.mothercollege.bean.PersonHead;
import com.player.mothercollege.login.LoginActivity;
import com.player.mothercollege.utils.ConfigUtils;
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
 * Created by Administrator on 2016/10/25.
 * 个人主页
 */
public class HeadIconActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_refresh;
    private Button btn_refrsh,btn_back;
    private static final int GET_HEADICON_DATA = 001;
    private static final int GET_DYNAMIC_DATA = 002;
    private ListView lv_headicon;
    private RequestManager glideRequest;
    private RequestQueue requestQueue;
    private View personHeadView;
    private ProgressDialog pd;
    private TextView tv_otherperson_guanzhu,tv_otherperson_chat;
    private LinearLayout ll_other_zhuanchat;
    private String toUid;
    private String apptoken;
    private String uid;

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
        lv_headicon = (ListView) findViewById(R.id.lv_headicon);
        tv_otherperson_guanzhu = (TextView) findViewById(R.id.tv_otherperson_guanzhu);
        tv_otherperson_chat = (TextView) findViewById(R.id.tv_otherperson_chat);
        ll_other_zhuanchat = (LinearLayout) findViewById(R.id.ll_other_zhuanchat);
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
        request.add("lastindex","0");
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
        List<PersonDynamicBean.TrendsBean> trendsList = personDynamicBean.getTrends();
        PersonAdapter adapter = new PersonAdapter(HeadIconActivity.this,trendsList,uid);
        lv_headicon.setAdapter(adapter);
        lv_headicon.addHeaderView(personHeadView);
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
        String niceName = personHeadBean.getNiceName();//昵称
        boolean isVip = personHeadBean.isIsVip();//是否VIP
        String guid = personHeadBean.getGuid();
        int followCount = personHeadBean.getFollowCount();//关注人数
        int fansCount = personHeadBean.getFansCount();//粉丝人数
        int sex = personHeadBean.getSex();//性别
        String phone = personHeadBean.getPhone();//手机号
        boolean isShowPhone = personHeadBean.isIsShowPhone();//是否显示手机号
        String autograph = personHeadBean.getAutograph();//个性签名
        boolean isSelf = personHeadBean.isIsSelf();//是否是用户本人
        if (isSelf){
           //是用户本人 隐藏关注、聊天两个按钮
            ll_other_zhuanchat.setVisibility(View.GONE);
        }else {
            ll_other_zhuanchat.setVisibility(View.VISIBLE);
            tv_otherperson_guanzhu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (uid.equals("")){
                        //未登录  提示登录
                        Intent intent = new Intent(HeadIconActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(HeadIconActivity.this,"关注",Toast.LENGTH_SHORT).show();
                    }
                }
            });
            tv_otherperson_chat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (uid.equals("")){
                        //未登录  提示登录
                        Intent intent = new Intent(HeadIconActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(HeadIconActivity.this,"聊天",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        //赋值
        glideRequest = Glide.with(this);
        glideRequest.load(uicon).transform(
                new GlideCircleTransform(HeadIconActivity.this)).into(iv_person_icon);
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
            tv_person_phone.setText("未公开");
        }else {
            tv_person_phone.setText(phone);
        }
        tv_person_style.setText(autograph);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                finish();
                break;
        }
    }
}
