package com.spl.custom_widget_learn.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

/**
 * author: Oscar Liu
 * Date: 2021/3/8  11:18
 * File: ListDataScreenView
 * Desc: 实现58同城多条目数据筛选顶部tab+下拉菜单+阴影
 * Test:
 */
public class ListDataScreenView extends LinearLayout implements View.OnClickListener {

  private Context mContext;
  //1.1 创建头部用来存放Tab
  private LinearLayout mMenuTabView;
  //1.2 创建FrameLayout用来存放阴影+菜单内容布局（FrameLayout)
  private FrameLayout mMenuMiddleView;
  //阴影
  private View mShadowView;
  //阴影颜色,可以修改透明度
  private int mShadowColor = 0x88888888;
  private FrameLayout mMenuContainerView;
  //筛选菜单的adapter
  private BaseMenuAdapter mAdapter;
  //内容菜单的高度
  private int mMenuContainerHeight;
  //当前打开的位置
  private int mCurrentPosition = -1;
  private long DURATION_TIME = 350;
  //动画是否在进行
  private boolean mAnimatorExcute;

  public ListDataScreenView(Context context) {
    this(context, null);
  }

  public ListDataScreenView(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public ListDataScreenView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    mContext = context;
    initLayout();
  }

  /**
   * 1.布局实例化（组合控件）
   */
  private void initLayout() {
    //1.先创建一个xml布局，再加载，findViewById
    //2.简单的效果，用代码去创建
    setOrientation(VERTICAL);
    //1.1 创建头部用来存放Tab
    mMenuTabView = new LinearLayout(mContext);
    mMenuTabView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    addView(mMenuTabView);
    //1.2 创建FrameLayout用来存放阴影+菜单内容布局（FrameLayout)
    mMenuMiddleView = new FrameLayout(mContext);
    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
    params.weight = 1;
    mMenuMiddleView.setLayoutParams(params);
    addView(mMenuMiddleView);

    //向FrameLayout中添加阴影，可以不设置LayoutParams,默认就是match_parent
    mShadowView = new View(mContext);
    mShadowView.setBackgroundColor(mShadowColor);
    //开始时，阴影不显示
    mShadowView.setAlpha(0f);
    mShadowView.setOnClickListener(this);
    mShadowView.setVisibility(GONE);
    mMenuMiddleView.addView(mShadowView);
    //创建菜单 用来存放菜单内容
    mMenuContainerView = new FrameLayout(mContext);
    mMenuContainerView.setBackgroundColor(Color.WHITE);
    mMenuMiddleView.addView(mMenuContainerView);
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    //内容的高度占整个View的75%
    int height = MeasureSpec.getSize(heightMeasureSpec);
    //加入判断，避免切换tab时重新调用onMeasure
    if (mMenuContainerHeight == 0 && height > 0) {
      mMenuContainerHeight = (int) (height * 75f / 100);
      ViewGroup.LayoutParams params = mMenuContainerView.getLayoutParams();
      params.height = mMenuContainerHeight;
      mMenuContainerView.setLayoutParams(params);

      //进来时，内容不显示（移上去）
      mMenuContainerView.setTranslationY(-mMenuContainerHeight);
    }
  }

  /**
   * 具体的观察者类
   */
  private class AdapterMenuObserver extends MenuObserver {

    @Override
    public void closeMenu() {
      //如果有注册就会收到通知
      ListDataScreenView.this.closeMenu();
    }
  }

  private AdapterMenuObserver mMenuObserver;

  /**
   * 设置adapter
   */
  public void setAdapter(BaseMenuAdapter menuAdapter) {
    if (menuAdapter == null) {
      throw new NullPointerException("menuAdapter 不能为null");
    }

    //================================观察者模式↓====================================
    //观察者 相当于微信的公众号用户
    if (mAdapter != null && mMenuObserver != null) {
      //取消订阅，避免设置两次observer
      mAdapter.unregisterDataSetObserver(mMenuObserver);
    }
    this.mAdapter = menuAdapter;
    //注册观察者 具体的观察者实例对象 定于
    mMenuObserver = new AdapterMenuObserver();
    mAdapter.registDataSetObserver(mMenuObserver);
    //================================观察者模式↑====================================

    //获取有多少条
    int count = mAdapter.getCount();
    for (int i = 0; i < count; i++) {
      //获取菜单的tab
      View tabView = menuAdapter.getTabView(i, mMenuTabView);
      mMenuTabView.addView(tabView);
      LinearLayout.LayoutParams params = (LayoutParams) tabView.getLayoutParams();
      params.weight = 1;
      tabView.setLayoutParams(params);
      //设置tab点击事件
      setTabClick(tabView, i);

      //获取菜单的内容
      View menuView = menuAdapter.getMenuView(i, mMenuContainerView);
      menuView.setVisibility(GONE);
      mMenuContainerView.addView(menuView);
    }

  }

