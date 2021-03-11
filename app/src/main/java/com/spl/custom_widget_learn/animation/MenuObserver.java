package com.spl.custom_widget_learn.animation;

/**
 * author: Oscar Liu
 * Date: 2021/3/9  15:29
 * File: MenuObserver
 * Desc: 观察者设计模式，用于Adapter观察ListDataScreenView的菜单开闭
 * Test:
 */
public abstract class MenuObserver {
  //可以参考一下ListView的源码
  public abstract void closeMenu();
}
