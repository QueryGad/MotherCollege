package com.player.mothercollege.me.details;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.player.mothercollege.R;
import com.player.mothercollege.activity.BaseActivity;
import com.player.mothercollege.bean.ProvinceBean;
import com.player.mothercollege.utils.ConfigUtils;
import com.player.mothercollege.utils.PrefUtils;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ch.ielse.view.SwitchView;

public class PickerActivity extends BaseActivity implements View.OnClickListener {

    private static final int POST_ADDRESS_DATA = 001;
    private Button btn_back;
    private TextView tv_address_ok,tv_address_picker;
    private EditText et_address_name,et_address_phone,et_address_details;
    private SwitchView btn_address_ischeck;
    private AlertDialog dialog;
    private RequestQueue requestQueue;
    private OptionsPickerView pvOptions;
    //  省份
    ArrayList<ProvinceBean> provinceBeanList = new ArrayList<>();
    //  城市
    ArrayList<String> cities;
    ArrayList<List<String>> cityList = new ArrayList<>();
    //  区/县
    ArrayList<String> district;
    ArrayList<List<String>> districts;
    ArrayList<List<List<String>>> districtList = new ArrayList<>();
    private String address;
    private String sheng;
    private String shi;
    private String xian;
    private String moren = "1";
    private SwitchView.OnStateChangedListener SwitchButtonStateListener = new SwitchView.OnStateChangedListener() {
        @Override
        public void toggleToOn(SwitchView view) {
            //打开状态
            view.toggleSwitch(true);
            //默认地址
            moren = "1";
        }

        @Override
        public void toggleToOff(SwitchView view) {
            //关闭状态
            view.toggleSwitch(false);
            moren = "0";
        }
    };

    @Override
    public void setContentView() {
        setContentView(R.layout.act_me_picker);
        requestQueue = NoHttp.newRequestQueue();
    }

    @Override
    public void initViews() {
        btn_back = (Button) findViewById(R.id.btn_back);
        tv_address_picker = (TextView) findViewById(R.id.tv_address_picker);
        tv_address_ok = (TextView) findViewById(R.id.tv_address_ok);
        et_address_name = (EditText) findViewById(R.id.et_address_name);
        et_address_phone = (EditText) findViewById(R.id.et_address_phone);
        et_address_details = (EditText) findViewById(R.id.et_address_details);
        btn_address_ischeck = (SwitchView) findViewById(R.id.btn_address_ischeck);

    }

    @Override
    public void initListeners() {
        btn_back.setOnClickListener(this);
        tv_address_ok.setOnClickListener(this);
        tv_address_picker.setOnClickListener(this);
        btn_address_ischeck.setOnStateChangedListener(SwitchButtonStateListener);
    }

