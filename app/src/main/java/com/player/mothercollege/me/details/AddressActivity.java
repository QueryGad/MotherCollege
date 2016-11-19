package com.player.mothercollege.me.details;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.player.mothercollege.R;
import com.player.mothercollege.activity.BaseActivity;
import com.player.mothercollege.bean.AddressListBean;
import com.player.mothercollege.utils.ConfigUtils;
import com.player.mothercollege.utils.MyLog;
import com.player.mothercollege.utils.PrefUtils;
import com.player.mothercollege.view.LeftSlideDeleteListView;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/25.
 * 收货地址
 */
public class AddressActivity extends BaseActivity implements View.OnClickListener {

    private static final int GET_ADDRESSLIST_DATA = 001;
    private static final int POST_DELETE_DATA = 002;
    private Button btn_back;
    private TextView tv_details_title;
    private LeftSlideDeleteListView lv_address;
    private RequestQueue requestQueue;
    private String apptoken;
    private String uid;
    private String aid;

    private LeftSlideDeleteListView.OnListViewItemDeleteClikListener DeleteListener = new LeftSlideDeleteListView.OnListViewItemDeleteClikListener() {
        @Override
        public void onListViewItemDeleteClick(int position) {
            //删除条目  联网上传数据
            deleteNetWork();
            myAddressList.remove(position);
            adapter.notifyDataSetChanged();
        }
    };

    private void deleteNetWork() {
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.LOGIN_URL, RequestMethod.POST);
        request.add("apptoken",apptoken);
        request.add("op","changeuseradd");
        request.add("ctype","2");
        request.add("uid",uid);
        request.add("linkphone","null");
        request.add("linkname","null");
        request.add("aid",aid);
        request.add("ad1","null");
        request.add("ad2","null");
        request.add("ad3","null");
        request.add("add_ext","null");
        request.add("isdefault","null");
        requestQueue.add(POST_DELETE_DATA, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                MyLog.testLog("删除地址:"+info);
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    private AdapterView.OnItemClickListener ItemListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //点击条目  选中为默认收货地址

        }
    };
    private List<AddressListBean.MyAddressBean> myAddressList = new ArrayList<>();
    private AddressListAdapter adapter;


    @Override
    public void setContentView() {
        setContentView(R.layout.act_me_address);
        requestQueue = NoHttp.newRequestQueue();
    }

    @Override
    public void initViews() {
        btn_back = (Button) findViewById(R.id.btn_back);
        tv_details_title = (TextView) findViewById(R.id.tv_details_title);
        lv_address = (LeftSlideDeleteListView) findViewById(R.id.lv_address);

        tv_details_title.setText("收货地址");
    }

    private void initHead() {
        View viewHead = View.inflate(this,R.layout.head_me_newaddress,null);
        RelativeLayout rl_add_address = (RelativeLayout) viewHead.findViewById(R.id.rl_add_address);
        rl_add_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddressActivity.this,PickerActivity.class);
                startActivity(intent);
            }
        });
        lv_address.addHeaderView(viewHead);
    }

    @Override
    public void initListeners() {
        btn_back.setOnClickListener(this);
    }

    @Override
    public void initData() {
        initHead();
        netWork();

    }

    private void netWork() {
        apptoken = PrefUtils.getString(this, "apptoken", "");
        uid = PrefUtils.getString(this, "uid", "null");
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.LOGIN_URL, RequestMethod.GET);
        request.add("apptoken", apptoken);
        request.add("uid", uid);
        request.add("op","useraddlist");
        requestQueue.add(GET_ADDRESSLIST_DATA, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                MyLog.testLog("收货地址列表"+info);
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

    private void parseJson(String info) {
        Gson gson = new Gson();
        AddressListBean addressListBean = gson.fromJson(info, AddressListBean.class);
        myAddressList = addressListBean.getMyAddress();
        adapter = new AddressListAdapter();
        lv_address.setAdapter(adapter);
        lv_address.setOnListViewItemDeleteClikListener(DeleteListener);
        lv_address.setOnItemClickListener(ItemListener);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btn_back:
                finish();
                break;


        }
    }

    private class AddressListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return myAddressList.size();
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
            AddressListHolder holder = null;
            if (convertView==null){
                view = View.inflate(AddressActivity.this, R.layout.item_me_adresslist,null);
                holder = new AddressListHolder();
                holder.tv_addresslist_name = (TextView) view.findViewById(R.id.tv_addresslist_name);
                holder.tv_addresslist_phone = (TextView) view.findViewById(R.id.tv_addresslist_phone);
                holder.tv_addresslist_adress = (TextView) view.findViewById(R.id.tv_addresslist_adress);
                view.setTag(holder);
            }else {
                view = convertView;
                holder = (AddressListHolder) view.getTag();
            }
            holder.tv_addresslist_name.setText(myAddressList.get(position).getLinkname());
            holder.tv_addresslist_phone.setText(myAddressList.get(position).getLinkphone());
            holder.tv_addresslist_adress.setText(myAddressList.get(position).getAdFull());
            holder.tv_addresslist_adress.setText(myAddressList.get(position).getAdFull());
            aid =  myAddressList.get(position).getAid();
            return view;
        }
    }

    private class AddressListHolder{

        private TextView tv_addresslist_name,tv_addresslist_phone,tv_addresslist_adress;

    }
}
