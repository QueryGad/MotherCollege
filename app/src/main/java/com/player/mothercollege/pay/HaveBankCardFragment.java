package com.player.mothercollege.pay;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.player.mothercollege.R;
import com.player.mothercollege.adapter.UserBankListAdapter;
import com.player.mothercollege.bean.UserBankListBean;
import com.player.mothercollege.utils.ConfigUtils;
import com.player.mothercollege.utils.MyLog;
import com.player.mothercollege.utils.PrefUtils;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

import java.util.List;

/**
 * Created by Administrator on 2016/12/17.
 */
public class HaveBankCardFragment extends Fragment{

    private static final int POST_BANK_LIST = 001;
    private View view;
    private ListView lv_havebank;
    private Button btn_havebank_ok;
    private RequestQueue requestQueue;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.frg_pay_havebank,null);
        requestQueue = NoHttp.newRequestQueue();
        initView();
        initData();
        return view;
    }

    private void initView() {
        lv_havebank = (ListView) view.findViewById(R.id.lv_havebank);
        btn_havebank_ok = (Button) view.findViewById(R.id.btn_havebank_ok);


    }

    private void initData() {
        netWork();
    }

    private void netWork() {
        String apptoken = PrefUtils.getString(getActivity(), "apptoken", "");
        String uid = PrefUtils.getString(getActivity(), "uid", "");
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.ME_URL, RequestMethod.POST);
        request.add("apptoken",apptoken);
        request.add("op","userbanklist");
        request.add("uid",uid);
        requestQueue.add(POST_BANK_LIST, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                MyLog.testLog("提现界面数据:"+info);
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
        UserBankListBean userBankListBean = gson.fromJson(info, UserBankListBean.class);
        List<UserBankListBean.UserBankCardListBean> userBankCardList = userBankListBean.getUserBankCardList();
        UserBankListAdapter adapter = new UserBankListAdapter(getActivity(),userBankCardList);
        lv_havebank.setAdapter(adapter);
        initFoot();
    }

    private void initFoot(){
        View viewFoot = View.inflate(getActivity(),R.layout.foot_pay_clent_bank,null);
        TextView tv_bank_maxmoney = (TextView) viewFoot.findViewById(R.id.tv_bank_maxmoney);//可提现金额
        TextView tv_bank_selfmoney = (TextView) viewFoot.findViewById(R.id.tv_bank_selfmoney);//本次可提
        TextView tv_bank_allmoney = (TextView) viewFoot.findViewById(R.id.tv_bank_allmoney);//全部提现
        EditText et_bank_getmoney = (EditText) viewFoot.findViewById(R.id.et_bank_getmoney);//本次提现金额
        TextView tv_bank_othermoney = (TextView) viewFoot.findViewById(R.id.tv_bank_othermoney);//手续费
        EditText et_bank_phone = (EditText) viewFoot.findViewById(R.id.et_bank_phone);//手机号
        EditText et_bank_yanzheng = (EditText) viewFoot.findViewById(R.id.et_bank_yanzheng);//验证码
        Button btn_bank_yanzhenma = (Button) viewFoot.findViewById(R.id.btn_bank_yanzhenma);//获取验证码
        lv_havebank.addFooterView(viewFoot);
    }
}
