package com.player.mothercollege.unity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.player.mothercollege.R;

/**
 * Created by Administrator on 2016/10/25.
 * 好友页面
 */
public class GoodFrendFragment extends Fragment{

    private View view;
    private RadioGroup rg_unity_frend;
    private RadioButton rb_unity_message,rb_unity_addresslist,rb_unity_qunchat;
    private RadioGroup.OnCheckedChangeListener FrendOnCheckListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId){
                case R.id.rb_unity_message:
//                    Intent intent1 = new Intent(getActivity(), FrendMessageActivity.class);
//                    startActivity(intent1);
                    break;
                case R.id.rb_unity_addresslist:
//                    Intent intent2 = new Intent(getActivity(), ListAddressActivity.class);
//                    startActivity(intent2);
                    break;
                case R.id.rb_unity_qunchat:
//                    Intent intent3 = new Intent(getActivity(), QunChatActivity.class);
//                    startActivity(intent3);
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.frg_unity_goodfrend,null);
        initView();
        initData();
        return view;
    }

    private void initView() {
        rg_unity_frend = (RadioGroup) view.findViewById(R.id.rg_unity_frend);
        rb_unity_message = (RadioButton) view.findViewById(R.id.rb_unity_message);
        rb_unity_addresslist = (RadioButton) view.findViewById(R.id.rb_unity_addresslist);
        rb_unity_qunchat = (RadioButton) view.findViewById(R.id.rb_unity_qunchat);

        rg_unity_frend.setOnCheckedChangeListener(FrendOnCheckListener);
    }

    private void initData() {

    }

}
