package com.spl.custom_widget_learn.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.spl.custom_widget_learn.R;

/**
 * author: Oscar Liu
 * Date: 2021/2/20  10:18
 * File: StatusBarUtil
 * Desc: 仿QQ6.0好友动态，沉浸式状态栏效果,顶部TitleBar的透明度随ScrollView滚动变化
 * Test:
 */
public class ImmersiveStatusBarActivity extends AppCompatActivity {
  private View mTitleBar;
  private ScrollView mScrollView;
  private ImageView mImageView;
  private int mImageViewHeight;
  private int mTitleBarHeight;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_immersive_status_bar);

    //QQ 效果 1.不断监听ScrollView的滚动，判断当前滚动的位置跟头部的ImageView做比较计算透明度等
    //2.自定义Behavior，简书有文章

    //1.刚进来背景完全透明
    mTitleBar = findViewById(R.id.title_bar);
    //这里设置不会导致内部文本变透明
    mTitleBar.getBackground().setAlpha(0);

    mImageView = findViewById(R.id.image_view);
    //根据布局绘制流程，无法直接获取高度，需要异步操作,会在页面绘制后测量
    mImageView.post(new Runnable() {
      @Override
      public void run() {
        mImageViewHeight = mImageView.getMeasuredHeight();
        mTitleBarHeight = mTitleBar.getMeasuredHeight();
      }
    });

    //不断监听滚动
    mScrollView = findViewById(R.id.scroll_view);
    mScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener(){
      @Override
      public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        //获取图片的高度，根据当前滚动的位置，计算alpha值
        if(mImageViewHeight == 0) return;

        //mImageViewHeight - titleBar的高度
        float alpha = (float)scrollY/(mImageViewHeight-mTitleBarHeight);

        if(alpha<=0){
          alpha=0;
        }

        if(alpha > 1){
          alpha = 1;
        }
        mTitleBar.getBackground().setAlpha((int)(alpha*255));

      }
    });
  }
}