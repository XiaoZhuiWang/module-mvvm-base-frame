package com.dandelion.utils;

import android.app.Activity;

import java.util.Stack;

public class ActivityManager {

    private volatile static ActivityManager instance;
    private static Stack<Activity> sActivityStack;

    private ActivityManager() {
    }

    public static ActivityManager getInstance() {
        if (instance == null) {
            synchronized (ActivityManager.class) {
                if (instance == null) {
                    instance = new ActivityManager();
                }
            }
        }
        return instance;
    }

    public void addActivity(Activity activity) {
        if (sActivityStack == null) {
            sActivityStack = new Stack<>();
        }
        sActivityStack.add(activity);
    }

    public int getActivityCount() {
        if (sActivityStack != null) {
            return sActivityStack.size();
        }
        return 0;
    }

    public Activity getTopActivity() {
        if (sActivityStack != null && sActivityStack.size() > 0) {
            return sActivityStack.lastElement();
        }
        return null;
    }

    public void removeActivity(Activity activity) {
        if (activity != null) {
            if (sActivityStack != null) {
                sActivityStack.remove(activity);
            }
        }
    }

    public void finishActivity(Activity activity) {
        if (activity != null) {
            if (sActivityStack != null) {
                sActivityStack.remove(activity);
            }
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    public void finishAllActivity() {
        if (sActivityStack != null) {
            for (int i = 0; i < sActivityStack.size(); i++) {
                finishActivity(sActivityStack.get(i));
            }
            sActivityStack.clear();
        }
    }

    public void exitApp() {
        try {
            finishAllActivity();
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        } catch (Exception e) {
        }
    }

    public Stack<Activity> getActivitys() {
        return sActivityStack;
    }
}