package com.spl.custom_widget_learn.md_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.spl.custom_widget_learn.R;
import com.spl.custom_widget_learn.adapters.MyBehaviorAdapter;
import com.spl.custom_widget_learn.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

public class MyBehaviorActivity extends AppCompatActivity {

  private static RecyclerView recyclerview;
  private CoordinatorLayout coordinatorLayout;
  private LinearLayoutManager mLayoutManager;
  private List<String> mData;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    StatusBarUtil.setActivityTranslucent(this);
    setContentView(R.layout.activity_my_behavior);

    initData();

    initView();
  }

  private void initView() {
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    coordinatorLayout=(CoordinatorLayout)findViewById(R.id.behavior_demo_coordinatorLayout);
    recyclerview=(RecyclerView)findViewById(R.id.behavior_demo_recycler);
    mLayoutManager = new LinearLayoutManager(this);
    recyclerview.setLayoutManager(mLayoutManager);
    recyclerview.setAdapter(new MyBehaviorAdapter(this,mData,R.layout.item_lv));

   // SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.behavior_demo_swipe_refresh);
    FloatingActionButton fab = findViewById(R.id.fab);

  }

  private void initData() {
    mData = new ArrayList<>();
    for(int j=0;j<10;j++){
      for(int i='A';i<'Z';i++){
        mData.add(""+(char)i);
      }
    }
  }
}