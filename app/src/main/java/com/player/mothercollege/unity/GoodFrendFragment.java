package com.player.mothercollege.unity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.player.mothercollege.R;

/**
 * Created by Administrator on 2016/10/25.
 * 好友页面
 */
public class GoodFrendFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.frg_unity_goodfrend,null);
        return view;
    }
}