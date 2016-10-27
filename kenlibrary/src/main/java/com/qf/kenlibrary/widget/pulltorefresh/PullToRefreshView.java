package com.qf.kenlibrary.widget.pulltorefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by Ken on 2016/9/28.14:04
 * 下拉刷新控件
 */
public class PullToRefreshView extends LinearLayout implements View.OnTouchListener {

    //头部控件对象
    private View headView;
    private ListView lv;

    private static final int NONE = 0;//默认情况
    private static final int PULL = 1;//下拉状态
    private static final int PULL_REFRES = 2;//松手刷新的状态
    private static final int REFRESHING = 3;//正在刷新
    private int state = NONE;//下拉控件当前的状态

    private int headViewHeight;//头部控件的高度

    /**
     * 下拉的接口回调
     */
    private OnPullToRefreshListener onPullToRefreshListener;

    public void setOnPullToRefreshListener(OnPullToRefreshListener onPullToRefreshListener) {
        this.onPullToRefreshListener = onPullToRefreshListener;
    }

    public PullToRefreshView(Context context) {
        this(context, null);
    }

    public PullToRefreshView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullToRefreshView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化方法
     */
    private void init() {
        //把线性布局默认设置为纵向
        super.setOrientation(VERTICAL);
    }

    /**
     * 设置下拉刷新头部控件的方法
     */
    public void setHeadView(View headView){
        this.headView = headView;
        initView();
    }

    public void setHeadView(int layoutId){
        View view = LayoutInflater.from(getContext()).inflate(layoutId, null);
        setHeadView(view);
    }

    /**
     * 初始化组件
     */
    private void initView() {
        this.addView(headView);

        lv = new ListView(getContext());
        lv.setOnTouchListener(this);
        LinearLayout.LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        this.addView(lv, layoutParams);
    }

    /**
     * onTouch监听方法
     *
     * onTouch方法会先于ListView的onTouchEvent方法被调用
     * 如果onTouch方法返回false，则onTouchEvent方法正常被调用，
     * 反之则不会再调用onTouchEvent方法
     *
     * @param v
     * @param event
     * @return
     */
    private int by = -1;//开始的y位置
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                if(isTop()){
                    //表示ListView已经滑动到了顶部

                    int ey = (int) event.getRawY();//基于屏幕的y值
                    int movey = 0;
                    if(by == -1){
                        by = ey;
                    } else {
                        movey = ey - by;
                    }
                    by = ey;


                    if(movey < 0 && headView.getPaddingTop() <= -headViewHeight){
                        //如果网上滑动，并且头部没有出现则把事件交给ListView
                        return false;
                    }

                    //头部已经被拉开了
                    headView.setPadding(headView.getPaddingLeft(), headView.getPaddingTop() + movey / 3, headView.getPaddingRight(), headView.getPaddingBottom());
                    if(headView.getPaddingTop() > -headViewHeight){
                        //正在下拉的状态
                        state = PULL;

                        if(headView.getPaddingTop() >= 0){
                            state = PULL_REFRES;
                        }

                        //----------------调用正在下拉的回调方法----------------------
                        if(onPullToRefreshListener != null){
                            onPullToRefreshListener.pull(headView, (headView.getPaddingTop() + headViewHeight) / 4);
                        }
                    } else {
                        //普通状态
                        state = NONE;
                    }

                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                //重置by
                reset();
                break;
        }
        return false;
    }

    /**
     * 重置的方法
     */
    private int backHeigth;//返回的一个高度值
    private void reset() {
        by = -1;

        //如果当前的状态为下拉状态或者正在刷新状态
        if(state == PULL || state == REFRESHING){
            //重置到初始位置
            backHeigth = -headViewHeight;
        } else if(state == PULL_REFRES){
            //重置到PaddingTop为0的位置
            backHeigth = 0;
        }

        //重置控件的PaddingTop
        this.post(new MyRunnable(backHeigth));
    }

    private class MyRunnable implements Runnable{
        private int backHeight;

        public MyRunnable(int backHeigth){
            this.backHeight = backHeigth;
        }


        @Override
        public void run() {
            if(headView.getPaddingTop() > backHeight) {
                headView.setPadding(headView.getPaddingLeft(), headView.getPaddingTop() - 50, headView.getPaddingRight(), headView.getPaddingBottom());
                postDelayed(this, 5);
            } else {
                //已经到达指定位置
                headView.setPadding(headView.getPaddingLeft(), backHeight, headView.getPaddingRight(), headView.getPaddingBottom());
                if(state == PULL || state == REFRESHING){
                    //如果松手的时候状态为下拉状态和正在刷新状态，则重置后的状态为NONE
                    state = NONE;
                } else if(state == PULL_REFRES){
                    //如果松手时的状态是松手刷新状态，则重置后的状态的为正在刷新状态
                    state = REFRESHING;

                    //-------------------回调正在刷新的方法-------------------------
                    if(onPullToRefreshListener != null){
                        onPullToRefreshListener.refreshing(headView);
                    }
                }
            }
        }
    }

    /**
     * 判断ListView是否被拉到顶部
     */
    private boolean isTop(){
        int count = lv.getChildCount();
        if(count > 0){
            View view = lv.getChildAt(0);
            int position = lv.getFirstVisiblePosition();
            if(view.getTop() == 0 && position == 0){
                return true;
            } else {
                return false;
            }
        } else {
            //如果没有数据则默认可以进行下拉
            return true;
        }
    }

    /**
     * 更新完成
     */
    public void refreshComplete(){
        reset();
        state = NONE;

        //------------------回调刷新完成的方法-----------------------------
        if(onPullToRefreshListener != null){
            onPullToRefreshListener.refreshCompelet(headView);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        headViewHeight = headView.getMeasuredHeight();
        //隐藏头部控件
        headView.setPadding(getPaddingLeft(), -headViewHeight, getPaddingRight(), getPaddingBottom());
    }

    public interface OnPullToRefreshListener{
        //正在下拉
        void pull(View headView, int movey);

        //正在刷新的方法
        void refreshing(View headView);

        //刷新完成的方法
        void refreshCompelet(View headView);
    }

    /**
     * 提供一些ListView的常用方法
     * @param adapter
     */
    public void setAdapter(ListAdapter adapter){
        lv.setAdapter(adapter);
    }

    public void addHeaderView(View view){
        lv.addHeaderView(view);
    }

    public void addFooterView(View view){
        lv.addFooterView(view);
    }

    public ListView getLv() {
        return lv;
    }
}
