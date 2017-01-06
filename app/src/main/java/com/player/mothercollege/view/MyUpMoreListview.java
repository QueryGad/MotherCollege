package com.player.mothercollege.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.player.mothercollege.R;

/**
 * Created by Administrator on 2017/1/6.
 */
public class MyUpMoreListview extends ListView implements AbsListView.OnScrollListener{

    private static final String TAG = "RefreshListView";
    private int firstVisibleItemPosition; // 屏幕显示在第一个的item的索引

    private int downY; // 按下时y轴的偏移量
    private OnRefreshListener mOnRefershListener;
    private boolean isScrollToBottom; // 是否滑动到底部
    private View footerView; // 脚布局的对象
    private int footerViewHeight; // 脚布局的高度
    private boolean isLoadingMore = false; // 是否正在加载更多中


    public MyUpMoreListview(Context context, AttributeSet attrs) {
        super(context, attrs);
        initFooterView();
        this.setOnScrollListener(this);
    }

    /**
     * 初始化脚布局
     */
    private void initFooterView() {
        footerView = View.inflate(getContext(), R.layout.listview_footer, null);
        footerView.measure(0, 0);
        footerViewHeight = footerView.getMeasuredHeight();
        footerView.setPadding(0, -footerViewHeight, 0, 0);
        this.addFooterView(footerView);
    }



    /**
     * 当滚动状态改变时回调
     */
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

        if (scrollState == SCROLL_STATE_IDLE
                || scrollState == SCROLL_STATE_FLING) {
            // 判断当前是否已经到了底部
            if (isScrollToBottom && !isLoadingMore) {
                isLoadingMore = true;
                // 当前到底部
                Log.i(TAG, "加载更多数据");
                footerView.setVisibility(View.VISIBLE);
                footerView.setPadding(0, 0, 0, 0);
                this.setSelection(this.getCount());

                if (mOnRefershListener != null) {
                    mOnRefershListener.onLoadingMore();
                }
            }
        }
    }
    /**
     * 当滚动时调用
     *
     * @param firstVisibleItem
     *            当前屏幕显示在顶部的item的position
     * @param visibleItemCount
     *            当前屏幕显示了多少个条目的总数
     * @param totalItemCount
     *            ListView的总条目的总数
     */
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        firstVisibleItemPosition = firstVisibleItem;

        if (getLastVisiblePosition() == (totalItemCount - 1)) {
            isScrollToBottom = true;
        } else {
            isScrollToBottom = false;
        }
    }

    /**
     * 设置刷新监听事件
     *
     * @param listener
     */
    public void setOnRefreshListener(OnRefreshListener listener) {
        mOnRefershListener = listener;
    }

    /**
     * 隐藏脚布局
     */
    public void hideFooterView() {
        Log.d("上拉加载", "hideFooterView被执行");
        footerView.setPadding(0, -footerViewHeight, 0, 0);
        isLoadingMore = false;
    }

    /**
     * 隐藏脚布局--用于当二手车市场没有数据时
     */
    public void setFooterViewVisibilityToGone() {
        Log.d("上拉加载", "setFooterViewVisibilityToGone被执行");
        footerView.setVisibility(View.GONE);
    }

    public interface OnRefreshListener {

        /**
         * 上拉加载更多
         */
        void onLoadingMore();
    }
}
