package com.player.mothercollege.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;
import com.player.mothercollege.R;
import com.player.mothercollege.bean.PersonDynamicBean;
import com.player.mothercollege.unity.details.HotArticleDetailsActivity;
import com.player.mothercollege.utils.ConfigUtils;
import com.player.mothercollege.utils.DateUtils;
import com.player.mothercollege.utils.DensityUtils;
import com.player.mothercollege.utils.MyLog;
import com.player.mothercollege.utils.PrefUtils;
import com.player.mothercollege.utils.ScreenUtils;
import com.player.mothercollege.view.GlideCircleTransform;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/26.
 */
public class PersonAdapter extends BaseAdapter{

    private Context context;
    private RequestManager glideRequest;
    private List<PersonDynamicBean.TrendsBean> lists = new ArrayList<>();
    private String selfuid;
    private String apptoken;

    public PersonAdapter(Context context, List lists,String uid) {
        super();
        this.context = context;
        this.lists = lists;
        this.selfuid = uid;
        apptoken = PrefUtils.getString(context, "apptoken", "");
    }



    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;
        PersonHolder ph = null;
        if (convertView==null){
            view = View.inflate(context, R.layout.item_person,null);
            ph = new PersonHolder();
            ph.ll_person = (LinearLayout) view.findViewById(R.id.ll_person);
            ph.iv_head = (ImageView) view.findViewById(R.id.iv_head);
            ph.tv_name = (TextView) view.findViewById(R.id.tv_name);
            ph.tv_time = (TextView) view.findViewById(R.id.tv_time);
            ph.tv_address = (TextView) view.findViewById(R.id.tv_address);
            ph.tv_title = (TextView) view.findViewById(R.id.tv_title);
            ph.tv_desc = (TextView) view.findViewById(R.id.tv_desc);
            ph.nineGrid = (NineGridView) view.findViewById(R.id.nineGrid);
            ph.tv_person_zan = (TextView) view.findViewById(R.id.tv_person_zan);
            ph.tv_person_comment = (TextView) view.findViewById(R.id.tv_person_comment);
            ph.tv_person_delete = (TextView) view.findViewById(R.id.tv_person_delete);
            view.setTag(ph);
        }else {
            view = convertView;
            ph = (PersonHolder) view.getTag();
        }
        glideRequest = Glide.with(context);
        glideRequest.load(lists.get(position).getUicon())
                .transform(new GlideCircleTransform(context)).into(ph.iv_head);
        ph.tv_name.setText(lists.get(position).getUniceName());
        String datetime = lists.get(position).getDatetime();
        ph.tv_time.setText(DateUtils.getStandardDate(datetime));
        ph.tv_address.setText(lists.get(position).getFrom());
        if (lists.get(position).getTitle()==""){
            ph.tv_title.setVisibility(View.GONE);
        }else {
            ph.tv_title.setText(lists.get(position).getTitle());
        }
        ph.tv_desc.setText(lists.get(position).getContent());
        //点赞
        List<PersonDynamicBean.TrendsBean.ZlikesBean> zlikesList = lists.get(position).getZlikes();
        ph.tv_person_zan.setText(zlikesList.size()+"");
        //评论
        List<PersonDynamicBean.TrendsBean.ReviewsBean> reviewsList = lists.get(position).getReviews();
        ph.tv_person_comment.setText(reviewsList.size()+"");
        //九宫格图片
        ArrayList<ImageInfo> imageInfo = new ArrayList<>();
        List<String> imageDetails = lists.get(position).getPics();
        if (imageDetails != null) {
            for (String imageDetail : imageDetails) {
                ImageInfo info = new ImageInfo();
                info.setThumbnailUrl(imageDetail);
                info.setBigImageUrl(imageDetail);
                imageInfo.add(info);
            }
        }
        int width = ScreenUtils.getWidth(context)/3;
        ph.nineGrid.setSingleImageSize(width);
        ph.nineGrid.setGridSpacing(DensityUtils.dip2px(context,5));
        ph.nineGrid.setAdapter(new NineGridViewClickAdapter(context,imageInfo));
        //点击进入详情页面
        final String tid = lists.get(position).getTid();
        ph.ll_person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,HotArticleDetailsActivity.class);
                intent.putExtra("tid",tid);
                context.startActivity(intent);
            }
        });
        //删除
        String uid = lists.get(position).getUid();

        if (selfuid.equals(uid)){
            ph.tv_person_delete.setVisibility(View.VISIBLE);
            ph.tv_person_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("确认删除此贴?");

                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deleteArticle(tid);
                            lists.remove(position);
                            notifyDataSetChanged();
                        }
                    });
                    builder.show();
                }
            });
        }else {
            ph.tv_person_delete.setVisibility(View.GONE);
        }

        return view;
    }

    private void deleteArticle(String tid) {
        RequestQueue requestQueue = NoHttp.newRequestQueue();
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.ME_URL, RequestMethod.POST);
        request.add("op","DeleTetrend");
        request.add("uid",selfuid);
        request.add("trendno",tid);
        request.add("apptoken",apptoken);
        MyLog.testLog("删帖参数: uid:"+selfuid+",trendno:"+tid+",apptoken:"+apptoken);
        requestQueue.add(001, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                MyLog.testLog("删除帖子:"+info);
                notifyDataSetChanged();
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    class PersonHolder{
        public LinearLayout ll_person;
        public ImageView iv_head;
        public TextView tv_name,tv_time,tv_address,tv_title,tv_desc,tv_person_zan,tv_person_comment,tv_person_delete;
        public NineGridView nineGrid;
    }
}
