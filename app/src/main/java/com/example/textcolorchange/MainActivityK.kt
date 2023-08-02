package com.example.textcolorchange

import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import java.util.*

class MainActivityK : AppCompatActivity() {
    private val item = arrayOf("first", "second", "third", "fourth", "fifth", "sixth", "seventh")
    private lateinit var viewPager2: ViewPager2
    private var mIndicatorContainer: LinearLayout? = null
    private var mIndicator: MutableList<ColorTrackTextView>? = null
    private var dataList: MutableList<Fragment>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mIndicator = ArrayList()
        mIndicatorContainer = findViewById(R.id.indicator_view)
        viewPager2 = findViewById(R.id.vp2)
        initIndicator()
        initViewPager()
    }

    private fun initIndicator() {
        for (i in item) {
            val params = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            params.weight = 1f
            val trackTextView = ColorTrackTextView(this)
            trackTextView.textSize = 20f
            trackTextView.setChangeColor(Color.RED)
            trackTextView.text = i
            trackTextView.layoutParams = params
            //把新的加入到LinearLayout容器
            mIndicatorContainer!!.addView(trackTextView)
            //加入集合
            mIndicator!!.add(trackTextView)
        }
    }

    private fun initViewPager() {
        initData()
        viewPager2 = findViewById(R.id.vp2)
        viewPager2.adapter=MyViewPagerAdapter(supportFragmentManager, lifecycle, dataList)
        viewPager2.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                //利用viewpager的滚动监听，为ColorTrackTextView提供滚动进度
                val left = mIndicator!![position]
                left.setDirection(ColorTrackTextView.Direction.RIGHT_TO_LEFT)
                left.setCurrentProgress(1 - positionOffset)
                val right: ColorTrackTextView
                if (position < mIndicator!!.size - 1) {
                    right = mIndicator!![position + 1]
                    right.setDirection(ColorTrackTextView.Direction.LEFT_TO_RIGHT)
                    right.setCurrentProgress(positionOffset)
                }
            }
        })
    }
    private fun initData() {
        dataList = ArrayList()
        for (s in item) {
            val fragment = ItemFragment.newInstance(s)
            (dataList as ArrayList<Fragment>).add(fragment)
        }
    }
}




