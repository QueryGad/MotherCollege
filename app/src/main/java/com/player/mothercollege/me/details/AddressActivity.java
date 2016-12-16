package com.player.mothercollege.me.details;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.gson.Gson;
import com.player.mothercollege.R;
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
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/10/25.
 * 收货地址
 */
public class AddressActivity extends Activity implements View.OnClickListener {

    private static final int GET_LESTADDRESS_DATA = 001;
    private static final int POST_DELETE_DATA = 002;
    private RequestQueue requestQueue;
    private LeftSlideDeleteListView lv;
    private Button btn_back;
    private TextView tv_details_title;
    private TextView tv_address_add;
    private List<AddressListBean.MyAddressBean> myAddressList = new ArrayList<>();
    private String aid;
    private LeftSlideDeleteListView.OnListViewItemDeleteClikListener DeleteItemListener = new LeftSlideDeleteListView.OnListViewItemDeleteClikListener() {
        @Override
        public void onListViewItemDeleteClick(int position) {
            myAddressList.remove(position);
            deleteNetWork();
            adapter.notifyDataSetChanged();
        }
    };

    private void deleteNetWork() {
        String apptoken = PrefUtils.getString(this, "apptoken", "");
        String uid = PrefUtils.getString(this, "uid", "null");
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.LOGIN_URL, RequestMethod.POST);
        request.add("apptoken",apptoken);
        request.add("op","changeuseradd");
        request.add("ctype","2");
        request.add("uid",uid);
        request.add("aid",aid);
        request.add("linkphone","null");
        request.add("linkname","null");
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

    private AddressAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_me_address);
        requestQueue = NoHttp.newRequestQueue();
        initView();
        initData();
    }

    private void initView() {
        btn_back = (Button) findViewById(R.id.btn_back);
        tv_details_title = (TextView) findViewById(R.id.tv_details_title);
        lv = (LeftSlideDeleteListView) findViewById(R.id.lv_address);
        tv_address_add = (TextView) findViewById(R.id.tv_address_add);

        tv_details_title.setText("收货地址");
        btn_back.setOnClickListener(this);
        tv_address_add.setOnClickListener(this);
    }

    private void initData() {
        netWork();
    }

    private void netWork() {
        String apptoken = PrefUtils.getString(this, "apptoken", "");
        String uid = PrefUtils.getString(this, "uid", "");
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.LOGIN_URL, RequestMethod.GET);
        request.add("apptoken",apptoken);
        request.add("uid",uid);
        request.add("op","useraddlist");
        requestQueue.add(GET_LESTADDRESS_DATA, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                Log.e("收货地址",info);
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
        adapter = new AddressAdapter();
        lv.setAdapter(adapter);
        lv.setOnListViewItemDeleteClikListener(DeleteItemListener);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                finish();
                break;
            case R.id.tv_address_add:
                Intent intent = new Intent(AddressActivity.this,PickerActivity.class);
                startActivityForResult(intent,100);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100&&requestCode==100){
            String name = data.getStringExtra("name");
            String phone = data.getStringExtra("phone");
            String details = data.getStringExtra("details");
            String sheng = data.getStringExtra("sheng");
            String shi = data.getStringExtra("shi");
            String xian = data.getStringExtra("xian");
            String moren = data.getStringExtra("moren");
            AddressListBean.MyAddressBean bean = new AddressListBean.MyAddressBean();
            bean.setLinkname(name);
            bean.setLinkphone(phone);
//            private boolean isdefault;
            bean.setAd1(sheng);
            bean.setAd2(shi);
            bean.setAd3(xian);
            bean.setAdFull(sheng+shi+xian+details);
            if (moren.equals("1")){
                //默认收货地址   地址列表打勾
                //TOdo
            }else {

            }
            myAddressList.add(bean);
            adapter.notifyDataSetChanged();
            lv.setSelection(lv.getBottom());
        }
    }

    class AddressAdapter extends BaseAdapter {

        private HashMap<String,Boolean> states = new HashMap<>();

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
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = null;
            AddressListHolder holder =new AddressListHolder();
            if (convertView==null){
                view = View.inflate(AddressActivity.this,R.layout.item_me_adresslist,null);
                holder = new AddressListHolder();
                holder.tv_addresslist_name = (TextView) view.findViewById(R.id.tv_addresslist_name);
                holder.tv_addresslist_phone = (TextView) view.findViewById(R.id.tv_addresslist_phone);
                holder.tv_addresslist_adress = (TextView) view.findViewById(R.id.tv_addresslist_adress);
                holder.checkBox = (CheckBox) view.findViewById(R.id.item_cb_section);
                view.setTag(holder);
            }else {
                view = convertView;
                holder = (AddressListHolder) view.getTag();
            }
            holder.tv_addresslist_name.setText(myAddressList.get(position).getLinkname());
            holder.tv_addresslist_phone.setText(myAddressList.get(position).getLinkphone());
            holder.tv_addresslist_adress.setText(myAddressList.get(position).getAdFull());
            final CheckBox radio = (CheckBox) view.findViewById(R.id.item_cb_section);
            holder.checkBox = radio;
            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (String key: states.keySet()){
                        states.put(key,false);
                    }
                    states.put(String.valueOf(position),radio.isChecked());
                    AddressAdapter.this.notifyDataSetChanged();
                }
            });
            boolean res = false;
            if (states.get(String.valueOf(position))==null
                    ||states.get(String.valueOf(position))==false){
                res = false;
                states.put(String.valueOf(position),false);
            }else
                res = true;
            holder.checkBox.setChecked(res);

            aid = myAddressList.get(position).getAid();
            return view;
        }
    }

    private class AddressListHolder{
        private TextView tv_addresslist_name,tv_addresslist_phone,tv_addresslist_adress;
        private CheckBox checkBox;
    }


}
