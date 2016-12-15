package com.player.mothercollege.me.details;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.player.mothercollege.R;
import com.player.mothercollege.activity.BaseActivity;
import com.player.mothercollege.adapter.PhotoAdapter;
import com.player.mothercollege.utils.ConfigUtils;
import com.player.mothercollege.utils.ImagePostUtils;
import com.player.mothercollege.utils.MyLog;
import com.player.mothercollege.utils.MyUtils;
import com.player.mothercollege.utils.PhotoSmallUitls;
import com.player.mothercollege.utils.PrefUtils;
import com.player.mothercollege.view.RecyclerItemClickListener;
import com.yanzhenjie.permission.AndPermission;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;

/**
 * Created by Administrator on 2016/11/8.
 */
public class CirlePostMessageActivity extends BaseActivity implements View.OnClickListener {

    private static final int POST_MESSAGE_DATA = 001;
    private static final int GET_PERMISSIONS_PHONE_STATE = 002;
    private Button btn_back;
    private TextView tv_details_title,tv_postmessage_xianzhi;
    private EditText et_postmessage_title,et_postmessage_content;
    private Button btn_postmessage_camer;
    private Button btn_postmessage;
    private RecyclerView recycler_view;

    private PhotoAdapter photoAdapter;
    private ArrayList<String> selectedPhotos = new ArrayList<>();
    private ArrayList<String> postImageArr = new ArrayList<>();
    private View viewSelect;
    private int currentClickId = -1;
    private String groupId;
    private RequestQueue requestQueue;
    private ProgressDialog pd;
    private TextWatcher EditTextInPutListener = new TextWatcher() {

        private CharSequence temp;
        private int editStart;
        private int editEnd;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            temp = s;
        }

