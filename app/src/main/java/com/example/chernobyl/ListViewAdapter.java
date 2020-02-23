package com.example.chernobyl;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.collection.LruCache;
import androidx.viewpager.widget.ViewPager;

import com.example.chernobyl.classes.MainCategory;
import com.example.chernobyl.classes.SubCategory;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {
    private MainCategory mMainCategory;
    private Context mContext;
    //private LruCache<Integer, Bitmap> mLruCache;
    private TextView mCategoryName;
    private ArrayList<LruCache<Integer, Bitmap>> mLruCacheList;

    public ListViewAdapter(Context context, MainCategory mainCategory, ArrayList<LruCache<Integer, Bitmap>> mLruCacheList) {
      this.mLruCacheList=mLruCacheList;
        mMainCategory = mainCategory;
        mContext = context;
    }


    @Override
    public int getCount() {
        return mMainCategory.mCategories.size();
    }

    @Override
    public SubCategory getItem(int position) {
        return mMainCategory.mCategories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.pager, null, false);
        }
        mCategoryName = convertView.findViewById(R.id.category_name);
        mCategoryName.setText(mMainCategory.mCategories.get(position).name);
        CategoryAdapter categoryAdapter = new CategoryAdapter(mContext, mMainCategory.mCategories.get(position));
        ViewPager page = convertView.findViewById(R.id.pager);
        Log.d("ankit", mLruCacheList.size() + " position  " + position);
        categoryAdapter.setBmpCache(mLruCacheList.get(position));
        page.setAdapter(categoryAdapter);
        return convertView;
    }
}
