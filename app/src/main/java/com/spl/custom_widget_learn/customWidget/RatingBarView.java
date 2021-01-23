package com.spl.custom_widget_learn.customWidget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.spl.custom_widget_learn.R;

/**
 * author: Oscar Liu
 * Date: 2021/1/15  16:26
 * File: RatingBar
 * Desc:
 * Test:
 */
public class RatingBarView extends View {

  //要显示的位图
  private Bitmap mStarNormalBitmap,mStarFocusBitmap;
  //星星数量
  private int mGradeNumber = 5;
  //星星间隔
  private int mStarSpacing = 0;
  //选择的星数
  private int mCurrentGrade = 0;


  public RatingBarView(Context context) {
    this(context,null);
  }

  public RatingBarView(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs,0);
  }

  public RatingBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    //获取布局属性
    TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RatingBarView);
    mGradeNumber = array.getInt(R.styleable.RatingBarView_gradeNumber,mGradeNumber);
    float spacing = array.getDimension(R.styleable.RatingBarView_starSpacing,0);
    mStarSpacing = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,spacing,getResources().getDisplayMetrics());

    //获取图片资源id
    int starFocusId = array.getResourceId(R.styleable.RatingBarView_starFocus, 0);
    int starNormalId = array.getResourceId(R.styleable.RatingBarView_starNormal, 0);
    if(starFocusId == 0){
      throw new RuntimeException("请设置属性starFocus");
    }
    if(starNormalId == 0){
      throw new RuntimeException("请设置属性starNormal");
    }
    //根据id解析位图
    mStarFocusBitmap = BitmapFactory.decodeResource(getResources(),starFocusId);
    mStarNormalBitmap = BitmapFactory.decodeResource(getResources(),starNormalId);
    array.recycle();
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    //高度 一张图片的高度，自己实现padding
    int height = getPaddingTop() + mStarFocusBitmap.getHeight() + getPaddingBottom();
    //自己加上间隔
    int width = (mStarFocusBitmap.getWidth() + mStarSpacing) * mGradeNumber ;
      setMeasuredDimension(width,height);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    for(int i=0;i<mGradeNumber;i++){
      //i*星星宽度
      int x = i*(mStarNormalBitmap.getWidth()+mStarSpacing);
      //结合第二个步骤 触摸时mCurrentGrade是不断变化的
      if(mCurrentGrade > i){//当前分数之前,绘制选中
        canvas.drawBitmap(mStarFocusBitmap,x,getPaddingTop(),null);
      }else{//当前分数之后，绘制未选中
        canvas.drawBitmap(mStarNormalBitmap,x,getPaddingTop(),null);
      }

    }
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    // 移动 按下 抬起 处理逻辑都一样，判断手指的位置，根据当前位置计算出分数，再去刷新界面
    switch (event.getAction()){
      case MotionEvent.ACTION_DOWN://按下,被优化掉了
      case MotionEvent.ACTION_MOVE://移动
      //case MotionEvent.ACTION_UP://抬起,被优化掉了
        float moveX = event.getX();//event.getX()相当于当前控件的位置，getRowX()相对于屏幕起点的位置
        int currentGrade = (int) (moveX/(mStarNormalBitmap.getWidth()+mStarSpacing) + 1);
        //范围问题
        if(currentGrade < 0 ){
          currentGrade = 0;
        }
        if(currentGrade > mGradeNumber){
          currentGrade = mGradeNumber;
        }
        //分数相同的情况，就不要重绘了
        if(currentGrade == mCurrentGrade){
          return true;
        }
        //刷新显示
        mCurrentGrade = currentGrade;
        invalidate();
        break;
    }
    return true;//这里使用super，无法响应ACTION_MOVE
  }
}
