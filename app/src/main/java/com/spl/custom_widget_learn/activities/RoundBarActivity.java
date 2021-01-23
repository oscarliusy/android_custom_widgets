package com.spl.custom_widget_learn.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;

import com.spl.custom_widget_learn.R;
import com.spl.custom_widget_learn.customWidget.RoundBarView;

public class RoundBarActivity extends AppCompatActivity {

  private RoundBarView round_bar_view;
  private Button btn_round_bar_start;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_round_bar);

    round_bar_view = findViewById(R.id.round_bar_view);
    btn_round_bar_start = findViewById(R.id.btn_round_bar_start);

    btn_round_bar_start.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        initAnimation();
      }
    });


  }

  private void initAnimation() {
    ValueAnimator valueAnimator = ObjectAnimator.ofInt(0, 100);
    valueAnimator.setDuration(2000);
    valueAnimator.setInterpolator(new DecelerateInterpolator());
    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override
      public void onAnimationUpdate(ValueAnimator animation) {
        int currentEnd = (int) animation.getAnimatedValue();
        round_bar_view.setEnd(currentEnd);
      }
    });
    valueAnimator.start();
  }
}