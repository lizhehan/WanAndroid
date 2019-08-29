package com.lizhehan.wanandroid.ui.collect;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lizhehan.wanandroid.R;
import com.lizhehan.wanandroid.base.BaseResultActivity;
import com.lizhehan.wanandroid.data.bean.CollectBean;
import com.lizhehan.wanandroid.ui.collect.adapter.CollectRecyclerViewAdapter;
import com.lizhehan.wanandroid.ui.webview.WebViewActivity;
import com.lizhehan.wanandroid.ui.webview.WebViewContract;
import com.lizhehan.wanandroid.ui.webview.WebViewPresenter;
import com.lizhehan.wanandroid.util.ConstantUtil;
import com.lizhehan.wanandroid.util.IntentUtil;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;

public class CollectActivity extends BaseResultActivity implements CollectContract.View, WebViewContract.view,
        CollectRecyclerViewAdapter.OnItemClickListener, CollectRecyclerViewAdapter.OnItemChildClickListener {

    @BindView(R.id.toolbar_collect)
    Toolbar collectToolbar;
    @BindView(R.id.rv_collect)
    RecyclerView collectRecyclerView;
    @BindView(R.id.normal_view)
    SwipeRefreshLayout collectSwipeRefreshLayout;

    private CollectPresenter collectPresenter;
    private WebViewPresenter webViewPresenter;
    private CollectRecyclerViewAdapter mAdapter;
    private List<CollectBean.DatasBean> datasBeanList;
    private boolean loading;
    RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            if (!loading && linearLayoutManager.getItemCount() == (linearLayoutManager.findLastVisibleItemPosition() + 10)) {
                loading = true;
                collectPresenter.onLoadMore();
                loading = false;
            }
        }
    };
    private int collectPosition;
    private CollectBean.DatasBean collectBean;
    private int collectOriginId;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_collect;
    }

    @Override
    protected void initView() {
        super.initView();
        showLoading();
        collectToolbar.setTitle(getString(R.string.dialog_bottom_sheet_collect));
        setSupportActionBar(collectToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        collectRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        collectToolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
    }

    @Override
    protected void initData() {
        setRefresh();
        datasBeanList = new LinkedList<>();
        collectPresenter = new CollectPresenter(this);
        webViewPresenter = new WebViewPresenter(this);
        mAdapter = new CollectRecyclerViewAdapter(R.layout.item_home, datasBeanList);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemChildClickListener(this);
        collectRecyclerView.setAdapter(mAdapter);
//        ItemTouchHelper.Callback callback = new ItemTouchHelperCallback(adapter);
//        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(callback);
//        mItemTouchHelper.attachToRecyclerView(collectRecyclerView);
        collectPresenter.getCollectionList(0);
    }

    private void setRefresh() {
        collectSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                collectPresenter.onRefresh();
                loading = false;
            }
        });

        collectRecyclerView.addOnScrollListener(scrollListener);
    }

    @Override
    public void getCollectionListOK(CollectBean collectBean, boolean isRefresh) {
        if (collectSwipeRefreshLayout.isRefreshing()) {
            collectSwipeRefreshLayout.setRefreshing(false);
        }
        if (isRefresh) {
            datasBeanList = collectBean.getDatas();
            mAdapter.replaceData(datasBeanList);
//            adapter.setItems(datasBeanList);
        } else {
            if (collectBean.getDatas().size() > 0) {
                datasBeanList.addAll(collectBean.getDatas());
                mAdapter.addData(collectBean.getDatas());
//            adapter.setItems(datasBeanList);
            } else {
                loading = true;
            }
        }
        showNormal();
    }

    @Override
    public void getCollectionListErr(String err) {
        if (collectSwipeRefreshLayout.isRefreshing()) {
            collectSwipeRefreshLayout.setRefreshing(false);
        }
        showError(err);
    }

    @Override
    public void collectArticleOK(String info) {
        mAdapter.addData(collectPosition, collectBean);
    }

    @Override
    public void collectArticleErr(String info) {
        Toast.makeText(activity, getString(R.string.collect_fail) + info, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void cancelCollectArticleOK(String info) {
        mAdapter.remove(collectPosition);
        Snackbar.make(collectSwipeRefreshLayout, context.getString(R.string.cancel_collect_success), Snackbar.LENGTH_LONG)
                .setAction(context.getString(R.string.undo), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        webViewPresenter.collectArticle(collectOriginId);
                    }
                }).show();
    }

    @Override
    public void cancelCollectArticleErr(String info) {
        Toast.makeText(activity, getString(R.string.cancel_collect_fail) + info, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void reload() {
        super.reload();
        showLoading();
        collectPresenter.onRefresh();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        CollectBean.DatasBean bean = mAdapter.getData().get(position);
        Bundle bundle = new Bundle();
        bundle.putInt(ConstantUtil.DETAIL_ID, bean.getOriginId());
        bundle.putString(ConstantUtil.DETAIL_PATH, bean.getLink());
        bundle.putString(ConstantUtil.DETAIL_TITLE, bean.getTitle());
        bundle.putBoolean(ConstantUtil.DETAIL_IS_COLLECT, ConstantUtil.TRUE);
        bundle.putString(ConstantUtil.DETAIL_AUTHOR, bean.getAuthor());
        IntentUtil.startActivity(activity, WebViewActivity.class, bundle);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        if (view.getId() == R.id.image_collect) {
            collectBean = mAdapter.getData().get(position);
            collectPosition = position;
            collectOriginId = collectBean.getOriginId();
            webViewPresenter.cancelCollectArticle(collectOriginId);
        }
    }
}
