package com.spl.custom_widget_learn.customWidget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.spl.custom_widget_learn.R;

/**
 * author: Oscar Liu
 * Date: 2021/1/12  19:59
 * File: QQStepView
 * Desc:
 * Test:
 */
public class QQStepView extends View {

  private int mOuterColor = Color.BLUE;
  private int mInnerColor = Color.RED;
  private int mBorderWidth = 20;//20px
  private int mStepTextSize;
  private int mStepTextColor = Color.RED;
  private Paint mOutPaint,mInnerPaint,mTextPaint;

  //最高步数，当前部署
  private int mStepMax = 0;
  private int mCurrentStep = 0;

  public QQStepView(Context context) {
    this(context,null);
  }

  public QQStepView(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs,0);
  }

  public QQStepView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
//    1.分析效果
//    2.确定自定义属性，编写attrs.xml
//    3.在布局中使用
//    4.在自定义View中获取自定义属性
    TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.QQStepView);
    mOuterColor = array.getColor(R.styleable.QQStepView_outerColor,mOuterColor);
    mInnerColor = array.getColor(R.styleable.QQStepView_innerColor,mInnerColor);
    mStepTextColor = array.getColor(R.styleable.QQStepView_stepTextColor,mStepTextColor);
    mBorderWidth = (int) array.getDimension(R.styleable.QQStepView_borderWidth,mBorderWidth);
    mStepTextSize = array.getDimensionPixelSize(R.styleable.QQStepView_stepTextSize,mStepTextSize);
    array.recycle();

    //外层圆弧画笔
    mOutPaint = new Paint();
    mOutPaint.setAntiAlias(true);
    mOutPaint.setStrokeWidth(mBorderWidth);
    mOutPaint.setColor(mOuterColor);
    mOutPaint.setStrokeCap(Paint.Cap.ROUND);
    mOutPaint.setStyle(Paint.Style.STROKE);//画笔空心STROKE，实心：FILL，描边加实心：FILL_AND_STROKE

    //内层圆弧画笔
    mInnerPaint = new Paint();
    mInnerPaint.setAntiAlias(true);
    mInnerPaint.setStrokeWidth(mBorderWidth);
    mInnerPaint.setColor(mInnerColor);
    mInnerPaint.setStrokeCap(Paint.Cap.ROUND);
    mInnerPaint.setStyle(Paint.Style.STROKE);//画笔空心STROKE，实心：FILL，描边加实心：FILL_AND_STROKE

    //文字画笔
    mTextPaint = new Paint();
    mTextPaint.setAntiAlias(true);
    mTextPaint.setColor(mStepTextColor);
    mTextPaint.setTextSize(mStepTextSize);


//    5.onMeasure
//    6.画外圆弧，内圆弧，文字
//    7.其他处理
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    //调用者在布局文件中可能 warp_content, 宽度高度不一致
    //获取模式 AT_MOST 报错或给一个值

    //宽度高度不一致 取最小值 确保是个正方形
    int width = MeasureSpec.getSize(widthMeasureSpec);
    int height = MeasureSpec.getSize(heightMeasureSpec);

    setMeasuredDimension(width>height?height:width,width>height?height:width);
  }

  //    6.画外圆弧，内圆弧，文字
  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);

    //6.1 画外圆弧  思考：边缘没有显示完整， 描边有宽度mBorderWidth
    int center = getWidth()/2;
    int radius = center - mBorderWidth/2;
    RectF rectF = new RectF(mBorderWidth/2, mBorderWidth/2, center+radius, center+radius);
    canvas.drawArc(rectF,135,270,false,mOutPaint);

    //6.2 画内圆弧 不能写死，用户设置百分比
    if(mStepMax == 0)return;
    float sweepAngle = (float)270*mCurrentStep/mStepMax;
    canvas.drawArc(rectF,135,sweepAngle,false,mInnerPaint);

    //6.3 画文字
    String stepText = mCurrentStep + "";
    Rect textBounds = new Rect();
    mTextPaint.getTextBounds(stepText,0,stepText.length(),textBounds);
    //x方向起点
    int dx = getWidth()/2 - textBounds.width()/2;
    //基线
    Paint.FontMetricsInt fontMetricsInt = mTextPaint.getFontMetricsInt();
    int dy = (fontMetricsInt.bottom - fontMetricsInt.top)/2 - fontMetricsInt.bottom;
    int baseLine = getHeight()/2 + dy;
    canvas.drawText(stepText,dx,baseLine,mTextPaint);
  }

  //    7.其他处理,加入动画
  public synchronized void setStepMax(int stepMax){
    this.mStepMax = stepMax;
  }

  public synchronized void setCurrentStep(int currentStep){
    this.mCurrentStep = currentStep;
    //不断绘制 调用onDraw
    invalidate();
  }

}
