package com.spl.custom_widget_learn.animation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.spl.custom_widget_learn.R;

/**
 * 仿58同城loading效果的属性动画
 */
public class AutoShape58Activity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_auto_shape58);

  }

  public void click(View v){
    v.setVisibility(View.GONE);
  }

  public void start(View v){
    Intent intent = new Intent(this,AutoShape58Activity.class);
    startActivity(intent);
  }
}