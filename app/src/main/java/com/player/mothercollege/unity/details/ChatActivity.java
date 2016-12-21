package com.player.mothercollege.unity.details;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;
import com.player.mothercollege.R;
import com.player.mothercollege.activity.BaseActivity;
import com.player.mothercollege.utils.PrefUtils;

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

        String snsUid = getIntent().getStringExtra("snsUid");

        fl_chat = (FrameLayout) findViewById(R.id.fl_chat);

        //new出EaseChatFragment或其子类的实例
        EaseChatFragment chatFragment = new EaseChatFragment();
        //传入参数
        Bundle args = new Bundle();
        args.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
        args.putString(EaseConstant.EXTRA_USER_ID, snsUid);
        chatFragment.setArguments(args);
        chatFragment.setChatFragmentListener(new EaseChatFragment.EaseChatFragmentHelper() {
            @Override
            public void onSetMessageAttributes(EMMessage message) {
                if (isRobot){
                    //设置消息拓展属性
                    message.setAttribute("em_robot_message", isRobot);
                }
                // 通过扩展属性，将userPic和userName发送出去。
                String uicon = PrefUtils.getString(ChatActivity.this, "uicon", "");
                String username = PrefUtils.getString(ChatActivity.this, "username", "");
                if (!TextUtils.isEmpty(uicon)) {
                    message.setAttribute("userPic", uicon);
                }
                if (!TextUtils.isEmpty(username)) {
                    message.setAttribute("userName", username);
                }

            }

            @Override
            public void onEnterToChatDetails() {

            }

            @Override
            public void onAvatarClick(String username) {

            }

            @Override
            public void onAvatarLongClick(String username) {

            }

            @Override
            public boolean onMessageBubbleClick(EMMessage message) {
                return false;
            }

            @Override
            public void onMessageBubbleLongClick(EMMessage message) {

            }

            @Override
            public boolean onExtendMenuItemClick(int itemId, View view) {
                return false;
            }

            @Override
            public EaseCustomChatRowProvider onSetCustomChatRowProvider() {
                return null;
            }
        });
        getSupportFragmentManager().beginTransaction().add(R.id.fl_chat, chatFragment).commit();
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {

    }
}
