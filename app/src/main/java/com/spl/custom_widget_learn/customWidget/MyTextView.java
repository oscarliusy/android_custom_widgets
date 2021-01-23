package com.spl.custom_widget_learn.customWidget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.spl.custom_widget_learn.R;

/**
 * author: Oscar Liu
 * Date: 2021/1/11  16:12
 * File: MyTextView
 * Desc:
 * Test:
 */
public class MyTextView extends View {

  private String mtv_text;//需要绘制的文字
  private int mtv_text_color = Color.BLACK;//文字颜色
  private int mtv_text_size = 100;//文字大小
  private Paint paint = new Paint();
  //绘制时控制文本绘制的范围
  private Paint mPaint;
  private Rect mBound;

  /**
   * 该构造函数，会在new的时候调用
   * MyTextView mtv = new MyTextView(this)
   */
  public MyTextView(Context context) {
    //调用MyTextView(Context context, @Nullable AttributeSet attrs)
    this(context,null);
  }

  /**
   * 该构造函数，在layout布局中使用
   <com.spl.custom_widget_learn.customWidget.MyTextView
      android:text = "Hello World"
      android:layout_width="wrap_content"
      android:layout_height="wrap_content"/>
   */
  public MyTextView(Context context, @Nullable AttributeSet attrs) {
    //调用MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    this(context,attrs,0);
  }

  /**
   * 在layout布局中使用，但是会和styles.xml配套使用
   * <com.spl.custom_widget_learn.customWidget.MyTextView
   *         style="@style/DefaultMyTextView"/>
   */
  public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    Log.d("TAG","构造函数MyTextView启动");
    //从context主题中检索样式的属性信息
    //TypedArray:通过“资源索引“（index）可以得到相应的资源，
    //  第一参数:资源对应的索引，
    //  第二参数(不一定有):返回的默认值。
    TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MyTextView,defStyleAttr,0);

    mtv_text = array.getString(R.styleable.MyTextView_text);
    mtv_text_color = array.getColor(R.styleable.MyTextView_textColor, mtv_text_color);
    mtv_text_size = array.getDimensionPixelSize(R.styleable.MyTextView_textSize, mtv_text_size);
    //属性回收
    array.recycle();

    mPaint = new Paint();
    mPaint.setAntiAlias(true);//抗锯齿
    mPaint.setTextSize(mtv_text_size);
    mPaint.setColor(mtv_text_color);

    //测量文字宽高信息，使用Rect对象-mBound对宽高信息进行存储
    mBound = new Rect();
    mPaint.getTextBounds(mtv_text,0,mtv_text.length(),mBound);

  }

//  public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//    super(context, attrs, defStyleAttr, defStyleRes);
//  }

  /**
   * 自定义View的测量方法
   * @param widthMeasureSpec
   * @param heightMeasureSpec
   */
  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    Log.d("TAG","onMeasure启动");
    /**
     *     //布局的宽高都是有由这个方法指定
     *     widthMode:
     *        UPSPECIFIED:0
     *        EXACTLY:match_parent || 具体宽高  1073741824
     *        AT_MOST:wrap_content -2147483648
     */
    int widthMode = MeasureSpec.getMode(widthMeasureSpec);//-2147483648
    int heightMode = MeasureSpec.getMode(heightMeasureSpec);//-2147483648
    int widthSize = MeasureSpec.getSize(widthMeasureSpec);//1080
    int heightSize = MeasureSpec.getSize(heightMeasureSpec);//1536

    Log.d("TAG", "宽的模式:"+widthMode);
    Log.d("TAG", "高的模式:"+heightMode);
    Log.d("TAG", "宽的尺寸:"+widthSize);
    Log.d("TAG", "高的尺寸:"+heightSize);
    int width;
    int height;
    if(widthMode == MeasureSpec.EXACTLY){
      //match_parent或具体值，不需要计算，直接赋值
      width = widthSize;
    }else{
      //wrap_content
      int textWidth = mBound.width();//从保存文本宽高的Rect对象获取文本宽度
      //空间宽度 = 文本宽度 + 两侧内边距
      width = getPaddingLeft() + textWidth + getPaddingRight();
      Log.d("TAG","文本宽度："+textWidth + "控件宽度：" + width);
    }

    if(heightMode == MeasureSpec.EXACTLY){
      height = heightSize;
    }else{
      int textHeight = mBound.height();
      height = getPaddingTop() + textHeight + getPaddingBottom();
      Log.d("TAG","文本宽度："+textHeight + "控件宽度：" + height);
    }

    //设置控件的宽高
    setMeasuredDimension(width,height);
  }

  /**
   * 用于绘制
   * @param canvas
   */
  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    Log.d("TAG","启动onDraw");

    //dy：整体高度的一半到baseLine的距离
    Paint.FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();

    //计算Text的baseLine相对Text中线向下的偏移量 负-（负）得到一个正值
    int dy = (fontMetrics.top - fontMetrics.bottom)/2 - fontMetrics.top;
    //getHeight/2是View或者Rect的中线位置，加上dy偏移量，获得Text的基线位置。
    int baseLine = getHeight()/2 + dy;
    Log.d("TAG","fontMetrics.top:" + String.valueOf(fontMetrics.top));//-159 字符最高点到baseline的最大距离(与实际坐标无关，起点为baseLine)
    Log.d("TAG","fontMetrics.bottom:" + String.valueOf(fontMetrics.bottom));//41 字符最低点到baseline的最大距离(与实际坐标无关，起点为baseLine)
    Log.d("TAG","dy:" + String.valueOf(dy));//59
    Log.d("TAG","getHeight:" + String.valueOf(getHeight()));//147
    Log.d("TAG","baseLine:" + String.valueOf(baseLine));//132
    Log.d("TAG","mBound.height:"+ String.valueOf(mBound.height()));//147

    /**
     * text:文本
     * float x： x方向的起点
     * float y： y方向的基线高度
     * Paint paint：画笔
     */
    canvas.drawText(mtv_text,0,baseLine,mPaint);
    //canvas.drawText(mtv_text, getWidth() / 2 - mBound.width() / 2, getHeight() / 2 + mBound.height() / 2, mPaint);
  }

  /**
   * 处理跟用户的交互，手指触摸，按下抬起
   * @param event 事件分发，事件拦截
   * @return
   */
  @Override
  public boolean onTouchEvent(MotionEvent event) {
    switch(event.getAction()){
      case MotionEvent.ACTION_DOWN:
        //手指按下
        break;
      case MotionEvent.ACTION_UP:
        //手指抬起
        break;
      case MotionEvent.ACTION_MOVE:
        //手指移动
        break;
    }
    return super.onTouchEvent(event);
  }
}
