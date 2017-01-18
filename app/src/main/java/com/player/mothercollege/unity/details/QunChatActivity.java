package com.player.mothercollege.unity.details;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.google.gson.Gson;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseContactListFragment;
import com.player.mothercollege.R;
import com.player.mothercollege.activity.BaseActivity;
import com.player.mothercollege.application.MyApplication;
import com.player.mothercollege.bean.ListAddressBean;
import com.player.mothercollege.utils.ConfigUtils;
import com.player.mothercollege.utils.MyLog;
import com.player.mothercollege.utils.PrefUtils;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/21.
 */
public class QunChatActivity extends BaseActivity{

    private FrameLayout fl_qunchat;
    private RequestQueue requestQueue;
    private EaseContactListFragment contactListFragment;


    @Override
    public void setContentView() {
        setContentView(R.layout.act_unity_qunchat);
        requestQueue = NoHttp.newRequestQueue();
    }

    @Override
    public void initViews() {
        fl_qunchat = (FrameLayout) findViewById(R.id.fl_qunchat);
        //需要设置联系人列表才能启动fragment；
        contactListFragment = new EaseContactListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title","群组");
        contactListFragment.setArguments(bundle);
        getContacts();
        //设置点击事件
        contactListFragment.setContactListItemClickListener(new EaseContactListFragment.EaseContactListItemClickListener() {
            @Override
            public void onListItemClicked(EaseUser user) {
                Intent intent = new Intent(QunChatActivity.this,ChatActivity.class);
                intent.putExtra(EaseConstant.EXTRA_USER_ID,user.getUsername());
                intent.putExtra("chatType",EaseConstant.CHATTYPE_GROUP);
                intent.putExtra("niceName",user.getNick());
                startActivity(intent);
            }
        });
        getSupportFragmentManager().beginTransaction().add(R.id.fl_qunchat,contactListFragment).commit();
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {

    }

    public void getContacts() {
        String apptoken = PrefUtils.getString(this, "apptoken", "");
        String uid = PrefUtils.getString(this, "uid", "");
        final Map<String,EaseUser> contacts = new HashMap<>();
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.UNITY_URL, RequestMethod.GET);
        request.add("apptoken",apptoken);
        request.add("op","myAddressBook");
        request.add("uid",uid);
        requestQueue.add(001, request, new OnResponseListener<String>() {

            private EaseUser user;
            private List<ListAddressBean.MyGroupsBean> myGroups = new ArrayList<ListAddressBean.MyGroupsBean>();
            private List<ListAddressBean.MyFriendsBean> myFriends = new ArrayList<ListAddressBean.MyFriendsBean>();

            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                MyLog.testLog("联系人页面:"+info);
                if (info!=null){
                    Gson gson = new Gson();
                    ListAddressBean listAddressBean = gson.fromJson(info, ListAddressBean.class);
                    myFriends = listAddressBean.getMyFriends();
                    myGroups = listAddressBean.getMyGroups();
                    for (int i = 0;i<myGroups.size();i++){
                        String icon = myGroups.get(i).getGroupIcon();
                        String niceName = myGroups.get(i).getGroupName();
                        String snsUid = myGroups.get(i).getSnsGroupID();
                        user = new EaseUser(snsUid);
                        user.setNick(niceName);
                        user.setInitialLetter(niceName);
                        user.setAvatar(icon);
                        contacts.put(snsUid,user);
                    }

                    MyApplication.CONTENTLIST = contacts;
                    EaseConstant.CHANGLIANG = contacts;
                    Iterator<Map.Entry<String, EaseUser>> iterator = contacts.entrySet().iterator();
                    while (iterator.hasNext()) {
                        Map.Entry<String, EaseUser> entry = iterator.next();
                        EaseUser user = entry.getValue();
                    }
                    contactListFragment.setContactsMap(contacts);
                    contactListFragment.setUpView();
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
