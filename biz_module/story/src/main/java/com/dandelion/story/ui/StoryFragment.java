package com.dandelion.story.ui;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dandelion.common.base_ui.fragment.BaseFragment;
import com.dandelion.communication.story.router.StoryRouterTable;
import com.dandelion.story.R;
import com.dandelion.story.databinding.StoryFragmentStoryBinding;
import com.dandelion.widget.magic_indicator.ViewPagerHelper2Kt;

import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 故事模块首页
 * Created by lin.wang on 2021/6/23.
 */
@Route(path = StoryRouterTable.PATH_PAGE_STORY)
public class StoryFragment extends BaseFragment {
    protected StoryFragmentStoryBinding mBinding;

    private List<String> mTabTitle;
    private FragmentStateAdapter mAdapter;
    private List<Fragment> mFragments;

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        modifyStatusBarFontColor(ContextCompat.getColor(mContext, R.color.widget_bg_white_FFFFFF));
    }

    @Override
    protected void objectInject() {
        mTabTitle = Arrays.asList("最新", "推荐");

        mFragments = new ArrayList<>();
        mFragments.add(ArticleListFragment.newInstance(ArticleListFragment.COLUMN_RECOMMEND));
        mFragments.add(ArticleListFragment.newInstance(ArticleListFragment.COLUMN_NEWEST));

        mAdapter = new FragmentStateAdapter(this) {
            @NotNull
            @Override
            public Fragment createFragment(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getItemCount() {
                return mFragments.size();
            }
        };

    }

    @Override
    protected View initBinding(LayoutInflater layoutInflater) {
        mBinding = StoryFragmentStoryBinding.inflate(layoutInflater);
        return mBinding.getRoot();
    }

    @Override
    protected void initView() {
        createIndicator();
        mBinding.vpStory.setAdapter(mAdapter);
    }

    @Override
    protected void addListener() {

    }

    private void createIndicator() {
        CommonNavigator commonNavigator = new CommonNavigator(mContext);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mTabTitle.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int i) {
                SimplePagerTitleView titleView = new SimplePagerTitleView(mContext);
                titleView.setText(mTabTitle.get(i));
                titleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                titleView.setNormalColor(ContextCompat.getColor(mContext, R.color.widget_text_gray_333333));
                titleView.setSelectedColor(ContextCompat.getColor(mContext, R.color.widget_text_green_45B62D));
                titleView.setOnClickListener(view -> mBinding.vpStory.setCurrentItem(i));
                return titleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
                linePagerIndicator.setColors(ContextCompat.getColor(mContext, R.color.widget_bg_green_45B62D));
                linePagerIndicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                return linePagerIndicator;
            }
        });
        commonNavigator.setAdjustMode(true);
        mBinding.magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper2Kt.bind(mBinding.magicIndicator, mBinding.vpStory);
    }
}