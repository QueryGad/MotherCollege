package com.player.mothercollege.me.details;

import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;
import com.player.mothercollege.R;
import com.player.mothercollege.activity.BaseActivity;
import com.player.mothercollege.bean.CirleArticleBean;
import com.player.mothercollege.me.HeadIconActivity;
import com.player.mothercollege.utils.DensityUtils;
import com.player.mothercollege.utils.ScreenUtils;
import com.player.mothercollege.view.GlideCircleTransform;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/29.
 * 帖子详情
 */
public class CirleArticleDetailsActivity extends BaseActivity implements View.OnClickListener {

    private Button btn_back;
    private TextView tv_details_title,tv_invit_title,tv_invit_name,tv_invit_time,tv_invit_viewCount,
            tv_invit_zan,tv_invit_comment,tv_invit_desc,tv_persondetails_nocomment,tv_person_othername,tv_person_othercontent;
    private ImageView iv_invit_head,iv_invit_right,iv_persondetails_nocomment;
    private GridView gr_invit_head;
    private RequestManager glideRequest;
    private CirleArticleBean.TrendsBean trendsBean;
    private NineGridView nineGrid;
    private ListView lv_details;

    @Override
    public void setContentView() {
         setContentView(R.layout.act_me_persondetails);
    }

    @Override
    public void initViews() {

        trendsBean = ( CirleArticleBean.TrendsBean) getIntent().getSerializableExtra("trendsBean");

        btn_back = (Button) findViewById(R.id.btn_back);
        tv_details_title = (TextView) findViewById(R.id.tv_details_title);
        tv_invit_title = (TextView) findViewById(R.id.tv_invit_title);
        iv_invit_head = (ImageView) findViewById(R.id.iv_invit_head);
        tv_invit_name = (TextView) findViewById(R.id.tv_invit_name);
        tv_invit_time = (TextView) findViewById(R.id.tv_invit_time);
        tv_invit_viewCount = (TextView) findViewById(R.id.tv_invit_viewCount);
        tv_invit_zan = (TextView) findViewById(R.id.tv_invit_zan);
        tv_invit_comment = (TextView) findViewById(R.id.tv_invit_comment);
        gr_invit_head = (GridView) findViewById(R.id.gr_invit_head);
        iv_invit_right = (ImageView) findViewById(R.id.iv_invit_right);
        tv_invit_desc = (TextView) findViewById(R.id.tv_invit_desc);
        iv_persondetails_nocomment = (ImageView) findViewById(R.id.iv_persondetails_nocomment);
        tv_persondetails_nocomment = (TextView) findViewById(R.id.tv_persondetails_nocomment);
        nineGrid = (NineGridView)findViewById(R.id.nineGrid);
        lv_details = (ListView) findViewById(R.id.lv_details);

        tv_details_title.setText("帖子详情");
    }

    @Override
    public void initListeners() {
        btn_back.setOnClickListener(this);
        iv_invit_right.setOnClickListener(this);
    }

