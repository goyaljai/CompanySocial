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
   //ArrayList<LruCache<Integer, Bitmap>> mLruCacheList;
    Context mContext;
    ArrayList<MainCategory> mMainCategory;
    ListViewAdapter listViewAdapter;



    public TabPager(FragmentManager fm, int tabCount, Context mContext, ArrayList<MainCategory> mMainCategory) {
        super(fm);
        this.tabCount = tabCount;
        this.mMainCategory = mMainCategory;
        this.mContext = mContext;
    }

    @Override
    public Fragment getItem(int position) {

        // currrently we are inflating same fragment at every position
        return new CategoryFragment(mContext,mMainCategory.get(position));

    }

    @Override
    public int getCount() {
        return tabCount;

    }
}
