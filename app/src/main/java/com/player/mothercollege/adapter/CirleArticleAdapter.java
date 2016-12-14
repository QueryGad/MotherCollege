package com.player.mothercollege.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;
import com.player.mothercollege.R;
import com.player.mothercollege.bean.CirleArticleBean;
import com.player.mothercollege.unity.details.HotArticleDetailsActivity;
import com.player.mothercollege.utils.DensityUtils;
import com.player.mothercollege.utils.PrefUtils;
import com.player.mothercollege.utils.ScreenUtils;
import com.player.mothercollege.view.GlideCircleTransform;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/3.
 */
public class CirleArticleAdapter extends BaseAdapter{

    private Context context;
    private RequestManager glideRequest;
    private List<CirleArticleBean.TrendsBean> lists = new ArrayList<>();

    public CirleArticleAdapter(Context context,List lists) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;
        CirleArticleHolder holder = null;
        if (convertView==null){
            view = View.inflate(context, R.layout.item_cirledetails_article,null);
            holder = new CirleArticleHolder();
            holder.ll_cirlearticle = (LinearLayout) view.findViewById(R.id.ll_cirlearticle);
            holder.iv_cirlearticle_head = (ImageView) view.findViewById(R.id.iv_cirlearticle_head);
            holder.tv_cirlearticle_name = (TextView) view.findViewById(R.id.tv_cirlearticle_name);
            holder.tv_cirlearticle_time = (TextView) view.findViewById(R.id.tv_cirlearticle_time);
            holder.tv_cirlearticle_address = (TextView) view.findViewById(R.id.tv_cirlearticle_address);
            holder.tv_cirlearticle_title = (TextView) view.findViewById(R.id.tv_cirlearticle_title);
            holder.tv_cirlearticle_desc = (TextView) view.findViewById(R.id.tv_cirlearticle_desc);
            holder.tv_cirlearticle_zan = (TextView) view.findViewById(R.id.tv_cirlearticle_zan);
            holder.tv_cirlearticle_comment = (TextView) view.findViewById(R.id.tv_cirlearticle_comment);
            holder.tv_cirlearticle_detele = (TextView) view.findViewById(R.id.tv_cirlearticle_detele);
            holder.nineGrid = (NineGridView) view.findViewById(R.id.nineGrid);
            view.setTag(holder);
        }else {
            view = convertView;
            holder = (CirleArticleHolder) view.getTag();
        }
        glideRequest = Glide.with(context);
        glideRequest.load(lists.get(position).getUicon())
                .transform(new GlideCircleTransform(context)).into(holder.iv_cirlearticle_head);
        holder.tv_cirlearticle_name.setText(lists.get(position).getUniceName());
        holder.tv_cirlearticle_time.setText(lists.get(position).getDatetime());
        holder.tv_cirlearticle_address.setText(lists.get(position).getFrom());
        if (lists.get(position).getTitle()==""){
            holder.tv_cirlearticle_title.setVisibility(View.GONE);
        }else {
            holder.tv_cirlearticle_title.setVisibility(View.VISIBLE);
        }
        holder.tv_cirlearticle_title.setText(lists.get(position).getTitle());
        holder.tv_cirlearticle_desc.setText(lists.get(position).getContent());
        List<CirleArticleBean.TrendsBean.ZlikesBean> zlikesList = lists.get(position).getZlikes();//点赞
        holder.tv_cirlearticle_zan.setText(zlikesList.size()+"");
        List<CirleArticleBean.TrendsBean.ReviewsBean> reviewsList = lists.get(position).getReviews();//评论
        holder.tv_cirlearticle_comment.setText(reviewsList.size()+"");
        //九宫格图片
        ArrayList<ImageInfo> imageInfo = new ArrayList<>();
        List<String> imageDetails = lists.get(position).getPics();
        if (imageDetails != null) {
            for (String imageDetail : imageDetails) {
                ImageInfo info = new ImageInfo();
                info.setThumbnailUrl(imageDetail);
                info.setBigImageUrl(imageDetail);
                imageInfo.add(info);
            }
        }
        int width = ScreenUtils.getWidth(context)/3;
        holder.nineGrid.setSingleImageSize(width);
        holder.nineGrid.setGridSpacing(DensityUtils.dip2px(context,5));
        holder.nineGrid.setAdapter(new NineGridViewClickAdapter(context,imageInfo));
        //判断帖子是否是自己写的如果是自己写的可以进行删除
        String myuid = PrefUtils.getString(context, "myuid", "null");
        String uid = lists.get(position).getUid();
        if (uid==myuid){
            holder.tv_cirlearticle_detele.setVisibility(View.VISIBLE);
        }else {
            holder.tv_cirlearticle_detele.setVisibility(View.INVISIBLE);
        }
        holder.tv_cirlearticle_detele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击删除对自己的帖子进行删除
                Toast.makeText(context,"我把我的帖子删了",Toast.LENGTH_SHORT).show();
            }
        });
        final String tid = lists.get(position).getTid();
        //点击进入详情页面
        holder.ll_cirlearticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,HotArticleDetailsActivity.class);
                intent.putExtra("tid",tid);
                context.startActivity(intent);
            }
        });
        return view;
    }

    class CirleArticleHolder{
        public LinearLayout ll_cirlearticle;
        public ImageView iv_cirlearticle_head;
        public TextView tv_cirlearticle_name,tv_cirlearticle_time,tv_cirlearticle_address,
                tv_cirlearticle_title,tv_cirlearticle_desc,tv_cirlearticle_zan,tv_cirlearticle_comment,tv_cirlearticle_detele;
        public NineGridView nineGrid;
    }
}
