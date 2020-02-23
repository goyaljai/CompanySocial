package com.example.chernobyl;

import android.content.Context;
import android.graphics.Bitmap;

import androidx.collection.LruCache;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.chernobyl.classes.MainCategory;

import java.util.ArrayList;

public class TabPager extends FragmentStatePagerAdapter {
    private int tabCount;
    ArrayList<LruCache<Integer, Bitmap>> mLruCacheList;
    Context mContext;
    ArrayList<MainCategory> mCategoryList;
    ListViewAdapter listViewAdapter;



    public TabPager(FragmentManager fm, int tabCount, ArrayList<LruCache<Integer, Bitmap>> lruCacheList, Context mContext, ArrayList<MainCategory> categoryList) {
        super(fm);
        this.tabCount = tabCount;
        this.mCategoryList = categoryList;
        this.mLruCacheList = lruCacheList;
        this.mContext = mContext;
    }

    @Override
    public Fragment getItem(int position) {

        // currrently we are inflating same fragment at every position
        return new CategoryFragment(mContext, mLruCacheList, mCategoryList.get(position));

    }

    @Override
    public int getCount() {
        return tabCount;

    }
}
