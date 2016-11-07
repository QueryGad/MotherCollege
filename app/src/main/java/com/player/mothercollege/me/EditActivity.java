package com.player.mothercollege.me;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.RequestManager;
import com.player.mothercollege.R;
import com.player.mothercollege.activity.BaseActivity;
import com.player.mothercollege.me.details.AddressActivity;
import com.player.mothercollege.me.details.AlterNameActivity;
import com.player.mothercollege.me.details.StyleActivity;
import com.player.mothercollege.utils.ConfigUtils;
import com.player.mothercollege.utils.DensityUtils;
import com.player.mothercollege.utils.MyLog;
import com.player.mothercollege.utils.MyUtils;
import com.player.mothercollege.utils.PrefUtils;
import com.squareup.picasso.Picasso;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;
import com.yolanda.nohttp.rest.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import ch.ielse.view.SwitchView;

/**
 * Created by Administrator on 2016/10/25.
 * 修改资料
 */
public class EditActivity extends BaseActivity implements View.OnClickListener {

    private static final int POST_PHONE_STATE =001 ;
    private Button btn_back;
    private TextView tv_details_title;
    private LinearLayout ll_me_data_name;
    private LinearLayout ll_me_data_sex;
    private LinearLayout ll_me_data_style;
    private LinearLayout ll_me_data_address;
    private TextView tv_me_data_name;
    private TextView tv_me_data_sex;
    private TextView tv_me_data_style;
    private AlertDialog alertDialog;
    private int sex_index ;
    private RequestManager glideRequest;
    private SwitchView sv_phone;

