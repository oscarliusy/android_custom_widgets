package com.spl.custom_widget_learn.behavior;

import android.animation.Animator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.Interpolator;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

/**
 * author: Oscar Liu
 * Date: 2021/2/22  10:36
 * File: MyFabBehavior
 * Desc:
 * Test:
 */
public class MyFabBehavior extends CoordinatorLayout.Behavior<View> {

  private float viewY;//控件距离coordinatorLayout底部距离
  private static final Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();
  private boolean isAnimate;//动画是否在进行
  private boolean isOut = false;

  public MyFabBehavior(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
    if(child.getVisibility() == View.VISIBLE && viewY == 0){
      //获取控件距离父布局（coordinatorLayout）底部距离
      viewY=coordinatorLayout.getHeight()-child.getY();
    }
    return (axes & ViewCompat.SCROLL_AXIS_VERTICAL) !=0;//判断是否竖直滚动
  }

  @Override
  public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target,
                                int dx, int dy, @NonNull int[] consumed, int type) {
    //大于0是向上滚动 小于0是向下滚动
//    if (dy >=0&&!isAnimate&&child.getVisibility()==View.VISIBLE) {
    if (dy >= 0 && !isAnimate){
       hide(child);
//    } else if (dy <0&&!isAnimate&&child.getVisibility()==View.GONE) {
    }else if(dy<0&&!isAnimate){
      show(child);
    }

    //这是另一种写法
//    if(dy > 0){
//      if(!isOut){
//        //往上,btn隐藏，加一个标志位
//        int translationY = ((CoordinatorLayout.LayoutParams)child.getLayoutParams()).bottomMargin + child.getMeasuredHeight();
//        child.animate().translationY(translationY).setDuration(500).start();
//        isOut = true;
//      }
//
//    }else{
//      if(isOut){
//        //往下
//        child.animate().translationY(0).setDuration(1000).start();
//        isOut = false;
//      }
//    }
  }

  private void hide(final View view){
    Log.d("TAG","hide");
    ViewPropertyAnimator animator = view.animate().translationY(viewY).setInterpolator(INTERPOLATOR).setDuration(200);
    animator.setListener(new Animator.AnimatorListener() {
      @Override
      public void onAnimationStart(Animator animation) {
        isAnimate = true;
      }

      @Override
      public void onAnimationEnd(Animator animation) {
//        view.setVisibility(View.GONE);
        isAnimate = false;
      }

      @Override
      public void onAnimationCancel(Animator animation) {
        show(view);
      }

      @Override
      public void onAnimationRepeat(Animator animation) {

      }
    });
    animator.start();
  }

  private void show(final View view){
    Log.d("TAG","show");
    ViewPropertyAnimator animator = view.animate().translationY(0).setInterpolator(INTERPOLATOR).setDuration(200);
    animator.setListener(new Animator.AnimatorListener() {
      @Override
      public void onAnimationStart(Animator animator) {
//        view.setVisibility(View.VISIBLE);
        isAnimate=true;
      }

      @Override
      public void onAnimationEnd(Animator animator) {
        isAnimate=false;
      }

      @Override
      public void onAnimationCancel(Animator animator) {
        hide(view);
      }

      @Override
      public void onAnimationRepeat(Animator animator) {

      }
    });
    animator.start();
  }


}
