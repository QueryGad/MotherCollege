package com.player.mothercollege.pay;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.player.mothercollege.R;

/**
 * Created by Administrator on 2016/12/17.
 */
public class NoBankCardFragment extends Fragment implements View.OnClickListener {

    private ImageView iv_nobankcard_add;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.frg_pay_nobankcard,null);
        initView();
        initData();
        return view;
    }

    private void initView() {
        iv_nobankcard_add = (ImageView) view.findViewById(R.id.iv_nobankcard_add);

        iv_nobankcard_add.setOnClickListener(this);
    }

    private void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_nobankcard_add:
                //添加银行卡
                Intent intent = new Intent(getActivity(),AddBankCardActivity.class);
                startActivity(intent);
                break;
        }
    }
}
