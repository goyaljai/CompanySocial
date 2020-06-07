package com.eaclothes.chernobyl;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.eaclothes.chernobyl.classes.SubCategory;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;

public class SlidingImage_Adapter extends PagerAdapter {


    private ArrayList<SubCategory> randomImageList;
    private LayoutInflater inflater;
    private Context context;


    public SlidingImage_Adapter(Context context, ArrayList<SubCategory> imageModelArrayList) {
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
        String imageUri = "http://13.233.83.239" + randomImageList.get(position).image;

        Transformation transformation = new Transformation() {

            @Override
            public Bitmap transform(Bitmap source) {
                int targetWidth = imageLayout.getWidth();
                Log.d("GOYSLL",""+targetWidth);
                if(targetWidth==0) return source;
                double aspectRatio = (double) source.getHeight() / (double) source.getWidth();
                Log.d("GOYSLL",""+aspectRatio);
                int targetHeight = (int) (targetWidth * aspectRatio);
                Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
                if (result != source) {
                    // Same bitmap is returned if sizes are the same
                    source.recycle();
                }
                return result;
            }

            @Override
            public String key() {
                return "transformation" + " desiredWidth";
            }
        };

        Picasso.with(context).load(imageUri).transform(transformation).into(imageView);
        view.addView(imageLayout);
        imageLayout.setOnClickListener(v -> {

            Intent intent  = new Intent(context,Detail_Activity.class);
            intent.putExtra("data",randomImageList.get(position));
            context.startActivity(intent);
        });
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