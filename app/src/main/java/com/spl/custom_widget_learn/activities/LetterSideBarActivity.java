package com.spl.custom_widget_learn.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.spl.custom_widget_learn.R;
import com.spl.custom_widget_learn.customWidget.LetterSideBarView;

public class LetterSideBarActivity extends AppCompatActivity implements LetterSideBarView.LetterTouchListener {

  private TextView letter_tv;
  private LetterSideBarView letter_side_bar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_letter_side_bar);

    letter_tv = findViewById(R.id.letter_tv);
    letter_side_bar = findViewById(R.id.letter_side_bar);
    letter_side_bar.setOnLetterTouchListener(this);
  }

  //通过LetterSideBar的回调接口，获取选择的字母
  @Override
  public void touch(CharSequence letter, boolean isTouch) {
    if(isTouch){
      letter_tv.setVisibility(View.VISIBLE);
      letter_tv.setText(letter);
    }else{
      letter_tv.setVisibility(View.GONE);
    }
  }




}