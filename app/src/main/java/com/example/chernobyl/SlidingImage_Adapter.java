package com.example.chernobyl;

import android.content.Context;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Parsania Hardik on 23/04/2016.
 */
public class SlidingImage_Adapter extends PagerAdapter {


    private ArrayList<String> randomImageList;
    private LayoutInflater inflater;
    private Context context;


    public SlidingImage_Adapter(Context context, ArrayList<String> imageModelArrayList) {
        this.context = context;
        this.randomImageList = imageModelArrayList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return randomImageList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View imageLayout = inflater.inflate(R.layout.slidingimages_layout, null);

        assert imageLayout != null;
        final ImageView imageView = (ImageView) imageLayout.findViewById(R.id.slide_image);
        String imageUri = "http://13.233.83.239" + randomImageList.get(position);
        Picasso.with(context).load(imageUri).fit().into(imageView);
        view.addView(imageLayout);
        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }


}