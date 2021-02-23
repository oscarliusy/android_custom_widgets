package com.spl.custom_widget_learn.adapters;

import android.content.Context;
import android.widget.TextView;

import com.spl.custom_widget_learn.R;

import java.util.List;

/**
 * author: Oscar Liu
 * Date: 2021/2/22  11:10
 * File: MyBehaviorAdapter
 * Desc:
 * Test:
 */
public class MyBehaviorAdapter extends CommonAdapter<String>{
  public MyBehaviorAdapter(Context mContext, List<String> mData, int mLayoutId) {
    super(mContext, mData, mLayoutId);
  }

  @Override
  public void convert(ViewHolder holder, String item) {
    TextView textView = holder.itemView.findViewById(R.id.item_tv);
    textView.setText(item);
  }
}