        @Override
        public void afterTextChanged(Editable s) {
            editStart = et_postmessage_title.getSelectionStart();
            editEnd = et_postmessage_title.getSelectionEnd();
            tv_postmessage_xianzhi.setText(temp.length()+"/15");
            tv_postmessage_xianzhi.setTextColor(Color.RED);
            if (temp.length()>15){
                Toast.makeText(CirlePostMessageActivity.this,"您输入的字数已经超过了限制!",Toast.LENGTH_SHORT).show();
                s.delete(editStart-1,editEnd);
                int tempSelection = editStart;
                et_postmessage_title.setText(s);
                et_postmessage_title.setSelection(tempSelection);
            }
        }
    };

    @Override
    public void setContentView() {
        setContentView(R.layout.act_me_postmessage);
        requestQueue = NoHttp.newRequestQueue();
    }

    @Override
    public void initViews() {
        groupId = getIntent().getStringExtra("groupId");
        btn_back = (Button) findViewById(R.id.btn_back);
        tv_details_title = (TextView) findViewById(R.id.tv_details_title);
        et_postmessage_title = (EditText) findViewById(R.id.et_postmessage_title);
        tv_postmessage_xianzhi = (TextView) findViewById(R.id.tv_postmessage_xianzhi);
        et_postmessage_content = (EditText) findViewById(R.id.et_postmessage_content);
        btn_postmessage_camer = (Button) findViewById(R.id.btn_postmessage_camer);
        btn_postmessage = (Button) findViewById(R.id.btn_postmessage);
        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);

        tv_details_title.setText("发帖");
    }

    @Override
    public void initListeners() {
        btn_back.setOnClickListener(this);
        btn_postmessage_camer.setOnClickListener(this);
        recycler_view.setOnClickListener(this);
        btn_postmessage.setOnClickListener(this);
        et_postmessage_title.addTextChangedListener(EditTextInPutListener);
    }

    @Override
    public void initData() {
        photoAdapter = new PhotoAdapter(this, selectedPhotos);
        recycler_view.setLayoutManager(new StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL));
        recycler_view.setAdapter(photoAdapter);
        recycler_view.addOnItemTouchListener(new RecyclerItemClickListener(CirlePostMessageActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                PhotoPreview.builder()
                        .setPhotos(selectedPhotos)
                        .setCurrentItem(position)
                        .start(CirlePostMessageActivity.this);
            }
        }));
    }

    List<String> photos = new ArrayList<>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK &&
                (requestCode == PhotoPicker.REQUEST_CODE || requestCode == PhotoPreview.REQUEST_CODE)) {


            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
            }
            selectedPhotos.clear();

            if (photos != null) {

                selectedPhotos.addAll(photos);
            }

            photoAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (currentClickId!=-1)onClick(viewSelect);
        } else {
            Toast.makeText(this, "No read storage permission! Cannot perform the action.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean shouldShowRequestPermissionRationale(@NonNull String permission) {
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                AlertDialog.Builder builder = new AlertDialog.Builder(CirlePostMessageActivity.this);
                builder.setTitle("温馨提示");
                builder.setMessage("确定放弃此次发帖?");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                builder.show();
                break;
            case R.id.btn_postmessage_camer:
                AndPermission.with(this).requestCode(GET_PERMISSIONS_PHONE_STATE)
                        .permission(Manifest.permission.CAMERA)
                        .send();
                PhotoPicker.builder()
                        .setPhotoCount(9)
                        .setGridColumnCount(4)
                        .start(this);
                break;
            case R.id.btn_postmessage:  //发布帖子
                String title = et_postmessage_title.getText().toString().trim();
                String content = et_postmessage_content.getText().toString().trim();
                if (TextUtils.isEmpty(content)){
                    Toast.makeText(CirlePostMessageActivity.this,"请填写内容!",Toast.LENGTH_SHORT).show();
                    break;
                }
                if (TextUtils.isEmpty(title)){
                    title = "";
                }
                postMessage(title,content);

                break;
        }

        viewSelect = v;
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CirlePostMessageActivity.this);
        builder.setTitle("放弃发帖");
        builder.setMessage("确定放弃此次操作?");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.show();
    }

    private  void postMessage(String title,String content){

        for (int i = 0;i<photos.size();i++){
//                postImageArr.clear();
            String photoLocation = photos.get(i).toString();  //所选图片路径
            //压缩图片
            //// TODO: 2016/12/15
            Bitmap bitmapFromPath = MyUtils.getBitmapFromPath(photoLocation);
            Bitmap bitmap = PhotoSmallUitls.compressImage(bitmapFromPath);
            String newPath = MyUtils.convertIconToString(bitmap);
            MyLog.testLog("新图片路径:"+newPath);
            String png = photoLocation.substring(photoLocation.lastIndexOf("."));
            png = png.substring(1,png.length());
            String location = ImagePostUtils.imageBASE64(photoLocation);//转换为64位编码格式的图片路径
            MyLog.testLog("转换后路径:"+location);
            String postImage = png+"|"+location;
            postImageArr.add(postImage);
        }


        String imgs="";
        for (int i =0;i<postImageArr.size();i++){
            String img = postImageArr.get(i);
                imgs = imgs+(img+"^");
        }

        if (imgs!=null&&imgs.length()>0){
            imgs = imgs.substring(0, imgs.length() - 1);
        }
        MyLog.testLog("最后:"+imgs);
        String apptoken = PrefUtils.getString(this, "apptoken", "");
        String uid = PrefUtils.getString(this, "uid", "");
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.ME_URL, RequestMethod.POST);
        request.add("apptoken",apptoken);
        request.add("op","PostTrend");
        request.add("uid",uid);
        request.add("gid",groupId);
        request.add("title",title);
        request.add("content",content);
        request.add("imgs",imgs);
        requestQueue.add(POST_MESSAGE_DATA, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
                pd = new ProgressDialog(CirlePostMessageActivity.this);
                pd.show();
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                MyLog.testLog("帖子上传是否成功:"+info);
                Toast.makeText(CirlePostMessageActivity.this,"发帖成功!",Toast.LENGTH_SHORT).show();
                pd.dismiss();
                //操作逻辑 上传成功后关闭编辑页面，刷新圈子显示发布
                finish();
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }
}
