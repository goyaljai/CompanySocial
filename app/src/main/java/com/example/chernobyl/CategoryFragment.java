package com.example.chernobyl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.example.chernobyl.classes.ErrorActivity;
import com.example.chernobyl.classes.MainCategory;

import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;


public class CategoryFragment extends Fragment {
    private ListViewAdapter listViewAdapter;
    private MainCategory mainCategory;
    private Context mContext;
    private ViewPager slider;
    private View view;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private MySwipeRefreshLayout refreshLayout;

    public CategoryFragment() {
        // Required empty public constructor
    }

    public CategoryFragment(Context mContext, MainCategory mainCategory) {

        this.mainCategory = mainCategory;
        this.mContext = mContext;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    private int count = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_category_frag, container, false);
        refreshLayout = view.findViewById(R.id.refresh);
        slider = view.findViewById(R.id.slider);

        init();
        if(mainCategory!=null){
            listViewAdapter = new ListViewAdapter(mContext, mainCategory);
            ListView listView = view.findViewById(R.id.listView);
            listView.setAdapter(listViewAdapter);
            listView.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {

                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    if (slider.getChildAt(0) != null) {

                    }
                    if (listView.getChildAt(0) != null) {
                        refreshLayout.setEnabled(listView.getFirstVisiblePosition() == 0 && listView.getChildAt(0).getTop() == 0);
                    }
                }
            });


            refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    //Toast.makeText(mContext, "Count is" + count, Toast.LENGTH_LONG).show();
                    count++;
                    refreshLayout.setRefreshing(false);
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK |
                            Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            });
        }
        return view;
    }


    private void init() {
        slider = (ViewPager) view.findViewById(R.id.slider);
        if(mainCategory!=null){
            SlidingImage_Adapter slidingImage_adapter = new SlidingImage_Adapter(mContext, mainCategory.getRandomImageList());
            slider.setAdapter(slidingImage_adapter);
            CircleIndicator indicator = (CircleIndicator) view.findViewById(R.id.indicator);
            indicator.setViewPager(slider);
            slidingImage_adapter.registerDataSetObserver(indicator.getDataSetObserver());
            NUM_PAGES = mainCategory.getRandomImageList().size();

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


}
