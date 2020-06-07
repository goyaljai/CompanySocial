package com.eaclothes.chernobyl;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.eaclothes.chernobyl.classes.SubCategory;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;

public class GridViewAdapter extends ArrayAdapter {
    private Context ctx;
    private ArrayList<SubCategory> mSubCategory;
    private ImageView mImageView;
    private TextView title, summary;

    public GridViewAdapter(Context context, int content, ArrayList<SubCategory> subCategoryArrayList) {
        super(context,content,subCategoryArrayList);
        mSubCategory = subCategoryArrayList;
        ctx = context;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (inflater != null) {
            View view = null;
            view = inflater.inflate(R.layout.content, null);

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
            imageUri = "http://13.233.83.239" + imageUri;
            Picasso.with(ctx).load(imageUri).transform(transformation).into(mImageView);
            mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(ctx, Detail_Activity.class);
                    intent.putExtra("data", mSubCategory.get(position));
                    ctx.startActivity(intent);
                }
            });

            return view;
        }
        return convertView;
    }

}
