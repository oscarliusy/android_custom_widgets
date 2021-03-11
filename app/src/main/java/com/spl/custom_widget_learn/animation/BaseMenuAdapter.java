package com.spl.custom_widget_learn.animation;

import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * author: Oscar Liu
 * Date: 2021/3/8  14:10
 * File: BaseMenuAdapter
 * Desc: 筛选菜单的适配器
 * Test:
 */
public abstract class BaseMenuAdapter {

  //微信公众号，有注册订阅用户就放进集合
//  private List<MenuObserver> observers = new ArrayList<MenuObserver>();
//
//  public void registDataSetObserver(MenuObserver observer){
//    observers.add(observer);
//  }
//
//  public void unregisterDataSetObserver(MenuObserver observer){
//    observers.remove(observer);
//  }

  //单个观察者模式
  private MenuObserver mObserver;

  public void registDataSetObserver(MenuObserver observer) {
    mObserver = observer;
  }

  public void unregisterDataSetObserver(MenuObserver observer) {
    mObserver = null;
  }

  public void closeMenu() {
    if (mObserver != null) {
      mObserver.closeMenu();
    }
  }

  //获取一共有多少条
  public abstract int getCount();

  //获取当前的TabView
  public abstract View getTabView(int position, ViewGroup parent);

  //获取当前的菜单内容
  public abstract View getMenuView(int position, ViewGroup parent);

  //打开菜单
  public void menuOpen(View tabView) {
  }

  //关闭菜单
  public void menuClose(View tabView) {
  }
}
