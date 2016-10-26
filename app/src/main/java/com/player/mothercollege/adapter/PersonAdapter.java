package com.player.mothercollege.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;
import com.player.mothercollege.R;
import com.player.mothercollege.bean.PersonDynamicBean;
import com.player.mothercollege.utils.DensityUtils;
import com.player.mothercollege.utils.ScreenUtils;
import com.player.mothercollege.view.GlideCircleTransform;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/26.
 */
public class PersonAdapter extends BaseAdapter{

    private Context context;
    private RequestManager glideRequest;
    private List<PersonDynamicBean.TrendsBean> lists = new ArrayList<>();

    public PersonAdapter(Context context, List lists) {
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
        PersonHolder ph = null;
        if (convertView==null){
            view = View.inflate(context, R.layout.item_person,null);
            ph = new PersonHolder();
            ph.iv_head = (ImageView) view.findViewById(R.id.iv_head);
            ph.tv_name = (TextView) view.findViewById(R.id.tv_name);
            ph.tv_time = (TextView) view.findViewById(R.id.tv_time);
            ph.tv_address = (TextView) view.findViewById(R.id.tv_address);
            ph.tv_title = (TextView) view.findViewById(R.id.tv_title);
            ph.tv_desc = (TextView) view.findViewById(R.id.tv_desc);
            ph.nineGrid = (NineGridView) view.findViewById(R.id.nineGrid);
            view.setTag(ph);
        }else {
            view = convertView;
            ph = (PersonHolder) view.getTag();
        }
        glideRequest = Glide.with(context);
        glideRequest.load(lists.get(position).getUicon())
                .transform(new GlideCircleTransform(context)).into(ph.iv_head);
        ph.tv_name.setText(lists.get(position).getTitle());
        ph.tv_time.setText(lists.get(position).getDatetime());
        ph.tv_address.setText(lists.get(position).getFrom());
        if (lists.get(position).getTitle()==""){
            ph.tv_title.setVisibility(View.GONE);
        }else {
            ph.tv_title.setText(lists.get(position).getTitle());
        }
        ph.tv_desc.setText(lists.get(position).getContent());
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
        ph.nineGrid.setSingleImageSize(width);
        ph.nineGrid.setGridSpacing(DensityUtils.dip2px(context,5));
        ph.nineGrid.setAdapter(new NineGridViewClickAdapter(context,imageInfo));
        return view;
    }

    class PersonHolder{
        public ImageView iv_head;
        public TextView tv_name,tv_time,tv_address,tv_title,tv_desc;
        public NineGridView nineGrid;
    }
}
