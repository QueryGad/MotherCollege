package com.player.mothercollege.me;

import android.app.ProgressDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.gson.Gson;
import com.player.mothercollege.R;
import com.player.mothercollege.activity.BaseActivity;
import com.player.mothercollege.adapter.PersonAdapter;
import com.player.mothercollege.bean.PersonDynamicBean;
import com.player.mothercollege.bean.PersonHead;
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

    @Override
    public void setContentView() {
        setContentView(R.layout.act_me_headicon);
        requestQueue = NoHttp.newRequestQueue();
    }

    @Override
    public void initViews() {
        iv_refresh = (ImageView) findViewById(R.id.iv_refresh);
        btn_refrsh = (Button) findViewById(R.id.btn_refrsh);
        btn_back = (Button) findViewById(R.id.btn_back);
        lv_headicon = (ListView) findViewById(R.id.lv_headicon);

    }

    @Override
    public void initListeners() {
        btn_back.setOnClickListener(this);
    }

    @Override
    public void initData() {
        netWork1();//个人信息
        netWork2();//帖子
    }

    private void netWork2() {
        String apptoken = PrefUtils.getString(HeadIconActivity.this, "apptoken", "");
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.ME_URL, RequestMethod.GET);
        request.add("op","tiezilist");
        request.add("uid","null");
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
        String apptoken = PrefUtils.getString(HeadIconActivity.this, "apptoken", "");
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.ME_URL, RequestMethod.GET);
        request.add("op","userinfo");
        request.add("uid","null");
        request.add("apptoken",apptoken);
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
        PersonAdapter adapter = new PersonAdapter(HeadIconActivity.this,trendsList);
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
        String uidID = personHeadBean.getUid();//用户ID
        int followCount = personHeadBean.getFollowCount();//关注人数
        int fansCount = personHeadBean.getFansCount();//粉丝人数
        int sex = personHeadBean.getSex();//性别
        String phone = personHeadBean.getPhone();//手机号
        boolean isShowPhone = personHeadBean.isIsShowPhone();//是否显示手机号
        String autograph = personHeadBean.getAutograph();//个性签名

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
        tv_person_id.setText("ID:"+uidID);
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
