package com.player.mothercollege.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.player.mothercollege.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/12/20.
 * 下拉刷新
 */
public class PullToRefreshListView extends ListView{

    private static final int STATE_PULL_TO_REFRESH = 1;//开始刷新
    private static final int STATE_RELEASE_TO_REFRESH = 2;//松开刷新
    private static final int STATE__REFRESHING = 3;//刷新中

    private int mCurrentState = STATE_PULL_TO_REFRESH;

    private View mHeaderView;
    private int measuredHeight;
    private int startY =-1;
    private int endY;
    private ImageView iv_pull_arrow;
    private ProgressBar pb_pull_loading;
    private TextView tv_pull_title;
    private TextView tv_pull_time;
    private RotateAnimation animUp;
    private RotateAnimation animDown;

    public PullToRefreshListView(Context context) {
        super(context);
        initHeaderView();
    }

    public PullToRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initHeaderView();
    }

    public PullToRefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHeaderView();
    }

    /**
     * 初始化头布局
     */
    private void initHeaderView(){
        mHeaderView = View.inflate(getContext(), R.layout.pull_to_refresh,null);
        this.addHeaderView(mHeaderView);


        iv_pull_arrow = (ImageView) mHeaderView.findViewById(R.id.iv_pull_arrow);
        pb_pull_loading = (ProgressBar) mHeaderView.findViewById(R.id.pb_pull_loading);
        tv_pull_title = (TextView) mHeaderView.findViewById(R.id.tv_pull_title);
        tv_pull_time = (TextView) mHeaderView.findViewById(R.id.tv_pull_time);

        initAnim();
        
        //隐藏头布局
        mHeaderView.measure(0,0);
        measuredHeight = mHeaderView.getMeasuredHeight();
        mHeaderView.setPadding(0,-measuredHeight,0,0);

        setCurrentTime();
    }

    /**
     * 设置刷新时间
     */
    private void setCurrentTime(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(new Date());
        tv_pull_time.setText(time);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                startY = (int) ev.getY();

                break;
            case MotionEvent.ACTION_MOVE:
                if (startY==-1){
                    startY = (int) ev.getY();
                }
                if (mCurrentState==STATE__REFRESHING){

                    break;
                }
                endY = (int) ev.getY();
                int dy = endY - startY;
                int firstVisiblePosition = getFirstVisiblePosition();
                //必须下拉 并且当前显示的是第一个item
                if (dy>0&&firstVisiblePosition==0){
                     int padding = dy - measuredHeight;//计算当前下拉距离
                     mHeaderView.setPadding(0,padding,0,0);

                    if (padding>0&&mCurrentState!=STATE_RELEASE_TO_REFRESH){
                        //松开刷新
                        mCurrentState = STATE_RELEASE_TO_REFRESH;
                        refreshState();
                    }else if (padding<0&&mCurrentState!=STATE_PULL_TO_REFRESH){
                        //下拉刷新
                        mCurrentState = STATE_PULL_TO_REFRESH;
                        refreshState();
                    }
                     return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                startY = -1;
                if (mCurrentState==STATE_RELEASE_TO_REFRESH){
                    mCurrentState = STATE__REFRESHING;
                    refreshState();
                    //完整展示头布局
                    mHeaderView.setPadding(0,0,0,0);
                    //4进行回调
                    if (mListner!=null){
                        mListner.onRefresh();
                    }

                }else {
                    //隐藏头布局
                    mHeaderView.setPadding(0,-measuredHeight,0,0);
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 初始化箭头动画
     */
    private void initAnim(){
        animUp = new RotateAnimation(0,-180, Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF,0.5f);
        animUp.setDuration(200);
        animUp.setFillAfter(true);
        animDown = new RotateAnimation(-180,0, Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF,0.5f);
        animDown.setDuration(200);
        animDown.setFillAfter(true);
    }

    //刷新页面
    private void refreshState() {
        switch (mCurrentState){
            case STATE_PULL_TO_REFRESH:
                tv_pull_title.setText("下拉刷新");
                iv_pull_arrow.startAnimation(animDown);
                pb_pull_loading.setVisibility(INVISIBLE);
                iv_pull_arrow.setVisibility(VISIBLE);
                break;
            case STATE_RELEASE_TO_REFRESH:
                tv_pull_title.setText("松开刷新");
                iv_pull_arrow.startAnimation(animUp);
                pb_pull_loading.setVisibility(INVISIBLE);
                iv_pull_arrow.setVisibility(VISIBLE);
                break;
            case STATE__REFRESHING:
                tv_pull_title.setText("正在刷新......");
                iv_pull_arrow.clearAnimation();//清除动画才能隐藏
                pb_pull_loading.setVisibility(VISIBLE);
                iv_pull_arrow.setVisibility(INVISIBLE);
                break;
        }
    }

    //刷新结束  收起控件
    public void onRefreshComplete(boolean success){

            mHeaderView.setPadding(0,-measuredHeight,0,0);
            mCurrentState = STATE_PULL_TO_REFRESH;
            tv_pull_title.setText("下拉刷新");
            pb_pull_loading.setVisibility(INVISIBLE);
            iv_pull_arrow.setVisibility(VISIBLE);
        if (success){
            setCurrentTime();
        }

    }

    //3.定义成员变量监听的对象
    private OnRefreshListener mListner;

    //2.暴露接口设置监听

    public void setOnRefreshListener(OnRefreshListener listener){
        mListner = listener;
    }
    /**
     * 1.下拉刷新接口
     */
    public interface OnRefreshListener{
        public void onRefresh();
    }
}
