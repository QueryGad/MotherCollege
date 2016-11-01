package com.player.mothercollege.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.player.mothercollege.R;
import com.player.mothercollege.bean.CirleBean;
import com.player.mothercollege.utils.DensityUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/31.
 */
public class CirleAdapter extends BaseAdapter{

    private Context context;
    private List<CirleBean> list = new ArrayList<>();
    private List<CirleBean.MaybeLikeGroupsBean> maybeLikeGroupsBeenList = new ArrayList<>();//感兴趣的圈子

    public CirleAdapter(Context context,List maybeLikeGroupsBeenList) {
        super();
        this.context = context;
        this.maybeLikeGroupsBeenList = maybeLikeGroupsBeenList;
    }

    @Override
    public int getCount() {
        return maybeLikeGroupsBeenList.size();
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
        CirleHolder ch = null;
        if (convertView==null){
            view = View.inflate(context, R.layout.item_cirle_maybe,null);
            ch = new CirleHolder();
            ch.btn_cirle_checkAll = (Button) view.findViewById(R.id.btn_cirle_checkAll);
            ch.iv_cirle_head = (ImageView) view.findViewById(R.id.iv_cirle_head);
            ch.btn_cirle_check = (Button) view.findViewById(R.id.btn_cirle_check);
            ch.tv_cirle_name = (TextView) view.findViewById(R.id.tv_cirle_name);
            ch.tv_cirle_viewCount = (TextView) view.findViewById(R.id.tv_cirle_viewCount);
            ch.tv_cirle_more = (TextView) view.findViewById(R.id.tv_cirle_more);
            view.setTag(ch);
        }else {
            view = convertView;
            ch = (CirleHolder) view.getTag();
        }
        Picasso.with(context).load(maybeLikeGroupsBeenList.get(position).getIcon())
                .resize(DensityUtils.dip2px(context,44),DensityUtils.dip2px(context,44))
                .centerCrop().into(ch.iv_cirle_head);
        ch.tv_cirle_name.setText(maybeLikeGroupsBeenList.get(position).getGroupName());
        ch.tv_cirle_viewCount.setText(maybeLikeGroupsBeenList.get(position).getJoinCount()+"");
        if (position==maybeLikeGroupsBeenList.size()-1){
            ch.tv_cirle_more.setVisibility(View.VISIBLE);
        }else {
            ch.tv_cirle_more.setVisibility(View.GONE);
        }
        boolean hasJoin = maybeLikeGroupsBeenList.get(position).isHasJoin();
        if (hasJoin){//已加入
            ch.btn_cirle_check.setBackgroundResource(R.mipmap.icon_join);
        }else {//未加入
            ch.btn_cirle_check.setBackgroundResource(R.mipmap.icon_2_join);
            ch.btn_cirle_check.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,"我该变状态",Toast.LENGTH_SHORT).show();
                }
            });
        }

        return view;
    }

    class CirleHolder{
        private ImageView iv_cirle_head;
        private TextView tv_cirle_name,tv_cirle_viewCount,tv_cirle_more;
        private Button btn_cirle_checkAll,btn_cirle_check;
    }
}
