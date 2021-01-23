package com.spl.custom_widget_learn.customWidget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * author: Oscar Liu
 * Date: 2021/1/20  19:43
 * File: TouchView
 * Desc:
 * Test:
 */
public class TouchView extends View {
  public TouchView(Context context) {
    super(context);
  }

  public TouchView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public TouchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    Log.e("TAG","onTouchEvent->" + event.getAction());
    return super.onTouchEvent(event);
    //return true;//此时会将事件消费，不向下传递
  }

  @Override
  public boolean dispatchTouchEvent(MotionEvent event) {

    //super.dispatchTouchEvent(event);//此时不会影响事件执行
    return true;//不调用super的话，后续事件都被阻拦
  }
}
