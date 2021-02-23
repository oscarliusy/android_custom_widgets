package com.spl.custom_widget_learn.customWidget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * author: Oscar Liu
 * Date: 2021/2/20  14:31
 * File: MyScrollView
 * Desc:自定义ScrollView,当版本过低没有ScrollView的onScrollChange方法时使用
 * Test:
 */
public class MyScrollView extends ScrollView {
  public MyScrollView(Context context) {
    this(context,null);
  }

  public MyScrollView(Context context, AttributeSet attrs) {
    this(context, attrs,0);
  }

  public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  protected void onScrollChanged(int l, int t, int oldl, int oldt) {
    super.onScrollChanged(l, t, oldl, oldt);
    if(mListener != null){
      mListener.onScroll(l, t, oldl, oldt);
    }
  }

  public interface ScrollChangeListener{
    public void onScroll(int l, int t, int oldl, int oldt);
  }

  private ScrollChangeListener mListener;

  public void setOnScrollListener(ScrollChangeListener listener){
    mListener = listener;
  }
}
