package com.player.mothercollege.me.details;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.player.mothercollege.R;
import com.player.mothercollege.activity.BaseActivity;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

/**
 * Created by Administrator on 2016/10/5.
 * 关于
 */
public class SettingAboutActivity extends BaseActivity implements View.OnClickListener {

    private Button btn_back;
    private TextView tv_details_title;
    private ImageView iv_about_phone;
    private int GET_PERMISSIONS_CALL = 001;

    @Override
    public void setContentView() {
        setContentView(R.layout.act_me_setting_about);
    }

    @Override
    public void initViews() {

        btn_back = (Button) findViewById(R.id.btn_back);
        tv_details_title = (TextView) findViewById(R.id.tv_details_title);
        iv_about_phone = (ImageView) findViewById(R.id.iv_about_phone);

        tv_details_title.setText("关于");
    }

    @Override
    public void initListeners() {
        iv_about_phone.setOnClickListener(this);
        btn_back.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.iv_about_phone:
                Intent intent = new Intent();
                //为安卓6.0添加动态权限
                AndPermission.with(this).requestCode(GET_PERMISSIONS_CALL)
                        .permission(Manifest.permission.CALL_PHONE)
                        .send();
                String call = "4000405119";
//                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:"+call));
                startActivity(intent);
                break;
            case R.id.btn_back:
                finish();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // 只需要调用这一句，剩下的AndPermission自动完成。
        AndPermission.onRequestPermissionsResult(this, requestCode, permissions, grantResults, listener);
    }

    private PermissionListener listener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode) {

        }

        @Override
        public void onFailed(int requestCode) {
              if (requestCode==GET_PERMISSIONS_CALL){
                  Toast.makeText(SettingAboutActivity.this,"您拒绝了拨打电话的权限，无法联系我们!",Toast.LENGTH_SHORT).show();;
              }
        }
    };
}
