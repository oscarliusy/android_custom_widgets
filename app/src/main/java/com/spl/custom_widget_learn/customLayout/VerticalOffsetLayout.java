package com.spl.custom_widget_learn.customLayout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * author: Oscar Liu
 * Date: 2021/1/19  10:47
 * File: VerticalOffsetLayout
 * Desc:
 * Test:
 */
public class VerticalOffsetLayout extends ViewGroup {

  private static final int OFFSET = 120;
  private Paint mPaint;

  public VerticalOffsetLayout(Context context) {
    super(context);
    init(context,null,0);
  }

  public VerticalOffsetLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context,null,0);
  }

  public VerticalOffsetLayout(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context,null,defStyleAttr);
  }

  private void init(Context context,AttributeSet attrs,int defStyleAttr){
    mPaint = new Paint(Color.BLUE);
    mPaint.setAntiAlias(true);
    mPaint.setAlpha(125);
    Log.d("TAG","init paint");
    //ViewGroup默认不会调用onDraw，需要更改标志位
    setWillNotDraw(false);
  }

  /**
   *  1.获取自己的布局和尺寸
   * 2.计算child的尺寸
   * 3.根据child的尺寸和自己的布局mode，计算自己的大小
   */
  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    int widthMode = MeasureSpec.getMode(widthMeasureSpec);
    int heightMode = MeasureSpec.getMode(heightMeasureSpec);
    int widthSize = MeasureSpec.getSize(widthMeasureSpec);
    int heightSize = MeasureSpec.getSize(heightMeasureSpec);

    int width = 0;
    int height = 0;

    int childCount = getChildCount();
    for(int i = 0;i<childCount;i++){
      View child = getChildAt(i);
      LayoutParams lp = child.getLayoutParams();
      int childWidthSpec = getChildMeasureSpec(widthMeasureSpec, 0, lp.width);
      int childHeightSpec = getChildMeasureSpec(heightMeasureSpec, 0, lp.height);
      child.measure(childWidthSpec,childHeightSpec);
    }

    switch(widthMode){
      case MeasureSpec.EXACTLY:
        width = widthSize;
        break;
      case MeasureSpec.AT_MOST:
      case MeasureSpec.UNSPECIFIED:
            for(int i=0;i<childCount;i++){
              View child = getChildAt(i);
              int widthAddOffset = i*OFFSET +child.getMeasuredWidth();
              width = Math.max(width,widthAddOffset);
            }
            break;
      default:
        break;
    }
    switch (heightMode) {
      case MeasureSpec.EXACTLY:
        height = heightSize;
        break;
      case MeasureSpec.AT_MOST:
      case MeasureSpec.UNSPECIFIED:
        for (int i = 0; i < childCount; i++) {
          View child = getChildAt(i);
          height = height + child.getMeasuredHeight();
        }
        break;
      default:
        break;

    }

    setMeasuredDimension(width,height);

  }

  /**
   * 通过child.layout
   * 设置每个子布局的位置
   */
  @Override
  protected void onLayout(boolean changed, int l, int t, int r, int b) {
    int left = 0;
    int right = 0;
    int top = 0;
    int bottom = 0;

    int childCount = getChildCount();

    for(int i = 0; i <childCount; i++){
      View child = getChildAt(i);
      left = i * OFFSET;
      right = left + child.getMeasuredWidth();
      bottom = top + child.getMeasuredHeight();
      child.layout(left,top,right,bottom);
      top += child.getMeasuredHeight();
    }
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    Log.d("TAG","on draw");
    int x = getWidth()/2;
    int y = getHeight()/2;
    canvas.drawCircle(x,y,Math.min(x,y),mPaint);
  }
}
