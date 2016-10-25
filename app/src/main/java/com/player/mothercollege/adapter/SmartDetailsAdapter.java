package com.player.mothercollege.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.player.mothercollege.R;
import com.player.mothercollege.bean.SmartMoneyBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/18.
 */
public class SmartDetailsAdapter extends RecyclerView.Adapter<SmartDetailsAdapter.SmartDetailsHolder>{

    private List<SmartMoneyBean.DetailBean> lists = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context context;
    private OnItemClickListener listener;
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public SmartDetailsAdapter(Context context, List lists) {
        super();
        this.context = context;
        this.lists = lists;
    }

    @Override
    public SmartDetailsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mInflater = LayoutInflater.from(parent.getContext());
        View view = mInflater.inflate(R.layout.item_me_glodedetails,null);
        return new SmartDetailsHolder(view);
    }

    @Override
    public void onBindViewHolder(SmartDetailsHolder holder, int position) {
        holder.tv_glodedetails_title.setText(lists.get(position).getInfo());
        holder.tv_glodedetails_date.setText(lists.get(position).getLogDate());
        if (lists.get(position).getChangeType()==0){
            holder.tv_glodedetails_num.setText("-"+lists.get(position).getChangeNum());
        }else {
            holder.tv_glodedetails_num.setText("+"+lists.get(position).getChangeNum());
        }
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    class SmartDetailsHolder extends RecyclerView.ViewHolder{
        private TextView tv_glodedetails_title,tv_glodedetails_date,tv_glodedetails_num;
        public SmartDetailsHolder(View itemView) {
            super(itemView);
            tv_glodedetails_title = (TextView) itemView.findViewById(R.id.tv_glodedetails_title);
            tv_glodedetails_date = (TextView) itemView.findViewById(R.id.tv_glodedetails_date);
            tv_glodedetails_num = (TextView) itemView.findViewById(R.id.tv_glodedetails_num);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener!=null){
                        listener.onClick(v,getLayoutPosition(),lists.get(getLayoutPosition()));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onClick(View v, int position, SmartMoneyBean.DetailBean data);
    }
}
