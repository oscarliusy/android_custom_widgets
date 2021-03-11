package com.spl.custom_widget_learn.animation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.spl.custom_widget_learn.R;

/**
 * desc:实现58同城多条目数据筛选效果
 */
public class ListDataScreenActivity extends AppCompatActivity {

  private ListDataScreenView mListDataScreenView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_list_data_screen);

    mListDataScreenView = findViewById(R.id.list_data_screen_view);
    mListDataScreenView.setAdapter(new ListScreenMenuAdapter(this));

  }
}