package com.player.mothercollege.me;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.player.mothercollege.R;
import com.player.mothercollege.activity.BaseActivity;
import com.player.mothercollege.me.details.DataCleanManager;
import com.player.mothercollege.me.details.SettingAboutActivity;
import com.player.mothercollege.me.details.SettingHelpActivity;

import ch.ielse.view.SwitchView;

/**
 * Created by Administrator on 2016/10/25.
 * 系统设置
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout rl_me_setting_help,rl_me_setting_about,rl_me_setting_out;
    private LinearLayout ll_me_setting_cache;
    private Button btn_back;
    private TextView tv_details_title;
    private SwitchView setting_switch;
    private AlertDialog alertDialog;
    private TextView tv_setting_cache;
    private AlertDialog alertDialog2;

    @Override
    public void setContentView() {
       setContentView(R.layout.act_me_setting);
    }

    @Override
    public void initViews() {
        btn_back = (Button) findViewById(R.id.btn_back);
        tv_details_title = (TextView) findViewById(R.id.tv_details_title);
        setting_switch = (SwitchView) findViewById(R.id.setting_switch);
        tv_setting_cache = (TextView) findViewById(R.id.tv_setting_cache);
        rl_me_setting_help = (RelativeLayout) findViewById(R.id.rl_me_setting_help);
        rl_me_setting_about = (RelativeLayout) findViewById(R.id.rl_me_setting_about);
        rl_me_setting_out = (RelativeLayout) findViewById(R.id.rl_me_setting_out);
        ll_me_setting_cache = (LinearLayout) findViewById(R.id.ll_me_setting_cache);

        tv_details_title.setText("设置");
    }

    @Override
    public void initListeners() {
        rl_me_setting_help.setOnClickListener(this);
        rl_me_setting_about.setOnClickListener(this);
        rl_me_setting_out.setOnClickListener(this);
        ll_me_setting_cache.setOnClickListener(this);
        btn_back.setOnClickListener(this);
        setting_switch.setOnClickListener(this);
    }

    @Override
    public void initData() {
        //获取缓存大小，显示在清除缓存栏
        try {
            getCacheSize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.rl_me_setting_help:
                Intent intent = new Intent(SettingActivity.this, SettingHelpActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_me_setting_about:
                Intent intent1 = new Intent(SettingActivity.this, SettingAboutActivity.class);
                startActivity(intent1);
                break;
            case R.id.rl_me_setting_out:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("温馨提示");
                builder.setMessage("确定退出此账号?");
                builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                });
                builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog = builder.show();
                alertDialog.setCanceledOnTouchOutside(false);
                break;
            case R.id.btn_back:
                finish();
                break;
            case R.id.setting_switch:
                //新消息提醒开关设置
                boolean isOpened = setting_switch.isOpened();
                break;
            case R.id.ll_me_setting_cache:
                AlertDialog.Builder builder2 = new AlertDialog.Builder(SettingActivity.this);
                builder2.setTitle("温馨提示");
                builder2.setMessage("确认清除缓存?");
                builder2.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog2.dismiss();
                    }
                });
                builder2.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DataCleanManager.clearAllCache(SettingActivity.this);
                        String totalCacheSize = null;
                        try {
                            totalCacheSize = DataCleanManager.getTotalCacheSize(SettingActivity.this);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        tv_setting_cache.setText(totalCacheSize);
                    }
                });
                alertDialog2 = builder2.show();
                alertDialog2.setCanceledOnTouchOutside(false);
                break;
           }
        }
    /**
     * 获取缓存大小
     */
    public void getCacheSize() throws Exception {
        String totalCacheSize = DataCleanManager.getTotalCacheSize(this);
        tv_setting_cache.setText(totalCacheSize);
    }
}
