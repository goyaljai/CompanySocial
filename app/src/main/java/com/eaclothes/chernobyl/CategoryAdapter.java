package com.eaclothes.chernobyl;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.eaclothes.chernobyl.classes.SubCategory;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;

public class CategoryAdapter extends PagerAdapter {
    private Context ctx;
    private ArrayList<SubCategory> mSubCategory;
    private ImageView mImageView;
    private TextView title, summary;

    CategoryAdapter(Context context, ArrayList<SubCategory> mMainSubCategory) {
        this.ctx = context;
        this.mSubCategory = mMainSubCategory;
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
        title.setText(mSubCategory.get(position).title);
        summary.setText(mSubCategory.get(position).summary);
        Transformation transformation = new Transformation() {

            @Override
            public Bitmap transform(Bitmap source) {
                int targetWidth = mImageView.getWidth();
                Log.d("GOYSLL", "" + targetWidth);
                if (targetWidth == 0) return source;
                double aspectRatio = (double) source.getHeight() / (double) source.getWidth();
                Log.d("GOYSLL", "" + aspectRatio);
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

        String imageUri = mSubCategory.get(position).image;
        imageUri = "http://13.233.83.239"+imageUri;
        Picasso.with(ctx).load(imageUri).transform(transformation).into(mImageView);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent  = new Intent(ctx,Detail_Activity.class);
                intent.putExtra("data",mSubCategory.get(position));
                ctx.startActivity(intent);
            }
        });
        container.addView(view);
        return view;
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
        return mSubCategory.size();
    }
}
