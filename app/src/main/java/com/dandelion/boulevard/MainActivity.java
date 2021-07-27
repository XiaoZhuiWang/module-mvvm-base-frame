package com.dandelion.boulevard;

import android.view.LayoutInflater;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dandelion.boulevard.databinding.AppActivityMainBinding;
import com.dandelion.common.base_ui.activity.BaseActivity;
import com.dandelion.common.base_ui.fragment.SupportFragment;
import com.dandelion.communication.app.event.SwitchMainTabEvent;
import com.dandelion.communication.app.router.MainRouterTable;
import com.dandelion.communication.me.router.MeServiceUtil;
import com.dandelion.communication.story.router.StoryServiceUtil;
import com.dandelion.me.ui.MeFragment;
import com.dandelion.story.ui.StoryFragment;
import com.jeremyliao.liveeventbus.LiveEventBus;

import net.lucode.hackware.magicindicator.FragmentContainerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

/**
 * MainActivity，逻辑上应仅用于组合各业务组件首页
 * Created by lin.wang on 2021/6/24.
 */
@Route(path = MainRouterTable.PATH_PAGE_MAIN)
public class MainActivity extends BaseActivity {
    public static final int STORY_FRAGMENT_INDEX = 0;
    public static final int ME_FRAGMENT_INDEX = 1;

    AppActivityMainBinding mBinding;

    private FragmentContainerHelper mFragmentContainerHelper;
    private MainTabAdapter mMainTabAdapter;
    private CommonNavigator commonNavigator;

    private SupportFragment[] mFragments = new SupportFragment[5];

    public static void start(){
        ARouter.getInstance()
                .build(MainRouterTable.PATH_PAGE_MAIN)
                .navigation();
    }

    @Override
    protected void objectInject() {
        commonNavigator = new CommonNavigator(this);
        mMainTabAdapter = new MainTabAdapter(this);
        mFragmentContainerHelper = new FragmentContainerHelper();
    }

    @Override
    protected View initBinding(LayoutInflater layoutInflater) {
        mBinding = AppActivityMainBinding.inflate(layoutInflater);
        return mBinding.getRoot();
    }

    @Override
    protected void initView() {
        loadFragment();

        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(mMainTabAdapter);
        mBinding.magicIndicator.setNavigator(commonNavigator);
        mFragmentContainerHelper.attachMagicIndicator(mBinding.magicIndicator);
    }

    @Override
    protected void addListener() {
        mMainTabAdapter.setOnItemClickListener(new MainTabAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // 通知对应条目被选中
                mFragmentContainerHelper.handlePageSelected(position, false);
                showHideFragment(mFragments[position]);
            }
        });

        //订阅切换Tab事件
        LiveEventBus.get(SwitchMainTabEvent.KEY, SwitchMainTabEvent.class)
                .observe(this, new Observer<SwitchMainTabEvent>() {
                    @Override
                    public void onChanged(SwitchMainTabEvent switchMainTabEvent) {
                        // 切换到一级页面
                        showHideFragment(mFragments[switchMainTabEvent.getIndex()]);
                        if(switchMainTabEvent.getChildIndex() != SwitchMainTabEvent.NO_CHILD_PAGE){
                            // 切换到二级页面
                            switch (switchMainTabEvent.getIndex()) {
                                case STORY_FRAGMENT_INDEX:
                                    // 发送LiveEventBus事件
                                    break;

                                case ME_FRAGMENT_INDEX:

                                    break;
                            }
                        }
                    }
                });
    }

    @Override
    protected boolean isFillStatusBar() {
        return true;
    }

    @Override
    protected boolean canSwipeBack() {
        return false;
    }

    @Override
    protected int appointStatusBarColor() {
        return ContextCompat.getColor(mContext, com.dandelion.story.R.color.widget_bg_green_45B62D);
    }

    private void loadFragment() {
        SupportFragment firstFragment = findFragment(StoryFragment.class);
        if (firstFragment == null) {
            mFragments[STORY_FRAGMENT_INDEX] = (SupportFragment) StoryServiceUtil.navigateStoryPage();
            mFragments[ME_FRAGMENT_INDEX] = (SupportFragment) MeServiceUtil.navigateMePage();

            //加载所有同级Fragment，并显示第一个Fragment
            loadMultipleRootFragment(R.id.container, STORY_FRAGMENT_INDEX,
                    mFragments[STORY_FRAGMENT_INDEX],
                    mFragments[ME_FRAGMENT_INDEX]);
        } else {
            // 这里库已经做了Fragment恢复，所以不需要额外的处理了, 不会出现重叠问题
            // 这里我们需要拿到mFragments的引用
            mFragments[STORY_FRAGMENT_INDEX] = firstFragment;
            mFragments[ME_FRAGMENT_INDEX] = findFragment(MeFragment.class);
        }
    }
}