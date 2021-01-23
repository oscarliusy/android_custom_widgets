package com.spl.custom_widget_learn.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.animation.DecelerateInterpolator;

import com.spl.custom_widget_learn.R;
import com.spl.custom_widget_learn.customWidget.QQStepView;

public class QQStepActivity extends Activity {

  private QQStepView qqStepView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_q_q_step);

    initView();

    initAnimation();
  }

  private void initView() {
    qqStepView = findViewById(R.id.step_view);
    qqStepView.setStepMax(4000);
  }

  private void initAnimation() {
    //值起始和终止范围
    ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, 3000);
    //动画持续时间
    valueAnimator.setDuration(1000);
    //插值函数，使变化速率先快后慢
    valueAnimator.setInterpolator(new DecelerateInterpolator());
    //动画监听器
    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override
      public void onAnimationUpdate(ValueAnimator animation) {
        float currentStep = (float) animation.getAnimatedValue();
        qqStepView.setCurrentStep((int)currentStep);
      }
    });
    valueAnimator.start();
  }
}