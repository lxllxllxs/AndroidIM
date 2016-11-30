package com.yiyekeji.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.yiyekeji.im.R;
import com.yiyekeji.utils.LogUtil;

/**
 * Created by lxl on 2016/11/30.
 */
public class NumberView extends View {
    private Context context;
    public NumberView(Context context) {
        super(context);
        this.context=context;
        init();
    }

    public NumberView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        init();
    }

    public NumberView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private Paint mPaint;
    // 画圆所在的距形区域
    private  RectF mRectF;

    private  void init(){
        mPaint = new Paint();
        mRectF=new RectF();
        mPaint.setColor(ContextCompat.getColor(context, R.color.red_light));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(10);
    }


    private String number;
    public void setNumber(String number){
        this.number = number;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = this.getWidth();
        int height = this.getHeight();
        if (width != height) {
            int min = Math.min(width, height);
            width = min;
            height = min;
        }
        canvas.drawCircle(width/2,height/2,width/2,mPaint);
        int textHeight =height/2;
        mPaint.setTextSize(textHeight);
        mPaint.setStrokeWidth(1);
        mPaint.setColor(Color.WHITE);
        Paint.FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();
        // 转载请注明出处：http://blog.csdn.net/hursing
        int baseline = height/2-(fontMetrics.bottom-fontMetrics.top)/2-fontMetrics.top;
        LogUtil.d("baseLine",baseline);
//        paint.setTextAlign(Paint.Align.CENTER);
        int textWidth = (int) mPaint.measureText(number, 0, number.length());
        canvas.drawText(number, width / 2 - textWidth / 2, baseline, mPaint);
    }
}
