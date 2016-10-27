package com.qf.kenlibrary.widget.slidbar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Ken on 2016/9/22.11:09
 */
public class LabelView extends View {


    private Paint criclePaint;
    private Paint txtPaint;
    private int radius;
    private String text;

    public LabelView(Context context) {
        this(context, null);
    }

    public LabelView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LabelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        radius = 60;

        criclePaint = new Paint();
        criclePaint.setColor(Color.parseColor("#39a6d9"));
        criclePaint.setAntiAlias(true);

        txtPaint = new Paint();
        txtPaint.setAntiAlias(true);
        txtPaint.setTextSize(60);
        txtPaint.setColor(Color.WHITE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(getWidth()/2, getHeight()/2, radius, criclePaint);
        if(text != null){
            canvas.drawText(text,
                    getWidth()/2 - txtPaint.measureText(text)/2,
                    getHeight()/2 + (txtPaint.descent() - txtPaint.ascent())/2 - txtPaint.descent(),
                    txtPaint);
        } else {
            this.setVisibility(GONE);
        }
    }

    public void setText(String text){
        this.setVisibility(VISIBLE);
        this.text = text;
        invalidate();
    }
}
