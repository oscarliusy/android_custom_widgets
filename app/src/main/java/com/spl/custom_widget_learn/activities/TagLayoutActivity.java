package com.spl.custom_widget_learn.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.spl.custom_widget_learn.R;
import com.spl.custom_widget_learn.adapters.MyBaseAdapter;
import com.spl.custom_widget_learn.customLayout.TagLayout;
import com.spl.custom_widget_learn.customWidget.TouchView;

import java.util.ArrayList;
import java.util.List;

public class TagLayoutActivity extends AppCompatActivity {

  private TagLayout tag_layout;

  private List<String> mItems;
  private TouchView touch_view;



  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_tag_layout);

    tag_layout = findViewById(R.id.tag_layout);
    touch_view = findViewById(R.id.touch_view);

    initData();

    initTouchListener();


  }

  private void initTouchListener() {
    touch_view.setOnTouchListener(new View.OnTouchListener() {
      @Override
      public boolean onTouch(View v, MotionEvent event) {
        Log.e("TAG","onTouch->" + event.getAction());
        return false;
      }
    });
    touch_view.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Log.e("TAG","onClick");
      }
    });
  }


  private void initData() {
    //标签 后台获取 很多实现方式 加List<String>的集合
    mItems = new ArrayList<>();
    mItems.add("111111");
    mItems.add("111");
    mItems.add("11111111");
    mItems.add("11");
    mItems.add("1111");
    mItems.add("11");
    mItems.add("111111111");
    mItems.add("11111111");
    mItems.add("111");
    mItems.add("111111");
    mItems.add("111");
    mItems.add("11111111");
    mItems.add("11");
    tag_layout.setAdapter(new TagLayoutAdapterMy());
  }

  private class TagLayoutAdapterMy extends MyBaseAdapter {

    @Override
    public int getCount() {
      return mItems.size();
    }

    @Override
    public View getView(int position, ViewGroup parent) {
      TextView tagTv = (TextView) LayoutInflater.from(TagLayoutActivity.this).inflate(R.layout.item_tag, parent, false);
      tagTv.setText(mItems.get(position));
      //操作ListView的方式差不多
      //还可以设置onClickListener
      tagTv.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          Toast.makeText(TagLayoutActivity.this, mItems.get(position), Toast.LENGTH_SHORT).show();
        }
      });
      return tagTv;
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }
  }
}