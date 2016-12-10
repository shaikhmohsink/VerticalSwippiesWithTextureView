package com.mohsin;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.mohsin.adapter.PopularCategoriesFragmentAdapter;
import com.mohsin.components.VerticalViewPager;
import com.mohsin.components.transformer.DepthPageTransformer;
import com.mohsin.fragment.PopularCategoriesFragment;

public class MainActivity extends AppCompatActivity {
    public int currentlySelectedPopularCategoriesPage = 0;
    public PopularCategoriesFragmentAdapter popularCategoriesFragmentAdapter;
    public boolean pauseVideoAndShowImageOnlyOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        VerticalViewPager popularCategoriesVerticalViewPager = (VerticalViewPager) findViewById(R.id.popularCategoriesVerticalViewPager);
        popularCategoriesVerticalViewPager.setPageTransformer(true, new DepthPageTransformer());
        // The easiest way to get rid of the overscroll drawing that happens on the left and right
        popularCategoriesVerticalViewPager.setOverScrollMode(popularCategoriesVerticalViewPager.OVER_SCROLL_NEVER);
        popularCategoriesVerticalViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(!pauseVideoAndShowImageOnlyOnce) {
                    try { ((PopularCategoriesFragment) popularCategoriesFragmentAdapter.getItem(currentlySelectedPopularCategoriesPage)).mediaPlayer.pause(); } catch(Exception e) {}
                    pauseVideoAndShowImageOnlyOnce = true;
                }

                if(pauseVideoAndShowImageOnlyOnce && positionOffsetPixels == 0) {
                    pauseVideoAndShowImageOnlyOnce = false;
                    ((PopularCategoriesFragment) popularCategoriesFragmentAdapter.getItem(currentlySelectedPopularCategoriesPage)).currentlySelected = position;
                    AppController.currentlySelectedPopularCategoriesPage = position;
                    try { ((PopularCategoriesFragment) popularCategoriesFragmentAdapter.getItem(currentlySelectedPopularCategoriesPage)).mediaPlayer.start(); } catch(Exception e) {}
                }
            }

            @Override
            public void onPageSelected(int position) {
                currentlySelectedPopularCategoriesPage = position;
                System.out.println("onPageSelected position:"+position);
                ((PopularCategoriesFragment) popularCategoriesFragmentAdapter.getItem(currentlySelectedPopularCategoriesPage)).currentlySelected = position;
                AppController.currentlySelectedPopularCategoriesPage = position;
                try { ((PopularCategoriesFragment) popularCategoriesFragmentAdapter.getItem(currentlySelectedPopularCategoriesPage)).mediaPlayer.start(); } catch(Exception e) {}
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        // Create an adapter with the fragments we show on the ViewPager
        popularCategoriesFragmentAdapter = new PopularCategoriesFragmentAdapter(
                getSupportFragmentManager(), 7);
        for (int i = 0; i < 7; i++) {
            PopularCategoriesFragment popularCategoriesFragment = new PopularCategoriesFragment();

            popularCategoriesFragment.indexOfThis = i;

            if(i == 0) {
                popularCategoriesFragment.setColorVaule("#ffffff");
                popularCategoriesFragment.videoToPlayURL = "Video URL";
            } else if(i == 1) {
                popularCategoriesFragment.setColorVaule("#ff0000");
                popularCategoriesFragment.videoToPlayURL = "Video URL";
            } else if(i == 2) {
                popularCategoriesFragment.setColorVaule("#00ff00");
                popularCategoriesFragment.videoToPlayURL = "Video URL";
            } else if(i == 3) {
                popularCategoriesFragment.setColorVaule("#0000ff");
                popularCategoriesFragment.videoToPlayURL = "Video URL";
            } else if(i == 4) {
                popularCategoriesFragment.setColorVaule("#0f0f0f");
                popularCategoriesFragment.videoToPlayURL = "Video URL";
            } else if(i == 5) {
                popularCategoriesFragment.setColorVaule("#ffffff");
                popularCategoriesFragment.videoToPlayURL = "Video URL";
            } else if(i == 6) {
                popularCategoriesFragment.setColorVaule("#ff0000");
                popularCategoriesFragment.videoToPlayURL = "Video URL";
            } else {
                popularCategoriesFragment.setColorVaule("#00ff00");
                popularCategoriesFragment.videoToPlayURL = "Video URL";
            }
            popularCategoriesFragmentAdapter.addFragment(popularCategoriesFragment);
        }

        popularCategoriesVerticalViewPager.setAdapter(popularCategoriesFragmentAdapter);
        //popularCategoriesVerticalViewPager.setOffscreenPageLimit(popularCategoriesFragmentAdapter.getCount());
        //popularCategoriesVerticalViewPager.setVerticalFadingEdgeEnabled(true);

        AppController.currentlySelectedPopularCategoriesPage = 0;
        //try { ((PopularCategoriesFragment) popularCategoriesFragmentAdapter.getItem(currentlySelectedPopularCategoriesPage)).mediaPlayer.start(); } catch(Exception e) {}
    }
}
