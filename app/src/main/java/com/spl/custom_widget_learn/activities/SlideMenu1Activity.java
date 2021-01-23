package com.spl.custom_widget_learn.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.spl.custom_widget_learn.R;
import com.spl.custom_widget_learn.adapters.ContentAdapter;
import com.spl.custom_widget_learn.adapters.MenuAdapter;
import com.spl.custom_widget_learn.bean.ContentItem;
import com.spl.custom_widget_learn.bean.MenuItem;
import com.spl.custom_widget_learn.customLayout.MySlidingMenu1;

import java.util.ArrayList;
import java.util.List;

public class SlideMenu1Activity extends AppCompatActivity {

  private ViewGroup mMenu;
  private ViewGroup mContent;
  private MySlidingMenu1 mySlidingMenu;
  private ListView mMenuListView;
  private List<MenuItem> mMenuDatas;
  private List<ContentItem> mContentDatas;
  private MenuAdapter mMenuAdapter;
  private ListView mContentListView;
  private ImageView mMenuToggle;
  private ContentAdapter mContentAdapter;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.activity_slide_menu1);

    mySlidingMenu = findViewById(R.id.slidingmenu);
    mMenu = findViewById(R.id.menu);
    mContent = findViewById(R.id.content);

    mMenuListView = mMenu.findViewById(R.id.menu_listview);
    initMenuData();
    mMenuAdapter = new MenuAdapter(getApplicationContext(),mMenuDatas);
    mMenuListView.setAdapter(mMenuAdapter);
    mMenuListView.setOverScrollMode(View.OVER_SCROLL_NEVER);
    mMenuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(SlideMenu1Activity.this, "Click Menu->" + (position+1), Toast.LENGTH_SHORT).show();
      }
    });

    mContentListView = findViewById(R.id.content_listview);
    mMenuToggle = findViewById(R.id.menu_toggle);
    initContentData();
    mContentAdapter = new ContentAdapter(getApplicationContext(),mContentDatas);
    mContentListView.setAdapter(mContentAdapter);
    mContentListView.setOverScrollMode(View.OVER_SCROLL_NEVER);
    mContentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(SlideMenu1Activity.this, "Click Content->"+ (position+1), Toast.LENGTH_SHORT).show();
      }
    });

    mMenuToggle.setOnClickListener(new View.OnClickListener(){
      @Override
      public void onClick(View v) {
        mySlidingMenu.toggleMenu();
        Toast.makeText(SlideMenu1Activity.this, "toggleMenu", Toast.LENGTH_SHORT).show();
      }
    });
  }

  private void initContentData() {
    mContentDatas = new ArrayList<>();
    mContentDatas.add(new ContentItem(R.drawable.content_1 , "Content - 1"));
    mContentDatas.add(new ContentItem(R.drawable.content_2 , "Content - 2"));
    mContentDatas.add(new ContentItem(R.drawable.content_3 , "Content - 3"));
    mContentDatas.add(new ContentItem(R.drawable.content_4 , "Content - 4"));
    mContentDatas.add(new ContentItem(R.drawable.content_5 , "Content - 5"));
    mContentDatas.add(new ContentItem(R.drawable.content_6 , "Content - 6"));
    mContentDatas.add(new ContentItem(R.drawable.content_7 , "Content - 7"));
    mContentDatas.add(new ContentItem(R.drawable.content_8 , "Content - 8"));
    mContentDatas.add(new ContentItem(R.drawable.content_9 , "Content - 9"));
    mContentDatas.add(new ContentItem(R.drawable.content_10 , "Content - 10"));
    mContentDatas.add(new ContentItem(R.drawable.content_11 , "Content - 11"));
    mContentDatas.add(new ContentItem(R.drawable.content_12 , "Content - 12"));
    mContentDatas.add(new ContentItem(R.drawable.content_13 , "Content - 13"));
  }

  private void initMenuData() {
    mMenuDatas = new ArrayList<>();
    mMenuDatas.add(new MenuItem(R.drawable.menu_1, "菜单1"));
    mMenuDatas.add(new MenuItem(R.drawable.menu_2, "菜单2"));
    mMenuDatas.add(new MenuItem(R.drawable.menu_3, "菜单3"));
    mMenuDatas.add(new MenuItem(R.drawable.menu_4, "菜单4"));
    mMenuDatas.add(new MenuItem(R.drawable.menu_5, "菜单5"));
    mMenuDatas.add(new MenuItem(R.drawable.menu_6, "菜单6"));
    mMenuDatas.add(new MenuItem(R.drawable.menu_7, "菜单7"));
    mMenuDatas.add(new MenuItem(R.drawable.menu_8, "菜单8"));
    mMenuDatas.add(new MenuItem(R.drawable.menu_9, "菜单9"));
    mMenuDatas.add(new MenuItem(R.drawable.menu_10, "菜单10"));
  }

}