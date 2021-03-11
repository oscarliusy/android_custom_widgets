package com.spl.custom_widget_learn.animation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * author: Oscar Liu
 * Date: 2021/3/11  15:10
 * File: CircleView
 * Desc: FlowerLoading中用到的圆形
 * Test:
 */
public class CircleView extends View {

  private Paint mPaint;
  private int mColor;

  public CircleView(Context context) {
    this(context,null);
  }

  public CircleView(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs,0);
  }

  public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    mPaint = new Paint();
    mPaint.setAntiAlias(true);
    mPaint.setDither(true);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    //画背景圆形 三个参数 cx,cy,r,paint
    int cx = getWidth()/2;
    int cy = getHeight()/2;
    canvas.drawCircle(cx,cy,cx,mPaint);
  }

  //切换颜色
  public void exchangeColor(int color){
    mColor = color;
    mPaint.setColor(color);
    invalidate();
  }

  public int getColor() {
    return mColor;
  }
}
