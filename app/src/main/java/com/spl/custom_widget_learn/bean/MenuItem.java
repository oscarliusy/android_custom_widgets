package com.spl.custom_widget_learn.bean;

/**
 * author: Oscar Liu
 * Date: 2021/1/21  18:10
 * File: MenuItem
 * Desc:slideMenu的菜单项bean类
 * Test:
 */
public class MenuItem {
  private int resId;
  private String name;

  public MenuItem() {
  }

  public MenuItem(int resId, String name) {
    this.resId = resId;
    this.name = name;
  }

  public int getResId() {
    return resId;
  }

  public void setResId(int resId) {
    this.resId = resId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
