package com.player.mothercollege.unity.details;

import android.content.Intent;
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
import com.player.mothercollege.bean.FastDetailsAdapter;
import com.player.mothercollege.bean.FastDetailsBean;
import com.player.mothercollege.utils.ConfigUtils;
import com.player.mothercollege.utils.DateUtils;
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
 * Created by Administrator on 2016/11/1.
 * 快速咨询详情
 */
public class FastInquiryDetailsActivity extends BaseActivity implements View.OnClickListener {

    private static final int GET_FASTDETAILS_DATA = 001;
    private Button btn_back,btn_fastdetails_answer;
    private TextView tv_details_title;
    private ListView lv_fastdetails;
    private RequestManager glideRequest;
    private String qid;
    private RequestQueue requestQueue;
    private FastDetailsBean fastDetailsBean;

    @Override
    public void setContentView() {
        setContentView(R.layout.act_unity_fastdetails);
        requestQueue = NoHttp.newRequestQueue();
    }

    @Override
    public void initViews() {
        qid = getIntent().getStringExtra("qid");
        btn_back = (Button) findViewById(R.id.btn_back);
        tv_details_title = (TextView) findViewById(R.id.tv_details_title);
        lv_fastdetails = (ListView) findViewById(R.id.lv_fastdetails);
        btn_fastdetails_answer = (Button) findViewById(R.id.btn_fastdetails_answer);

        tv_details_title.setText("问答详情");
    }

    @Override
    public void initListeners() {
        btn_back.setOnClickListener(this);
        btn_fastdetails_answer.setOnClickListener(this);
    }

    @Override
    public void initData() {
        netWork();
    }

    private void netWork() {
        String apptoken = PrefUtils.getString(FastInquiryDetailsActivity.this, "apptoken", "");
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.UNITY_URL, RequestMethod.GET);
        request.add("apptoken",apptoken);
        MyLog.testLog("apptoken："+apptoken);
        request.add("op","qustioninfo");
        request.add("qid",qid+"");
        MyLog.testLog("QID："+qid);
        requestQueue.add(GET_FASTDETAILS_DATA, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                MyLog.testLog("快速咨询详情"+info);
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
        fastDetailsBean = gson.fromJson(info, FastDetailsBean.class);
        List<FastDetailsBean.AnswerBean> answerList = fastDetailsBean.getAnswer();
        addHeadTitle();
        FastDetailsAdapter adapter = new FastDetailsAdapter(FastInquiryDetailsActivity.this,answerList);
        lv_fastdetails.setAdapter(adapter);
    }

    private void addHeadTitle() {
        View headView = View.inflate(FastInquiryDetailsActivity.this,R.layout.head_fasttails_request,null);
        TextView tv_fastdetails_title = (TextView) headView.findViewById(R.id.tv_fastdetails_title);
        ImageView iv_fastdetails_me = (ImageView) headView.findViewById(R.id.iv_fastdetails_me);
        TextView tv_fastdetails_mename = (TextView) headView.findViewById(R.id.tv_fastdetails_mename);
        TextView tv_fastdetails_metime = (TextView) headView.findViewById(R.id.tv_fastdetails_metime);
        TextView tv_fastdetails_viewCount = (TextView) headView.findViewById(R.id.tv_fastdetails_viewCount);
        TextView tv_fastdetails_questContent = (TextView) headView.findViewById(R.id.tv_fastdetails_questContent);
        TextView tv_fastdetails_num = (TextView) headView.findViewById(R.id.tv_fastdetails_num);
        tv_fastdetails_title.setText(fastDetailsBean.getTitle());
        glideRequest = Glide.with(FastInquiryDetailsActivity.this);
        glideRequest.load(fastDetailsBean.getUicon())
                .transform(new GlideCircleTransform(FastInquiryDetailsActivity.this)).into(iv_fastdetails_me);
        tv_fastdetails_mename.setText(fastDetailsBean.getUnicename());
        String time = fastDetailsBean.getDate();
        tv_fastdetails_metime.setText(DateUtils.getStandardDate(time));
        tv_fastdetails_viewCount.setText("浏览人数:"+fastDetailsBean.getViewCount());
        tv_fastdetails_questContent.setText(fastDetailsBean.getQusition());
        tv_fastdetails_num.setText(fastDetailsBean.getAnswer().size()+"条回答");
        lv_fastdetails.addHeaderView(headView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_fastdetails_answer://我来回答
                Intent intent = new Intent(FastInquiryDetailsActivity.this,MyAnswerActivity.class);
                intent.putExtra("qid",qid);
                startActivity(intent);
                break;
        }
    }
}
