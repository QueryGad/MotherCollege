package com.player.mothercollege.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.SystemClock;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.player.mothercollege.R;
import com.player.mothercollege.utils.ConfigUtils;
import com.player.mothercollege.utils.MyLog;
import com.player.mothercollege.utils.MyUtils;
import com.player.mothercollege.utils.PrefUtils;
import com.yanzhenjie.permission.AndPermission;
import com.yolanda.nohttp.Headers;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.download.DownloadListener;
import com.yolanda.nohttp.download.DownloadQueue;
import com.yolanda.nohttp.download.DownloadRequest;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

import org.json.JSONObject;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SplashActivity extends BaseActivity {

    private static final int GET_APPTOKEN_DATA = 001;
    private static final int GET_PERMISSIONS_PHONE_STATE = 002;
    private int DOWN_APP_NEW = 003;
    private SharedPreferences sp;
    private int versionCode;
    private AlertDialog dialog;
    private DownloadQueue downloadQueue;
    private RequestQueue requestQueue;


    @Override
    public void setContentView() {
        setContentView(R.layout.activity_splash);
        requestQueue = NoHttp.newRequestQueue();
        sp = getSharedPreferences("configSP", MODE_PRIVATE);
    }

    @Override
    public void initViews() {
        downloadQueue = NoHttp.newDownloadQueue();
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {
        // 得到当前应用版本
        getVersionCode();
        //请求公钥联网获取数据
        network();
        // 检查版本更新
        checkUpdate();
    }

    private void checkUpdate() {
        // 联网获取数据拿到json文件 进行版本对比
        new Thread() {
            public void run() {
                SystemClock.sleep(1000);
                try {
                    URL url = new URL(ConfigUtils.UPDATE_URL);
                    HttpURLConnection conn = (HttpURLConnection) url
                            .openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(2000);
                    if (conn.getResponseCode() == 200) {
                        InputStream is = conn.getInputStream();
                        String body = MyUtils.RequestInput(is);
                        Log.e("====", body);
                        JSONObject jsonObject = new JSONObject(body);
                        final int newCode = jsonObject.getInt("versioncode");
//						String newName = jsonObject.getString("versionname");
                        final String updateurl = jsonObject
                                .getString("updateurl");
//						String remind = jsonObject.getString("remind");
//						String force = jsonObject.getString("force");
                        final String msg = jsonObject.getString("msg");
                        MyLog.testLog(newCode + "");
                        MyLog.testLog(updateurl + "");
                        MyLog.testLog(msg + "");
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                // 判断与当前程序版本是否相同 ，不同则提示升级
                                equalsCode(newCode, updateurl, msg);
                            }
                        });

                    } else {
                        gotoHomeActivity();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    gotoHomeActivity();
                }
            }

        }.start();
    }

    /**
     * 跳转至主页面
     */
    private void gotoHomeActivity() {
        boolean isGuide = sp.getBoolean("isGuide", true);
        if (isGuide) {
            Intent intent = new Intent(this, GuideActicity.class);
            startActivity(intent);
            sp.edit().putBoolean("isGuide", false).commit();
            finish();
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    };

    /**
     * 判断当前版本与服务器版本是否相同
     */
    protected void equalsCode(int newCode, String updateurl, String msg) {
        MyLog.testLog(versionCode + "");
        if (newCode > versionCode) {
            // 弹框 提示用户进行版本升级
            DialogFaceUser(updateurl, msg);
        } else {
            // 直接进入主页面
            gotoHomeActivity();
        }

    }

    private void network() {
        //联网得到公钥
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.APPTOKEN_URL, RequestMethod.GET);
        request.add("devID",getdeviceId());
        request.add("op","appktoken");
        request.add("devType","2");
        requestQueue.add(GET_APPTOKEN_DATA, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String apptoken = response.get();
                PrefUtils.setString(SplashActivity.this,"apptoken",apptoken);
                MyLog.testLog("apptoken:"+apptoken);
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    /**
     * 得到当前应用版本
     */
    private void getVersionCode() {
        PackageManager pm = getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
//          String versionName = packageInfo.versionName;
            versionCode = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 得到设备唯一识别码
     */
    private String getdeviceId(){
        AndPermission.with(this).requestCode(GET_PERMISSIONS_PHONE_STATE)
                .permission(Manifest.permission.READ_PHONE_STATE)
                .send();
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        String deviceId = telephonyManager.getDeviceId();
        if (telephonyManager!=null){
            return deviceId;
        }
        return "";
    }

    /**
     * 提示用户更新页面
     */
    private void DialogFaceUser(final String updateurl, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
        builder.setTitle("温馨提示");
        String message = msg.replace("#", "\n");
        builder.setMessage(message);

        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                gotoHomeActivity();
            }
        });
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 使用NoHttp开始下载
                dialog.dismiss();
                final String filefolder = getFilesDir().getAbsolutePath();
                final DownloadRequest request = NoHttp.createDownloadRequest(
                        updateurl, filefolder, "news.apk", true, true);
                downloadQueue.add(DOWN_APP_NEW, request, new DownloadListener() {
                    ProgressDialog pd = new ProgressDialog(SplashActivity.this);
                    @Override
                    public void onStart(int what, boolean isResume,
                                        long rangeSize, Headers responseHeaders,
                                        long allCount) {
                        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                        pd.setTitle("正在下载....");
                        pd.setMax(100);
                        pd.setButton(DialogInterface.BUTTON_POSITIVE, "取消下载",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        request.cancel();
                                        gotoHomeActivity();
                                    }
                                });
                        pd.show();
                    }

                    @Override
                    public void onProgress(int what, int progress,
                                           long fileCount) {
                        pd.setProgress(progress);
                    }

                    @Override
                    public void onFinish(int what, String filePath) {
                        pd.dismiss();
                        // 打开安装向导页面；
                        installApk(filefolder);
                    }

                    @Override
                    public void onDownloadError(int what, Exception exception) {

                    }

                    @Override
                    public void onCancel(int what) {

                    }
                });

            }
        });
        dialog = builder.show();
        dialog.setCanceledOnTouchOutside(false);
    }

    // 安装apk应用
    protected void installApk(String filefolder) {
        // 如果没有i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);这一步的话，最后安装好了，点打开，是不会打开新版本应用的。
        // 如果没有android.os.Process.killProcess(android.os.Process.myPid());最后不会提示完成、打开。
        File apkfile = new File(filefolder, "news.apk");
        if (!apkfile.exists()) {
            gotoHomeActivity();
            return;
        }
        // 通过Intent安装APK文件
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
                "application/vnd.android.package-archive");
        this.startActivity(i);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

}
