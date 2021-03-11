package com.spl.custom_widget_learn.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.spl.custom_widget_learn.R;
import com.spl.custom_widget_learn.customWidget.ShapeChangeView;

/**
 * author: Oscar Liu
 * Date: 2021/3/7  12:09
 * File: LoadingView
 * Desc: 仿58同城加载动画
 * Test:
 */
public class LoadingView extends LinearLayout {

  private ShapeChangeView mShapeView;//形状
  private View mShadowView;//阴影
  private int mTranslationDistance = 0;
  //动画执行的时间
  private final long ANIMATOR_DURATION = 350;
  //是否停止动画
  private boolean mIsStopAnimator = false;

  public LoadingView(Context context) {
    this(context, null);
  }

  public LoadingView(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    mTranslationDistance = dip2px(80);
    initLayout();
  }

  private int dip2px(int dip) {
    return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getResources().getDisplayMetrics());
  }

  /**
   * 初始化加载布局
   */
  private void initLayout() {
    //1.加载写好的布局ui_loding_view
    //1.1 实例化View
//    View loadView = inflate(getContext(), R.layout.ui_loading_view, null);
    //1.2 添加到该View
//    addView(loadView);
    //更好的写法，将layout加载到root中
    //this代表把ui_loading_view加载到LoadingView中
    inflate(getContext(), R.layout.ui_loading_view, this);
    mShapeView = findViewById(R.id.shape_view);
    mShadowView = findViewById(R.id.shadow_view);

    post(new Runnable() {
      @Override
      public void run() {
        //onResume之后（View的绘制流程源码分析那一章）
        startFallAnimator();
      }
    });
    //onCreate()方法中执行,布局文件解析，反射创建实例
    //startFallAnimator();


  }

  //开始下落动画
  private void startFallAnimator() {
    if(mIsStopAnimator){
      return;
    }
    //动画作用在谁的身上
    //下落，位移动画
    ObjectAnimator translationAnimator = ObjectAnimator.ofFloat(mShapeView, "translationY", 0, mTranslationDistance);
//    translationAnimator.setDuration(ANIMATOR_DURATION);

    //配合中间阴影缩小
    ObjectAnimator scaleAnimation = ObjectAnimator.ofFloat(mShadowView, "scaleX", 1f, 0.3f);
//    translationAnimator.setDuration(ANIMATOR_DURATION);

    AnimatorSet set = new AnimatorSet();
    set.playTogether(translationAnimator, scaleAnimation);
    set.setDuration(ANIMATOR_DURATION);
    set.setInterpolator(new AccelerateInterpolator());
    //按先后顺序执行动画
    //set.playSequentially(translationAnimator,scaleAnimation);
    set.start();

    //下落后上抛，监听动画执行完毕
    //是一种思想，在adapter中的BannerView写过
    set.addListener(new AnimatorListenerAdapter() {
      @Override
      public void onAnimationEnd(Animator animation) {
        //开始改变形状
        mShapeView.exchange();
        //下落后就上抛
        startUpAnimator();
      }
    });

  }

  private void startUpAnimator() {
    if(mIsStopAnimator){
      return;
    }
    //上抛，位移动画
    ObjectAnimator translationAnimator = ObjectAnimator.ofFloat(mShapeView, "translationY", mTranslationDistance, 0);

    //配合中间阴影缩小
    ObjectAnimator scaleAnimation = ObjectAnimator.ofFloat(mShadowView, "scaleX", 0.3f, 1f);

    AnimatorSet set = new AnimatorSet();
    set.setDuration(ANIMATOR_DURATION);
    set.playTogether(translationAnimator, scaleAnimation);
    set.setInterpolator(new DecelerateInterpolator());
    //按先后顺序执行动画
    set.addListener(new AnimatorListenerAdapter() {
      @Override
      public void onAnimationEnd(Animator animation) {
        startFallAnimator();
      }

      @Override
      public void onAnimationStart(Animator animation) {
        //开始旋转
        startRotationAnimator();
      }
    });
    //必须放在监听后面
    set.start();
  }

  /**
   * 上抛的时候需要旋转
   */
  private void startRotationAnimator() {
    ObjectAnimator rotationAnimator = null;
    switch (mShapeView.getCurrentShape()) {
      case ROUND:
      case SQUARE:
        //180
        rotationAnimator = ObjectAnimator.ofFloat(mShapeView, "rotation", 0,180);
        break;
      case TRIANGLE:
        //60
        rotationAnimator = ObjectAnimator.ofFloat(mShapeView, "rotation", 0,-120);
        break;
    }
    rotationAnimator.setDuration(ANIMATOR_DURATION);
    rotationAnimator.setInterpolator(new DecelerateInterpolator());
    rotationAnimator.start();

  }

  //优化性能
  @Override
  public void setVisibility(int visibility) {
    super.setVisibility(INVISIBLE);//不要再去摆放和计算，少走一些系统源码
    //清理动画
    mShapeView.clearAnimation();
    mShadowView.clearAnimation();

    //把loadingView从父布局移除
    ViewGroup parent = (ViewGroup)getParent();
    if(parent != null){
      parent.removeView(this);//从父布局移除自己
      removeAllViews();//移除自己所有的view
    }
    //关闭所有动画效果
    mIsStopAnimator = true;
  }
}



















