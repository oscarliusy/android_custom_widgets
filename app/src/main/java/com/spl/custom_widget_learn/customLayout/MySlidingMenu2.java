package com.spl.custom_widget_learn.customLayout;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;

import com.spl.custom_widget_learn.R;

/**
 * author: Oscar Liu
 * Date: 2021/1/22  11:34
 * File: MySlidingMenu2
 * Desc:仿QQ6.0主页面侧滑效果
 * Test:
 */
public class MySlidingMenu2 extends HorizontalScrollView {

  private int mMenuWidth;
  private float rightPadding;
  private ViewGroup mMenuView;
  private ViewGroup mContentView;
  private View mShadowView;
  // 手势处理类 主要用来处理手势快速滑动
  private GestureDetector mGestureDetector;
  // 菜单是否打开
  private boolean mMenuIsOpen = false;
  private boolean mIsIntercept = false;//当使用onIntercept时，控制onTouchEvent是否执行

  public MySlidingMenu2(Context context) {
    this(context, null);
  }

  public MySlidingMenu2(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public MySlidingMenu2(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    // 获取自定义的右边留出的宽度
    TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MySlidingMenu2);
    rightPadding = array.getDimension(R.styleable.MySlidingMenu2_rightPadding, dip2px(50));
    // 计算菜单的宽度 = 屏幕的宽度 - 自定义右边留出的宽度
    mMenuWidth = (int) (getScreenWidth() - rightPadding);
    array.recycle();

    // 实例化手势处理类
    mGestureDetector = new GestureDetector(context, new GestureListener());
  }

  private int getScreenWidth() {
    Resources resources = this.getResources();
    DisplayMetrics dm = resources.getDisplayMetrics();
    return dm.widthPixels;
  }

  private float dip2px(int dip) {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, dip, getResources().getDisplayMetrics()
    );
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();

    //1.根据根View获取测量结果
    ViewGroup container = (ViewGroup) this.getChildAt(0);

    int containerChildCount = container.getChildCount();
    if (containerChildCount > 2) {
      throw new IllegalStateException("SlidingMenu根布局LinearLayout下面仅包含2个子布局,menu & content");
    }

    //2.获取菜单和内容布局
    mMenuView = (ViewGroup) container.getChildAt(0);

    //把内容布局单独提出来
    mContentView = (ViewGroup) container.getChildAt(1);
    ViewGroup.LayoutParams contentParams = mContentView.getLayoutParams();
    container.removeView(mContentView);
    //然后在外面套一层阴影
    RelativeLayout contentContainer = new RelativeLayout(getContext());
    contentContainer.addView(mContentView);
    mShadowView = new View(getContext());
    mShadowView.setBackgroundColor(Color.parseColor("#55000000"));
    contentContainer.addView(mShadowView);
    mShadowView.setAlpha(0.0f);
    //最后把容器放回原位
    contentParams.width = getScreenWidth();
    contentContainer.setLayoutParams(contentParams);


    //3指定内容和菜单布局的宽度
    //3.1菜单宽度 = 屏幕宽度 - 自定义的右边留出的宽度
    mMenuView.getLayoutParams().width = mMenuWidth;
    //3.2 内容宽度 = 屏幕宽度
//    mContentView.getLayoutParams().width = getScreenWidth();
    container.addView(contentContainer);
  }

  @Override
  public boolean onTouchEvent(MotionEvent ev) {
    /**
     * 1.获取手指滑动的速率，当大于一定值就认为是快速滑动，进入GestureDetector处理event
     * 2.如果进入GestureDetector处理返回了true，就结束方法。不进入switch
     */
    if (mGestureDetector.onTouchEvent(ev)) {
      return mGestureDetector.onTouchEvent(ev);
    }

    //如果有拦截，就不往下走了
    if (mIsIntercept) {
      return true;
    }

    switch (ev.getAction()) {
      case MotionEvent.ACTION_UP:
        // 手指抬起获取滚动的位置
        int currentScrollX = getScrollX();
        if (currentScrollX > mMenuWidth / 2) {
          //关闭菜单
          closeMenu();
        } else {
          //打开菜单
          openMenu();
        }
        return false;
    }
    return super.onTouchEvent(ev);
  }

  @Override
  protected void onLayout(boolean changed, int l, int t, int r, int b) {
    super.onLayout(changed, l, t, r, b);
    // 布局指定后会从新摆放子布局，当其摆放完毕后，让菜单滚动到不可见状态
    if (changed) {
      scrollTo(mMenuWidth, 0);
    }
  }

  @Override
  public boolean onInterceptTouchEvent(MotionEvent ev) {
    mIsIntercept = false;
    //2.处理事件拦截+ViewGroup事件分发
    //当菜单打开的时候，手指触摸右边内容部分需要关闭菜单，还需要拦截事件（打开情况下，点击内容页不会响应点击事件）
    if (mMenuIsOpen) {
      float currentX = ev.getX();
      if (currentX > mMenuWidth) {
        //1.关闭菜单
        closeMenu();
        //2.子View不需要响应任何事件（点击和触摸），拦截子View事件
        //返回true代表我回拦截子View，但是会响应自己的onTouchEvent，导致点击2下才能返回context页
        //这里使用mIntercept控制onTouchEvent的执行
        mIsIntercept = true;
        return true;
      }
    }
    return super.onInterceptTouchEvent(ev);
  }

  @Override
  protected void onScrollChanged(int l, int t, int oldl, int oldt) {
    super.onScrollChanged(l, t, oldl, oldt);
    //计算梯度值
    float scale = 1f * l / mMenuWidth;//scale的变化是1-0
    //控制阴影 随着content向右滑动，alpha 0 - 1
    float alphaScale = 1 - scale;

    mShadowView.setAlpha(alphaScale);


//    float rightScale = 0.7f + 0.3f * scale;//可以用来计算缩放比例
//    float leftAlpha = 0.5f + (1 - scale) * 0.5f;//可以计算透明度变化

    //这个做法的效果不太明白
    mMenuView.setTranslationX(l * 0.8f);
  }

  private class GestureListener extends GestureDetector.SimpleOnGestureListener {
    //可以对按下，点击，滑动等手势做监听
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
      // 当手指快速滑动时候回调的方法
      // 如果菜单打开 并且是向左快速滑动 切换菜单的状态
      //左滑：负数，右划：正数
      if (mMenuIsOpen) {
        if (velocityX < -500) {
          toggleMenu();
          return true;
        }
      } else {
        // 如果菜单关闭 并且是向右快速滑动 切换菜单的状态
        if (velocityX > 500) {
          toggleMenu();
          return true;
        }
      }
      return false;
    }
  }

  public void toggleMenu() {
    if (mMenuIsOpen) {
      closeMenu();
    } else {
      openMenu();
    }
  }

  private void openMenu() {
    smoothScrollTo(0, 0);
    mMenuIsOpen = true;
  }

  private void closeMenu() {
    smoothScrollTo(mMenuWidth, 0);
    mMenuIsOpen = false;
  }


}
