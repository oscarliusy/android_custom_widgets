package com.spl.custom_widget_learn.animation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.spl.custom_widget_learn.R;

/**
 * 飞舞的桃心--自定义属性动画+贝塞尔曲线+点赞动画
 */
public class LoveActivity extends AppCompatActivity {

  private LoveLayout mLoveLayout;
  private Button mAddLoveBtn;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_love);

    mLoveLayout = findViewById(R.id.love_layout);
    mAddLoveBtn = findViewById(R.id.btn_add_love);
    addLove();
  }

  public void addLove(){
    mAddLoveBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mLoveLayout.addLove();
      }
    });
  }
}