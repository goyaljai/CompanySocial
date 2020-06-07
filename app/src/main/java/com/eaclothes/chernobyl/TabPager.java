package com.eaclothes.chernobyl;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.eaclothes.chernobyl.classes.MainCategory;

import java.util.ArrayList;

public class TabPager extends FragmentPagerAdapter {
    private int tabCount;
    Context mContext;

    ArrayList<MainCategory> mMainCategory;

    public TabPager(FragmentManager fm, int tabCount, Context mContext, ArrayList<MainCategory> mMainCategory) {
        super(fm);
        this.tabCount = tabCount;
        this.mMainCategory = mMainCategory;
        this.mContext = mContext;
    }

    @Override
    public Fragment getItem(int position) {
        return new CategoryFragment(mContext,mMainCategory.get(position));
    }

    @Override
    public int getCount() {
        return tabCount;

    }
}
