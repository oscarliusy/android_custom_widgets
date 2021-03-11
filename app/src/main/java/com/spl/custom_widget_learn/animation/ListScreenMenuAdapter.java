package com.spl.custom_widget_learn.animation;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.spl.custom_widget_learn.R;

/**
 * author: Oscar Liu
 * Date: 2021/3/8  14:30
 * File: ListScreenMenuAdapter
 * Desc:
 * Test:
 */
public class ListScreenMenuAdapter extends BaseMenuAdapter{

  private String[] mItems = {"类型","品牌","价格","更多"};
  private Context mContext;

  public ListScreenMenuAdapter(Context mContext) {
    this.mContext = mContext;
  }

  @Override
  public int getCount() {
    return mItems.length;
  }

  @Override
  public View getTabView(int position, ViewGroup parent) {
    TextView tabView = (TextView) LayoutInflater.from(mContext).inflate(R.layout.ui_list_data_screen_tab, parent, false);
    tabView.setText(mItems[position]);
    return tabView;
  }

  @Override
  public View getMenuView(int position, ViewGroup parent) {
    //真正的开发过程中，不同的位置显示的布局不一样，构建一个View的集合，根据position返回不同的View
    TextView menuView = (TextView) LayoutInflater.from(mContext).inflate(R.layout.ui_list_data_screen_menu, parent, false);
    menuView.setText(mItems[position]);
    menuView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        //关闭菜单,通过父类中mObserver调用ListDataScreenView的closeMenu方法
        closeMenu();
      }
    });
    return menuView;
  }

  @Override
  public void menuClose(View tabView) {
    TextView tabTv = (TextView) tabView;
    tabTv.setTextColor(Color.BLACK);

  }

  @Override
  public void menuOpen(View tabView) {
    TextView tabTv = (TextView) tabView;
    tabTv.setTextColor(Color.RED);
  }
}
