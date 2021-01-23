package com.spl.custom_widget_learn.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.spl.custom_widget_learn.R;
import com.spl.custom_widget_learn.customWidget.ColorTrackTextView;

public class ColorTrackTextActivity extends Activity {

  private ColorTrackTextView mColorTrackTextView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_color_track_text);

    mColorTrackTextView = findViewById(R.id.color_track_text);
  }

  public void leftToRight(View view){
    //设定变化方向
    mColorTrackTextView.setDirection(ColorTrackTextView.Direction.LEFT_TO_RIGHT);
    //设定取值范围
    ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, 1);
    valueAnimator.setDuration(2000);
    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override
      public void onAnimationUpdate(ValueAnimator animation) {
        Float currentProgress = (Float) animation.getAnimatedValue();
        mColorTrackTextView.setCurrentProgress(currentProgress);
      }
    });
    valueAnimator.start();
  }

  public void rightToLeft(View view){
    mColorTrackTextView.setDirection(ColorTrackTextView.Direction.RIGHT_TO_LEFT);
    ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, 1);
    valueAnimator.setDuration(2000);
    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override
      public void onAnimationUpdate(ValueAnimator animation) {
        Float currentProgress = (Float) animation.getAnimatedValue();
        mColorTrackTextView.setCurrentProgress(currentProgress);
      }
    });
    valueAnimator.start();
  }

}