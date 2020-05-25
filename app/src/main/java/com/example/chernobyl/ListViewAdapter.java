package com.example.chernobyl;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.chernobyl.classes.MainCategory;

public class ListViewAdapter extends BaseAdapter {
    private MainCategory mMainCategory;
    private Context mContext;
    private TextView mCategoryName;

    public ListViewAdapter(Context context, MainCategory mainCategory) {
        //this.mLruCacheList=mLruCacheList;
        mMainCategory = mainCategory;
        mContext = context;
    }


    @Override
    public int getCount() {
        return mMainCategory.getMainUISubCategories().size();
    }

    @Override
    public MainSubCategory getItem(int position) {
        return mMainCategory.getMainUISubCategories().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.pager, null, false);
        }
        mCategoryName = convertView.findViewById(R.id.category_name);
        LinearLayout clicker = convertView.findViewById(R.id.category_name_clicker);
        clicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(mContext,MainCategoryExpandedActivity.class);
                intent.putExtra("data",getItem(position));
                mContext.startActivity(intent);
            }
        });
        mCategoryName.setText(mMainCategory.getMainUISubCategories().get(position).description);
        CategoryAdapter categoryAdapter = new CategoryAdapter(mContext, mMainCategory.getMainUISubCategories().get(position).subCategoryArrayList);
        ViewPager page = convertView.findViewById(R.id.pager);
        page.setAdapter(categoryAdapter);

        //To remove flickering
        page.setOnTouchListener((v, event) -> {
            //page.requestDisallowInterceptTouchEvent(true); // not sure if this is required
            //PagerAdapter adapter = page.getAdapter();
            // consume the move event if we have only one page full - removes flickering artifact
            // getNumberOfPagesOnScreen() is a mehtod we have to get the number of pages we are going to display. ymmv
            if (categoryAdapter.getCount() <= 2 && event.getAction() == MotionEvent.ACTION_MOVE) {
                return true;
            } else {
                return false;
            }
        });
        return convertView;
    }
}
