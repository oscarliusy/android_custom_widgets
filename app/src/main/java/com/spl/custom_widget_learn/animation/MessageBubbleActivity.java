package com.spl.custom_widget_learn.animation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.spl.custom_widget_learn.R;
import com.spl.custom_widget_learn.utils.StatusBarUtil;

/**
 * desc: 实现拖拽View+贝塞尔曲线+爆炸消失
 */
public class MessageBubbleActivity extends AppCompatActivity implements BubbleMessageTouchListener.BubbleDisappearListener {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    StatusBarUtil.setActivityTranslucent(MessageBubbleActivity.this);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_message_bubble);

    TextView textView = findViewById(R.id.text_view);
    MessageBubbleView.attach(textView,this);
  }

  @Override
  public void dismiss(View view) {
    Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show();
  }
}