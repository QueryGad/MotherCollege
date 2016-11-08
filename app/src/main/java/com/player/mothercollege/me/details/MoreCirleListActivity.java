package com.player.mothercollege.me.details;

import android.app.ProgressDialog;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.gson.Gson;
import com.player.mothercollege.R;
import com.player.mothercollege.activity.BaseActivity;
import com.player.mothercollege.bean.MoreCirleBean;
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
 * Created by Administrator on 2016/11/4.
 * 更多圈子列表
 */
public class MoreCirleListActivity extends BaseActivity{

    private static final int POST_INJOIN_DATA = 001;
    private RequestQueue requestQueue;
    private ListView lv_title,lv_content;
    private ProgressDialog pd;
    private TextView tv_title;
    private RequestManager glideRequest;
    private List<MoreCirleBean.GroupWithClassBean> groupWithClass = new ArrayList<>();
    List<MoreCirleBean.GroupWithClassBean.GroupsBean> groupsList = new ArrayList<>();
    private AdapterView.OnItemClickListener TitleItemListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //内容listview
            groupsList =  groupWithClass.get(position).getGroups();
            contentAdapter = new ContentAdapter();
            lv_content.setAdapter(contentAdapter);
        }
    };
    private ContentAdapter contentAdapter;

    @Override
    public void setContentView() {
       setContentView(R.layout.act_me_morecirle);
        requestQueue = NoHttp.newRequestQueue();
    }

    @Override
    public void initViews() {
        lv_title = (ListView) findViewById(R.id.lv_title);
        lv_content = (ListView) findViewById(R.id.lv_content);
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {
        netWork();
    }

    private void netWork() {

        Request<String> request = NoHttp.createStringRequest(ConfigUtils.ME_URL);
        request.add("uid","null");
        request.add("apptoken","sefsefa");
        request.add("op","moreGroup");
        requestQueue.add(001, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
                pd = new ProgressDialog(MoreCirleListActivity.this);
                pd.show();
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                MyLog.testLog("更多圈子:"+info);
                parseJson(info);
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
        MoreCirleBean moreCirleBean = gson.fromJson(info, MoreCirleBean.class);
        groupWithClass = moreCirleBean.getGroupWithClass();
        TitleAdapter adapter = new TitleAdapter();
        lv_title.setAdapter(adapter);

        groupsList =  groupWithClass.get(0).getGroups();

        contentAdapter = new ContentAdapter();
        lv_content.setAdapter(contentAdapter);
        lv_title.setOnItemClickListener(TitleItemListener);
    }

    class TitleAdapter extends BaseAdapter {

        private List<MoreCirleBean.GroupWithClassBean.GroupsBean> groupsList;

        @Override
        public int getCount() {
            return groupWithClass.size();
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
            View view = View.inflate(MoreCirleListActivity.this,R.layout.item_morecirle_title,null);
            tv_title = (TextView) view.findViewById(R.id.tv_title);
            tv_title.setText(groupWithClass.get(position).getGroupClass().getClassName());

            return view;
        }
    }

    class ContentAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return groupsList.size();
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
            View view = null;
            ContentHolder holder = null;
            if (convertView==null){
                view = View.inflate(MoreCirleListActivity.this,R.layout.item_morecirle_content,null);
                holder = new ContentHolder();
                holder.iv_head = (ImageView) view.findViewById(R.id.iv_head);
                holder.tv_content_title = (TextView) view.findViewById(R.id.tv_content_title);
                holder.tv_viewCount = (TextView) view.findViewById(R.id.tv_viewCount);
                holder.btn_join = (Button) view.findViewById(R.id.btn_more_join);
                holder.rl_morecirle = (RelativeLayout) view.findViewById(R.id.rl_morecirle);
                view.setTag(holder);
            }else {
                view = convertView;
                holder = (ContentHolder) view.getTag();
            }
            glideRequest = Glide.with(MoreCirleListActivity.this);
            glideRequest.load(groupsList.get(position).getIcon())
                    .transform(new GlideCircleTransform(MoreCirleListActivity.this)).into(holder.iv_head);
            holder.tv_content_title.setText(groupsList.get(position).getGroupName());
            holder.tv_viewCount.setText(groupsList.get(position).getJoinCount()+"");
            boolean hasJoin = groupsList.get(position).isHasJoin();
            if (hasJoin){
                //我加入过的群
                holder.btn_join.setBackgroundResource(R.mipmap.icon_join);
            }else {
                //我没有加入的群
                holder.btn_join.setBackgroundResource(R.mipmap.icon_2_join);
            }
            //点击加入按钮显示已加入 有holder不能用
            final String groupId = groupsList.get(position).getGroupId();
            holder.btn_join.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    holder.btn_join.setBackgroundResource(R.mipmap.icon_join);
                    netWork(groupId);
                }
            });
            //点击进入圈子详情
            holder.rl_morecirle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //点击进入圈子
                    Intent intent = new Intent(MoreCirleListActivity.this,CirleDetailsActivity.class);
                    intent.putExtra("groupId",groupId);
                    startActivity(intent);
                }
            });
            return view;
        }
    }

    class ContentHolder{
        private ImageView iv_head;
        private TextView tv_content_title,tv_viewCount;
        private Button btn_join;
        private RelativeLayout rl_morecirle;
    }

    private void netWork(String groupId){
        String apptoken = PrefUtils.getString(MoreCirleListActivity.this, "apptoken", "");
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.ME_URL, RequestMethod.POST);
        request.add("apptoken",apptoken);
        request.add("op","joinGroup");
        request.add("uid","null");
        request.add("groupNos",groupId);
        requestQueue.add(POST_INJOIN_DATA, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
                pd = new ProgressDialog(MoreCirleListActivity.this);
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
