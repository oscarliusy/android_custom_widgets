package com.spl.custom_widget_learn.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.spl.custom_widget_learn.R;
import com.spl.custom_widget_learn.customWidget.ShapeChangeView;

public class ShapeAutoChangeActivity extends AppCompatActivity {

  private Button btn_shape_change_start;
  private ShapeChangeView shape_change_view;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_shape_auto_change);

    initView();

    btn_shape_change_start.setOnClickListener(new View.OnClickListener(){
      @Override
      public void onClick(View v) {
        Toast.makeText(ShapeAutoChangeActivity.this, "启动", Toast.LENGTH_SHORT).show();
        exchange(v);
      }
    });
  }



  private void initView() {
    btn_shape_change_start = findViewById(R.id.btn_shape_change_start);
    shape_change_view = findViewById(R.id.shape_change_view);
  }

  //定时器触发，间隔1s
  public void exchange(View view){
    new Thread(new Runnable() {
      @Override
      public void run() {
        while (true){
          runOnUiThread(new Runnable() {
            @Override
            public void run() {
              shape_change_view.exchange();
            }
          });
          try {
            Thread.sleep(1000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
    }).start();
  }
}