    //修改用户头像
    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    private static final int CROP_SMALL_PICTURE = 2;
    protected static Uri tempUri;
    private ImageView iv_personal_icon;
    private RequestQueue requestQueue;
    private String uniceName;
    private SwitchView.OnStateChangedListener SwitchButtonStateListener = new SwitchView.OnStateChangedListener() {
        @Override
        public void toggleToOn(SwitchView view) {
            //打开状态
            postData("0","1");
        }

        @Override
        public void toggleToOff(SwitchView view) {
           //关闭状态
            postData("1","0");
        }
    };
    private View.OnClickListener SwitchButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            boolean isOpened = sv_phone.isOpened();
        }
    };

    @Override
    public void setContentView() {
       setContentView(R.layout.act_me_edit);
        requestQueue = NoHttp.newRequestQueue();
    }

    @Override
    public void initViews() {

        String uicon = getIntent().getStringExtra("uicon");
        uniceName = getIntent().getStringExtra("uniceName");

        btn_back = (Button) findViewById(R.id.btn_back);
        tv_details_title = (TextView) findViewById(R.id.tv_details_title);
        iv_personal_icon = (ImageView) findViewById(R.id.iv_me_changeHead);
        ll_me_data_name = (LinearLayout) findViewById(R.id.ll_me_data_name);
        ll_me_data_sex = (LinearLayout) findViewById(R.id.ll_me_data_sex);
        ll_me_data_style = (LinearLayout) findViewById(R.id.ll_me_data_style);
        ll_me_data_address = (LinearLayout) findViewById(R.id.ll_me_data_address);

        tv_me_data_name = (TextView) findViewById(R.id.tv_me_data_name);
        tv_me_data_sex = (TextView) findViewById(R.id.tv_me_data_sex);
        tv_me_data_style = (TextView) findViewById(R.id.tv_me_data_style);

        sv_phone = (SwitchView) findViewById(R.id.sv_phone);

        tv_details_title.setText("个人资料");
        //进行数据回显
        Picasso.with(EditActivity.this).load(uicon)
                .resize(DensityUtils.dip2px(EditActivity.this,86),DensityUtils.dip2px(EditActivity.this,86))
                .centerCrop().into(iv_personal_icon);
        tv_me_data_name.setText(uniceName);
    }

    @Override
    public void initListeners() {
        btn_back.setOnClickListener(this);
        iv_personal_icon.setOnClickListener(this);
        ll_me_data_name.setOnClickListener(this);
        ll_me_data_sex.setOnClickListener(this);
        ll_me_data_style.setOnClickListener(this);
        ll_me_data_address.setOnClickListener(this);
        sv_phone.setOnStateChangedListener(SwitchButtonStateListener);
        sv_phone.setOnClickListener(SwitchButtonClickListener);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                finish();
                break;
            //修改头像
            case R.id.iv_me_changeHead:
                showChoosePicDialog();
                break;
            //修改昵称
            case R.id.ll_me_data_name:
                Intent intent = new Intent(EditActivity.this,AlterNameActivity.class);
                intent.putExtra("nuiceName",uniceName);
                startActivityForResult(intent,004);
                break;
            //收货地址
            case R.id.ll_me_data_address:
                Intent intent1 = new Intent(EditActivity.this, AddressActivity.class);
                startActivity(intent1);
                break;
            case R.id.ll_me_data_sex:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                View view = View.inflate(EditActivity.this,R.layout.dialog_sex,null);
                TextView sex_secret = (TextView) view.findViewById(R.id.sex_secret);
                TextView sex_women = (TextView) view.findViewById(R.id.sex_women);
                TextView sex_man = (TextView) view.findViewById(R.id.sex_man);
                sex_secret.setOnClickListener(this);
                sex_women.setOnClickListener(this);
                sex_man.setOnClickListener(this);
                builder.setView(view);
                alertDialog = builder.show();
                alertDialog.setCanceledOnTouchOutside(false);
                break;
            //修改个性签名
            case R.id.ll_me_data_style:
                Intent intent2 = new Intent(EditActivity.this,StyleActivity.class);
                startActivityForResult(intent2,005);
                break;
            case R.id.sex_secret:
                tv_me_data_sex.setText("保密");
                sex_index = 0;
                PrefUtils.setInt(this,"sex_index",0);
                alertDialog.dismiss();
                break;
            case R.id.sex_women:
                tv_me_data_sex.setText("女");
                sex_index = 1;
                PrefUtils.setInt(this,"sex_index",1);
                alertDialog.dismiss();
                break;
            case R.id.sex_man:
                tv_me_data_sex.setText("男");
                sex_index = 2;
                PrefUtils.setInt(this,"sex_index",2);
                alertDialog.dismiss();
                break;

        }
    }

    private void showChoosePicDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("设置头像");
        String[] items = { "选择本地照片", "拍照" };
        builder.setNegativeButton("取消", null);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case CHOOSE_PICTURE: // 选择本地照片
                        Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
                        openAlbumIntent.setType("image/*");
                        startActivityForResult(openAlbumIntent,CHOOSE_PICTURE);
                        break;
                    case TAKE_PICTURE: //拍照
                        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        tempUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory()
                                ,"headImage.jpg"));
                        // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
                        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
                        startActivityForResult(openCameraIntent, TAKE_PICTURE);
                        break;
                }
            }
        });
        builder.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==004&&resultCode==004){
            String name = data.getStringExtra("newName");
            tv_me_data_name.setText(name);
            PrefUtils.setString(this,"name",name);
        }else if (requestCode==005&&resultCode==005){
            String newStyle = data.getStringExtra("newStyle");
            tv_me_data_style.setText(newStyle);
            PrefUtils.setString(this,"newStyle",newStyle);
        }else if (requestCode == TAKE_PICTURE){
            startPhotoZoom(tempUri); // 开始对图片进行裁剪处理
        }else if (requestCode == CHOOSE_PICTURE){
            startPhotoZoom(data.getData()); // 开始对图片进行裁剪处理
        }else if (requestCode == CROP_SMALL_PICTURE){
            if (data != null) {
                setImageToView(data); // 让刚才选择裁剪得到的图片显示在界面上
            }
        }
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    protected void startPhotoZoom(Uri uri) {
        if (uri == null) {
            Log.i("tag", "The uri is not exist.");
        }
        tempUri = uri;
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_SMALL_PICTURE);
    }
    /**
     * 保存裁剪之后的图片数据
     * @param
     */
    protected void setImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            photo = MyUtils.toRoundBitmap(photo,tempUri);// 这个时候的图片已经被处理成圆形的了
            iv_personal_icon.setImageBitmap(photo);
            uploadPic(photo);
        }
    }

    private void uploadPic(Bitmap bitmap) {
        // 上传至服务器
        // ... 可以在这里把Bitmap转换成file，然后得到file的url，做文件上传操作
        // 注意这里得到的图片已经是圆形图片了
        // bitmap是没有做个圆形处理的，但已经被裁剪了

        String imagePath = MyUtils.savePhoto(bitmap, Environment
                .getExternalStorageDirectory().getAbsolutePath(), String
                .valueOf(System.currentTimeMillis()));
        PrefUtils.setString(EditActivity.this,"imagePath",imagePath);

        File file = new File(imagePath);
        Log.e("imagePath", imagePath+"");
            // 拿着imagePath上传了
            String apptoken = PrefUtils.getString(EditActivity.this, "apptoken", "");
            String url = "http://121.42.31.133:8201/m/api/tools/fileUploadapi.ashx";
//            Request<String> request = NoHttp.createStringRequest("http://121.42.31.133:8201/m/api/tools/fileUploadapi.ashx", RequestMethod.POST);
            StringRequest request = new StringRequest(url, RequestMethod.POST);
            request.setContentType("multipart/form-data");
            request.add("apptoken",apptoken);
            request.add("imgFile",new File(Environment.getExternalStorageDirectory()
                    ,"headImage.jpg"));
            request.add("filetype","image");
            request.add("optype","101");
            requestQueue.add(001, request, new OnResponseListener<String>() {
                @Override
                public void onStart(int what) {

                }

                @Override
                public void onSucceed(int what, Response<String> response) {
                    String info = response.get();
                    MyLog.testLog("上传头像"+info);
                }

                @Override
                public void onFailed(int what, Response<String> response) {

                }

                @Override
                public void onFinish(int what) {

                }
            });
    }

    private String StrToBinstr(String imagePath) {
        char[] strChar=imagePath.toCharArray();
        String result="";
        for(int i=0;i<strChar.length;i++){
             result +=Integer.toBinaryString(strChar[i]);
           }
        return result;
    }

    //手机号是否公开
    private void postData(String ctype,String cvalue){
        String apptoken = PrefUtils.getString(EditActivity.this, "apptoken", "");
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.LOGIN_URL, RequestMethod.POST);
        request.add("apptoken",apptoken);
        request.add("op","changeUserInfo");
        request.add("uid","null");
        request.add("ctype",ctype);
        request.add("cvalue",cvalue);
        requestQueue.add(POST_PHONE_STATE, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                MyLog.testLog("提交结果"+info);
                try {
                    JSONObject json = new JSONObject(info);
                    String resultCode = json.getString("resultCode");
                    int resultInt = Integer.parseInt(resultCode);
                    if (resultInt==1001){
                        Toast.makeText(EditActivity.this,"ctype 不在内定的参数范围.",Toast.LENGTH_SHORT).show();
                    }else if (resultInt==1002){
                        Toast.makeText(EditActivity.this,"提交的参数值不在规定的格式内或为空",Toast.LENGTH_SHORT).show();
                    }else if (resultInt==5001){
                        Toast.makeText(EditActivity.this,"服务器内部错误",Toast.LENGTH_SHORT).show();
                    }else if (resultInt==1){
                        Toast.makeText(EditActivity.this,"修改成功!",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
    };

}
