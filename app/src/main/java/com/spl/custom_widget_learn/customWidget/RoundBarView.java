package com.spl.custom_widget_learn.customWidget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.spl.custom_widget_learn.R;

/**
 * author: Oscar Liu
 * Date: 2021/1/14  19:57
 * File: RoundBarView
 * Desc:
 * Test:
 */
public class RoundBarView extends View {

  private final float mTextSize = 15;
  //外圈颜色，内圈颜色
  private int mOuterColor = Color.BLUE;
  private int mInnerColor = Color.RED;
  //圆弧宽度
  private int mBorderWidth= 20;
  //起止角度
  private int mStart = 0;
  private int mEnd = 0;
  //内外层画笔
  private Paint mOutPaint,mInnerPaint,mTextPaint;
  private float mSweep;

  public RoundBarView(Context context) {
    this(context,null);
  }

  public RoundBarView(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs,0);
  }

  public RoundBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    //获取属性
    TypedArray array = context.obtainStyledAttributes(R.styleable.RoundBarView);
    mOuterColor = array.getColor(R.styleable.RoundBarView_OuterColor,mOuterColor);
    mInnerColor = array.getColor(R.styleable.RoundBarView_InnerColor,mInnerColor);
    mBorderWidth = (int) array.getDimension(R.styleable.RoundBarView_RBVBorderWidth,dip2px(10));
    array.recycle();

    mOutPaint = initPaint(mOuterColor);
    mInnerPaint = initPaint(mInnerColor);
    mTextPaint = new Paint();
    mTextPaint.setAntiAlias(true);
    mTextPaint.setColor(mInnerColor);
    mTextPaint.setTextSize(sp2px(mTextSize));
  }

  //像素转px
  private float dip2px(int dip){
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dip,getResources().getDisplayMetrics());
  }

  //字体sp转px
  private int sp2px(float sp){
    return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,sp,getResources().getDisplayMetrics());
  }

  private Paint initPaint(int color) {
    Paint paint = new Paint();
    paint.setAntiAlias(true);
    paint.setDither(true);
    paint.setColor(color);
    paint.setStrokeWidth(mBorderWidth);
    paint.setStrokeCap(Paint.Cap.ROUND);
    paint.setStyle(Paint.Style.STROKE);
    return paint;
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    int width = MeasureSpec.getSize(widthMeasureSpec);
    int height = MeasureSpec.getSize(heightMeasureSpec);

    setMeasuredDimension(Math.min(width,height),Math.min(width,height));
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    //1.画完整外圆
    int center = getWidth()/2;
    int radius = center - mBorderWidth/2;
    canvas.drawCircle(center,center,radius,mOutPaint);

    //2.画内圆弧
    RectF rectF = new RectF(mBorderWidth/2,mBorderWidth/2,center+radius,center+radius);
    canvas.drawArc(rectF,mStart,mSweep,false,mInnerPaint);

    //3.画文字
    String percentText = this.mEnd + "%";
    Rect bounds = new Rect();
    mTextPaint.getTextBounds(percentText,0,percentText.length(),bounds);
    int dx = getWidth()/2 - bounds.width()/2;
    Paint.FontMetricsInt fontMetricsInt = mTextPaint.getFontMetricsInt();
    int dy = (fontMetricsInt.bottom - fontMetricsInt.top)/2 - fontMetricsInt.bottom;
    int baseLine = getHeight()/2 + dy;
    canvas.drawText(percentText,dx,baseLine,mTextPaint);

  }

  public synchronized void setEnd(int end){
    if(end < 0){
      return;
    }
    this.mEnd = end;
    mSweep = (float)end*360/100;
    invalidate();
  }


}
