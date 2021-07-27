package com.dandelion.widget.magic_indicator

import androidx.viewpager2.widget.ViewPager2
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.ViewPagerHelper

/**
 * 让MagicIndicator支持ViewPager2
 * Created by lin.wang on 2021/7/22
 */
fun bind(magicIndicator: MagicIndicator, viewPager: ViewPager2) {
    viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            magicIndicator.onPageScrolled(position, positionOffset, positionOffsetPixels)
        }

        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            magicIndicator.onPageSelected(position)
        }

        override fun onPageScrollStateChanged(state: Int) {
            super.onPageScrollStateChanged(state)
            magicIndicator.onPageScrollStateChanged(state)
        }
    })
}
