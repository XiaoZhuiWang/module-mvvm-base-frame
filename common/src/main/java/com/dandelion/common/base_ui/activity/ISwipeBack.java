package com.dandelion.common.base_ui.activity;

import me.yokeyword.fragmentation.SwipeBackLayout;
import me.yokeyword.fragmentation_swipeback.core.ISwipeBackActivity;

/**
 * Created by lin.wang on 2021/7/27
 */
interface ISwipeBack extends ISwipeBackActivity {
    @Override
    default void setSwipeBackEnable(boolean enable){}

    @Override
    default void setEdgeLevel(SwipeBackLayout.EdgeLevel edgeLevel){}

    @Override
    default void setEdgeLevel(int widthPixel){}

    @Override
    default boolean swipeBackPriority(){
        //        return mDelegate.swipeBackPriority();
        return true;
    }
}
