package com.example.chernobyl;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.pager, null, false);
        }
        mCategoryName = convertView.findViewById(R.id.category_name);
        mCategoryName.setText(mMainCategory.getMainUISubCategories().get(position).description);
        CategoryAdapter categoryAdapter = new CategoryAdapter(mContext, mMainCategory.getMainUISubCategories().get(position).subCategoryArrayList);
        ViewPager page = convertView.findViewById(R.id.pager);
        page.setAdapter(categoryAdapter);
        return convertView;
    }
}
