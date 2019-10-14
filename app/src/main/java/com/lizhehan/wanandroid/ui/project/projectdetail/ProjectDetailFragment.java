package com.lizhehan.wanandroid.ui.project.projectdetail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lizhehan.wanandroid.R;
import com.lizhehan.wanandroid.base.BaseFragment;
import com.lizhehan.wanandroid.data.bean.ArticleBean;
import com.lizhehan.wanandroid.ui.project.projectdetail.adapter.ProjectDetailRecyclerViewAdapter;
import com.lizhehan.wanandroid.ui.webview.WebViewActivity;
import com.lizhehan.wanandroid.util.ConstantUtil;
import com.lizhehan.wanandroid.util.IntentUtil;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;

/**
 * 项目 列表fragment
 */

public class ProjectDetailFragment extends BaseFragment implements ProjectDetailContract.View,
        BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.normal_view)
    SwipeRefreshLayout swipeRefreshLayout;

    private ProjectDetailRecyclerViewAdapter mAdapter;
    private ProjectDetailPresenter presenter;
    private List<ArticleBean.DatasBean> demoDetailListBeans;
    private boolean loading;
    private RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            if (!loading && linearLayoutManager.getItemCount() == (linearLayoutManager.findLastVisibleItemPosition() + 10)) {
                presenter.loadMore();
            }
        }
    };
    /**
     * id 编号
     */
    private int id = -1;

    public static ProjectDetailFragment getInstance(int id) {
        ProjectDetailFragment fragment = new ProjectDetailFragment();
        Bundle args = new Bundle();
        args.putInt(ConstantUtil.PROJECT_FRAGMENT_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 布局复用 体系 的列表界面
     *
     * @return
     */
    @Override
    public int getLayoutResID() {
        return R.layout.fragment_tree_detail;
    }

    @Override
    protected void initView() {
        super.initView();
        setRefresh();
        showLoading();
        rv.setLayoutManager(new LinearLayoutManager(activity));
    }

    @Override
    protected void initData() {
        presenter = new ProjectDetailPresenter(this);
        demoDetailListBeans = new LinkedList<>();
        mAdapter = new ProjectDetailRecyclerViewAdapter(R.layout.item_project, demoDetailListBeans);
        mAdapter.setOnItemClickListener(this);
        if (getArguments() != null) {
            id = getArguments().getInt(ConstantUtil.PROJECT_FRAGMENT_ID);
            presenter.getProjectDetail(1, id);
        }
        rv.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    private void setRefresh() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.refresh();
                loading = false;
            }
        });

        rv.addOnScrollListener(scrollListener);
    }

    @Override
    public void reload() {
        super.reload();
        if (id != -1) {
            presenter.getProjectDetail(1, id);
        }
    }

    /**
     * 刷新 重置，不刷新 添加
     */
    @Override
    public void getProjectDetailOK(ArticleBean articleBean, boolean isRefresh) {
        if (swipeRefreshLayout != null) {
            if (swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);
            }
        }
        if (id == -1 && mAdapter == null) {
            return;
        }
        if (isRefresh) {
            demoDetailListBeans.clear();
            demoDetailListBeans.addAll(articleBean.getDatas());
            mAdapter.replaceData(articleBean.getDatas());
        } else {
            if (articleBean.getDatas().size() > 0) {
                demoDetailListBeans.addAll(articleBean.getDatas());
                mAdapter.addData(articleBean.getDatas());
            } else {
                loading = true;
            }
        }
        showNormal();
    }

    @Override
    public void getProjectDetailErr(String err) {
        if (swipeRefreshLayout != null) {
            if (swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);
            }
        }
        showError(err);
    }

    /**
     * 滚动到 顶部
     */
    public void scrollToTop() {
        rv.scrollToPosition(0);
    }

    /**
     * item 跳转事件
     *
     * @param adapter
     * @param view
     * @param position
     */
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ArticleBean.DatasBean bean = mAdapter.getData().get(position);
        Bundle bundle = new Bundle();
        bundle.putInt(ConstantUtil.DETAIL_ID, bean.getId());
        bundle.putString(ConstantUtil.DETAIL_PATH, bean.getLink());
        bundle.putString(ConstantUtil.DETAIL_TITLE, bean.getTitle());
        bundle.putBoolean(ConstantUtil.DETAIL_IS_COLLECT, bean.isCollect());
        bundle.putString(ConstantUtil.DETAIL_AUTHOR, bean.getAuthor());
        IntentUtil.startActivity(activity, WebViewActivity.class, bundle);
    }
}
