package com.spl.custom_widget_learn.animation;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.drawable.AnimationDrawable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.spl.custom_widget_learn.R;
import com.spl.custom_widget_learn.utils.StatusBarUtil;

/**
 * author: Oscar Liu
 * Date: 2021/3/12  16:31
 * File: BubbleMessageTouchListener
 * Desc:监听当前View的触摸事件
 * Test:
 */
public class BubbleMessageTouchListener implements View.OnTouchListener {
  //原来需要拖动爆炸的View
  private View mStaticView;
  private WindowManager mWindowManager;
  private MessageBubbleView mMessageBubbleView;
  private WindowManager.LayoutParams mParams;
  private Context mContext;
  private int mStatusbarHeight;
  //爆炸动画
  private FrameLayout mBombFrame;
  private ImageView mBombImageView;
  private BubbleDisappearListener mListener;


  public BubbleMessageTouchListener(View view, Context context,BubbleDisappearListener listener) {
    mStaticView = view;
    mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    mMessageBubbleView = new MessageBubbleView(context);
    this.mListener = listener;
    //使得动画可以运动到statusBar
    mParams = new WindowManager.LayoutParams();
    //背景要透明
    mParams.format = PixelFormat.TRANSPARENT;
    mContext = context;
    mStatusbarHeight = StatusBarUtil.getStatusBarHeight(mContext);

    mBombFrame = new FrameLayout(mContext);
    mBombImageView = new ImageView(mContext);

    mBombImageView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    mBombFrame.addView(mBombImageView);

    //为了实现拖拽后的消失和爆炸，实现两个方法
    mMessageBubbleView.setMessageBubbleListener(new MessageBubbleView.MessageBubbleListener() {
      @Override
      public void restore() {
        //把消息的View移除
        mWindowManager.removeView(mMessageBubbleView);
        //把原来的view显示
        mStaticView.setVisibility(View.VISIBLE);
      }

      @Override
      public void dismiss(PointF pointF) {
        //把消息的View移除
        mWindowManager.removeView(mMessageBubbleView);
        //执行爆炸动画（帧动画）
        //要在mWindowManager上 添加一个爆炸动画
        mWindowManager.addView(mBombFrame, mParams);
        mBombImageView.setBackgroundResource(R.drawable.animation_bomb);

        AnimationDrawable drawable = (AnimationDrawable) mBombImageView.getBackground();
        //给爆炸动画一个偏移，与松手位置一致
        mBombImageView.setX(pointF.x - drawable.getIntrinsicWidth()/2);
        mBombImageView.setY(pointF.y - drawable.getIntrinsicHeight()/2);
        drawable.start();
        //等它执行完我要移除这个爆炸动画，也就是mBombFrame
        mBombImageView.postDelayed(new Runnable() {
          @Override
          public void run() {
            mWindowManager.removeView(mBombFrame);
            //通知一下外面，该view消失了
            mListener.dismiss(mStaticView);
            //把原来的view显示
            mStaticView.setVisibility(View.VISIBLE);
          }
        },getAnimationDrawableTime(drawable));

      }
    });
  }

  private long getAnimationDrawableTime(AnimationDrawable drawable) {
    int numberOfFrames = drawable.getNumberOfFrames();
    long time = 0;
    for(int i=0;i<numberOfFrames;i++){
      time += drawable.getDuration(i);
    }
    return time;
  }

  @Override
  public boolean onTouch(View v, MotionEvent event) {
    float currentX,currentY;
    switch (event.getAction()){
      case MotionEvent.ACTION_DOWN:

        //2.在WindowManager上面搞一个View，上一节写好的贝塞尔View
        mWindowManager.addView(mMessageBubbleView,mParams);
        //3.初始化贝塞尔的点
        //getRowX(): 获取相对屏幕的位置，getX() ：获取相对父布局的位置

        //将初始点设置在mStaticView的中心点
        int[] location = new int[2];
        mStaticView.getLocationOnScreen(location);
        currentX  = location[0] + mStaticView.getWidth()/2;//event.getRawX();
        currentY  = location[1] + mStaticView.getHeight()/2- mStatusbarHeight;//event.getRawY()- mStatusbarHeight;
        mMessageBubbleView.initPoint(currentX,currentY);

        //4.给消息拖拽设置一个Bitmap
        mMessageBubbleView.setDragBitmap(getBitmapByView(mStaticView));

        //1.把自己隐藏，写在后面，避免画面抖动
        mStaticView.setVisibility(View.INVISIBLE);
        break;
      case MotionEvent.ACTION_MOVE:
        //1.移动时，更新贝塞尔的点
        currentX = event.getRawX();
        currentY = event.getRawY() - mStatusbarHeight;
        mMessageBubbleView.updateDragPoint(currentX,currentY);

        break;
      case MotionEvent.ACTION_UP:
        mMessageBubbleView.handleActionUp();
        break;
    }
    return true;
  }


  /**
   * 从一个View中获取bitmap
   */
  private Bitmap getBitmapByView(View view){
//    view.buildDrawingCache();
//    Bitmap bitmap = view.getDrawingCache();
    int width = view.getWidth();
    int height = view.getHeight();
    Bitmap bitmap = Bitmap.createBitmap(width,height,Bitmap.Config.RGB_565);
    Canvas canvas = new Canvas(bitmap);
    view.draw(canvas);
    return bitmap;
  }

  /**
   * 由activity传入，实现主页面的回调方法
   */
  public interface BubbleDisappearListener {
    void dismiss(View view);
  }
}
