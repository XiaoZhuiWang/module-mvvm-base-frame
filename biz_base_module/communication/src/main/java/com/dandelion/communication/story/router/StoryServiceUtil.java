package com.dandelion.communication.story.router;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * 故事组件服务工具类
 * 其他组件使用此类，即可跳转故事组件相关页面、调用故事组件服务
 * Created by lin.wang on 2021/6/24.
 */
public class StoryServiceUtil {
    /**
     * 故事模块首页
     *
     * @return
     */
    public static Fragment navigateStoryPage() {
        return (Fragment) ARouter.getInstance()
                .build(StoryRouterTable.PATH_PAGE_STORY)
                .navigation();
    }
}
