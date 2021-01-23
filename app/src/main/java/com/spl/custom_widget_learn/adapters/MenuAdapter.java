package com.spl.custom_widget_learn.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import com.spl.custom_widget_learn.bean.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.spl.custom_widget_learn.R;

import java.util.List;

/**
 * author: Oscar Liu
 * Date: 2021/1/21  18:25
 * File: MenuAdapter
 * Desc:slidingMenu的menu listView 适配器
 * Test:
 */
public class MenuAdapter extends BaseAdapter {

  private Context mContext;
  private List<MenuItem> mDatas;
  private LayoutInflater mInflater;

  public MenuAdapter(Context context,List<MenuItem> datas){
    mContext = context;
    mInflater = LayoutInflater.from(context);
    mDatas = datas;
  }

  @Override
  public int getCount() {
    return mDatas.size();
  }

  @Override
  public Object getItem(int position) {
    return mDatas.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    ViewHolder viewHolder = null;
    if(convertView == null){
      convertView = mInflater.inflate(R.layout.left_menu_item,parent,false);
      viewHolder = new ViewHolder();
      viewHolder.mImageView = convertView.findViewById(R.id.menu_imageview);
      viewHolder.mTextView = convertView.findViewById(R.id.menu_textview);
      convertView.setTag(viewHolder);
    }else{
      viewHolder = (ViewHolder) convertView.getTag();
    }
    MenuItem menuItem = mDatas.get(position);
    viewHolder.mImageView.setImageResource(menuItem.getResId());
    viewHolder.mTextView.setText(menuItem.getName());
    return convertView;

  }

  private static class ViewHolder{
    ImageView mImageView;
    TextView mTextView;
  }
}
