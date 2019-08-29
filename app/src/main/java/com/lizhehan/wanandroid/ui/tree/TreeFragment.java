package com.lizhehan.wanandroid.ui.tree;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
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

public class TreeFragment extends BaseFragment implements TreeContract.View,
        TreeAdapter.OnItemClickListener {
    @BindView(R.id.rv)
    RecyclerView rvSystem;
    @BindView(R.id.normal_view)
    SwipeRefreshLayout swipeRefreshLayout;
//    @BindView(R.id.normal_view)
//    SmartRefreshLayout normalView;

    private List<TreeBean> treeBeanList;
    private TreePresenter presenter;
    private TreeAdapter madapter;

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
        madapter = new TreeAdapter(R.layout.item_tree, treeBeanList);
        presenter.getSystemList();
        madapter.setOnItemClickListener(this);
        rvSystem.setAdapter(madapter);
    }

    @Override
    protected void initView() {
        super.initView();
        showLoading();
        setRefresh();
        rvSystem.setLayoutManager(new LinearLayoutManager(context));
    }

    @Override
    public void getSystemListOk(List<TreeBean> dataBean) {
        treeBeanList = dataBean;
        madapter.replaceData(dataBean);
        showNormal();
    }

    @Override
    public void getSystemListErr(String info) {
        showError(info);
    }

    /**
     * SmartRefreshLayout刷新加载
     */
    private void setRefresh() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.autoRefresh();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void reload() {
        showLoading();
        presenter.getSystemList();
    }

    /**
     * 回到顶部
     */
    public void scrollToTop() {
        rvSystem.scrollToPosition(0);
    }

    /**
     * item 点击事件
     *
     * @param adapter
     * @param view
     * @param position
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(activity, view, getString(R.string.web_view));
        Intent intent = new Intent(activity, TreeDetailActivity.class);
        intent.putExtra(ConstantUtil.TREE, madapter.getData().get(position));
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}
