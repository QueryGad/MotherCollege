package com.player.mothercollege.unity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.gson.Gson;
import com.player.mothercollege.R;
import com.player.mothercollege.bean.FrendQunRecommBean;
import com.player.mothercollege.bean.JiaQunBean;
import com.player.mothercollege.me.HeadIconActivity;
import com.player.mothercollege.unity.details.FrendMessageActivity;
import com.player.mothercollege.unity.details.ListAddressActivity;
import com.player.mothercollege.unity.details.QunChatActivity;
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
 * 好友页面
 */
public class GoodFrendFragment extends Fragment implements View.OnClickListener {

    private static final int GET_RECOMM_DATA = 001;
    private static final int POST_CANQUN_DATA = 002;
    private View view;
    private LinearLayout ll_user_view,ll_group_view;
    private RequestQueue requestQueue;
    private RequestManager glideRequest;
    private List<FrendQunRecommBean.RecommendGroupBean> recommendGroup;
    private List<FrendQunRecommBean.RecommendUsersBean> recommendUsers;
    private Button btn_unity_message,btn_unity_addresslist,btn_unity_qunchat;
    private String apptoken;
    private String uid;
    private ImageView iv_qun_join;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.frg_unity_goodfrend,null);
        requestQueue = NoHttp.newRequestQueue();
        initView();
        initData();
        return view;
    }

    private void initView() {

        ll_user_view = (LinearLayout) view.findViewById(R.id.ll_user_view);
        ll_group_view = (LinearLayout) view.findViewById(R.id.ll_group_view);
        btn_unity_message = (Button) view.findViewById(R.id.btn_unity_message);
        btn_unity_addresslist = (Button) view.findViewById(R.id.btn_unity_addresslist);
        btn_unity_qunchat = (Button) view.findViewById(R.id.btn_unity_qunchat);

        btn_unity_message.setOnClickListener(this);
        btn_unity_addresslist.setOnClickListener(this);
        btn_unity_qunchat.setOnClickListener(this);
    }



    private void initData() {
        netWork();
    }

    private void netWork() {
        apptoken = PrefUtils.getString(getActivity(), "apptoken", "");
        uid = PrefUtils.getString(getActivity(), "uid", "");
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.UNITY_URL, RequestMethod.GET);
        request.add("apptoken", apptoken);
        request.add("op","FriendPage");
        request.add("uid", uid);
        requestQueue.add(GET_RECOMM_DATA, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                MyLog.testLog("好友群推荐页面:"+info);
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

    private void parseJson(String info) {
        Gson gson = new Gson();
        FrendQunRecommBean frendQunRecommBean = gson.fromJson(info, FrendQunRecommBean.class);
        //群推荐
        recommendGroup = frendQunRecommBean.getRecommendGroup();
        //好友推荐
        recommendUsers = frendQunRecommBean.getRecommendUsers();
        initRecommendUsers();
        initRecommendGroup();
    }

    private void initRecommendUsers() {
        for ( int i =0;i<recommendUsers.size();i++){
            View viewUsers = View.inflate(getActivity(),R.layout.item_frendqun_recomm,null);
            ImageView iv_frend_icon = (ImageView) viewUsers.findViewById(R.id.iv_frend_icon);
            TextView tv_frend_name = (TextView) viewUsers.findViewById(R.id.tv_frend_name);
            LinearLayout ll_user = (LinearLayout) viewUsers.findViewById(R.id.ll_user);
            glideRequest = Glide.with(this);
            glideRequest.load(recommendUsers.get(i).getIcon())
                    .transform(new GlideCircleTransform(getActivity())).into(iv_frend_icon);
            tv_frend_name.setText(recommendUsers.get(i).getNiceName());
            final String toUid = recommendUsers.get(i).getUid();

            ll_user.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), HeadIconActivity.class);
                    intent.putExtra("toUid",toUid);

                    startActivity(intent);
                }
            });
            ll_user_view.addView(viewUsers);
        }
    }

    private void initRecommendGroup() {

        for ( int i =0;i<recommendGroup.size();i++){
            View viewGroup = View.inflate(getActivity(),R.layout.item_frendqun_qun,null);
            ImageView iv_qun_icon = (ImageView) viewGroup.findViewById(R.id.iv_qun_icon);
            TextView tv_qun_name = (TextView) viewGroup.findViewById(R.id.tv_qun_name);
            iv_qun_join = (ImageView) viewGroup.findViewById(R.id.iv_qun_join);

            glideRequest = Glide.with(this);
            glideRequest.load(recommendGroup.get(i).getGroupIcon())
                    .transform(new GlideCircleTransform(getActivity())).into(iv_qun_icon);
            tv_qun_name.setText(recommendGroup.get(i).getGroupName());
            final String groupId = recommendGroup.get(i).getGroupId();
            final int currentUserJoinState = recommendGroup.get(i).getCurrentUserJoinState();
            iv_qun_join.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentUserJoinState==0){
                        //未加入
                        iv_qun_join.setImageResource(R.mipmap.icon_2_join);
                        //申请加入群
                        Request<String> request = NoHttp.createStringRequest(ConfigUtils.UNITY_URL, RequestMethod.POST);
                        request.add("apptoken",apptoken);
                        request.add("op","joinImGroup");
                        request.add("uid",uid);
                        request.add("gid",groupId);
                        requestQueue.add(POST_CANQUN_DATA, request, new OnResponseListener<String>() {
                            @Override
                            public void onStart(int what) {

                            }

                            @Override
                            public void onSucceed(int what, Response<String> response) {
                                String info = response.get();
                                Gson gson = new Gson();
                                JiaQunBean jiaQunBean = gson.fromJson(info, JiaQunBean.class);
                                boolean isSuccess = jiaQunBean.isIsSuccess();
                                String resultInfo = jiaQunBean.getResultInfo();
                                if (isSuccess){
                                    iv_qun_join.setImageResource(R.mipmap.icon_join);
                                }else {
                                    Toast.makeText(getActivity(),resultInfo,Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailed(int what, Response<String> response) {

                            }

                            @Override
                            public void onFinish(int what) {

                            }
                        });
                    }else if (currentUserJoinState==1){
                        //审核中
                        iv_qun_join.setImageResource(R.mipmap.icon_applying_join);
                        Toast.makeText(getActivity(),"正在审核中，请求管理员通过!",Toast.LENGTH_SHORT).show();
                    }else if (currentUserJoinState==2){
                        //通过
                        iv_qun_join.setImageResource(R.mipmap.icon_join);
                        iv_qun_join.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getContext(),"已加入,请在群聊中查看",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });
            ll_group_view.addView(viewGroup);
        }

    }




    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_unity_message:
                Intent intent1 = new Intent(getActivity(), FrendMessageActivity.class);
                startActivity(intent1);
                break;
            case R.id.btn_unity_addresslist:
                Intent intent2 = new Intent(getActivity(), ListAddressActivity.class);
                startActivity(intent2);
                break;
            case R.id.btn_unity_qunchat:
                Intent intent3 = new Intent(getActivity(), QunChatActivity.class);
                startActivity(intent3);
                break;
        }
    }
}
