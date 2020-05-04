package com.example.chernobyl;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.collection.LruCache;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.example.chernobyl.classes.ImageModel;
import com.example.chernobyl.classes.MainCategory;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;


public class CategoryFragment extends Fragment {
    //private androidx.collection.LruCache<Integer, Bitmap> mLruCache;
    //private ArrayList<LruCache<Integer, Bitmap>> mLruCacheList;
    private ListViewAdapter listViewAdapter;
    private MainCategory mainCategory;
    private ViewPager page;
    private Context mContext;
    private ViewPager slider;
    private View view;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private ArrayList<ImageModel> imageModelArrayList;
    private SwipeRefreshLayout refreshLayout;
    private int[] myImageList = new int[]{R.drawable.my8, R.drawable.my2,
            R.drawable.my4, R.drawable.my3
            , R.drawable.my5, R.drawable.my6};

    public CategoryFragment() {
        // Required empty public constructor
    }

    public CategoryFragment(Context mContext, MainCategory mainCategory) {
       // this.mLruCacheList = mLruCacheList;
        this.mainCategory = mainCategory;
        this.mContext = mContext;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
    private int count=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_category_frag, container, false);
        refreshLayout = view.findViewById(R.id.refresh);
        slider = view.findViewById(R.id.slider);

        imageModelArrayList = new ArrayList<>();
        imageModelArrayList = populateList();
        init();
        listViewAdapter = new ListViewAdapter(mContext, mainCategory);
        ListView listView = view.findViewById(R.id.listView);

        listView.setAdapter(listViewAdapter);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(mContext,"Count is"+count,Toast.LENGTH_LONG).show();
                count++;
                refreshLayout.setRefreshing(false);
            }
        });
        return view;
    }

    private ArrayList<ImageModel> populateList() {

        ArrayList<ImageModel> list = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            ImageModel imageModel = new ImageModel();
            imageModel.setImage_drawable(myImageList[i]);
            list.add(imageModel);
        }

        return list;
    }

    private void init() {
        slider = (ViewPager) view.findViewById(R.id.slider);
        SlidingImage_Adapter slidingImage_adapter = new SlidingImage_Adapter(mContext, imageModelArrayList);
        slider.setAdapter(slidingImage_adapter);
        CircleIndicator indicator = (CircleIndicator) view.findViewById(R.id.indicator);
        indicator.setViewPager(slider);
        slidingImage_adapter.registerDataSetObserver(indicator.getDataSetObserver());
        NUM_PAGES = imageModelArrayList.size();

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                slider.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);

        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });

    }


}
