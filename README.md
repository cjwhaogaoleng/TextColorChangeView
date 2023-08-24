# TextColorChangeView
实现字体变色效果的view，可以和其余需要滑动的页面结合
 ## 效果展示
https://github.com/cjwhaogaoleng/TextColorChangeView/assets/117556474/7f792e20-3ab4-466d-b2f8-c1711dc01c06

以上展示的只是和viewPager相结合
 ## 引入依赖
暂未打包，后面可以导成依赖供考核使用
 ## 源码位置
/app/src/main/java/com/example/textcolorchange/ColorTrackTextViewK.kt
/app/src/main/java/com/example/textcolorchange/ColorTrackTextView.java
 ## 代码讲解
  ### JAVA
```java
viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                //利用viewpager的滚动监听，为ColorTrackTextView提供滚动进度
                ColorTrackTextView left = mIndicator.get(position);
                //设置左边的滚动方式
                left.setDirection(ColorTrackTextView.Direction.RIGHT_TO_LEFT);
                left.setCurrentProgress(1-positionOffset);
                //设置右边textview的滚动方式
                ColorTrackTextView right;
                if (position<mIndicator.size()-1) {
                    right = mIndicator.get(position + 1);
                    right.setDirection(ColorTrackTextView.Direction.LEFT_TO_RIGHT);
                    right.setCurrentProgress(positionOffset);
                        }
            }
        });
```
  ### KOTLIN
```kotlin
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
                //设置左边的滚动方式
                left.setDirection(ColorTrackTextView.Direction.RIGHT_TO_LEFT)
                left.setCurrentProgress(1 - positionOffset)
                //设置右边textview的滚动方式
                val right: ColorTrackTextView
                if (position < mIndicator!!.size - 1) {
                    right = mIndicator!![position + 1]
                    right.setDirection(ColorTrackTextView.Direction.LEFT_TO_RIGHT)
                    right.setCurrentProgress(positionOffset)
                }
            }
        })
    }
```
 ## 实现方法
 ```
 //设置颜色变化方向
 fun setDirection(direction: Direction?);
 //设置颜色变化的进度
 fun setCurrentProgress(mCurrentProgress: Float);
 //设置变化后的颜色
 fun setChangeColor(changeColor: Int);
 //设置初始字体颜色
 fun setOriginColor(originColor: Int);
```
 ## 待完成
 - [x] 自定义view & viewgroup
   - [x] onMeasure 源码和写法基本了解
   - [x] onDraw 源码和写法基本了解
   - [x] onTouch 分发，拦截，处理事件，源码分析完成
   - [x] onLayout viewGroup内view的摆放
   - [ ] 动画处理
   - [ ] materialDdsign 正在学习
 - [ ] compose 已经接触，还没有另一种熟练
 - [ ] :disappointed: :blush:

 ## 另外的自定义view
 NineSquareGridView（最近的一个自定义view）完全使用kotlin，其余的部分使用kotlin

 https://github.com/cjwhaogaoleng/QQStepView.git

 https://github.com/cjwhaogaoleng/NineSquareGridView.git
 
 https://github.com/verticaldraglistview/VerticalDragListView.git

 https://github.com/cjwhaogaoleng/FlowLayout.git

 https://github.com/cjwhaogaoleng/LetterSideBar.git


Loading…
