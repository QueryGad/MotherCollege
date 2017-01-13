package com.player.mothercollege.find;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.player.mothercollege.R;
import com.player.mothercollege.activity.BaseActivity;
import com.player.mothercollege.bean.QRBean;
import com.player.mothercollege.login.LoginActivity;
import com.player.mothercollege.utils.CacheUtils;
import com.player.mothercollege.utils.ConfigUtils;
import com.player.mothercollege.utils.DensityUtils;
import com.player.mothercollege.utils.MyLog;
import com.player.mothercollege.utils.PrefUtils;
import com.squareup.picasso.Picasso;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.utils.Log;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

import java.util.Hashtable;

/**
 * Created by Administrator on 2016/10/25.
 * 邀请好友
 */
public class FrendActivity extends BaseActivity implements View.OnClickListener {

    private static final int GET_QR_DATA = 001;
    private Button btn_back,btn_share;
    private TextView tv_details_title,tv_yaoqingma;
    private ImageView iv_find_frend;
    private RequestQueue requestQueue;
    private String inviteCode;
    private String apptoken;
    private String uid;

    private ProgressDialog pd;

    @Override
    public void setContentView() {
        setContentView(R.layout.act_find_frend);
        requestQueue = NoHttp.newRequestQueue();
    }

    @Override
    public void initViews() {
        btn_back = (Button) findViewById(R.id.btn_back);
        tv_details_title = (TextView) findViewById(R.id.tv_details_title);
        iv_find_frend = (ImageView) findViewById(R.id.iv_find_frend);
        tv_yaoqingma = (TextView) findViewById(R.id.tv_yaoqingma);
        btn_share = (Button) findViewById(R.id.btn_share);

        tv_details_title.setText("邀请好友");
    }

