package com.spl.custom_widget_learn.utils;

/**
 * author: Oscar Liu
 * Date: 2021/1/24  10:51
 * File: MathUtil
 * Desc:
 * Test:
 */
public class MathUtil {
  /**
   * 判断点是否在圆内
   * @param sx 圆心x
   * @param sy 圆心y
   * @param r 半径
   * @param x
   * @param y
   * @return 布尔值
   */
  public static boolean checkInRound(float sx, float sy, float r, float x, float y) {
    return Math.sqrt((sx - x) * (sx - x) + (sy - y) * (sy - y)) < r;
  }

  public static double pointToDegrees(double x,double y){
    return Math.toDegrees(Math.atan2(x,y));
  }

  public static double distance(double x1,double y1,double x2,double y2){
    return Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
  }
}
