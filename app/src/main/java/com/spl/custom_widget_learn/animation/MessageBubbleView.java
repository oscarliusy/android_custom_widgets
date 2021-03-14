package com.spl.custom_widget_learn.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import static android.widget.RelativeLayout.CENTER_IN_PARENT;

/**
 * author: Oscar Liu
 * Date: 2021/3/11  18:00
 * File: MessageBubbleView
 * Desc:可以和任意View绑定，生成拖拽，爆炸特效的自定义View
 * Test:
 */
public class MessageBubbleView extends View {
  private PointF mFixPoint, mDragPoint;
  private int mDragRadius = 10;
  private final int FIX_RADIUS_MAX = dip2px(8);
  private final int FIX_RADIUS_MIN = dip2px(2);
  private int mFixRadius;
  //画笔
  private Paint mDragPaint, mFixPaint;
  private int distance;
  //根据被拖拽的View生成的bitmap
  private Bitmap mDragBitmap;
  private Path bezeierPath;

  public MessageBubbleView(Context context) {
    this(context, null);
  }

  public MessageBubbleView(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public MessageBubbleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    mDragRadius = dip2px(mDragRadius);
    //拖拽圆画笔
    mDragPaint = new Paint();
    mDragPaint.setColor(Color.RED);
    mDragPaint.setAntiAlias(true);
    mDragPaint.setDither(true);
    //固定圆画笔
    mFixPaint = new Paint();
    mFixPaint.setColor(Color.RED);
    mFixPaint.setAntiAlias(true);
    mFixPaint.setDither(true);
  }


