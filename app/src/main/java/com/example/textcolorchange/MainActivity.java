package com.example.textcolorchange;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String[] item = {"first", "second", "third", "fourth", "fifth", "sixth", "seventh"};

    private ViewPager2 viewPager2;
    private LinearLayout mIndicatorContainer;
    private List<ColorTrackTextView> mIndicator;
    public static String TAG = "tag";
    private List<Fragment> dataList;

    //测试的，不要删
    private ColorTrackTextView colorTrackTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        colorTrackTextView = findViewById(R.id.color);

        mIndicator = new ArrayList<>();
        mIndicatorContainer = findViewById(R.id.indicator_view);
        viewPager2 = findViewById(R.id.vp2);
        initIndicator();
        initViewPager();


    }

    private void initIndicator() {
        for (int i = 0; i < item.length; i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.weight = 1;
            ColorTrackTextView trackTextView = new ColorTrackTextView(this);
            trackTextView.setTextSize(20);
            trackTextView.setChangeColor(Color.RED);
            trackTextView.setText(item[i]);
            trackTextView.setLayoutParams(params);
            //把新的加入到LinearLayout容器
            mIndicatorContainer.addView(trackTextView);
            //加入集合
            mIndicator.add(trackTextView);
        }

    }

    private void initViewPager() {
        initData();
        viewPager2 = findViewById(R.id.vp2);
        viewPager2.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager(), getLifecycle(), dataList));
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                //利用viewpager的滚动监听，为ColorTrackTextView提供滚动进度
                ColorTrackTextView left = mIndicator.get(position);
                left.setDirection(ColorTrackTextView.Direction.RIGHT_TO_LEFT);
                left.setCurrentProgress(1-positionOffset);
                ColorTrackTextView right;
                if (position<mIndicator.size()-1) {
                    right = mIndicator.get(position + 1);
                    right.setDirection(ColorTrackTextView.Direction.LEFT_TO_RIGHT);
                    right.setCurrentProgress(positionOffset);
                }


            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });


    }

    private void initData() {
        dataList = new ArrayList<>();
        for (String s : item) {
            ItemFragment fragment = ItemFragment.newInstance(s);
            dataList.add(fragment);
        }
    }

    public void leftToRight(View view) {
        colorTrackTextView.setDirection(ColorTrackTextView.Direction.LEFT_TO_RIGHT);
        ValueAnimator animator = ObjectAnimator.ofFloat(0, 1);
        animator.setDuration(2000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(@NonNull ValueAnimator animation) {
                float currentProgress = (float) animation.getAnimatedValue();
                colorTrackTextView.setCurrentProgress(currentProgress);
            }
        });
        animator.start();
    }

    public void rightToLeft(View view) {
        colorTrackTextView.setDirection(ColorTrackTextView.Direction.RIGHT_TO_LEFT);
        ValueAnimator animator = ObjectAnimator.ofFloat(0, 1);
        animator.setDuration(2000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(@NonNull ValueAnimator animation) {
                float currentProgress = (float) animation.getAnimatedValue();
                colorTrackTextView.setCurrentProgress(currentProgress);
            }
        });
        animator.start();
    }


}