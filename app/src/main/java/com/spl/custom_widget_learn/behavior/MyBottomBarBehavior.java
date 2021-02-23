package com.spl.custom_widget_learn.behavior;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.appbar.AppBarLayout;

/**
 * author: Oscar Liu
 * Date: 2021/2/22  10:38
 * File: MyBottomBarBehavior
 * Desc:
 * Test:
 */
public class MyBottomBarBehavior extends CoordinatorLayout.Behavior<View>{
  public MyBottomBarBehavior(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
    return dependency instanceof AppBarLayout;
  }

  @Override
  public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
    float translationY = Math.abs(dependency.getTop());
    child.setTranslationY(translationY);
    return true;
  }
}