  private int dip2px(int dip) {
    return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getResources().getDisplayMetrics());
  }

  @Override
  protected void onDraw(Canvas canvas) {

    if (mDragPoint == null || mFixPoint == null) {
      return;
    }
    //画两个圆
    //拖拽圆
    canvas.drawCircle(mDragPoint.x, mDragPoint.y, mDragRadius, mDragPaint);

    //计算2圆距离
    distance = getDistance(mDragPoint, mFixPoint);

    //计算固定圆半径,半径随着距离增加而减小，到一定程度就消失了
    mFixRadius = FIX_RADIUS_MAX - distance / 14;

    bezeierPath = getBezeierPath();

    if (bezeierPath != null) {
      //固定圆
      canvas.drawCircle(mFixPoint.x, mFixPoint.y, mFixRadius, mFixPaint);
      //画贝塞尔曲线
      canvas.drawPath(bezeierPath, mDragPaint);
    }

    //画图片,位置跟随手指移动,中心位置才是手指拖动的位置
    if (mDragBitmap != null) {
      canvas.drawBitmap(
          mDragBitmap,
          mDragPoint.x - mDragBitmap.getWidth() / 2,
          mDragPoint.y - mDragBitmap.getHeight() / 2,
          null
      );
    }
  }

  private Path getBezeierPath() {
    if (mFixRadius < FIX_RADIUS_MIN) {
      return null;
    }
    Path bezierPath = new Path();

    float dx = mFixPoint.x - mDragPoint.x;
    float dy = mFixPoint.y - mDragPoint.y;
    if (dx == 0) {
      dx = 0.001f;
    }
    float tan = dy / dx;
    //获取角度
    float arcTanA = (float) Math.atan(tan);

    //计算p0,p1,p2,p3的位置
    float P0X = (float) (mFixPoint.x + mFixRadius * Math.sin(arcTanA));
    float P0Y = (float) (mFixPoint.y - mFixRadius * Math.cos(arcTanA));

    float P1X = (float) (mDragPoint.x + mDragRadius * Math.sin(arcTanA));
    float P1Y = (float) (mDragPoint.y - mDragRadius * Math.cos(arcTanA));

    float P2X = (float) (mDragPoint.x - mDragRadius * Math.sin(arcTanA));
    float P2Y = (float) (mDragPoint.y + mDragRadius * Math.cos(arcTanA));

    float P3X = (float) (mFixPoint.x - mFixRadius * Math.sin(arcTanA));
    float P3Y = (float) (mFixPoint.y + mFixRadius * Math.cos(arcTanA));

    //求控制点，以两个圆心中心为控制点
    PointF controlPoint = getPointByPercent(mDragPoint, mFixPoint, 0.5f);

    // 整合贝塞尔曲线路径
    bezierPath.moveTo(P0X, P0Y);
    //使用控制点和目标点，画贝塞尔曲线
    bezierPath.quadTo(controlPoint.x, controlPoint.y, P1X, P1Y);
    bezierPath.lineTo(P2X, P2Y);
    bezierPath.quadTo(controlPoint.x, controlPoint.y, P3X, P3Y);
    //关闭路径
    bezierPath.close();
    return bezierPath;
  }

  private PointF getPointByPercent(PointF mDragPoint, PointF mFixPoint, float percent) {
    PointF controlPoint = new PointF();
    float x = mFixPoint.x + (mDragPoint.x - mFixPoint.x) * percent;
    float y = mFixPoint.y + (mDragPoint.y - mFixPoint.y) * percent;
    controlPoint.x = x;
    controlPoint.y = y;
    return controlPoint;
  }

  private int getDistance(PointF pointA, PointF pointB) {
    float dx = pointA.x - pointB.x;
    float dy = pointA.y - pointB.y;
    int dis = (int) Math.sqrt(dx * dx + dy * dy);
    return dis;

  }

  /**
   *处理两个圆点的点击事件
   * 向activity中view绑定效果时，注释此部分，在BubbleMessageTouchListener中实现
   */
  /**
   @Override public boolean onTouchEvent(MotionEvent event) {
   switch (event.getAction()) {
   case MotionEvent.ACTION_DOWN:
   //手指按下去指定当前位置,初始化手指位置
   float downX = event.getX();
   float downY = event.getY();
   initPoint(downX,downY);

   break;
   case MotionEvent.ACTION_MOVE:
   //手指移动时，更新手指位置
   float moveX = event.getX();
   float moveY = event.getY();
   updateDragPoint(moveX,moveY);
   break;
   case MotionEvent.ACTION_UP:
   break;
   }
   invalidate();
   return true;
   }
   **/

  /**
   * 更新当前拖拽点的位置
   *
   * @param moveX
   * @param moveY
   */
  public void updateDragPoint(float moveX, float moveY) {
    mDragPoint.x = moveX;
    mDragPoint.y = moveY;
    //更新后重新绘制
    invalidate();
  }

  /**
   * 初始化按键位置
   */
  public void initPoint(float downX, float downY) {
    mFixPoint = new PointF(downX, downY);
    mDragPoint = new PointF(downX, downY);
  }

  /**
   * 手指松开处理回弹和爆炸
   */
  public void handleActionUp() {
    if(mFixRadius > FIX_RADIUS_MIN){
      //回弹 ValueAnimator 值变化的动画 0--》1
      ValueAnimator animator = ObjectAnimator.ofFloat(1);
      animator.setDuration(350);
      PointF start = new PointF(mDragPoint.x,mDragPoint.y);
      PointF end = new PointF(mFixPoint.x,mFixPoint.y);
      animator.setInterpolator(new BounceInterpolator());
      animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
          float percent = 1-(float) animation.getAnimatedValue();
          PointF pointF = getPointByPercent(start,end,percent);
          //用代码更新拖拽点
          updateDragPoint(pointF.x,pointF.y);
        }
      });
      animator.start();
      //还要通知TouchListener移除当前View，然后显示staticView
      animator.addListener(new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
          if(mListener!=null){
            mListener.restore();
          }
        }
      });
    }else{
      //爆炸
      if(mListener!=null){
        mListener.dismiss(mDragPoint);
      }
    }

  }



  private MessageBubbleListener mListener;
  public void setMessageBubbleListener(MessageBubbleListener listener){
    this.mListener = listener;
  }

  public interface MessageBubbleListener{
    //还原
    public void restore();
    //消失爆炸
    public void dismiss(PointF pointF);
  }

  /**
   * 绑定可以拖拽的控件
   *
   * @param view
   * @param listener
   */
  public static void attach(View view, BubbleMessageTouchListener.BubbleDisappearListener listener) {
    view.setOnTouchListener(new BubbleMessageTouchListener(view, view.getContext(),listener));
  }

  public void setDragBitmap(Bitmap bitmap) {
    mDragBitmap = bitmap;
  }
}