    @Override
    public void initListeners() {
        btn_back.setOnClickListener(this);
        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uid.equals("")){
                    //未登录  提示登录
                    Intent intent = new Intent(FrendActivity.this, LoginActivity.class);
                    startActivity(intent);
                }else {
                    showShareDialog();
                }
            }
        });
    }
    private Dialog dialog;

    private void showShareDialog() {

        View view = LayoutInflater.from(this).inflate(R.layout.dialog_share, null);
        // 设置style 控制默认dialog带来的边距问题 
        dialog = new Dialog(this, R.style.common_dialog);
        dialog.setContentView(view);
        dialog.show();
        RelativeLayout pengyou = (RelativeLayout) view.findViewById(R.id.view_share_pengyou);
        RelativeLayout wechat = (RelativeLayout) view.findViewById(R.id.view_share_wechat);
        RelativeLayout sina = (RelativeLayout) view.findViewById(R.id.view_share_sina);
        RelativeLayout space = (RelativeLayout) view.findViewById(R.id.view_share_space);
        RelativeLayout qq = (RelativeLayout) view.findViewById(R.id.view_share_qq);
        RelativeLayout frend = (RelativeLayout) view.findViewById(R.id.view_share_frend);
        Button btn_canle = (Button) view.findViewById(R.id.btn_canle);
        pengyou.setOnClickListener(this);
        wechat.setOnClickListener(this);
        sina.setOnClickListener(this);
        space.setOnClickListener(this);
        qq.setOnClickListener(this);
        frend.setOnClickListener(this);
        btn_canle.setOnClickListener(this);
        // 设置相关位置，一定要在 show()之后  
        Window window = dialog.getWindow();
        window.getDecorView().setPadding(0,0,0,0);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
    }

    @Override
    public void initData() {
        //获取缓存数据
        String cacheJson = CacheUtils.getCache(FrendActivity.this, ConfigUtils.COLLEGE_URL + "qr_code");
        if (!TextUtils.isEmpty(cacheJson)){
            parseJson(cacheJson);
        }
        netWork();
    }

    private void netWork() {
        apptoken = PrefUtils.getString(this, "apptoken", "");
        uid = PrefUtils.getString(this, "uid", "");
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.FIND_URL, RequestMethod.GET);
        request.add("op","yqhy");
        request.add("uid", uid);
        request.add("apptoken", apptoken);
        requestQueue.add(GET_QR_DATA, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
                pd = new ProgressDialog(FrendActivity.this);
                pd.show();
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                MyLog.testLog("二维码页面:"+info);
                parseJson(info);
                CacheUtils.saveCache(FrendActivity.this,ConfigUtils.COLLEGE_URL + "qr_code",info);
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }

            @Override
            public void onFinish(int what) {
                pd.dismiss();
            }
        });
    }

    private void parseJson(String info){
        Gson gson = new Gson();
        QRBean qrBean = gson.fromJson(info, QRBean.class);
        //邀请码
        inviteCode = qrBean.getInviteCode();
        String qrCode = qrBean.getQrCode();
        Picasso.with(FrendActivity.this).load(qrCode)
                .resize(DensityUtils.dip2px(FrendActivity.this,110),DensityUtils.dip2px(FrendActivity.this,110))
                .centerCrop().into(iv_find_frend);
//        String qrCode = qrBean.getQrCode(); //二维码
        tv_yaoqingma.setText("您的邀请码:"+ inviteCode);
//        try {
//            Bitmap bm = qr_code(qrCode,BarcodeFormat.QR_CODE);
//            iv_find_frend.setImageBitmap(bm);
//        } catch (WriterException e) {
//            e.printStackTrace();
//        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                finish();
                break;
            case R.id.view_share_pengyou:
                new ShareAction(FrendActivity.this).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                        .withText("有爱还不够，教育孩子先教好自己！我正在母亲大学堂学习， " +
                                "名师亲授，在线互动，你也快来吧！输入我的邀请码："+inviteCode+"，有惊喜哦！")
                        .setCallback(umShareListener)
                        .share();
                break;
            case R.id.view_share_wechat:
                new ShareAction(FrendActivity.this).setPlatform(SHARE_MEDIA.WEIXIN)
                        .withText("有爱还不够，教育孩子先教好自己！我正在母亲大学堂学习， " +
                                "名师亲授，在线互动，你也快来吧！输入我的邀请码："+inviteCode+"，有惊喜哦！")
                        .setCallback(umShareListener)
                        .share();
                break;
            case R.id.view_share_sina:
                new ShareAction(FrendActivity.this).setPlatform(SHARE_MEDIA.SINA)
                        .withText("有爱还不够，教育孩子先教好自己！我正在母亲大学堂学习， " +
                                "名师亲授，在线互动，你也快来吧！输入我的邀请码："+inviteCode+"，有惊喜哦！")
                        .setCallback(umShareListener)
                        .share();
                break;
            case R.id.view_share_space:
                new ShareAction(FrendActivity.this).setPlatform(SHARE_MEDIA.QZONE)
                        .withText("有爱还不够，教育孩子先教好自己！我正在母亲大学堂学习， " +
                                "名师亲授，在线互动，你也快来吧！输入我的邀请码："+inviteCode+"，有惊喜哦！")
                        .setCallback(umShareListener)
                        .share();
                break;
            case R.id.view_share_qq:
                new ShareAction(FrendActivity.this).setPlatform(SHARE_MEDIA.QQ)
                        .withText("有爱还不够，教育孩子先教好自己！我正在母亲大学堂学习， " +
                                "名师亲授，在线互动，你也快来吧！输入我的邀请码："+inviteCode+"，有惊喜哦！")
                        .setCallback(umShareListener)
                        .share();
                break;
            case R.id.view_share_frend:
                Toast.makeText(FrendActivity.this,"母亲大学堂",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_canle:
                dialog.dismiss();
                break;

        }
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat","platform"+platform);

            Toast.makeText(FrendActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(FrendActivity.this,platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if(t!=null){
                Log.d("throw","throw:"+t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(FrendActivity.this,platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };

    public Bitmap qr_code(String string, BarcodeFormat format)
            throws WriterException {
        MultiFormatWriter writer = new MultiFormatWriter();
        Hashtable<EncodeHintType, String> hst = new Hashtable<EncodeHintType, String>();
        hst.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        BitMatrix matrix = writer.encode(string, format, 400, 400, hst);
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (matrix.get(x, y)) {
                    pixels[y * width + x] = 0xff000000;
                }

            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        // 通过像素数组生成bitmap,具体参考api
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }
}
