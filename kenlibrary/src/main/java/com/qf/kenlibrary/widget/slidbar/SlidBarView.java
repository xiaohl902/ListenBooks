package com.qf.kenlibrary.widget.slidbar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Ken on 2016/9/22.10:28
 * 侧边字母栏控件
 */
public class SlidBarView extends View {

    private String[] cotents = {"当前", "热门", "A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
    private int strHeight;//每个文本的高度
    private Paint strPaint;
    private int index = -1;

    private OnTouchSlidBarListener onTouchSlidBarListener;

    public void setOnTouchSlidBarListener(OnTouchSlidBarListener onTouchSlidBarListener) {
        this.onTouchSlidBarListener = onTouchSlidBarListener;
    }

    public SlidBarView(Context context) {
        this(context, null);
    }

    public SlidBarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化方法
     */
    private void init() {
        strPaint = new Paint();
        strPaint.setAntiAlias(true);
        strPaint.setColor(Color.GRAY);
        strPaint.setTextSize(40);

        //获得每个文本的高度
        strHeight = (int) (strPaint.descent() - strPaint.ascent());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < cotents.length; i++) {
            if(i == index){
                strPaint.setColor(Color.parseColor("#39a6d9"));
            } else {
                strPaint.setColor(Color.GRAY);
            }

            canvas.drawText(cotents[i],
                    getWidth()/2 - strPaint.measureText(cotents[i])/2,
                    (i + 1) * strHeight,
                    strPaint);

        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureSpec(widthMeasureSpec, 1), measureSpec(heightMeasureSpec, 2));
    }

    /**
     *
     * @param spec 宽度或者高度的空间值
     * @param type 当前测量的是宽度还是高度，如果是宽度传1，如果是高度传2
     * @return
     */
    private int measureSpec(int spec, int type){
        int size = MeasureSpec.getSize(spec);
        int mode = MeasureSpec.getMode(spec);
        switch (mode){
            case MeasureSpec.EXACTLY:
                return size;
            case MeasureSpec.AT_MOST:
                switch (type){
                    case 1:
                        //返回宽度的测量结果
                        return (int) strPaint.measureText(cotents[0]) + getPaddingLeft() + getPaddingRight();
                    case 2:
                        //返回高度的测量结果
                        return (strHeight * cotents.length) + getPaddingTop() + getPaddingBottom();
                }
                break;
            case MeasureSpec.UNSPECIFIED:
                break;
        }
        return size;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                touch(event);
                break;
            case MotionEvent.ACTION_MOVE:
                touch(event);
                break;
            case MotionEvent.ACTION_UP:
                index = -1;
                invalidate();

                if(onTouchSlidBarListener != null){
                    onTouchSlidBarListener.unTouch();
                }
                break;
        }
        return true;
    }

    private void touch(MotionEvent event){
        int y = (int) event.getY();
        int in = y/strHeight;
        if(in < 0){
            in = 0;
        }

        if(in >= cotents.length){
            in = cotents.length - 1;
        }

        if(in == index){
            return;
        }
        index = in;

        //调用回调接口
        if(onTouchSlidBarListener != null){
            onTouchSlidBarListener.selectWord(cotents[index], index);
        }
        invalidate();
    }

    public interface OnTouchSlidBarListener{
        void selectWord(String word, int postition);
        void unTouch();
    }
}
