package com.spl.custom_widget_learn.animation;

import android.animation.TypeEvaluator;
import android.graphics.PointF;
import android.util.Log;

/**
 * author: Oscar Liu
 * Date: 2021/3/14  15:40
 * File: LoveTypeEvaluator
 * Desc: 用来实现贝塞尔曲线运动的自定义估值器
 * Test:
 */
public class LoveTypeEvaluator implements TypeEvaluator<PointF> {
  private PointF point1,point2;//三阶贝塞尔的2个控制点

  public LoveTypeEvaluator(PointF point1, PointF point2) {
    this.point1 = point1;
    this.point2 = point2;
  }

  /**
   * ObjectAnimator会调用这个方法，获取返回的估值
   * @param t   0-1,贝塞尔函数中的变量
   * @param point0  三阶贝塞尔的起点
   * @param point3  三阶贝塞尔的终点
   * @return
   */
  @Override
  public PointF evaluate(float t, PointF point0, PointF point3) {
    //这里的t自动赋值0-1， result = x0 + t * (x1 - x0)
    PointF point = new PointF();
    point.x = point0.x * (1 - t) * (1 - t) * (1 - t) //
        + 3 * point1.x * t * (1 - t) * (1 - t)//
        + 3 * point2.x * t * t * (1 - t)//
        + point3.x * t * t * t;//

    point.y = point0.y * (1 - t) * (1 - t) * (1 - t) //
        + 3 * point1.y * t * (1 - t) * (1 - t)//
        + 3 * point2.y * t * t * (1 - t)//
        + point3.y * t * t * t;//
    // 套用上面的公式把点返回
    return point;
  }
}
