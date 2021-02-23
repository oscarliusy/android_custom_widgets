package com.spl.custom_widget_learn.md_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.spl.custom_widget_learn.R;

public class CoordinateLayoutActivity extends AppCompatActivity {

  private FloatingActionButton mFab;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_coordinate_layout);

    initFab();

    initToolbar();

    initBtn();
  }

  private void initBtn() {
    findViewById(R.id.my_behavior_bt).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent=new Intent(CoordinateLayoutActivity.this,MyBehaviorActivity.class);
        startActivity(intent);
      }
    });
  }

  private void initToolbar() {
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
  }

  private void initFab() {
    mFab = findViewById(R.id.fab);
    mFab.setOnClickListener(new View.OnClickListener(){
      @Override
      public void onClick(View v) {
        Snackbar.make(v,"FAB",Snackbar.LENGTH_LONG)
            .setAction("cancel",new View.OnClickListener(){
              @Override
              public void onClick(View v) {
                Toast.makeText(CoordinateLayoutActivity.this, "cancel", Toast.LENGTH_SHORT).show();
              }
            }).show();
      }
    });
  }
}