package com.player.mothercollege.unity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.player.mothercollege.R;
import com.player.mothercollege.unity.details.FastFragment;
import com.player.mothercollege.unity.details.ZhuanJiaFragment;

/**
 * Created by Administrator on 2016/10/25.
 * 咨询页面
 */
public class InquiryFragment extends Fragment implements View.OnClickListener {

    private Button btn_zixun_fast,btn_zixun_zhuanjia;
    private FastFragment fastFragment;
    private ZhuanJiaFragment zhuanjiaFragment;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.frg_unity_inquiry,null);
        initView();
        initData();
        return view;
    }

    private void initView() {
        btn_zixun_fast = (Button) view.findViewById(R.id.btn_zixun_fast);
        btn_zixun_zhuanjia = (Button) view.findViewById(R.id.btn_zixun_zhuanjia);

        fastFragment = new FastFragment();
        zhuanjiaFragment = new ZhuanJiaFragment();

        btn_zixun_fast.setOnClickListener(this);
        btn_zixun_zhuanjia.setOnClickListener(this);

        //默认显示快速咨询
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fl_zixun,fastFragment).commit();
    }

    private void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_zixun_fast:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_zixun,fastFragment).commit();
                btn_zixun_zhuanjia.setBackgroundResource(R.mipmap.bg_expertreference);
                btn_zixun_fast.setBackgroundResource(R.mipmap.bg_quickreference);
                break;
            case R.id.btn_zixun_zhuanjia:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_zixun,zhuanjiaFragment).commit();
                btn_zixun_zhuanjia.setBackgroundResource(R.mipmap.bg_expertreference_nor);
                btn_zixun_fast.setBackgroundResource(R.mipmap.bg_quickreference_nor);
                break;
        }
    }
}
