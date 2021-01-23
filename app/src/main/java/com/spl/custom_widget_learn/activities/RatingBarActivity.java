package com.spl.custom_widget_learn.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import com.spl.custom_widget_learn.R;
import com.spl.custom_widget_learn.customWidget.RatingBarView;

public class RatingBarActivity extends AppCompatActivity {

  private RatingBarView rating_bar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_rating_bar);

    LayoutInflater from = LayoutInflater.from(this);

    rating_bar = findViewById(R.id.rating_bar);
  }
}