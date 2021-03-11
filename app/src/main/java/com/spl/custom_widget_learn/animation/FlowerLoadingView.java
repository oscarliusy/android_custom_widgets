package com.spl.custom_widget_learn.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;

/**
 * author: Oscar Liu
 * Date: 2021/3/11  15:03
 * File: FlowerLoadingView
 * Desc:
 * Test:
 */
public class FlowerLoadingView extends RelativeLayout {
  private CircleView mLeftView, mMiddleView, mRightView;
  private int mDistance = dip2px(100);
  private int mDuration = 500;
  private boolean mIsAnimator;

  public FlowerLoadingView(Context context) {
    this(context, null);
  }

  public FlowerLoadingView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public FlowerLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);

    //添加3个圆形View
    mLeftView = getCircleView(context);
    mLeftView.exchangeColor(Color.RED);
    mMiddleView = getCircleView(context);
    mMiddleView.exchangeColor(Color.BLUE);
    mRightView = getCircleView(context);
    mRightView.exchangeColor(Color.GREEN);

    addView(mLeftView);
    addView(mRightView);
    addView(mMiddleView);

    //在布局实例化后再开启动画
    post(new Runnable() {
      @Override
      public void run() {
        startAnimator();
      }
    });
  }

  //添加圆形，设置其布局
  private CircleView getCircleView(Context context) {
    CircleView circleView = new CircleView(context);
    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(dip2px(8), dip2px(8));
    params.addRule(CENTER_IN_PARENT);
    circleView.setLayoutParams(params);
    return circleView;
  }

  private int dip2px(int dip) {
    return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getResources().getDisplayMetrics());
  }

  //动画的前半程
  private void startAnimator() {
    if(!mIsAnimator){
      return;
    }
    //向左跑
    ObjectAnimator leftViewAnimator = ObjectAnimator.ofFloat(mLeftView, "translationX", 0, -mDistance);
    //向右跑
    ObjectAnimator rightViewAnimator = ObjectAnimator.ofFloat(mRightView, "translationX", 0, mDistance);

    AnimatorSet set = new AnimatorSet();
    set.playTogether(leftViewAnimator, rightViewAnimator);
    set.setDuration(mDuration);
    set.setInterpolator(new DecelerateInterpolator());
    set.addListener(new AnimatorListenerAdapter() {
      @Override
      public void onAnimationEnd(Animator animation) {
        startNextAnimator();
      }
    });
    set.start();
  }

  //动画的后半程
  private void startNextAnimator() {
    if(!mIsAnimator){
      return;
    }
    //向右跑
    ObjectAnimator leftViewAnimator = ObjectAnimator.ofFloat(mLeftView, "translationX", -mDistance, 0);
    //向左跑
    ObjectAnimator rightViewAnimator = ObjectAnimator.ofFloat(mRightView, "translationX", mDistance, 0);

    AnimatorSet set = new AnimatorSet();
    set.playTogether(leftViewAnimator, rightViewAnimator);
    set.setDuration(mDuration);
    set.setInterpolator(new AccelerateInterpolator());
    set.addListener(new AnimatorListenerAdapter() {
      @Override
      public void onAnimationEnd(Animator animation) {
        //切换颜色顺序 左给中，中给右，右给左
        int leftColor = mLeftView.getColor();
        int rightColor = mRightView.getColor();
        int middleColor = mMiddleView.getColor();

        mMiddleView.exchangeColor(leftColor);
        mRightView.exchangeColor(middleColor);
        mLeftView.exchangeColor(rightColor);

        //循环动画
        startAnimator();
      }
    });
    set.start();
  }

  //优化,清理动画
  @Override
  public void setVisibility(int visibility) {
    super.setVisibility(INVISIBLE);
    mLeftView.clearAnimation();
    mRightView.clearAnimation();
    mMiddleView.clearAnimation();

    mIsAnimator = false;
  }
}
