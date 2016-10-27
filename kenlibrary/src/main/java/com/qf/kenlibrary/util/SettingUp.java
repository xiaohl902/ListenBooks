package com.qf.kenlibrary.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.qf.kenlibrary.R;

/**
 * Created by Administrator on 2016/10/27 0027.
 */
public class SettingUp extends View {

    private Bitmap bitmapFrame,//边框图片
            bitmapBottom,//底图
            bitmapUnPressed,//按钮未按中状态
            bitmapPressed,//按钮按下状态
            bitmapMask;//遮障图

    private Paint paint;

    private Bitmap showBtn;//当前显示的按钮图片

    private Rect mRect;

    private int movex;
    private int moveMax;

    //当前按钮的开关状态
    private boolean isChecked = false;
    private OnSwitchListener onSwitchListener;

    public void setOnSwitchListener(OnSwitchListener onSwitchListener){
            this.onSwitchListener=onSwitchListener;
    }
    public SettingUp(Context context) {
        this(context,null);
    }

    public SettingUp(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SettingUp(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        bitmapFrame = BitmapFactory.decodeResource(getResources(), R.drawable.checkswitch_frame);
        bitmapBottom = BitmapFactory.decodeResource(getResources(), R.drawable.checkswitch_bottom);
        bitmapUnPressed = BitmapFactory.decodeResource(getResources(), R.drawable.checkswitch_btn_unpressed);
        bitmapPressed = BitmapFactory.decodeResource(getResources(), R.drawable.checkswitch_btn_pressed);
        bitmapMask = BitmapFactory.decodeResource(getResources(), R.drawable.checkswitch_mask);
        //默认显示未按下的图片
        showBtn = bitmapUnPressed;

        paint = new Paint();
        paint.setAntiAlias(true);

        moveMax = bitmapBottom.getWidth() - bitmapFrame.getWidth();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mRect = new Rect(movex, 0, bitmapFrame.getWidth() + movex, bitmapFrame.getHeight());
        Rect rectFrame = new Rect(0, 0, bitmapFrame.getWidth(), bitmapFrame.getHeight());

        canvas.saveLayer(new RectF(rectFrame), null, 0);//在当前画布上创建一个新图层
        //绘制底图
        canvas.drawBitmap(bitmapBottom, mRect, rectFrame, null);
        //绘制边框
        canvas.drawBitmap(bitmapFrame, rectFrame, rectFrame,null);
        //绘制按钮
        canvas.drawBitmap(showBtn, mRect, rectFrame, null);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        //绘制遮障
        canvas.drawBitmap(bitmapMask, rectFrame, rectFrame, paint);
        canvas.restore();//把新图层的内容显示到原先的画布上
        paint.reset();
    }
        private  int bx;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                //将显示的按钮替换成被按下的图片
                showBtn = bitmapPressed;
                //获得按下时的x坐标
                bx = (int) event.getX();
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                int ex = (int) event.getX();
                movex -= ex - bx;
                bx = ex;

                //判断范围
                if(movex < 0){
                    movex = 0;
                }

                if(movex > moveMax){
                    movex = moveMax;
                }

                break;
            case MotionEvent.ACTION_UP:
                //将显示的按钮替换成未被按下的图片
                showBtn = bitmapUnPressed;

                if(movex > 0 && movex < moveMax){
                    if(movex < moveMax/2){
                        movex = 0;
                    }

                    if(movex >= moveMax/2){
                        movex = moveMax;
                    }
                }

                //设置按钮的当前状态
                boolean checked = false;
                if(movex == 0){
                    checked = false;
                }

                if(movex == moveMax){
                    checked = true;
                }

                if(checked != isChecked){
                    isChecked = checked;
                    //调用回调方法
                    if(onSwitchListener != null){
                        onSwitchListener.onSwitch(this, isChecked);
                    }
                }

                invalidate();
                break;
        }
        invalidate();
        return true;
    }

    public interface OnSwitchListener{
        void onSwitch(SettingUp settingUp, boolean ischecked);
    }
}
