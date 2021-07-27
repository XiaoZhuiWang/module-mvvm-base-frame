package com.dandelion.boulevard;

import androidx.annotation.DrawableRes;

import com.dandelion.network.bean.IBean;

/**
 * MainActivity的Tab
 * Created by lin.wang on 2021/7/21
 */
public class MainTabBean implements IBean {

    @DrawableRes
    private int icon;
    private String title;

    public MainTabBean(@DrawableRes int icon, String title) {
        this.icon = icon;
        this.title = title;
    }


    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}