package com.dandelion.communication.app.event;

/**
 * 切换MainActivity Tab事件
 * Created by lin.wang on 2021/7/21
 */
public class SwitchMainTabEvent {
    public static final String KEY = "app_switch_tab";
    public static final int NO_CHILD_PAGE = -1;

    // 一级页面
    private int index;
    // 二级页面
    private int childIndex = NO_CHILD_PAGE;

    public SwitchMainTabEvent(int index) {
        this.index = index;
    }

    public SwitchMainTabEvent(int index, int childIndex) {
        this.index = index;
        this.childIndex = childIndex;
    }

    public int getIndex() {
        return index;
    }

    public int getChildIndex() {
        return childIndex;
    }
}
