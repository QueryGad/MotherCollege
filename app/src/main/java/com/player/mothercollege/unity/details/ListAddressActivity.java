package com.player.mothercollege.unity.details;

import android.content.Intent;
import android.widget.FrameLayout;

import com.google.gson.Gson;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseContactListFragment;
import com.hyphenate.exceptions.HyphenateException;
import com.player.mothercollege.R;
import com.player.mothercollege.activity.BaseActivity;
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
public class ListAddressActivity extends BaseActivity{

    private FrameLayout fl_listaddress;
    private EaseContactListFragment contactListFragment;
    private RequestQueue requestQueue;


    @Override
    public void setContentView() {
        setContentView(R.layout.act_unity_listaddress);
        requestQueue = NoHttp.newRequestQueue();
    }

    @Override
    public void initViews() {
        fl_listaddress = (FrameLayout) findViewById(R.id.fl_listaddress);

        contactListFragment = new EaseContactListFragment();

        //需要设置联系人列表才能启动fragment；
        new Thread(){
            @Override
            public void run() {
                contactListFragment.setContactsMap(getContacts());
            }
        }.start();

        //设置点击事件
        contactListFragment.setContactListItemClickListener(new EaseContactListFragment.EaseContactListItemClickListener() {
            @Override
            public void onListItemClicked(EaseUser user) {
                startActivity(new Intent(ListAddressActivity.this, ChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID, user.getUsername()));
            }
        });
        getSupportFragmentManager().beginTransaction().add(R.id.fl_listaddress,contactListFragment).commit();

    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {

    }

    public Map<String,EaseUser> getContacts() {
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
//                        contacts.put("myFriends",myFriends.get(i));
                    String icon = myFriends.get(i).getIcon();
                    String niceName = myFriends.get(i).getNiceName();
                    user = new EaseUser(niceName);
                    user.setInitialLetter(niceName);
                    user.setAvatar(icon);
                }

                try {
                    List<String> userNames =  EMClient.getInstance().contactManager().getAllContactsFromServer();
                    for (String userId : userNames){
                        contacts.put(userId,new EaseUser(userId));
                    }
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }

//                contacts.put("myFriends",user);

            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
        return contacts;
    }
}
