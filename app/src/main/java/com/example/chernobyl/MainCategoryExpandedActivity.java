package com.example.chernobyl;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;

public class MainCategoryExpandedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_category_expanded);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        GridView gridViewList = findViewById(R.id.expandedGridView);
        MainSubCategory msc = (MainSubCategory) getIntent().getSerializableExtra("data");
        actionBar.setTitle(msc.description);
        actionBar.setDisplayHomeAsUpEnabled(true);
        GridViewAdapter gridViewAdapter = new GridViewAdapter(this,R.layout.content,msc.subCategoryArrayList);
        gridViewList.setAdapter(gridViewAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
