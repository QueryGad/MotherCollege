package com.player.mothercollege.me.details;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.gson.Gson;
import com.player.mothercollege.R;
import com.player.mothercollege.activity.BaseActivity;
import com.player.mothercollege.adapter.CirleArticleAdapter;
import com.player.mothercollege.bean.CirleArticleBean;
import com.player.mothercollege.bean.CirleNameDetailsBean;
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
 * Created by Administrator on 2016/11/3.
 * 圈子详情
 */
public class CirleDetailsActivity extends BaseActivity implements View.OnClickListener {

    private static final int GET_CIRLEDETAILS_DATA = 001;
    private static final int GET_CIRLEDETAILSTITLE_DATA = 002;
    private static final int POST_CANLEGUANZHU_DATA = 003;
    private static final int POST_ADDGUANZHU_DATA = 004;
    private Button btn_back;
    private TextView tv_details_title;
    private ListView lv_cirledetails;
    private RequestQueue requestQueue;
    private String groupId;
    private RequestManager glideRequest;
    private ImageView iv_cirle_edit;
    private ProgressDialog pd;

    @Override
    public void setContentView() {
        setContentView(R.layout.act_me_cirledetails);
        requestQueue = NoHttp.newRequestQueue();
    }

    @Override
    public void initViews() {

        groupId = getIntent().getStringExtra("groupId");

        btn_back = (Button) findViewById(R.id.btn_back);
        tv_details_title = (TextView) findViewById(R.id.tv_details_title);
        lv_cirledetails = (ListView) findViewById(R.id.lv_cirledetails);
        iv_cirle_edit = (ImageView) findViewById(R.id.iv_cirle_edit);
    }

    @Override
    public void initListeners() {
        btn_back.setOnClickListener(this);
        iv_cirle_edit.setOnClickListener(this);
    }

    @Override
    public void initData() {
        netWorkTitle();
    }

