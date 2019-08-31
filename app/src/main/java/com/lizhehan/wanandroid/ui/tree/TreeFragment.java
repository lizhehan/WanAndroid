package com.lizhehan.wanandroid.ui.tree;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lizhehan.wanandroid.R;
import com.lizhehan.wanandroid.base.BaseFragment;
import com.lizhehan.wanandroid.data.bean.TreeBean;
import com.lizhehan.wanandroid.ui.tree.adapter.TreeAdapter;
import com.lizhehan.wanandroid.ui.tree.treedetail.TreeDetailActivity;
import com.lizhehan.wanandroid.util.ConstantUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class TreeFragment extends BaseFragment implements TreeContract.View, TreeAdapter.OnItemClickListener {
    @BindView(R.id.rv)
    RecyclerView rvTree;
    @BindView(R.id.normal_view)
    SwipeRefreshLayout swipeRefreshLayout;

    private List<TreeBean> treeBeanList;
    private TreePresenter presenter;
    private TreeAdapter mAdapter;

    public static TreeFragment getInstance() {
        return new TreeFragment();
    }

    @Override
    public int getLayoutResID() {
        return R.layout.fragment_tree;
    }

    @Override
    protected void initData() {
        presenter = new TreePresenter(this);
        treeBeanList = new ArrayList<>();
        mAdapter = new TreeAdapter(R.layout.item_tree, treeBeanList);
        presenter.getTree();
        mAdapter.setOnItemClickListener(this);
        rvTree.setAdapter(mAdapter);
    }

    @Override
    protected void initView() {
        super.initView();
        showLoading();
        setRefresh();
        rvTree.setLayoutManager(new LinearLayoutManager(context));
    }

    @Override
    public void getTreeOk(List<TreeBean> dataBean) {
        if (swipeRefreshLayout != null) {
            if (swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);
            }
        }
        treeBeanList = dataBean;
        mAdapter.replaceData(dataBean);
        showNormal();
    }

    @Override
    public void getTreeErr(String info) {
        if (swipeRefreshLayout != null) {
            if (swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);
            }
        }
        showError(info);
    }

    /**
     * SmartRefreshLayout刷新加载
     */
    private void setRefresh() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.refresh();
            }
        });
    }

    @Override
    public void reload() {
        showLoading();
        presenter.getTree();
    }

    /**
     * 回到顶部
     */
    public void scrollToTop() {
        rvTree.scrollToPosition(0);
    }

    /**
     * item 点击事件
     *
     * @param adapter
     * @param view
     * @param position
     */
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Intent intent = new Intent(activity, TreeDetailActivity.class);
        intent.putExtra(ConstantUtil.TREE, mAdapter.getData().get(position));
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}
