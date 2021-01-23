package com.spl.custom_widget_learn.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.spl.custom_widget_learn.R;
import com.spl.custom_widget_learn.bean.ContentItem;

import java.util.List;

/**
 * author: Oscar Liu
 * Date: 2021/1/21  18:51
 * File: ContentAdapter
 * Desc:sliding menu的content listview adapter
 * Test:
 */
public class ContentAdapter extends BaseAdapter {
  private Context mContext;
  private List<ContentItem> mDatas;
  private LayoutInflater mInflater;
  public ContentAdapter(Context context, List<ContentItem> datas){
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
    if (convertView == null){
      convertView = mInflater.inflate(R.layout.content_item,parent,false);
      viewHolder = new ViewHolder();
      viewHolder.mImageView = convertView.findViewById(R.id.content_imageview);
      viewHolder.mTextView =  convertView.findViewById(R.id.content_textview);
      convertView.setTag(viewHolder);
    }else{
      viewHolder = (ViewHolder) convertView.getTag();
    }
    ContentItem contentItem = mDatas.get(position);
    viewHolder.mImageView.setImageResource(contentItem.getResId());
    viewHolder.mTextView.setText(contentItem.getName());
    return convertView;
  }
  private static class ViewHolder{
    ImageView mImageView;
    TextView mTextView;
  }
}
