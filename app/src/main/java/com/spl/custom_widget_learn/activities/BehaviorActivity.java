package com.spl.custom_widget_learn.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.spl.custom_widget_learn.R;

/**
 * desc:使用Behavior的方式实现沉浸式状态栏，知乎效果
 */
public class BehaviorActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_behavior);

    Toolbar toolbar = findViewById(R.id.tool_bar);
    setSupportActionBar(toolbar);

    RecyclerView recyclerView = findViewById(R.id.recycler_view);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setAdapter(new RecyclerView.Adapter() {
      @NonNull
      @Override
      public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //最后一个参数 false，不传会报错
        View itemView = LayoutInflater.from(BehaviorActivity.this).inflate(R.layout.item_behavior,parent,false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
      }

      @Override
      public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

      }

      @Override
      public int getItemCount() {
        return 100;
      }
    });
  }

  private class ViewHolder extends RecyclerView.ViewHolder{
    public ViewHolder(@NonNull View itemView) {
      super(itemView);
    }
  }
}