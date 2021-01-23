package com.spl.custom_widget_learn.customLayout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Scroller;

/**
 * author: Oscar Liu
 * Date: 2021/1/21  16:33
 * File: MySlidingMenu1
 * Desc:滑动菜单类
 * Test:
 */
public class MySlidingMenu1 extends ViewGroup {

  private int mScreenWidth;
  private int mScreenHeight;
  private int mMenuRightPadding;
  private ViewGroup mMenu;
  private ViewGroup mContent;
  private int mMenuWidth;
  private int mContentWidth;
  private int mLastX;
  private int mLastY;
  private Scroller mScroller;
  private boolean isOpen = false;
  private int mLastXIntercept;
  private int mLastYIntercept;
  private float scale;


  public MySlidingMenu1(Context context) {
    this(context, null, 0);
  }

  public MySlidingMenu1(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public MySlidingMenu1(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    //描述有关显示器的一般信息
    DisplayMetrics metrics = new DisplayMetrics();
    WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    //访问DisplayMetrics成员，需要进行初始化
    wm.getDefaultDisplay().getMetrics(metrics);
    //获取屏幕的宽高
    mScreenWidth = metrics.widthPixels;
    mScreenHeight = metrics.heightPixels;
    //设置Menu距离屏幕右侧的距离，convertToDp是将代码中的100转换成100dp
    mMenuRightPadding = convertToDp(context, 100);
    //实现自动滑动的滑动器
    mScroller = new Scroller(context);
  }

  private int convertToDp(Context context, int num) {
    return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, num, context.getResources().getDisplayMetrics());
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    //拿到Menu，第0个child
    mMenu = (ViewGroup) getChildAt(0);
    //拿到Content，第1个child
    mContent = (ViewGroup) getChildAt(1);
    //设置Menu的宽为屏幕宽度-Menu距离屏幕右侧的距离
    mMenuWidth = mMenu.getLayoutParams().width = mScreenWidth - mMenuRightPadding;
    //设置Content的宽为屏幕宽度
    mContentWidth = mContent.getLayoutParams().width = mScreenWidth;
    //测量Menu
    measureChild(mMenu, widthMeasureSpec, heightMeasureSpec);
    //测量Content
    measureChild(mContent, widthMeasureSpec, heightMeasureSpec);
    //测量自己
    setMeasuredDimension(mMenuWidth + mContentWidth, mScreenHeight);
  }

  @Override
  protected void onLayout(boolean changed, int l, int t, int r, int b) {
    //摆放Menu的位置
    mMenu.layout(-mMenuWidth, 0, 0, mScreenHeight);
    //摆放Content的位置
    mContent.layout(0, 0, mScreenWidth, mScreenHeight);
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    switch (event.getAction()) {
      case MotionEvent.ACTION_DOWN:
        mLastX = (int) event.getX();
        mLastY = (int) event.getY();
        break;
      case MotionEvent.ACTION_MOVE:
        int currentX = (int) event.getX();
        int currentY = (int)event.getY();
        //拿到x方向的偏移量
        int dx = currentX - mLastX;
        if(dx < 0){//向左滑动
          //边界控制，如果Menu已经完全显示，就停止，避免出现白边
          if(getScrollX() + Math.abs(dx)>=0){
            //直接移动到(0,0)位置，不会出现白边
            scrollTo(0,0);
          }else{//Menu还没有完全显示
            //dx的正负，可以自己去尝试
            scrollBy(-dx,0);
            slidingMode2();
          }
        }else{//向右滑动
          //边界控制，如果Content已经完全显示，再滑动的话
          //Content右侧就会出现白边了，进行边界控制
          if(getScrollX()-dx <= -mMenuWidth){
            //直接移动到（-mMenuWidth,0）位置，不会出现白边
            scrollTo(-mMenuWidth,0);
          }else{//Content没有完全显示呢
            scrollBy(-dx,0);
            slidingMode2();
          }
        }
        mLastX = currentX;
        mLastY = currentY;
        //计算划过的比例，计算缩放倍数
        scale = Math.abs((float)getScaleX())/(float)mMenuWidth;
        break;
      case MotionEvent.ACTION_UP:
        if(getScrollX()<-mMenuWidth/2){//满足条件自动打开menu
          //调用startScroll方法，第一个参数是起始X坐标，第二个参数
          //是起始Y坐标，第三个参数是X方向偏移量，第四个参数是Y方向偏移量
          mScroller.startScroll(getScrollX(),0,-mMenuWidth-getScrollX(),0,300);
          //设置一个已经打开的标识，当实现点击开关自动打开关闭功能时会用到
          isOpen = true;
          //重绘
          invalidate();
        }else{//关闭menu
          mScroller.startScroll(getScrollX(),0,-getScrollX(),0,300);
          isOpen = false;
          invalidate();
        }
        break;
    }
    return true;
  }

  @Override
  public boolean onInterceptTouchEvent(MotionEvent ev) {
    boolean intercept = false;
    int x = (int)ev.getX();
    int y = (int)ev.getY();
    switch(ev.getAction()){
      case MotionEvent.ACTION_DOWN:
      case MotionEvent.ACTION_UP:
        intercept = false;
        break;
      case MotionEvent.ACTION_MOVE:
        int deltaX = (int)ev.getX() - mLastXIntercept;
        int deltaY = (int)ev.getY() - mLastYIntercept;
        if(Math.abs(deltaX)>Math.abs(deltaY)){
          //横向滑动，实施拦截
          intercept = true;
        }else{
          //纵向滑动，放开拦截
          intercept = false;
        }
        break;
    }
    mLastX = x;
    mLastY = y;
    mLastXIntercept = x;
    mLastYIntercept = y;
    return intercept;
  }

  //确保自然滑动的模板写法
  @Override
  public void computeScroll() {
    if(mScroller.computeScrollOffset()){
      scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
      scale = Math.abs((float)getScrollX()) / (float) mMenuWidth;
      slidingMode2();
      invalidate();
    }
  }

  public void toggleMenu(){
    if(isOpen){
      closeMenu();
    }else{
      openMenu();
    }
  }

  private void openMenu() {
    mScroller.startScroll(getScrollX(),0,-mMenuWidth-getScrollX(),0,500);
    invalidate();
    isOpen = true;
  }

  private void closeMenu() {
    mScroller.startScroll(getScrollX(),0,-getScrollX(),0,500);
    invalidate();
    isOpen = false;
  }

  private void slidingMode1(){

  }

  private void slidingMode2(){
    mMenu.setTranslationX(2*(mMenuWidth+getScrollX())/3);
  }

  private void slidingMode3(){
    mMenu.setTranslationX(mMenuWidth + getScrollX() - (mMenuWidth/2)*(1.0f-scale));
    mMenu.setScaleX(0.7f + 0.3f*scale);
    mMenu.setScaleY(0.7f + 0.3f*scale);
    mMenu.setAlpha(scale);

    mContent.setScaleX(1 - 0.3f*scale);
    mContent.setPivotX(0);
    mContent.setScaleY(1.0f - 0.3f * scale);
  }
}




















