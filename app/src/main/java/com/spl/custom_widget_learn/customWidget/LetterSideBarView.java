package com.spl.custom_widget_learn.customWidget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.spl.custom_widget_learn.R;

/**
 * author: Oscar Liu
 * Date: 2021/1/16  12:39
 * File: LetterSideBarView
 * Desc:可滚动的字母表索引
 * Test:
 */
public class LetterSideBarView extends View {

  private Paint mPaint;
  private Paint mCurrentPaint;
  private int mTextSize = 20;
  private int mTextColor = Color.BLACK;
  private int mCurrentTextColor = Color.RED;
  private static String[] mLetters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
      "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#"};
  private String mCurrentTouchLetter;
  private String TAG = "LetterSideBarView";

  public LetterSideBarView(Context context) {
    this(context, null);
  }

  public LetterSideBarView(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public LetterSideBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);

    TypedArray array = context.obtainStyledAttributes(R.styleable.LetterSideBarView);
    mTextSize = array.getInt(R.styleable.LetterSideBarView_LSBTextSize, mTextSize);
    mTextColor = array.getInt(R.styleable.LetterSideBarView_LSTTextColor, mTextColor);
    array.recycle();

    mPaint = initPaint(mTextColor, mTextSize);
    mCurrentPaint = initPaint(mCurrentTextColor, mTextSize);
  }

  private Paint initPaint(int color, int textSize) {
    Paint paint = new Paint();
    paint.setAntiAlias(true);
    //自定义属性：颜色，字体大小
    paint.setTextSize(sp2px(textSize));//设置的是像素
    paint.setColor(color);
    return paint;
  }

  //字体sp转px
  private int sp2px(float sp) {
    return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    //计算宽度 = 左右padding + 字母宽度(取决于画笔)
    int width = getPaddingLeft() + getPaddingRight() + mTextSize;
    //高度matchParent,直接获取
    int height = MeasureSpec.getSize(heightMeasureSpec);
    //指定宽高
    setMeasuredDimension(width, height);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    //画26个字母和特殊字符
    int itemHeight = (getHeight() - getPaddingTop() - getPaddingBottom()) / mLetters.length;
    for (int i = 0; i < mLetters.length; i++) {
      String letter = mLetters[i];
      //計算文字的x起點
      int textWidth = (int) mPaint.measureText(letter);
      int dx = getWidth() / 2 - textWidth / 2;

      //获取每个字母的中心位置 1.字母高度的一半  2.字母高度一半+前面字符高度
      Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
      int dy = (int) ((fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom);
      //基线位置
      int baseLine = itemHeight * i + itemHeight / 2 + dy + getPaddingTop();
      canvas.drawText(letter, dx, baseLine, mPaint);
      //当前字母 高亮， 1.用两个画笔(最好)，2.改变颜色
      Paint paint = letter == mCurrentTouchLetter ? mCurrentPaint : mPaint;
      canvas.drawText(letter, dx, baseLine, paint);
    }
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    switch (event.getAction()) {
      case MotionEvent.ACTION_DOWN:
      case MotionEvent.ACTION_MOVE:
        //計算出當前觸摸字母，獲取当前位置
        float currentMoveY = event.getY();
        //位置 = currentMoveY/字母高度
        int itemHeight = (getHeight() - getPaddingTop() - getPaddingBottom()) / mLetters.length;
        int currentPosition = (int) (currentMoveY / itemHeight);

        //当触摸位置超出上下界
        if (currentPosition < 0) currentPosition = 0;
        if (currentPosition >= mLetters.length - 1) currentPosition = mLetters.length - 1;

        //优化重绘
        if (mLetters[currentPosition] == mCurrentTouchLetter) {
          return true;
        }

        mCurrentTouchLetter = mLetters[currentPosition];

        //接口回调
        if (mListener != null) {
          mListener.touch(mCurrentTouchLetter, true);
        }

        //重新绘制
        invalidate();
        break;
      case MotionEvent.ACTION_UP:
        if (mListener != null) {
          mListener.touch(mCurrentTouchLetter, false);
        }
        break;
    }
    return true;
  }

  private LetterTouchListener mListener;

  public void setOnLetterTouchListener(LetterTouchListener listener) {
    this.mListener = listener;
  }

  public interface LetterTouchListener {
    void touch(CharSequence letter, boolean isTouch);
  }


}
