package com.player.mothercollege.pay;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_pay_bank_ok:  //确定

                break;
        }
    }
}
