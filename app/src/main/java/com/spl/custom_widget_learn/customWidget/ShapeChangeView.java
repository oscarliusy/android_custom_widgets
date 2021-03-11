package com.spl.custom_widget_learn.customWidget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.shapes.Shape;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * author: Oscar Liu
 * Date: 2021/1/15  10:30
 * File: ShapeChangeView
 * Desc: 根据传入的enum.shape,绘制不同图形
 * Test:
 */
public class ShapeChangeView extends View {
  private Paint mRoundPaint,mSquarePaint,mTrianglePaint;
  private String mRoundColor = "#aa738ffe";
  private String mSquareColor = "#aae84e40";
  private String mTriangleColor = "#aa72d572";
  private mShape currentShape = mShape.ROUND;
  public enum mShape{
    ROUND,
    SQUARE,
    TRIANGLE
  }

  public mShape getCurrentShape(){
    return currentShape;
  }

  public ShapeChangeView(Context context) {
    this(context,null);
  }

  public ShapeChangeView(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs,0);
  }

  public ShapeChangeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    /**
     * 1.如果有，获取外界传入的参数
     * 2.定义画笔
     * 3.根据enum.shape绘制不同形状
     */
    mRoundPaint = createPaint(Color.parseColor(mRoundColor));
    mSquarePaint = createPaint(Color.parseColor(mSquareColor));
    mTrianglePaint = createPaint(Color.parseColor(mTriangleColor));
  }

  private Paint createPaint(int color){
    Paint paint = new Paint();
    paint.setAntiAlias(true);
    paint.setDither(true);
    paint.setColor(color);
    paint.setStyle(Paint.Style.FILL);
    return paint;
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    int width = MeasureSpec.getSize(widthMeasureSpec);
    int height = MeasureSpec.getSize(heightMeasureSpec);
    //设置一个正方形的绘图区域
    setMeasuredDimension(width>height?height:width,width>height?height:width);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    int center = getWidth()/2;
    int radius = getWidth()/2;
    RectF rectF = new RectF(0,0,getWidth(),getHeight());
    switch (currentShape){
      case ROUND:
        canvas.drawCircle(center,center,radius,mRoundPaint);
        //canvas.drawArc(rectF,0,360,false,mRoundPaint);
        break;
      case SQUARE:
        canvas.drawRect(rectF,mSquarePaint);
        break;
      case TRIANGLE:
        Path path = new Path();
        //绘制等边三角形
        path.moveTo(0, (float) ((getHeight()/2)*Math.sqrt(3)));
        path.lineTo(getWidth(),(float) ((getHeight()/2)*Math.sqrt(3)));
        path.lineTo(getWidth()/2,0);
        path.close();
        canvas.drawPath(path,mTrianglePaint);
        break;
      default:
        break;
    }
  }

//  public void setCurrentShape(mShape shape){
//    this.currentShape = shape;
//    invalidate();
//  }

  //每被外界调用一次，修改当前shape
  public void exchange(){
    switch(currentShape){
      case ROUND:
        currentShape = mShape.SQUARE;
        break;
      case SQUARE:
        currentShape = mShape.TRIANGLE;
        break;
      case TRIANGLE:
        currentShape = mShape.ROUND;
        break;
    }
    invalidate();
  }
}
