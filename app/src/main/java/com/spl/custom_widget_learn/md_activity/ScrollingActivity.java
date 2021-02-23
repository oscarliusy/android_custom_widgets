package com.spl.custom_widget_learn.md_activity;

import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.spl.custom_widget_learn.R;

/**
 * desc:改造ScrollActivity,使图片可以跟随AppBarLayout的滚动改变透明度
 */
public class ScrollingActivity extends AppCompatActivity {

  private Toolbar toolbar;
  private AppBarLayout appBar;
  private ImageView imageView,bulb;
  private float totalHeight;      //总高度
  private float toolBarHeight;    //toolBar高度
  private float offSetHeight;     //总高度 -  toolBar高度  布局位移值

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_scrolling);

    getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//设置顶部statusBar半透明
    CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
    toolBarLayout.setTitle("我是标题");
    toolBarLayout.setStatusBarScrimResource(R.drawable.map);//设置折叠后statusBar的背景
    toolBarLayout.setCollapsedTitleTextColor(Color.RED);//折叠后title的颜色
    toolBarLayout.setExpandedTitleColor(Color.BLUE);//展开时title的颜色
    toolBarLayout.setContentScrimColor(Color.GREEN);//折叠后toolbar的背景色

    toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    toolbar.setLogo(R.mipmap.ic_launcher);//有效
    toolbar.setNavigationIcon(R.drawable.icon_bulb_64);//有效，可以绑定点击事件

    toolbar.setTitle("新标题");//无效
    toolbar.setSubtitle("副标题");//无效
    toolbar.setBackgroundColor(Color.TRANSPARENT);//无效

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show();
      }
    });
    appBar = findViewById(R.id.app_bar);
    imageView = findViewById(R.id.image);
    bulb = findViewById(R.id.iv_bulb);

    //异步测量，在onCreate中无法获取高度
    appBar.post(new Runnable() {
      @Override
      public void run() {
        totalHeight = appBar.getMeasuredHeight();
        toolBarHeight = toolbar.getMeasuredHeight();
        offSetHeight = totalHeight - toolBarHeight;
      }
    });

    appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
      @Override
      public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        //设置图片透明度变化
        float alphaScale = (-verticalOffset)/(2*offSetHeight);
        //Log.d("TAG","alpha->"+alphaScale);
        imageView.setAlpha(1.0f - alphaScale);
        //实现图片的跟随滚动，设置一个阈值，到了与icon水平就不再滚动，而是消失
        float bulbY = (float) (verticalOffset*0.1);
        if(verticalOffset > -400){
          bulb.setTranslationY(bulbY);
        }
//        Log.d("TAG","verticalOffset->"+verticalOffset+" bulbY->"+bulbY);
        /**
         * 向上划，存在
         * verticalOffset->0 bulbY->0.0
         * verticalOffset->-415 bulbY->-41.5
         * 滑到这个值，v和b不再改变，同时b消失
         * verticalOffset->-1050 bulbY->-105.0
         */
        //修改toolbar透明度
        //toolbar.setBackgroundColor(changeAlpha(getResources().getColor(R.color.purple_200),Math.abs(verticalOffset*1.0f)/appBar.getTotalScrollRange()));

      }

    });

    /**
     * 设置左上角icon的点击事件
     */
    toolbar.setNavigationOnClickListener(new View.OnClickListener(){
      @Override
      public void onClick(View v) {
        Toast.makeText(ScrollingActivity.this, "nav icon", Toast.LENGTH_SHORT).show();
      }
    });
  }

  /** 根据百分比改变颜色透明度 */
  public int changeAlpha(int color, float fraction) {
    int red = Color.red(color);
    int green = Color.green(color);
    int blue = Color.blue(color);
    int alpha = (int) (Color.alpha(color) * fraction);
    return Color.argb(alpha, red, green, blue);
  }

}