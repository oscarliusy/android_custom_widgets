package com.spl.custom_widget_learn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.spl.custom_widget_learn.activities.LockPatternActivity;
import com.spl.custom_widget_learn.activities.MyTextViewActivity;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener{

  private Button btn_main2_lockpattern;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main2);
    initView();
  }

  private void initView() {
    btn_main2_lockpattern = findViewById(R.id.btn_main2_lockpattern);
    btn_main2_lockpattern.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    Intent intent = null;
    switch (v.getId()) {
      case R.id.btn_main_mtv:
        intent = new Intent(MainActivity2.this, LockPatternActivity.class);
        startActivity(intent);
        break;
    }
  }
}