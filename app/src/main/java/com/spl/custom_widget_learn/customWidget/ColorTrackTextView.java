package com.spl.custom_widget_learn.customWidget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.spl.custom_widget_learn.R;

/**
 * author: Oscar Liu
 * Date: 2021/1/13  16:23
 * File: ColorTrackTextView
 * Desc:滑动下方控件，上方文本变色
 * Test:
 */
@SuppressLint("AppCompatCustomView")
public class ColorTrackTextView extends TextView {

  //绘制不变色字体的画笔
  private Paint mOriginPaint;
  //绘制变色字体的画笔
  private Paint mChangePaint;

  //2.实现不同朝向
  private Direction mDirection = Direction.LEFT_TO_RIGHT;
  private int mOriginColor;
  private int mChangeColor;

  public enum Direction{
    LEFT_TO_RIGHT,RIGHT_TO_LEFT
  }

  private float mCurrentProgress = 0.0f;

  public ColorTrackTextView(Context context) {
    this(context,null);
  }

  public ColorTrackTextView(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs,0);
  }

  public ColorTrackTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);

    initPaint(context,attrs);

  }

  public void setOriginColor(int mOriginColor) {
    this.mOriginPaint.setColor(mOriginColor);
  }

  public void setChangeColor(int mChangeColor) {
    this.mChangePaint.setColor(mChangeColor);
  }

  /**
   * 初始化画笔
   */
  private void initPaint(Context context,AttributeSet attrs) {

    TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ColorTrackTextView);

    mOriginColor = array.getColor(R.styleable.ColorTrackTextView_originColor, Color.BLACK);
    mChangeColor = array.getColor(R.styleable.ColorTrackTextView_changeColor,Color.RED);

    mOriginPaint = getPaintByColor(mOriginColor);
    mChangePaint = getPaintByColor(mChangeColor);

    array.recycle();

  }

  private Paint getPaintByColor(int color) {
    Paint paint = new Paint();
    //抗锯齿
    paint.setAntiAlias(true);
    paint.setColor(color);
    //防抖动
    paint.setDither(true);
    //设置字体大小，这里使用TextView.getTextSize,获取android:TextSize属性
    paint.setTextSize(getTextSize());
    return paint;
  }

  //1.一个文字两种颜色
  //利用clipRect的API可以裁剪 左边用一个画笔去画，右边用另一个画笔去画 不断的改变中间值
  @Override
  protected void onDraw(Canvas canvas) {
//    super.onDraw(canvas); 不能用父类TextView的onDraw，自己来画

    //根据进度把中间值算出来
    int middle = (int) (mCurrentProgress*getWidth());

    if(mDirection == Direction.LEFT_TO_RIGHT){//从左到右，起点在左
      //绘制不变色
      drawText(canvas,mChangePaint,0,middle);
      //绘制变色
      drawText(canvas,mOriginPaint,middle,getWidth());
    }else{//从右到左，起点在右
      drawText(canvas,mChangePaint,getWidth() - middle,getWidth());
      drawText(canvas,mOriginPaint,0,getWidth() - middle);
    }

  }

  //绘制Text
  private void drawText(Canvas canvas,Paint paint,int start,int end){
    canvas.save();
    //canvas.clipRect();裁剪区域,显示被裁剪的区域，之外的区域将不会显示
    //clipxx方法只对设置以后的drawxx起作用，已经画出来的图形，是不会有作用的
    Rect rect = new Rect(start,0,end,getHeight());
    canvas.clipRect(rect);

    String text = getText().toString();
    Rect bounds = new Rect();
    paint.getTextBounds(text,0,text.length(),bounds);
    //获取字体的宽度
    int x = getWidth()/2 -bounds.width()/2;
    Paint.FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();
    int dy = (fontMetricsInt.bottom - fontMetricsInt.top)/2 - fontMetricsInt.bottom;
    int baseLine = getHeight()/2 + dy;
    canvas.drawText(text,x,baseLine,paint);
    canvas.restore();
  }

  public void setDirection(Direction direction){
    this.mDirection = direction;
    invalidate();
  }

  public void setCurrentProgress(float currentProgress){
    this.mCurrentProgress = currentProgress;
    invalidate();
  }























}
