package com.spl.custom_widget_learn.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Interpolator;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BaseInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.core.content.ContextCompat;

import com.spl.custom_widget_learn.R;

import java.util.Random;

/**
 * author: Oscar Liu
 * Date: 2021/3/14  11:31
 * File: LoveLayout
 * Desc: 点赞+桃心沿贝塞尔曲线飞舞效果
 * Test:
 */
public class LoveLayout extends RelativeLayout {
  //随机数
  private Random mRandom;
  //图片资源
  private int[] mImageRes;

  //控件的宽高
  private int mWidth, mHeight;
  //图片的宽高
  private int mDrawableWidth, mDrawableHeight;

  //插值器
  private BaseInterpolator[] mInterpolator;

  public LoveLayout(Context context) {
    this(context, null);
  }

  public LoveLayout(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public LoveLayout(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    mRandom = new Random();
    //图片随机资源库
    mImageRes = new int[]{R.drawable.pl_blue, R.drawable.pl_red, R.drawable.pl_yellow};
    //获取图片，转化为drawable资源，用来测量
    Drawable drawable = ContextCompat.getDrawable(context, R.drawable.pl_blue);
    //测量结果用于设定贝塞尔曲线的起点Point0
    mDrawableWidth = drawable.getIntrinsicWidth();
    mDrawableHeight = drawable.getIntrinsicHeight();
    //初始化一组插值器
    mInterpolator = new BaseInterpolator[]{new AccelerateDecelerateInterpolator(),new AccelerateInterpolator(),
      new DecelerateInterpolator(),new LinearInterpolator()};
  }

  //测量整个控件的宽高，设置match parent，则等同于phoneWindow大小
  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    //获取控件的宽高，用来初始化贝塞尔的Point0
    mWidth = MeasureSpec.getSize(widthMeasureSpec);
    mHeight = MeasureSpec.getSize(heightMeasureSpec);
  }

  /**
   * 添加一个点赞的View
   */
  public void addLove() {
    //添加一个ImageView在底部
    ImageView loveLv = new ImageView(getContext());
    //随机给一张图片资源,从[0,1,2]中取一个随机数
    loveLv.setImageResource(mImageRes[mRandom.nextInt(mImageRes.length)]);
    //怎么添加到底部中心？LayoutParams
    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    params.addRule(ALIGN_PARENT_BOTTOM);
    params.addRule(CENTER_HORIZONTAL);
    loveLv.setLayoutParams(params);
    addView(loveLv);
    //添加的效果：有放大和透明度变化（属性动画）
    AnimatorSet set = getAnimator(loveLv);
    //动画执行结束后，移除iv
    set.addListener(new AnimatorListenerAdapter() {
      @Override
      public void onAnimationEnd(Animator animation) {
        removeView(loveLv);
      }
    });
    set.start();

  }

  /**
   * 在传入的ImageView上，绑定进入动画和运动动画，并设定按先后顺序播放
   * @param iv
   */
  private AnimatorSet getAnimator(ImageView iv) {
    AnimatorSet set = new AnimatorSet();
    //添加的效果：有放大和透明度变化（属性动画）
    AnimatorSet enterAnimator = new AnimatorSet();
    ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(iv, "alpha", 0.3f, 1.0f);
    ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(iv, "scaleX", 0.3f, 1.0f);
    ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(iv, "scaleY", 0.3f, 1.0f);
    enterAnimator.playTogether(alphaAnimator, scaleXAnimator, scaleYAnimator);
    enterAnimator.setDuration(350);
    //单独测试时使用
//    enterAnimator.start();

    //运行的路径动画,按顺序执行：入场动画--> 贝塞尔路径动画
    set.playSequentially(enterAnimator, getBezierAnimator(iv));
    return set;
  }

  /**
   * 通过自定义的三阶贝塞尔估值器，计算任一时间IV所在的坐标，通过ValueAnimator来设定iv的参数
   * @param iv
   */
  private Animator getBezierAnimator(ImageView iv) {
    //确定三阶贝塞尔的四个点
    //1.起点位于底部中心
    PointF point0 = new PointF(mWidth / 2 - mDrawableWidth / 2, mHeight - mDrawableHeight);
    //2.控制点1在屏幕下半部分
    PointF point1 = new PointF(mRandom.nextInt(mWidth) - mDrawableWidth, mHeight / 2 + mRandom.nextInt(mHeight / 2));
    //3.控制点2在屏幕上半部分
    PointF point2 = new PointF(mRandom.nextInt(mWidth) - mDrawableWidth, mRandom.nextInt(mHeight / 2));
    //4.终点在顶部
    PointF point3 = new PointF(mRandom.nextInt(mWidth), 0);
    //使用自定义估值器，计算三阶贝塞尔的路径点
    LoveTypeEvaluator typeEvaluator = new LoveTypeEvaluator(point1, point2);
    ValueAnimator bezierAnimator = ObjectAnimator.ofObject(typeEvaluator, point0, point3);
    //加一些随机插值器(效果更好）
    bezierAnimator.setInterpolator(mInterpolator[mRandom.nextInt(mInterpolator.length)]);

    bezierAnimator.setDuration(3000);
    //向估值器传入0-1的自变量t
    bezierAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override
      public void onAnimationUpdate(ValueAnimator animation) {
        PointF pointF = (PointF) animation.getAnimatedValue();
        iv.setX(pointF.x);
        iv.setY(pointF.y);
        //透明度
        float t = animation.getAnimatedFraction();
        //到顶部仍保留0.2透明度
        iv.setAlpha(1 - t + 0.2f);
      }
    });
    return bezierAnimator;
  }
}
