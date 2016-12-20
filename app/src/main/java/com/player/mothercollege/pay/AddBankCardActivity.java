package com.player.mothercollege.pay;

import android.app.ProgressDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.player.mothercollege.R;
import com.player.mothercollege.activity.BaseActivity;
import com.player.mothercollege.bean.ProvinceBean;
import com.player.mothercollege.me.details.JsonFileReader;
import com.player.mothercollege.utils.ConfigUtils;
import com.player.mothercollege.utils.MyLog;
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

/**
 * Created by Administrator on 2016/12/17.
 */
public class AddBankCardActivity extends BaseActivity implements View.OnClickListener {

    private static final int POST_ADDBANK_DATA = 001;
    private Button btn_back;
    private TextView tv_details_title,btn_addbank_ok;
    private EditText et_addbankcard_name,et_addbankcard_number,et_addbankcard_bank;
    private TextView tv_addbankcard_address;
    private OptionsPickerView pvOptions;
    private ProgressDialog pd;
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
    private RequestQueue requestQueue;

    @Override
    public void setContentView() {
        setContentView(R.layout.act_pay_add_bank_card);
        requestQueue = NoHttp.newRequestQueue();

    }

    @Override
    public void initViews() {
        btn_back = (Button) findViewById(R.id.btn_back);
        tv_details_title = (TextView) findViewById(R.id.tv_details_title);
        btn_addbank_ok = (TextView) findViewById(R.id.btn_addbank_ok);
        et_addbankcard_name = (EditText) findViewById(R.id.et_addbankcard_name);
        et_addbankcard_number = (EditText) findViewById(R.id.et_addbankcard_number);
        et_addbankcard_bank = (EditText) findViewById(R.id.et_addbankcard_bank);
        tv_addbankcard_address = (TextView) findViewById(R.id.tv_addbankcard_address);

        tv_details_title.setText("添加银行卡");
    }

    @Override
    public void initListeners() {
        btn_back.setOnClickListener(this);
        tv_addbankcard_address.setOnClickListener(this);
        btn_addbank_ok.setOnClickListener(this);
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
                tv_addbankcard_address.setText(address);
                sheng =  provinceBeanList.get(options1).getPickerViewText();
                shi = cityList.get(options1).get(option2);
                xian = districtList.get(options1).get(option2).get(options3);
            }
        });

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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                finish();
                break;
            case R.id.tv_addbankcard_address://三级联动
                ((InputMethodManager)getSystemService(INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(AddBankCardActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                pvOptions.show();
                break;
            case R.id.btn_addbank_ok:
                String name = et_addbankcard_name.getText().toString().trim();
                String number = et_addbankcard_number.getText().toString().trim();
                String bankname = et_addbankcard_bank.getText().toString().trim();
                //判空
                if (TextUtils.isEmpty(name)&&TextUtils.isEmpty(number)&&TextUtils.isEmpty(bankname)){
                    Toast.makeText(AddBankCardActivity.this,"请将信息填写完整",Toast.LENGTH_SHORT).show();
                    break;
                }
                netWork(name,number,bankname);
                break;
        }
    }

    private void netWork(String name, String number, String bankname) {
        String apptoken = PrefUtils.getString(this, "apptoken", "");
        String uid = PrefUtils.getString(this, "uid", "");
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.ME_URL, RequestMethod.POST);
        request.add("apptoken",apptoken);
        request.add("op","addUserBank");
        request.add("uid",uid);
        request.add("Bankid",number);
        request.add("bankUname",name);
        request.add("bankcreateAdd",bankname);
        request.add("bankadd1",sheng);
        request.add("bankadd2",shi);
        request.add("bankadd3",xian);
        requestQueue.add(POST_ADDBANK_DATA, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
                 pd = new ProgressDialog(AddBankCardActivity.this);
                 pd.show();
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                MyLog.testLog("添加银行卡:"+info);
                pd.dismiss();
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                pd.dismiss();
            }

            @Override
            public void onFinish(int what) {
                pd.dismiss();
            }
        });
    }
}
