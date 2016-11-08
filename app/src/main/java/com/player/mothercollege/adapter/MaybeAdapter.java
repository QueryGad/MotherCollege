package com.player.mothercollege.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.player.mothercollege.R;
import com.player.mothercollege.bean.CirleBean;
import com.player.mothercollege.me.details.CirleDetailsActivity;
import com.player.mothercollege.me.details.MoreCirleListActivity;
import com.player.mothercollege.utils.ConfigUtils;
import com.player.mothercollege.utils.MyLog;
import com.player.mothercollege.utils.PrefUtils;
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
 * Created by Administrator on 2016/11/3.
 */
public class MaybeAdapter extends BaseAdapter{

    private static final int POST_INJOIN_DATA = 001;
    private Context context;
    private List<CirleBean.MaybeLikeGroupsBean> maybeList = new ArrayList<>();
    private RequestManager glideRequest;
    private ProgressDialog pd;
    private final RequestQueue requestQueue;

    public MaybeAdapter(Context context,List lists) {
        super();
        this.context = context;
        this.maybeList = lists;
        requestQueue = NoHttp.newRequestQueue();
    }

    @Override
    public int getCount() {
        return maybeList.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(context, R.layout.item_me_maybecirle,null);
        TextView tv_maybe_title = (TextView) view.findViewById(R.id.tv_maybecirle_title);
        TextView tv_maybe_name = (TextView) view.findViewById(R.id.tv_maybecirle_name);
        TextView tv_maybe_num = (TextView) view.findViewById(R.id.tv_maybecirle_num);
        ImageView iv_maybe_icon = (ImageView) view.findViewById(R.id.iv_maybecirle_icon);
        final Button btn_join = (Button) view.findViewById(R.id.btn_join);
        LinearLayout ll_maybe = (LinearLayout) view.findViewById(R.id.ll_maybecirle);
        Button btn_moreCirle = (Button) view.findViewById(R.id.btn_moreCirle);
        RelativeLayout rl_click = (RelativeLayout) view.findViewById(R.id.rl_click);

        if (position==0){
            ll_maybe.setVisibility(View.VISIBLE);
        }else {
            ll_maybe.setVisibility(View.GONE);
        }
        tv_maybe_name.setText(maybeList.get(position).getGroupName());
        tv_maybe_num.setText(maybeList.get(position).getJoinCount()+"");
        glideRequest = Glide.with(context);
        glideRequest.load(maybeList.get(position).getIcon())
                .transform(new GlideCircleTransform(context)).into(iv_maybe_icon);
        if (position==maybeList.size()-1){
            btn_moreCirle.setVisibility(View.VISIBLE);
        }else {
            btn_moreCirle.setVisibility(View.GONE);
        }
        final String groupId = maybeList.get(position).getGroupId();
        //点击加入按钮,申请加入
        btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //上传服务器显示加入
                netWork(groupId);
                btn_join.setBackgroundResource(R.mipmap.icon_join);
            }
        });

        //点击进入页面详情  未加入隐藏编辑按钮
        rl_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CirleDetailsActivity.class);
                intent.putExtra("groupId",groupId);
                context.startActivity(intent);
            }
        });


        //点击更多推荐圈子展示圈子列表
        btn_moreCirle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,MoreCirleListActivity.class);
                context.startActivity(intent);
            }
        });
        return view;
    }

    private void netWork(String groupId){
        String apptoken = PrefUtils.getString(context, "apptoken", "");
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.ME_URL, RequestMethod.POST);
        request.add("apptoken",apptoken);
        request.add("op","joinGroup");
        request.add("uid","null");
        request.add("groupNos",groupId);
        requestQueue.add(POST_INJOIN_DATA, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
                pd = new ProgressDialog(context);
                pd.show();
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                MyLog.testLog("是否加入成功:"+info);
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
}
