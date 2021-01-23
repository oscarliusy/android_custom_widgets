package com.spl.custom_widget_learn.customLayout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.spl.custom_widget_learn.adapters.MyBaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * author: Oscar Liu
 * Date: 2021/1/19  16:30
 * File: TagLayout
 * Desc:自定义流式布局
 * Test:
 */
public class TagLayout extends ViewGroup {
  private List<List<View>> mChildViews = new ArrayList<>();
  private MyBaseAdapter mAdapter;

  public TagLayout(Context context) {
    super(context);
  }

  public TagLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public TagLayout(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  //1.onMeasure() 指定宽高
  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    //onMeasure会被调用两次，需要清空
    mChildViews.clear();
    int childCount = getChildCount();

    //获取宽度高度
    int width = MeasureSpec.getSize(widthMeasureSpec);
    //高度需要计算（流式布局自动换行）
    int height = getPaddingTop() + getPaddingBottom();

    int lineWidth = getPaddingLeft();

    ArrayList<View> childViews = new ArrayList<>();

    int topMargin = 0,bottomMargin=0;

    //1.1 for循环测量子View
    for (int i = 0; i < childCount; i++) {
      View childView = getChildAt(i);
      //这段代码执行后即可获取子View的宽高,因为会调用child.measure方法
      measureChild(childView,widthMeasureSpec,heightMeasureSpec);
      int childWidth = childView.getMeasuredWidth();
      int childHeight = childView.getMeasuredHeight();
      //1.2 根据子View计算自己的宽高
      //margin值的获取,ViewGroup.LayoutParams没有，就用系统的MarginLayoutParams
      //LinearLayout的margin来自于复写generateLayoutParams
      ViewGroup.MarginLayoutParams params = (MarginLayoutParams) childView.getLayoutParams();
      int rightMargin = params.rightMargin;
      int leftMargin = params.leftMargin;
      topMargin = params.topMargin;
      bottomMargin = params.bottomMargin;

      if(lineWidth + childWidth + leftMargin + rightMargin <= width){
        //Log.d("TAG","没有超出一行");
        //没有超出layout宽度，不换行，添加child
        childViews.add(childView);
        lineWidth += childWidth + leftMargin + rightMargin;

      }else{
        //超出宽度，将当前行内Views存入mChildViews,重置childViews
        //将当前View存入新的childViews，重置左边起点，高度增加
        //Log.d("TAG","已经超出一行");
        mChildViews.add(childViews);
        childViews = new ArrayList<>();
        childViews.add(childView);
        lineWidth = getPaddingLeft()+childWidth + leftMargin + rightMargin;
        height += childHeight + topMargin + bottomMargin;
      }
    }
    if(childViews.size() > 0){
      mChildViews.add(childViews);
      height += childViews.get(0).getMeasuredHeight() + topMargin +bottomMargin ;
    }

    //1.3指定自己宽高
    //Log.d("TAG","layout width->"+width + " height->" + height);
    Log.d("TAG", "mChildViews.size->" + String.valueOf(mChildViews.size()));
    setMeasuredDimension(width, height);
  }

  @Override
  protected void onLayout(boolean changed, int l, int t, int r, int b) {
    //Log.d("TAG", "onLayout");
    int left, right;
    int top = 0;
    int bottom = 0;
    Log.d("TAG", "layout top->" + t + " bottom->" + b);
    for (List<View> childViews : mChildViews) {

      //每一行初始化left
      left = getPaddingLeft();

      Log.d("TAG","child line top->" + top + " bottom->" + bottom);
      //Log.d("TAG", "childViews.size->" + String.valueOf(childViews.size()));

      //同一行内child计算摆放
      for (View childView : childViews) {
        //如果子View不可见，则不考虑摆放
        if(childView.getVisibility() == GONE){
          continue;
        }

        ViewGroup.MarginLayoutParams params = (MarginLayoutParams) childView.getLayoutParams();
        int rightMargin = params.rightMargin;
        int leftMargin = params.leftMargin;
        int topMargin = params.topMargin;
        int bottomMargin = params.bottomMargin;

        int childTop = top + topMargin;
        left += leftMargin;
        right = left + childView.getMeasuredWidth()+rightMargin;
        bottom = top + childView.getMeasuredHeight() + bottomMargin;
        //子布局摆放
        childView.layout(left, childTop, right, bottom);
        //同一行内left累加
        left += childView.getMeasuredWidth()+rightMargin;
      }
      //换行累加高度
      top += childViews.get(0).getMeasuredHeight();
    }
  }

  @Override
  public LayoutParams generateLayoutParams(AttributeSet attrs) {
    return new MarginLayoutParams(getContext(),attrs);
  }

  public void setAdapter(MyBaseAdapter adapter) {
    if (adapter == null) {
      //抛空指针异常
      throw new NullPointerException();
    }

    //清空父布局所有子View
    removeAllViews();

    mAdapter = null;
    mAdapter = adapter;

    int childCount = mAdapter.getCount();
    for (int i = 0; i < childCount; i++) {
      //通过位置获取View
      View childView = mAdapter.getView(i, this);
      //向父布局添加孩子（类似于在xml文件中添加），添加后可以用getChildAt获取
      addView(childView);
    }
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    return super.onTouchEvent(event);
  }
}
