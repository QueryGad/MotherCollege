package com.player.mothercollege.unity.details;

import android.content.Intent;
import android.widget.FrameLayout;

import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.player.mothercollege.R;
import com.player.mothercollege.activity.BaseActivity;

/**
 * Created by Administrator on 2016/12/21.
 */
public class FrendMessageActivity extends BaseActivity{

    private FrameLayout fl_message;
    private EaseConversationListFragment conversationListFragment;

    @Override
    public void setContentView() {
        setContentView(R.layout.act_unity_message);
    }

    @Override
    public void initViews() {
        fl_message = (FrameLayout) findViewById(R.id.fl_message);
        conversationListFragment = new EaseConversationListFragment();
        conversationListFragment.setConversationListItemClickListener(new EaseConversationListFragment.EaseConversationListItemClickListener() {
            @Override
            public void onListItemClicked(EMConversation conversation) {
                startActivity(new Intent(FrendMessageActivity.this,ChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID,conversation.getUserName()));
            }
        });
        getSupportFragmentManager().beginTransaction().add(R.id.fl_message,conversationListFragment).commit();
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {

    }
}
