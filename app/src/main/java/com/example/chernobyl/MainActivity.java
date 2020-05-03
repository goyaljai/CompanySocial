package com.example.chernobyl;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.LruCache;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.example.chernobyl.classes.AppData;
import com.example.chernobyl.classes.ErrorActivity;
import com.example.chernobyl.classes.MainCategory;
import com.example.chernobyl.classes.MainCategoryData;
import com.example.chernobyl.classes.MainSubCategoryData;
import com.example.chernobyl.classes.SubCategory;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    private ArrayList<LruCache<Integer, Bitmap>> mLruCacheList = new ArrayList<>();
    private ArrayList<MainCategory> mMainCategoryArrayList = new ArrayList<>();
    protected List<List<AppData>> appData;
    protected List<List<MainCategoryData>> mainCategoryData;
    protected List<List<MainSubCategoryData>> mainSubCategoryData;
    private BottomNavigationView bottomNavigationView;
    private ArrayList<Integer> id;
    private int tabSize;
    private TabLayout mTabLayout;

    private TabPager adapter;
    private ViewPager pager;
    private ActionBar actionBar;
    private TextView textView;
    private ActionBar.LayoutParams layoutParams;



    private void requestDataForApp() {

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<AppData>> call = service.requestAppData();
        call.enqueue(new Callback<List<AppData>>() {
            @Override
            public void onResponse(Call<List<AppData>> call, Response<List<AppData>> response) {

                if (response.code() == 404) {
                    Intent intent = new Intent(MainActivity.this, ErrorActivity.class);
                    startActivity(intent);
                }

                if (response.body().size() > 0) {
                    appData = new ArrayList<List<AppData>>();
                    List<AppData> jsonArray = response.body();
                    appData.add(new ArrayList<AppData>(jsonArray));

                    //To Display App Data(for verifying)
                    displayDataApp(appData);

                    //Function to request MainCategory Data
                    requestTabsFromAPi();
                } else {
                    Toast.makeText(MainActivity.this, "No App Data Received", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<AppData>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error in fetching", Toast.LENGTH_LONG).show();
            }
        });

    }
    private void requestTabsFromAPi() {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<MainCategoryData>> call = service.requestMainData();
        call.enqueue(new Callback<List<MainCategoryData>>() {
            @Override
            public void onResponse(Call<List<MainCategoryData>> call, Response<List<MainCategoryData>> response) {
                if (response.code() == 404) {
                    Intent intent = new Intent(MainActivity.this, ErrorActivity.class);
                    startActivity(intent);
                }

                if (response.body().size() > 0) {
                    mainCategoryData = new ArrayList<List<MainCategoryData>>();
                    List<MainCategoryData> jsonArray = response.body();
                    mainCategoryData.add(new ArrayList<MainCategoryData>(jsonArray));
                    tabSize = mainCategoryData.get(0).size();
                    //Displays tab data for verification
                    displayData(mainCategoryData);

                    //Requests sybcategories Data
                    requestSubCategoriesFromApi();
                } else {
                    Toast.makeText(MainActivity.this, "Empty Tab Received", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<MainCategoryData>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error in fetching", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void requestSubCategoriesFromApi() {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<MainSubCategoryData>> call;
        //Loop through all the tabs
        for (int i = 0; i < tabSize; i++) {

            call = service.requestMainSubData("json", mainCategoryData.get(0).get(i).getId());
            call.enqueue(new Callback<List<MainSubCategoryData>>() {
                @Override
                public void onResponse(Call<List<MainSubCategoryData>> call, Response<List<MainSubCategoryData>> response) {
                    if (response.code() == 404) {
                        Intent intent = new Intent(MainActivity.this, ErrorActivity.class);
                        startActivity(intent);
                    }
                    Log.d("MyResp", response + "");
                    if (response.body().size() > 0) {
                        mainSubCategoryData = new ArrayList<List<MainSubCategoryData>>();
                        List<MainSubCategoryData> jsonArray = response.body();
                        mainSubCategoryData.add(new ArrayList<MainSubCategoryData>(jsonArray));

                        //Function to print data whether it is correct or not
                        displayDataSub(mainSubCategoryData);
                        // init(mainSubCategoryData);  currently commented as not setting the data to UI
                    } else {
                        Toast.makeText(MainActivity.this, "Empty Sub Data Received", Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<List<MainSubCategoryData>> call, Throwable t) {
                    Log.d("prob", "problem" + t.toString());

                    Toast.makeText(MainActivity.this, "Some error occured", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }





//    private void init(List<List<MainCategoryData>> mainCategoryData) {
//
//        //String[] mName1 = {"Pic 1", "Pic 2", "Pic 3", "Pic 4"};
//        //String[] mName2 = {"Pic 5", "Pic 6", "Pic 7", "Pic 8"};
//        //String[] mSummary1 = {"This is  1", "This is  2", "This is  3", "This is  4"};
//        //String[] mSummary2 = {"This is  5", "This is  6", "This is  7", "This is  8"};
//        //int[] imgArray1 = {R.drawable.my9, R.drawable.my9, R.drawable.my9, R.drawable.my9};
//        //int[] imgArray2 = {R.drawable.my1, R.drawable.my1, R.drawable.my1, R.drawable.my1};
//        String[] mName1 = new String[mainCategoryData.get(0).size()];
//        String[] mName2 = new String[mainCategoryData.get(0).size()];
//        String[] imgArray1 = new String[mainCategoryData.get(0).size()];
//        String[] imgArray2 = new String[mainCategoryData.get(0).size()];
//        String[] mSummary1 = new String[mainCategoryData.get(0).size()];
//        String[] mSummary2 = new String[mainCategoryData.get(0).size()];
//
//        List<MainCategoryData> temp = mainCategoryData.get(0);
//        for (int i = 0; i < mainCategoryData.get(0).size(); i++) {
//            mName1[i] = temp.get(i).getTitle();
//            mName2[i] = mName1[i];
//            imgArray1[i] = temp.get(i).getUrl();
//            imgArray2[i] = imgArray1[i];
//            mSummary1[i] = temp.get(i).getImgdesc();
//            mSummary2[i] = mSummary1[i];
//        }
//        mTabLayout = findViewById(R.id.tabLayout);
//        mTabLayout.addTab(mTabLayout.newTab().setText("Tab1"));
//        mTabLayout.addTab(mTabLayout.newTab().setText("Tab2"));
//        mTabLayout.addTab(mTabLayout.newTab().setText("Tab3"));
//        int size = mTabLayout.getTabCount();
//        for (int t = 0; t < size; t++) {
//
//
////            generateLruCache(imgArray1);
////            generateLruCache(imgArray2);
//            ArrayList<SubCategory> subCategories = new ArrayList<>();
//            subCategories.add(new SubCategory("Category 1", imgArray1, mSummary1, mName1));
//            subCategories.add(new SubCategory("Category 2", imgArray2, mSummary2, mName2));
//            mMainCategoryArrayList.add(new MainCategory("World", subCategories));
//        }
//
//        //init(mTabLayout.getTabCount(), mainCategoryData);
//        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
//        pager = findViewById(R.id.main_pager);
//        //adapter = new TabPager(getSupportFragmentManager(), mTabLayout.getTabCount(), mLruCacheList, this, mMainCategoryArrayList);
//        adapter = new TabPager(getSupportFragmentManager(), mTabLayout.getTabCount(), this, mMainCategoryArrayList);
//        pager.setAdapter(adapter);
//        mTabLayout.addOnTabSelectedListener(this);
//        bottomNavigationView = findViewById(R.id.bottom_navigation);
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                Fragment f = new BlankFragment();
//                Fragment fragment = new CategoryFragment(MainActivity.this, mMainCategoryArrayList.get(0));
//                switch (item.getItemId()) {
//                    case R.id.navigation_home: {
//                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                        transaction.replace(R.id.sample_frag, fragment);
//                        transaction.addToBackStack(null);
//                        transaction.commit();
//                        return true;
//                    }
//                    case R.id.navigation_sms: {
//                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                        transaction.replace(R.id.sample_frag, f);
//                        transaction.addToBackStack(null);
//                        transaction.commit();
//                        return true;
//                    }
//                    case R.id.navigation_notifications: {
//                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                        transaction.replace(R.id.sample_frag, fragment);
//                        transaction.addToBackStack(null);
//                        transaction.commit();
//                        return true;
//                    }
//                }
//                return false;
//            }
//        });
//    }


//    private void generateLruCache(int[] imgArray) {
//        LruCache<Integer, Bitmap> mLruCache;
//        mLruCache = new LruCache<Integer, Bitmap>(4000) {
//            @Override
//            protected int sizeOf(@NonNull Integer key, @NonNull Bitmap bitmap) {
//                return bitmap.getByteCount() / 1024;
//            }
//        };
//        int i = 0;
//        for (int img : imgArray) {
//            Bitmap bmp = BitmapFactory.decodeResource(getResources(), img);
//            bmp = Bitmap.createScaledBitmap(bmp, 150, 150, true);
//            mLruCache.put(i++, bmp);
//        }
//
//        mLruCacheList.add(mLruCache);
//    }




    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Function to Request App Data
        requestDataForApp();

        ActionBarTitleGravity();
    }


    private void displayDataApp(List<List<AppData>> appData) {
        System.out.println(appData.get(0).get(0).getTitle() + "this is my title");
    }

    private void displayData(List<List<MainCategoryData>> mainCategoryData) {
        for (int i = 0; i < mainCategoryData.get(0).size(); i++) {
            System.out.println("HIIII" + mainCategoryData.get(0).size() + "  " + mainCategoryData.get(0).get(i).getTitle());
        }
    }

    private void displayDataSub(List<List<MainSubCategoryData>> mainSubCategoryData) {
        System.out.println(mainSubCategoryData.get(0).get(0).getSubcategories().get(0).getImgdesc() + "Descc" + "    " + mainSubCategoryData.get(0).get(0).getSubcategories().size());
    }

    private void ActionBarTitleGravity() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        actionBar = getSupportActionBar();
        textView = new TextView(getApplicationContext());

        layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        actionBar.setDisplayHomeAsUpEnabled(true);

        textView.setLayoutParams(layoutParams);
        textView.setText("Chernobyl");
        textView.setTextColor(Color.WHITE);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(22);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        // actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setCustomView(textView);
        actionBar.setDisplayHomeAsUpEnabled(true);

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

