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
import com.example.chernobyl.classes.SubCategoryData;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    private ArrayList<LruCache<Integer, Bitmap>> mLruCacheList = new ArrayList<>();

    protected List<List<AppData>> appData;
    protected List<List<MainCategoryData>> mainCategoryData;
    protected List<List<MainSubCategoryData>> mainSubCategoryData;
    private BottomNavigationView bottomNavigationView;
    private ArrayList<Integer> id;
    private int tabSize;
    private TabLayout mTabLayout;
    ArrayList<MainSubCategory> mainSubCategoryArrayList = new ArrayList<>();
    private ArrayList<MainCategory> mainCategoryList;
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
                } else {

                    if (response.body().size() > 0) {
                        appData = new ArrayList<List<AppData>>();
                        List<AppData> jsonArray = response.body();
                        appData.add(new ArrayList<AppData>(jsonArray));

                        //Sets App Basic Data
                        setAppData(appData.get(0).get(0).getAppTitle());

                        //To Display App Data(for verifying)
                        displayDataApp(appData);

                        //Function to request MainCategory Data
                        requestTabsFromAPi();
                    } else {
                        Toast.makeText(MainActivity.this, "No App Data Received", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<AppData>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error in fetching", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void setAppData(String appTitle) {
        ActionBarTitleGravity(appTitle);
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
                } else {
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
            }

            @Override
            public void onFailure(Call<List<MainCategoryData>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error in fetching", Toast.LENGTH_LONG).show();
            }
        });
    }

    private int finalI = 0;

    private void requestSubCategoriesFromApi() {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<MainSubCategoryData>> call;
        //Loop through all the tabs
        mainSubCategoryData = new ArrayList<List<MainSubCategoryData>>();
        finalI = 0;
        for (int i = 0; i < tabSize; i++) {
            Log.d("Ankit", mainCategoryData.get(0).get(i).getId() + " idd");
            call = service.requestMainSubData("json", mainCategoryData.get(0).get(i).getId());

            call.enqueue(new Callback<List<MainSubCategoryData>>() {
                @Override
                public void onResponse(Call<List<MainSubCategoryData>> call, Response<List<MainSubCategoryData>> response) {
                    finalI++;
                    Log.d("JAI ", finalI + "");
                    if (response.code() == 404) {
                        Log.d("40444", "4044");
                        Intent intent = new Intent(MainActivity.this, ErrorActivity.class);
                        startActivity(intent);
                    } else {

                        Log.d("Ankit", response + "");


                        List<MainSubCategoryData> jsonArray = response.body();
                        Log.d("Ankit", "Adding......" + mainSubCategoryData.size());
                        Log.d("JAI", response.body().toString());
                        mainSubCategoryData.add(new ArrayList<MainSubCategoryData>(jsonArray));

                        displayDataSub(mainSubCategoryData);
                        if (mainSubCategoryData.size() == tabSize) {
                            System.out.println("I came Here " + finalI);
                            System.out.println(mainSubCategoryData.size() + "163");
                            init();
                        }


                    }
                }

                @Override
                public void onFailure(Call<List<MainSubCategoryData>> call, Throwable t) {
                    Log.d("prob", "problem" + t.toString());
                    finalI++;
                    Log.d("Ankit", finalI + "f");
                    Toast.makeText(MainActivity.this, "Some error occured", Toast.LENGTH_SHORT).show();
                }
            });
        }


    }


    private void init() {
        //int size = mainSubCategoryData.size();
        System.out.println(tabSize + "hsizeeeee");
        System.out.println(mainSubCategoryData.size() + "mainSubcategory Data");
        System.out.println(mainSubCategoryData.size() + "mainSubcategory Data");
        mTabLayout = findViewById(R.id.tabLayout);

        for (int k = 0; k < tabSize; k++) {
            mTabLayout.addTab(mTabLayout.newTab().setText(mainCategoryData.get(0).get(k).getTitle()));
        }

        for (int t = 0; t < tabSize; t++) {

            mainSubCategoryArrayList = new ArrayList<>();
            //List<MainCategoryData> temp = mainCategoryData.get(0);
            for (int i = 0; i < mainSubCategoryData.get(t).size(); i++) {
                setCategoryData(mainSubCategoryData.get(t).get(i));
            }
            //ArrayList<MainCategory> tempMainCategoryList=new ArrayList<MainCategory>(appData.get(0).get(0).getAppTitle(), mainSubCategoryArrayList);
            mainCategoryList.add(new MainCategory(appData.get(0).get(0).getAppTitle(), mainSubCategoryArrayList));
        }
        System.out.println("tabselecteddd  " + mTabLayout.getSelectedTabPosition());
        pager = findViewById(R.id.main_pager);
        //adapter = new TabPager(getSupportFragmentManager(), mTabLayout.getTabCount(), mLruCacheList, this, mMainCategoryArrayList);
        adapter = new TabPager(getSupportFragmentManager(), mTabLayout.getTabCount(), this, mainCategoryList);
        pager.setAdapter(adapter);

        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        mTabLayout.addOnTabSelectedListener(this);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment f = new BlankFragment();
                Fragment fragment = new CategoryFragment(MainActivity.this, mainCategoryList.get(mTabLayout.getSelectedTabPosition()));
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

    private void setCategoryData(MainSubCategoryData mainsubCategoryData) {

        ArrayList<SubCategory> subCategories = new ArrayList<>();
        String mCategoryName = mainsubCategoryData.getDescription();
        String[] mName = new String[mainsubCategoryData.getSubcategories().size()];
        String[] mSummary = new String[mainsubCategoryData.getSubcategories().size()];
        String[] imgArray = new String[mainsubCategoryData.getSubcategories().size()];

        for (int i = 0; i < mainsubCategoryData.getSubcategories().size(); i++) {
            SubCategoryData subCategoryData = mainsubCategoryData.getSubcategories().get(i);
            mName[i] = subCategoryData.getImgtitle();
            mSummary[i] = subCategoryData.getImgdesc();
            imgArray[i] = subCategoryData.getUrl();
            subCategories.add(new SubCategory(mCategoryName, imgArray[i], mSummary[i], mName[i]));
        }

        mainSubCategoryArrayList.add(new MainSubCategory(mainsubCategoryData.getId(), mainsubCategoryData.getDescription(), subCategories));

    }


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
        mainCategoryList = new ArrayList<>();
        //Function to Request App Data
        requestDataForApp();


    }


    private void displayDataApp(List<List<AppData>> appData) {
        System.out.println(appData.get(0).get(0).getTitle() + "this is my title");
    }

    private void displayData(List<List<MainCategoryData>> mainCategoryData) {
        System.out.println("My sizzeee" + tabSize);
        for (int i = 0; i < mainCategoryData.get(0).size(); i++) {
            System.out.println("HIIII" + mainCategoryData.get(0).size() + "  " + mainCategoryData.get(0).get(i).getTitle());
        }
    }

    private void displayDataSub(List<List<MainSubCategoryData>> mainSubCategoryData) {
        System.out.println(mainSubCategoryData.size() + " see it increasing " + mainSubCategoryData.get(0).get(0).getSubcategories().get(0).getImgdesc() + "Descc" + "    " + mainSubCategoryData.get(0).get(0).getSubcategories().size());
    }

    private void ActionBarTitleGravity(String appTitle) {
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        actionBar = getSupportActionBar();
        textView = new TextView(getApplicationContext());

        layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        actionBar.setDisplayHomeAsUpEnabled(true);

        textView.setLayoutParams(layoutParams);
        textView.setText(appTitle);
        textView.setTextColor(Color.WHITE);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(22);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        // actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setCustomView(textView);
        //actionBar.setDisplayHomeAsUpEnabled(true);

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

