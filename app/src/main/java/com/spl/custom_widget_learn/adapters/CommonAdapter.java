package com.spl.custom_widget_learn.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * author: Oscar Liu
 * Date: 2021/2/22  10:58
 * File: CommonAdapter
 * Desc:
 * Test:
 */
public abstract  class CommonAdapter<T> extends RecyclerView.Adapter<ViewHolder> {

  private Context mContext;
  private List<T> mData;
  private LayoutInflater mInflater;
  private int mLayoutId;

  public CommonAdapter(Context mContext, List<T> mData, int mLayoutId) {
    this.mContext = mContext;
    this.mData = mData;
    this.mLayoutId = mLayoutId;
    this.mInflater = LayoutInflater.from(mContext);
  }

  /**
   * 返回加载view的holder
   * @param parent
   * @param viewType
   * @return
   */
  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View itemView = mInflater.inflate(mLayoutId,parent,false);
    ViewHolder holder = new ViewHolder(itemView);
    return holder;
  }

  /**
   * 向holder中绑定数据
   * @param holder
   * @param position
   */
  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    convert(holder,mData.get(position));
  }

  @Override
  public int getItemCount() {
    return mData.size();
  }

  public abstract void convert(ViewHolder holder,T item);
}
