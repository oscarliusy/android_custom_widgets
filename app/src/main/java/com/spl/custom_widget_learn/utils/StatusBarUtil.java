package com.spl.custom_widget_learn.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

/**
 * author: Oscar Liu
 * Date: 2021/2/20  10:18
 * File: StatusBarUtil
 * Desc: 修改页面顶部状态栏工具类
 * Test:
 */
public class StatusBarUtil {
  /**
   * 为activity的状态栏设置颜色
   * @param activity
   * @param color
   * 兼容问题：
   *    5.0以下，头部状态栏默认是黑色，
   *    5.0以上是有颜色的，这个是系统帮我们做的处理
   *    4.4以下，没办法处理，主要处理4.4以上
   */
  public static void setStatusBarColor(Activity activity,int color){
    //5.0 以上
    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
      //直接调用系统提供的方法setStatusBarColor
      activity.getWindow().setStatusBarColor(color);
    }
    //4.4 - 5.0之间,采用技巧，首先把他弄成全屏，在状态栏的部分加一个布局
    else if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
      //弄成全屏，在状态栏的部分加一个布局
      //设置最外层的PhoneWindow状态，电量，时间，网络状态都还在
      activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

      //在状态栏的部分加一个布局，setContentView源码分析，自己加一个布局,高度是状态栏的高度
      View view = new View(activity);
      view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,getStatusBarHeight(activity)));
      view.setBackgroundColor(color);

      //获取Window下的DecorView，将变色部分加入
      ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
      decorView.addView(view);

      //android:fitsSystemWindows="true"每个布局都要写
      //代码实现

      //获取activity中setContentView的根布局
      ViewGroup contentView = activity.findViewById(android.R.id.content);
      //3.方法3
      //contentView.setPadding(0,getStatusBarHeight(activity),0,0);

      View activityView = contentView.getChildAt(0);
      //1.方法1
      activityView.setFitsSystemWindows(true);
      //2.方法2，设置padding
      //activityView.setPadding(0,getStatusBarHeight(activity),0,0);

    }

  }

  private static int getStatusBarHeight(Activity activity) {
    //插件式换肤，如何获取资源，先获取资源id，根据id获取资源
    Resources resources = activity.getResources();
    int statusBarHeightId = resources.getIdentifier("status_bar_height","dimen","android");
    return resources.getDimensionPixelOffset(statusBarHeightId);
  }

  public static int getStatusBarHeight(Context context){
    int resId = context.getResources().getIdentifier("status_bar_height","dimen","android");
    if(resId>0){
      //根据资源ID获取响应的尺寸值
      return context.getResources().getDimensionPixelSize(resId);
    }
    return dip2px(25,context);
  }

  private static int dip2px(int dip,Context context) {
    return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, context.getResources().getDisplayMetrics());
  }
  /**
   * 设置页面全屏
   * @param activity
   */
  public static void setActivityTranslucent(Activity activity){
    //5.0 以上
    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
      View decorView = activity.getWindow().getDecorView();
      decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
      activity.getWindow().setStatusBarColor(Color.TRANSPARENT);

    }
    else if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
      //弄成全屏
      activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }
  }
}





















