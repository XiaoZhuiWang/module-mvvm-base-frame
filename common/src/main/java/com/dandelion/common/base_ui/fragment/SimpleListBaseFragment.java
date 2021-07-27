package com.dandelion.common.base_ui.fragment;

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
 * 简单list布局
 * 继承这个Fragment，并创建一个继承自SimpleListBaseFragment.SimpleListVM的ViewModel
 * 泛型V为列表数据类型
 * Created by lin.wang on 2021.07.22
 */
public abstract class SimpleListBaseFragment<T extends SimpleListBaseFragment.SimpleListVM<V>, V>
        extends PullBaseFragment<T> {

    protected CommonActivitySimpleListBinding mBinding;

    protected SimpleAdapter mAdapter;

    @SuppressWarnings("unchecked")
    @NotNull
    @Override
    protected T initViewModel() {
        T viewModel = instanceViewModel();
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
        return viewModel;
    }

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
     * 创建ViewModel
     *
     * @return
     */
    protected abstract T instanceViewModel();

    /**
     * 设置条目
     *
     * @param baseViewHolder
     * @param v
     */
    protected abstract void convertItem(BaseViewHolder baseViewHolder, V v);

    private class SimpleAdapter extends BaseQuickAdapter<V, BaseViewHolder> {

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
