package com.spl.custom_widget_learn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.spl.custom_widget_learn.activities.ColorTrackTextActivity;
import com.spl.custom_widget_learn.activities.LetterSideBarActivity;
import com.spl.custom_widget_learn.activities.MyTextViewActivity;
import com.spl.custom_widget_learn.activities.QQStepActivity;
import com.spl.custom_widget_learn.activities.RatingBarActivity;
import com.spl.custom_widget_learn.activities.RoundBarActivity;
import com.spl.custom_widget_learn.activities.ShapeAutoChangeActivity;
import com.spl.custom_widget_learn.activities.SlideMenu1Activity;
import com.spl.custom_widget_learn.activities.SlideMenu2Activity;
import com.spl.custom_widget_learn.activities.TagLayoutActivity;
import com.spl.custom_widget_learn.activities.ViewPagerActivity;
import com.spl.custom_widget_learn.customLayout.VerticalOffsetLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


  private Button btn_main_mtv,btn_main_qq,btn_main_color_text,
      btn_main_view_pager,btn_main_round_bar,btn_main_shape_change,
      btn_main_rating_bar,btn_main_letter_sidebar,btn_main_offset_layout,
      btn_main_tagLayout,btn_main_slideMenu1,btn_main_slideMenu2;



  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    initView();
  }

  private void initView() {
    btn_main_mtv = findViewById(R.id.btn_main_mtv);
    btn_main_qq = findViewById(R.id.btn_main_qq);
    btn_main_color_text = findViewById(R.id.btn_main_color_text);
    btn_main_view_pager = findViewById(R.id.btn_main_view_pager);
    btn_main_round_bar = findViewById(R.id.btn_main_round_bar);
    btn_main_shape_change = findViewById(R.id.btn_main_shape_change);
    btn_main_rating_bar = findViewById(R.id.btn_main_rating_bar);
    btn_main_letter_sidebar = findViewById(R.id.btn_main_letter_sidebar);
    btn_main_offset_layout = findViewById(R.id.btn_main_offset_layout);
    btn_main_tagLayout = findViewById(R.id.btn_main_tagLayout);
    btn_main_slideMenu1 = findViewById(R.id.btn_main_slideMenu1);
    btn_main_slideMenu2 = findViewById(R.id.btn_main_slideMenu2);

    btn_main_mtv.setOnClickListener(this);
    btn_main_qq.setOnClickListener(this);
    btn_main_color_text.setOnClickListener(this);
    btn_main_view_pager.setOnClickListener(this);
    btn_main_round_bar.setOnClickListener(this);
    btn_main_shape_change.setOnClickListener(this);
    btn_main_rating_bar.setOnClickListener(this);
    btn_main_letter_sidebar.setOnClickListener(this);
    btn_main_offset_layout.setOnClickListener(this);
    btn_main_tagLayout.setOnClickListener(this);
    btn_main_slideMenu1.setOnClickListener(this);
    btn_main_slideMenu2.setOnClickListener(this);

  }

  @Override
  public void onClick(View v) {
    Intent intent = null;
    switch (v.getId()){
      case R.id.btn_main_mtv:
        intent = new Intent(MainActivity.this, MyTextViewActivity.class);
        startActivity(intent);
        break;
      case R.id.btn_main_qq:
        intent = new Intent(MainActivity.this, QQStepActivity.class);
        startActivity(intent);
        break;
      case R.id.btn_main_color_text:
        intent = new Intent(MainActivity.this, ColorTrackTextActivity.class);
        startActivity(intent);
        break;
      case R.id.btn_main_view_pager:
        intent = new Intent(MainActivity.this, ViewPagerActivity.class);
        startActivity(intent);
        break;
      case R.id.btn_main_round_bar:
        intent = new Intent(MainActivity.this, RoundBarActivity.class);
        startActivity(intent);
        break;
      case R.id.btn_main_shape_change:
        intent = new Intent(MainActivity.this, ShapeAutoChangeActivity.class);
        startActivity(intent);
        break;
      case R.id.btn_main_rating_bar:
        intent = new Intent(MainActivity.this, RatingBarActivity.class);
        startActivity(intent);
        break;
      case R.id.btn_main_letter_sidebar:
        intent = new Intent(MainActivity.this, LetterSideBarActivity.class);
        startActivity(intent);
        break;
      case R.id.btn_main_offset_layout:
        intent = new Intent(MainActivity.this, VerticalOffsetLayout.class);
        startActivity(intent);
        break;
      case R.id.btn_main_tagLayout:
        intent = new Intent(MainActivity.this, TagLayoutActivity.class);
        startActivity(intent);
        break;
      case R.id.btn_main_slideMenu1:
        intent = new Intent(MainActivity.this, SlideMenu1Activity.class);
        startActivity(intent);
        break;
      case R.id.btn_main_slideMenu2:
        intent = new Intent(MainActivity.this, SlideMenu2Activity.class);
        startActivity(intent);
        break;
      default:
        break;
    }
  }

  @Override
  protected void onStart() {
    super.onStart();
  }

  @Override
  protected void onResume() {
    super.onResume();
  }
}