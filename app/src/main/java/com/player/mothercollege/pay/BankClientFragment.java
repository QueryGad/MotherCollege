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

import com.player.mothercollege.R;

/**
 * Created by Administrator on 2016/12/15.
 */
public class BankClientFragment extends Fragment implements View.OnClickListener {


    private View view;
    private ListView lv_pay_bank;
    private Button btn_pay_bank_ok;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.frg_pay_bank,null);
        initView();
        initData();
        return view;
    }

    private void initView() {
        lv_pay_bank = (ListView) view.findViewById(R.id.lv_pay_bank);
        btn_pay_bank_ok = (Button) view.findViewById(R.id.btn_pay_bank_ok);

        btn_pay_bank_ok.setOnClickListener(this);
    }

    private void initData() {

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


        lv_pay_bank.addFooterView(viewFoot);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_pay_bank_ok:  //确定

                break;
        }
    }

}
