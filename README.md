# TextColorChangeView
实现字体变色效果的view，可以和其余需要滑动的页面结合
## 效果展示
https://github.com/cjwhaogaoleng/TextColorChangeView/assets/117556474/7f792e20-3ab4-466d-b2f8-c1711dc01c06

以上展示的只是和viewPager相结合
## 引入依赖
暂未打包
## 代码讲解
'
viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
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
        });
'
