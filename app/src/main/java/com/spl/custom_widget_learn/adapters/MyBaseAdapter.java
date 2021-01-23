package com.spl.custom_widget_learn.adapters;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;

/**
 * author: Oscar Liu
 * Date: 2021/1/20  12:10
 * File: BaseAdapter
 * Desc:流式布局Adapter
 * Test:
 */
public abstract  class MyBaseAdapter {
  //1.有多少个条目
  public abstract int getCount();

  //2.通过position拿到view
  public abstract View getView(int position, ViewGroup parent);

  //3.观察者模式及时通知更新
  //注销观察者
  public abstract void unregisterDataSetObserver(DataSetObserver observer);

  //注册观察者
  public abstract void registerDataSetObserver(DataSetObserver observer);

}
