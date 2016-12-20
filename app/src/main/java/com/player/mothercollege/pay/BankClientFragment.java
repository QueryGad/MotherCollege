package com.player.mothercollege.pay;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.gson.Gson;
import com.player.mothercollege.R;
import com.player.mothercollege.bean.BankListBean;
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
 * Created by Administrator on 2016/12/15.
 */
public class BankClientFragment extends Fragment implements View.OnClickListener {


    private static final int POST_BANK_LIST_DATA = 001;
    private View view;
    private FrameLayout fl_bank_clent;
    private RequestQueue requestQueue;
    private String apptoken;
    private String uid;

    private NoBankCardFragment noBankCardFragment;
    private HaveBankCardFragment haveBankCardFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.frg_pay_bank,null);
        requestQueue = NoHttp.newRequestQueue();
        initView();
        initData();
        return view;
    }

    private void initView() {
        fl_bank_clent = (FrameLayout) view.findViewById(R.id.fl_bank_clent);


        noBankCardFragment = new NoBankCardFragment();
        haveBankCardFragment = new HaveBankCardFragment();

    }

    private void initData() {
        netWork();
    }

    private void netWork() {
        apptoken = PrefUtils.getString(getActivity(), "apptoken", "");
        uid = PrefUtils.getString(getActivity(), "uid", "");
        Request<String> request = NoHttp.createStringRequest(ConfigUtils.ME_URL, RequestMethod.POST);
        request.add("apptoken",apptoken);
        request.add("op","userbanklist");
        request.add("uid",uid);
        requestQueue.add(POST_BANK_LIST_DATA, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String info = response.get();
                MyLog.testLog("银行卡列表:"+info);
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
        BankListBean bankListBean = gson.fromJson(info, BankListBean.class);
        List<BankListBean.UserBankCardListBean> userBankCardList = bankListBean.getUserBankCardList();
        if (userBankCardList.size()==0){
            //未添加银行卡  提示用户添加银行卡
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_bank_clent,noBankCardFragment).commit();

        }else {
            //以添加过银行卡  显示银行卡列表
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_bank_clent,haveBankCardFragment).commit();
        }
    }

//    private void initFoot(){
//        View viewFoot = View.inflate(getActivity(),R.layout.foot_pay_clent_bank,null);
//        TextView tv_bank_maxmoney = (TextView) viewFoot.findViewById(R.id.tv_bank_maxmoney);//可提现金额
//        TextView tv_bank_selfmoney = (TextView) viewFoot.findViewById(R.id.tv_bank_selfmoney);//本次可提
//        TextView tv_bank_allmoney = (TextView) viewFoot.findViewById(R.id.tv_bank_allmoney);//全部提现
//        EditText et_bank_getmoney = (EditText) viewFoot.findViewById(R.id.et_bank_getmoney);//本次提现金额
//        TextView tv_bank_othermoney = (TextView) viewFoot.findViewById(R.id.tv_bank_othermoney);//手续费
//        EditText et_bank_phone = (EditText) viewFoot.findViewById(R.id.et_bank_phone);//手机号
//        EditText et_bank_yanzheng = (EditText) viewFoot.findViewById(R.id.et_bank_yanzheng);//验证码
//        Button btn_bank_yanzhenma = (Button) viewFoot.findViewById(R.id.btn_bank_yanzhenma);//获取验证码
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }
    }

}
