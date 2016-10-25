package com.player.mothercollege.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

/**
 * Created by Administrator on 2016/9/30.
 */
public class SpringBackScroll extends ScrollView{

    private Context mContext;
    private View mView;
    private float touchY;
    private int scrollY = 0;
    private boolean handleStop = false;
    private int eachStep = 0;

    private static final int MAX_SCROLL_HEIGHT = 400;//最大滑动距离
    private static final float SCROLL_RATIO = 0.4f;

    public SpringBackScroll(Context context) {
        super(context);
        this.mContext = context;
    }

    public SpringBackScroll(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    public SpringBackScroll(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount()>0){
            this.mView = getChildAt(0);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN){
            touchY = ev.getY();
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mView==null){
            return super.onTouchEvent(ev);
        }else {
            commonOnTouchEvent(ev);
        }
        return super.onTouchEvent(ev);
    }

    private void commonOnTouchEvent(MotionEvent ev){
        int action = ev.getAction();
        switch (action){
            case MotionEvent.ACTION_UP:
                if (mView.getScaleY()!= 0){
                    handleStop = true;
                    animation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                float nowY = ev.getY();
                int deltaY = (int) (touchY- nowY);
                touchY = nowY;
                if (isNeedMove()){
                    int offset = mView.getScrollY();
                    if (offset<MAX_SCROLL_HEIGHT&&offset> - MAX_SCROLL_HEIGHT){
                        mView.scrollBy(0, (int) (deltaY*SCROLL_RATIO));
                        handleStop = false;
                    }
                }
                break;
        }
    }

    private boolean isNeedMove(){
        int viewHight = mView.getMeasuredHeight();
        int scrollHight = getHeight();
        int offset = viewHight - scrollY;
        if (scrollY==0||scrollY==offset){
            return true;
        }
        return false;
    }

    private void animation(){
        scrollY = mView.getScrollY();
        eachStep = scrollY/10;
        resetPositionHandler.sendEmptyMessage(0);
    }

    Handler resetPositionHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (scrollY!=0&&handleStop){
                scrollY -= eachStep;
                if ((eachStep<0&&scrollY>0)||(eachStep>0&&scrollY<0)){
                    scrollY = 0;
                }
                mView.scrollTo(0,scrollY);
                this.sendEmptyMessageDelayed(0,5);
            }
        }
    };
}
