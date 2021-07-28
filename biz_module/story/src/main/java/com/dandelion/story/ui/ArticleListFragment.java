package com.dandelion.story.ui;

import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.dandelion.common.base_ui.fragment.SimpleListBaseFragment;
import com.dandelion.communication.story.router.StoryRouterTable;
import com.dandelion.story.R;
import com.dandelion.story.data.bean.ArticleBean;
import com.dandelion.story.databinding.StoryItemArticleBinding;
import com.dandelion.story.vm.ArticleListVM;

import org.jetbrains.annotations.NotNull;

/**
 * 文章列表
 * Created by lin.wang on 2021.07.21
 */
@Route(path = StoryRouterTable.PATH_PAGE_ARTICLE_LIST)
public class ArticleListFragment extends SimpleListBaseFragment<ArticleListVM, ArticleBean> {

    public static final int COLUMN_RECOMMEND = 0;
    public static final int COLUMN_NEWEST = 1;
    @Autowired(name = "column")
    int mColumn; //栏目：最新、推荐...

    public static ArticleListFragment newInstance(int column) {
        return (ArticleListFragment) ARouter.getInstance()
                .build(StoryRouterTable.PATH_PAGE_ARTICLE_LIST)
                .withInt("column", column)
                .navigation();
    }

    @Override
    protected int attachItemLayoutRes() {
        return R.layout.story_item_article;
    }

    @Override
    protected void convertItem(BaseViewHolder baseViewHolder, ArticleBean articleBean) {
        StoryItemArticleBinding binding = baseViewHolder.getBinding();
        if (binding != null) {
            binding.tvTitle.setText(articleBean.getTitle());
            binding.tvContent.setText(articleBean.getContent());
        }
    }

    @Override
    protected View onAttachLoadSir() {
        return mColumn == COLUMN_RECOMMEND ? null : super.onAttachLoadSir();
    }

    @NotNull
    @Override
    protected ArticleListVM initViewModel() {
        ArticleListVM articleListVM = new ViewModelProvider(this).get(ArticleListVM.class);
        articleListVM.setColumn(mColumn);
        return articleListVM;
    }

    @Override
    protected void addListener() {
        super.addListener();
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NotNull BaseQuickAdapter<?, ?> adapter, @NotNull View view, int position) {

            }
        });

    }


}