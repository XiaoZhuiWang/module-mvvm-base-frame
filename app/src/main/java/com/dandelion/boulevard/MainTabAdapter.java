package com.dandelion.boulevard;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.dandelion.boulevard.databinding.AppTabMainNavigationBinding;

import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView;

import java.util.ArrayList;
import java.util.List;

/**
 * 管理主界面导航Tab的Adapter
 * <p>
 * Created by wangl on 2017/9/28.
 */

public class MainTabAdapter extends CommonNavigatorAdapter {
    private final Context mContext;
    private final List<MainTabBean> mMainTabBeans ;
    private OnItemClickListener mOnItemClickListener;

    private int mSelectTab;

    public MainTabAdapter(Context context) {
        mContext = context;
        mMainTabBeans = new ArrayList<>();
        mMainTabBeans.add(new MainTabBean(R.drawable.app_ic_main_navigation_story,
                context.getString(R.string.app_story)));
        mMainTabBeans.add(new MainTabBean(R.drawable.app_ic_main_navigation_me,
                context.getString(R.string.app_me)));
    }

    @Override
    public int getCount() {
        return mMainTabBeans == null ? 0 : mMainTabBeans.size();
    }

    @Override
    public IPagerTitleView getTitleView(Context context, final int index) {
        final MainTabBean mainTabBean = mMainTabBeans.get(index);

        final CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(context);
        AppTabMainNavigationBinding mBinding = AppTabMainNavigationBinding.inflate(LayoutInflater.from(mContext));
        if(TextUtils.isEmpty(mainTabBean.getTitle())){
            mBinding.llTextTab.setVisibility(View.GONE);
            mBinding.ivImageTab.setVisibility(View.VISIBLE);
            mBinding.ivImageTab.setImageResource(mainTabBean.getIcon());
        }else {
            mBinding.llTextTab.setVisibility(View.VISIBLE);
            mBinding.ivImageTab.setVisibility(View.GONE);
            mBinding.tvTitle.setText(mainTabBean.getTitle());
            mBinding.ivIcon.setImageResource(mainTabBean.getIcon());
        }
        commonPagerTitleView.setContentView(mBinding.getRoot());

        commonPagerTitleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!commonPagerTitleView.isSelected() && mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(index);
                }
            }
        });
        commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() {

            @Override
            public void onSelected(int index, int totalCount) {
                commonPagerTitleView.setSelected(true);
                mSelectTab = index;
            }

            @Override
            public void onDeselected(int index, int totalCount) {
                commonPagerTitleView.setSelected(false);
            }

            @Override
            public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {

            }

            @Override
            public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {

            }
        });
        return commonPagerTitleView;
    }

    @Override
    public IPagerIndicator getIndicator(Context context) {
        return null;
    }

    /**
     * 获取被选中的Tab
     *
     * @return
     */
    public int getSelectTab() {
        return mSelectTab;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
