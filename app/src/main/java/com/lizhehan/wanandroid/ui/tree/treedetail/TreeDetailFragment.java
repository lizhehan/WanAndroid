package com.lizhehan.wanandroid.ui.tree.treedetail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lizhehan.wanandroid.R;
import com.lizhehan.wanandroid.base.BaseFragment;
import com.lizhehan.wanandroid.data.bean.TreeDetailBean;
import com.lizhehan.wanandroid.ui.tree.treedetail.adapter.TreeDetailRecyclerViewAdapter;
import com.lizhehan.wanandroid.ui.webview.WebViewActivity;
import com.lizhehan.wanandroid.util.ConstantUtil;
import com.lizhehan.wanandroid.util.IntentUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 体系详情界面 碎片  ( 体系 二级数据详细信息列表)
 */

public class TreeDetailFragment extends BaseFragment implements TreeDetailContract.View
        , TreeDetailRecyclerViewAdapter.OnItemClickListener {
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.normal_view)
    SwipeRefreshLayout swipeRefreshLayout;

    private int id;
    private TreeDetailPresenter presenter;
    private List<TreeDetailBean.DatasBean> datasBeanList;
    private TreeDetailRecyclerViewAdapter adapter;
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

    public static TreeDetailFragment getInstance(int id) {
        TreeDetailFragment fragment = new TreeDetailFragment();
        Bundle args = new Bundle();
        args.putInt(ConstantUtil.TREE_FRAGMENT_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

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
        presenter = new TreeDetailPresenter(this);
        if (getArguments() != null) {
            id = getArguments().getInt(ConstantUtil.TREE_FRAGMENT_ID);
            presenter.getSystemDetailList(0, id);
        }
        datasBeanList = new ArrayList<>();
        adapter = new TreeDetailRecyclerViewAdapter(R.layout.item_home, datasBeanList);
        adapter.setOnItemClickListener(this);
        rv.setAdapter(adapter);

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
    public void getSystemDetailListResultOK(TreeDetailBean treeDetailBean, boolean isRefresh) {
        showNormal();
        if (isRefresh) {
            datasBeanList = treeDetailBean.getDatas();
            adapter.replaceData(treeDetailBean.getDatas());
        } else {
            if (treeDetailBean.getDatas().size() > 0) {
                datasBeanList.addAll(treeDetailBean.getDatas());
                adapter.addData(treeDetailBean.getDatas());
            } else {
                loading = true;
            }
        }
    }

    @Override
    public void getSystemDetailListResultErr(String info) {
        showError(info);
    }


    /**
     * item 点击事件
     *
     * @param madapter
     * @param view
     * @param position
     */
    @Override
    public void onItemClick(BaseQuickAdapter madapter, View view, int position) {
        TreeDetailBean.DatasBean bean = adapter.getData().get(position);
        Bundle bundle = new Bundle();
        bundle.putInt(ConstantUtil.DETAIL_ID, bean.getId());
        bundle.putString(ConstantUtil.DETAIL_PATH, bean.getLink());
        bundle.putString(ConstantUtil.DETAIL_TITLE, bean.getTitle());
        bundle.putBoolean(ConstantUtil.DETAIL_IS_COLLECT, bean.isCollect());
        bundle.putString(ConstantUtil.DETAIL_AUTHOR, bean.getAuthor());
        IntentUtil.startActivity(activity, WebViewActivity.class, bundle);
    }

    public void scrollToTop() {
        rv.smoothScrollToPosition(0);
    }


    @Override
    public void reload() {
        super.reload();
        if (presenter != null && id != -1) {
            presenter.getSystemDetailList(0, id);
        }
        showLoading();
    }
}