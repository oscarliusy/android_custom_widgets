package com.spl.custom_widget_learn.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.spl.custom_widget_learn.R;

/**
 * desc:面试题测试页面
 */
public class TestActivity extends AppCompatActivity {

  private TextView tv_hello;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_test);

    tv_hello = findViewById(R.id.tv_hello);
    Log.e("TAG","height1->" + tv_hello.getMeasuredHeight());//height1 -> 0

    tv_hello.post(new Runnable() {//保存到队列中，啥也没干，会在dispatchAttachedToWindow中执行
      @Override
      public void run() {
        Log.e("TAG","height2->" + tv_hello.getMeasuredHeight());//height2 -> 53
      }
    });
  }

  @Override
  protected void onResume() {
    super.onResume();
    Log.e("TAG","height3->" + tv_hello.getMeasuredHeight());//height3 -> 0
  }
}