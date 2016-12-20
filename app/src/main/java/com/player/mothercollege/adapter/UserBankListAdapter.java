package com.player.mothercollege.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.player.mothercollege.R;
import com.player.mothercollege.bean.UserBankListBean;
import com.player.mothercollege.pay.AddBankCardActivity;
import com.player.mothercollege.utils.DensityUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/19.
 */
public class UserBankListAdapter extends BaseAdapter{

    private Context context;
    private List<UserBankListBean.UserBankCardListBean> lists = new ArrayList<>();


    public UserBankListAdapter(Context context,List lists) {
        super();
        this.context = context;
        this.lists = lists;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        UserBankListHolder holder = null;
        if (convertView==null){
            view = View.inflate(context, R.layout.frg_pay_havebanklist,null);
            holder = new UserBankListHolder();
            holder.iv_havebank_icon = (ImageView) view.findViewById(R.id.iv_havebank_icon);
            holder.tv_havebank_name = (TextView) view.findViewById(R.id.tv_havebank_name);
            holder.tv_havebank_mowei = (TextView) view.findViewById(R.id.tv_havebank_mowei);
            holder.iv_havebank_cardtype = (ImageView) view.findViewById(R.id.iv_havebank_cardtype);
            holder.item_cb_section = (CheckBox) view.findViewById(R.id.item_cb_section);
            holder.ll_havebank_add = (LinearLayout) view.findViewById(R.id.ll_havebank_add);

            view.setTag(holder);
        }else {
           view = convertView;
           holder = (UserBankListHolder) view.getTag();
        }
        if (lists.get(position).getBankIcon()==""){
            //不做处理
        }else {
            Picasso.with(context).load(lists.get(position).getBankIcon())
                    .resize(DensityUtils.dip2px(context,27),DensityUtils.dip2px(context,27))
                    .centerCrop().into(holder.iv_havebank_icon);
        }

        holder.tv_havebank_name.setText(lists.get(position).getBankName());
        holder.tv_havebank_mowei.setText(lists.get(position).getCardNo());

        if (position==lists.size()-1){
            holder.ll_havebank_add.setVisibility(View.VISIBLE);
            holder.ll_havebank_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //添加银行卡
                    Intent intent = new Intent(context,AddBankCardActivity.class);
                    context.startActivity(intent);
                }
            });

        }else {
            holder.ll_havebank_add.setVisibility(View.GONE);
        }
        return view;
    }

    class UserBankListHolder {
        private ImageView iv_havebank_icon;  // 银行卡图标
        private TextView tv_havebank_name;//银行名称
        private TextView tv_havebank_mowei;//尾号
        private ImageView iv_havebank_cardtype;//卡种
        private CheckBox item_cb_section;
        private LinearLayout ll_havebank_add;
    }
}