    private void netWorkTitle() {
        String apptoken = PrefUtils.getString(this, "apptoken", "");
        String uid = PrefUtils.getString(this, "uid", "");
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.ME_URL, RequestMethod.GET);
        request.add("apptoken",apptoken);
        request.add("uid",uid);
        request.add("groupNo",groupId);
        request.add("op","GroupInfo");
        requestQueue.add(GET_CIRLEDETAILSTITLE_DATA, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                MyLog.testLog("圈子详情:"+info);
                parseJsonTitle(info);
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    private void parseJsonTitle(String info){
        Gson gson = new Gson();
        CirleNameDetailsBean cirleNameDetailsBean = gson.fromJson(info, CirleNameDetailsBean.class);
        initHead(cirleNameDetailsBean);
        netWorkDetails();
    }

    private void netWorkDetails() {
        String apptoken = PrefUtils.getString(this, "apptoken", "");
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.ME_URL, RequestMethod.GET);
        request.add("apptoken",apptoken);
        request.add("lastIndex","0");
        request.add("groupNo",groupId);
        request.add("op","GroupTrends");
        requestQueue.add(GET_CIRLEDETAILS_DATA, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                MyLog.testLog("圈子贴详情:"+info);
                parseJsonDetails(info);
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    private void parseJsonDetails(String info){
        Gson gson = new Gson();
        CirleArticleBean cirleArticleBean = gson.fromJson(info, CirleArticleBean.class);
        int lastIndex = cirleArticleBean.getLastIndex();
        List<CirleArticleBean.TrendsBean> trendsList = cirleArticleBean.getTrends();
        CirleArticleAdapter adapter = new CirleArticleAdapter(CirleDetailsActivity.this,trendsList);
        lv_cirledetails.setAdapter(adapter);
    }

    private void initHead(final CirleNameDetailsBean cirleNameDetailsBean) {
        View viewHead = View.inflate(CirleDetailsActivity.this,R.layout.head_me_cirledetails_name,null);
        ImageView iv_cirledetails_head = (ImageView) viewHead.findViewById(R.id.iv_cirledetails_head);
        final ImageView iv_cirle_hasjoin = (ImageView) viewHead.findViewById(R.id.iv_cirle_hasjoin);
        TextView tv_cirledetails_join = (TextView) viewHead.findViewById(R.id.tv_cirledetails_join);
        TextView tv_cirledetails_trend = (TextView) viewHead.findViewById(R.id.tv_cirledetails_trend);
        TextView tv_cirledetails_introduce = (TextView) viewHead.findViewById(R.id.tv_cirledetails_introduce);
        glideRequest = Glide.with(CirleDetailsActivity.this);
        glideRequest.load(cirleNameDetailsBean.getIcon())
                .transform(new GlideCircleTransform(CirleDetailsActivity.this)).into(iv_cirledetails_head);
        tv_cirledetails_join.setText(cirleNameDetailsBean.getJoinCount()+"");
        tv_cirledetails_trend.setText(cirleNameDetailsBean.getTrendCount()+"");
        tv_details_title.setText(cirleNameDetailsBean.getGroupName());
        boolean hasJoin = cirleNameDetailsBean.isHasJoin();
        final String groupId = cirleNameDetailsBean.getGroupId();
        if (hasJoin){
            //显示编辑按钮
            iv_cirle_edit.setVisibility(View.VISIBLE);
            iv_cirle_hasjoin.setImageResource(R.mipmap.icon_join);
            //如果加入过的圈子点击可取消关注
            iv_cirle_hasjoin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iv_cirle_hasjoin.setImageResource(R.mipmap.icon_2_join);
                    iv_cirle_edit.setVisibility(View.GONE);
                    postCanleGuanZhu(groupId);

                }
            });
        }else {
            //不显示编辑按钮
            //todo
            iv_cirle_edit.setVisibility(View.GONE);
            iv_cirle_hasjoin.setImageResource(R.mipmap.icon_2_join);
            //如果未加入过的圈子点击可关注
            iv_cirle_hasjoin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iv_cirle_hasjoin.setImageResource(R.mipmap.icon_join);
                    iv_cirle_edit.setVisibility(View.VISIBLE);
                    postAddGuanZhu(groupId);
                }
            });
        }


        tv_cirledetails_introduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CirleDetailsActivity.this);
                builder.setTitle("圈子介绍");
                builder.setMessage(cirleNameDetailsBean.getGroupInfo());
                builder.show();
            }
        });
        lv_cirledetails.addHeaderView(viewHead);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                finish();
                break;
            case R.id.iv_cirle_edit:
                //进入编辑页面上传文章与图片
                Intent intent = new Intent(CirleDetailsActivity.this,CirlePostMessageActivity.class);
                intent.putExtra("groupId",groupId);
                startActivity(intent);
                break;
        }
    }

    private void postCanleGuanZhu(String groupId){
        String apptoken = PrefUtils.getString(this, "apptoken", "");
        String uid = PrefUtils.getString(this, "uid", "null");
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.ME_URL, RequestMethod.POST);
        request.add("apptoken",apptoken);
        request.add("uid",uid);
        request.add("op","exitGroup");
        request.add("groupNo",groupId);
        requestQueue.add(POST_CANLEGUANZHU_DATA, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
                 pd = new ProgressDialog(CirleDetailsActivity.this);
                 pd.show();
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                MyLog.testLog("返回结果:"+info);
                Toast.makeText(CirleDetailsActivity.this,"已退出该群!",Toast.LENGTH_SHORT).show();
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

    private void postAddGuanZhu(String groupId){
        String apptoken = PrefUtils.getString(this, "apptoken", "");
        String uid = PrefUtils.getString(CirleDetailsActivity.this, "uid", "null");
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.ME_URL, RequestMethod.POST);
        request.add("op","joinGroup");
        request.add("apptoken",apptoken);
        request.add("uid",uid);
        request.add("groupNos",groupId);
        requestQueue.add(POST_ADDGUANZHU_DATA, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
                pd = new ProgressDialog(CirleDetailsActivity.this);
                pd.show();
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                MyLog.testLog("返回结果:"+info);
                Toast.makeText(CirleDetailsActivity.this,"已加入该群!",Toast.LENGTH_SHORT).show();
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
}
