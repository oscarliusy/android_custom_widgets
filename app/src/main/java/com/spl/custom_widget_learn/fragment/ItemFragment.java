package com.spl.custom_widget_learn.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.spl.custom_widget_learn.R;

/**
 * author: Oscar Liu
 * Date: 2021/1/14  13:25
 * File: ItemFragment
 * Desc:
 * Test:
 */
public class ItemFragment extends Fragment {
  public static ItemFragment newInstance(String item){
    ItemFragment itemFragment = new ItemFragment();
    Bundle bundle = new Bundle();
    bundle.putString("title",item);
    itemFragment.setArguments(bundle);
    return itemFragment;
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_item,null);
    TextView tv = view.findViewById(R.id.text);
    Bundle bundle = getArguments();
    tv.setText(bundle.getString("title"));
    return view;
  }
}
