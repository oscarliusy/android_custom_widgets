package com.spl.custom_widget_learn.customLayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.ListViewCompat;
import androidx.customview.widget.ViewDragHelper;

/**
 * author: Oscar Liu
 * Date: 2021/1/23  11:53
 * File: VerticalDragListView
 * Desc: 垂直方向抽屉，仿汽车之家
 * Test:
 */
public class VerticalDragListView extends FrameLayout {

  //可以认为这是系统给我们写好的工具类
  private ViewDragHelper mDragHelper;

  private View mDragListView;
  //后面菜单的高度
  private int mMenuHeight;
  private View mMenuView;
  private int velocityThreshold;
  private boolean mMenuIsOpen;//菜单是否打开

  public VerticalDragListView(@NonNull Context context) {
    this(context, null);
  }

  public VerticalDragListView(@NonNull Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public VerticalDragListView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);

    //创建mDragHelper实例
    mDragHelper = ViewDragHelper.create(this, mDragHelperCallback);
  }

  //onFinishInflate在页面的setOnContentView中调用，此时还没有开始View的绘制，拿不到控件尺寸
  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();

    int childCount = getChildCount();
    if (childCount != 2) {
      throw new RuntimeException("VerticalDragListView 只能包含 2个子布局");
    }
    mDragListView = getChildAt(1);
  }

  @Override
  protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
    super.onLayout(changed, left, top, right, bottom);
    //测量菜单高度，必须在onMeasure之后的生命周期
    if (changed) {
      mMenuView = getChildAt(0);
      mMenuHeight = mMenuView.getMeasuredHeight();
    }
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    //将事件传入mDragHelper
    mDragHelper.processTouchEvent(event);
    return true;
  }

  //1.拖动我们的子View
  private ViewDragHelper.Callback mDragHelperCallback = new ViewDragHelper.Callback() {
    @Override
    public boolean tryCaptureView(@NonNull View child, int pointerId) {
      //指定该子View是否可以拖动，就是child
      //默认全都能拖动，这里只能列表mDragListView拖动
      //2.1 后面不能拖动
      return mDragListView == child;
    }

    //实现垂直拖动必须重写这个方法
    @Override
    public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
      //垂直拖动移动的位置
      //2.3 垂直拖动的范围只能是后面菜单View的高度
      if (top <= 0) {
        top = 0;
      }
      if (top >= mMenuHeight) {
        top = mMenuHeight;
      }
      return top;
    }

    //实现水平拖动必须重写这个方法
    //2.2列表只能垂直拖动
//    @Override
//    public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
//      //水平拖动移动的位置
//      return left;
//    }

    //2.4 手指松开时，打开或关闭
    //2.5 当拖动速度大于阈值，就打开，小于，则关闭
    @Override
    public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
      /**
       *       //通过滑动速度进行控制
       *       velocityThreshold = 300;
       *       if(yvel > velocityThreshold){
       *         //滚动到菜单高度 打开
       *         mDragHelper.smoothSlideViewTo(releasedChild,0,mMenuHeight);
       *       }else{
       *         //滚动到0，关闭
       *         mDragHelper.smoothSlideViewTo(releasedChild,0,0);
       *       }
       */

      //通过手指松开位置进行控制
      if (mDragListView.getTop() > mMenuHeight / 2) {
        mDragHelper.smoothSlideViewTo(releasedChild, 0, mMenuHeight);
        mMenuIsOpen = true;
      } else {
        mDragHelper.smoothSlideViewTo(releasedChild, 0, 0);
        mMenuIsOpen = false;
      }
      invalidate();
    }
  };
  //www.tuicool.com/articles/Zru6zi

  //现象：listView可以滑动，但是菜单没有滑动效果

  private float mDownY;

  @Override
  public boolean onInterceptTouchEvent(MotionEvent ev) {
    //菜单打开，要全部拦截
    if (mMenuIsOpen) {
      return true;
    }
    //向下滑动拦截，不要给ListView做处理
    //谁拦截谁 父View拦截子View，但是子View可以调用requestDisallowInterceptTouchEvent,请求父View不要拦截
    //改变mGroupFlags的值
    switch (ev.getAction()) {
      case MotionEvent.ACTION_DOWN:
        mDownY = ev.getY();
        mDragHelper.processTouchEvent(ev);
        break;
      case MotionEvent.ACTION_MOVE:
        float moveY = ev.getY();
        if ((moveY - mDownY) > 0 && !canChildScrollUp()) {
          //向下滑动拦截 && ListView滚动到了顶部,拦截不让ListView做处理
          return true;
        }
        break;
    }

    return super.onInterceptTouchEvent(ev);
  }

  /**
   * 从SwipeRefreshLayout偷来的源码，改造一下自己用
   *
   * @return Whether it is possible for the child view of this layout to
   * scroll up. Override this if the child view is a custom view.
   * 判断View是否滚动到了顶部
   */
  public boolean canChildScrollUp() {
    ListView mTarget = (ListView) mDragListView;
    if (mTarget instanceof ListView) {
      return ListViewCompat.canScrollList((ListView) mTarget, -1);
    }
    return mTarget.canScrollVertically(-1);
  }


  //响应滚动
  @Override
  public void computeScroll() {
    if (mDragHelper.continueSettling(true)) {
      invalidate();
    }
  }
}





