    @Override
    public void initData() {
        //  创建选项选择器
        pvOptions = new OptionsPickerView(this);
        //  获取json数据
        String province_data_json = JsonFileReader.getJson(this, "province_data.json");
        //  解析json数据
        parseJson(province_data_json);
        //  设置三级联动效果
        pvOptions.setPicker(provinceBeanList, cityList, districtList, true);
        //  设置选择的三级单位
        //pvOptions.setLabels("省", "市", "区");
        //pvOptions.setTitle("选择城市");

        //  设置是否循环滚动
        pvOptions.setCyclic(false, false, false);
        // 设置默认选中的三级项目
        pvOptions.setSelectOptions(0, 0, 0);
        //  监听确定选择按钮
        //  监听确定选择按钮
        pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //返回的分别是三个级别的选中位置
                String city = provinceBeanList.get(options1).getPickerViewText();
                //  如果是直辖市或者特别行政区只设置市和区/县
                if ("北京市".equals(city) || "上海市".equals(city) || "天津市".equals(city) || "重庆市".equals(city) || "澳门".equals(city) || "香港".equals(city)) {
                    address = provinceBeanList.get(options1).getPickerViewText()
                            + " " + districtList.get(options1).get(option2).get(options3);
                } else {
                    address = provinceBeanList.get(options1).getPickerViewText()
                            + " " + cityList.get(options1).get(option2)
                            + " " + districtList.get(options1).get(option2).get(options3);
                }
                tv_address_picker.setText(address);
                sheng =  provinceBeanList.get(options1).getPickerViewText();
                shi = cityList.get(options1).get(option2);
                xian = districtList.get(options1).get(option2).get(options3);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                final AlertDialog.Builder builder = new AlertDialog.Builder(PickerActivity.this);
                builder.setTitle("温馨提示");
                builder.setMessage("确认取消此次操作?");
                builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                dialog = builder.show();
                break;
            case R.id.tv_address_ok:    //确定，联网提交
                String name = et_address_name.getText().toString().trim();//收货人
                String phone = et_address_phone.getText().toString().trim();//联系电话
                String details = et_address_details.getText().toString().trim();//详细地址
                if (TextUtils.isEmpty(name)||TextUtils.isEmpty(name)||TextUtils.isEmpty(name)){
                    Toast.makeText(PickerActivity.this,"信息不完整，请补全您的信息!",Toast.LENGTH_SHORT).show();
                    return;
                }
                netWork(name,phone,details);
                break;
            case R.id.tv_address_picker:  //三级联动
                pvOptions.show();
                break;

        }
    }


    public void parseJson(String str) {
        try {
            //  获取json中的数组
            JSONArray jsonArray = new JSONArray(str);
            //  遍历数据组
            for (int i = 0; i < jsonArray.length(); i++) {
                //  获取省份的对象
                JSONObject provinceObject = jsonArray.optJSONObject(i);
                //  获取省份名称放入集合
                String provinceName = provinceObject.getString("name");
                provinceBeanList.add(new ProvinceBean(provinceName));
                //  获取城市数组
                JSONArray cityArray = provinceObject.optJSONArray("city");
                cities = new ArrayList<>();//   声明存放城市的集合
                districts = new ArrayList<>();//声明存放区县集合的集合
                //  遍历城市数组
                for (int j = 0; j < cityArray.length(); j++) {
                    //  获取城市对象
                    JSONObject cityObject = cityArray.optJSONObject(j);
                    //  将城市放入集合
                    String cityName = cityObject.optString("name");
                    cities.add(cityName);
                    district = new ArrayList<>();// 声明存放区县的集合
                    //  获取区县的数组
                    JSONArray areaArray = cityObject.optJSONArray("area");
                    //  遍历区县数组，获取到区县名称并放入集合
                    for (int k = 0; k < areaArray.length(); k++) {
                        String areaName = areaArray.getString(k);
                        district.add(areaName);
                    }
                    //  将区县的集合放入集合
                    districts.add(district);
                }
                //  将存放区县集合的集合放入集合
                districtList.add(districts);
                //  将存放城市的集合放入集合
                cityList.add(cities);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void netWork(String name,String phone,String details) {
        String apptoken = PrefUtils.getString(this, "apptoken", "");
        String uid = PrefUtils.getString(this, "uid", "null");
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.LOGIN_URL, RequestMethod.POST);
        request.add("apptoken",apptoken);
        request.add("op","changeuseradd");
        request.add("ctype","0");
        request.add("uid",uid);
        request.add("aid","");
        request.add("linkphone",phone);
        request.add("linkname",name);
        request.add("ad1",sheng);
        Log.e("========",sheng);
        request.add("ad2",shi);
        Log.e("========",shi);
        request.add("ad3",xian);
        Log.e("========",xian);
        request.add("add_ext",details);
        request.add("isdefault",moren);
        requestQueue.add(POST_ADDRESS_DATA, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                Log.e("========",info);
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("温馨提示");
        builder.setMessage("确定放弃此次操作?");
        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        dialog = builder.show();
    }

}