  //设置tab点击事件
  private void setTabClick(View tabView, int position) {
    tabView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (mCurrentPosition == -1) {
          //没打开
          openMenu(tabView, position);
        } else {
          if (mCurrentPosition == position) {
            //打开了,关闭
            closeMenu();
          } else {
            //切换一下显示,将当前的menu隐藏，tab变色
            View currentMenu = mMenuContainerView.getChildAt(mCurrentPosition);
            currentMenu.setVisibility(View.GONE);
            mAdapter.menuClose(mMenuTabView.getChildAt(mCurrentPosition));

            //将选择的新menu显示，tab变色
            mCurrentPosition = position;
            currentMenu = mMenuContainerView.getChildAt(mCurrentPosition);
            currentMenu.setVisibility(View.VISIBLE);
            mAdapter.menuOpen(mMenuTabView.getChildAt(mCurrentPosition));
          }
        }
      }
    });
  }

  //关闭菜单
  private void closeMenu() {
    if (mAnimatorExcute) {
      //当有动画在执行时，避免再次执行
      return;
    }
    //打开关闭动画 位移动画，透明度动画
    ObjectAnimator translationAnimator = ObjectAnimator.ofFloat(mMenuContainerView, "translationY", 0, -mMenuContainerHeight);
    translationAnimator.setDuration(DURATION_TIME);
    translationAnimator.start();

    mShadowView.setVisibility(View.VISIBLE);
    ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(mShadowView, "alpha", 1f, 0f);
    alphaAnimator.setDuration(DURATION_TIME);
    //要等关闭动画执行完才能隐藏当前菜单
    alphaAnimator.addListener(new AnimatorListenerAdapter() {
      @Override
      public void onAnimationEnd(Animator animation) {
        View menuView = mMenuContainerView.getChildAt(mCurrentPosition);
        menuView.setVisibility(GONE);
        //不隐藏会影响遮蔽区域的activity中内容的点击
        mShadowView.setVisibility(GONE);
        mAnimatorExcute = false;
        mCurrentPosition = -1;
      }

      @Override
      public void onAnimationStart(Animator animation) {
        mAnimatorExcute = true;
        //把当前的tab传到外面,控制tab变色等
        mAdapter.menuClose(mMenuTabView.getChildAt(mCurrentPosition));
      }
    });
    alphaAnimator.start();

  }

  //打开菜单
  private void openMenu(View tabView, final int position) {
    //如果动画正在执行，返回
    if (mAnimatorExcute) {
      return;
    }
    //获取当前位置，显示当前菜单，菜单是加到了菜单容器
    View menuView = mMenuContainerView.getChildAt(position);
    menuView.setVisibility(VISIBLE);


    //打开开启动画 位移动画，透明度动画
    ObjectAnimator translationAnimator = ObjectAnimator.ofFloat(mMenuContainerView, "translationY", -mMenuContainerHeight, 0);
    translationAnimator.setDuration(DURATION_TIME);
    translationAnimator.start();

    mShadowView.setVisibility(View.VISIBLE);
    ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(mShadowView, "alpha", 0f, 1f);
    alphaAnimator.setDuration(DURATION_TIME);
    alphaAnimator.addListener(new AnimatorListenerAdapter() {
      @Override
      public void onAnimationEnd(Animator animation) {
        mCurrentPosition = position;
        mAnimatorExcute = false;
      }

      @Override
      public void onAnimationStart(Animator animation) {
        mAnimatorExcute = true;
        //把当前的tab传到外面,控制tab变色等
        mAdapter.menuOpen(tabView);
      }
    });
    alphaAnimator.start();
  }

  //点击阴影关闭菜单
  @Override
  public void onClick(View v) {
    closeMenu();
  }
}
