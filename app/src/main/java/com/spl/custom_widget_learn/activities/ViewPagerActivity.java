package com.spl.custom_widget_learn.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.spl.custom_widget_learn.R;
import com.spl.custom_widget_learn.customWidget.ColorTrackTextView;
import com.spl.custom_widget_learn.fragment.ItemFragment;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerActivity extends AppCompatActivity {

  //顶部显示的数据List
  private String[] items = {"直播","推荐","视频","图片","段子","精华"};
  //顶部线性容器
  private LinearLayout mIndicatorContainer;
  //装载顶部绘图控件的List
  private List<ColorTrackTextView> mIndicators;
  //左右翻页系统控件
  private ViewPager mViewPager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_view_pager);

    mIndicators = new ArrayList<>();
    mIndicatorContainer = findViewById(R.id.indicator_view);
    mViewPager = findViewById(R.id.view_pager);
    initIndicator();

    initViewPager();
  }

  //初始化ViewPager
  private void initViewPager() {
    mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager(),FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
      @NonNull
      @Override
      public Fragment getItem(int position) {
        return ItemFragment.newInstance(items[position]);
      }

      @Override
      public int getCount() {
        return items.length;
      }

      @Override
      public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
      }
    });
    mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
      /**
       *
       * @param position:代表当前位置
       * @param positionOffset:Value from [0, 1) 代表滚动的0-1百分比.
       * @param positionOffsetPixels
       */
      @Override
      public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //Log.d("TAG","position->"+position+"   positionOffset->"+positionOffset);
        //直播->推荐 position：0-1，offset：0-1
        //1.分割线移动时，右侧变红，左侧变黑，最左全红，最右除最后一个全黑
        ColorTrackTextView left = mIndicators.get(position);
        left.setDirection(ColorTrackTextView.Direction.RIGHT_TO_LEFT);
        left.setCurrentProgress(1-positionOffset);

        //防止数据溢出
        if(position+1 < mIndicators.size()) {
          //2.分割线移动时，左侧变红，右侧变黑，最左全黑，最右除左一全红
          ColorTrackTextView right = mIndicators.get(position + 1);
          right.setDirection(ColorTrackTextView.Direction.LEFT_TO_RIGHT);
          right.setCurrentProgress(positionOffset);
        }
        //3.当二者同时生效时，存在2根分割线，2线中间区域红，其他区域黑。
      }

      @Override
      public void onPageSelected(int position) {
      }

      @Override
      public void onPageScrollStateChanged(int state) {
      }
    });
  }

  //生成顶部的LinearLayout，初始化List：mIndicators
  private void initIndicator() {
    for(int i = 0; i < items.length; i++){
      //动态添加颜色跟踪的TextView
      LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
      params.weight = 1;
      ColorTrackTextView colorTrackTextView = new ColorTrackTextView(this);
      //设置颜色
      colorTrackTextView.setTextSize(20);
      colorTrackTextView.setChangeColor(Color.RED);
      colorTrackTextView.setText(items[i]);
      colorTrackTextView.setLayoutParams(params);
      //把新的加入LinearLayout容器
      mIndicatorContainer.addView(colorTrackTextView);
      //加入集合
      mIndicators.add(colorTrackTextView);

    }
  }


}