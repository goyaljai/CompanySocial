package com.example.chernobyl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.collection.LruCache;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavInflater;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.toolbox.ImageLoader;
import com.example.chernobyl.classes.MainCategory;
import com.example.chernobyl.classes.RetroData;
import com.example.chernobyl.classes.SubCategory;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {
    private ArrayList<LruCache<Integer, Bitmap>> mLruCacheList = new ArrayList<>();
    private ArrayList<MainCategory> mMainCategoryArrayList = new ArrayList<>();
    private List<RetroData> mData;
    private BottomNavigationView bottomNavigationView;

    private void init(int size) {
//        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
//        Call<List<RetroData>> call = service.getAllPhotos();

        for (int t = 0; t < size; t++) {

//            call.enqueue(new Callback<List<RetroData>>() {
//                @Override
//                public void onResponse(Call<List<RetroData>> call, Response<List<RetroData>> response) {
//                 mData=response.body();
//                }
//
//                @Override
//                public void onFailure(Call<List<RetroData>> call, Throwable t) {
//                    Toast.makeText(MainActivity.this, "Some error occured", Toast.LENGTH_SHORT).show();
//                }
//            });
            int[] imgArray1 = {R.drawable.my9, R.drawable.my9, R.drawable.my9, R.drawable.my9};
            int[] imgArray2 = {R.drawable.my1, R.drawable.my1, R.drawable.my1, R.drawable.my1};
            String[] mName1 = {"Pic 1", "Pic 2", "Pic 3", "Pic 4"};
            String[] mName2 = {"Pic 5", "Pic 6", "Pic 7", "Pic 8"};
            String[] mSummary1 = {"This is  1", "This is  2", "This is  3", "This is  4"};
            String[] mSummary2 = {"This is  5", "This is  6", "This is  7", "This is  8"};

            generateLruCache(imgArray1);
            generateLruCache(imgArray2);

            ArrayList<SubCategory> subCategories = new ArrayList<>();
            subCategories.add(new SubCategory("Category 1", imgArray1, mSummary1, mName1));
            subCategories.add(new SubCategory("Category 2", imgArray2, mSummary2, mName2));
            mMainCategoryArrayList.add(new MainCategory("World", subCategories));
        }
    }


    private void generateLruCache(int[] imgArray) {
        LruCache<Integer, Bitmap> mLruCache;

        mLruCache = new LruCache<Integer, Bitmap>(4000) {
            @Override
            protected int sizeOf(@NonNull Integer key, @NonNull Bitmap bitmap) {
                return bitmap.getByteCount() / 1024;
            }
        };
        int i = 0;
        for (int img : imgArray) {
            Bitmap bmp = BitmapFactory.decodeResource(getResources(), img);
            bmp = Bitmap.createScaledBitmap(bmp, 150, 150, true);
            mLruCache.put(i++, bmp);
        }

        mLruCacheList.add(mLruCache);
    }


    private TabLayout mTabLayout;

    private TabPager adapter;
    private ViewPager pager;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mTabLayout = findViewById(R.id.tabLayout);
        mTabLayout.addTab(mTabLayout.newTab().setText("Tab1"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Tab2"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Tab3"));
        init(mTabLayout.getTabCount());
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        pager = findViewById(R.id.main_pager);
        adapter = new TabPager(getSupportFragmentManager(), mTabLayout.getTabCount(), mLruCacheList, this, mMainCategoryArrayList);

        pager.setAdapter(adapter);
        mTabLayout.addOnTabSelectedListener(this);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment f=new BlankFragment();
                Fragment fragment = new CategoryFragment(MainActivity.this, mLruCacheList, mMainCategoryArrayList.get(0));
                switch (item.getItemId()) {
                    case R.id.navigation_home: {
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.sample_frag, fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                        return true;
                    }
                    case R.id.navigation_sms: {
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.sample_frag, f);
                        transaction.addToBackStack(null);
                        transaction.commit();
                        return true;
                    }
                    case R.id.navigation_notifications: {
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.sample_frag, fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                        return true;
                    }
                }
                return false;
            }
        });




    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        pager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }


}