    @Override
    public void initData() {
        if (trendsBean.getTitle()==""){
            tv_invit_title.setVisibility(View.GONE);
        }else {
            tv_invit_title.setText(trendsBean.getTitle());
        }
        glideRequest = Glide.with(CirleArticleDetailsActivity.this);
        glideRequest.load(trendsBean.getUicon())
                .transform(new GlideCircleTransform(CirleArticleDetailsActivity.this)).into(iv_invit_head);
        tv_invit_name.setText(trendsBean.getUniceName());
        tv_invit_time.setText("发布时间:"+trendsBean.getDatetime());
        tv_invit_viewCount.setText("浏览人数:"+trendsBean.getViewCount());
        tv_invit_zan.setText(trendsBean.getZlikes().size()+"");
        tv_invit_comment.setText(trendsBean.getReviews().size()+"");
        tv_invit_desc.setText(trendsBean.getContent());
        //九宫格图片
        ArrayList<ImageInfo> imageInfo = new ArrayList<>();
        List<String> imageDetails = trendsBean.getPics();
        if (imageDetails != null) {
            for (String imageDetail : imageDetails) {
                ImageInfo info = new ImageInfo();
                info.setThumbnailUrl(imageDetail);
                info.setBigImageUrl(imageDetail);
                imageInfo.add(info);
            }
        }
        int width = ScreenUtils.getWidth(CirleArticleDetailsActivity.this)/3;
        nineGrid.setSingleImageSize(width);
        nineGrid.setGridSpacing(DensityUtils.dip2px(CirleArticleDetailsActivity.this,5));
        nineGrid.setAdapter(new NineGridViewClickAdapter(CirleArticleDetailsActivity.this,imageInfo));

        haveNoComment();

        int length = DensityUtils.dip2px(CirleArticleDetailsActivity.this,15);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        float density = dm.density;
        int gridviewWidth = (int) (trendsBean.getZlikes().size() * (length + 4) * density);
        int itemWidth = (int) (length * density);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                gridviewWidth, LinearLayout.LayoutParams.FILL_PARENT);
        gr_invit_head.setLayoutParams(params); // 设置GirdView布局参数,横向布局的关键
        gr_invit_head.setColumnWidth(itemWidth); // 设置列表项宽
        gr_invit_head.setHorizontalSpacing(5); // 设置列表项水平间距
        gr_invit_head.setStretchMode(GridView.NO_STRETCH);
        gr_invit_head.setNumColumns(trendsBean.getZlikes().size()); // 设置列数量=列表集合数

        PersonDetailsAdapter adapter = new PersonDetailsAdapter();
        gr_invit_head.setAdapter(adapter);
    }

    /**
     * 下方是否有评论
     */
    private void haveNoComment() {
        List< CirleArticleBean.TrendsBean.ReviewsBean> reviewsList = trendsBean.getReviews();
        if (reviewsList.size()==0){
            iv_persondetails_nocomment.setVisibility(View.VISIBLE);
            tv_persondetails_nocomment.setVisibility(View.VISIBLE);
            lv_details.setVisibility(View.GONE);
        }else {
            iv_persondetails_nocomment.setVisibility(View.GONE);
            tv_persondetails_nocomment.setVisibility(View.GONE);
            lv_details.setVisibility(View.VISIBLE);
            DetailsConmmentAdapter adapter = new DetailsConmmentAdapter();
            lv_details.setAdapter(adapter);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                finish();
                break;
            case R.id.iv_invit_right:
                //点击移动
                break;
        }
    }

    class DetailsConmmentAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return trendsBean.getReviews().size();
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
            View view = View.inflate(CirleArticleDetailsActivity.this,R.layout.item_persondetails_comment,null);
            TextView tv_person_othername = (TextView) view.findViewById(R.id.tv_person_othername);
            TextView tv_person_othercontent = (TextView) view.findViewById(R.id.tv_person_othercontent);
            tv_person_othername.setText(trendsBean.getReviews().get(position).getUnicename()+":");
            tv_person_othercontent.setText(trendsBean.getReviews().get(position).getContent());
            return view;
        }
    }



    class PersonDetailsAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return trendsBean.getZlikes().size();
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
            View view = View.inflate(CirleArticleDetailsActivity.this, R.layout.item_details_personzan,null);
            ImageView iv_person_zan = (ImageView) view.findViewById(R.id.iv_person_zan);
            glideRequest = Glide.with(CirleArticleDetailsActivity.this);
            glideRequest.load(trendsBean.getZlikes().get(position).getUicon())
                    .transform(new GlideCircleTransform(CirleArticleDetailsActivity.this)).into(iv_person_zan);
            iv_person_zan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //点击头像进入他人主页
                    Intent intent = new Intent(CirleArticleDetailsActivity.this, HeadIconActivity.class);
                    startActivity(intent);
                }
            });
            return view;
        }
    }
}
