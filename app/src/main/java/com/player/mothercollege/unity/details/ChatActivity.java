package com.player.mothercollege.unity.details;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.player.mothercollege.R;
import com.player.mothercollege.activity.BaseActivity;

/**
 * Created by Administrator on 2016/12/20.
 */
public class ChatActivity extends BaseActivity {

    private FrameLayout fl_chat;
    private boolean isRobot = true;

    @Override
    public void setContentView() {
        setContentView(R.layout.act_unity_details_chat);
    }

    @Override
    public void initViews() {

        fl_chat = (FrameLayout) findViewById(R.id.fl_chat);
        String username = getIntent().getStringExtra(EaseConstant.EXTRA_USER_ID);
        int chatType = getIntent().getIntExtra("chatType",0);
        String niceName = getIntent().getStringExtra("niceName");
        String niceIcon = getIntent().getStringExtra("niceIcon");
        //new出EaseChatFragment或其子类的实例
        EaseChatFragment chatFragment = new EaseChatFragment();
        //传入参数
        Bundle args = new Bundle();
        args.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
        args.putString(EaseConstant.EXTRA_USER_ID, username);
        args.putString("niceName", niceName);
        args.putString("niceIcon", niceIcon);

        chatFragment.setArguments(args);

        getSupportFragmentManager().beginTransaction().add(R.id.fl_chat, chatFragment).commit();
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {

    }
}
