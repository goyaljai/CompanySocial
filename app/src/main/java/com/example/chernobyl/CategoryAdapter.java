package com.example.chernobyl;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.collection.LruCache;
import androidx.viewpager.widget.PagerAdapter;

import com.example.chernobyl.classes.SubCategory;

public class CategoryAdapter extends PagerAdapter {
    private Context ctx;
    private SubCategory mCategory;
    private ImageView mImageView;
    private TextView title, summary;
    private LruCache<Integer, Bitmap> mLruCache;

    CategoryAdapter(Context context, SubCategory mCategory) {
        this.ctx = context;
        this.mCategory = mCategory;
    }

    public void setBmpCache(LruCache<Integer, Bitmap> mLruCache) {
        this.mLruCache = mLruCache;
    }

    @Override
    public void finishUpdate(@NonNull ViewGroup container) {
        super.finishUpdate(container);
    }

    @Override
    public float getPageWidth(int position) {
        return 0.45f;

    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.content, null);
        title = view.findViewById(R.id.t1);
        summary = view.findViewById(R.id.t2);
        mImageView = view.findViewById(R.id.image);
        title.setText(mCategory.title[position]);
        summary.setText(mCategory.summary[position]);
        mImageView.setImageBitmap(mLruCache.get(position));
        container.addView(view);
        return view;
        // use glide or lrucache
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((View) object);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return mCategory.image.length;
    }
}
