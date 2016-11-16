package com.player.mothercollege.find;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import com.player.mothercollege.utils.CacheUtils;
import com.player.mothercollege.utils.ConfigUtils;
import com.player.mothercollege.utils.MyLog;
import com.player.mothercollege.utils.PrefUtils;
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
        btn_share.setOnClickListener(this);
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
        String apptoken = PrefUtils.getString(this, "apptoken", "");
        String uid = PrefUtils.getString(FrendActivity.this, "uid", "null");
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.FIND_URL, RequestMethod.GET);
        request.add("op","yqhy");
        request.add("uid",uid);
        request.add("apptoken",apptoken);
        requestQueue.add(GET_QR_DATA, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                MyLog.testLog("二维码页面:"+info);
                CacheUtils.saveCache(FrendActivity.this,ConfigUtils.COLLEGE_URL + "qr_code",info);
                parseJson(info);
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    private void parseJson(String info){
        Gson gson = new Gson();
        QRBean qrBean = gson.fromJson(info, QRBean.class);
        String inviteCode = qrBean.getInviteCode();//邀请码
        String qrCode = qrBean.getQrCode(); //二维码
        tv_yaoqingma.setText("您的邀请码:"+inviteCode);
        try {
            Bitmap bm = qr_code(qrCode,BarcodeFormat.QR_CODE);
            iv_find_frend.setImageBitmap(bm);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_share:
                Toast.makeText(FrendActivity.this,"第三方分享功能",Toast.LENGTH_SHORT).show();
                break;
        }
    }

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
