package com.dandelion.common.base_ui.activity;

import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.dandelion.common.base_vm.PullBaseVM;
import com.dandelion.common.databinding.CommonActivitySimpleListBinding;
import com.dandelion.utils.CollectionUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author: Lyn Wang
 * @descrption: 简单的List布局Activity
 * @date: 2018/4/25.
 */

public abstract class SimpleListBaseActivity<T extends SimpleListBaseActivity.SimpleListVM<V>, V>
        extends PullBaseActivity<T>{

    protected CommonActivitySimpleListBinding mBinding;

    protected SimpleAdapter mAdapter;


    @Override
    protected void objectInject() {
        mAdapter = new SimpleAdapter();
    }

    @Override
    protected View initBinding(LayoutInflater layoutInflater) {
        mBinding = CommonActivitySimpleListBinding.inflate(layoutInflater);
        return mBinding.getRoot();
    }

    @Override
    protected void initView() {
        View rowLayout = attachRowLayout();
        if (rowLayout != null) {
            mBinding.contentLayout.addView(rowLayout, 0);
        }

        View headerView = addHeaderView();
        if(headerView != null){
            mAdapter.addHeaderView(headerView);
        }

        mBinding.recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void addListener() {
        viewModel.getDataLiveData().observe(this, new Observer<List<V>>() {
            @Override
            public void onChanged(List<V> vs) {
                if (CollectionUtil.isEmpty(mAdapter.getData())) {
                    mAdapter.setNewInstance(vs);
                } else {
                    // TODO: 2021/7/22 可以优化成局部刷新，如何拿到加载类型？
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    protected SmartRefreshLayout onAttachRefreshLayout() {
        return mBinding.refreshLayout;
    }

    @Override
    protected View onAttachLoadSir() {
        return mBinding.refreshLayout;
    }

    /**
     * 绑定条目布局
     *
     * @return
     */
    protected abstract int attachItemLayoutRes();

    /**
     * 在RecyclerView，上面添加一个布局
     * 应用场景：如条目的列名
     *
     * @return
     */
    protected View attachRowLayout() {
        return null;
    }

    /**
     * 给RecyclerView添加一个头部View，该View可以随RecyclerView滑动
     *
     * @return
     */
    protected View addHeaderView() {
        return null;
    }

    /**
     * 设置条目
     *
     * @param baseViewHolder
     * @param v
     */
    protected abstract void convertItem(BaseViewHolder baseViewHolder, V v);

    protected class SimpleAdapter extends BaseQuickAdapter<V, BaseViewHolder> {

        public SimpleAdapter() {
            super(attachItemLayoutRes());
        }

        @Override
        protected void onItemViewHolderCreated(@NotNull BaseViewHolder viewHolder, int viewType) {
            super.onItemViewHolderCreated(viewHolder, viewType);
            // 支持ViewBinding
            DataBindingUtil.bind(viewHolder.itemView);
        }

        @Override
        protected void convert(@NotNull BaseViewHolder baseViewHolder, V v) {
            convertItem(baseViewHolder, v);
        }
    }


    public static abstract class SimpleListVM<V> extends PullBaseVM {
        protected final MutableLiveData<List<V>> mDataLiveData = new MutableLiveData<>();

        public SimpleListVM(@NonNull @NotNull Application application) {
            super(application);
        }

        public MutableLiveData<List<V>> getDataLiveData() {
            return mDataLiveData;
        }
    }

}
