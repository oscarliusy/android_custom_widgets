package com.spl.custom_widget_learn.bean;

/**
 * author: Oscar Liu
 * Date: 2021/1/21  18:12
 * File: ContentItem
 * Desc:sliding menu的context item的bean类
 * Test:
 */
public class ContentItem {
  private int resId;
  private String name;
  public ContentItem(){

  }

  public ContentItem(int resId, String name) {
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
