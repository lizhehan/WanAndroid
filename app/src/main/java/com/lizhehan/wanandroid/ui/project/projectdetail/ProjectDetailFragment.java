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
import com.lizhehan.wanandroid.data.bean.ProjectDetailBean;
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

    ProjectDetailRecyclerViewAdapter adapter;
    ProjectDetailPresenter presenter;
    List<ProjectDetailBean.DatasBean> demoDetailListBeans;
    private boolean loading;
    RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            if (!loading && linearLayoutManager.getItemCount() == (linearLayoutManager.findLastVisibleItemPosition() + 10)) {
                loading = true;
                presenter.loadMore();
                loading = false;
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
        adapter = new ProjectDetailRecyclerViewAdapter(R.layout.item_project, demoDetailListBeans);
        adapter.setOnItemClickListener(this);
        if (getArguments() != null) {
            id = getArguments().getInt(ConstantUtil.PROJECT_FRAGMENT_ID);
            presenter.getDemoList(1, id);
        }
        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void setRefresh() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.autoRefresh();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        rv.addOnScrollListener(scrollListener);
    }

    @Override
    public void reload() {
        super.reload();
        if (id != -1) {
            presenter.getDemoList(1, id);
        }
    }

    /**
     * 刷新 重置，不刷新 添加
     */
    @Override
    public void getDemoListOK(ProjectDetailBean beans, boolean isRefresh) {
        if (id == -1 && adapter == null) {
            return;
        }
        if (isRefresh) {
            demoDetailListBeans.clear();
            demoDetailListBeans.addAll(beans.getDatas());
            adapter.replaceData(beans.getDatas());
        } else {
            if (beans.getDatas().size() > 0) {
                demoDetailListBeans.addAll(beans.getDatas());
                adapter.addData(beans.getDatas());
            } else {
                loading = true;
            }
        }
        showNormal();
    }

    @Override
    public void getDemoListErr(String err) {
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
     * @param madapter
     * @param view
     * @param position
     */
    @Override
    public void onItemClick(BaseQuickAdapter madapter, View view, int position) {
        ProjectDetailBean.DatasBean bean = adapter.getData().get(position);
        Bundle bundle = new Bundle();
        bundle.putInt(ConstantUtil.DETAIL_ID, bean.getId());
        bundle.putString(ConstantUtil.DETAIL_PATH, bean.getLink());
        bundle.putString(ConstantUtil.DETAIL_TITLE, bean.getTitle());
        bundle.putBoolean(ConstantUtil.DETAIL_IS_COLLECT, bean.isCollect());
        bundle.putString(ConstantUtil.DETAIL_AUTHOR, bean.getAuthor());
        IntentUtil.startActivity(activity, WebViewActivity.class, bundle);
    }
}
