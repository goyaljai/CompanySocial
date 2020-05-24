package com.example.chernobyl;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
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
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    private ArrayList<LruCache<Integer, Bitmap>> mLruCacheList = new ArrayList<>();
    protected List<List<AppData>> appData;
    protected List<List<MainCategoryData>> mainCategoryData;
    protected List<MainSubCategoryData> mainSubCategoryData;
    private BottomNavigationView bottomNavigationView;
    private ArrayList<Integer> id;
    private int tabSize;
    private TabLayout mTabLayout;
    ArrayList<MainSubCategory> mainSubCategoryArrayList = new ArrayList<>();
    private ArrayList<MainCategory> mainCategoryList;
    private ArrayList<MainCategory> mainCategoryUIList;
    private TabPager adapter;
    private ViewPager pager;
    private ActionBar actionBar;
    private TextView textView;
    private ActionBar.LayoutParams layoutParams;
    private LinkedHashMap<Integer, Integer> tabIdMap;
    private LinkedHashMap<Integer, String> tabNameMap;
    private LinkedHashMap<Integer, List<MainSubCategoryData>> helper;
    private ArrayList<String> subRandomImageList;

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

                        setAppData(appData.get(0).get(0).getTitle());
                        //Function to request MainCategory Data
                        requestTabsFromAPi();
                    } else {
                        Intent intent = new Intent(MainActivity.this, ErrorActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finishAffinity();
                        finish();
                        Toast.makeText(MainActivity.this, "No App Data Received", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<AppData>> call, Throwable t) {
                Intent intent = new Intent(MainActivity.this, ErrorActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finishAffinity();
                finish();
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
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK |
                            Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finishAffinity();
                    finish();
                } else {
                    if (response.body().size() > 0) {
                        mainCategoryData = new ArrayList<List<MainCategoryData>>();
                        List<MainCategoryData> jsonArray = response.body();
                        mainCategoryData.add(new ArrayList<MainCategoryData>(jsonArray));
                        tabSize = mainCategoryData.get(0).size();
                        mapTabIdWithMainCategory(tabSize);

                        try {
                            requestSubCategoriesFromApi();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Intent intent = new Intent(MainActivity.this, ErrorActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finishAffinity();
                        finish();
                        Toast.makeText(MainActivity.this, "Empty Tab Received", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<MainCategoryData>> call, Throwable t) {
                Intent intent = new Intent(MainActivity.this, ErrorActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finishAffinity();
                finish();
                Toast.makeText(MainActivity.this, "Error in fetching", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void mapTabIdWithMainCategory(int tabSize) {
        tabNameMap = new LinkedHashMap<>();
        tabIdMap = new LinkedHashMap<>();
        for (int k = 0; k < tabSize; k++) {
            tabNameMap.put(mainCategoryData.get(0).get(k).getId(), mainCategoryData.get(0).get(k).getTitle());
            tabIdMap.put(k, mainCategoryData.get(0).get(k).getId());
        }
    }

    private void initiateUI(LinkedHashMap<Integer, List<MainSubCategoryData>> helper) {
        ArrayList<Integer> sorter = new ArrayList<>(helper.keySet());
        Collections.sort(sorter);
        for (int i : sorter) {
            mainCategoryList.add(new MainCategory(i, tabNameMap.get(i), helper.get(i)));
        }
        for (int i = 0; i < mainCategoryList.size(); i++) {
            Log.i("Finally", mainCategoryList.get(i).getId() + "  " + mainCategoryList.get(i).getmMainSubCategories());
        }
        init();
    }

    public class MyCallable implements Callable<MainCategory> {
        private int id;
        List<MainSubCategoryData> res;

        public MyCallable(int id) {
            this.id = id;
            this.res = new ArrayList<>();
        }

        @Override
        public MainCategory call() throws Exception {
            GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<List<MainSubCategoryData>> call;

            /***************************/

            call = service.requestMainSubData("json", id);

            call.enqueue(new Callback<List<MainSubCategoryData>>() {
                @Override
                public void onResponse(Call<List<MainSubCategoryData>> call, Response<List<MainSubCategoryData>> response) {

                    //  Log.d("JAI ", finalI + "");
                    if (response.code() == 404) {
                        Log.d("40444", "4044");
                        Intent intent = new Intent(MainActivity.this, ErrorActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finishAffinity();
                        finish();
                    } else {
                        res = response.body();
                        Log.d("Resss", res + " id " + id);
                        helper.put(id, res);
                        if (helper.size() == tabSize) {
                            initiateUI(helper);
                        }
                        //Log.d("Ankit", helper.size() + "");
                        //  Log.d("Ankit", "Adding......" + mainSubCategoryData.size());
                        //Log.d("JAI", response.body().toString());

                    }

                }

                @Override
                public void onFailure(Call<List<MainSubCategoryData>> call, Throwable t) {
                    Log.d("prob", "problem" + t.toString());
                    Intent intent = new Intent(MainActivity.this, ErrorActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK |
                            Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finishAffinity();
                    finish();
                    Toast.makeText(MainActivity.this, "Some error occured", Toast.LENGTH_SHORT).show();
                }
            });

            /***************************/

            return new MainCategory(id, tabNameMap.get(id), res);
        }

    }

    private void requestSubCategoriesFromApi() throws ExecutionException, InterruptedException {
        helper = new LinkedHashMap<>();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<MainCategory>[] future = new Future[tabSize];
        for (int i = 0; i < tabSize; i++) {
            future[i] = executor.submit(new MyCallable(mainCategoryData.get(0).get(i).getId()));
        }
    }


    private void init() {
        Log.d("Finally1", mainCategoryList.size() + " ");
        mTabLayout = findViewById(R.id.tabLayout);
        mainCategoryUIList = new ArrayList<>();
        for (int k = 0; k < tabSize; k++) {
            mTabLayout.addTab(mTabLayout.newTab().setText(tabNameMap.get(tabIdMap.get(k))));
        }

        for (int t = 0; t < tabSize; t++) {

            subRandomImageList = new ArrayList<>();
            mainSubCategoryArrayList = new ArrayList<>();

            Log.d("Finally1", mainCategoryList.get(t).getmMainSubCategories().size() + " size(260)");

            for (int i = 0; i < mainCategoryList.get(t).getmMainSubCategories().size(); i++) {
                setCategoryData(mainCategoryList.get(t).getmMainSubCategories().get(i));
            }

            mainCategoryUIList.add(new MainCategory(mainSubCategoryArrayList, tabIdMap.get(t), tabNameMap.get(tabIdMap.get(t)), subRandomImageList));

            // Log.d("Finally1", mainCategoryUIList.size() + "sizeMain266  sub" + subRandomImageList.size() + "  " + subRandomImageList.get(0));
        }
        System.out.println("tabselecteddd  " + mTabLayout.getSelectedTabPosition());
        pager = findViewById(R.id.main_pager);
        adapter = new TabPager(getSupportFragmentManager(), mTabLayout.getTabCount(), this, mainCategoryUIList);
        pager.setAdapter(adapter);

        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mTabLayout.addOnTabSelectedListener(this);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_sms:
                case R.id.navigation_notifications: {
                    Uri uri = Uri.parse("https://www.instagram.com/everything_abt_clothes/"); // missing 'http://' will cause crashed
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                    return true;
                }
            }
            return false;
        });


    }

    private void setCategoryData(MainSubCategoryData mainsubCategoryData) {

        ArrayList<SubCategory> subCategories = new ArrayList<>();
        String mCategoryName = mainsubCategoryData.getDescription();
        String[] mName = new String[mainsubCategoryData.getSubcategories().size()];
        String[] mSummary = new String[mainsubCategoryData.getSubcategories().size()];
        String[] imgArray = new String[mainsubCategoryData.getSubcategories().size()];
        int randCount = 0;
        for (int i = 0; i < mainsubCategoryData.getSubcategories().size(); i++) {
            SubCategoryData subCategoryData = mainsubCategoryData.getSubcategories().get(i);
            mName[i] = subCategoryData.getImgtitle();
            mSummary[i] = subCategoryData.getImgdesc();
            imgArray[i] = subCategoryData.getUrl();
            subCategories.add(new SubCategory(mCategoryName, imgArray[i], mSummary[i], mName[i]));
            if (randCount < 2) {
                subRandomImageList.add(imgArray[i]);
                randCount++;
            }
        }
        Log.d("Finally1", subCategories.size() + "sizesub326");
        mainSubCategoryArrayList.add(new MainSubCategory(mainsubCategoryData.getId(), mainsubCategoryData.getDescription(), subCategories));
        Log.d("Finally1", mainSubCategoryArrayList.size() + "size328");
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainCategoryList = new ArrayList<>();
        //Function to Request App Data
        requestDataForApp();


    }

    private void ActionBarTitleGravity(String appTitle) {

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
        actionBar.setCustomView(textView);
        textView.setOnClickListener(v -> {
            Uri uri = Uri.parse("https://www.instagram.com/everything_abt_clothes/"); // missing 'http://' will cause crashed
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
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

    public Future<String> calculateAsync() throws InterruptedException {
        CompletableFuture<String> completableFuture
                = new CompletableFuture<>();

        Executors.newCachedThreadPool().submit(() -> {
            Thread.sleep(500);
            completableFuture.complete("Hello");
            return null;
        });

        return completableFuture;
    }

}

