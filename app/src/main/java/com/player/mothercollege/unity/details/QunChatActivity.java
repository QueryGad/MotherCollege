package com.player.mothercollege.unity.details;

import android.widget.FrameLayout;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCursorResult;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMGroupInfo;
import com.hyphenate.exceptions.HyphenateException;
import com.player.mothercollege.R;
import com.player.mothercollege.activity.BaseActivity;

import java.util.List;

/**
 * Created by Administrator on 2016/12/21.
 */
public class QunChatActivity extends BaseActivity{

    private FrameLayout fl_qunchat;

    @Override
    public void setContentView() {
        setContentView(R.layout.act_unity_qunchat);
    }

    @Override
    public void initViews() {

    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //从服务器获取自己加入的和创建的群组列表，此api获取的群组sdk会自动保存到内存和db。
                try {
                    List<EMGroup> grouplist = EMClient.getInstance().groupManager().getJoinedGroupsFromServer();
                    //从本地加载群组列表
                    List<EMGroup> group = EMClient.getInstance().groupManager().getAllGroups();
                    //获取公开群列表
                    //pageSize为要取到的群组的数量，cursor用于告诉服务器从哪里开始取
                    EMCursorResult<EMGroupInfo> result = EMClient.getInstance().groupManager().getPublicGroupsFromServer(5, 0+"");//需异步处理
                    List<EMGroupInfo> returnGroups = result.getData();
                    String cursor = result.getCursor();
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }
}
