package com.example.chernobyl;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.drawerlayout.widget.DrawerLayout;

public class TopLinearLayout extends LinearLayout {

    public TopLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

    }
    public TopLinearLayout(Context applicationContext) {
        super(applicationContext,null);

        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);

        LayoutInflater inflater = (LayoutInflater) applicationContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_color_options, this, true);
    }

    public View getView22(){
        return findViewById(R.id.badge_layout1);
    }
}