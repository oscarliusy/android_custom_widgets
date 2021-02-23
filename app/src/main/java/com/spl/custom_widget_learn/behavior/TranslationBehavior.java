package com.spl.custom_widget_learn.behavior;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * author: Oscar Liu
 * Date: 2021/2/20  18:44
 * File: TranslationBehavior
 * Desc: FloatingActionButton的自定义behavior
 * Test:
 */
public class TranslationBehavior extends FloatingActionButton.Behavior {

  public TranslationBehavior(Context context, AttributeSet attrs) {
    super(context, attrs);
  }


  /**
   * 当TranslationBehavior实例创建完毕，会调用这个方法
   * @param lp
   */
  @Override
  public void onAttachedToLayoutParams(@NonNull CoordinatorLayout.LayoutParams lp) {
    super.onAttachedToLayoutParams(lp);
  }

  //关注垂直滚动，向上的时候出来，向下的时候隐藏
  @Override
  public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout,
                                     @NonNull FloatingActionButton child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
    return axes == ViewCompat.SCROLL_AXIS_VERTICAL;
  }

  private boolean isOut = false;
  @Override
  public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull FloatingActionButton child,
                             @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type, @NonNull int[] consumed) {
    super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type, consumed);

    if(dyConsumed > 0){
      if(!isOut){
        //往上,btn隐藏，加一个标志位
        int translationY = ((CoordinatorLayout.LayoutParams)child.getLayoutParams()).bottomMargin + child.getMeasuredHeight();
        child.animate().translationY(translationY).setDuration(500).start();
        isOut = true;
      }

    }else{
      if(isOut){
        //往下
        child.animate().translationY(0).setDuration(1000).start();
        isOut = false;
      }

    }
  }

  @Override
  public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull FloatingActionButton child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
    super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
  }
}
