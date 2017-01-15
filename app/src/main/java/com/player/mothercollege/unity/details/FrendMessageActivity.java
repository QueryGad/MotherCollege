package com.player.mothercollege.unity.details;


import android.content.Intent;
import android.widget.FrameLayout;

import com.google.gson.Gson;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
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
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/21.
 */
public class FrendMessageActivity extends BaseActivity {

    private FrameLayout fl_message;
    private EaseConversationListFragment conversationListFragment;
    private RequestQueue requestQueue;

    @Override
    public void setContentView() {
        setContentView(R.layout.act_unity_message);
        requestQueue = NoHttp.newRequestQueue();
    }

    @Override
    public void initViews() {
        fl_message = (FrameLayout) findViewById(R.id.fl_message);
        if (EaseConstant.CHANGLIANG!=null&&EaseConstant.CHANGLIANG.size()>0){
            conversationListFragment = new EaseConversationListFragment();

            conversationListFragment.setConversationListItemClickListener(new EaseConversationListFragment.EaseConversationListItemClickListener() {
                @Override
                public void onListItemClicked(EMConversation conversation) {
                    if (conversation.isGroup()){
                        if(conversation.getType() == EMConversation.EMConversationType.ChatRoom){

                        }else{
                            Intent intent = new Intent(FrendMessageActivity.this,ChatActivity.class);
                            intent.putExtra(EaseConstant.EXTRA_USER_ID,conversation.getUserName());
                            intent.putExtra("chatType",EaseConstant.CHATTYPE_GROUP);
                            intent.putExtra("niceName",conversation.getUserName());
                            startActivity(intent);
                        }
                    }
                    Intent intent = new Intent(FrendMessageActivity.this,ChatActivity.class);
                    intent.putExtra(EaseConstant.EXTRA_USER_ID,conversation.getUserName());

                    intent.putExtra("chatType",EaseConstant.CHATTYPE_SINGLE);
                    intent.putExtra("niceName",conversation.getUserName());
                    startActivity(intent);
                }
            });
            getSupportFragmentManager().beginTransaction().add(R.id.fl_message,conversationListFragment).commit();
        }else {
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
                    Gson gson = new Gson();
                    ListAddressBean listAddressBean = gson.fromJson(info, ListAddressBean.class);
                    myFriends = listAddressBean.getMyFriends();
                    myGroups = listAddressBean.getMyGroups();
                    for (int i = 0;i<myFriends.size();i++){
                        String icon = myFriends.get(i).getIcon();
                        String niceName = myFriends.get(i).getNiceName();
                        String snsUid = myFriends.get(i).getSnsUid();
                        user = new EaseUser(snsUid);
                        user.setNick(niceName);
                        user.setInitialLetter(niceName);
                        user.setAvatar(icon);
                        contacts.put(snsUid,user);

                    }
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
                    conversationListFragment = new EaseConversationListFragment();
                    getSupportFragmentManager().beginTransaction().add(R.id.fl_message,conversationListFragment).commit();
                    conversationListFragment.setConversationListItemClickListener(new EaseConversationListFragment.EaseConversationListItemClickListener() {
                        @Override
                        public void onListItemClicked(EMConversation conversation) {

                            Intent intent = new Intent(FrendMessageActivity.this,ChatActivity.class);
                            intent.putExtra(EaseConstant.EXTRA_USER_ID,conversation.getUserName());
                            intent.putExtra("chatType",EaseConstant.CHATTYPE_SINGLE);
                            intent.putExtra("niceName",conversation.getUserName());
                            startActivity(intent);
                        }
                    });

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

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {

    }
}
