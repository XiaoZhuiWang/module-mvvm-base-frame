package com.dandelion.common.base_ui.activity;

/**
 * @author: Lyn Wang
 * @descrption: 简单的List布局Activity
 * @date: 2018/4/25.
 */

public abstract class SimpleListBaseActivity<V> extends PullBaseActivity{

//    protected RecyclerView recyclerView;
//    protected SmartRefreshLayout refreshLayout;
//    protected LinearLayout contentLayout;
//
//    protected SimpleAdapter mAdapter;
//
//    @Override
//    protected int attachLayoutRes() {
//        return R.layout.activity_simple_list;
//    }
//
//    @Override
//    protected void objectInject() {
//        mPresenter = new Presenter();
//        mCompositeDisposable = new CompositeDisposable();
//        mAdapter = new SimpleAdapter();
//    }
//
//    @Override
//    protected void initView() {
//        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
//        refreshLayout = (SmartRefreshLayout) findViewById(R.id.refresh_layout);
//        contentLayout = (LinearLayout) findViewById(R.id.content_layout);
//
//        recyclerView.setAdapter(mAdapter);
//    }
//
//    @Override
//    public <T> void onRefreshSuccess(T data) {
//        List<V> entities = CastUtil.cast(data);
//        mAdapter.addData(0, entities);
//    }
//
//    @Override
//    public void onRefreshFail() {
//    }
//
//    @Override
//    public <T> void onLoadMoreSuccess(T data) {
//        List<V> entities = CastUtil.cast(data);
//        mAdapter.addData(entities);
//    }
//
//    @Override
//    public void onLoadMoreFail() {
//
//    }
//
//    @Override
//    public <T> void onLoadDefaultSuccess(T data) {
//        List<V> entities = CastUtil.cast(data);
//        mAdapter.setNewData(entities);
//    }
//
//    @Override
//    public void onLoadDefaultFail() {
//
//    }
//
//    @Override
//    protected SmartRefreshLayout onAttachRefreshLayout() {
//        return refreshLayout;
//    }
//
//    protected class SimpleAdapter extends BaseQuickAdapter<V, BaseViewHolder> {
//
//        public SimpleAdapter() {
//            super(attachItemLayoutRes());
//        }
//
//        @Override
//        protected void convert(BaseViewHolder baseViewHolder, V v) {
//            convertItem(baseViewHolder, v);
//        }
//    }
//
//    protected class Presenter extends BasePresenterImpl<PullBaseContract.View> implements PullBaseContract.Presenter {
//
//
//        public Presenter() {
//        }
//
//        @Override
//        public void onLoadDefault() {
//            loadDefault();
//        }
//
//        @Override
//        public void onRefresh(boolean isRefresh) {
//            refresh();
//        }
//
//        @Override
//        public void onLoadMore() {
//            loadMore();
//        }
//    }
//
//    /**
//     * 绑定条目布局
//     *
//     * @return
//     */
//    protected abstract int attachItemLayoutRes();
//
//
//    /**
//     * 设置条目
//     *
//     * @param baseViewHolder
//     * @param v
//     */
//    protected abstract void convertItem(BaseViewHolder baseViewHolder, V v);
//
//    /**
//     * 加载初始数据，获取到数据后调用{@linkplain #onRefreshSuccess(Object)}
//     */
//    protected abstract void loadDefault();
//
//    /**
//     * 获取刷新数据，获取到数据后调用{@linkplain #onRefreshSuccess(Object)}
//     */
//    protected abstract void refresh();
//
//    /**
//     * 获取更多数据，获取到数据后调用{@linkplain #onLoadMoreSuccess(Object)}
//     */
//    protected abstract void loadMore();

}
