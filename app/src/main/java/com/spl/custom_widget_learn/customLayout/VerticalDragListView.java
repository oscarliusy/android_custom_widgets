package com.spl.custom_widget_learn.customLayout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * author: Oscar Liu
 * Date: 2021/1/23  11:53
 * File: VerticalDragListView
 * Desc: 垂直方向抽屉
 * Test:
 */
public class VerticalDragListView extends FrameLayout {
  public VerticalDragListView(@NonNull Context context) {
    super(context);
  }

  public VerticalDragListView(@NonNull Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public VerticalDragListView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }
}
