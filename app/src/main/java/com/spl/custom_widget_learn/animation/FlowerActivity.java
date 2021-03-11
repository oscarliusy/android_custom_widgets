package com.spl.custom_widget_learn.animation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.spl.custom_widget_learn.R;

/**
 * 花束直播loading动画
 */
public class FlowerActivity extends AppCompatActivity {

  private FlowerLoadingView flower_load_view;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_flower);

    //1.获取后台数据
    //2.获取完设置loadingView为GONE
    flower_load_view = findViewById(R.id.flower_load_view);
    //仅仅隐藏的话，动画还在跑，需要释放内存
    flower_load_view.setVisibility(View.GONE);
  }
}